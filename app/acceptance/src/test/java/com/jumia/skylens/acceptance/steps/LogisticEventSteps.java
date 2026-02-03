package com.jumia.skylens.acceptance.steps;

import com.jumia.skylens.acceptance.context.EventTestContext;
import com.jumia.skylens.acceptance.utils.DatabaseQueries;
import com.jumia.skylens.acceptance.utils.LogisticEventPublisher;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogisticEventSteps {

    private final LogisticEventPublisher logisticEventPublisher;

    private final DatabaseQueries databaseQueries;

    private final EventTestContext eventTestContext;

    @Given("a first mile logistic event is received for partner {string}")
    public void firstMileLogisticEventIsReceived(String partnerSid) {

    }

    @Given("a last mile logistic event is received for partner {string}")
    public void lastMileLogisticEventIsReceived(String partnerSid) {

    }

    @And("the event has been processed")
    public void theEventHasBeenProcessed() {

    }
}
