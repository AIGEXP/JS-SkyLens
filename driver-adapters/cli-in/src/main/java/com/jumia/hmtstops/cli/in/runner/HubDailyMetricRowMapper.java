package com.jumia.hmtstops.cli.in.runner;

import com.jumia.skylens.domain.catalog.HubDailyMetric;
import com.jumia.skylens.domain.catalog.MovementType;
import com.jumia.skylens.domain.catalog.PaymentType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class HubDailyMetricRowMapper implements RowMapper<HubDailyMetric> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public HubDailyMetric mapRow(final ResultSet rs, final int rowNum) throws SQLException {

        final String movementType = rs.getString("Movement_Type");

        return HubDailyMetric.builder()
                .hubSid(UUID.fromString(rs.getString("Hub_SID")))
                .serviceProviderSid(UUID.fromString(rs.getString("Service_Provider_SID")))
                .day(LocalDate.parse(rs.getString("Day"), dateTimeFormatter))
                .paymentType(rs.getBoolean("Pre_paid") ? PaymentType.PRE : PaymentType.POST)
                .movementType("DD".equals(movementType) ? MovementType.DOOR : MovementType.PUS)
                .packagesDelivered(getIntOrZero(rs, "Nr_packages_delivered"))
                .packagesClosed(getIntOrZero(rs, "Nr_packages_closed"))
                .packagesReceived(getIntOrZero(rs, "Nr_packages_received"))
                .packagesLostAtHub(getIntOrZero(rs, "Nr_packages_lost_at_Hub"))
                .packagesNoAttemptsOneDay(getIntOrZero(rs, "Nr_packages_no_attempt_1D"))
                .packagesNoAttemptsTwoDays(getIntOrZero(rs, "Nr_packages_no_attempt_2D"))
                .packagesNoAttemptsThreeDays(getIntOrZero(rs, "Nr_packages_no_attempt_3D"))
                .packagesNoAttemptsOverThreeDays(getIntOrZero(rs, "Nr_packages_no_attempt_4D")
                                                         + getIntOrZero(rs, "Nr_packages_no_attempt_+4D"))
                .build();
    }

    private Integer getIntOrZero(final ResultSet rs, final String column) throws SQLException {

        final int value = rs.getInt(column);
        return rs.wasNull() ? 0 : value;
    }
}
