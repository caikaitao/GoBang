package com.ckt.gobang.util;

import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ConstantUtil {
	//布局
	public static final int WELCOME_VIEW=0;
	public static final int MAIN_VIEW=1;
	public static final int ABOUT_VIEW=2;
	public static final int SINGLE_GAME_VIEW=3;
	public static final int BULETOOTH_GAME_VIEW=4;
	public static final int MAX_LINE=10;
	 public static final int MAX_COUNT_IN_LINE=5;
	 public static  final float scaleOfStone=3*1.0f/4;
	
		// 白棋
		public final static int WHITE_CHESS = 1;
		// 黑棋
		public final static int BLACK_CHESS = 2;

		// 对方还未获胜
		public final static int ENEMYNOTWIN = 0;
		// 对方获胜
		public final static int ENEMYWIN = 1;
		// 格子长和高度
		public  static int RECT_R = 26;
		// 棋子长和高度
		public  static int CHESS_R = 18;
		// 时候是服务器端
		public static boolean serverOrClient = true;
		
		//屏幕宽度
		public  static int SCREENWIDTH;
		//屏幕高度	
		public  static int SCREENHEIGHT;
		/**
		 * 目标设备的蓝牙物理地址
		 * **/
		public static String address ;
		public static String LogTag = "五子棋";
		/**
		 * 用于蓝牙之间通信的表示uuid
		 * **/
		public final static UUID uuid = UUID
				.fromString("00001101-0000-1000-8000-00805F9B34FB");
		/**
		 * 棋盘
		 * **/
		public static int[][] ground = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
		
		/**
		 * 客户端正在连接主机
		 * **/
		public final static int CLIENT_CONNECTING = 1;
		/**
		 * 客户端连接主机成功
		 * **/
		public final static int CLIENT_CONNECT_OK = 2;
		/**
		 * 客户端胜利
		 * **/
		public final static int CLIENT_WIN = 3;
		/**
		 * 客户端连接异常
		 * **/
		public final static int CLIENT_CONNECT_ERROR = 4;
		/**
		 * 客户端失败	
		 **/
		public final static int CLIENT_FAIL = 5;
		
		
		/**
		 * 服务器正在等待连接
		 * **/
		public final static int SERVER_CONNECTING = 1;
		/**
		 * 服务器连接客户端成功
		 * **/
		public final static int SERVER_CONNECT_OK = 2;
		/**
		 * 服务器连接客户端异常
		 * **/
		public final static int SERVER_CONNECT_ERROR = 3;
		/**
		 * 服务器胜利
		 * **/
		public final static int SERVER_WIN = 4;
		/**
		 * 服务器失败	
		 **/
		public final static int SERVER_FAIL = 5;
	 public static Bitmap FitTheScreenSizeImage(Bitmap m,int ScreenWidth, int ScreenHeight)
	{
        float width  = (float)ScreenWidth/m.getWidth();
        float height = (float)ScreenHeight/m.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(width,height);
        return Bitmap.createBitmap(m, 0, 0, m.getWidth(), m.getHeight(), matrix, true);
 }
}
