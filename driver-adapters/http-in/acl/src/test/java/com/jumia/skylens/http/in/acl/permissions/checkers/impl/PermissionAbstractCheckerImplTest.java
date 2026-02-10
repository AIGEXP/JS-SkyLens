package com.jumia.skylens.http.in.acl.permissions.checkers.impl;

import com.jumia.skylens.http.in.acl.AclFaker;
import com.jumia.skylens.http.in.acl.authentication.AuthToken;
import com.jumia.skylens.http.in.acl.exceptions.AclInternalErrorException;
import com.jumia.skylens.http.in.acl.exceptions.ForbiddenException;
import com.jumia.skylens.http.in.acl.permissions.AclTargetPathBuilder;
import com.jumia.skylens.http.in.acl.permissions.ApplicationPermission;
import com.jumia.skylens.http.in.acl.permissions.PartnerPermission;
import com.jumia.skylens.http.in.acl.permissions.Permission;
import com.jumia.skylens.http.in.acl.resources.ApplicationResource;
import com.jumia.skylens.http.in.acl.resources.PartnerResource;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.jumia.services.acl.lib.AclConnectApiClient;
import pt.jumia.services.acl.lib.AclErrorException;
import pt.jumia.services.acl.lib.RequestUser;
import pt.jumia.services.acl.lib.client.authorization.HierarchicalAuthorizationClient;
import pt.jumia.services.acl.lib.client.authorization.Path;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PermissionAbstractCheckerImplTest {

    @Mock
    private AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient;

    @Mock
    private AclTargetPathBuilder aclTargetPathBuilder;

    @Mock
    private AuthToken authToken;

    @InjectMocks
    private TestPermissionChecker subject;

    @Test
    void checkAnyPermission_whenAclApiFails_throwAclInternalError() {

        // Given
        final RequestUser requestUser = subject.getRequestUser();
        final Permission adminPermission = ApplicationPermission.of(ApplicationResource.ADMIN);
        final Permission partnerPermission = PartnerPermission.of(UUID.randomUUID(), PartnerResource.DASHBOARD_READ);
        final HierarchicalAuthorizationClient authorizationClient = mock(HierarchicalAuthorizationClient.class);
        final Path adminPermissionPath = Path.fromTargetsHierarchyAsString(adminPermission.target());
        final Path partnerPermissionPath = Path.fromTargetsHierarchyAsString(partnerPermission.target());

        doReturn(authorizationClient)
                .when(aclConnectApiClient)
                .authorization();
        Mockito.lenient().doReturn(partnerPermissionPath)
                .when(aclTargetPathBuilder)
                .buildPath(partnerPermission);
        Mockito.lenient().doReturn(adminPermissionPath)
                .when(aclTargetPathBuilder)
                .buildPath(adminPermission);

        doThrow(AclErrorException.build(0))
                .when(authorizationClient)
                .hasPermission(eq(requestUser),
                               eq(requestUser.getUsername()),
                               argThat(a -> a.equals(adminPermission.resource()) || a.equals(partnerPermission.resource())),
                               argThat(a -> a.equals(adminPermissionPath) || a.equals(partnerPermissionPath)));

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.checkAnyPermission(authToken,
                                                                                           adminPermission,
                                                                                           partnerPermission);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(AclInternalErrorException.class);
    }

    @Test
    void checkAnyPermission_whenDoNotHavePermission_throwForbiddenException() {

        // Given
        final Permission adminPermission = ApplicationPermission.of(ApplicationResource.ADMIN);
        final Permission partnerPermission = PartnerPermission.of(UUID.randomUUID(), PartnerResource.DASHBOARD_READ);
        final HierarchicalAuthorizationClient authorizationClient = mock(HierarchicalAuthorizationClient.class);

        doReturn(authorizationClient)
                .when(aclConnectApiClient)
                .authorization();

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.checkAnyPermission(authToken,
                                                                                           adminPermission,
                                                                                           partnerPermission);

        // Then
        assertThatThrownBy(callable)
                .isInstanceOf(ForbiddenException.class);
    }

    @Test
    void checkAnyPermission_whenHasPermission_doNotThrowException() {

        // Given
        final RequestUser requestUser = subject.getRequestUser();
        final Permission adminPermission = ApplicationPermission.of(ApplicationResource.ADMIN);
        final Permission partnerPermission = PartnerPermission.of(UUID.randomUUID(), PartnerResource.DASHBOARD_READ);
        final HierarchicalAuthorizationClient authorizationClient = mock(HierarchicalAuthorizationClient.class);
        final Path partnerPermissionPath = Path.fromTargetsHierarchyAsString(partnerPermission.target());
        final Path adminPermissionPath = Path.fromTargetsHierarchyAsString(adminPermission.target());

        doReturn(authorizationClient)
                .when(aclConnectApiClient)
                .authorization();
        Mockito.lenient().doReturn(partnerPermissionPath)
                .when(aclTargetPathBuilder)
                .buildPath(partnerPermission);
        Mockito.lenient().doReturn(adminPermissionPath)
                .when(aclTargetPathBuilder)
                .buildPath(adminPermission);

        doReturn(true)
                .when(authorizationClient)
                .hasPermission(eq(requestUser),
                               eq(requestUser.getUsername()),
                               argThat(a -> a.equals(adminPermission.resource()) || a.equals(partnerPermission.resource())),
                               argThat(a -> a.equals(adminPermissionPath) || a.equals(partnerPermissionPath)));

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.checkAnyPermission(authToken,
                                                                                           adminPermission,
                                                                                           partnerPermission);

        // Then
        assertThatCode(callable)
                .doesNotThrowAnyException();
    }

    @Test
    void checkPermission_whenHasPermission_doNotThrowException() {

        // Given
        final RequestUser requestUser = subject.getRequestUser();
        final Permission partnerPermission = PartnerPermission.of(UUID.randomUUID(), PartnerResource.DASHBOARD_READ);
        final HierarchicalAuthorizationClient authorizationClient = mock(HierarchicalAuthorizationClient.class);
        final Path path = Path.fromTargetsHierarchyAsString(partnerPermission.target());

        doReturn(authorizationClient)
                .when(aclConnectApiClient)
                .authorization();
        doReturn(path)
                .when(aclTargetPathBuilder)
                .buildPath(any());
        doReturn(true)
                .when(authorizationClient)
                .hasPermission(requestUser, requestUser.getUsername(), partnerPermission.resource(), path);

        // When
        final ThrowableAssert.ThrowingCallable callable = () -> subject.checkPermission(authToken, partnerPermission);

        // Then
        assertThatCode(callable)
                .doesNotThrowAnyException();
    }

    @Test
    void hasPermission_whenDoesNotHavePermission_returnFalse() {

        // Given
        final RequestUser requestUser = subject.getRequestUser();
        final Permission partnerPermission = PartnerPermission.of(UUID.randomUUID(), PartnerResource.DASHBOARD_READ);
        final HierarchicalAuthorizationClient authorizationClient = mock(HierarchicalAuthorizationClient.class);
        final Path path = Path.fromTargetsHierarchyAsString(partnerPermission.target());

        doReturn(authorizationClient)
                .when(aclConnectApiClient)
                .authorization();
        doReturn(path)
                .when(aclTargetPathBuilder)
                .buildPath(any());
        doReturn(false)
                .when(authorizationClient)
                .hasPermission(requestUser, requestUser.getUsername(), partnerPermission.resource(), path);

        // When
        final boolean result = subject.hasPermission(authToken, partnerPermission);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void hasPermission_whenHasPermission_returnTrue() {

        // Given
        final RequestUser requestUser = subject.getRequestUser();
        final Permission partnerPermission = PartnerPermission.of(UUID.randomUUID(), PartnerResource.DASHBOARD_READ);
        final HierarchicalAuthorizationClient authorizationClient = mock(HierarchicalAuthorizationClient.class);
        final Path path = Path.fromTargetsHierarchyAsString(partnerPermission.target());

        doReturn(authorizationClient)
                .when(aclConnectApiClient)
                .authorization();
        doReturn(path)
                .when(aclTargetPathBuilder)
                .buildPath(any());
        doReturn(true)
                .when(authorizationClient)
                .hasPermission(requestUser, requestUser.getUsername(), partnerPermission.resource(), path);

        // When
        final boolean result = subject.hasPermission(authToken, partnerPermission);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void isAdmin_whenDoesNotHaveApplicationPermissionOfAdmin_returnFalse() {

        // Given
        final RequestUser requestUser = subject.getRequestUser();
        final Permission adminPermission = ApplicationPermission.of(ApplicationResource.ADMIN);
        final HierarchicalAuthorizationClient authorizationClient = mock(HierarchicalAuthorizationClient.class);
        final Path path = Path.fromTargetsHierarchyAsString(adminPermission.target());

        doReturn(authorizationClient)
                .when(aclConnectApiClient)
                .authorization();
        doReturn(path)
                .when(aclTargetPathBuilder)
                .buildPath(any());
        doReturn(false)
                .when(authorizationClient)
                .hasPermission(requestUser, requestUser.getUsername(), adminPermission.resource(), path);

        // When
        final boolean result = subject.isAdmin(authToken);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void isAdmin_whenHasApplicationPermissionOfAdmin_returnTrue() {

        // Given
        final RequestUser requestUser = subject.getRequestUser();
        final Permission adminPermission = ApplicationPermission.of(ApplicationResource.ADMIN);
        final HierarchicalAuthorizationClient authorizationClient = mock(HierarchicalAuthorizationClient.class);
        final Path path = Path.fromTargetsHierarchyAsString(adminPermission.target());

        doReturn(authorizationClient)
                .when(aclConnectApiClient)
                .authorization();
        doReturn(path)
                .when(aclTargetPathBuilder)
                .buildPath(any());
        doReturn(true)
                .when(authorizationClient)
                .hasPermission(requestUser, requestUser.getUsername(), adminPermission.resource(), path);

        // When
        final boolean result = subject.isAdmin(authToken);

        // Then
        assertThat(result).isTrue();
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    private static class TestPermissionChecker extends PermissionAbstractCheckerImpl {

        RequestUser requestUser = new AclFaker().requestUser().build();

        TestPermissionChecker(AclTargetPathBuilder aclTargetPathBuilder,
                              AclConnectApiClient<HierarchicalAuthorizationClient> aclConnectApiClient) {

            super(List.of(aclConnectApiClient), aclTargetPathBuilder);
        }

        @Override
        RequestUser createRequestUser(AuthToken authorization) {

            return requestUser;
        }
    }
}
