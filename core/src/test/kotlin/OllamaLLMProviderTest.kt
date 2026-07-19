import domain.LLMProvider
import io.ktor.client.plugins.logging.LogLevel
import kotlinx.coroutines.test.runTest
import ollama.OllamaClientFactory
import ollama.OllamaLLMProvider
import org.junit.jupiter.api.Tag
import kotlin.test.Test

class OllamaLLMProviderTest {
    @Tag("integration")
    @Test
    fun `list return availble models`(): Unit =
        runTest {
            val factory = OllamaClientFactory(logLevel = LogLevel.BODY)
            val provider: LLMProvider = OllamaLLMProvider(factory.getClient())

            val models = provider.list()

            println(models)
            assert(models.isNotEmpty())
        }
}
