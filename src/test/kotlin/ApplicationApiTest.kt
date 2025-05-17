import com.cashwu.model.Priority
import com.cashwu.model.Task
import com.cashwu.module
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ApplicationApiTest {

    @Test
    fun testCanBeFoundByPriority() = testApplication {

        application {
            module()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.get("/api/byPriority/Medium")

        val result = response.body<List<Task>>()

        assertEquals(HttpStatusCode.OK, response.status)

        val expectedTaskNames = listOf("gardening", "painting")
        val actualTaskNames = result.map(Task::name)

        assertContentEquals(expectedTaskNames, actualTaskNames)

//        assertEquals(1, result.size)
//        assertEquals("Mow the lawn", result[0].name)
//        assertEquals("Paint the fence", result[0].description)
//        assertEquals("Medium", result[0].priority.name)
    }

    @Test
    fun invalidPriority() = testApplication {

        application {
            module()
        }

        val response = client.get("/api/byPriority/invalid")
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun notFoundPriority() = testApplication {

        application {
            module()
        }
        val response = client.get("/api/byPriority/Vital")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun addTask() = testApplication {

        application {
            module()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val task = Task("swimming", "Clean the house", Priority.Low)

        val response = client.post("/api/addTask") {
            header(
                HttpHeaders.ContentType,
                ContentType.Application.Json
            )
//            contentType(ContentType.Application.Json)
            setBody(task)
        }

        assertEquals(HttpStatusCode.Created, response.status)

        val response2 = client.get("/api/tasks")
        assertEquals(HttpStatusCode.OK, response2.status)

        val taskNames = response2.body<List<Task>>().map { it.name }

        assertContains(taskNames, task.name)
    }
}