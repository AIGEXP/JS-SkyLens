Feature: Processing last mile logistic events

  Scenario: Create a stop when a last mile logistic event is received
    Given a last mile logistic event is received for partner "94629b09-8874-46d8-ad3d-78f50276adff"
    And the event has been processed
    When the last mile stops publishing job runs
    Then the stop created from the event is published
    When I list stops for partner "94629b09-8874-46d8-ad3d-78f50276adff"
    Then the stop created from the event is returned
