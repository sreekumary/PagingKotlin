package com.example.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.paging.databinding.UserDetailsBinding

class UserDetails : AppCompatActivity(){
    lateinit var binding : UserDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent.extras
        val firstName = intent!!.getString("firstName")
        val gender = intent!!.getString("gender")
        val phone = intent!!.getString("phone")

        val bundle = getIntent().extras
        if (bundle != null) {
            val image = bundle.getInt("image")
            binding.image.setImageResource(image)
        }

        binding.firstName.text = firstName
        binding.gender.text = gender
        binding.phone.text = phone



    }
}



