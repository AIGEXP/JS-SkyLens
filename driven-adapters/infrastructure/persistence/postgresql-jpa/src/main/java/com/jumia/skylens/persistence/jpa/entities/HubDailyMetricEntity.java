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
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "hub_daily_metrics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubDailyMetricEntity {

    @EmbeddedId
    private HubDailyMetricEntityId id;

    @Column(nullable = false)
    private int packagesDelivered;

    @Column(nullable = false)
    private int packagesClosed;

    @Column(nullable = false)
    private int packagesReceived;

    @Column(nullable = false)
    private int packagesLostAtHub;

    @Column(nullable = false)
    private int packagesNoAttempts;

    @Column(nullable = false)
    private int packagesNoAttemptsOneDay;

    @Column(nullable = false)
    private int packagesNoAttemptsTwoDays;

    @Column(nullable = false)
    private int packagesNoAttemptsThreeDays;

    @Column(nullable = false)
    private int packagesNoAttemptsFourDays;

    @Column(nullable = false)
    private int packagesNoAttemptsOverFourDays;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime createdAt;
}
