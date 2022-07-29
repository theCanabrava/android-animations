package com.example.androidanimations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.Scene
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.androidanimations.databinding.ActivityLayoutBinding

class LayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLayoutBinding
    var displayingSceneA = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDefaultTransition()
        setUpSceneTransition()
    }

    private fun setupDefaultTransition()
    {
        binding.addElement.setOnClickListener {
            val textView = TextView(this)
            textView.text = getString(R.string.element)
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            binding.simpleList.addView(textView)
        }
    }

    private fun setUpSceneTransition()
    {
        binding.moveElement.setOnClickListener {
            val sceneRoot: ViewGroup = binding.sceneRoot
            val endScene: Scene = Scene.getSceneForLayout(
                sceneRoot,
                if (displayingSceneA) R.layout.scene_b else R.layout.scene_a,
                this)
            TransitionManager.go(endScene, ChangeBounds())
            displayingSceneA = !displayingSceneA
        }
    }
}