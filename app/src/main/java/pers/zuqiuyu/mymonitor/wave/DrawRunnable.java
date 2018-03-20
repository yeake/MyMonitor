package pers.zuqiuyu.mymonitor.wave;

import java.util.concurrent.LinkedBlockingQueue;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import pers.zuqiuyu.mymonitor.MonitorActivity;
import pers.zuqiuyu.mymonitor.R;


public class DrawRunnable implements Runnable{

	private Paint         mPaint;
	private int           WAVE_PADDING = 40;
	private int           STROKE_WIDTH = 3;
	private LinkedBlockingQueue<Integer> mQueue;
	private SurfaceHolder mSurfaceHolder;
	private SurfaceView   mSurfaceView;
	private WaveParse     mWaveParas;
	
	
	public DrawRunnable(LinkedBlockingQueue<Integer> queue,
			SurfaceView surfaceView, SurfaceHolder surfaceHolder,
			WaveParse waveParas)
	{
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setStrokeWidth(STROKE_WIDTH);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStyle(Paint.Style.STROKE);
		
		this.mQueue = queue;
		this.mSurfaceHolder = surfaceHolder;
		this.mSurfaceView = surfaceView;
		this.mWaveParas = waveParas;
		
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub 

		int    temp = 0;
	    Point  oldPoint = new Point(0, 0);
	    Point  newPoint = new Point(0, 0);
	    int[]  tempArray = new int[5];
	    int    counter   = 0;
	    Path   mPath     = new Path();
	    
		while(true)
		{

			    for(counter = 0; counter < mWaveParas.bufferCounter; counter++)
			    {
			    	try {
	            		temp = mQueue.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	tempArray[counter] = temp;
			    }
			   
			    synchronized (this) {
			    		Canvas mCanvas = mSurfaceHolder.lockCanvas(new Rect(oldPoint.x,0,oldPoint.x+STROKE_WIDTH*5,mSurfaceView.getHeight()-WAVE_PADDING));
					    if(mCanvas != null)
					    {
					    	mCanvas.drawColor(MonitorActivity.mContext.getResources().getColor(R.color.nephritis));
						    mPath.reset();
						    for(counter = 0;counter <mWaveParas.bufferCounter; counter++)
						    {
						    	
						    	newPoint.x = (oldPoint.x+mWaveParas.xStep)%mSurfaceView.getWidth();
				            	newPoint.y = mSurfaceView.getHeight()- WAVE_PADDING*2 - (int)(0.004*tempArray[counter]*(mSurfaceView.getHeight()- WAVE_PADDING*2));
				            	           	
				    	        //
				    	        
				    	        mPath.moveTo(oldPoint.x, oldPoint.y);
				    	        mPath.quadTo((newPoint.x+oldPoint.x)/2, (newPoint.y+oldPoint.y)/2, newPoint.x, newPoint.y);
				    	        
				            	oldPoint.x = newPoint.x;
				    		    oldPoint.y = newPoint.y;
				    		    
				    		   
						    }
						    
						    mCanvas.drawPath(mPath, mPaint);
			    		    mCanvas.save();
			    		    
			    		    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
					    }
			    		
			    	
				}
			    
    		    
    		    
		}
	}

	
}