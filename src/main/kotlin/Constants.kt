import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val objectMapper: ObjectMapper = jacksonObjectMapper().findAndRegisterModules()

const val RES_CREATE = """{"resId": 1,"createdAt": "2023-01-12T06:27:18Z"}"""
const val RES_MODIFY = """{"resId": 2,"modifiedAt": "2023-01-12T06:27:18Z"}"""
const val RES_CANCEL = """{"resId": 3,"cancelledAt": "2023-01-12T06:27:18Z"}"""

fun doSomething(any: Any) {}
