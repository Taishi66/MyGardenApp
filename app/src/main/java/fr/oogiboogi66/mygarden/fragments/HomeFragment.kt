package fr.oogiboogi66.mygarden.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.oogiboogi66.mygarden.MainActivity
import fr.oogiboogi66.mygarden.PlantModel
import fr.oogiboogi66.mygarden.R
import fr.oogiboogi66.mygarden.adapter.PlantAdapter
import fr.oogiboogi66.mygarden.adapter.PlantItemDecoration

class HomeFragment (
    private val context: MainActivity
        ): Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        //creer une liste qui stocker les plantes
        val plantList = arrayListOf<PlantModel>()

        //enregistrer une première plante dans notre liste (pissenlit)
        plantList.add(PlantModel(
            "Pissenlit",
            "jaune soleil",
            "https://cdn.pixabay.com/photo/2018/03/29/02/42/dandelion-3271145_960_720.jpg",
            false
        ))

        //enregistrer une seconde plante dans notre liste (rose)
        plantList.add(PlantModel(
            "Rose",
            "ça pique",
            "https://cdn.pixabay.com/photo/2013/07/21/13/00/rose-165819_960_720.jpg",
            false
        ))

        //enregistrer une troisième plante dans notre liste (cactus)
        plantList.add(PlantModel(
            "Cactus",
            "ça pique encore plus",
            "https://cdn.pixabay.com/photo/2016/11/29/11/35/abstract-1869217_960_720.jpg",
            false
        ))

        //enregistrer une quatrième plante dans notre liste (tulipe)
        plantList.add(PlantModel(
            "Tulipe",
            "Tua lipa",
            "https://cdn.pixabay.com/photo/2013/10/09/20/44/tulip-193354_960_720.jpg",
            false
        ))

        //recupérer le recycle view
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(context, plantList,R.layout.item_horizontal_plant)

        // récupére le second recycler view
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(context, plantList,R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }
}