package kx.io.dashboard.gitlab

import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class GitlabClientIT {

    @Autowired
    lateinit var gitlabClient: GitlabClient

    @Test
    fun fetchPipeline() {
        Assertions.assertThat(gitlabClient.fetchPipelines(185, sort = Sort.DESC)
                .doOnNext{println(it)}
                .count()
                .block())
                .isGreaterThan(0)
    }

    @Test
    fun deletePipeline() {
        val resp = gitlabClient.deletePipeline(185, 75980)
    }

    @Test
    fun fetchProjects() {
        val resp = gitlabClient.fetchProjects().doOnNext{ println(it)}.blockLast()

    }

    @Test
    fun fetchProject() {
        val resp = gitlabClient.fetchProject("CFC/cfc-web-app-bff").doOnNext{ println(it)}.block()

    }
}