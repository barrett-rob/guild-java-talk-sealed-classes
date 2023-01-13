import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.time.ZonedDateTime

sealed class ReservationWithSealedClasses {
    data class Create(val resId: Int, val createdAt: ZonedDateTime?) : ReservationWithSealedClasses()
    data class Modify(val resId: Int, val modifiedAt: ZonedDateTime?) : ReservationWithSealedClasses()
    data class Cancel(val resId: Int, val cancelledAt: ZonedDateTime?) : ReservationWithSealedClasses()
}

fun main() {

    val creation = objectMapper.readValue(RES_CREATE, jacksonTypeRef<ReservationWithSealedClasses.Create>())
    val modification = objectMapper.readValue(RES_MODIFY, jacksonTypeRef<ReservationWithSealedClasses.Modify>())
    val cancellation = objectMapper.readValue(RES_CANCEL, jacksonTypeRef<ReservationWithSealedClasses.Cancel>())

    val reservations = listOf(creation, modification, cancellation)

    reservations.forEach {
        val handled = when (it) {
            is ReservationWithSealedClasses.Create -> println("creation     ==> $it")
            is ReservationWithSealedClasses.Modify -> println("modification ==> $it")
            is ReservationWithSealedClasses.Cancel -> println("cancellation ==> $it")
        }
    }
}
