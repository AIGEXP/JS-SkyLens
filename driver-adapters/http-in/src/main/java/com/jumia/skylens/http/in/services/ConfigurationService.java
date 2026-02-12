package com.jumia.skylens.http.in.services;

import com.jumia.skylens.domain.UpsertNetworkThresholdUseCase;
import com.jumia.skylens.domain.catalog.NetworkThreshold;
import com.jumia.skylens.http.in.converters.NetworkThresholdConverter;
import com.jumia.skylens.http.in.converters.ThresholdResponseConverter;
import com.jumia.skylens.http.in.model.ReportType;
import com.jumia.skylens.http.in.model.ThresholdRequest;
import com.jumia.skylens.http.in.model.ThresholdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigurationService {

    private final UpsertNetworkThresholdUseCase upsertNetworkThresholdUseCase;

    private final NetworkThresholdConverter networkThresholdConverter;

    private final ThresholdResponseConverter thresholdResponseConverter;

    public ThresholdResponse setThresholdTarget(final String country, final ReportType reportType, final ThresholdRequest request) {

        final NetworkThreshold networkThreshold = networkThresholdConverter.convert(country, reportType, request);

        final NetworkThreshold savedNetworkThreshold = upsertNetworkThresholdUseCase.run(networkThreshold);

        return thresholdResponseConverter.convert(savedNetworkThreshold);
    }
}
