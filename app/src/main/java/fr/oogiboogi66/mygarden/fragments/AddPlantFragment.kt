package fr.oogiboogi66.mygarden.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.oogiboogi66.mygarden.MainActivity
import fr.oogiboogi66.mygarden.PlantModel
import fr.oogiboogi66.mygarden.PlantRepository
import fr.oogiboogi66.mygarden.PlantRepository.Singleton.downloadUri
import fr.oogiboogi66.mygarden.R
import java.util.*

class AddPlantFragment(
    private val context:MainActivity
): Fragment() {

    private val file: Uri? = null
    private var uploadedImage : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_plant, container, false)

        //recuperer uploadedImage pour lui assoicer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        //recuperer le button pour charge rl'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        //lorsqu'on clique dessus ça ouvre les img du téléphone
        pickupImageButton.setOnClickListener { pickupImage() }

        //recuperer le button confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener { sendForm(view) }

        return view
    }
    private fun sendForm(view:View){
        val repo = PlantRepository()
        repo.uploadedImage(file!!){

            val plantName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<EditText>(R.id.description_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            //creer un nouvel objet de type PlantModel
            val plant = PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDescription,
                downloadImageUrl.toString(),
                false,
                grow,
                water

            )
            //envoyer en bdd
             repo.insertPlant(plant)

        }


    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){

            //verifier si les données sont nulles
            if(data == null || data.data == null) return

            //recuperer l'image qui a été sélectionné
            val selectedImage = data.data

            //mettre à jour l'aperçu de l'image
            uploadedImage?.setImageURI(selectedImage)

        }
    }
}