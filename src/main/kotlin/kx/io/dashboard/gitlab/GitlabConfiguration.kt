package kx.io.dashboard.gitlab

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GitlabConfiguration {

    @Bean
    fun gitlabClient(gitlabProperties: GitlabProperties): GitlabClient {
        return GitlabClient(gitlabProperties)
    }

    @Bean
    fun gitLabProjects(gitlabProperties: GitlabProperties): List<Project?> {
        return gitlabProperties.projects.map { gitlabClient(gitlabProperties).fetchProject(it).block() }
    }
}