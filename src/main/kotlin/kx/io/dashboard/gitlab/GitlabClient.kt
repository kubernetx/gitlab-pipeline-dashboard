package kx.io.dashboard.gitlab

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

class GitlabClient(gitlabProperties: GitlabProperties) {

    private val webClient = WebClient.builder()
            .baseUrl(gitlabProperties.hostUrl).defaultHeader("PRIVATE-TOKEN", gitlabProperties.accessToken)
            .build()

    fun fetchPipelines(projectId: String, perPage: Int = 100, sort: Sort): Flux<Pipeline> {
        return webClient.get()
                .uri("/projects/{projectId}/pipelines?per_page={perPage}&sort={sort}", projectId, perPage, sort.displayName)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Pipeline::class.java)
    }
}

data class Pipeline (val id: Int,
                val status: String,
                val ref: String,
                val sha: String,
                @JsonProperty("web_url")
                 val webUrl: String)

enum class Sort(val displayName: String) {
    ASC("asc"), DESC("desc")
}
