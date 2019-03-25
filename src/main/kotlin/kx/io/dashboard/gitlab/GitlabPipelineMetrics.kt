package kx.io.dashboard.gitlab

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tags
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.atomic.AtomicInteger

class GitlabPipelineMetrics(private val gitlabPipelineService: GitlabPipelineService,
                            private val meterRegistry: MeterRegistry,
                            private val gitlabProperties: GitlabProperties) {

    private val projectMap = mutableMapOf<String, AtomicInteger>()

    private val log = KotlinLogging.logger { }

    init {
        gitlabProperties.projects.forEach {project ->
            project.branchRefs.forEach {ref ->
                val metricStatus = AtomicInteger(gitlabPipelineService.lastPipeLineStatus(project.pathWithNamespace, ref).ordinal)
                meterRegistry.gauge("gitlab",
                        Tags.of("pathWithNamespace", project.pathWithNamespace, "ref", ref),
                        metricStatus)
                projectMap[project.pathWithNamespace + ref] = metricStatus
            }
        }
    }

    @Scheduled(fixedRate = 10000)
    fun updatePipelineStatus() {
        gitlabProperties.projects.forEach {project ->
            project.branchRefs.forEach {ref ->
                log.info { "Update gitlab pipeline metric for ${project.pathWithNamespace} with ref pattern $ref" }
                val projectFromMap = projectMap[project.pathWithNamespace + ref]
                projectFromMap?.set(gitlabPipelineService.lastPipeLineStatus(project.pathWithNamespace, ref).ordinal)
            }
        }
    }

}