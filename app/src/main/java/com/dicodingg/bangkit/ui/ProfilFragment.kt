package com.dicodingg.bangkit.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatButton
import com.dicodingg.bangkit.R
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private val PICK_IMAGE_REQUEST = 1 // Request code for picking an image

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_profil, container, false)

        // Initializing UI components
        val imageView2: ImageView = rootView.findViewById(R.id.imageView2)
        val textView: TextView = rootView.findViewById(R.id.textView)
        val textView2: TextView = rootView.findViewById(R.id.textView2)

        val button1: AppCompatButton = rootView.findViewById(R.id.button1)
        val button2: AppCompatButton = rootView.findViewById(R.id.button2)
        val button4: AppCompatButton = rootView.findViewById(R.id.button4)
        val button5: AppCompatButton = rootView.findViewById(R.id.button5) // Logout button

        // Set the profile details (you can set dynamic data here, e.g., from a ViewModel)
        textView.text = "Lion Fischer"
        textView2.text = "lionfischer@gmail.com"

        // Button1 click listener to select a profile picture
        button1.setOnClickListener {
            openGallery()
        }

        // Button2 click listener to navigate to SettingsActivity
        button2.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        // Button4 click listener to navigate to NotificationActivity
        button4.setOnClickListener {
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity(intent)
        }

        // Button5 click listener to log out
        button5.setOnClickListener {
            logoutUser()
        }

        // Returning the root view of the fragment
        return rootView
    }

    // Method to open the gallery
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Handle the result of the image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val imageView2: ImageView = view?.findViewById(R.id.imageView2)!!

            // Set the selected image as the new profile picture and apply 'centerCrop' to ensure it remains circular or squared.
            imageView2.setImageURI(selectedImageUri)

            // Optional: To make sure the image fits like a profile photo (square crop with rounded corners)
            imageView2.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView2.layoutParams.width = 200 // Adjust width for profile image size
            imageView2.layoutParams.height = 200 // Adjust height for profile image size
        }
    }

    // Method to log out the user
    private fun logoutUser() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut() // Log out from Firebase

        // Navigate back to LoginActivity
        val intent = Intent(requireContext(), WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear backstack
        startActivity(intent)
        requireActivity().finish() // Close the current activity
    }
}
