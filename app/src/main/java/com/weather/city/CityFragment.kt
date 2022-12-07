package com.weather.city

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.App
import com.weather.Database
import com.weather.R
import com.weather.utils.toast

class CityFragment : Fragment(), CityAdapter.CityItemlistener {
    interface CityFragmentListener{
        fun onCitySelected(city: City)
    }
    var listener : CityFragmentListener? = null
    private lateinit var cities : MutableList<City>
    private lateinit var  database : Database
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter
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
        recyclerView = view.findViewById(R.id.cities_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cities = database.getAllCities()
        adapter = CityAdapter(cities, this)
        recyclerView.adapter = adapter
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
    @SuppressLint("UseRequireInsteadOfGet")
    private fun showDelteCityDialog(city: City) {
        val deleteCityFragment = DeleteCityDialogFragment.newInstance(city.name)
        deleteCityFragment.listener = object : DeleteCityDialogFragment.DeleteCityDialogListener{
            override fun onDialogPosiiveClick() {
                deleteCity(city)
            }

            override fun onDialogNegativeClick() {
            }

        }
        deleteCityFragment.show(fragmentManager!!, "DeleteCityDialogFragment")
    }
    private fun saveCity(city: City) {
        if(database.createCity(city)){
            cities.add(city)
            adapter.notifyDataSetChanged()
            context?.toast("Ville '${city.name}' créée")
        }else{
            context?.toast("Impossible de créér la ville '${city.name}'")
        }
    }
    private fun deleteCity(city: City) {
        if(database.deleteCity(city)){
            cities.remove(city)
            adapter.notifyDataSetChanged()
            context?.toast("Ville '${city.name}' supprimée")
        }else{
            context?.toast("Impossible de supprimer la ville '${city.name}'")
        }
    }
    override fun onCitySelected(city: City) {
        listener?.onCitySelected(city)
    }
    override fun onCityDeleted(city: City) {
        showDelteCityDialog(city)
    }
}