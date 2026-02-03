package com.jumia.skylens.persistence.jpa.repositories.custom.impl;

import com.jumia.skylens.domain.catalog.Granularity;
import com.jumia.skylens.domain.catalog.PackageNoAttemptsStatistics;
import com.jumia.skylens.domain.catalog.PackageStatistics;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntity;
import com.jumia.skylens.persistence.jpa.entities.HubDailyMetricEntityId;
import com.jumia.skylens.persistence.jpa.entities.QHubDailyMetricEntity;
import com.jumia.skylens.persistence.jpa.repositories.custom.HubDailyMetricCustomRepository;
import com.jumia.skylens.persistence.jpa.repositories.custom.utils.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class HubDailyMetricCustomRepositoryImpl extends QuerydslRepositorySupport implements HubDailyMetricCustomRepository {

    public HubDailyMetricCustomRepositoryImpl() {

        super(HubDailyMetricEntity.class);
    }

    @Override
    public List<PackageStatistics> findByFilters(final UUID serviceProviderSid,
                                                 final UUID hubSid,
                                                 final LocalDate startDate,
                                                 final HubDailyMetricEntityId.PaymentType paymentType,
                                                 final HubDailyMetricEntityId.MovementType movementType,
                                                 final Granularity granularity) {

        final QHubDailyMetricEntity qHubDailyMetricEntity = QHubDailyMetricEntity.hubDailyMetricEntity;

        final Predicate predicate = new OptionalBooleanBuilder()
                .optionalAnd(serviceProviderSid, qHubDailyMetricEntity.id.serviceProviderSid::eq)
                .optionalAnd(hubSid, qHubDailyMetricEntity.id.hubSid::eq)
                .optionalAnd(startDate, qHubDailyMetricEntity.id.day::goe)
                .optionalAnd(paymentType, qHubDailyMetricEntity.id.paymentType::eq)
                .optionalAnd(movementType, qHubDailyMetricEntity.id.movementType::eq);

        final SimpleTemplate<LocalDate> date = Expressions.template(LocalDate.class, "date_trunc({0}, {1})",
                                                                    getDateTruncFormat(granularity),
                                                                    qHubDailyMetricEntity.id.day);

        return from(qHubDailyMetricEntity)
                .select(Projections.constructor(PackageStatistics.class,
                                                date,
                                                qHubDailyMetricEntity.packagesDelivered.sum(),
                                                qHubDailyMetricEntity.packagesClosed.sum(),
                                                qHubDailyMetricEntity.packagesReceived.sum(),
                                                qHubDailyMetricEntity.packagesLostAtHub.sum()))
                .where(predicate)
                .groupBy(qHubDailyMetricEntity.id.day)
                .orderBy(qHubDailyMetricEntity.id.day.desc())
                .fetch();
    }

    @Override
    public PackageNoAttemptsStatistics findCurrentNoAttemptsStatistics(final UUID serviceProviderSid,
                                                                       final UUID hubSid,
                                                                       final HubDailyMetricEntityId.PaymentType paymentType,
                                                                       final HubDailyMetricEntityId.MovementType movementType) {

        final QHubDailyMetricEntity qHubDailyMetricEntity = QHubDailyMetricEntity.hubDailyMetricEntity;
        final QHubDailyMetricEntity qHubDailyMetricEntitySubQuery = new QHubDailyMetricEntity("QHubDailyMetricEntitySubQuery");

        final Predicate predicate = new OptionalBooleanBuilder()
                .optionalAnd(serviceProviderSid, qHubDailyMetricEntity.id.serviceProviderSid::eq)
                .optionalAnd(hubSid, qHubDailyMetricEntity.id.hubSid::eq)
                .optionalAnd(paymentType, qHubDailyMetricEntity.id.paymentType::eq)
                .optionalAnd(movementType, qHubDailyMetricEntity.id.movementType::eq);

        final JPQLQuery<LocalDate> maxDaySubquery = JPAExpressions.select(qHubDailyMetricEntitySubQuery.id.day.max())
                .from(qHubDailyMetricEntitySubQuery)
                .where(predicate);

        return from(qHubDailyMetricEntity)
                .select(Projections.constructor(PackageNoAttemptsStatistics.class,
                                                qHubDailyMetricEntity.packagesNoAttemptsOneDay.sum(),
                                                qHubDailyMetricEntity.packagesNoAttemptsTwoDays.sum(),
                                                qHubDailyMetricEntity.packagesNoAttemptsThreeDays.sum(),
                                                qHubDailyMetricEntity.packagesNoAttemptsOverThreeDays.sum()))
                .where(qHubDailyMetricEntity.id.day.eq(maxDaySubquery).and(predicate))
                .fetchFirst();
    }

    private String getDateTruncFormat(final Granularity granularity) {

        return switch (granularity) {
            case DAILY -> "day";
            case WEEKLY -> "week";
            case MONTHLY -> "month";
        };
    }
}

