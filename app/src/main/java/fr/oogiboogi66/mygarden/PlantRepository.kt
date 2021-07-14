package fr.oogiboogi66.mygarden

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.oogiboogi66.mygarden.PlantRepository.Singleton.databaseRef
import fr.oogiboogi66.mygarden.PlantRepository.Singleton.downloadUri
import fr.oogiboogi66.mygarden.PlantRepository.Singleton.plantList
import fr.oogiboogi66.mygarden.PlantRepository.Singleton.storageReference
import java.net.URI
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.coroutines.Continuation

class PlantRepository  {

    object Singleton{
        //donner le lien pour accéder &ua bucket
        private val BUCKET_URL : String = "gs://my-garden-f8e1f.appspot.com/"

        //se connecter a notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //se connecter à la référence "plants"
        val databaseRef = FirebaseDatabase.getInstance("https://my-garden-f8e1f-default-rtdb.firebaseio.com/").getReference("plants")
        //créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

        //contenir le lien de l'image courante
        var downloadUri : Uri? = null
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
    // créer une fonction pour envoyer des fichiers sur le storage
    fun uploadedImage(file: Uri, callback: () -> Unit){
        // verifier que ce fichier n'est pas null
        if (file != null){
            val filename = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(filename)
            val uploadTask = ref.putFile(file)

            uploadTask.continueWithTask(com.google.android.gms.tasks.Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl

            }).addOnCompleteListener{ task ->
                //verifier si tout a bien fonctionné
                if (task.isSuccessful){
                    //recupérer l'img
                    downloadUri = task.result
                    callback()
                }
            }


              }
        }
    

    //mettre à jour l'objet plante
    fun updatePlant(plant:PlantModel) = databaseRef.child(plant.id).setValue(plant)

    //inserer une nouvelle plante en bdd
    fun insertPlant(plant:PlantModel) = databaseRef.child(plant.id).setValue(plant)

    //supprimer une plante de la base
    fun deletePlant(plant:PlantModel) = databaseRef.child(plant.id).removeValue()
}




