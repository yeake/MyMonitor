package pers.zuqiuyu.mymonitor;

import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import pers.zuqiuyu.mymonitor.bluetooth.BluetoothUtils;
import pers.zuqiuyu.mymonitor.utils.CONST;


public class UIActions
{
	static public boolean isMainScreenShow = true;

	
	public static void OnClick(View view, BluetoothUtils mBluetoothUtils)
	{
		
		switch (view.getId()) {
			case R.id.rlSpO2:
						RLSpO2Show();	
					    break;
					    
			case R.id.rlECG:
						RLECGShow();
					    break;
					    
//			case R.id.rlNIBP_TEMP:
//						RLNIBP_TEMPShow();	
//					    break;
					    
			case R.id.llBCR:
						RLConfigShow();
					    break;
					    
			case R.id.ibBluetooth:
				        BluetoothSelectShow(mBluetoothUtils);
				        RLConfigShow();				       
				        break;
			case R.id.ibConfig:
		                CfgDetailShow();
		                RLConfigShow();
				        break;
			case R.id.ibRecord:
				        RecordShow();
				        RLConfigShow();
				        break;
			case R.id.btnResearch:
				        mBluetoothUtils.StartScanDevices();
				        break;
			case R.id.ibNIBP:
				        
				        MonitorActivity.mBluetoothUtils.SendCMD(CONST.START_NIBP);
				        break;
				        
			default:
				        break;
		}
	}

	private static void RecordShow() {
		// TODO Auto-generated method stub
		if(!isMainScreenShow)
		{
			//Toast.makeText(MonitorActivity.mContext, MonitorActivity.mContext.getResources().getString(R.string.unfinishedfunc), Toast.LENGTH_LONG).show();
			Intent intent = new Intent(MonitorActivity.mContext,RecordListActivity.class);
			MonitorActivity.mContext.startActivity(intent);
		}
	}

	private static void CfgDetailShow() {
		// TODO Auto-generated method stub
		if(!isMainScreenShow)
		{
			Intent intent = new Intent(MonitorActivity.mContext,SettingsActivity.class);
            MonitorActivity.mContext.startActivity(intent);
		}
	}

	private static void BluetoothSelectShow(final BluetoothUtils mBluetoothUtils) {
		// TODO Auto-generated method stub
		
		if(!isMainScreenShow)
		{
			
			
			Animation animation = AnimationUtils.loadAnimation(MonitorActivity.mContext, R.anim.slide_left);
			animation.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					mBluetoothUtils.StartScanDevices();
				}
			});
			
			
			MonitorActivity.llBTSelect.startAnimation(animation);
			MonitorActivity.llBTSelect.setVisibility(View.VISIBLE);
			MonitorActivity.llCfgDedail.setVisibility(View.GONE);
			MonitorActivity.llBT.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 2.5f));
		    MonitorActivity.llCFG.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
			
		}
	}

	public static void ShowMainScreen() {
		// TODO Auto-generated method stub
	    DividerShow();
	    MonitorActivity.rlSpO2.setVisibility(View.VISIBLE);
	    MonitorActivity.rlECG.setVisibility(View.VISIBLE);
	    MonitorActivity.llNTC.setVisibility(View.VISIBLE);
	    MonitorActivity.rlNIBP_TEMP.setVisibility(View.VISIBLE);
	    MonitorActivity.llBCR.setVisibility(View.VISIBLE);
	    MonitorActivity.llBT.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
	    MonitorActivity.llCFG.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
	    MonitorActivity.llBTSelect.setVisibility(View.GONE);
	    MonitorActivity.llCfgDedail.setVisibility(View.GONE);
	    MonitorActivity.mECGWaveDraw.setVisibility(true);
		MonitorActivity.mSpO2WaveDraw.setVisibility(true);
	    
	    isMainScreenShow = true;
	}
	
	private static void RLConfigShow() {
		// TODO Auto-generated method stub
		if(isMainScreenShow) 
		{
			DividerHide();
			MonitorActivity.rlECG.setVisibility(View.GONE);
			MonitorActivity.mECGWaveDraw.setVisibility(false);
			MonitorActivity.rlSpO2.setVisibility(View.GONE);
			MonitorActivity.mSpO2WaveDraw.setVisibility(false);
			MonitorActivity.rlNIBP_TEMP.setVisibility(View.GONE);
			
			MonitorActivity.ibBluetooth.startAnimation(AnimationUtils.loadAnimation(MonitorActivity.mContext, R.anim.bt_in_translate_top));
			MonitorActivity.ibConfig.startAnimation(AnimationUtils.loadAnimation(MonitorActivity.mContext, R.anim.cfg_in_translate_top));
			MonitorActivity.ibRecord.startAnimation(AnimationUtils.loadAnimation(MonitorActivity.mContext, R.anim.rcd_in_translate_top));
			isMainScreenShow = false;
		}
	}

	
	private static void RLSpO2Show() {
		// TODO Auto-generated method stub
		if(!isMainScreenShow) return;
		
		DividerHide();
		MonitorActivity.rlECG.setVisibility(View.GONE);
		MonitorActivity.mECGWaveDraw.setVisibility(false);
		MonitorActivity.llNTC.setVisibility(View.GONE);
	}
	
	private static void RLECGShow() {
		// TODO Auto-generated method stub
		if(!isMainScreenShow) return;
		
		DividerHide();
		MonitorActivity.rlSpO2.setVisibility(View.GONE);
		MonitorActivity.mSpO2WaveDraw.setVisibility(false);
		MonitorActivity.llNTC.setVisibility(View.GONE);
	}


	private static void DividerShow() {
		// TODO Auto-generated method stub
		MonitorActivity.vwDivider0.setVisibility(View.VISIBLE);
		MonitorActivity.vwDivider1.setVisibility(View.VISIBLE);
		MonitorActivity.vwDivider2.setVisibility(View.VISIBLE);
		MonitorActivity.btnSave.setVisibility(View.VISIBLE);
	}

	private static void DividerHide() {
		// TODO Auto-generated method stub
		MonitorActivity.vwDivider0.setVisibility(View.GONE);
		MonitorActivity.vwDivider1.setVisibility(View.GONE);
		MonitorActivity.vwDivider2.setVisibility(View.GONE);
		MonitorActivity.btnSave.setVisibility(View.GONE);
	}

	public class BTDevicesAdapter extends BaseAdapter {

		private ArrayList<BluetoothDevice> mBluetootDevices;
        private LayoutInflater mInflator;
        private Context mContext;

        public BTDevicesAdapter(Context context) {
            super();
            this.mContext = context;
            mBluetootDevices = new ArrayList<BluetoothDevice>();
            mInflator = ((Activity) mContext).getLayoutInflater();
        }
        
        public void addDevice(BluetoothDevice device)
        {
            if(!mBluetootDevices.contains(device))	
            {
            	mBluetootDevices.add(device);
            }
        }
        
        public void clear() {
        	mBluetootDevices.clear();
        }
        
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mBluetootDevices.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mBluetootDevices.get(position);
		}
		
		@Override
		public long getItemId(int i) {
			// TODO Auto-generated method stub
			return i;
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			view = mInflator.inflate(R.layout.bt_device_item, null);
			TextView tvName     = (TextView) view.findViewById(R.id.device_name);
			TextView tvAddr     = (TextView) view.findViewById(R.id.device_address);
			TextView tvIsPaired = (TextView) view.findViewById(R.id.device_isPaired); 
			ImageView ivBtImage = (ImageView) view.findViewById(R.id.device_image);
			
			String strName = mBluetootDevices.get(position).getName();
			String strAddr = mBluetootDevices.get(position).getAddress();
			Boolean isPaired = (mBluetootDevices.get(position).getBondState() == BluetoothDevice.BOND_BONDED) ? true:false;
			
			
			if(strName != null && strName.equals(mContext.getResources().getString(R.string.berry_bluetooth_name)))
			{
				ivBtImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.berry_bt_image));
			}
			else
			{
				ivBtImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.unknown_bt_image));	
			}
			
			if (strName != null && strName.length() > 0)
			{
				tvName.setText(strName);
			}
			else
			{
			    tvName.setText(R.string.unknown_device);	
			}
			
			tvAddr.setText(strAddr);
			
			if(isPaired)
			{
				tvIsPaired.setText(R.string.paired);
			}
			
			return view;
		}
	}

	public static void ResearchBtnHide() {
		// TODO Auto-generated method stub
		MonitorActivity.btnResearch.setVisibility(View.GONE);
		MonitorActivity.pbSearch.setVisibility(View.VISIBLE);
	}

	public static void ResearchBtnShow() {
		// TODO Auto-generated method stub
		MonitorActivity.pbSearch.setVisibility(View.GONE);
		MonitorActivity.btnResearch.setVisibility(View.VISIBLE);
	}

	public static void RefreshSpO2(int arg1) {
		// TODO Auto-generated method stub
		if(arg1 != CONST.SPO2_INVALID_VALUE)
    	{
    		MonitorActivity.tvSpO2.setText(arg1+"");
    	}
    	else {
    		MonitorActivity.tvSpO2.setText(R.string.tvSpO2Invalid);
		}
	}

	public static void RefreshPR(int arg2) {
		// TODO Auto-generated method stub
		if(arg2 != CONST.PR_INVALID_VALUE)
    	{
			MonitorActivity.tvPR.setText(arg2+"");
    	}
    	else {
    		MonitorActivity.tvPR.setText(R.string.tvPRInvalid);
		}
	}

	public static void RefreshResp(int arg1) {
		// TODO Auto-generated method stub
		if(arg1 != CONST.RESP_INVALID_VALUE)
    	{
			MonitorActivity.tvResp.setText(arg1+"");
    	}
    	else {
    		MonitorActivity.tvResp.setText(R.string.tvRespInvalid);
		}
		
	}

	public static void RefreshHR(int arg2) {
		// TODO Auto-generated method stub
		if(arg2 != CONST.HR_INVALID_VALUE)
    	{
			MonitorActivity.tvHR.setText(arg2+"");
    	}
    	else {
    		MonitorActivity.tvHR.setText(R.string.tvHRInvalid);
		}
		
	}

	public static void RefreshTemp(int arg1, int arg2) {
		// TODO Auto-generated method stub
		
		if(arg1==0 && arg2 ==0)
		{
			MonitorActivity.tvTemp.setText(MonitorActivity.mContext.getResources().getString(R.string.tvTempInvalid));
			return;
		}
		
		float temp = arg1 + ((float)arg2)/10;
		
		MonitorActivity.tvTemp.setText(String.format("%.1f",temp));
	}

	public static void RefreshNIBP(int arg1, int arg2) {
		// TODO Auto-generated method stub
		
		if(arg1 == 0 && arg2 == 0)
		{
			MonitorActivity.tvHighPressure.setText(MonitorActivity.mContext.getResources().getString(R.string.tvNIBPInvalid));
			MonitorActivity.tVLowPressure.setText(MonitorActivity.mContext.getResources().getString(R.string.tvNIBPInvalid));
			
			return;
		}
		MonitorActivity.tvHighPressure.setText(arg1+"");
		MonitorActivity.tVLowPressure.setText(arg2+"");
	}
}