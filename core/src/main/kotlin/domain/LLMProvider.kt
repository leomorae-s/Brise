package domain

import kotlinx.coroutines.flow.Flow
import ollama.dtos.DetailsResponse
import ollama.dtos.GenerateResponse
import ollama.dtos.PullProgress

interface LLMProvider {
    suspend fun list(): List<ModelInfo>

    suspend fun listRunning(): List<ModelInfo>

    suspend fun showModelDetails(model: String): DetailsResponse

    suspend fun deleteModel(model: String): Unit

    fun pullModel(model: String): Flow<PullProgress>

    fun generate(
        model: String,
        prompt: String,
    ): Flow<GenerateResponse>
}
