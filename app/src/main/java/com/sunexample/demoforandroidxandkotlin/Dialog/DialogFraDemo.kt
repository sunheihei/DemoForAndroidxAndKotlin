package com.sunexample.demoforandroidxandkotlin.Dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.sunexample.demoforandroidxandkotlin.R


class DialogFraDemo : DialogFragment() {

    companion object {
        fun Instance() = DialogFraDemo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val wd = dialog!!.window
        val params = wd!!.getAttributes()
        params.gravity = Gravity.CENTER or Gravity.CENTER_HORIZONTAL
//        params.windowAnimations = R.style.bottomSheet_animation
        wd.attributes = params
        wd.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCanceledOnTouchOutside(true)
//        wd.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val view = inflater.inflate(R.layout.fragment_dialog, container)
        return view
    }
    /**
     *  常规使用
     */
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//
//        var builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity());
//        // 设置主题的构造方法
//        // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
//        builder.setTitle("注意：")
//            .setMessage("是否退出应用？")
//            .setPositiveButton("确定", null)
//            .setNegativeButton("取消", null)
//            .setCancelable(false);
//        //builder.show(); // 不能在这里使用 show() 方法
//        return builder.create();
//
//    }

    /**
     *  自定义View
     */

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        var inflater: LayoutInflater = requireActivity().layoutInflater
//        var view = inflater.inflate(R.layout.fragment_dialog, null)
//        val dialog = Dialog(requireActivity(),0);
//        // 关闭标题栏，setContentView() 之前调用
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////          设置主题的构造方法
////           Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
//        dialog.setContentView(view)
//        return dialog
//    }
}