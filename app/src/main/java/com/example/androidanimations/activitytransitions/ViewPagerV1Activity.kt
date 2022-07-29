package com.example.androidanimations.activitytransitions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.androidanimations.databinding.ActivityViewPagerV1Binding
import com.example.androidanimations.fragments.SampleFragment
import com.example.androidanimations.pagetransformers.DepthPageTransformer


class ViewPagerV1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPagerV1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerV1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = ScreenSlidePagerAdapter(supportFragmentManager)
        binding.pager.setPageTransformer(true, DepthPageTransformer())
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

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm)
    {
        override fun getCount() = 5
        override fun getItem(position: Int) = SampleFragment()
    }

}