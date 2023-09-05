package com.sunexample.demoforandroidxandkotlin.customView;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

    //Resources.getSystem() 可以拿到系统的一些配置，无法拿到string等项目专属资源
    public static float dp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }

}
