package pers.zuqiuyu.mymonitor.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import pers.zuqiuyu.mymonitor.MainActivity;
import pers.zuqiuyu.mymonitor.utils.CONST;


public class BluetoothUtils 
{
	private static String Tag               = "BluetoothUtils";
	
	private Context              mContext;
	private BluetoothAdapter     mBluetoothAdapter;
	private Handler              mHandler;
	private BluetoothChatService mBluetoothChatService;
	
	public BluetoothUtils(Context context, Handler handler) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mHandler = handler;
	}
	
	/**
	 * Init
	 */
	public void Init()
	{
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		if(!mBluetoothAdapter.isEnabled())
		{
			mBluetoothAdapter.enable();
		}

	}
	
	/**
	 * Register Receiver
	 */
	public void RegisterRecevier()
	{
		IntentFilter intent = new IntentFilter();
		intent.addAction(BluetoothDevice.ACTION_FOUND);
		intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		intent.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		mContext.registerReceiver(mBtBoardCastReceiver, intent);
	}
	
	
	/**
	 * unRegister Receiver
	 */
	public void UnRegisterRecevier()
	{
		
		mContext.unregisterReceiver(mBtBoardCastReceiver);
	}
	
	
	/**
	 * define BoarfCastReceiver
	 */
	private BroadcastReceiver mBtBoardCastReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if(BluetoothDevice.ACTION_FOUND.equals(action))
			{
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Log.i(Tag, "Get BT device   name:" + device.getName() + "  mac:" + device.getAddress());
                mHandler.obtainMessage(CONST.MESSAGE_BLUETOOTH_A_DEVICE, device).sendToTarget();
			}
			else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
			{
				Log.i(Tag, "-----finishDiscovery----");
				mHandler.obtainMessage(CONST.MESSAGE_BLUETOOTH_STOP_SCAN).sendToTarget();
			}
			else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
			{
				Log.i(Tag, "-----startDiscovery----");
				mHandler.obtainMessage(CONST.MESSAGE_BLUETOOTH_START_SCAN).sendToTarget();
			}
		}
	};
	
	
	/**
	 * Scan Bluetooth Device
	 */
	public void StartScanDevices()
	{

		mBluetoothAdapter.startDiscovery();

	}

	/**
	 * Init Bluetooth chat Service
	 * @param context
	 * @param handler
	 * @param device
	 */
	public void BluetoothServiceInit(Context context, Handler handler, BluetoothDevice device)
	{
		if(mBluetoothChatService != null)
		{
			mBluetoothChatService.stop();
			mBluetoothChatService = null;
		}
		
		mBluetoothChatService = new BluetoothChatService(context,handler);
		mBluetoothChatService.connect(device, true);
	}

	public void StopBluetoothChatService() {
		// TODO Auto-generated method stub
		if(mBluetoothChatService!=null)
			mBluetoothChatService.stop();
		
		mBluetoothAdapter.disable();
		while(mBluetoothAdapter.isEnabled());
	}

	public void SendCMD(byte[] sTART_NIBP) {
		// TODO Auto-generated method stub
		if(mBluetoothChatService == null)
		{
			Toast.makeText(MainActivity.mContext, "please connect the bluetooth...", Toast.LENGTH_SHORT).show();
			return;
		}
		mBluetoothChatService.write(sTART_NIBP);
		
	}

	
	
    
}


















