import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef


@JsonDeserialize(using = CustomDeserializer::class)
sealed class SealedWithCustomDeserializer {
    data class StringSealed(val value: String) : SealedWithCustomDeserializer()
    data class IntSealed(val value: Int) : SealedWithCustomDeserializer()
    data class BooleanSealed(val value: Boolean) : SealedWithCustomDeserializer()
}

class CustomDeserializer : StdDeserializer<SealedWithCustomDeserializer>(SealedWithCustomDeserializer::class.java) {
    override fun deserialize(jsonParser: JsonParser, ctxt: DeserializationContext?): SealedWithCustomDeserializer {
        val jsonNode = jsonParser.codec.readTree<JsonNode>(jsonParser)
        val value = jsonNode["value"]

        return when (jsonNode) {
            else -> throw IllegalArgumentException("can't deserialize node of type ${jsonNode.nodeType}")
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
        val deserialized = objectMapper.readValue(serialized, jacksonTypeRef<SealedWithTypeInfo>())
        println("deserialised ==> $deserialized")
    }

}
