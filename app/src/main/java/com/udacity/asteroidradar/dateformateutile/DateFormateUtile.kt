package com.udacity.asteroidradar.dateformateutile

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Constants.DEFAULT_END_DATE_DAYS
import java.text.SimpleDateFormat
import java.util.*

fun getEndDate(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, DEFAULT_END_DATE_DAYS)
    return getDateWithFormate(Constants.API_QUERY_DATE_FORMAT,calendar.time)
}

fun getCurrentDay(): String {
    val calendar = Calendar.getInstance()
    return getDateWithFormate(Constants.API_QUERY_DATE_FORMAT,calendar.time)
}
private fun getDateWithFormate(formateDate:String,date: Date): String {
    val dateFormat = SimpleDateFormat(formateDate, Locale.getDefault())
    return dateFormat.format(date)
}