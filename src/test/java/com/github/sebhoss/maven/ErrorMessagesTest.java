/*
 * Copyright © 2015 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.maven;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for ErrorMessages
 */
public class ErrorMessagesTest {

    /**
     * Checks that an instance of ErrorMessages can be created.
     */
    @Test
    @SuppressWarnings("static-method")
    public void shouldCreateErrorMessage() {
        // when
        final ErrorMessages errorMessage = ErrorMessages.valueOf("NO_ORGANIZATION_NAME"); //$NON-NLS-1$

        // then
        Assert.assertNotNull(errorMessage);
    }

}
