package com.example.androidanimations.activitytransitions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import com.example.androidanimations.databinding.ActivityTransitionContentBinding

class TransitionContentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransitionContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransitionContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setTransitionName(binding.badge, "cover")
    }
}