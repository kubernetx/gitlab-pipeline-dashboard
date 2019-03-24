package kx.io.dashboard.gitlab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(GitlabProperties::class)
class GitlabPipelineDashboardApplication

fun main(args: Array<String>) {
    runApplication<GitlabPipelineDashboardApplication>(*args)
}
