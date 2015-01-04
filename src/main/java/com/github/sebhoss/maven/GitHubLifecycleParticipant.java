/*
 * Copyright © 2015 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.maven;

import java.util.Locale;

import ch.qos.cal10n.IMessageConveyor;
import ch.qos.cal10n.MessageConveyor;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.CiManagement;
import org.apache.maven.model.DistributionManagement;
import org.apache.maven.model.IssueManagement;
import org.apache.maven.model.Organization;
import org.apache.maven.model.Scm;
import org.apache.maven.model.Site;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;

/**
 * Sets common settings for Maven based projects found on GitHub
 */
@Component(role = AbstractMavenLifecycleParticipant.class, hint = "GitHub")
public class GitHubLifecycleParticipant extends AbstractMavenLifecycleParticipant {

    private static final IMessageConveyor messages = new MessageConveyor(Locale.ENGLISH);

    private MavenProject                  project;

    @Override
    public void afterProjectsRead(final MavenSession session) throws MavenExecutionException {
        project = session.getCurrentProject();

        updateProject();
        updateOrganization();
        updateIssueManagement();
        updateScm();
        updateCiManagement();
        updateDistributionManagement();
    }

    private void updateProject() {
        if (project.getUrl() == null || project.getUrl().isEmpty()) {
            project.setUrl(getProperty(Constants.URL, organizationName(), artifactId()));
        }
    }

    private void updateOrganization() {
        final Organization organization = project.getOrganization();
        if (organization.getUrl() == null || organization.getUrl().isEmpty()) {
            organization.setUrl(getProperty(Constants.ORGANIZATION_URL, organization.getName()));
        }
    }

    private void updateIssueManagement() {
        IssueManagement issueManagement = project.getIssueManagement();

        if (issueManagement == null) {
            issueManagement = new IssueManagement();
            issueManagement.setSystem(getProperty(Constants.ISSUE_MANAGEMENT_SYSTEM));
            issueManagement.setUrl(getProperty(Constants.ISSUE_MANAGEMENT_URL, organizationName(), artifactId()));
            project.setIssueManagement(issueManagement);
        }
    }

    private void updateScm() {
        Scm scm = project.getScm();

        if (scm == null) {
            scm = new Scm();
            scm.setConnection(getProperty(Constants.SCM_CONNECTION, organizationName(), artifactId()));
            scm.setDeveloperConnection(getProperty(Constants.SCM_DEVELOPER_CONNECTION, organizationName(), artifactId()));
            scm.setTag(getProperty(Constants.SCM_TAG));
            scm.setUrl(getProperty(Constants.SCM_URL, organizationName(), artifactId()));
            project.setScm(scm);
        }
    }

    private void updateCiManagement() {
        CiManagement ciManagement = project.getCiManagement();

        if (ciManagement == null) {
            ciManagement = new CiManagement();
            ciManagement.setSystem(getProperty(Constants.CI_MANAGEMENT_SYSTEM));
            ciManagement.setUrl(getProperty(Constants.CI_MANAGEMENT_URL, organizationName(), artifactId()));
            project.setCiManagement(ciManagement);
        }
    }

    private void updateDistributionManagement() {
        DistributionManagement distributionManagement = project.getDistributionManagement();
        if (distributionManagement == null) {
            distributionManagement = new DistributionManagement();
        }

        Site site = distributionManagement.getSite();
        if (site == null) {
            site = new Site();
            site.setId(getProperty(Constants.DISTRIBUTION_MANAGEMENT_SITE_ID));
            site.setUrl(getProperty(Constants.DISTRIBUTION_MANAGEMENT_SITE_URL));
            distributionManagement.setSite(site);
        }

        project.setDistributionManagement(distributionManagement);
    }

    private String organizationName() {
        return project.getOrganization().getName();
    }

    private String artifactId() {
        return project.getArtifactId();
    }

    private static String getProperty(final Constants key, final Object... args) {
        return messages.getMessage(key, args);
    }

}
