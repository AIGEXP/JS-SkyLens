package com.jumia.skylens.domain.catalog;

import com.jumia.skylens.domain.catalog.enums.LastMileDeliveredBy;
import com.jumia.skylens.domain.catalog.enums.PaymentMethodType;
import com.jumia.skylens.domain.catalog.enums.Size;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Builder;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Builder
@SuppressFBWarnings(value = {"EI_EXPOSE_REP"}, justification = "I prefer to suppress these FindBugs warnings")
public record LogisticEvent(Long packageId,
                            String trackingNumber,
                            long currentSubRequestId,
                            BusinessPartner currentBusinessPartner,
                            Map<Long, SubRequest> subRequests,
                            String network,
                            Instant eventDate,
                            Size size) {

    public SubRequest currentSubRequest() {

        return subRequests.get(currentSubRequestId);
    }

    public Optional<Attempt> successfulAndConfirmedAttempt() {

        return currentSubRequest().attempts().values().stream()
                .filter(attempt -> attempt.successful() && attempt.isConfirmed())
                .findFirst();
    }

    public boolean isCurrentSubRequestComplete() {

        return currentSubRequest().isComplete();
    }

    public boolean currentSubRequestHasSuccessfulConfirmedAttempt() {

        return currentSubRequest().attempts().values().stream().anyMatch(attempt -> attempt.successful() && attempt.isConfirmed());
    }

    public boolean isServiceProviderThirdParty() {

        return currentBusinessPartner().serviceProvider().isThirdParty();
    }

    public boolean isDriverThirdParty() {

        return successfulAndConfirmedAttempt()
                .map(Attempt::driver)
                .map(Driver::serviceProvider)
                .map(ServiceProvider::isThirdParty)
                .orElse(false);
    }

    public LastMileDeliveredBy lastMileDeliveredBy() {

        if (isServiceProviderThirdParty()) {

            return LastMileDeliveredBy.FIRST_LAST_MILE;
        }

        if (isDriverThirdParty()) {

            return LastMileDeliveredBy.DA_CONTRACTOR;
        }

        return null;
    }

    @lombok.Builder
    public record SubRequest(SubRequestStatus status, Service service, Map<Long, Attempt> attempts, Invoice invoice, Address address) {

        public boolean isComplete() {

            return this.status == SubRequestStatus.COMPLETE;
        }
    }

    public record Service(String code) {

    }

    @lombok.Builder
    public record Attempt(AttemptStatus status, boolean successful, Driver driver) {

        public boolean isConfirmed() {

            return this.status == AttemptStatus.CONFIRMED;
        }
    }

    @lombok.Builder
    public record Driver(String sid, String name, ServiceProvider serviceProvider) {

    }

    public record Invoice(PaymentMethod paymentMethod) {

    }

    @lombok.Builder
    public record Address(long id, String email, String type, String region, String city) {

    }

    public record PaymentMethod(UUID sid, String name, PaymentMethodType type) {

    }

    @lombok.Builder
    public record BusinessPartner(UUID sid, String name, ServiceProvider serviceProvider, String region, String city) {

    }

    @lombok.Builder
    public record ServiceProvider(UUID sid, ServiceProviderType type) {

        public boolean isThirdParty() {

            return this.type == ServiceProviderType.THIRD_PARTY;
        }
    }

    public enum SubRequestStatus {
        COMPLETE,
        UNKNOWN
    }

    public enum AttemptStatus {
        CONFIRMED,
        UNKNOWN
    }

    public enum ServiceProviderType {
        THIRD_PARTY,
        UNKNOWN
    }
}
