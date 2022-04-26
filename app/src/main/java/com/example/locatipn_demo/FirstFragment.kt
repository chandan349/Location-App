package com.example.locatipn_demo

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.locatipn_demo.databinding.FragmentFirstBinding
import pub.devrel.easypermissions.EasyPermissions


class FirstFragment : Fragment(),EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val permission = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION)
            if (EasyPermissions.hasPermissions(requireContext(),*permission)){
                gotoNextScreen()
            }
                binding.buttonPermit.setOnClickListener{
                    EasyPermissions.requestPermissions(this, "Allow permission",23,*permission)

        }



    }
    private fun gotoNextScreen() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       EasyPermissions.onRequestPermissionsResult(
           requestCode,
           permissions,
           grantResults,
           this
       )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        gotoNextScreen()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        AlertDialog.Builder(requireContext()).setTitle("App Will not Work")
            .setMessage("Please allow the location usage for the app to work")
            .setPositiveButton("Retry",DialogInterface.OnClickListener{ _, _ ->})
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




