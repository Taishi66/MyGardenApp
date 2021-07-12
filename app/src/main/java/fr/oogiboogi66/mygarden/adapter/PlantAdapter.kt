package fr.oogiboogi66.mygarden.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import fr.oogiboogi66.mygarden.PlantModel
import fr.oogiboogi66.mygarden.R

class PlantAdapter (
    private val plantList : List<PlantModel>,
    private val layoutId : Int
    ): RecyclerView.Adapter<PlantAdapter.ViewHolder>() {
    //boite pour ranger tout les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //réupérer les infos de la plante
        val currentPlant = plantList[position]
    }

    override fun getItemCount(): Int = plantList.size

}