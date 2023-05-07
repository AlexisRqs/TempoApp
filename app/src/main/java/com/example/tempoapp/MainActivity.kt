package com.example.tempoapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.tempoapp.databinding.ActivityMainBinding
import com.example.tempoapp.model.ColorTempoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class MainActivity : AppCompatActivity() {
    val LOGTAG = MainActivity::class.simpleName

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val edfapi = ApiClient.instance.create(IEdfApi::class.java)


        val EDF_TEMPO_API_ALERT_TYPE = ""
        val call = edfapi.getColorTempo(EDF_TEMPO_API_ALERT_TYPE,String())
        call.enqueue(object : Callback<ColorTempoResponse> {
            override fun onResponse(
                call: Call<ColorTempoResponse>,
                response: Response<ColorTempoResponse>
            ) {
                Log.d(LOGTAG, response.body().toString())
            }

            override fun onFailure(call: Call<ColorTempoResponse>, t: Throwable) {
                Log.e(LOGTAG,"Call to getColorTempo failed")
            }
        } )





        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }
}

