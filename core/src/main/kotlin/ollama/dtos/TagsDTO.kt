@file:OptIn(ExperimentalSerializationApi::class)

package ollama.dtos

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
data class TagsResponse(
    val models: List<OllamaModel>,
)

@Serializable
data class OllamaModel(
    val name: String,
    val model: String,
    @SerialName("modified_at")
    val modifiedAt: String,
    val size: Long,
    val details: ModelDetails,
)

@Serializable
data class DetailsResponse(
    val parameters: String,
    val license: String,
    val capabilities: List<String>,
    @SerialName("modified_at")
    val modifiedAt: String,
    val details: ModelDetails,
)

@Serializable
data class ModelDetails(
    val format: String,
    val family: String,
    var families: List<String>? = null,
    @SerialName("parameter_size")
    val parameterSize: String,
    @SerialName("quantization_level")
    val quantizationLevel: String,
)

@Serializable
data class PullProgress(
    val status: String,
    val digest: String? = null,
    val total: Long? = null,
    val completed: Long? = null,
)

@Serializable
@JsonIgnoreUnknownKeys
data class GenerateResponse(
    val model: String,
    @SerialName("created_at")
    val createdAt: String,
    val response: String,
    val thinking: String? = null,
    val done: Boolean,
    @SerialName("done_reason")
    val doneReason: String? = null,
    @SerialName("total_duration")
    val totalDuration: Long? = null,
)

@Serializable
@JsonIgnoreUnknownKeys
data class Message(
    val role: String,
    val content: String,
    val thinking: String? = null,
    val images: List<String>? = null,
)

@JsonIgnoreUnknownKeys
@Serializable
data class ChatResponse(
    val model: String,
    @SerialName("created_at")
    val createdAt: String,
    val message: Message,
    val done: Boolean,
    @SerialName("done_reason")
    val doneReason: String? = null,
    @SerialName("total_duration")
    val totalDuration: Long? = null,
)
