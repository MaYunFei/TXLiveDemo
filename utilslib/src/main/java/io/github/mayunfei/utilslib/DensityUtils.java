/*
 *
 *  * Copyright (C) 2015 by  xunice@qq.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *
 */

package io.github.mayunfei.utilslib;

import android.content.Context;

/**
 * 跟屏幕相关的工具类
 * @author xunice
 * 
 */

public class DensityUtils {
	/**
	 * 获得屏幕高度 单位像素
	 * @param context
	 * @return
	 */
	public static final float getHeightInPx(Context context) {
		final float height = context.getResources().getDisplayMetrics().heightPixels;
		return height;
	}
	/**
	 * 获得屏幕宽度 单位像素
	 * @param context
	 * @return
	 */
	public static final float getWidthInPx(Context context) {
		final float width = context.getResources().getDisplayMetrics().widthPixels;
		return width;
	}
	/**
	 * 获得屏幕高度 单位dp
	 * @param context
	 * @return
	 */
	public static final int getHeightInDp(Context context) {
		final float height = context.getResources().getDisplayMetrics().heightPixels;
//		int heightInDp = px2dip(context, height);
		return (int)height;
	}
	/**
	 * 获得屏幕宽度 单位dp
	 * @param context
	 * @return
	 */
	public static final int getWidthInDp(Context context) {
		final float width = context.getResources().getDisplayMetrics().widthPixels;
//		int widthInDp = px2dip(context, width);
		return (int)width;
	}
	
	/**
	 * 获得屏幕密度  (像素比例：0.75(ldpi)/1.0(mdpi)/1.5(hdpi)/2.0(xhdpi))
	 * @param context
	 * @return
	 */
	public static final float getDensity(Context context){
		return context.getResources().getDisplayMetrics().density;
	}
	
	/**
	 * 获得屏幕密度  (每寸像素：120(ldpi)/160(mdpi)/240(hdpi)/320(xhdpi))
	 * @param context
	 * @return
	 */
	public static final float getDensityDpi(Context context){
		return context.getResources().getDisplayMetrics().densityDpi;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		return (int) (dpValue * getDensity(context) + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		return (int) (pxValue / getDensity(context) + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param context
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param context
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获取系统statusBar的高度
	 * @param context
	 * @return
     */
	public static int getStatusBarHeight(Context context){
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

}
