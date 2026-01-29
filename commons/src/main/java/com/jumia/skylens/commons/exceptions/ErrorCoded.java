package com.jumia.skylens.commons.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public interface ErrorCoded {

    Integer getCode();

    String getName();

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class BaseCodes {

        public static final int DOMAIN = 0;

        public static final int EXTERNAL_SERVICES = 1000;

        public static final int ACL = 2000;

        public static final int REST = 3000;

        public static final int PERSISTENCE = 4000;

        public static final int COMMONS = 5000;

        public static final int APP = 6000;

        public static final int TASK_VALIDATION = 7000;

        public static final int EXTERNAL_HTTP_SERVICES = 8000;

        public static final int SPRING_EVENTS = 11000;
    }
}
