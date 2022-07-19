package com.asmaa.weather.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.asmaa.weather.R
import com.asmaa.weather.data.models.ForecastDay
import com.asmaa.weather.presentation.Utils
import com.squareup.picasso.Picasso

class ForecastAdaptor (private val forecastDay: List<ForecastDay>): RecyclerView.Adapter<ForecastAdaptor.ForecastHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        val  item = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast_day,parent,false)
        return ForecastHolder(item)
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
     holder.tvMaxTemp.text = forecastDay[position].day.maxtemp_f+ " /"
        Picasso.get().load("https:" + forecastDay[position].day.condition.icon)
            .into(holder.ivForecast)
     holder.tvMinTemp.text = forecastDay[position].day.mintemp_f + ""
        when (position){
            0 -> holder.tvDay.text = "Today"
            1 -> holder.tvDay.text = "Tomorrow"
            2 -> {
                var day =Utils.getDate(forecastDay[2].date).day.toString()
                holder.tvDay.text = day.substring(0, 1) + day.substring(1)
                    .lowercase()
            }

        }


    }
    override fun getItemCount(): Int {
        return forecastDay.size
    }

    class ForecastHolder(item: View) : RecyclerView.ViewHolder(item){
        val tvMaxTemp: TextView = item.findViewById(R.id.tv_max_temperature_degree)
        val tvMinTemp:TextView = item.findViewById(R.id.tv_min_temperature_degree)
        val tvDay:TextView = item.findViewById(R.id.tvDay)
        val ivForecast: ImageView = item.findViewById(R.id.iv_forecast)
    }

}