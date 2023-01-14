fun validateDataType(value: String, propertyDefinition: PropertyDefinition): Boolean {

    if (propertyDefinition.dataType == PropertyDefinitionDataTypes.STRING) {
        return true // noop
    }

    if (propertyDefinition.dataType == PropertyDefinitionDataTypes.INTEGER) {
        return if (value.trim() == "") {
            true
        } else {
            try {
                value.toInt()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    if (propertyDefinition.dataType == PropertyDefinitionDataTypes.BOOLEAN) {
        return if (value.trim() == "") {
            true
        } else {
            try {
                value.toBooleanStrict()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    return true
}

fun validateMin(value: String, propertyDefinition: PropertyDefinition): Boolean {

    if (propertyDefinition.dataType == PropertyDefinitionDataTypes.STRING) {
        return if (propertyDefinition.min == null) {
            true
        } else {
            value.length >= propertyDefinition.min
        }
    }

    if (propertyDefinition.dataType == PropertyDefinitionDataTypes.INTEGER) {
        return if (propertyDefinition.min == null) {
            true
        } else {
            value.toInt() >= propertyDefinition.min
        }
    }

    if (propertyDefinition.dataType == PropertyDefinitionDataTypes.BOOLEAN) {
        return true // noop
    }

    return true
}

fun validateMax(value: String, propertyDefinition: PropertyDefinition): Boolean {
    if (propertyDefinition.dataType == PropertyDefinitionDataTypes.STRING) {
        return if (propertyDefinition.max == null) {
            true
        } else {
            value.length <= propertyDefinition.max
        }
    }

    if (propertyDefinition.dataType == PropertyDefinitionDataTypes.INTEGER) {
        return if (propertyDefinition.max == null) {
            true
        } else {
            value.toInt() <= propertyDefinition.max
        }
    }

    if (propertyDefinition.dataType == PropertyDefinitionDataTypes.BOOLEAN) {
        return true // noop
    }

    return true
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
        validateDataType(it.value, it.propertyDefinition)
        validateMin(it.value, it.propertyDefinition)
        validateMax(it.value, it.propertyDefinition)

        // how easy is it to prove that all type/min/max combinations are validated?
        // what if you add another 11 dataTypes?
    }

}
