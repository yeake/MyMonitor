package pers.zuqiuyu.mymonitor;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;



public class ContactUsActivity extends Activity implements OnClickListener
{
	private RelativeLayout rlEmail;
	private RelativeLayout rlTel;
	private RelativeLayout rlWebsite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contact_us);
		
		rlEmail = (RelativeLayout) findViewById(R.id.rlEmail);
		rlTel   = (RelativeLayout) findViewById(R.id.rlTel);
		rlWebsite = (RelativeLayout) findViewById(R.id.rlWebsite);
        
	    rlEmail.setOnClickListener(this);
		rlTel.setOnClickListener(this);
		rlWebsite.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rlTel:
			new AlertDialog.Builder(this) 
			                .setMessage(getResources().getString(R.string.areyousure))  
			                .setNegativeButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									Intent intentCall = new Intent();
									intentCall.setAction(Intent.ACTION_CALL);
									intentCall.setData(Uri.parse("tel:"+getResources().getString(R.string.contactTel)));
									startActivity(intentCall);
								}
							})
			                .setPositiveButton(getResources().getString(R.string.later), null)  
			                .show();  

			break;
		case R.id.rlEmail:
			Intent intentEmail = new Intent();
			intentEmail.setAction(Intent.ACTION_SENDTO);
			intentEmail.setData(Uri.parse("mailto:"+getResources().getString(R.string.ContactEmail)));
			startActivity(intentEmail);  
			break;
		case R.id.rlWebsite:
			Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+getResources().getString(R.string.contactWebsite)));
			startActivity(intentWeb);  
			break;
		default:
			break;
		}
	}
	
}