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
 * i18n keys
 */
@BaseName("constants")
@LocaleData(defaultCharset = "UTF8", value = @Locale("en"))
public enum Constants {

    /**
     * Constant for <code>project.issueManagement.system</code>
     */
    ISSUE_MANAGEMENT_SYSTEM,

    /**
     * Constant for <code>project.issueManagement.url</code>
     */
    ISSUE_MANAGEMENT_URL,

    /**
     * Constant for <code>project.url</code>
     */
    URL,

    /**
     * Constant for <code>project.scm.connection</code>
     */
    SCM_CONNECTION,

    /**
     * Constant for <code>project.scm.developerConnection</code>
     */
    SCM_DEVELOPER_CONNECTION,

    /**
     * Constant for <code>project.scm.tag</code>
     */
    SCM_TAG,

    /**
     * Constant for <code>project.scm.url</code>
     */
    SCM_URL,

    /**
     * Constant for <code>project.ciManagement.system</code>
     */
    CI_MANAGEMENT_SYSTEM,

    /**
     * Constant for <code>project.ciManagement.url</code>
     */
    CI_MANAGEMENT_URL,

    /**
     * Constant for <code>project.organization.url</code>
     */
    ORGANIZATION_URL,

    /**
     * Constant for <code>project.distributionManagement.site.id</code>
     */
    DISTRIBUTION_MANAGEMENT_SITE_ID,

    /**
     * Constant for <code>project.distributionManagement.site.url</code>
     */
    DISTRIBUTION_MANAGEMENT_SITE_URL;

}
