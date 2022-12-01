package com.weather.city

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.App
import com.weather.Database
import com.weather.R

class CityFragment : Fragment(), CityAdapter.CityItemlistener {
    private lateinit var cities : MutableList<City>
    private lateinit var  database : Database
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = App.database
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        recyclerView = view.findViewById(R.id.cities_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CityAdapter(cities, this)
        cities = database.getAllCities()
        recyclerView.adapter = adapter
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_city, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_create_city -> {
                showCreateDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun showCreateDialog() {
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
            adapter.notifyDataSetChanged()
        }else{
            Toast.makeText(context, "Impossible de créér la ville '${city.name}'", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCitySelected(city: City) {
    }

    override fun onCityDeleted(city: City) {
    }
}