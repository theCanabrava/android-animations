package com.example.androidanimations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidanimations.databinding.ActivityUiBinding
import kotlin.math.hypot

class UiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUiBinding
    private var shortAnimationDuration: Int = 0

    class CardFrontFragment: Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View = inflater.inflate(R.layout.fragment_card_front, container, false)
    }

    class CardBackFragment: Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View = inflater.inflate(R.layout.fragment_card_back, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.crossContent.visibility = View.GONE
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        binding.crossFade.setOnClickListener { crossFade() }


        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.flipFragment.id, CardFrontFragment())
                .commit()
        }
        binding.flipButton.setOnClickListener { flipCard() }

        binding.circleLayout.visibility = View.INVISIBLE
        binding.circleButton.setOnClickListener { circularEvent() }
    }

    private fun crossFade()
    {
        binding.crossContent.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }

        binding.crossSpinner.apply {

            alpha = 1f
            visibility = View.VISIBLE
            animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(object : AnimatorListenerAdapter()
                {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.crossSpinner.visibility = View.GONE
                    }
                })
        }
    }

    private var showingBack = false
    private fun flipCard()
    {
        if(showingBack)
        {
            supportFragmentManager.popBackStack()
            showingBack = false
            return
        }

        showingBack = true
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.animator.card_flip_right_in,
                R.animator.card_flip_right_out,
                R.animator.card_flip_left_in,
                R.animator.card_flip_left_out,
            )
            .replace(binding.flipFragment.id, CardBackFragment())
            .addToBackStack(null)
            .commit()

    }

    private var circleRevealed = false
    private fun circularEvent()
    {
        circleRevealed = if(circleRevealed) {
            circularFade()
            false
        } else {
            circularReveal()
            true
        }
    }
    private fun circularReveal()
    {
        calculateCircle(binding.circleLayout) { view, cx, cy, radius ->
            val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, radius)
            view.visibility = View.VISIBLE
            anim.start()
        }
    }

    private fun circularFade()
    {
        calculateCircle(binding.circleLayout) { view, cx, cy, radius ->
            val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, radius,0f )
            anim.addListener(object : AnimatorListenerAdapter()
            {
                override fun onAnimationEnd(animation: Animator?) {
                    view.visibility = View.INVISIBLE
                }
            })
            anim.start()
        }
    }

    private fun calculateCircle(view: View,
                                callback: (v: View, cx: Int, cy: Int, radius: Float)->Unit)
    {
        val cx = view.width / 2
        val cy = view.height / 2
        val radius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
        callback(view, cx, cy, radius)
    }
}