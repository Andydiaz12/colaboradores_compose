package com.upaep.upaeppersonal.view.base.navigation

import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem

sealed class Routes(val routes: String, val id: String = "") {
    data object LoginScreen : Routes("loginScreen")
    data object HomeScreen : Routes("homeScreen")
    data object MenuScreen : Routes("menuScreen")
    data object LibraryScreen : Routes("libraryScreen", id = "43")
    data object BooksListScreen : Routes("booksListScreen")
    data object CredentialScreen : Routes("credentialScreen", id = "35")
    data object QrScreen : Routes("qrScreen")
    data object AssistanceScreen : Routes("assistanceScreen", id = "32")
    data object AssistanceClassScreen : Routes("assistanceClassScreen")
    data object BenefitsScreen : Routes("benefitsScreen", id = "44")
    data object ParkUScreen : Routes("parkUScreen", id = "46")
    data object ScheduleScreen : Routes("scheduleScreen", id = "34")
    data object TokenScreen : Routes("tokenScreen", id = "33")
    data object MailboxScreen : Routes("mailboxScreen", id = "42")
    data object DirectoryScreen : Routes("directoryScreen", id = "37")
    data object CalendarScreen : Routes("calendarScreen", id = "41")
    data object UpressScreen : Routes("upressScreen", id = "39")
    data object EventsScreen : Routes("eventsScreen", id = "45")
    data object UpressDescScreen : Routes("upressDescScreen")
    data object PrivacyPolicyScreen : Routes("privacyPolicyScreen")
    data object BenefitsByCategoryScreen : Routes("benefitsByCategoryScreen")
    data object PfiScreen : Routes("pfiRoutesScreen", id = "36")
    data object PfiByCategoryScreen : Routes("pfiByCategory")
    data object DailyMailScreen : Routes("dailyMailScreen", id = "38")
    data object DailyMailByCategoryScreen : Routes("dailyMailByCategoryScreen")

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
