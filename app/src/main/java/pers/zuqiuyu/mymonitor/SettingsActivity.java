package pers.zuqiuyu.mymonitor;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;


public class SettingsActivity extends Activity implements OnClickListener{

    private RelativeLayout rlContact;
    private RelativeLayout rlAbout;
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings);  
        
        rlAbout = (RelativeLayout) findViewById(R.id.rlAbout);
        rlContact = (RelativeLayout) findViewById(R.id.rlContact);
        rlAbout.setOnClickListener(this);
        rlContact.setOnClickListener(this);
       
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		   case R.id.rlContact:
			   startActivity(new Intent(SettingsActivity.this, ContactUsActivity.class));
			   
			   break;
		   case R.id.rlAbout:
			   startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
			   break;
		}
	}

}