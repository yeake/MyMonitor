package pers.zuqiuyu.mymonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import pers.zuqiuyu.mymonitor.utils.CONST;
import pers.zuqiuyu.mymonitor.utils.IOtxt;

public class RecordListActivity extends Activity implements AdapterView.OnItemClickListener {

    public static String filesList[] = null;
    private ListView lvRecord;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordlist);
        System.out.println("RecordListActivity");
    }

    @Override
    protected void onResume() {
        lvRecord = (ListView) findViewById(R.id.lvRecord);
        lvRecord.setOnItemClickListener(this);
        filesList = IOtxt.listFile(CONST.FilePath);
        Log.e("filesList",filesList.length+"");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,filesList);
        lvRecord.setAdapter(adapter);
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try{
            String time = filesList[position];
            Intent intent = new Intent(this,RecordActivity.class);
            intent.putExtra("time",time);
            startActivity(intent);
        }
        catch (Exception e){

            Log.e("tag","出错了");
        }
    }
}
