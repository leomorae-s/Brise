package ollama.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagsResponse(
    val models: List<OllamaModel>
)

@Serializable
data class OllamaModel(
    val name: String,
    val model: String,
    @SerialName("modified_at")
    val modifiedAt: String,
    val size: Long,
    val details: ModelDetails
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
    val quantizationLevel: String
)