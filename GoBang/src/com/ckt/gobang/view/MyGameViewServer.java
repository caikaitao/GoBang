package com.ckt.gobang.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;









import com.ckt.gobang.R;
import com.ckt.gobang.activity.TestServerActivity;
import com.ckt.gobang.util.ConstantUtil;
import com.ckt.gobang.util.LogicUtil;
import com.ckt.gobang.util.StringDealer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.widget.Toast;


/**
 * 客户端棋盘，为白方
 * 
 * **/
public class MyGameViewServer extends SurfaceView implements Runnable, Callback {

	private SurfaceHolder sfh;
	private Canvas canvas;
	private Thread th = new Thread(this);
	private boolean isStop = false;
	private Paint paint;
	private Resources res;
	private Bitmap whiteMap;
	private Bitmap blackMap;
	private boolean WHITE_FLAG = true;
	private OutputStream out = null;
	private Bitmap woodBackground;
	private int whoWin = 0;// 0没有，1我方胜利，2对方胜利

	private boolean isExit = true;
	private boolean isMyTurn = false;

	// 蓝牙组件
	private BluetoothServerSocket mServerSocket;
	private BluetoothAdapter adapter;
	private BluetoothSocket socket;

	public MyGameViewServer(Context context) {
		super(context);
		isExit = false;
		sfh = this.getHolder();
		sfh.addCallback(this);
		setFocusable(true);
		res = getResources();
		whiteMap = BitmapFactory.decodeResource(res, R.drawable.human);
		blackMap = BitmapFactory.decodeResource(res, R.drawable.ai);
		paint = new Paint();
		paint.setTextSize(50);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		ConstantUtil.RECT_R=ConstantUtil.SCREENWIDTH/20;
		woodBackground = BitmapFactory.decodeResource(this.getContext()
				.getResources(), R.drawable.wood_background);
		initBluetooth();
		Log.i("SCREENWIDTH", ConstantUtil.SCREENWIDTH+"");
		Log.i("SCREENHEIGHT", ConstantUtil.SCREENHEIGHT+"");
		// TODO Auto-generated constructor stub
	}

	public void initBluetooth() {
		adapter = BluetoothAdapter.getDefaultAdapter();
		AcceptThread serverThread = new AcceptThread();
		serverThread.start();
	}

	// 触摸事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN)
			if (!isStop && isMyTurn) {
				for (int y = 0; y < 9; y++) {
					for (int x = 0; x < 9; x++) {
						if (ConstantUtil.ground[y][x] == 0
								&& isInCircle(event.getX(), event.getY(), x, y)) {
							ConstantUtil.ground[y][x] = ConstantUtil.WHITE_CHESS;
							
							if (LogicUtil.isWin(x, y)) {
								Message msg4 = TestServerActivity.handler
										.obtainMessage(ConstantUtil.SERVER_WIN);
								TestServerActivity.handler.sendMessage(msg4);
								whoWin = 1;
								new MWriteThread(new int[] { y, x,
										ConstantUtil.ENEMYWIN }).start();
							} else {
								new MWriteThread(new int[] { y, x,
										ConstantUtil.ENEMYNOTWIN }).start();
							}
							isMyTurn = false;
							draw();

							return super.onTouchEvent(event);
						}
					}
				}
			}

		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		th.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isExit = true;
		LogicUtil.initGroup();
		try {
			mServerSocket.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isStop = true;
	}

	// 判断是否与某点最近
	// 判断是否与某点最近
	private boolean isInCircle(float touch_x, float touch_y, int x, int y) {

		return ((touch_x - ((x + 1) * 2 * ConstantUtil.RECT_R))
				* (touch_x - ((x + 1) * 2 * ConstantUtil.RECT_R)) + (touch_y - ((y + 1) * 2 * ConstantUtil.RECT_R))
				* (touch_y - ((y + 1) * 2 * ConstantUtil.RECT_R))) < ConstantUtil.RECT_R
				* ConstantUtil.RECT_R;
	}

	private void draw() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawBitmap(woodBackground,null,new RectF(0, 0, ConstantUtil.SCREENWIDTH, ConstantUtil.SCREENHEIGHT),null);

			for(int i = 0; i < 9;i++){
				canvas.drawLine(2*ConstantUtil.RECT_R, 2*ConstantUtil.RECT_R*i+2*ConstantUtil.RECT_R, 9*2*ConstantUtil.RECT_R, 2*ConstantUtil.RECT_R*i+2*ConstantUtil.RECT_R, paint);
						}
			for(int j = 0; j < 9; j++){
				canvas.drawLine(2*ConstantUtil.RECT_R+2*ConstantUtil.RECT_R*j, 2*ConstantUtil.RECT_R, 2*ConstantUtil.RECT_R+2*ConstantUtil.RECT_R*j, 9*2*ConstantUtil.RECT_R , paint);
			}
			for(int y = 0;y<9;y++){
				for(int x= 0;x < 9 ;x++){
					if(ConstantUtil.ground[y][x] != 0)
					drawMyBitmap(x,y);
					//这里加入判断是否有人获胜
				}
			}
			if (whoWin == 0) {

				if (isMyTurn)
//					canvas.drawBitmap(AssetsLoad.picMyTurn, null, new RectF(
//							0, 10 * 2 * Constant.RECT_R, Constant.SCREENWIDTH,
//							Constant.SCREENHEIGHT), null);
					canvas.drawText("黑方请下", 0,10 * 2 * ConstantUtil.RECT_R, paint);
				else {
					canvas.drawText("白方请下", 0,10 * 2 * ConstantUtil.RECT_R, paint);

				}
			} else {
				if (whoWin == 1) {
					canvas.drawText("黑方胜利", 0,10 * 2 * ConstantUtil.RECT_R, paint);
				} else {
					canvas.drawText("白方胜利", 0,10 * 2 * ConstantUtil.RECT_R, paint);

				}

			}
			// canvas.dra
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (canvas != null) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	//以某点为中心，画图片上去
		private void drawMyBitmap(int x,int y){
			if(ConstantUtil.ground[y][x] == ConstantUtil.WHITE_CHESS)
				canvas.drawBitmap(whiteMap, ((x+1)*2*ConstantUtil.RECT_R) - ConstantUtil.CHESS_R, ((y + 1)*2*ConstantUtil.RECT_R) - ConstantUtil.CHESS_R, null);
			else{
				canvas.drawBitmap(blackMap, ((x+1)*2*ConstantUtil.RECT_R ) - ConstantUtil.CHESS_R, ((y + 1)*2*ConstantUtil.RECT_R) - ConstantUtil.CHESS_R, null);
			}
		}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!isExit) {
			draw();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 服务器段的线程
	private class AcceptThread extends Thread {
		public void run() {
			try {
				mServerSocket = adapter.listenUsingRfcommWithServiceRecord(
						"fiveChess", ConstantUtil.uuid);
				//Log.v(Constant.LogTag, "等待连接");
				// Message msg1 =
				// TestServerActivity.handler.obtainMessage(Constant.SERVER_CONNECTING);
				Message msg1 = new Message();
				msg1.what = ConstantUtil.SERVER_CONNECTING;
				TestServerActivity.handler.sendMessage(msg1);
				// System.out.println("等待连接");
				socket = mServerSocket.accept();
				Message msg2 = TestServerActivity.handler
						.obtainMessage(ConstantUtil.SERVER_CONNECT_OK);
				TestServerActivity.handler.sendMessage(msg2);
				//Log.v(Constant.LogTag, "已经连接上");
				// System.out.println("已经连接上");
				new MReadThread().start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 发送数据线程
	private class MWriteThread extends Thread {
		private String data;

		public MWriteThread(int[] buf) {
			super();
			if (buf == null) {
				data = "-1,-1,-1";
			}
			data = StringDealer.getStringFromData(buf);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			OutputStream mmOutStream = null;

			try {
				mmOutStream = socket.getOutputStream();
				mmOutStream.write(data.getBytes());
				Log.v(ConstantUtil.LogTag, data + "已经发送");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	// 读取操作线程
	private class MReadThread extends Thread {
		public void run() {

			byte[] buffer = new byte[1024];
			int bytes;
			InputStream mmInStream = null;

			try {
				mmInStream = socket.getInputStream();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				// Read from the InputStream
				while ((bytes = mmInStream.read(buffer)) > 0) {
					byte[] buf_data = new byte[bytes];
					for (int i = 0; i < bytes; i++) {
						buf_data[i] = buffer[i];
					}
					String s = new String(buf_data);

					int[] data = StringDealer.getDataFromString(s);
					if (data[0] != -1) {
						ConstantUtil.ground[data[0]][data[1]] = ConstantUtil.BLACK_CHESS;

						if (data[2] == ConstantUtil.ENEMYWIN) {
							isStop = true;
							// 黑棋获胜
							// Toast.makeText(context, "黑棋获胜",
							// Toast.LENGTH_SHORT).show();
							whoWin = 2;
							
							Message msg3 = TestServerActivity.handler
									.obtainMessage(ConstantUtil.SERVER_FAIL);
							TestServerActivity.handler.sendMessage(msg3);
						} else {
							isMyTurn = true;
						}
					}
					//Log.v(ConstantUtil.LogTag, s);
					System.out.println(s);
					// MyGameView.ground =
					// StringDealer.getGroupFromString(s);
				}
			} catch (IOException e) {
				try {
					mmInStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

}
