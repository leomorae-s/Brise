package domain

interface LLMProvider {


    suspend fun list(): List<ModelInfo>

}