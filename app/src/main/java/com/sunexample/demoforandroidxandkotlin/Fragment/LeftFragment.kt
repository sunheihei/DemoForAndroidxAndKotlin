package com.sunexample.demoforandroidxandkotlin.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sunexample.demoforandroidxandkotlin.R
import kotlinx.android.synthetic.main.left_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class LeftFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.left_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        button.setOnClickListener {
            (activity as FragmentActivity).replaceFragment(AnotherRightFragment.instance)
        }
    }

    companion object {
        @JvmStatic
        val instance: LeftFragment
            get() = LeftFragment()
    }
}