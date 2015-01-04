package com.github.sebhoss.maven;

import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

/**
 * Tests for {@link GitHubLifecycleParticipant}
 */
public class GitHubLifecycleParticipantTest {

    private static final String        EXISTING_PROJECT_URL = "EXISTING_PROJECT_URL"; //$NON-NLS-1$
    private static final String        ORGANIZATION_NAME    = "ORGANIZATION_NAME";   //$NON-NLS-1$

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

        BDDMockito.given(mavenSession.getCurrentProject()).willReturn(mavenProject);
    }

    /**
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

}
