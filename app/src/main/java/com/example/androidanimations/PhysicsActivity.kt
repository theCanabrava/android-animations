package com.example.androidanimations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.androidanimations.databinding.ActivityPhysicsBinding

class PhysicsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhysicsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhysicsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imageView.setOnClickListener {
            binding.imageView.let { img->
                SpringAnimation(img, DynamicAnimation.TRANSLATION_Y, -300f).apply {
                    spring.stiffness = SpringForce.STIFFNESS_LOW
                    animateToFinalPosition(-300f)
                    addEndListener { _, _, _, _ ->

                        animateToFinalPosition(0f)

                    }
                }
            }
        }
    }
}