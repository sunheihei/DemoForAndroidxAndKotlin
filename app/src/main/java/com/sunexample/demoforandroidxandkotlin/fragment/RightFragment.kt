package com.sunexample.demoforandroidxandkotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sunexample.demoforandroidxandkotlin.R

/**
 * A simple [Fragment] subclass.
 */
class RightFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.right_fragment, container, false)
    }

    companion object {
        //利用fragment的setArguments方法，将bundle设置到arguments中
        @JvmStatic
        val instance: RightFragment
            get() {
                val args = Bundle()
                args.putSerializable("RightFragment", "text")
                val crimeFragment = RightFragment()

                //利用fragment的setArguments方法，将bundle设置到arguments中
                crimeFragment.arguments = args
                return RightFragment()
            }
    }
}