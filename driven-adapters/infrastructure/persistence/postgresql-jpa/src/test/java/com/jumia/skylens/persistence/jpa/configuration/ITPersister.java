package com.jumia.skylens.persistence.jpa.configuration;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@TestComponent
public class ITPersister {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public <E> Collection<E> save(Collection<E> entities) {

        return entities.stream()
                .map(this::save)
                .toList();
    }

    @Transactional
    public <E> Collection<E> save(E... entities) {

        return Arrays.stream(entities)
                .map(this::save)
                .toList();
    }

    @Transactional
    public <E> E save(E entity) {

        return save(entity, new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    private <E> E save(E entity, List<Object> entitiesBeingPersisted) {

        final Class<?> entityClass = entity.getClass();

        // Map of (field, collection) used to store all OneToMany entity relation
        // that should be persisted after the main entity.
        final Map<Relationship, Collection<Object>> dependencies = new HashMap<>();

        // Prevent cycles
        if (entitiesBeingPersisted.contains(entity)) {
            return entity;
        }

        entitiesBeingPersisted.add(entity);

        final Map<Field, Object> persistLater = new HashMap<>();

        final List<Field> fields = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> Modifier.isPrivate(field.getModifiers()) || Modifier.isProtected(field.getModifiers()))
                .toList();

        fields.forEach(field -> {
            try {
                if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {

                    field.setAccessible(true);
                    final Object var = field.get(entity);

                    if (var != null) {
                        if (var instanceof Collection) {
                            final Relationship rel = parseRelationship(entityClass, field);
                            if (isEntity(rel.getChildType())) {
                                dependencies.put(rel, (Collection<Object>) var);
                            }
                        } else if (entityClass.equals(var.getClass())) {
                            field.set(entity, null);
                            persistLater.put(field, var);
                        } else if (isEntity(var.getClass())) {
                            field.set(entity, save(var, entitiesBeingPersisted));
                        }
                    }
                }
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });

        final E persisted = persist(entity);
        entitiesBeingPersisted.remove(persisted);
        entitiesBeingPersisted.add(persisted);

        persistLater.forEach((field, var) -> {
            try {
                field.set(entity, save(var, entitiesBeingPersisted));
            } catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException(illegalAccessException);
            }
        });

        dependencies.forEach((rel, children) -> {

            Collection<Object> persistedChildren = children.stream()
                    .peek(child -> ReflectionTestUtils.setField(child, rel.getChild().getName(), persisted))
                    .map(child -> save(child, entitiesBeingPersisted))
                    .collect(Collectors.toList());

            if (children instanceof Set) {
                persistedChildren = new HashSet<>(persistedChildren);
            }

            ReflectionTestUtils.setField(persisted, rel.getParent().getName(), persistedChildren);
        });

        entitiesBeingPersisted.remove(entity);

        return persisted;
    }

    @Transactional
    public <E> E saveAndFlush(E entity) {

        final E save = save(entity, new ArrayList<>());
        entityManager.flush();
        return save;
    }

    @Transactional
    public void flushAndClear1LevelCache() {
        // Flush and clean 1st level cache
        entityManager.flush();
        entityManager.clear();
    }

    private boolean isEntity(Class<?> collectionType) {

        return collectionType.isAnnotationPresent(Entity.class);
    }

    private Relationship parseRelationship(Class<?> entityClass, Field field) throws NoSuchFieldException {

        final Relationship rel = new Relationship();

        rel.setParentType(entityClass);
        rel.setParent(field);

        // Get childType
        final ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
        final Class<?> childType = (Class<?>) stringListType.getActualTypeArguments()[0];

        rel.setChildType(childType);

        if (field.getAnnotation(OneToMany.class) != null) {
            rel.setChild(childType.getDeclaredField(field.getAnnotation(OneToMany.class).mappedBy()));
        } else if (field.getAnnotation(ManyToMany.class) != null) {
            if (!field.getAnnotation(ManyToMany.class).mappedBy().isBlank()) {
                rel.setChild(childType.getDeclaredField(field.getAnnotation(ManyToMany.class).mappedBy()));
            }
        }

        return rel;
    }

    private <E> E persist(E entity) {

        return entityManager.merge(entity);
    }

    @Data
    private static final class Relationship {

        private Class<?> parentType;

        private Field parent;

        private Class<?> childType;

        private Field child;
    }
}
