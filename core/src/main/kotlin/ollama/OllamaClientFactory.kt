package ollama

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class OllamaClientFactory(private val baseUrl: String = "http://localhost:11434/", logLevel: LogLevel = LogLevel.NONE) {

    private val client = HttpClient(CIO) {

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            })

        }

        defaultRequest {
            url(baseUrl)
        }

    }


    fun getClient(): HttpClient {
        return client;
    }
}

