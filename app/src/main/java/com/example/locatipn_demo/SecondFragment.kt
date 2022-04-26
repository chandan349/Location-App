package com.example.locatipn_demo

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.locatipn_demo.databinding.FragmentSecondBinding
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return binding.root

    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       createLocationRequest()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(loc: LocationResult) {
                loc ?: return
                for (location in loc.locations){
                  binding.textLocation.text ="${location.longitude}, ${location.longitude}"
                }
            }
        }


    }
    fun createLocationRequest() {
         locationRequest = LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}