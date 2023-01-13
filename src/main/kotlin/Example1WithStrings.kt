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
    reservations.forEach {
        it.type = it.createdAt?.let { "CREATE" }
            ?: it.modifiedAt?.let { "MODIFY" }
            ?: it.cancelledAt?.let { "CANCEL" }
    }

    println("creation     ==> $creation")
    println("modification ==> $modification")
    println("cancellation ==> $cancellation")

    reservations.forEach {
        val handled = when (it.type) {
            "CREATE" -> doSomething(it)
            "MODIFY" -> doSomething(it)
            "CANCEL" -> doSomething(it)
            null -> throw IllegalStateException()
            else -> throw IllegalStateException()
        }
    }
}
