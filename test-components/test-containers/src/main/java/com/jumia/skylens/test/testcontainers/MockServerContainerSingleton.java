package com.jumia.skylens.test.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.mockserver.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public final class MockServerContainerSingleton extends MockServerContainer {

    private static final String IMAGE_VERSION = "mockserver/mockserver";

    private MockServerContainerSingleton() {

        super(DockerImageName.parse(IMAGE_VERSION));
    }

    public static MockServerContainerSingleton getInstance() {

        return ContainerHolder.CONTAINER;
    }

    private static final class ContainerHolder {

        private static final MockServerContainerSingleton CONTAINER = new MockServerContainerSingleton();
    }
}
