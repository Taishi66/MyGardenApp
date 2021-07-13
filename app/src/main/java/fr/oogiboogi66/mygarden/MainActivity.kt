package fr.oogiboogi66.mygarden

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.oogiboogi66.mygarden.fragments.AddPlantFragment
import fr.oogiboogi66.mygarden.fragments.CollectionFragment
import fr.oogiboogi66.mygarden.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //charger notre plantRepository
        val repo = PlantRepository()

        //mettre à jour la liste de plante
        repo.updateData{
            //injecter le fragment dans notre boite (fragment_container
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, AddPlantFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }
}