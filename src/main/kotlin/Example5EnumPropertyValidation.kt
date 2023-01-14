enum class PropertyDefinitionDataTypesEnum {
    STRING, INTEGER, BOOLEAN
}

fun validateDataTypeEnum(value: String, propertyDefinition: PropertyDefinition): Boolean {

    val enumValue = PropertyDefinitionDataTypesEnum.valueOf(propertyDefinition.dataType)

    return when (enumValue) {

        PropertyDefinitionDataTypesEnum.STRING -> {
            true // noop
        }

        PropertyDefinitionDataTypesEnum.INTEGER -> {
            if (value.trim() == "") {
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

        PropertyDefinitionDataTypesEnum.BOOLEAN -> {
            if (value.trim() == "") {
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
    }

}

fun validateMinEnum(value: String, propertyDefinition: PropertyDefinition): Boolean {

    val enumValue = PropertyDefinitionDataTypesEnum.valueOf(propertyDefinition.dataType)

    return when (enumValue) {
        PropertyDefinitionDataTypesEnum.STRING -> {
            if (propertyDefinition.min == null) {
                true
            } else {
                value.length >= propertyDefinition.min
            }
        }

        PropertyDefinitionDataTypesEnum.INTEGER -> {
            if (propertyDefinition.min == null) {
                true
            } else {
                value.toInt() >= propertyDefinition.min
            }
        }

        PropertyDefinitionDataTypesEnum.BOOLEAN -> {
            true // noop
        }
    }
}

fun validateMaxEnum(value: String, propertyDefinition: PropertyDefinition): Boolean {

    val enumValue = PropertyDefinitionDataTypesEnum.valueOf(propertyDefinition.dataType)

    return when (enumValue) {
        PropertyDefinitionDataTypesEnum.STRING -> {
            if (propertyDefinition.max == null) {
                true
            } else {
                value.length <= propertyDefinition.max
            }
        }

        PropertyDefinitionDataTypesEnum.INTEGER -> {
            if (propertyDefinition.max == null) {
                true
            } else {
                value.toInt() <= propertyDefinition.max
            }
        }

        PropertyDefinitionDataTypesEnum.BOOLEAN -> {
            true // noop
        }
    }
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

        // the compiler will tell us if all the dataTypes are handled
    }

}
