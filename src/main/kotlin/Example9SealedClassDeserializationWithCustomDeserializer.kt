import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets


@JsonDeserialize(using = CustomDeserializer::class)
sealed class SealedWithCustomDeserializer {
    data class StringSealed(val value: String, val encoding: Charset = StandardCharsets.UTF_8) : SealedWithCustomDeserializer()
    data class IntSealed(val value: Int, val default: Int = -1) : SealedWithCustomDeserializer()
    data class BooleanSealed(val value: Boolean) : SealedWithCustomDeserializer()
}

class CustomDeserializer : StdDeserializer<SealedWithCustomDeserializer>(SealedWithCustomDeserializer::class.java) {
    override fun deserialize(jsonParser: JsonParser, ctxt: DeserializationContext?): SealedWithCustomDeserializer {

        val jsonNode = jsonParser.codec.readTree<JsonNode>(jsonParser)
        val valueNode = jsonNode["value"]
        val encodingNode = jsonNode["encoding"]
        val defaultNode = jsonNode["default"]

        return if (valueNode.isTextual && encodingNode != null) {
            SealedWithCustomDeserializer.StringSealed(valueNode.textValue())
        } else if (valueNode.isInt && defaultNode != null) {
            SealedWithCustomDeserializer.IntSealed(valueNode.intValue())
        } else if (valueNode.isBoolean) {
            SealedWithCustomDeserializer.BooleanSealed(valueNode.booleanValue())
        } else {
            throw IllegalArgumentException("can't deserialize node of type ${valueNode.nodeType}")
        }

    }

}

fun main() {

    val withCustomDeserializer = listOf(
        SealedWithCustomDeserializer.StringSealed(value = "qwertyuiop"),
        SealedWithCustomDeserializer.IntSealed(value = 123),
        SealedWithCustomDeserializer.BooleanSealed(value = true)
    )

    withCustomDeserializer.forEach {
        println("object       ==> $it")
        val serialized = objectMapper.writeValueAsString(it)
        println("serialised   ==> $serialized")
        val deserialized = objectMapper.readValue(serialized, jacksonTypeRef<SealedWithCustomDeserializer>())
        println("deserialised ==> $deserialized")
    }

}
