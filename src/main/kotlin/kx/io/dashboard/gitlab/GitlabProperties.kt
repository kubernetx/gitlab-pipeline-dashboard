package kx.io.dashboard.gitlab

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("gitlab")
class GitlabProperties {
    lateinit var hostUrl: String
    lateinit var accessToken: String
}