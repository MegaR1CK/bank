package com.hfad.worldskillsbank.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.hfad.worldskillsbank.App
import com.hfad.worldskillsbank.R
import com.hfad.worldskillsbank.adapters.BankomatsAdapter
import com.hfad.worldskillsbank.models.ModelBankomat
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.activity_bankomats.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BankomatsActivity : AppCompatActivity() {

    var bankomats: List<ModelBankomat>? = listOf()
    var pointsList: List<Point>? = listOf()

    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_bankomats)

        recycler_bankomats.layoutManager = LinearLayoutManager(this)

        App.MAIN_API.getBankomats().enqueue(object : Callback<List<ModelBankomat>> {
            override fun onResponse(call: Call<List<ModelBankomat>>,
                                    response: Response<List<ModelBankomat>>) {
                if (response.isSuccessful) {
                    bankomats = response.body()

                    val preferences = PreferenceManager
                        .getDefaultSharedPreferences(this@BankomatsActivity)
                    if (preferences.contains("COORDINATE_0"))
                        pointsList = bankomats?.size?.let { prefsToList(preferences, it) }
                    else {
                        runBlocking {
                            pointsList = getBankomatsPointsList(bankomats ?: listOf())
                        }
                        pointsList?.let { listToPrefs(preferences, it) }
                    }

                    pointsList?.forEach {
                        bankomats_map.map.mapObjects.addPlacemark(it,
                            ImageProvider.fromBitmap(getBitmap(R.drawable.map_pin, 65)))
                    }

                    val adapter = bankomats?.let { BankomatsAdapter(it) }
                    adapter?.bankomatListener = object : BankomatsAdapter.BankomatListener {
                        override fun moveCard(pos: Int) {
                            bankomats_map.map.move(CameraPosition(
                                pointsList?.get(pos) ?: Point(),
                                16.0f, 0.0f, 0.0f),
                            Animation(Animation.Type.SMOOTH, 0.75F), null)
                        }
                    }
                    recycler_bankomats.adapter = adapter
                }
            }
            override fun onFailure(call: Call<List<ModelBankomat>>, t: Throwable) {}
        })
    }

    lateinit var locationClient: FusedLocationProviderClient
    val PERMISSION_ID = 45

    override fun onStart() {
        super.onStart()
        bankomats_map.onStart()
        MapKitFactory.getInstance().onStart()

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
         if (checkPermissions()) {
            if (isGeoEnabled()) {
                locationClient.lastLocation.addOnCompleteListener {
                    val location = it.result
                    if (location == null) {
                        val request = LocationRequest()
                        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        request.interval = 5
                        request.fastestInterval = 0
                        request.numUpdates = 1

                        val callback = object : LocationCallback() {
                            override fun onLocationResult(p0: LocationResult?) {
                                val lastLocation = p0?.lastLocation
                                bankomats_map.map.mapObjects.addPlacemark(Point(
                                    lastLocation?.latitude ?: 0.0,
                                    lastLocation?.longitude ?: 0.0),
                                    ImageProvider.fromBitmap(
                                        getBitmap(R.drawable.user_pin, 50)))
                                bankomats_map.map.move(
                                    CameraPosition(Point(
                                        lastLocation?.latitude ?: 0.0,
                                        lastLocation?.longitude ?: 0.0),
                                        16.0f, 0.0f, 0.0f))
                            }
                        }

                        locationClient = LocationServices.getFusedLocationProviderClient(this)
                        locationClient.requestLocationUpdates(request, callback,
                            Looper.getMainLooper())
                    }
                    else {
                        bankomats_map.map.mapObjects.addPlacemark(Point(
                            location.latitude, location.longitude),
                            ImageProvider.fromBitmap(getBitmap(R.drawable.user_pin, 50)))
                        bankomats_map.map.move(
                            CameraPosition(Point(location.latitude, location.longitude),
                                16.0f, 0.0f, 0.0f))
                    }
                }
            }
            else {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle(R.string.gps_disabled_title)
                    .setMessage(R.string.gps_disabled_desc)
                    .setPositiveButton(R.string.ok) { _: DialogInterface, i: Int ->
                        startActivity(Intent(android.provider.Settings
                            .ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                    .setNegativeButton(R.string.login_cancel) { dialog1, which ->
                        bankomats_map.map.move(
                            CameraPosition(pointsList?.first() ?: Point(),
                                12.0f, 0.0f, 0.0f))
                    }
                    .create()
                    .show()
            }
        }
        else
            ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID)
    }

    private fun checkPermissions(): Boolean {
        val permissionStatusFine = ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION)

        val permissionStatusCoarse = ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)

        return permissionStatusFine == PackageManager.PERMISSION_GRANTED &&
            permissionStatusCoarse == PackageManager.PERMISSION_GRANTED
    }

    private fun isGeoEnabled(): Boolean {
        val locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetEnabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return isGPSEnabled && isNetEnabled
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED)
            getLastLocation()
    }

    @ObsoleteCoroutinesApi
    fun getBankomatsPointsList(list: List<ModelBankomat>) : List<Point> {
        val pointsList = mutableListOf<Point>()
        runBlocking(newSingleThreadContext("NET")) {
            list.forEach {
                val address = it.address.replace(" ", "+")
                val pos = App.GEO_API.geocode("cd51ec47-28c8-44d4-a6e9-70f650fde58b",
                    address, "json").execute().body()?.response?.geoObjectCollection
                    ?.featureMember?.first()?.geoObject?.point?.pos
                val longitude = pos?.split(" ")?.first()?.toDouble()
                val latitude = pos?.split(" ")?.last()?.toDouble()
                pointsList.add(Point(latitude ?: 0.0, longitude ?: 0.0))
            }
        }
        return pointsList
    }

    fun getBitmap(res: Int, size: Int): Bitmap {
        val drawable = ResourcesCompat.getDrawable(resources, res, null)
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable?.setBounds(0, 0, size, size)
        drawable?.draw(canvas)
        return bitmap
    }

    fun listToPrefs(preferences: SharedPreferences, list: List<Point>) {
        list.forEach {
            val editor = preferences.edit()
            editor.putString("COORDINATE_${list.indexOf(it)}",
                it.latitude.toString() + " " + it.longitude.toString())
            editor.apply()
        }
    }

    fun prefsToList(preferences: SharedPreferences, size: Int): List<Point> {
        val list = mutableListOf<Point>()
        for(x in 0 until size) {
            val coordinate = preferences.getString("COORDINATE_$x", "0.0 0.0")
            val latitude = coordinate?.split(" ")?.first()?.toDouble()
            val longitude = coordinate?.split(" ")?.last()?.toDouble()
            list.add(Point(latitude ?: 0.0, longitude ?: 0.0))
        }
        return list
    }
}