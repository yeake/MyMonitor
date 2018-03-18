package pers.zuqiuyu.mymonitor.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import pers.zuqiuyu.mymonitor.MainActivity;
import pers.zuqiuyu.mymonitor.R;


public class utils
{
	static long exitTime = 0;
    public static void ExitWithToat(Context context)
    {
    	if ((System.currentTimeMillis() - exitTime) > 2000)
    	{
		    Toast.makeText(context, context.getResources().getString(R.string.exit_tip), Toast.LENGTH_SHORT).show();
		    exitTime = System.currentTimeMillis();
		}
    	else
    	{
    		MainActivity.mBluetoothUtils.StopBluetoothChatService();
    		((Activity) context).finish();
		}
    }
}