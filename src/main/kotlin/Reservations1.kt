import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.time.ZonedDateTime

val objectMapper = jacksonObjectMapper().findAndRegisterModules()

fun main() {

    val creation = objectMapper.readValue(CREATION, jacksonTypeRef<Reservation>())
    println(creation)

    val modification = objectMapper.readValue(MODIFICATION, jacksonTypeRef<Reservation>())
    println(modification)

    val cancellation = objectMapper.readValue(CANCELLATION, jacksonTypeRef<Reservation>())
    println(cancellation)

}

const val CREATION = """{"resId": 1,"createdAt": "2023-01-12T06:27:18Z"}"""
const val MODIFICATION = """{"resId": 2,"modifiedAt": "2023-01-12T06:27:18Z"}"""
const val CANCELLATION = """{"resId": 3,"cancelledAt": "2023-01-12T06:27:18Z"}"""

data class Reservation(
    val resId: Int,
    val createdAt: ZonedDateTime?,
    val modifiedAt: ZonedDateTime?,
    val cancelledAt: ZonedDateTime?,
    var type: String?
) {

    init {
        type = createdAt?.let { "CREATE" } ?: modifiedAt?.let { "MODIFY" } ?: cancelledAt?.let { "CANCEL" } ?: "UNKNOWN"
    }
}

