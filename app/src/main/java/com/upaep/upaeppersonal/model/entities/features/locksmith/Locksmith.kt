package com.upaep.upaeppersonal.model.entities.features.locksmith

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity
@Serializable
@TypeConverters(IDDKeychainConverter::class)
data class Locksmith(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @Embedded(prefix = "app_collaborators")
    @SerializedName("APP_COLLABORATORS")
    var appCollaboratorsKeychain: IDDKeychain? = null,

    @Embedded(prefix = "collaborator_data_general")
    @SerializedName("COLLABORATOR_DATA_GENERAL")
    var collaboratorDataGeneral: IDDKeychain? = null,

    @Embedded(prefix = "collaborator_login_general")
    @SerializedName("COLLABORATOR_LOGIN_GENERAL")
    var collaboratorLoginKeychain: IDDKeychain? = null,

    @Embedded(prefix = "events")
    @SerializedName("EVENTS")
    var eventsKeychain: IDDKeychain? = null,

    @Embedded(prefix = "general")
    @SerializedName("GENERAL")
    var generalKeychain: IDDKeychain? = null,

    @Embedded(prefix = "general_gospel")
    @SerializedName("GENERAL_GOSPEL")
    var generalGospelKeychain: IDDKeychain? = null,

    @Embedded(prefix = "general_library")
    @SerializedName("GENERAL_LIBRARY")
    var generalLibraryKeychain: IDDKeychain? = null,

    @Embedded(prefix = "general_movil")
    @SerializedName("GENERAL_MOVIL")
    var generalMovilKeychain: IDDKeychain? = null,

    @Embedded(prefix = "api_general_mailofday")
    @SerializedName("API_GENERAL_MAILOFDAY")
    var apiGeneralMailofday: IDDKeychain? = null,

    @Embedded(prefix = "api_general_calendars")
    @SerializedName("API_GENERAL_CALENDARS")
    var apiGeneralCalendars: IDDKeychain? = null,

    @Embedded(prefix = "access")
    @SerializedName("ACCESS")
    var accessKeychain: IDDKeychain? = null,

    @Embedded(prefix = "api_collaborators_schedules")
    @SerializedName("API_COLLABORATORS_SCHEDULES")
    var apiCollaboratorsSchedulesKeychain: IDDKeychain? = null,

    @Embedded(prefix = "api_collaborators_pfi")
    @SerializedName("API_COLLABORATORS_PFI")
    var apiCollaboratorsPfiKeychain: IDDKeychain? = null,

    @Embedded(prefix = "api_collaborators_token")
    @SerializedName("API_COLLABORATORS_TOKEN")
    var apiCollaboratorsTokenKeychain: IDDKeychain? = null,

    @Embedded(prefix = "api_collaborators_rollcall")
    @SerializedName("API_COLLABORATORS_ROLLCALL")
    var apiCollaboratorsRollcallKeychain: IDDKeychain? = null,

    @Embedded(prefix = "api_sexuality")
    @SerializedName("API_SEXUALITY")
    var apiPushNotifications: IDDKeychain? = null,

    @Embedded(prefix = "api_menu")
    @SerializedName("GETMENU_API")
    var apiGetMenu: IDDKeychain? = null
)