package com.upaep.upaeppersonal.viewmodel.features.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.entities.features.calendar.CalendarConfiguration
import com.upaep.upaeppersonal.model.entities.features.calendar.GoogleEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Month
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlin.math.ceil

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {

    private val _calendarConfiguration = MutableLiveData(getCalendar())
    val calendarConfiguration: LiveData<CalendarConfiguration> = _calendarConfiguration

    private val _daysWithEvents = MutableLiveData(MutableList(35) { 0 })
    val daysWithEvents: LiveData<MutableList<Int>> = _daysWithEvents

    private val actualMonth: Int = _calendarConfiguration.value!!.monthInInt
    private val actualDay: Int = Calendar.getInstance().get(Calendar.DATE)
    private val actualYear: Int = Calendar.getInstance().get(Calendar.YEAR)
    private var currentRequest: Job? = null

    init {
        fillCalendarData(firstTime = true)
    }

    fun getIfEventsInDay(selectedDay: Int): Boolean = _daysWithEvents.value!![selectedDay] == 0
    fun getActualDay(): Int = actualDay
    fun getActualYear(): Int = actualYear
    fun getActualMonth(): Int = actualMonth
    fun getOtherMonth(movement: String) {
        val operator = when (movement) {
            "prev" -> -1
            "next" -> 1
            else -> 0
        }
        _calendarConfiguration.value!!.monthInInt += operator
        if (_calendarConfiguration.value!!.monthInInt < 0) {
            _calendarConfiguration.value!!.monthInInt = 11
            _calendarConfiguration.value!!.yearInInt--
        } else if (_calendarConfiguration.value!!.monthInInt > 11) {
            _calendarConfiguration.value!!.monthInInt = 0
            _calendarConfiguration.value!!.yearInInt++
        }
        _calendarConfiguration.value = getCalendar(
            customMonth = _calendarConfiguration.value!!.monthInInt,
            customYear = _calendarConfiguration.value!!.yearInInt
        )
        fillCalendarData()
    }

    fun getCalendarConfiguration(): CalendarConfiguration {
        return _calendarConfiguration.value!!
    }

    fun dayWithinRange(selectedDay: Int, event: GoogleEvents): Boolean {
        val startDate = event.startDate?.split(" ")!![0].split("-")[2].toInt()
        val endDate = event.endDate?.split(" ")!![0].split("-")[2].toInt()
        return selectedDay in startDate..endDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fillCalendarData(firstTime: Boolean = false) {
//        currentRequest?.cancel()
//        if(firstTime) _loadedData.value = false
//        currentRequest = viewModelScope.launch {
//            val student = userPreferences.getSelectedStudent.first()
//            _daysWithEvents.value = MutableList(35) { 0 }
//            val calendarMonth =
//                if (calendarConfiguration.value?.monthInInt!! + 1 < 10) "0${calendarConfiguration.value?.monthInInt!! + 1}" else "${calendarConfiguration.value?.monthInInt!! + 1}"
//            val dateRange = CalendarGetEvents(
//                startDateTime = "$actualYear-${calendarConfiguration.value?.monthInInt!! + 1}-01",
//                endDateTime = "$actualYear-${calendarMonth}-${lastDayOfMonth()}",
//                calendar = when(student.school.lowercase()) {
//                    "preescolar" -> 20
//                    "primaria" -> 21
//                    else -> 22
//                }
//                //20 es prescolar
//                //21 primaria
//                //22 secundaria
//            )
//            Log.i("studentSelected", student.school)
//            Log.i("studentSelected", dateRange.toString())
//            val events = calendarRepository.getCalendarEvents(dateRange = dateRange)
//            events.data?.forEach() { event ->
//                event.startDateDesc = getParsedDate(event.startDate!!)
//                event.endDateDesc = getParsedDate(event.endDate!!)
//                val startDateSplit = event.startDate!!.split(" ")[0].split("-")
//                val endDateSplit = event.endDate!!.split(" ")[0].split("-")
//                val startDate = startDateSplit[2].toInt()
//                val endDate = endDateSplit[2].toInt()
//                for (i in startDate..endDate) {
//                    _daysWithEvents.value!![i] = 1
//                }
//            }
//            _daysWithEvents.value = _daysWithEvents.value
//            _calendarEvents.value = events.data ?: emptyList()
//            if(firstTime) _loadedData.value = true
//            errorDialog.value = events.error
//            errorDialogMessage.value = events.message
//        }
//
//        if(firstTime) {
//            viewModelScope.launch {
//                _minToVisible.value = false
//                delay(500L)
//                _minToVisible.value = true
//            }
//        }
        val simulatedResponse: List<GoogleEvents> = simulatedResponse()
        simulatedResponse.forEach() { event ->
            event.startDateDesc = getParsedDate(event.startDate!!)
            event.endDateDesc = getParsedDate(event.endDate!!)
            val startDateSplit = event.startDate!!.split(" ")[0].split("-")
            val endDateSplit = event.endDate!!.split(" ")[0].split("-")
            val startDate = startDateSplit[2].toInt()
            val endDate = endDateSplit[2].toInt()
            for (i in startDate..endDate) {
                _daysWithEvents.value!![i] = 1
            }
        }
    }

    private fun getCalendar(
        customMonth: Int? = null,
        customYear: Int? = null
    ): CalendarConfiguration {
        val calendarInstance = Calendar.getInstance()
        val year = customYear ?: calendarInstance.get(Calendar.YEAR)
        val month = customMonth ?: calendarInstance.get(Calendar.MONTH)
        val monthName = Month.of(month + 1).getDisplayName(TextStyle.FULL, Locale("es", "ES"))
            .replaceFirstChar { character -> character.uppercase() }
        val cal = calendarInstance.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val firstDayOfMonth = cal.get(Calendar.DAY_OF_WEEK)
        val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val startDay = Calendar.SUNDAY
        val numRows = ceil((firstDayOfMonth + daysInMonth - 1) / 7.0).toInt()
        val daysMatrix = Array(numRows) { IntArray(7) }
        cal.set(Calendar.DAY_OF_WEEK, startDay)
        for (row in 0 until numRows) {
            for (col in 0 until 7) {
                if (cal.get(Calendar.MONTH) == month) {
                    daysMatrix[row][col] = cal.get(Calendar.DAY_OF_MONTH)
                }
                cal.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return CalendarConfiguration(
            numRows = numRows,
            startDay = startDay,
            daysMatrix = daysMatrix,
            monthNumber = month,
            monthName = monthName,
            monthInInt = month,
            yearInInt = year
        )
    }

    private fun getParsedDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val outputFormat = SimpleDateFormat("d MMMM", Locale("es", "MX"))
        val parsedDate = inputFormat.parse(date)
        return outputFormat.format(parsedDate!!)
    }

    fun simulatedResponse() : List<GoogleEvents>{
        return listOf(
            GoogleEvents(summary = "Prueba 1", startDate = "2023-02-02 00:00:00", endDate = "2023-02-03 00:00:00"),
            GoogleEvents(summary = "Prueba 2", startDate = "2023-02-02 00:00:00", endDate = "2023-02-04 00:00:00"),
        )
    }
}