import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.time.ZonedDateTime

data class ReservationWithEnums(
    val resId: Int,
    val createdAt: ZonedDateTime?,
    val modifiedAt: ZonedDateTime?,
    val cancelledAt: ZonedDateTime?,
    var type: Type?
) {
    enum class Type {
        CREATE, MODIFY, CANCEL
    }
}

fun main() {

    val creation = objectMapper.readValue(RES_CREATE, jacksonTypeRef<ReservationWithEnums>())
    val modification = objectMapper.readValue(RES_MODIFY, jacksonTypeRef<ReservationWithEnums>())
    val cancellation = objectMapper.readValue(RES_CANCEL, jacksonTypeRef<ReservationWithEnums>())

    val reservations = listOf(creation, modification, cancellation)
    reservations.forEach {
        it.type = it.createdAt?.let { ReservationWithEnums.Type.CREATE }
            ?: it.modifiedAt?.let { ReservationWithEnums.Type.MODIFY }
                ?: it.cancelledAt?.let { ReservationWithEnums.Type.CANCEL }
                ?: null
    }

    println("creation     ==> $creation")
    println("modification ==> $modification")
    println("cancellation ==> $cancellation")

    reservations.forEach {
        val handled = when (it.type) {
            ReservationWithEnums.Type.CREATE -> doSomething(it)
            ReservationWithEnums.Type.MODIFY -> doSomething(it)
            ReservationWithEnums.Type.CANCEL -> doSomething(it)
            null -> throw IllegalStateException("type not handled")
        }
    }
}
