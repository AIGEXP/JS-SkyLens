package com.jumia.skylens.acceptance.steps;

import com.jumia.skylens.acceptance.context.EventTestContext;
import com.jumia.skylens.acceptance.utils.LastMileStopConsumer;
import com.jumia.skylens.acceptance.utils.PackagesRestClient;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@RequiredArgsConstructor
public class StopSteps {

    private final PackagesRestClient packagesRestClient;

    private final EventTestContext eventTestContext;

    private final LastMileStopConsumer lastMileStopConsumer;

    @When("I list stops for partner {string}")
    public void listStops(String partnerSid) {

        eventTestContext.setStopsResponseEntity(packagesRestClient.fetchStopPackages(UUID.fromString(partnerSid)));
    }

    @When("the last mile stops publishing job runs")
    public void theLastMileStopsPublishingJobRuns() {

        packagesRestClient.publishLastMileStops();
    }

    @Then("the stop created from the event is returned")
    public void theStopCreatedFromTheEventIsReturned() {

    }

    @Then("the stop created from the event is published")
    public void theStopCreatedFromTheEventIsPublished() {

        await()
                .atMost(10, SECONDS)
                .pollInterval(200, MILLISECONDS)
                .until(() -> lastMileStopConsumer.getLatch().getCount() == 0);
    }
}
