package com.ckt.gobang.activity;

import com.ckt.gobang.util.ConstantUtil;
import com.ckt.gobang.view.MyGameViewClient;
import com.ckt.gobang.view.MyWaitingProgressDialog;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class TestClientActivity extends Activity {
	
	private MyWaitingProgressDialog progressDialog;
	public static Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
		setContentView(new MyGameViewClient(this));
		 DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			ConstantUtil.SCREENWIDTH = dm.widthPixels;
			ConstantUtil.SCREENHEIGHT = dm.heightPixels;// �
		 handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				// 1代表要关闭activity
				//2代表已经连接上服务器用户
				case ConstantUtil.CLIENT_CONNECTING:
					//finish();
					progressDialog.show();
					break;
				case ConstantUtil.CLIENT_CONNECT_OK:
					progressDialog.dismiss();
					break;
				case ConstantUtil.CLIENT_WIN:
					Toast.makeText(TestClientActivity.this, "我方获胜", Toast.LENGTH_SHORT).show();
					break;
				case ConstantUtil.CLIENT_FAIL:
					Toast.makeText(TestClientActivity.this, "我方失利", Toast.LENGTH_SHORT).show();
					break;
					
				case ConstantUtil.CLIENT_CONNECT_ERROR:
					finish();
					break;
				
				default:
					break;
				}
				super.handleMessage(msg);
			}

		};
		// 创建ProgressDialog对象
				 progressDialog = new MyWaitingProgressDialog(
						this) {

					@Override
					public void onBackPressed() {
						// TODO Auto-generated method stub
						Message msg = new Message();
						msg.what = ConstantUtil.CLIENT_CONNECT_ERROR;
						handler.sendMessage(msg);
						super.onBackPressed();
					}

				};
				progressDialog.setMessage("正在寻找主机");
				
		
		 
	}

	

}
