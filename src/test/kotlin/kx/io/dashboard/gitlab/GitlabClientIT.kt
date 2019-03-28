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
        Assertions.assertThat(gitlabClient.fetchPipelines("julien.piccaluga/gitlab-pipeline-dashboard-it", sort = Sort.DESC)
                .doOnNext{println(it)}
                .count()
                .block())
                .isGreaterThan(0)
    }
}