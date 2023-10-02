package com.upaep.upaeppersonal.model.entities.features.assistance

data class RollcallStudent(
    var id: Int? = null, //PrimaryKey
    var groupId: String? = null,
    var courseId: String? = null,
    var name: String? = null,
    var attendIcon: String? = null,
    var attendanceId: String? = null,
    var dateRecord: String? = null,
    var courseHours: String? = null,
    var absenceHours: Int? = null,
    var visible: Boolean,
    var weekType: String? = null
)
