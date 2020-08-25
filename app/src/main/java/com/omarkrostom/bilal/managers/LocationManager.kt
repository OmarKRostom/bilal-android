package com.omarkrostom.bilal.managers

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.omarkrostom.bilal.utils.SingleEvent
import javax.inject.Inject

class LocationManager @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient
) {
    /**
     * Internal observers
     */
    private val _lastKnownLocation: MutableLiveData<SingleEvent<Location>> = MutableLiveData()
    private val _locationRetrievalError: MutableLiveData<SingleEvent<Throwable>> = MutableLiveData()
    private val _isLocationPermissionAllowed: MutableLiveData<SingleEvent<Boolean>> =
        MutableLiveData()
    private val _isWaitingForLocation: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()

    /**
     * Retrieve last known location upon permission accepted
     */
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationPermissionAllowed() {
        _isLocationPermissionAllowed.value = SingleEvent(true)
        if (_lastKnownLocation.value == null) {
            _isWaitingForLocation.value = SingleEvent(true)
            startListeningForLocationUpdates()
        } else {
            _isWaitingForLocation.value = SingleEvent(false)
            stopListeningForLocationUpdates()
        }
    }

    private fun startListeningForLocationUpdates() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            _lastKnownLocation.value = SingleEvent(it)
        }.addOnFailureListener {
            _locationRetrievalError.value = SingleEvent(it)
        }
    }

    private fun stopListeningForLocationUpdates() {
        fusedLocationClient.flushLocations()
    }

}