package pers.zuqiuyu.mymonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class AboutActivity extends Activity implements OnClickListener
{

	private RelativeLayout rlAboutCompany;
	private RelativeLayout rlAboutApp;
	private TextView tvAppVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		
		
		PackageManager manager = this.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tvAppVersion = (TextView) findViewById(R.id.tvAppVersion);
		tvAppVersion.setText(getResources().getString(R.string.AboutApp)+ info.versionName);
		
		
		rlAboutApp = (RelativeLayout) findViewById(R.id.rlAboutApp);
		rlAboutCompany   = (RelativeLayout) findViewById(R.id.rlAboutCompany);
        
	    rlAboutApp.setOnClickListener(this);
		rlAboutCompany.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rlAboutApp:
			Intent intentMarket = new Intent();
			intentMarket.setAction(Intent.ACTION_VIEW);
			intentMarket.setData(Uri.parse("market://details?id=" + getPackageName()));
			startActivity(intentMarket);
			break;

		case R.id.rlAboutCompany:
			Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.berry-med.com"));
			startActivity(intentWeb);  
			break;
		default:
			break;
		}
	}
}