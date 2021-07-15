package fr.oogiboogi66.mygarden.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.oogiboogi66.mygarden.MainActivity
import fr.oogiboogi66.mygarden.PlantModel
import fr.oogiboogi66.mygarden.PlantRepository.Singleton.plantList
import fr.oogiboogi66.mygarden.R
import fr.oogiboogi66.mygarden.adapter.PlantAdapter
import fr.oogiboogi66.mygarden.adapter.PlantItemDecoration

class HomeFragment (
    private val context: MainActivity
        ): Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)



        //recupérer le recycle view
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(context, plantList.filter{!it.liked},R.layout.item_horizontal_plant)

        // récupére le second recycler view
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(context, plantList,R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }
}