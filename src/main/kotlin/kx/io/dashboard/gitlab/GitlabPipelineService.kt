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

    fun lastPipelineStatusValue(projectId: String, branchName: String) : Int {
        return lastPipeLineStatus(projectId, branchName).ordinal
    }
}

enum class Status {
    SUCCESS, RUNNING, PENDING, SKIPPED, CANCELED, UNKNOWN, FAILED
}