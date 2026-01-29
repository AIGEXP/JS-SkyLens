package com.jumia.skylens.acceptance.steps;

import com.jumia.skylens.acceptance.context.EventTestContext;
import com.jumia.skylens.acceptance.providers.LogisticEventDTO;
import com.jumia.skylens.acceptance.providers.LogisticEventProvider;
import com.jumia.skylens.acceptance.utils.DatabaseQueries;
import com.jumia.skylens.acceptance.utils.LogisticEventPublisher;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@RequiredArgsConstructor
public class LogisticEventSteps {

    private final LogisticEventPublisher logisticEventPublisher;

    private final DatabaseQueries databaseQueries;

    private final EventTestContext eventTestContext;

    @Given("a first mile logistic event is received for partner {string}")
    public void firstMileLogisticEventIsReceived(String partnerSid) {

        final LogisticEventDTO logisticEventDTO = LogisticEventProvider.firstMileEvent(UUID.fromString(partnerSid));

        logisticEventPublisher.publish(logisticEventDTO);

        eventTestContext.setLogisticEventDTO(logisticEventDTO);
    }

    @Given("a last mile logistic event is received for partner {string}")
    public void lastMileLogisticEventIsReceived(String partnerSid) {

        final LogisticEventDTO logisticEventDTO = LogisticEventProvider.lastMileEvent(UUID.fromString(partnerSid));

        logisticEventPublisher.publish(logisticEventDTO);

        eventTestContext.setLogisticEventDTO(logisticEventDTO);
    }

    @And("the event has been processed")
    public void theEventHasBeenProcessed() {

        final LogisticEventDTO logisticEventDTO = eventTestContext.getLogisticEventDTO();

        await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(databaseQueries.packageExists(logisticEventDTO.trackingNumber())).isTrue());
    }
}
