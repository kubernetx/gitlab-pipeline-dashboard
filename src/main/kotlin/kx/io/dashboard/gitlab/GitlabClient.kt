package kx.io.dashboard.gitlab

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import java.net.http.HttpResponse
import java.util.function.Predicate

class GitlabClient(gitlabProperties: GitlabProperties) {

    val webClient = WebClient.builder()
            .baseUrl(gitlabProperties.hostUrl).defaultHeader("PRIVATE-TOKEN", gitlabProperties.accessToken)
            .build()

    fun fetchPipelines(projectId: Int, perPage: Int = 100): Flux<Pipeline> {
        return webClient.get()
                .uri("/projects/{projectId}/pipelines?per_page={perPage}", projectId, perPage)
                .retrieve()
                .bodyToFlux(Pipeline::class.java)
    }

    fun deletePipeline(projectId: Int, pipelineId: Int): WebClient.ResponseSpec {
        return webClient.delete().uri("/projects/{projectId}/pipelines/{pipelineId}", projectId, pipelineId).retrieve()
    }
}

class Pipeline (val id: Int,
                val status: String,
                val ref: String,
                val sha: String,
                @JsonProperty("web_url")
                 val webUrl: String)
