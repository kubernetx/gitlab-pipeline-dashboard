package kx.io.dashboard.gitlab

import java.util.regex.Pattern

class GitlabPipelineService(private val gitlabClient: GitlabClient) {

    fun lastPipeLineStatus(projectId: String, branchName: String) : Status {
        return gitlabClient.fetchPipelines(projectId, 100, Sort.DESC)
                .filter{ Pattern.matches(branchName, it.ref)}
                .map { it.status }
                .map {
                    when (it) {
                        "running" -> Status.RUNNING
                        "pending" -> Status.PENDING
                        "success" -> Status.SUCCESS
                        "failed" -> Status.FAILED
                        "canceled" -> Status.CANCELED
                        "skipped" -> Status.SKIPPED
                        else -> Status.UNKNOWN
                    }
                }
                .blockFirst() ?: Status.UNKNOWN
    }
}

enum class Status(int: Int) {
    RUNNING(0), PENDING(1), SUCCESS(2), FAILED(3), CANCELED(4), SKIPPED(5), UNKNOWN(6)
}