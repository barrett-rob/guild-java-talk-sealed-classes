fun doSomeNetworkCallToMaskingService(userDetails: List<String>): List<String> {
    TODO("Not yet implemented")
}

fun getUserDetails(data: Map<String, String>) :List<String>? {
    if (!data.containsKey("Name")) {
        return null
    } else {
        val userDetails = arrayOf("Name", "Age", "Email").map {
            val x = data[it]
            if (x == null) {
                throw IllegalArgumentException("data does not contain element $it")
            }
            if (it == "Age" && x.toInt() < 21) {
                throw IllegalStateException("User too young $x")
            }
            return@map x
        }
        val maskedUserDetails = doSomeNetworkCallToMaskingService(userDetails)
        return maskedUserDetails
    }
}

fun main() {

    val data = mapOf<String, String>()

    val userDetails = try {
        getUserDetails(data)
    } catch (iae:IllegalArgumentException) {
        // which data element is missing?
    } catch (ise: IllegalStateException) {
        // what is the user's age
    } catch (e: Exception) {
        // did the network call go wrong or something else?
    }

    if (userDetails == null) {
        // ignore
    } else {
        // continue processing
    }

    // can you prove that these 15 lines handle all possible return states from the function?
    // what happens if you throw a different type of exception from getUserDetails? this will still compile
}
