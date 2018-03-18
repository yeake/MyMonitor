package pers.zuqiuyu.mymonitor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import pers.zuqiuyu.mymonitor.bluetooth.BluetoothChatService;
import pers.zuqiuyu.mymonitor.bluetooth.BluetoothUtils;
import pers.zuqiuyu.mymonitor.utils.CONST;
import pers.zuqiuyu.mymonitor.utils.utils;
import pers.zuqiuyu.mymonitor.wave.Wave;
import pers.zuqiuyu.mymonitor.wave.WaveParse;


public class MainActivity extends Activity implements OnClickListener,OnItemClickListener{
	
	//UI
	public static View             vwDivider0;
	public static View             vwDivider1;
	public static View             vwDivider2;
	public static View             IsMainShow;
	public static RelativeLayout   rlSpO2;
	public static RelativeLayout   rlECG;
	public static RelativeLayout     rlNIBP_TEMP;
	public static LinearLayout     llBCR;
	public static LinearLayout     llNTC;
	public static LinearLayout     llBT;
	public static LinearLayout     llBTSelect;
	public static LinearLayout     llCFG;
	public static LinearLayout     llCfgDedail;
	public static ImageButton      ibBluetooth;
	public static ImageButton      ibRecord;
	public static ImageButton      ibConfig;
	public static ListView         lvBtDevices;
	public static Button           btnResearch;
	public static ProgressBar      pbSearch;
	public static ImageView        ivNIBP;
	public static TextView         tvHighPressure;
	public static TextView         tVLowPressure;
	
	//bluetooth service
	public  static Context          mContext;
	public static BluetoothUtils mBluetoothUtils;
	private UIActions.BTDevicesAdapter mBTDevicesAdapter;
	private ProgressDialog          mBtConnectProgressDialog;
	

	public static TextView tvSpO2;
	public static TextView tvPR;
	public static TextView tvResp;
	public static TextView tvHR;
	public static TextView tvTemp;
	
	//draw wave
	public static SurfaceView sfvSpO2Wave;
	public static Wave mSpO2WaveDraw;
	
	public static SurfaceView sfvECGWave;
	public static Wave mECGWaveDraw;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		mBluetoothUtils = new BluetoothUtils(this,mMainHandler);
		mBluetoothUtils.Init();
		mBluetoothUtils.RegisterRecevier();
		
		UI_WidgetInit();
		
		
		WaveParse mSpO2WaveParas = new WaveParse();
		mSpO2WaveParas.bufferCounter = 1;
		mSpO2WaveParas.xStep = 4;
		mSpO2WaveDraw = new Wave(sfvSpO2Wave,mSpO2WaveParas);
		
		
		WaveParse mECGWaveParas = new WaveParse();
		mECGWaveParas.bufferCounter = 5;
		mECGWaveParas.xStep = 2;
		mECGWaveDraw = new Wave(sfvECGWave,mECGWaveParas);
		
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}
    	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			if(vwDivider0.getVisibility() == View.GONE)
			{
				UIActions.ShowMainScreen(); 
			}
			else
			{
				utils.ExitWithToat(MainActivity.this);
			}
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		MainActivity.mBluetoothUtils.UnRegisterRecevier();
		UIActions.isMainScreenShow = true;
		super.onDestroy();
	}



	private void UI_WidgetInit() {
		// TODO Auto-generated method stub
		vwDivider0  =                  findViewById(R.id.vwDevider0);	
		vwDivider1  =                  findViewById(R.id.vwDevider1);
		vwDivider2  =                  findViewById(R.id.vwDevider2);
		IsMainShow  = vwDivider0;
		rlSpO2      = (RelativeLayout) findViewById(R.id.rlSpO2);
		rlECG       = (RelativeLayout) findViewById(R.id.rlECG);
	    rlNIBP_TEMP = (RelativeLayout) findViewById(R.id.rlNIBP_TEMP);
	    llBCR       = (LinearLayout)   findViewById(R.id.llBCR);
	    llNTC       = (LinearLayout)   findViewById(R.id.llNTC);
	    llBT        = (LinearLayout)   findViewById(R.id.llBT);
	    llBTSelect  = (LinearLayout)   findViewById(R.id.llBTSelect);
	    llCFG       = (LinearLayout)   findViewById(R.id.llCFG);
	    llCfgDedail = (LinearLayout)   findViewById(R.id.llConfigDedail);
	    ibBluetooth = (ImageButton)    findViewById(R.id.ibBluetooth);
	    ibRecord    = (ImageButton)    findViewById(R.id.ibRecord);
	    ibConfig    = (ImageButton)    findViewById(R.id.ibConfig);
	    lvBtDevices = (ListView)       findViewById(R.id.lvBtDevices);
	    btnResearch = (Button)         findViewById(R.id.btnResearch);
	    pbSearch    = (ProgressBar)    findViewById(R.id.pbSearch);
	    tvSpO2      = (TextView)       findViewById(R.id.tvSpO2);
	    tvPR        = (TextView)       findViewById(R.id.tvPR);
	    sfvSpO2Wave = (SurfaceView)    findViewById(R.id.sfvSpO2Wave);
	    tvResp      = (TextView)       findViewById(R.id.tvResp);
	    tvHR        = (TextView)       findViewById(R.id.tvHR);
	    sfvECGWave  = (SurfaceView)    findViewById(R.id.sfvECGWave);
	    tvTemp      = (TextView)       findViewById(R.id.tvTemp);
	    ivNIBP      = (ImageView)      findViewById(R.id.ibNIBP);
	    tvHighPressure = (TextView)    findViewById(R.id.tvHighPressure);
	    tVLowPressure  = (TextView)    findViewById(R.id.tvLowPressure);

	    
	    mContext    =  MainActivity.this;
	    
	    rlSpO2.setOnClickListener(this);
	    rlECG.setOnClickListener(this);
	    rlNIBP_TEMP.setOnClickListener(this);
	    llBCR.setOnClickListener(this);
	    ibBluetooth.setOnClickListener(this);
	    ibRecord.setOnClickListener(this);
	    ibConfig.setOnClickListener(this);
	    btnResearch.setOnClickListener(this);
	    ivNIBP.setOnClickListener(this);
	    
	    mBTDevicesAdapter =  new UIActions().new BTDevicesAdapter(this);
	    lvBtDevices.setAdapter(mBTDevicesAdapter);
	    lvBtDevices.setOnItemClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		UIActions.OnClick(view,mBluetoothUtils);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		//mBluetoothUtils.UnRegisterRecevier();
		
		BluetoothDevice device = (BluetoothDevice) mBTDevicesAdapter.getItem(position);
		if(device != null)
		{
			mBluetoothUtils.BluetoothServiceInit(MainActivity.this, mMainHandler, device);
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mMainHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) 
			{
				case CONST.MESSAGE_ECG_PARAMS:
					UIActions.RefreshResp(msg.arg1);
					UIActions.RefreshHR(msg.arg2);
					break;
			    case CONST.MESSAGE_SPO2_PARAM:
			    	UIActions.RefreshSpO2(msg.arg1);
			    	UIActions.RefreshPR(msg.arg2);
			    	break;
			    case CONST.MESSAGE_TEMP_PARAMS:
			    	UIActions.RefreshTemp(msg.arg1,msg.arg2);
			    	break;
			    case CONST.MESSAGE_NIBP_PARAMS:
			    	UIActions.RefreshNIBP(msg.arg1,msg.arg2);
			    	break;
				case CONST.MESSAGE_BLUETOOTH_START_SCAN:
					mBTDevicesAdapter.clear();
					mBTDevicesAdapter.notifyDataSetChanged();
					UIActions.ResearchBtnHide();
					break;
					
				case CONST.MESSAGE_BLUETOOTH_STOP_SCAN:
					UIActions.ResearchBtnShow();
					break;
					
				case CONST.MESSAGE_BLUETOOTH_A_DEVICE:
					mBTDevicesAdapter.addDevice((BluetoothDevice)msg.obj);
					mBTDevicesAdapter.notifyDataSetChanged();
					break;
					
				case CONST.MESSAGE_BLUETOOTH_STATE_CHANGE:
					{
						switch(msg.arg1)
						{
							case BluetoothChatService.STATE_CONNECTING:
								mBtConnectProgressDialog = ProgressDialog.show(MainActivity.this, null, getResources().getString(R.string.wait_for_connect));
								UIActions.ShowMainScreen();
								break;
							case BluetoothChatService.STATE_CONNECTED:
								if(mBtConnectProgressDialog != null)
								{
									mBtConnectProgressDialog.dismiss();
									
								}
								break;
							default:
								break;
						}
						break;
					}
					
				case CONST.MESSAGE_BLUETOOTH_DEVICE_NAME:
					String strName = msg.getData().getString(CONST.DEVICE_NAME);
					Toast.makeText(MainActivity.this, getResources().getString(R.string.connect_successfully)+" "+strName, Toast.LENGTH_SHORT).show();
					break;
				case CONST.MESSAGE_BLUETOOTH_CONNECT_FAIL:
					if(mBtConnectProgressDialog != null)
					{
						mBtConnectProgressDialog.dismiss();
					}
					break;
					
				case CONST.MESSAGE_BLUETOOTH_TOAST:
					String strToast = msg.getData().getString(CONST.TOAST);
					Toast.makeText(MainActivity.this, strToast, Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
			}
		}
	};

}
