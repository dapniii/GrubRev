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
import android.app.Activity
import android.content.ContentValues.TAG
import android.os.ProxyFileDescriptorCallback
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapActivity : AppCompatActivity() {
    private lateinit var profileIv: ImageView
    private lateinit var mapView: MapView

    private lateinit var profileActivityResultLauncher: ActivityResultLauncher<Intent>

    private var firebaseDb = Firebase.firestore
//    private var customMarkerList: ArrayList<CustomMarker> = ArrayList()

    //Temp, local hardcoded markers
    private val customMarkerList: ArrayList<CustomMarker> = DataHelper.initializeCustomMarker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //1. Initialize OpenStreetMap library
        //needs to be before setContentView
        Configuration.getInstance().load(this, getSharedPreferences("OpenStreetMap", MODE_PRIVATE))

        setContentView(R.layout.activity_map)

        //Initialize
        this.profileIv = findViewById(R.id.profileIv)
        this.mapView = findViewById(R.id.mapView)

        //Record markers to database
//        setMarkerstoDB(customMarkerList)

      /*Checks if user pressed Logout button from profile activity
        if user clicked logout, finish MapActivity and reopen MainActivity (login page)*/
        profileActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        //OnClick Profile ImageView
        this.profileIv.setOnClickListener(View.OnClickListener {
            openProfileActivity()
        })

        //< * * * OpenStreetMap * * *
        //2. Setup OSM MapView Settings
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        //3. Initialize Map Zoom Level and Position
        //20 is Max Zoom and Location set to DLSU Agno
        val mapController = mapView.controller
        mapController.setZoom(20.0)
        val startPoint = GeoPoint(14.56638, 120.99250)
        mapController.setCenter(startPoint)

        //4. Request permissions from User to access location
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        ActivityCompat.requestPermissions(this, permissions, 0)

        //5. TODO: TENTATIVE user current location
        //mapView.isMyLocationEnabled = true
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mapView)
        locationOverlay.enableMyLocation()
        mapView.overlays.add(locationOverlay)

        //6. Print out all custom markers to map
        loadMarkers { customMarkers ->
            Log.d(TAG, "customMarkerList: $customMarkerList")
            for (customMarker in customMarkerList) {
                val osmMarker = Marker(mapView)
                osmMarker.position = customMarker.location
                osmMarker.title = customMarker.name
                osmMarker.snippet = "Rating: ${customMarker.avgRating}"

                //Assign corresponding color depending on restaurant's average rating
                when (customMarker.avgRating) {
                    in 0.0..1.9 -> osmMarker.icon = resources.getDrawable(R.drawable.pin_red)
                    in 2.0..3.9 -> osmMarker.icon = resources.getDrawable(R.drawable.pin_orange)
                    in 4.0..5.0 -> osmMarker.icon = resources.getDrawable(R.drawable.pin_yellow)
                }
                //When marker is clicked, open RestaurantActivity with its corresponding restaurant
                osmMarker.setOnMarkerClickListener { marker, mapView ->
                    openRestaurantActivity(customMarker.name)
                    true
                }
                //adds the markers on top of the map
                mapView.overlays.add(osmMarker)
            }

            mapView.invalidate()
        }

        // * * * OpenStreetMap * * * />
    }

    //TODO: Temp - user data fetching stuff

    //When opening ProfileActivity from MapActivity, show Logout button
    private fun openProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("SHOW_LOGOUT", true)
        intent.putExtra("USERNAME", "Candice")
        profileActivityResultLauncher.launch(intent)
    }

    private fun openRestaurantActivity(restaurant: String) {
        val intent = Intent(this, RestaurantActivity::class.java)
        intent.putExtra("RESTAURANT", restaurant)
        startActivity(intent)
    }

    //This is a temp function only to be ran once to instantiate the marker data to the db
    private fun setMarkerstoDB(customMarkerList: ArrayList<CustomMarker>) {
        for (customMarker in customMarkerList) {
            val markerData = hashMapOf(
                "name" to customMarker.name,
                "location" to customMarker.location,
                "avgRating" to customMarker.avgRating
            )

            firebaseDb.collection("markers").add(markerData)
                //Notify if marker added to db
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "Marker added to Firestore with ID: ${documentReference.id}")
                }
                //Notify if marker wasn't added to db
                .addOnFailureListener { error ->
                    Log.d(TAG, "Error adding marker to Firestore: $error")
                }
        }
    }

    private fun loadMarkers(callback: (ArrayList<CustomMarker>) -> Unit) {
        val customMarkers = arrayListOf<CustomMarker>()

        firebaseDb.collection("markers").get()
            .addOnSuccessListener { storedMarkers ->
                for (storedMarker in storedMarkers) {
                    val name = storedMarker["name"] as String
                    val locationHash = storedMarker["location"] as HashMap<*, *>
                    val location = GeoPoint(locationHash["latitude"] as Double,
                                            locationHash["longitude"] as Double)
                    val avgRating = storedMarker["avgRating"] as Double

                    val customMarker = CustomMarker(name, location, avgRating)
                    customMarkers.add(customMarker)
                    Log.d(TAG, "Marker added to List: $customMarker")
                }
                callback(customMarkers)
            }
            .addOnFailureListener {error ->
                Log.d(TAG, "ERROR: $error")
            }
    }

    /*private fun OLDsetMarkerstoDB(customMarkerList: ArrayList<CustomMarker>) {
        for (customMarker in customMarkerList) {
            firebaseDb.collection("markers").document().set(customMarker)
                //Notify if marker added to db
                .addOnSuccessListener {
                    Log.d(TAG, "Marker added to Firestore: $customMarker")
                }
                //Notify if marker wasn't added to db
                .addOnFailureListener {
                    Log.d(TAG, "Error adding marker to Firestore")
                }
        }
    }*/

    /*private suspend fun OLDloadMarkers(): ArrayList<CustomMarker> {
        val customMarkers : ArrayList<CustomMarker> = ArrayList()

        try {
            val storedMarkers = firebaseDb.collection("markers").get().await()

            for (storedMarker in storedMarkers) {
                val name = storedMarker.getString("name") ?: ""
                val location = storedMarker.getGeoPoint("location") ?: GeoPoint(0.0, 0.0)
                val avgRating = storedMarker.getDouble("avgRating") ?: 0.0

                val customMarker = CustomMarker(name, location as GeoPoint, avgRating)
                customMarkers.add(customMarker)
                Log.d(TAG, "Marker added to List: $customMarker")
            }
        } catch (error : Exception) {
            Log.d(TAG, "ERROR: $error")
        }

        return customMarkers
    }*/

    /*private fun MoreOldloadMarkers(callback: (ArrayList<CustomMarker>) -> Unit) {
        firebaseDb.collection("markers").get()
            .addOnSuccessListener { storedMarkers ->
                val customMarkers : ArrayList<CustomMarker> = ArrayList()
                for (storedMarker in storedMarkers) {
                    val name = storedMarker.getString("name") ?: ""
                    val location : GeoPoint =
                        (storedMarker.getGeoPoint("location") ?: GeoPoint(0.0, 0.0)) as GeoPoint
                    val avgRating = storedMarker.getDouble("avgRating") ?: 0.0

                    val customMarker = CustomMarker(name, location, avgRating)
                    customMarkers.add(customMarker)
                    Log.d(TAG, "Marker added to List: $customMarker")
                }
                callback(customMarkers)

            }
            .addOnFailureListener { error ->
                Log.d(TAG, "Marker Error: $error")
                callback(ArrayList())
            }
        }*/
}