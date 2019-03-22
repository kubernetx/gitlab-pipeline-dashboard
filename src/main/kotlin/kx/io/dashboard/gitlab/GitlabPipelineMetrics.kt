package kx.io.dashboard.gitlab

class GitlabPipelineMetrics(gitlabClient: GitlabClient) {

    val gitlabClient = gitlabClient

    fun lastPipeLineStatus(projectId: Int, branchName: String) {
        gitlabClient.fetchPipelines(projectId, 100, Sort.DESC)
                .filter{ it.ref == branchName}.blockFirst()l
    }

}