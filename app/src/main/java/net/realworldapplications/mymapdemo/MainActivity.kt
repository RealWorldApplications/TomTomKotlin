package net.realworldapplications.mymapdemo

import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    //lateinit late initialization of non-null type variables
    private lateinit var map: TomtomMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapFragment) as MapFragment
        mapFragment.getAsyncMap(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 101
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) {
            location ->
            if (location != null){
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                val balloon = SimpleMarkerBalloon("You are Here")
                map.addMarker(MarkerBuilder(currentLatLng).markerBalloon(balloon))
                map.centerOn(CameraPosition.builder(currentLatLng).zoom(7.0).build())
            }

        }
    }

    override fun onMapReady(@NonNull tomtomMap: TomtomMap) {
        this.map = tomtomMap
        setUpMap()
    }
}
