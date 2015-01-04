/*
 * Copyright © 2015 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.maven;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Organization;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

/**
 * Tests for GitHubLifecycleParticipant
 */
public class GitHubLifecycleParticipantTest {

    private static final String        EXISTING_PROJECT_URL = "EXISTING_PROJECT_URL";                           //$NON-NLS-1$
    private static final String        ORGANIZATION_NAME    = "ORGANIZATION_NAME";                              //$NON-NLS-1$
    private static final String        GITHUB_PROJECT_URL   = "http://ORGANIZATION_NAME.github.io/PROJECT_NAME"; //$NON-NLS-1$
    private static final String        ARTIFACT_ID          = "PROJECT_NAME";                                   //$NON-NLS-1$

    /** Checks thrown exceptions */
    @Rule
    public ExpectedException           thrown               = ExpectedException.none();

    private GitHubLifecycleParticipant gitHubLifecycleParticipant;
    private MavenSession               mavenSession;
    private MavenProject               mavenProject;

    /**
     *
     */
    @Before
    public void setUp() {
        gitHubLifecycleParticipant = new GitHubLifecycleParticipant();
        gitHubLifecycleParticipant.setOrganizationName(ORGANIZATION_NAME);

        mavenSession = Mockito.mock(MavenSession.class);
        mavenProject = new MavenProject();
        mavenProject.setArtifactId(ARTIFACT_ID);

        BDDMockito.given(mavenSession.getCurrentProject()).willReturn(mavenProject);
    }

    /**
     * Checks that an existing project.url is not overwritten.
     *
     * @throws MavenExecutionException
     *             In case something went wrong with the maven execution
     */
    @Test
    public void shouldNotUpdateExistingProjectUrl() throws MavenExecutionException {
        // given
        mavenProject.setUrl(EXISTING_PROJECT_URL);

        // when
        gitHubLifecycleParticipant.afterProjectsRead(mavenSession);

        // then
        Assert.assertEquals(EXISTING_PROJECT_URL, mavenProject.getUrl());
    }

    /**
     * Checks that a project without a project.url is updated with the correct GitHub project URL.
     *
     * @throws MavenExecutionException
     *             In case something went wrong with the maven execution
     */
    @Test
    public void shouldUpdateNonExistingProjectUrl() throws MavenExecutionException {
        // given
        mavenProject.setUrl(null);

        // when
        gitHubLifecycleParticipant.afterProjectsRead(mavenSession);

        // then
        Assert.assertEquals(GITHUB_PROJECT_URL, mavenProject.getUrl());
    }

    /**
     * Checks that a project with an empty project.url is updated with the correct GitHub project URL.
     *
     * @throws MavenExecutionException
     *             In case something went wrong with the maven execution
     */
    @Test
    public void shouldUpdateEmptyProjectUrl() throws MavenExecutionException {
        // given
        mavenProject.setUrl(""); //$NON-NLS-1$

        // when
        gitHubLifecycleParticipant.afterProjectsRead(mavenSession);

        // then
        Assert.assertEquals(GITHUB_PROJECT_URL, mavenProject.getUrl());
    }

    /**
     * Checks that the configured organization name is returned.
     */
    @Test
    public void shouldGetConfigurationOrganizationName() {
        // when
        final String organizationName = gitHubLifecycleParticipant.getOrganizationName();

        // then
        Assert.assertEquals(ORGANIZATION_NAME, organizationName);
    }

    /**
     * Checks that the extension can't run without a configured organizatio name.
     *
     * @throws MavenExecutionException
     *             In case something went wrong with the maven execution
     */
    @Test
    public void shouldThrowNPEWithoutOrganizationName() throws MavenExecutionException {
        // given
        gitHubLifecycleParticipant.setOrganizationName(null);

        // when
        thrown.expect(NullPointerException.class);

        // then
        gitHubLifecycleParticipant.afterProjectsRead(mavenSession);
    }

    /**
     * Checks that the configured POM organization name (<code>${project.organization.name}</code>) is used as a
     * fallback in case the extension was not configured itself.
     *
     * @throws MavenExecutionException
     *             In case something went wrong with the maven execution
     */
    @Test
    public void shouldUsePOMOrganizationNameAsFallback() throws MavenExecutionException {
        // given
        gitHubLifecycleParticipant.setOrganizationName(null);
        final Organization organization = new Organization();
        organization.setName(ORGANIZATION_NAME);
        mavenProject.setOrganization(organization);

        // when
        gitHubLifecycleParticipant.afterProjectsRead(mavenSession);

        // then
        Assert.assertEquals(GITHUB_PROJECT_URL, mavenProject.getUrl());
    }

}
