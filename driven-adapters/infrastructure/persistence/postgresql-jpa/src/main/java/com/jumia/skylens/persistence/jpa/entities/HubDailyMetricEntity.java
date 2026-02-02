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

    private String country;

    @Column(name = "is_3pl")
    private boolean is3PL;

    private Integer packagesDelivered;

    private Integer packagesClosed;

    private Integer packagesReceived;

    private Integer packagesLostAtHub;

    private Integer packagesNoAttempts;

    private Integer packagesNoAttemptsOneDay;

    private Integer packagesNoAttemptsTwoDays;

    private Integer packagesNoAttemptsThreeDays;

    private Integer packagesNoAttemptsFourDays;

    private Integer packagesNoAttemptsOverFourDays;

    @CreationTimestamp
    private OffsetDateTime createdAt;
}
