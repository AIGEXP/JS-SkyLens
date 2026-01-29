# MockServer
[Official page](https://www.mock-server.com/)

MockServer is a powerful tool for software development and testing, it allows to simulate APIs, services, or components that a system  
interacts with. Why to use it?:

## Isolate and Test Components Independently
   **Decouple Development**: When building an application that relies on external APIs or services, MockServer allows to create a mock version of these services. That allows to develop and test components without needing access to the actual external services.
   **Component Isolation**: Focus on testing individual components in isolation, ensuring that their behavior is correct even when the actual service is not available or fully developed.
## Facilitate Continuous Integration and Delivery
   **Automated Testing**: MockServer can be integrated into CI/CD pipelines to simulate different scenarios, including edge cases and failures, without needing real-time interaction with external systems. This ensures that application behaves correctly under various conditions.
   **Consistency in Tests**: By using mocks, we avoid flakiness in tests caused by external factors such as network latency, downtime, or changes in the external service.
## Handle Unavailable or Unreliable Services
   **Service Unavailability**: If the external service is down or not yet available, MockServer can simulate its behavior, allowing development and testing to proceed without interruption.
   **Simulate Latency and Failures**: It can be configured to simulate network latency, timeouts, and different failure scenarios, which 
   helps in testing the robustness of the application.
## Test Edge Cases and Error Handling
   **Controlled Testing Environment**: MockServer allows to simulate various scenarios, including those that are difficult to replicate with the actual service, such as specific error responses or unusual data formats.
   **Edge Case Simulation**: It can easily test how application handles unexpected responses, large payloads, or invalid data without needing to configure the actual service to behave that way.
## Save Costs
   **Avoiding External API Costs**: Some APIs charge per request or have usage limits. Using MockServer helps to avoid these costs by simulating the API during development and testing.
   **No Need for Multiple Environments**: Instead of setting up and maintaining multiple environments for testing (like staging or QA environments), we can use MockServer to simulate these environments.
## Enable Faster Development
   **Parallel Development**: Frontend and backend teams can work in parallel, with the frontend team using MockServer to simulate the backend until the actual API is ready.
   **Quick Feedback Loops**: Developers can get immediate feedback on their code without waiting for the external service to be available or respond, speeding up the development process.
## Support for Complex Scenarios
   **Dynamic Responses**: MockServer allows to configure dynamic responses based on the request, making it possible to simulate complex interactions and workflows.
   **Stateful Mocking**: It can maintain state across requests, enabling the simulation of more sophisticated scenarios where the response depends on previous interactions.
## Compliance and Security Testing
   **Sensitive Data Handling**: When dealing with APIs that return sensitive data, we can use MockServer to simulate these APIs without exposing actual data during testing.
   **Regulatory Compliance**: MockServer can help simulate compliant responses for various regulatory requirements, making it easier to test how your application handles such scenarios.

## MockServer UI
Useful [on local environment](http://localhost:8083/mockserver/dashboard) to visualize what is happening in each request.

## MockerServer Open API documentation
[Documentation](https://app.swaggerhub.com/apis/jamesdbloom/mock-server-openapi/5.15.x#/control/put_mockserver_clear)

## MockerServer Proxy
https://www.mock-server.com/proxy/getting_started.html

## Examples
How to define a custom expectation on demand:
```shell
curl -X PUT "http://localhost:1080/mockserver/expectation"  \
  -H 'Content-Type: application/json' 
  -d  '{
        "httpRequest": {
          "method": "GET",
          "path": "/hi"
        },
        "httpResponse": {
          "body": {
            "say": "hello world"
          }
        }
      }
    '
```
Response:
```json
[
  {
    "httpRequest": {
      "method": "GET",
      "path": "/hi"
    },
    "httpResponse": {
      "body": {
        "say": "hello world"
      }
    },
    "id": "dcc8e646-e4b3-4b1d-a271-637ede4d1175",
    "priority": 0,
    "timeToLive": {
      "unlimited": true
    },
    "times": {
      "unlimited": true
    }
  }
]
```

# Default expectations defined
Where are they defined?: [tools/docker/dev/mockserver/initializations](https://github.com/Jumia/Java-Boot/tree/main/tools/docker/dev/mockserver/initializations)
* `hmt-expectations.json` Replace this by any dependency on your application! This is just an example of a dependency on Java-Boot.

## Other expectations in the project
To run QA Acceptance test there are some expectations defined at [app/src/test-acceptance/resources/acl/default-expectations.json](https://github.com/Jumia/Java-Boot/tree/main/app/src/test-acceptance/resources/acl/default-expectations.json)
