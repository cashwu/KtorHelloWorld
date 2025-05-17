import com.cashwu.model.Priority
import com.cashwu.module
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationJsonPathTest {

    @Test
    fun testCanBeFound() = testApplication {

        application {
            module()
        }

        val jsonDoc = client.getAsJsonPath("/api/tasks")

        val result: List<String> = jsonDoc.read("$[*].name")

        assertEquals("cleaning", result[0])
        assertEquals("gardening", result[1])
        assertEquals("shopping", result[2])
    }

    @Test
    fun tasksBanBeFoundByPriority() = testApplication {

        application {
            module()
        }

        val priority = Priority.Medium
        val jsonDoc = client.getAsJsonPath("/api/byPriority/$priority")

        val result: List<String> = jsonDoc.read("$[?(@.priority == '$priority')].name")

        assertEquals(2, result.size)

        assertEquals("gardening", result[0])
        assertEquals("painting", result[1])
    }

    suspend fun HttpClient.getAsJsonPath(url: String): DocumentContext {
        val response = this.get(url) {
            accept(ContentType.Application.Json)
        }
        return JsonPath.parse(response.bodyAsText())
    }
}