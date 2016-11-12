package com.ckt.gobang.activity;


import java.nio.channels.AlreadyConnectedException;

import com.ckt.gobang.R;
import com.ckt.gobang.view.GameView;
import com.ckt.gobang.view.GameView.GameOverlistener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends Activity implements GameOverlistener{
	private TextView tv_info;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==1){
				
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.welcome);
//		handler.sendEmptyMessageDelayed(1, 2000);
		setContentView(R.layout.game_main);
		tv_info=(TextView) findViewById(R.id.tv_whorun);
	}
	@Override
	public void Result(final GameView v) {
		// TODO Auto-generated method stub
		
		if(v.isGameOver){
			if(v.isWhiteWin){
				tv_info.setText("恭喜白方获胜!!");
			}else{
				tv_info.setText("恭喜黑方获胜!!");
			}
			String text=v.isWhiteWin?"恭喜白方获胜":"恭喜黑方获胜";
			Toast.makeText(this,text , Toast.LENGTH_LONG).show();
			new AlertDialog.Builder(this)
			.setMessage(text)
			.setPositiveButton("重来一局", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					v.restart();
				}
			}).setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			}).show();
			
			
			return;
		}
		if(v.isWhiteRun){
			tv_info.setText("白子 请下");
		}else{
			tv_info.setText("黑子 请下");
		}
	}
	
	
}
