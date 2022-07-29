package com.example.androidanimations

import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidanimations.databinding.ActivityBitmapBinding

class BitmapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBitmapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBitmapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.drawableAnimation.setOnClickListener {
            (binding.drawableAnimation.drawable as Animatable).start()
        }
    }
}