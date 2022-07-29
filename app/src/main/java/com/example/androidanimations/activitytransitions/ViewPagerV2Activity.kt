package com.example.androidanimations.activitytransitions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.androidanimations.databinding.ActivityViewPagerV2Binding
import com.example.androidanimations.fragments.SampleFragment
import com.example.androidanimations.pagetransformers.ZoomOutPageTransformer

class ViewPagerV2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPagerV2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerV2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = ScreenSlidePagerAdapter(this)
        binding.pager.setPageTransformer(ZoomOutPageTransformer())
        binding.previous.setOnClickListener { change(binding.pager.currentItem-1) }
        binding.next.setOnClickListener { change(binding.pager.currentItem+1) }
    }

    private fun change(page: Int)
    {
        if(page < 0 || page > 5) return
        else binding.pager.currentItem = page
    }

    override fun onBackPressed() {
        if(binding.pager.currentItem == 0) super.onBackPressed()
        else binding.pager.currentItem -= 1
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 5

        override fun createFragment(position: Int): Fragment = SampleFragment()
    }

}