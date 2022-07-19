package com.asmaa.weather.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.asmaa.weather.R
import com.asmaa.weather.data.models.Country
import kotlinx.coroutines.NonDisposableHandle.parent


class CountryAdapter(private val context: Context, private val countries : MutableList<Country>): RecyclerView.Adapter<CountryAdapter.CountryHolder>() {


    var mutableLiveData:MutableLiveData<String> = MutableLiveData()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val  item = LayoutInflater.from(parent.context).inflate(R.layout.item_country,parent,false)
        return CountryHolder(item)
    }




    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.tvCountry.text =countries[position].name
        holder.tvCountry.setOnClickListener{
            mutableLiveData.postValue(countries[position].name)
            clear()

        }
        if(position==countries.size-1){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.expanded_view, null);

        }


    }
    override fun getItemCount(): Int {
         return countries.size
    }

    fun clear() {
       countries.clear()
       notifyDataSetChanged()
    }

    class CountryHolder(item:View) : RecyclerView.ViewHolder(item){
        val tvCountry: TextView = item.findViewById(R.id.tv_country_name)
    }

}