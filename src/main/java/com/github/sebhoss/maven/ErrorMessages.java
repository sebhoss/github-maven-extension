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
