import com.cashwu.module
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {

        application {
            module()
        }

        val response = client.get("/")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello World!", response.bodyAsText())
    }

    @Test
    fun testHtml() = testApplication {

        application {
            module()
        }

        val response = client.get("/test")

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(ContentType.Text.Html.contentSubtype, response.contentType()?.contentSubtype)
        assertContains(response.bodyAsText(), "<h1>Hello World!</h1>")
    }

    @Test
    fun testByPriority() = testApplication {

        application {
            module()
        }

        val response = client.get("/tasks/byPriority/Medium")
        val body = response.bodyAsText()

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(body, "Mow the lawn")
        assertContains(body, "Paint the fence")
    }

    @Test
    fun testByPriorityInvalid() = testApplication {

        application {
            module()
        }

        val response = client.get("/tasks/byPriority/invalid")

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun testByPriorityNotFound() = testApplication {

        application {
            module()
        }

        val response = client.get("/tasks/byPriority/Vital")

        assertEquals(HttpStatusCode.OK, response.status)
        assertContains(response.bodyAsText(), "No tasks found")
    }


}