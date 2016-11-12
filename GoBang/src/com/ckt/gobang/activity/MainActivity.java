package com.ckt.gobang.activity;

import com.ckt.gobang.R;
import com.ckt.gobang.util.ConstantUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity{
	private Button bt_offlinegame;
	private Button bt_bluetoothGame;
	private Button bt_about;
	private Button bt_exit;
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ConstantUtil.WELCOME_VIEW:
				setContentView(R.layout.welcome);
				
	
						handler.sendEmptyMessageDelayed(ConstantUtil.MAIN_VIEW, 2000);
						//handler.sendEmptyMessage(ConstantUtil.MAIN_VIEW);
					
				break;
			case ConstantUtil.MAIN_VIEW:
				
				initMainView();
				break;
			case ConstantUtil.ABOUT_VIEW:
				Intent it=new Intent(MainActivity.this,AboutActivity.class);
				startActivity(it);
				break;	
				
			case ConstantUtil.BULETOOTH_GAME_VIEW:
				Intent it1=new Intent(MainActivity.this,ConnectActivity.class);
				startActivity(it1);
			
			}
			
			
		}

		private void initMainView() {
			// TODO Auto-generated method stub
		
			setContentView(R.layout.activity_main);
		
			 bt_offlinegame=(Button) findViewById(R.id.offline_button);
			 
			 bt_offlinegame.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent it=new Intent(MainActivity.this,OffLineGameActivity.class);
						
						startActivity(it);
					}
				});
			 bt_bluetoothGame=(Button) findViewById(R.id.online_button);
			 bt_bluetoothGame.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent it=new Intent(MainActivity.this,ConnectActivity.class);
					Log.i("testetsetsetset", "77777777777777777777777777");
					startActivity(it);
				}
			});
			 
			 bt_about=(Button) findViewById(R.id.about);
			 bt_about.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					handler.sendEmptyMessage(ConstantUtil.ABOUT_VIEW);
				}
			});
			 
			 bt_exit=(Button) findViewById(R.id.exit);
			 bt_exit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					android.os.Process.killProcess(android.os.Process.myPid());
					System.exit(0); 
				}
			});
		};
	};
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		handler.sendEmptyMessage(ConstantUtil.WELCOME_VIEW);
		
	}
	
	
}
