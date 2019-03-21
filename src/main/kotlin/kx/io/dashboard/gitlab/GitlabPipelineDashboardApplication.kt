package kx.io.dashboard.gitlab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@EnableConfigurationProperties(GitlabProperties::class)
@SpringBootApplication
class GitlabPipelineDashboardApplication

fun main(args: Array<String>) {
    runApplication<GitlabPipelineDashboardApplication>(*args)
}
