package com.asmaa.weather.presentation.ui

import android.annotation.SuppressLint
import android.location.*
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.View.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.asmaa.weather.R
import com.asmaa.weather.data.models.Country
import com.asmaa.weather.data.models.WeatherForecastResponse
import com.asmaa.weather.databinding.WeatherHomeActivityBinding
import com.asmaa.weather.presentation.Utils.formatDate
import com.asmaa.weather.presentation.Utils.formatTime
import com.asmaa.weather.presentation.Utils.getDate
import com.asmaa.weather.presentation.adapters.CountryAdapter
import com.asmaa.weather.presentation.adapters.ForecastAdaptor
import com.asmaa.weather.presentation.viewModel.GetWeatherViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var adaptorForecast: ForecastAdaptor
    lateinit var adaptorCountries: CountryAdapter
    var countriesList: MutableList<Country>? = null

    private val viewDataBinding: WeatherHomeActivityBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.weather_home_activity)
    }


    @Inject
    lateinit var viewModel: GetWeatherViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_home_activity)
        init()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun init() {
        hideStatusBar()
        getLocation()
        onClickItems()
        viewDataBinding.layoutSearchView.containerSearch.setPadding(0, 0, 0, 20)
        mutableLocationName.observe(this, androidx.lifecycle.Observer {
            getData(it)
        })


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(query: String) {
        lifecycleScope.launch {
            closeSearchView()
            bindData(viewModel.getWeather(query))
            viewDataBinding.ivSearch.visibility = VISIBLE
            viewDataBinding.progressBarCyclic.visibility = INVISIBLE
            viewDataBinding.lnTemp.visibility = VISIBLE
            viewDataBinding.lnNoGps.root.visibility = GONE


        }

    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    fun bindData(response: WeatherForecastResponse?) {

        viewDataBinding.tvCountry.text = response?.location?.name
        viewDataBinding.tvTime.text = formatTime(getTime(response?.location?.localtime).time)
        viewDataBinding.tvDate.text = formatDate(
            getDate(getTime(response?.location?.localtime).date),
            getTime(response?.location?.localtime).date
        )
        (response?.current?.temp_f.toString() + getString(R.string.fehrentit)).also {
            viewDataBinding.tvTempratureDegree.text = it
        }
        (getString(R.string.it_is) + response?.current?.condition?.text + getString(R.string.day)).also {
            viewDataBinding.tvTempratureStatus.text = it
        }
        (response?.current?.wind_mph.toString() + getString(R.string.mph)).also {
            viewDataBinding.tvWind.text = it
        }
        (response?.current?.humidity.toString() + getString(R.string.percentage)).also {
            viewDataBinding.tvHumidity.text = it
        }
        Picasso.get().load(getString(R.string.https) + response?.current?.condition?.icon)
            .into(viewDataBinding.icWeatherStatus)

        setupForecastAdapter(response)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupSearch(countries: MutableList<Country>?) {
        countriesList = countries

        if (countries != null) {
            viewDataBinding.layoutSearchView.rvCountries.visibility = VISIBLE
            if (countries.size != 0){
                viewDataBinding.layoutSearchView.containerSearch.setPadding(0, 0, 0, 0)
                viewDataBinding.layoutSearchView.clExpandLess.visibility = VISIBLE

            }
            adaptorCountries = CountryAdapter(this, countries)
            viewDataBinding.layoutSearchView.rvCountries.adapter = adaptorCountries
            adaptorCountries.mutableLiveData.observe(this, androidx.lifecycle.Observer {

                getData(it)
            })
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        if (viewDataBinding.progressBarCyclic.isShown && !isLocationEnabled()) {
            viewDataBinding.progressBarCyclic.visibility = View.GONE
            viewDataBinding.lnNoGps.root.visibility = View.VISIBLE
            viewDataBinding.lnNoGps.setting.setOnClickListener {
                goToSetting()
            }
            viewDataBinding.lnNoGps.ivRefresh.setOnClickListener {
                getLocation()
            }

        } else {
            locationEnabled()

        }
    }


    private fun closeSearchView() {
        viewDataBinding.layoutSearchView.root.visibility = GONE
        viewDataBinding.ivSearch.visibility = VISIBLE
        clearDataOnSearch()

    }

    private fun clearDataOnSearch() {
        viewDataBinding.layoutSearchView.etSearch.text.clear()
        viewDataBinding.layoutSearchView.clExpandLess.visibility = GONE
        viewDataBinding.layoutSearchView.tvClose.visibility = GONE
        if (countriesList != null) {
            adaptorCountries.clear()
        }
    }

    private fun setupForecastAdapter(response: WeatherForecastResponse?) {
        adaptorForecast = ForecastAdaptor(response?.forecast!!.forecastday)
        viewDataBinding.rvForecast.adapter = adaptorForecast
    }

    private fun onClickItems() {




        with(this.viewDataBinding) {
            layoutSearchView.clExpandLess.setOnClickListener {
                viewDataBinding.layoutSearchView.containerSearch.setPadding(0, 0, 0, 0)
                clearDataOnSearch()
            }
        }
        with(this.viewDataBinding) {
            layoutSearchView.tvClose.setOnClickListener {
                clearDataOnSearch()

            }
        }

        with(this.viewDataBinding) {
            layoutSearchView.ivBack.setOnClickListener {
                closeSearchView()

            }
        }

        with(this.viewDataBinding) {
            ivSearch.setOnClickListener {
                viewDataBinding.layoutSearchView.root.visibility = VISIBLE
            }
        }
        with(this.viewDataBinding) {
            layoutSearchView.etSearch.addTextChangedListener(object : TextWatcher {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun afterTextChanged(s: Editable?) {

                    lifecycleScope.launch {

                        setupSearch(viewModel.getCountries(s.toString()))
                        if(countriesList!=null &&countriesList!!.size==0)
                            viewDataBinding.layoutSearchView.clExpandLess.visibility = GONE
                    }

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if(s.toString().isNotEmpty()){
                        viewDataBinding.layoutSearchView.tvClose.visibility = VISIBLE
                        if(countriesList!=null &&countriesList!!.size==0)
                            viewDataBinding.layoutSearchView.clExpandLess.visibility = GONE
                    }

                }
            })
        }
    }


}




