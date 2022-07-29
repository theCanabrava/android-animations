package com.example.androidanimations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import com.example.androidanimations.databinding.ActivityObjectBinding

class ObjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityObjectBinding
    private var currentAnimator: Animator? = null
    private var shortAnimationDuration = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjectBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.flingDown.setOnClickListener { flingY(500f) }
        binding.flingUp.setOnClickListener { flingY(-500f) }
        binding.flingRight.setOnClickListener { flingX(500f) }
        binding.flingLeft.setOnClickListener { flingX(-500f) }

        binding.zoomableView.setOnClickListener { zoom() }
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
    }

    private fun flingY(speed: Float )
    {
        FlingAnimation(binding.circleObject, DynamicAnimation.Y).apply {
            setStartVelocity(speed)
            setMaxValue(200f)
            setMinValue(0f)
            friction = 1.1f
            start()
        }
    }

    private fun flingX(speed: Float )
    {
        FlingAnimation(binding.circleObject, DynamicAnimation.X).apply {
            setStartVelocity(speed)
            setMaxValue(200f)
            setMinValue(0f)
            friction = 1.1f
            start()
        }
    }

    private fun zoom()
    {
        currentAnimator?.cancel()

        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        binding.zoomableView.getGlobalVisibleRect(startBoundsInt)
        binding.root.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        val startScale: Float
        if((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height()))
        {
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2

            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        }
        else
        {
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        binding.zoomableView.alpha = 0f
        binding.zoomedView.visibility = View.VISIBLE

        binding.zoomedView.pivotX = 0f
        binding.zoomedView.pivotY = 0f

        currentAnimator = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(
                binding.zoomedView,
                View.X,
                startBounds.left,
                finalBounds.left
            )).apply {
                with(ObjectAnimator.ofFloat(binding.zoomedView, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(binding.zoomedView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(binding.zoomedView, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter()
            {
                override fun onAnimationEnd(animation: Animator?) {
                    currentAnimator = null
                    binding.zoomableView.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                    currentAnimator = null
                    binding.zoomableView.visibility = View.GONE
                }
            })
            start()
        }

        binding.zoomedView.setOnClickListener {
            currentAnimator?.cancel()
            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(binding.zoomedView, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(binding.zoomedView, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(binding.zoomedView, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(binding.zoomedView, View.SCALE_Y, startScale))
                }

                duration = shortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter()
                {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.zoomableView.alpha = 1f
                        binding.zoomableView.visibility = View.VISIBLE
                        binding.zoomedView.visibility = View.GONE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        binding.zoomableView.alpha = 1f
                        binding.zoomableView.visibility = View.VISIBLE
                        binding.zoomedView.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }

}