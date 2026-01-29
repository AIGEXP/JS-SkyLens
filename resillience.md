# 🔄 Resilient Retry Strategy for skylens Flow

## 📊 Current Flow Analysis

### Flow Path
```
RabbitMQ Message → LogisticEventsMessageListener → LogisticEventsMessageProcessor 
    → ProcessLogisticEventUseCase → StopEventProcessor
    → External HTTP Call (ZoneFetcher) → Database Save (StopDAO/PackageDAO)
```

### Current State
1. **RabbitMQ Consumer**: Manual acknowledgment mode
2. **Transaction Boundary**: `TransactionService.runInNewTransaction()` wraps the entire business logic
3. **External HTTP**: `ZoneFetcher` calls SkyPin API (zone service)
4. **Database**: PostgreSQL with JPA/Hibernate
5. **Error Handling**: Any exception → NACK → DLQ (Dead Letter Queue)

### Critical Issues
- **No retry mechanism** for transient failures (network timeouts, temporary service unavailability)
- **All-or-nothing approach**: Single failure sends message to DLQ immediately
- **Transaction rollback** on any error, even recoverable ones
- **External service failures** treated same as business logic errors

---

## 🎯 Recommended Multi-Layer Retry Strategy

### **Layer 1: HTTP Client-Level Retries** ⭐ **HIGHEST PRIORITY**
**Where**: External HTTP calls (ZoneFetcher)  
**Why**: Fast, automatic, transparent to business logic  
**Use**: Spring Retry or Resilience4j

#### Implementation Options:

**Option A: Spring Retry (Lightweight, Native)**
```java
@Retryable(
    retryFor = {HttpServerErrorException.class, ResourceAccessException.class},
    maxAttempts = 3,
    backoff = @Backoff(delay = 1000, multiplier = 2.0)
)
```

**Option B: Resilience4j (Production-Grade, Feature-Rich)** ⭐ **RECOMMENDED**
```java
@Retry(name = "zoneFetcher", fallbackMethod = "getZoneFallback")
@CircuitBreaker(name = "zoneFetcher")
@Bulkhead(name = "zoneFetcher")
```

**Benefits**:
- ✅ Handles transient network failures
- ✅ Configurable backoff strategies (exponential, random jitter)
- ✅ Circuit breaker prevents cascading failures
- ✅ Metrics and monitoring out-of-the-box
- ✅ Non-intrusive, declarative approach

---

### **Layer 2: Database Transaction Retries**
**Where**: Transaction boundaries  
**Why**: Handle deadlocks, optimistic locking failures  
**Use**: Spring `@Retryable` on transaction methods

#### Implementation:
```java
@Retryable(
    retryFor = {OptimisticLockException.class, PessimisticLockException.class, 
                CannotAcquireLockException.class},
    maxAttempts = 3,
    backoff = @Backoff(delay = 100, multiplier = 1.5, maxDelay = 500)
)
@Transactional(propagation = Propagation.REQUIRES_NEW)
```

**Benefits**:
- ✅ Handles database contention automatically
- ✅ Short delays appropriate for DB locks
- ✅ Prevents transaction timeout issues

---

### **Layer 3: RabbitMQ Consumer Retries**
**Where**: Message listener configuration  
**Why**: Last resort for message-level failures  
**Use**: Spring AMQP retry interceptor

#### Implementation Strategy:

**Option A: In-Memory Retry (Fast, Simpler)** ⭐ **RECOMMENDED FOR START**
```java
SimpleMessageListenerContainer container = ...
container.setAdviceChain(
    RetryInterceptorBuilder.stateless()
        .maxAttempts(3)
        .backOffOptions(1000, 2.0, 10000)
        .recoverer(new RejectAndDontRequeueRecoverer())
        .build()
);
```

**Option B: Delayed Retry Queue (More Resilient)**
```yaml
# Create delayed retry queues
skylens.q.retry-1 (TTL: 5s, routes back to main queue)
skylens.q.retry-2 (TTL: 30s, routes back to main queue)
skylens.q.retry-3 (TTL: 5min, routes back to main queue)
skylens.q.deadletter (final failure)
```

**Benefits**:
- ✅ Prevents message loss
- ✅ Configurable retry delays
- ✅ Preserves message order (with single consumer)
- ✅ Graceful degradation to DLQ

---

## 🏗️ Architecture Design Recommendations

### **1. Separate Transient from Permanent Failures**

```java
// Retriable exceptions (temporary issues)
- HttpServerErrorException (5xx)
- ResourceAccessException (network timeout)
- ConnectException
- SocketTimeoutException
- OptimisticLockException

// Non-retriable exceptions (permanent issues)
- HttpClientErrorException (4xx)
- ConstraintViolationException
- DiscardableEventException
- JsonParseException
```

### **2. Idempotency Guard**

Since retries can cause duplicate processing:

```java
@Override
public void process(Package pkg) {
    final String stopHash = calculateStopHash(pkg);
    
    // Idempotency check
    if (stopDAO.existsByStopHash(stopHash)) {
        log.info("Stop already processed, skipping: {}", stopHash);
        return; // ✅ Safe to retry
    }
    
    // ... continue processing
}
```

**Your code already has this!** ✅
- `FirstMileStopEventProcessor` checks `stopDAO.existsByStopHash()`
- `LastMileStopEventProcessor` uses `findByStopHash()` with `ifPresentOrElse()`

### **3. Transaction Boundary Optimization**

**Current**:
```java
transactionService.runInNewTransaction(() -> 
    processEvent(logisticEvent, stopEventProcessor)
);
```

**Problem**: External HTTP call inside transaction = long transaction time

**Recommended Refactor**:
```java
// Step 1: Fetch zone OUTSIDE transaction (retriable)
final Zone zone = zoneFetcherRetryable.get(country, region, city);

// Step 2: Database operations inside transaction (fast)
transactionService.runInNewTransaction(() -> {
    stopDAO.save(stop);
    packageDAO.save(pkg);
});
```

**Benefits**:
- ✅ Shorter transaction times
- ✅ HTTP retries don't lock database
- ✅ Better resource utilization
- ✅ Clearer separation of concerns

---

## 📦 Implementation Plan

### **Phase 1: HTTP Retries (Quick Win)** - 2-3 days

1. Add Resilience4j dependency
```kotlin
// gradle/libs.versions.toml
resilience4j-spring-boot = "2.2.0"

// build.gradle.kts
implementation("io.github.resilience4j:resilience4j-spring-boot4:2.2.0")
```

2. Configure retry policy
```yaml
resilience4j.retry:
  instances:
    zoneFetcher:
      maxAttempts: 3
      waitDuration: 1s
      exponentialBackoffMultiplier: 2
      retryExceptions:
        - org.springframework.web.client.HttpServerErrorException
        - org.springframework.web.client.ResourceAccessException
      ignoreExceptions:
        - org.springframework.web.client.HttpClientErrorException
```

3. Annotate ZoneFetcher
```java
@Retry(name = "zoneFetcher")
@CircuitBreaker(name = "zoneFetcher")
public Zone get(String city, String region, String country) {
    // existing implementation
}
```

### **Phase 2: Transaction Optimization** - 3-4 days

1. Extract HTTP calls from transactions
2. Add retry logic to transaction layer
3. Comprehensive testing

### **Phase 3: RabbitMQ Retries** - 2-3 days

1. Configure retry interceptor
2. Update listener container
3. Add metrics/monitoring
4. Test failure scenarios

### **Phase 4: Observability** - 2 days

1. Add retry metrics
2. Dashboards for retry counts, success rates
3. Alerting for high retry rates

---

## 🎛️ Configuration Strategy

### **Environment-Specific Tuning**

```yaml
# development
resilience4j.retry.instances.zoneFetcher:
  maxAttempts: 2
  waitDuration: 500ms

# production
resilience4j.retry.instances.zoneFetcher:
  maxAttempts: 5
  waitDuration: 2s
  exponentialBackoffMultiplier: 2.0
  randomizedWaitFactor: 0.5  # Add jitter
```

### **Feature Flags**

```yaml
features:
  retry:
    http-enabled: true
    database-enabled: true
    rabbitmq-enabled: false  # Enable after testing
```

---

## 🔍 Monitoring & Alerting

### **Key Metrics to Track**

1. **Retry Rates**:
   - `resilience4j_retry_calls{name="zoneFetcher", kind="successful_with_retry"}`
   - `resilience4j_retry_calls{name="zoneFetcher", kind="failed_with_retry"}`

2. **Circuit Breaker State**:
   - `resilience4j_circuitbreaker_state{name="zoneFetcher"}`

3. **Message Processing**:
   - Messages requeued count
   - DLQ message count
   - Average processing time

### **Alerts**

- Retry rate > 20% for 5 minutes
- Circuit breaker open
- DLQ depth > 100 messages
- HTTP call failure rate > 50%

---

## 🧪 Testing Strategy

### **1. Chaos Engineering Tests**

```java
@Test
void shouldRetryOnTransientNetworkFailure() {
    // Simulate 2 failures, then success
    when(skyPinClient.getZone(...))
        .thenThrow(new ResourceAccessException("Network timeout"))
        .thenThrow(new ResourceAccessException("Network timeout"))
        .thenReturn(zoneResponse);
    
    Zone zone = zoneFetcher.get(...);
    
    assertThat(zone).isNotNull();
    verify(skyPinClient, times(3)).getZone(...);
}
```

### **2. Load Testing**

- Simulate high message volume
- Test retry under load
- Verify transaction performance

### **3. Failure Scenarios**

- ✅ External service down
- ✅ Database connection pool exhausted
- ✅ Network partition
- ✅ Malformed messages
- ✅ Duplicate messages

---

## 🎯 Success Criteria

1. **Resilience**: 99.9% message processing success rate
2. **Performance**: < 2% increase in p99 latency
3. **Recovery**: Automatic recovery from transient failures within 30 seconds
4. **Observability**: Full visibility into retry patterns
5. **Zero Data Loss**: No messages lost due to transient failures

---

## 🚨 Risks & Mitigations

| Risk | Mitigation |
|------|------------|
| **Duplicate Processing** | ✅ Already have idempotency checks |
| **Increased Latency** | ✅ Use exponential backoff, circuit breakers |
| **Resource Exhaustion** | ✅ Add bulkheads, rate limiters |
| **Message Order** | ✅ Use single consumer or partition keys |
| **DLQ Overflow** | ✅ Set TTL on DLQ, monitoring alerts |

---

## 💡 Best Practices Applied

1. ✅ **Defense in Depth**: Multiple retry layers
2. ✅ **Fail Fast**: Circuit breakers prevent cascading failures
3. ✅ **Gradual Degradation**: DLQ as last resort
4. ✅ **Observability**: Metrics at each layer
5. ✅ **Configuration over Code**: Externalize retry policies
6. ✅ **Test Failures**: Chaos engineering approach

---

## 📚 Recommended Libraries

1. **Resilience4j** ⭐ (Modern, reactive, lightweight)
   - Retry, Circuit Breaker, Rate Limiter, Bulkhead
   - Spring Boot integration
   - Excellent metrics

2. **Spring Retry** (Simpler, native)
   - Good for basic retry needs
   - Less features than Resilience4j

3. **Awaitility** (For testing)
   - Already in your project ✅
   - Great for async testing

---

## 🎬 Next Steps

1. **Review this strategy** with the team
2. **Choose implementation approach** (I recommend starting with Layer 1: HTTP retries using Resilience4j)
3. **Create spike/POC** for Resilience4j integration
4. **Plan incremental rollout** (dev → staging → production)
5. **Set up monitoring** before enabling retries in production

Would you like me to start implementing any specific layer?
