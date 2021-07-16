package com.sunexample.demoforandroidxandkotlin.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sunexample.demoforandroidxandkotlin.databinding.LeftFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class LeftFragment : Fragment() {

    lateinit var binding: LeftFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LeftFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.button.setOnClickListener {
            (activity as FragmentActivity).replaceFragment(AnotherRightFragment.instance)
        }
    }

    companion object {
        @JvmStatic
        val instance: LeftFragment
            get() = LeftFragment()
    }
}