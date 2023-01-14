sealed class GetUserDetailsState {
    object NoUserDetails: GetUserDetailsState()
    data class UserDetails(val userDetails: List<String>): GetUserDetailsState()
    data class MissingDataElement(val elementName: String): GetUserDetailsState()
    data class UserTooYoung(val age: Int): GetUserDetailsState()
    data class MaskingError(val e: Exception): GetUserDetailsState()
}

fun getUserDetailsWithSealedClassReturnStates(data: Map<String, String>) :GetUserDetailsState {
    if (!data.containsKey("Name")) {
        return GetUserDetailsState.NoUserDetails
    } else {
        val userDetails = arrayOf("Name", "Age", "Email").map {
            val x = data[it]
            if (x == null) {
                return GetUserDetailsState.MissingDataElement(it)
            }
            if (it == "Age" && x.toInt() < 21) {
                return GetUserDetailsState.UserTooYoung(x.toInt())
            }
            return@map x
        }
        val maskedUserDetails = try{
            doSomeNetworkCallToMaskingService(userDetails)
        } catch (e: Exception) {
            return GetUserDetailsState.MaskingError(e)
        }
        return GetUserDetailsState.UserDetails(maskedUserDetails)
    }
}

fun main() {

    val data = mapOf<String, String>()

    val userDetails = getUserDetailsWithSealedClassReturnStates(data)

    when (userDetails) {
    }

    // can you prove that this handle all possible return states from the function?
}
