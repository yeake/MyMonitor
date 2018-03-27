package pers.zuqiuyu.mymonitor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pers.zuqiuyu.mymonitor.utils.CONST;
import pers.zuqiuyu.mymonitor.utils.WebUtil;
import com.loopj.android.http.*;

import org.apache.http.Header;


public class DiagnosisActivity extends Activity implements View.OnClickListener {
    public String time;
    private TextView tvDiagnosis;
    String filePATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        time = getIntent().getStringExtra("time");
        filePATH = CONST.FilePath + time;
        tvDiagnosis = findViewById(R.id.tvDiagnosis);
        findViewById(R.id.btnUpload).setOnClickListener(this);
        findViewById(R.id.btnGetDiagnosis).setOnClickListener(this);

    }

    //uiHandler在主线程中创建，所以自动绑定主线程
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONST.MESSAGE_UPLOAD_SUCCESS:
                    tvDiagnosis.setHint("上传成功,等候医生诊断");
                    tvDiagnosis.setHintTextColor(Color.rgb(255, 0, 0));
                    Toast.makeText(DiagnosisActivity.this, "上传成功,等候医生诊断", Toast.LENGTH_SHORT).show();
                    Log.e("zuqiuyu", msg.obj.toString());
                    break;
                case CONST.MESSAGE_GETDIAGNOSIS_SUCCESS:
                    tvDiagnosis.setHint(msg.obj.toString());
                    tvDiagnosis.setHintTextColor(Color.rgb(255, 0, 0));
                    Log.e("zuqiuyu", msg.obj.toString());
                    break;
            }
        }
    };
    Runnable uploadTask = new Runnable() {
        @Override
        public void run() {
            // 在这里进行 http request.网络请求相关操作
            WebUtil.uploadFile(filePATH);
            WebUtil.uploadFile(filePATH + "ECG");
            Message msg = handler.obtainMessage();
            msg.what = CONST.MESSAGE_UPLOAD_SUCCESS;
            msg.obj = "上传成功";
            handler.sendMessage(msg);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpload:
                new Thread(uploadTask).start();
                break;
            case R.id.btnGetDiagnosis:
                getDiagnosis(time);
                break;
        }
    }

    public void getDiagnosis(String time) {
        String RequestURL = CONST.RequestURL + "servlet/GetDiagnosisServlet";
        RequestParams requestParams = new RequestParams();
        requestParams.add("time", time);
        new AsyncHttpClient().post(RequestURL, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    tvDiagnosis.setText(new String(responseBody));
                    Toast.makeText(DiagnosisActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                tvDiagnosis.setText("服务器连接失败！请稍候再试！");
                Toast.makeText(DiagnosisActivity.this, "服务器连接失败！请稍候再试！", Toast.LENGTH_SHORT).show();
            }

        });
    }
}

