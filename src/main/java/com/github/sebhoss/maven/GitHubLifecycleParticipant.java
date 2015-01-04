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
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.annotations.Component;

/**
 * Sets common settings for Maven based projects found on GitHub
 */
@Component(role = AbstractMavenLifecycleParticipant.class, hint = "GitHub")
public class GitHubLifecycleParticipant extends AbstractMavenLifecycleParticipant {

    private static final IMessageConveyor messages = new MessageConveyor(Locale.ENGLISH);

    private MavenProject                  project;

    @Parameter(property = "github.organization.name", defaultValue = "${project.organization.name}")
    private String                        organizationName;

    @Override
    public void afterProjectsRead(final MavenSession session) throws MavenExecutionException {
        project = session.getCurrentProject();

        updateOrganization();
        updateProject();
        updateIssueManagement();
        updateScm();
        updateCiManagement();
        updateDistributionManagement();
    }

    private void updateProject() {
        if (project.getUrl() == null || project.getUrl().isEmpty()) {
            project.setUrl(getProperty(GitHubMessages.URL, organizationName(), artifactId()));
        }
    }

    private void updateOrganization() {
        Organization organization = project.getOrganization();
        if (organization == null) {
            organization = new Organization();
        }
        if (organization.getUrl() == null || organization.getUrl().isEmpty()) {
            organization.setUrl(getProperty(GitHubMessages.ORGANIZATION_URL, organizationName()));
        }
        project.setOrganization(organization);
    }

    private void updateIssueManagement() {
        IssueManagement issueManagement = project.getIssueManagement();

        if (issueManagement == null) {
            issueManagement = new IssueManagement();
            issueManagement.setSystem(getProperty(GitHubMessages.ISSUE_MANAGEMENT_SYSTEM));
            issueManagement.setUrl(getProperty(GitHubMessages.ISSUE_MANAGEMENT_URL, organizationName(), artifactId()));
            project.setIssueManagement(issueManagement);
        }
    }

    private void updateScm() {
        Scm scm = project.getScm();

        if (scm == null) {
            scm = new Scm();
            scm.setConnection(getProperty(GitHubMessages.SCM_CONNECTION, organizationName(), artifactId()));
            scm.setDeveloperConnection(getProperty(GitHubMessages.SCM_DEVELOPER_CONNECTION, organizationName(),
                    artifactId()));
            scm.setTag(getProperty(GitHubMessages.SCM_TAG));
            scm.setUrl(getProperty(GitHubMessages.SCM_URL, organizationName(), artifactId()));
            project.setScm(scm);
        }
    }

    private void updateCiManagement() {
        CiManagement ciManagement = project.getCiManagement();

        if (ciManagement == null) {
            ciManagement = new CiManagement();
            ciManagement.setSystem(getProperty(GitHubMessages.CI_MANAGEMENT_SYSTEM));
            ciManagement.setUrl(getProperty(GitHubMessages.CI_MANAGEMENT_URL, organizationName(), artifactId()));
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
            site.setId(getProperty(GitHubMessages.DISTRIBUTION_MANAGEMENT_SITE_ID));
            site.setUrl(getProperty(GitHubMessages.DISTRIBUTION_MANAGEMENT_SITE_URL));
            distributionManagement.setSite(site);
        }

        project.setDistributionManagement(distributionManagement);
    }

    private String organizationName() {
        if (organizationName != null) {
            return organizationName;
        }

        throw new NullPointerException(getProperty(ErrorMessages.NO_ORGANIZATION_NAME));
    }

    private String artifactId() {
        return project.getArtifactId();
    }

    private static <E extends Enum<?>> String getProperty(final E key, final Object... args) {
        return messages.getMessage(key, args);
    }

    /**
     * @return The configuration GitHub organization name
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * @param organizationName
     *            The GitHub organization name to use
     */
    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

}
