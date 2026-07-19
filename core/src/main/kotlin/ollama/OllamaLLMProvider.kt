package ollama

import domain.LLMProvider
import domain.ModelInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeoutConfig
import io.ktor.client.plugins.timeout
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.readLine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import ollama.dtos.DetailsResponse
import ollama.dtos.OllamaModel
import ollama.dtos.PullProgress
import ollama.dtos.TagsResponse

class OllamaLLMProvider(
    private val client: HttpClient,
) : LLMProvider {
    private fun OllamaModel.toDomain() =
        ModelInfo(
            name = name,
            sizeBytes = size,
            parameterSize = details.parameterSize,
            quantization = details.quantizationLevel,
        )

    override suspend fun list(): List<ModelInfo> {
        val response: TagsResponse = client.get("api/tags").body()
        return response.models.map { it.toDomain() }
    }

    override suspend fun listRunning(): List<ModelInfo> {
        val response: TagsResponse = client.get("api/ps").body()
        return response.models.map { it.toDomain() }
    }

    override suspend fun showModelDetails(model: String): DetailsResponse {
        val jsobBody = buildJsonObject { put("model", model) }
        val response: HttpResponse =
            client.post("api/show") {
                contentType(ContentType.Application.Json)
                setBody(jsobBody)
            }

        val parsedResponse: DetailsResponse = response.body()

        return parsedResponse
    }

    override suspend fun deleteModel(model: String) {
        val jsonBody = buildJsonObject { put("model", model) }
        client.delete("/api/delete") {
            contentType(ContentType.Application.Json)
            setBody(jsonBody)
        }
    }

    override fun pullModel(model: String): Flow<PullProgress> =
        flow {
            val jsobBody = buildJsonObject { put("model", model) }
            val response: HttpResponse =
                client.post("api/pull") {
                    contentType(ContentType.Application.Json)
                    setBody(jsobBody)
                    timeout {
                        requestTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
                        socketTimeoutMillis = 60_000
                    }
                }
            val channel = response.bodyAsChannel()
            while (!channel.isClosedForRead) {
                val line = channel.readLine() ?: break
                if (line.isBlank()) continue
                val progress: PullProgress = Json.decodeFromString(line)
                emit(progress)
            }
        }
}
