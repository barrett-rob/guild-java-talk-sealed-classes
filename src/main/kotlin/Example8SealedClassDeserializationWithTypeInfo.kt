import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
sealed class SealedWithTypeInfo {
    data class StringSealed(val value: String) : SealedWithTypeInfo()
    data class IntSealed(val value: Int) : SealedWithTypeInfo()
    data class BooleanSealed(val value: Boolean) : SealedWithTypeInfo()
}

fun main() {

    val withTypeInfo = listOf(
        SealedWithTypeInfo.StringSealed(value = "qwertyuiop"),
        SealedWithTypeInfo.IntSealed(value = 123),
        SealedWithTypeInfo.BooleanSealed(value = true)
    )

    withTypeInfo.forEach {
        println("object       ==> $it")
        val serialized = objectMapper.writeValueAsString(it)
        println("serialised   ==> $serialized")
        val deserialized = objectMapper.readValue(serialized, jacksonTypeRef<SealedWithTypeInfo>())
        println("deserialised ==> $deserialized")
    }
}
