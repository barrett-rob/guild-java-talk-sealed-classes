import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.time.ZonedDateTime

data class ReservationWithStrings(
    val resId: Int,
    val createdAt: ZonedDateTime?,
    val modifiedAt: ZonedDateTime?,
    val cancelledAt: ZonedDateTime?,
    var type: String?
)

fun main() {

    val creation = objectMapper.readValue(RES_CREATE, jacksonTypeRef<ReservationWithStrings>())
    val modification = objectMapper.readValue(RES_MODIFY, jacksonTypeRef<ReservationWithStrings>())
    val cancellation = objectMapper.readValue(RES_CANCEL, jacksonTypeRef<ReservationWithStrings>())

    val reservations = listOf(creation, modification, cancellation)

    // assign types
    reservations.forEach {
        it.type = it.createdAt?.let { "CREATE" }
            ?: it.modifiedAt?.let { "MODIFY" }
            ?: it.cancelledAt?.let { "CANCEL" }
    }

    reservations.forEach {
        val handled = when (it.type!!) {
            "CREATE" -> println("creation     ==> $it")
            "MODIFY" -> println("modification ==> $it")
            "CANCEL" -> println("cancellation ==> $it")
            else -> throw IllegalStateException()
        }
    }
}
