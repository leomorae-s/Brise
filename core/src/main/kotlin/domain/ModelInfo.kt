package domain

data class ModelInfo(
    val name: String,
    val sizeBytes: Long,
    val parameterSize: String,
    val quantization: String
)