package com.ckt.gobang.view;

import android.app.ProgressDialog;
import android.content.Context;

public class MyWaitingProgressDialog extends ProgressDialog {
	public MyWaitingProgressDialog(Context context) {
		super(context);
		// 设置进度条风格，风格为圆形，旋转的  
		setProgressStyle(ProgressDialog.STYLE_SPINNER);  
        // 设置ProgressDialog 标题  
       // setTitle("提示");  
        // 设置ProgressDialog 提示信息  
        //setMessage("这是一个圆形进度条对话框");  
        // 设置ProgressDialog 的进度条是否不明确  
        setIndeterminate(false);
        //设置点击对话框外面的部分不消失
        setCanceledOnTouchOutside(false);
        // 设置ProgressDialog 是否可以按退回按键取消  
       setCancelable(true);           
		// TODO Auto-generated constructor stub
	}
	public MyWaitingProgressDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

}
