/*
 * Copyright © 2015 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.maven;

import ch.qos.cal10n.BaseName;
import ch.qos.cal10n.Locale;
import ch.qos.cal10n.LocaleData;

/**
 * i18n keys for error messages
 */
@BaseName("errors")
@LocaleData(defaultCharset = "UTF8", value = @Locale("en"))
public enum ErrorMessages {

    /**
     * Error message signals that no organization name was configured.
     */
    NO_ORGANIZATION_NAME

}
