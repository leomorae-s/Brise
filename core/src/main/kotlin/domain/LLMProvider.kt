package domain

import ollama.dtos.DetailsResponse

interface LLMProvider {


    suspend fun list(): List<ModelInfo>

    suspend fun listRunning(): List<ModelInfo>

    suspend fun showModelDetails(model: String): DetailsResponse
}
