package pers.zuqiuyu.mymonitor.utils;


import android.os.Environment;

import java.util.ArrayList;

public class CONST
{
	public static final int MESSAGE_BLUETOOTH_STATE_CHANGE = 0;
	public static final int MESSAGE_BLUETOOTH_DEVICE_NAME  = 1;
	public static final int MESSAGE_BLUETOOTH_TOAST        = 2;
	public static final int MESSAGE_BLUETOOTH_READ         = 3;
	public static final int MESSAGE_BLUETOOTH_WRITE        = 4;
	public static final int MESSAGE_BLUETOOTH_CONNECT_FAIL = 5;
	
	public static final int MESSAGE_BLUETOOTH_START_SCAN   = 6;
	public static final int MESSAGE_BLUETOOTH_STOP_SCAN    = 7;
	public static final int MESSAGE_BLUETOOTH_A_DEVICE     = 8;
	
	public static final int MESSAGE_SPO2_PARAM             = 9;
	public static final int MESSAGE_SPO2_WAVE              = 10;
	public static final int MESSAGE_ECG_PARAMS             = 11;
	public static final int MESSAGE_TEMP_PARAMS            = 12;
	public static final int MESSAGE_NIBP_PARAMS            = 13;
	
	public static final int SPO2_INVALID_VALUE             = 127;
	public static final int PR_INVALID_VALUE               = 255;
	public static final int RESP_INVALID_VALUE             = 0;
	public static final int HR_INVALID_VALUE               = 0;
	
	public static byte[] START_NIBP = new byte[]{0x55, (byte) 0xaa, 0x04, 0x02, 0x01, (byte) 0xf8};
	public static byte[] STOP_NIBP  = new byte[]{0x55, (byte) 0xaa, 0x04, 0x02, 0x00, (byte) 0xf9};

	
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST       = "toast";


	public static final String FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
										"/Android/data/com.zuqiuyu.mymonitor/data/";//数据存储路径

	public static String TIME = "00000000000000";
	//public static String filePathRecord = FilePath+time;//Record file
	//public static String filePathECG = filePathRecord+"ECG";//ECG file

	public static ArrayList<String> listRecord =new ArrayList<String>();
	public static ArrayList<String> listECG =new ArrayList<String>();

	public static ArrayList<String> listRESP =new ArrayList<String>();
	public static ArrayList<String> listHR =new ArrayList<String>();
	public static ArrayList<String> listSPO2 =new ArrayList<String>();
	public static ArrayList<String> listPR =new ArrayList<String>();
	public static ArrayList<String> listSBP =new ArrayList<String>();
	public static ArrayList<String> listDBP =new ArrayList<String>();
	public static ArrayList<String> listTEMP =new ArrayList<String>();
	
}