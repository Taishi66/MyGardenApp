package fr.oogiboogi66.mygarden

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.oogiboogi66.mygarden.PlantRepository.Singleton.databaseRef
import fr.oogiboogi66.mygarden.PlantRepository.Singleton.plantList
import javax.security.auth.callback.Callback

class PlantRepository  {

    object Singleton{

        //se connecter à la référence "plants"
        val databaseRef = FirebaseDatabase.getInstance("https://my-garden-f8e1f-default-rtdb.firebaseio.com/").getReference("plants")
        //créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()
    }

    fun updateData(callback: () -> Unit){
        //absorber les données qu'on va récupérer dans la databaseRef -> liste de plantes
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                //retirer les anciennes
                plantList.clear()
               //recolter la liste
                for(ds in p0.children) {
                    //construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    //verifier que la plante n'est pas nulle
                    if (plant != null) {
                        plantList.add(plant)
                    }

                }
                //actionner le callback
                callback()
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }
    //mettre à jour l'objet plante
    fun updatePlant(plant:PlantModel) = databaseRef.child(plant.id).setValue(plant)

}