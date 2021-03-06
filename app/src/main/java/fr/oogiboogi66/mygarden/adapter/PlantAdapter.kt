package fr.oogiboogi66.mygarden.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.oogiboogi66.mygarden.*

class PlantAdapter (
    val context : MainActivity,
    private val plantList : List<PlantModel>,
    private val layoutId : Int
    ): RecyclerView.Adapter<PlantAdapter.ViewHolder>() {
    //boite pour ranger tout les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName: TextView? = view.findViewById(R.id.name_item)
        val plantDescription : TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
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

        //recupérer le repository
        val repo = PlantRepository()

        //utiliser glide pour recupérer l'img à partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        //mettre à jour le nom de la pllante
        holder.plantName?.text = currentPlant.name

        //mettre à jour la description
        holder.plantDescription?.text = currentPlant.description

        //verifier si la plante à été lié ou non
        if(currentPlant.liked){
            holder.starIcon.setImageResource(R.drawable.ic_like)
        } else{
            holder.starIcon.setImageResource(R.drawable.ic_unlike)
        }
        // rajouter une interaction sur cette étoile
        holder.starIcon.setOnClickListener{
            //inverser si le button est like ou non
            (!currentPlant.liked).also { currentPlant.liked = it }
            //mettre à jour l'objet plant
            repo.updatePlant(currentPlant)
        }
        //interaction lors du click sur une plante
        holder.itemView.setOnClickListener{
            //Afficher la popup
            PlantPopup(this, currentPlant).show()
        }
    }

    override fun getItemCount(): Int = plantList.size

}