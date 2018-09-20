package net.realworldapplications.mymapdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapFragment) as MapFragment
        mapFragment.getAsyncMap(this)
    }

    //lateinit late initialization of non-null type variable map
    private lateinit var map: TomtomMap

    override fun onMapReady(@NonNull tomtomMap: TomtomMap) {
        this.map = tomtomMap

        val tampa = LatLng(27.950575, -82.457176)
        val balloon = SimpleMarkerBalloon("Tampa")
        tomtomMap.addMarker(MarkerBuilder(tampa).markerBalloon(balloon))
        tomtomMap.centerOn(CameraPosition.builder(tampa).zoom(7.0).build())
    }
}
