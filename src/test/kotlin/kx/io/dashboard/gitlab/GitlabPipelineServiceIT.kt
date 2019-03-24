package kx.io.dashboard.gitlab

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class GitlabPipelineServiceIT {
    @Autowired
    lateinit var gitlabPipelineService: GitlabPipelineService

    @Test
    fun lastPipeLineStatus() {
        println(gitlabPipelineService.lastPipeLineStatus("org/project1", "^release\\/\\S*"))
    }
}