package com.jumia.skylens.http.in.configurations;

import lombok.extern.slf4j.Slf4j;
import pt.jumia.services.acl.lib.logging.Logger;

@Slf4j
public class AclLogger implements Logger {

    @Override
    public void debug(String debugMessage) {

        log.debug(debugMessage);
    }

    @Override
    public void info(String infoMessage) {

        log.info(infoMessage);
    }

    @Override
    public void warning(String warningMessage) {

        log.warn(warningMessage);
    }

    @Override
    public void error(String errorMessage) {

        log.error(errorMessage);
    }
}
