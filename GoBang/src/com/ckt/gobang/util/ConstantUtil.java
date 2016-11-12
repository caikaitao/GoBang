package com.ckt.gobang.util;

import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ConstantUtil {
	//����
	public static final int WELCOME_VIEW=0;
	public static final int MAIN_VIEW=1;
	public static final int ABOUT_VIEW=2;
	public static final int SINGLE_GAME_VIEW=3;
	public static final int BULETOOTH_GAME_VIEW=4;
	public static final int MAX_LINE=10;
	 public static final int MAX_COUNT_IN_LINE=5;
	 public static  final float scaleOfStone=3*1.0f/4;
	
		// ����
		public final static int WHITE_CHESS = 1;
		// ����
		public final static int BLACK_CHESS = 2;

		// �Է���δ��ʤ
		public final static int ENEMYNOTWIN = 0;
		// �Է���ʤ
		public final static int ENEMYWIN = 1;
		// ���ӳ��͸߶�
		public  static int RECT_R = 26;
		// ���ӳ��͸߶�
		public  static int CHESS_R = 18;
		// ʱ���Ƿ�������
		public static boolean serverOrClient = true;
		
		//��Ļ���
		public  static int SCREENWIDTH;
		//��Ļ�߶�	
		public  static int SCREENHEIGHT;
		/**
		 * Ŀ���豸�����������ַ
		 * **/
		public static String address ;
		public static String LogTag = "������";
		/**
		 * ��������֮��ͨ�ŵı�ʾuuid
		 * **/
		public final static UUID uuid = UUID
				.fromString("00001101-0000-1000-8000-00805F9B34FB");
		/**
		 * ����
		 * **/
		public static int[][] ground = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
		
		/**
		 * �ͻ���������������
		 * **/
		public final static int CLIENT_CONNECTING = 1;
		/**
		 * �ͻ������������ɹ�
		 * **/
		public final static int CLIENT_CONNECT_OK = 2;
		/**
		 * �ͻ���ʤ��
		 * **/
		public final static int CLIENT_WIN = 3;
		/**
		 * �ͻ��������쳣
		 * **/
		public final static int CLIENT_CONNECT_ERROR = 4;
		/**
		 * �ͻ���ʧ��	
		 **/
		public final static int CLIENT_FAIL = 5;
		
		
		/**
		 * ���������ڵȴ�����
		 * **/
		public final static int SERVER_CONNECTING = 1;
		/**
		 * ���������ӿͻ��˳ɹ�
		 * **/
		public final static int SERVER_CONNECT_OK = 2;
		/**
		 * ���������ӿͻ����쳣
		 * **/
		public final static int SERVER_CONNECT_ERROR = 3;
		/**
		 * ������ʤ��
		 * **/
		public final static int SERVER_WIN = 4;
		/**
		 * ������ʧ��	
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
