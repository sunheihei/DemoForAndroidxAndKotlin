package com.sunexample.demoforandroidxandkotlin.styleAndTheme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityMainBinding
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityStyleAndThemeBinding

class StyleAndThemeActivity : AppCompatActivity() {

    lateinit var binding: ActivityStyleAndThemeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStyleAndThemeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.textshower.setCharacterDelay(20)
        binding.textshower.animateText("Title: The Beauty of Nature\n" +
                "\n" +
                "Nature is a magnificent canvas, ever-changing and full of life. It is a source of inspiration for all, offering a myriad of images that speak volumes about the wonders of the world.\n" +
                "\n" +
                "From the serene tranquility of a meadow in summer to the dynamic fury of a thunderstorm, nature showcases a range of emotions. The sunrise, with its warm hues of orange and pink, promises a new day, full of possibilities. The soft petals of flowers, in their vibrant colors, seem to whisper secrets of the season. The rustling of leaves in the wind creates a soothing symphony, a melody that ease")

    }
}