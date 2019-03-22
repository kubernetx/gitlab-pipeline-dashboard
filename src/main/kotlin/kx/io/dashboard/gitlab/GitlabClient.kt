package kx.io.dashboard.gitlab

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class GitlabClient(gitlabProperties: GitlabProperties) {

    private val webClient = WebClient.builder()
            .baseUrl(gitlabProperties.hostUrl).defaultHeader("PRIVATE-TOKEN", gitlabProperties.accessToken)
            .build()

    fun fetchPipelines(projectId: Int, perPage: Int = 100, sort: Sort): Flux<Pipeline> {
        println(sort.name)
        return webClient.get()
                .uri("/projects/{projectId}/pipelines?per_page={perPage}&sort={sort}", projectId, perPage, sort.displayName)
                .retrieve()
                .bodyToFlux(Pipeline::class.java)
    }

    fun deletePipeline(projectId: Int, pipelineId: Int): WebClient.ResponseSpec {
        return webClient.delete().uri("/projects/{projectId}/pipelines/{pipelineId}", projectId, pipelineId).retrieve()
    }

    fun fetchProjects(perPage: Int = 100): Flux<Project> {
        return webClient.get()
                .uri("/projects?per_page={perPage}", perPage)
                .retrieve()
                .bodyToFlux(Project::class.java)
    }

    fun fetchProject(projectName: String): Mono<Project> {
        return webClient.get()
                .uri("/projects/{projectName}", projectName)
                .retrieve()
                .bodyToMono(Project::class.java)
    }
}

data class Pipeline (val id: Int,
                val status: String,
                val ref: String,
                val sha: String,
                @JsonProperty("web_url")
                 val webUrl: String)

data class Project (
        val id: Int,
        val description: String,
        val name: String,
        @JsonProperty("name_with_namespace")
        val nameWithNamespace: String,
        val path: String,
        @JsonProperty("path_with_namespace")
        val pathWithNamespace: String)

enum class Sort(val displayName: String) {
    ASC("asc"), DESC("desc")
}
