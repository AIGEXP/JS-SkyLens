Feature: Processing first mile logistic events

  Scenario: Create a stop when a first mile logistic event is received
    Given a first mile logistic event is received for partner "94629b09-8874-46d8-ad3d-78f50276adff"
    And the event has been processed
    When I list stops for partner "94629b09-8874-46d8-ad3d-78f50276adff"
    Then the stop created from the event is returned
