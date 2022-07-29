package com.example.androidanimations.activitytransitions

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidanimations.databinding.ActivityTransitionMenuBinding

class TransitionMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransitionMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransitionMenuBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.contentButton.setOnClickListener { navigate() }
        binding.viewPagerButton.setOnClickListener {
            startActivity(Intent(this, ViewPagerV1Activity::class.java))
        }
        binding.viewPager2Button.setOnClickListener {
            startActivity(Intent(this, ViewPagerV2Activity::class.java))
        }
    }

    private fun navigate()
    {
        val intent = Intent(this, TransitionContentActivity::class.java)
        val options = ActivityOptions
            .makeSceneTransitionAnimation(this, binding.coverImage, "cover")
        startActivity(intent, options.toBundle())
    }
}