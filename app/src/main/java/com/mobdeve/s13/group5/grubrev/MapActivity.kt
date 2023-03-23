package com.mobdeve.s13.group5.grubrev

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import android.Manifest
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapActivity : AppCompatActivity() {
    private lateinit var profileIv: ImageView

    private lateinit var mapView: MapView

    //TODO: Temp(?)
    private val customMarkerList: ArrayList<CustomMarker> = DataHelper.initializeCustomMarker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize OpenStreetMap library
        Configuration.getInstance().load(this, getSharedPreferences("OpenStreetMap", MODE_PRIVATE))

        //Set Activity Layout
        setContentView(R.layout.activity_map)

        //Initialize
        this.profileIv = findViewById(R.id.profileIv)
        this.mapView = findViewById(R.id.mapView)

        //Setup OSM MapView Settings
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        //Initialize Map Zoom Level and Position
        //20 is Max Zoom and Location set to DLSU Agno
        val mapController = mapView.controller
        mapController.setZoom(20.0)
        val startPoint = GeoPoint(14.56638, 120.99250)
        mapController.setCenter(startPoint)

        //OnClick Profile ImageView
        this.profileIv.setOnClickListener(View.OnClickListener {
            openProfileActivity()
        })

        //TODO: Request Permissions
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        ActivityCompat.requestPermissions(this, permissions, 0)


//        mapView.isMyLocationEnabled = true
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mapView)
        locationOverlay.enableMyLocation()
        mapView.overlays.add(locationOverlay)


        for (customMarker in customMarkerList) {
            val osmMarker = Marker(mapView)
            osmMarker.position = customMarker.location
            osmMarker.title = customMarker.name
            osmMarker.snippet = "Rating: ${customMarker.avgRating}"

            when (customMarker.avgRating) {
                in 0.0..1.9 -> osmMarker.setIcon(resources.getDrawable(R.drawable.pin_red))
                in 2.0..3.9 -> osmMarker.setIcon(resources.getDrawable(R.drawable.pin_orange))
                in 4.0..5.0 -> osmMarker.setIcon(resources.getDrawable(R.drawable.pin_yellow))
            }

            osmMarker.setOnMarkerClickListener { marker, mapView ->
                openRestaurantActivity(customMarker.name)
                true
            }

            mapView.overlays.add(osmMarker)
        }

        mapView.invalidate()
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

    private fun setMarkers(mapView: MapView) {

    }
}