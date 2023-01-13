object PropertyDefinitionDataTypes { // simplified
    val STRING = "string"
    val INTEGER = "integer"
    val BOOLEAN = "boolean"
}

data class PropertyDefinition(
    val dataType: String,
    val min: Int? = null,
    val max: Int? = null
)

data class Property(
    val propertyDefinition: PropertyDefinition,
    val value: String
)

