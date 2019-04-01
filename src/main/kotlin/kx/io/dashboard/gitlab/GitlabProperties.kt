package kx.io.dashboard.gitlab

import org.springframework.boot.context.properties.ConfigurationProperties
import javax.annotation.PostConstruct

@ConfigurationProperties("gitlab")
class GitlabProperties {
    lateinit var hostUrl: String
    lateinit var accessToken: String
    lateinit var projects: List<ProjectConfig>
    lateinit var branchRefs: List<BranchRef>

    @PostConstruct
    fun initIt() {
        projects.filter { it.branchRefs.isNullOrEmpty() }.forEach { it.branchRefs = branchRefs }
    }

    class ProjectConfig {
        lateinit var pathWithNamespace: String
        var branchRefs: List<BranchRef> = mutableListOf()

    }

    class BranchRef {
        lateinit var label: String
        lateinit var regex: String
    }
}
