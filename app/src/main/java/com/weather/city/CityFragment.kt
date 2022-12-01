package com.weather.city

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.weather.App
import com.weather.Database
import com.weather.R

class CityFragment : Fragment() {
    private lateinit var cities : MutableList<City>
    private lateinit var  database : Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = App.database
        cities = mutableListOf()
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        return view
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_city, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_create_city -> {
                showCreateCityDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun showCreateCityDialog() {
        val createCityFragment = CreateCityDialogFragment()
        createCityFragment.listener = object: CreateCityDialogFragment.CreateCityDialogListener{
            override fun OnDialogPositiveClick(cityName: String) {
                saveCity(City(cityName))
            }
            override fun OnDialogNegativeClick() { }
        }
        createCityFragment.show(fragmentManager!!, "CreateCityDialogFragment")
    }

    private fun saveCity(city: City) {
        if(database.createCity(city)){
            cities.add(city)
            Toast.makeText(context, "Ville '${city.name}' créée", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Impossible de créér la ville '${city.name}'", Toast.LENGTH_SHORT).show()
        }
    }
}