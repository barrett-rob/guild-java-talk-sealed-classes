interface InheritedProperty<T> {
    val value: T
    val min: Int?
    val max: Int?
    fun validateDataType(): Boolean
    fun validateMin(): Boolean
    fun validateMax(): Boolean
}

class StringInheritedProperty(override val value: String, override val min: Int = 0, override val max: Int?) : InheritedProperty<String> {

    override fun validateDataType() = true

    override fun validateMin() = value.length >= min

    override fun validateMax() = if (max == null) {
        true
    } else {
        value.length <= max
    }
}

class IntInheritedProperty(override val value: Int, override val min: Int = Int.MIN_VALUE, override val max: Int = Int.MAX_VALUE) : InheritedProperty<Int> {

    override fun validateDataType() = true

    override fun validateMin() = value >= min

    override fun validateMax() = if (max == null) {
        true
    } else {
        value <= max
    }
}

class BooleanInheritedProperty(override val value: Boolean, override val min: Int? = null, override val max: Int? = null) : InheritedProperty<Boolean> {

    override fun validateDataType() = true

    override fun validateMin() = true

    override fun validateMax() = true
}

fun main() {

    val stringProperty = Property(
        propertyDefinition = PropertyDefinition(dataType = PropertyDefinitionDataTypes.STRING, min = 0, max = 64),
        value = "qwertyuiop"
    )
    val intProperty = Property(
        propertyDefinition = PropertyDefinition(dataType = PropertyDefinitionDataTypes.INTEGER, min = 0, max = 999),
        value = "123"
    )
    val booleanProperty = Property(
        propertyDefinition = PropertyDefinition(dataType = PropertyDefinitionDataTypes.BOOLEAN),
        value = "true"
    )

    val properties = listOf(stringProperty, intProperty, booleanProperty)

    properties.forEach {
        validateDataTypeEnum(it.value, it.propertyDefinition)
        validateMinEnum(it.value, it.propertyDefinition)
        validateMaxEnum(it.value, it.propertyDefinition)

        // the compiler will tell us if all the properties are handled
    }

}
