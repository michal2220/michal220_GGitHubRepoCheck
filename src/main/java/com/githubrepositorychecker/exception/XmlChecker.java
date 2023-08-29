package com.githubrepositorychecker.exception;

import com.githubrepositorychecker.AppController;
import com.githubrepositorychecker.exception.HeaderNotAcceptableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class XmlChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    public void checkForXml() throws HeaderNotAcceptableException {
        LOGGER.warn("XML header was used, should be JSON ");
        throw new HeaderNotAcceptableException();
    }
}
