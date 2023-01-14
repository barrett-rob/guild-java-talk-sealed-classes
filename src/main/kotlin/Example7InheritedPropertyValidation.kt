interface InheritedProperty<T> {
    val value: T
    fun validateDataType(): Boolean
    fun validateMin(): Boolean
    fun validateMax(): Boolean
}

class StringInheritedProperty(override val value: String, val min: Int = 0, val max: Int?) : InheritedProperty<String> {

    override fun validateDataType() = true

    override fun validateMin() = value.length >= min

    override fun validateMax() = if (max == null) {
        true
    } else {
        value.length <= max
    }
}

class IntInheritedProperty(override val value: Int, val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE) : InheritedProperty<Int> {

    override fun validateDataType() = true

    override fun validateMin() = value >= min

    override fun validateMax() = value <= max
}

class BooleanInheritedProperty(override val value: Boolean) : InheritedProperty<Boolean> {

    override fun validateDataType() = true

    override fun validateMin() = true

    override fun validateMax() = true
}

fun main() {

    val stringProperty = StringInheritedProperty(value = "qwertyuiop", min = 0, max = 64)
    val intProperty = IntInheritedProperty(value = 123, min = 0, max = 999)
    val booleanProperty = BooleanInheritedProperty(value = true)

    val properties = listOf(stringProperty, intProperty, booleanProperty)

    properties.forEach {
        it.validateDataType()
        it.validateMin()
        it.validateMax()

        // the compiler will tell us if all the interface operations are implemented
    }

}
