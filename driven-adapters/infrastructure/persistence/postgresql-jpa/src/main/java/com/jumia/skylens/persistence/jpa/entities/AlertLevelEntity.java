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
import java.time.LocalDateTime;

@Entity
@Table(name = "alert_levels")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertLevelEntity {

    @EmbeddedId
    private AlertLevelEntityId id;

    @Column(nullable = false)
    private BigDecimal warningValue;

    @Column(nullable = false)
    private BigDecimal criticalValue;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
