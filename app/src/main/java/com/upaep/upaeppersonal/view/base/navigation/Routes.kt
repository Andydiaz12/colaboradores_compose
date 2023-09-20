package com.upaep.upaeppersonal.view.base.navigation

sealed class Routes(val routes: String, val id: String = "") {
    data object LoginScreen : Routes("loginScreen")
    data object HomeScreen : Routes("homeScreen")
    data object MenuScreen : Routes("menuScreen")
    data object LibraryScreen : Routes("libraryScreen", id = "9")
    data object CredentialScreen : Routes("credentialScreen", id = "1")
    data object QrScreen : Routes("qrScreen")
    data object AssistanceScreen : Routes("assistanceScreen", id = "13")
    data object BenefitsScreen : Routes("benefitsScreen", id = "10")
    data object ParkUScreen : Routes("parkUScreen", id = "12")
    data object ScheduleScreen : Routes("scheduleScreen", id = "15")
    data object TokenScreen : Routes("tokenScreen", id = "14")

    companion object {
        fun getById(id: String): Routes? {
            return values().find { element -> element.id == id }
        }

        private fun values(): List<Routes> {
            val subclasses = Routes::class.sealedSubclasses
            return subclasses.map { subclass ->
                subclass.objectInstance as Routes
            }
        }
    }
}
