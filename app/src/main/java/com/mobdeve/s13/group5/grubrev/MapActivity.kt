package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


class MapActivity : AppCompatActivity() {
    private lateinit var profileIv: ImageView

    private lateinit var mapView: MapView

    //TODO: Temp
//    private lateinit var yellowPinIv: ImageView
//    private lateinit var orangePinIv: ImageView
//    private lateinit var redPinIv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize OpenStreetMap library
        Configuration.getInstance().load(this, getSharedPreferences("OpenStreetMap", MODE_PRIVATE))

        //Set Activity Layout
        setContentView(R.layout.activity_map)

        //Initialize
        this.profileIv = findViewById(R.id.profileIv)
        this.mapView = findViewById(R.id.mapView)
        //TODO: Temp Initialize
//        this.yellowPinIv = findViewById(R.id.yellowPinIv)
//        this.orangePinIv = findViewById(R.id.orangePinIv)
//        this.redPinIv = findViewById(R.id.redPinIv)

        //Setup OSM MapView Settings
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        //Initialize Map Zoom Level and Position
        val mapController = mapView.controller
        mapController.setZoom(10.0)
        // TODO: Temp Default Location - Berlin, Germany
        mapController.setCenter(GeoPoint(52.5200, 13.4050))

        //OnClick Profile ImageView
        this.profileIv.setOnClickListener(View.OnClickListener {
            openProfileActivity()
        })

        //TODO: Temp Access to Restaurant Activity
//        this.yellowPinIv.setOnClickListener(View.OnClickListener {
//            openRestaurantActivity("SecretAgno")
//        })
//        this.orangePinIv.setOnClickListener(View.OnClickListener {
//            openRestaurantActivity("KainanKanto")
//        })
//        this.redPinIv.setOnClickListener(View.OnClickListener {
//            openRestaurantActivity("KuboBistro")
//        })
    }

    //TODO: Temp - user data fetching stuff
    //When opening ProfileActivity from MapActivity, show Logout button
    private fun openProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("SHOW_LOGOUT", true)
        intent.putExtra("USERNAME", "Candice")
        startActivity(intent)
    }

    private fun openRestaurantActivity(restaurant: String) {
        val intent = Intent(this, RestaurantActivity::class.java)
        intent.putExtra("RESTAURANT", restaurant)
        startActivity(intent)
    }
}