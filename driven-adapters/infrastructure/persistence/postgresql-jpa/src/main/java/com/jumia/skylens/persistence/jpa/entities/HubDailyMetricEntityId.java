package com.jumia.skylens.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HubDailyMetricEntityId implements Serializable {

    @Column(nullable = false)
    private UUID serviceProviderSid;

    @Column(nullable = false)
    private UUID hubSid;

    @Column(nullable = false)
    private LocalDate day;

    @Column(nullable = false)
    private boolean prePaid;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    private MovementType movementType;

    public enum MovementType {
        DD,
        PUS
    }
}
