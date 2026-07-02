package ollama

import domain.LLMProvider
import domain.ModelInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import ollama.dtos.OllamaModel
import ollama.dtos.TagsResponse


class OllamaLLMProvider(private val client: HttpClient): LLMProvider {

    private fun OllamaModel.toDomain() = ModelInfo(
        name = name,
        sizeBytes = size,
        parameterSize = details.parameterSize,
        quantization = details.quantizationLevel
    )


    override suspend fun list(): List<ModelInfo> {
        val response: TagsResponse = client.get("api/tags").body()
        return response.models.map { it.toDomain() }
    }

}
