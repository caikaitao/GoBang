package com.ckt.gobang.view;


import java.util.ArrayList;
import java.util.List;

import com.ckt.gobang.R;
import com.ckt.gobang.util.ConstantUtil;
import com.ckt.gobang.util.LogicUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameView extends View{
	private Paint paint=new Paint();
	private int mPanelWidth;
	private float mLineHeight;
	
	private Bitmap blackStone,whiteStone;
	
	private ArrayList<Point> mWhiteList=new ArrayList<Point>();
	private ArrayList<Point> mBlackList=new ArrayList<Point>();
	public boolean isWhiteRun=true;
	public boolean isGameOver;
	public boolean isWhiteWin;
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundColor(getResources().getColor(R.color.khaki));
		
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		paint.setColor(0x88000000);
		paint.setAntiAlias(true);
		paint.setDither(true);
		setAlpha(1);
		paint.setStyle(Paint.Style.STROKE);
		
		blackStone=BitmapFactory.decodeResource(getResources(), R.drawable.blackstone);
		whiteStone=BitmapFactory.decodeResource(getResources(), R.drawable.whitestone);
	}
	public void restart(){
		mWhiteList.clear();
		mBlackList.clear();
		isWhiteRun=true;
		 isGameOver=false;
		 invalidate();
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int widthSize=MeasureSpec.getSize(widthMeasureSpec);
		int widthMode=MeasureSpec.getMode(widthMeasureSpec);
		
		int heightSize=MeasureSpec.getSize(heightMeasureSpec);
		int heightMode=MeasureSpec.getMode(heightMeasureSpec);
		
		int width=Math.min(widthSize, heightSize);
		
		if(widthMode==MeasureSpec.UNSPECIFIED){
			width=heightSize;
		}else if(heightMode==MeasureSpec.UNSPECIFIED){
			width=widthSize;
		}
		setMeasuredDimension(width, width);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		mPanelWidth=w;
		mLineHeight=mPanelWidth*1.0f/ConstantUtil.MAX_LINE;
		int sizeStone=(int) (mLineHeight*ConstantUtil.scaleOfStone);
		blackStone=ConstantUtil.FitTheScreenSizeImage(blackStone, sizeStone, sizeStone);
		whiteStone=ConstantUtil.FitTheScreenSizeImage(whiteStone, sizeStone, sizeStone);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawBoard(canvas);
		drawPieces(canvas);
		
		
	}
	
	public void checkGameOver() {
		
		boolean whiteWin=checkFiveInLine(mWhiteList);
		boolean blackWin=checkFiveInLine(mBlackList);
		if(whiteWin||blackWin){
			isGameOver=true;
			isWhiteWin=whiteWin;
			Log.i("result","result");
			

		}
	}
	//提供游戏结束处理接口
	public  interface GameOverlistener{
		void Result(GameView v);
	}
	public void setGameOverlistener(GameOverlistener go){
		
	}
	
	private boolean checkFiveInLine(List<Point> points) {
		// TODO Auto-generated method stub
		for(Point p:points){
			int x=p.x;
			int y=p.y;
			boolean win=LogicUtil.checkHorizontal(x,y,points);
			if(win)return true;
			win=LogicUtil.checkVertical(x, y, points);
			if(win)return true;
			win=LogicUtil.checkLeftDiagonal(x, y, points);
			if(win)return true;
			win=LogicUtil.checkRightDiagonal(x, y, points);
			if(win)return true;
		}
		return false;
	}
	
	
	private void drawPieces(Canvas canvas) {
		// TODO Auto-generated method stub
		for(int i=0,n=mWhiteList.size();i<n;i++){
			Point wp=mWhiteList.get(i);
			canvas.drawBitmap(whiteStone, (wp.x+(1-ConstantUtil.scaleOfStone)/2)*mLineHeight, (wp.y+(1-ConstantUtil.scaleOfStone)/2)*mLineHeight, null);
		}
		
		for(int i=0,n=mBlackList.size();i<n;i++){
			Point bp=mBlackList.get(i);
			canvas.drawBitmap(blackStone, (bp.x+(1-ConstantUtil.scaleOfStone)/2)*mLineHeight, (bp.y+(1-ConstantUtil.scaleOfStone)/2)*mLineHeight, null);
		}
	}
	private void drawBoard(Canvas canvas) {
		// TODO Auto-generated method stub
		int maxWidth=mPanelWidth;
		float lineHeight=mLineHeight;
		for(int i=0;i<ConstantUtil.MAX_LINE;i++){
			int startX=(int) (lineHeight/2);
			int endX=(int) (maxWidth-lineHeight/2);
			int y=(int) ((0.5+i)*lineHeight);
			canvas.drawLine(startX, y, endX, y, paint);
			canvas.drawLine(y, startX, y, endX, paint);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		
		
		if(isGameOver)return false;
		if(event.getAction()==MotionEvent.ACTION_UP){
			int x=(int) event.getX();
			int y=(int) event.getY();
			Point p=getValidPoint(x,y);
			if(mWhiteList.contains(p)||mBlackList.contains(p)){
				return false;
			}
			if(isWhiteRun){
				mBlackList.add(p);
			}else{
				mWhiteList.add(p);
			}
			invalidate();checkGameOver();((GameOverlistener) getContext()).Result(this);
			isWhiteRun=!isWhiteRun;
			
			};
		
		return true;
	}
	private Point getValidPoint(int x, int y) {
		// TODO Auto-generated method stub
		return new Point((int)(x/mLineHeight),(int)(y/mLineHeight));
	}
	private static final String INSTANCE="instance";
	private static final String INSTANCE_GAME_OVER="instance_game_over";
	private static final String INSTANCE_WHITE_ARRAY="instance_white_array";
	private static final String INSTANCE_BLACK_ARRAY="instance_black_array";
	private static final String INSTANCE_WHITE_RUN="instance_white_run";
	@Override
	protected Parcelable onSaveInstanceState() {
		// TODO Auto-generated method stub
		Bundle bundle=new Bundle();
		bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
		bundle.putBoolean(INSTANCE_GAME_OVER, isGameOver);
		bundle.putBoolean(INSTANCE_WHITE_RUN, isWhiteRun);
		bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteList);
		bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mBlackList);
		return bundle;
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// TODO Auto-generated method stub
		if(state instanceof Bundle){
			Bundle bundle=(Bundle) state;
			isGameOver=bundle.getBoolean(INSTANCE_GAME_OVER);
			isWhiteRun=bundle.getBoolean(INSTANCE_WHITE_RUN);
			mWhiteList=bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
			mBlackList=bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
			return;
		}
		super.onRestoreInstanceState(state);
	}
}
