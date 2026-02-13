package com.jumia.skylens.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "boundaries")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoundaryEntity {

    @EmbeddedId
    private BoundaryEntityId id;

    @Column(nullable = false)
    private BigDecimal warning;

    @Column(nullable = false)
    private BigDecimal critical;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false)
    private OffsetDateTime updatedAt;
}
