package ragalik.baraxolka.paging_feed

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {

    companion object {
        private var instance: DateTimeUtils? = null
        private fun createInstance() {
            if (instance == null) {
                instance = DateTimeUtils()
            }
        }
        fun getInstance(): DateTimeUtils? {
            if (instance == null) createInstance()
            return instance
        }
    }

    fun getNormalizedDatetime (datetime: String) : String {
        return when {
            isToday(datetime) -> {
                "Сегодня, ${datetime.substring(11, 16)}"
            }
            isYesterday(datetime) -> {
                "Вчера, ${datetime.substring(11, 16)} "
            }
            else -> {
                "${datetime.substring(8, 10)} ${getMonthName(datetime.substring(5, 7))}, ${datetime.substring(11, 16)}"
            }
        }
    }

    private fun getMonthName (month: String) : String {
        return when (month) {
            "01" -> "Янв"
            "02" -> "Фев"
            "03" -> "Мар"
            "04" -> "Апр"
            "05" -> "Май"
            "06" -> "Июн"
            "07" -> "Июл"
            "08" -> "Авг"
            "09" -> "Сен"
            "10" -> "Окт"
            "11" -> "Нояб"
            "12" -> "Дек"
            else -> ""
        }
    }

    private fun isToday (date: String) : Boolean {
        val sdf = SimpleDateFormat("dd/MM hh:mm")
        sdf.timeZone = TimeZone.getTimeZone("GMT+3")
        val current = sdf.format(Date())

        val currentDay = current.substring(0, 2).toInt()
        val currentMonth = current.substring(3, 5).toInt()
        val adDay = date.substring(8, 10).toInt()
        val adMonth = date.substring(5, 7).toInt()

        if (currentDay - adDay == 0 && currentMonth - adMonth == 0) {
            return true
        }
        return false
    }

    private fun isYesterday (date: String) : Boolean {
        val sdf = SimpleDateFormat("dd/MM hh:mm")
        sdf.timeZone = TimeZone.getTimeZone("GMT+3")
        val current = sdf.format(Date())

        val currentDay = current.substring(0, 2).toInt()
        val currentMonth = current.substring(3, 5).toInt()
        val adDay = date.substring(8, 10).toInt()
        val adMonth = date.substring(5, 7).toInt()

        if (currentDay - adDay == 1 && currentMonth - adMonth == 0) {
            return true
        } else if (currentMonth == 1 && currentDay == 1 && adMonth == 12 && adDay == 31) {
            return true
        } else if ((currentDay == 1 && (currentMonth == 5 || currentMonth == 7 || currentMonth == 10 || currentMonth == 12) && adDay == 30) ||
                (currentDay == 1 && (currentMonth == 2 || currentMonth == 4 || currentMonth == 6 || currentMonth in 8..9 || currentMonth == 11) && adDay == 31)) {
            return true
        } else if (currentDay == 1 && currentMonth == 3 && adDay == 28) {
            return true
        }
        return false
    }
}