package kx.io.dashboard.gitlab

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tags
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalTime
import java.util.concurrent.atomic.AtomicInteger

class GitlabPipelineMetrics(private val gitlabPipelineService: GitlabPipelineService,
                            private val meterRegistry: MeterRegistry,
                            val gitlabProperties: GitlabProperties) {

    private val projectMap = mutableMapOf<String, AtomicInteger>()

    private val log = KotlinLogging.logger { }

    init {
        gitlabProperties.projects.forEach {project ->
            project.branchRefs.forEach {ref ->
                val statusValue = gitlabPipelineService.lastPipelineStatusValue(project.pathWithNamespace, ref.regex)
                val metricStatus = AtomicInteger(statusValue)
                meterRegistry.gauge("gitlab-pipeline",
                        Tags.of("pathWithNamespace", project.pathWithNamespace, "ref", ref.label),
                        metricStatus)
                projectMap[project.pathWithNamespace + ref.label] = metricStatus
            }
        }
    }

    @Scheduled(fixedRateString = "#{@gitlabPipelineMetrics.gitlabProperties.pollingMs}")
    fun updatePipelineStatus() {
        log.info { "Fetching pipeline status at ${LocalTime.now()}" }
        gitlabProperties.projects.forEach {project ->
            project.branchRefs.forEach {ref ->
                val msg = "Update gitlab pipeline metric for ${project.pathWithNamespace} with ref pattern ${ref.regex}"
                log.info { msg }
                val projectFromMap = projectMap[project.pathWithNamespace + ref.label]
                projectFromMap?.set(gitlabPipelineService.lastPipelineStatusValue(project.pathWithNamespace, ref.regex))
            }
        }
    }
}