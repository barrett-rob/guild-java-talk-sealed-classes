sealed class SealedProperty {
    data class StringSealedProperty(val value: String, val min: Int = 0, val max: Int? = null) : SealedProperty()
    data class IntSealedProperty(val value: Int, val min: Int = Integer.MIN_VALUE, val max: Int = Integer.MAX_VALUE) : SealedProperty()
    data class BooleanSealedProperty(val value: Boolean) : SealedProperty()
}

fun validateDataTypeSealedClass(property: SealedProperty): Boolean {

    return when (property) {
        is SealedProperty.StringSealedProperty,
        is SealedProperty.IntSealedProperty,
        is SealedProperty.BooleanSealedProperty -> true
    }

}

fun validateMinSealedClass(property: SealedProperty): Boolean {

    return when (property) {
        is SealedProperty.StringSealedProperty ->
            property.value.length >= property.min

        is SealedProperty.IntSealedProperty ->
            property.value >= property.min

        is SealedProperty.BooleanSealedProperty ->
            true
    }
}

fun validateMaxSealedClass(property: SealedProperty): Boolean {

    return when (property) {
        is SealedProperty.StringSealedProperty ->
            if (property.max == null) {
                true
            } else {
                property.value.length <= property.max
            }

        is SealedProperty.IntSealedProperty ->
            property.value <= property.max

        is SealedProperty.BooleanSealedProperty ->
            true
    }
}

fun main() {

    val stringProperty = SealedProperty.StringSealedProperty(value = "qwertyuiop", min = 0, max = 64)
    val intProperty = SealedProperty.IntSealedProperty(value = 123, min = 0, max = 999)
    val booleanProperty = SealedProperty.BooleanSealedProperty(value = true)

    val properties = listOf(stringProperty, intProperty, booleanProperty)

    properties.forEach {
        validateMaxSealedClass(it)
        validateMaxSealedClass(it)
        validateMaxSealedClass(it)

        // the compiler will tell us if all the properties are handled
    }

}
