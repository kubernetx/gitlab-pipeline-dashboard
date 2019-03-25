package kx.io.dashboard.gitlab

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GitlabConfiguration {

    @Bean
    fun gitlabClient(gitlabProperties: GitlabProperties): GitlabClient {
        return GitlabClient(gitlabProperties)
    }

    @Bean
    fun gitLabPipelineService(gitlabClient: GitlabClient): GitlabPipelineService {
        return GitlabPipelineService(gitlabClient)
    }

    @Bean
    fun gitlabPipelineMetrics(gitLabPipelineService: GitlabPipelineService,
                              meterRegistry: MeterRegistry,
                              gitlabProperties: GitlabProperties) : GitlabPipelineMetrics {
        return GitlabPipelineMetrics(gitLabPipelineService, meterRegistry, gitlabProperties)
    }
}