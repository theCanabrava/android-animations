package com.example.androidanimations

import android.animation.*
import android.content.Intent
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.*
import com.example.androidanimations.activitytransitions.TransitionMenuActivity
import com.example.androidanimations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.bitmapAnimations.setOnClickListener { navigate(BitmapActivity::class.java) }
        binding.uiVisibility.setOnClickListener { navigate(UiActivity::class.java) }
        binding.objectAnimations.setOnClickListener { navigate(ObjectActivity::class.java) }
        binding.physicsAnimations.setOnClickListener { navigate(PhysicsActivity::class.java) }
        binding.layoutAnimations.setOnClickListener { navigate(LayoutActivity::class.java) }
        binding.activityAnimations.setOnClickListener { navigate(TransitionMenuActivity::class.java) }

        binding.restart.setOnClickListener { startRestartableAnimations() }
        startCallbackAnimation()
        startInterpolationAnimation()
        startRestartableAnimations()
    }

    private fun startRestartableAnimations()
    {
        startValueAnimation()
        startObjectAnimation()
        startSetAnimation()
        startKeyframeAnimation()
        startArcAnimation()
    }

    private fun <T>navigate(c: Class<T>)
    {
        startActivity(Intent(this, c))
    }

    private fun startValueAnimation()
    {
        ValueAnimator.ofFloat(0f, 100f).apply {
            duration = 1000
            start()
            addUpdateListener { updated ->
                binding.valueText.translationX = updated.animatedValue as Float
            }
        }
    }

    private fun startObjectAnimation()
    {
        binding.objectText.translationX = 0f
        ObjectAnimator.ofFloat(binding.objectText,
            "translationX", 100f).apply {
            duration = 1000
            start()
        }
    }

    private fun startSetAnimation()
    {
        val forward = ObjectAnimator.ofFloat(binding.setText,
            "translationX", 100f).apply {
            duration = 1000
        }
        val backward = ObjectAnimator.ofFloat(binding.setText,
            "translationX", 0f).apply {
            duration = 1000
        }

        AnimatorSet().apply {
            play(forward).before(backward)
            start()
        }

    }

    private fun startCallbackAnimation()
    {
        val forward = ObjectAnimator.ofFloat(binding.callbackText,
            "translationX", 100f).apply {
            duration = 1000
        }
        val backward = ObjectAnimator.ofFloat(binding.callbackText,
            "translationX", 0f).apply {
            duration = 1000

        }

       AnimatorSet().apply {
            play(forward).before(backward)
            start()
            addListener(object : AnimatorListenerAdapter()
            {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    start()
                }
            })
        }

    }

    private fun startInterpolationAnimation()
    {

        val forward = ObjectAnimator.ofFloat(binding.interpolateText,
            "translationX", 100f).apply {
            duration = 1000
        }
        forward.interpolator = AccelerateInterpolator()

        val backward = ObjectAnimator.ofFloat(binding.interpolateText,
            "translationX", 0f).apply {
            duration = 1000

        }
        backward.interpolator = AccelerateInterpolator()

        AnimatorSet().apply {
            play(forward).before(backward)
            start()
            addListener(object : AnimatorListenerAdapter()
            {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    start()
                }
            })
        }

    }

    private fun startKeyframeAnimation()
    {
        val keyframes = ArrayList<Keyframe> ()
        keyframes.add(Keyframe.ofFloat(0f, 0f))
        keyframes.add(Keyframe.ofFloat(.1f, 1f))
        keyframes.add(Keyframe.ofFloat(.2f, 3f))
        keyframes.add(Keyframe.ofFloat(.3f, 5f))
        keyframes.add(Keyframe.ofFloat(.4f, 11f))
        keyframes.add(Keyframe.ofFloat(.5f, 22f))
        keyframes.add(Keyframe.ofFloat(.6f, 45f))
        keyframes.add(Keyframe.ofFloat(.7f, 90f))
        keyframes.add(Keyframe.ofFloat(.8f, 180f))
        keyframes.add(Keyframe.ofFloat(.9f, 360f))
        keyframes.add(Keyframe.ofFloat(1f, 720f))

        val holder = PropertyValuesHolder.ofKeyframe("rotation",
            keyframes[0],
            keyframes[1],
            keyframes[2],
            keyframes[3],
            keyframes[4],
            keyframes[5],
            keyframes[6],
            keyframes[7],
            keyframes[8],
            keyframes[9],
            keyframes[10])
        ObjectAnimator.ofPropertyValuesHolder(binding.keyframeText, holder).apply {
            duration = 1000
            start()
        }
    }

    private fun startArcAnimation()
    {
        val view = binding.arcCircle
        val path = Path().apply {
            arcTo(0f,
                0f,
                300f,
                300f,
                270f,
                -180f,
                true)
        }
        ObjectAnimator.ofFloat(binding.arcCircle, View.X, View.Y, path).apply {
            duration = 2000
            start()
        }
    }
}