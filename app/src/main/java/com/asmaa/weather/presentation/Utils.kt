package com.asmaa.weather.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import com.asmaa.weather.data.models.Date
import java.time.LocalDate

object  Utils {

    lateinit var date : Date

    fun formatTime(time:String):String{
        var timeAfterFormat=  time.substringBefore(":").toInt()
        return if(timeAfterFormat>12){
            timeAfterFormat -= 12
            timeAfterFormat.toString()+":"+ time.substringAfter(":")+" PM"
        } else{
            timeAfterFormat.toString()+":"+ time.substringAfter(":")+" AM"
        }


    }




    @RequiresApi(Build.VERSION_CODES.O)
     fun getDate(dateFormat: String): Date {

        val day= LocalDate.of(
            dateFormat.substringBefore("-").toInt(),
            dateFormat.substringAfter("-").take(2).toInt(),
            dateFormat.substringAfterLast("-").toInt()
        ).dayOfWeek

        val month= LocalDate.of(
            dateFormat.substringBefore("-").toInt(),
            dateFormat.substringAfter("-").take(2).toInt(),
            dateFormat.substringAfterLast("-").toInt()
        ).month

        return Date(day,month);
    }


     fun formatDate(date: Date, dateFormat: String): String {

        return date.day.toString().substring(0, 1) + date.day.toString().substring(1)
            .lowercase() + ", " + dateFormat.substringAfterLast("-")
            .toInt() + " " + date.month.toString().substring(0, 1) + date.month.toString()
            .substring(1, 3).lowercase() + " " + dateFormat.substringBefore("-").toInt()
    }


}