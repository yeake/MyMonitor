package pers.zuqiuyu.mymonitor.data;

import android.os.Handler;
import android.util.Log;

import pers.zuqiuyu.mymonitor.MainActivity;
import pers.zuqiuyu.mymonitor.utils.CONST;


public class DataParse implements Runnable
{
    private final String         TAG                 = getClass().getName(); 
	private final static int     BUFFER_SIZE         = 1024;
	private final int[]   PACKAGE_HEAD        = new int[]{0x55,0xaa};
	private final int     PKG_ECG_WAVE        = 0x01;
	private final int     PKG_ECG_PARAM       = 0x02;
	private final int     PKG_NIBP            = 0x03;
	private final int     PKG_SPO2            = 0x04;
	private final int     PKG_TEMP            = 0x05;
	private final int     PKG_SW_VER          = 0xfc;
	private final int     PKG_HW_VER          = 0xfd;
	private final int     PKG_SPO2_WAVE       = 0xfe;
	
	private int skipCounter = 0;
	private Handler mHandler;
	
	
	private byte[] recvData = new byte[BUFFER_SIZE];
	private int    emptyIndex = 0;
	private int    parseIndex = 0;
	
	
	
	
	public DataParse(Handler handler) 
	{
		// TODO Auto-generated constructor stub
		this.mHandler = handler;
	}
	long time = System.currentTimeMillis();
	public void Add(byte[] buf, int bufSize)
	{
		boolean pkgStart = false;
		int pkgIndex = 0; 
		int pkgLength = 0;
		byte[] pkgData = null;

		
		if(bufSize+emptyIndex <= BUFFER_SIZE)
		{
			System.arraycopy(buf, 0, recvData, emptyIndex, bufSize);
			emptyIndex = (emptyIndex+bufSize) % BUFFER_SIZE;
		}
		else if( (bufSize+emptyIndex > BUFFER_SIZE) && (bufSize+emptyIndex < 2*BUFFER_SIZE))
		{
			System.arraycopy(buf, 0, recvData, emptyIndex, BUFFER_SIZE-emptyIndex);
			int temp = emptyIndex;
			emptyIndex = 0;
			System.arraycopy(buf, BUFFER_SIZE-temp, recvData, emptyIndex, bufSize-(BUFFER_SIZE-temp));
			emptyIndex = bufSize-(BUFFER_SIZE-temp);
		}
		else {
			Log.i(TAG, "Receive too much data.");
			return;
		}
		
		
//		for (int i = 0; i < bufSize; i++) {
//			recvData[emptyIndex] = buf[i];
//			emptyIndex = (emptyIndex+1)%BUFFER_SIZE;
//			if(emptyIndex == parseIndex)
//			{
//				Log.i(TAG, "fffffffffffffffffffffuuuuuuuuuuuuuuuuuulllllllllllllllllllllllllllllll");
//			}
//			
//		}
		
		if(bufSize < 5) return;
		
		int i = parseIndex;
		while (i != emptyIndex) {
	        
			if ((recvData[i]&0xff) == PACKAGE_HEAD[0]) {
	            int j = (i + 1)%BUFFER_SIZE;
	            if (j != emptyIndex && (recvData[j]&0xff) == PACKAGE_HEAD[1]) {
	            	int k = (j+1)%BUFFER_SIZE;
	            	if(k != emptyIndex)
	            	{
	            		pkgLength = recvData[k]&0xff;
	            		pkgData = new byte[pkgLength+2];
	            		pkgStart = true;
		                pkgIndex = 0;
		                parseIndex = i;
	            	}
	            }
	        }
	        if (pkgStart && pkgLength > 0) {
	            pkgData[pkgIndex] = recvData[i];
	            pkgIndex++;

	            if ((pkgLength != 0) && (pkgIndex == pkgLength + 2)) {
	                if(CheckSum(pkgData)){
	                    ParsePackage(pkgData);
	                }
	                pkgStart = false;
	                parseIndex = (i + 1) % BUFFER_SIZE;
	            }
	        }
	        i = (i + 1) % BUFFER_SIZE;
	    }
		
	  
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

//		int packageLength = 0;
//		
//		while(isRun)
//		{
//
//			if(QueueGet() == PACKAGE_HEAD[0] && QueueGet() == PACKAGE_HEAD[1])
//			{
//				packageLength = QueueGet();
//				if(packageLength <= 0)
//					continue;
//				
//				packageLength = packageLength+2;
//				int[] packageData = new int[packageLength];
//				//packageData[0] = PACKAGE_HEAD[0];
//				//packageData[1] = PACKAGE_HEAD[1];
//				packageData[2] = packageLength-2;
//				for(int i = 3; i < packageLength; i++)
//				{
//					packageData[i] = QueueGet();
//				}
//				
//				if(CheckSum(packageData))
//				{
//					ParsePackage(packageData);
//				}
//			}
//			
//			
//		}
	}
	
	
	private void ParsePackage(byte[] pkgData) {
		// TODO Auto-generated method stub
		int pkgType = pkgData[3]&0xff;
		
		switch (pkgType) {
			case PKG_ECG_WAVE:
				//Log.i(TAG, "pkg_ecg_wave");
				skipCounter++;
				if(skipCounter == 1)
				{
					MainActivity.mECGWaveDraw.add(pkgData[4]&0xff);
					skipCounter = 0;
				}
				
				break;
			case PKG_SPO2_WAVE:
				
					MainActivity.mSpO2WaveDraw.add(pkgData[4]&0xff);
				
//					Log.i("TIME","     "+(System.currentTimeMillis() - time));
//            		time = System.currentTimeMillis();
				break;
			case PKG_ECG_PARAM:
				mHandler.obtainMessage(CONST.MESSAGE_ECG_PARAMS, 0xff&pkgData[6], 0xff&pkgData[5]).sendToTarget();
				//Log.i(TAG, "pkg_ecg_param");
				break;
			case PKG_NIBP:
				//Log.i(TAG, "pkg_nibp");
				mHandler.obtainMessage(CONST.MESSAGE_NIBP_PARAMS, 0xff&pkgData[6], 0xff&pkgData[8]).sendToTarget();
				break;
		    case PKG_SPO2:
			    mHandler.obtainMessage(CONST.MESSAGE_SPO2_PARAM, 0xff&pkgData[5], 0xff&pkgData[6]).sendToTarget();
			    break;
		    case PKG_TEMP:
			    mHandler.obtainMessage(CONST.MESSAGE_TEMP_PARAMS, 0xff&pkgData[5], 0xff&pkgData[6]).sendToTarget();
			    break;
		    case PKG_SW_VER:
			    //Log.i(TAG, "pkg_SW");
			    break;
		    case PKG_HW_VER:
			   // Log.i(TAG, "pkg_HW");
			    break;
		default:
			break;
		}
		
	}

	private boolean CheckSum(byte[] packageData) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i = 2; i < packageData.length-1; i++)
		{
			sum+=(packageData[i]&0xff);
		}
		
		if(((~sum)&0xff) == (packageData[packageData.length-1]&0xff))
		{
			return true;
		}
		
		return false;
	}

	
}