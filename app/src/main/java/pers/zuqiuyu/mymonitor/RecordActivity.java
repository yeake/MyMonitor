package pers.zuqiuyu.mymonitor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import pers.zuqiuyu.mymonitor.utils.CONST;
import pers.zuqiuyu.mymonitor.utils.IOtxt;

public class RecordActivity extends Activity implements View.OnClickListener {

    private TextView tvRESP,tvHR,tvSPO2,tvPR,tvSBP,tvDBP,tvTEMP;
    private RelativeLayout static_chart_line_layout;
    private GraphicalView chart;
    private String title = "ECG";
    public  Double[] ecg;
    public  String[] record;
    public  String time=null;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        time = getIntent().getStringExtra("time");
        progressDialog = ProgressDialog.show(this, "提示", "拼命加载中，请稍后……");
        progressDialog.show();
        new TaskThread().start();

    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    progressDialog.dismiss();
                    Log.e("tag", "-->回到主线程刷新ui任务");
                    initChart();
                }
                break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDiagnosis:
                Intent intent = new Intent(this,DiagnosisActivity.class);
                intent.putExtra("time",time);
                startActivity(intent);
                break;
        }
    }

    class TaskThread extends Thread {
        public void run() {
            Log.e("tag", "开始执行费时任务");
            try {
                Thread.sleep(1000);
                record = IOtxt.readRecord(CONST.FilePath+time);
                ecg = IOtxt.readECG(CONST.FilePath+time+"ECG");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("tag","执行完毕");
            handler.sendEmptyMessage(0);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        tvRESP = findViewById(R.id.tvRESP);
        tvHR = findViewById(R.id.tvHR);
        tvSPO2 = findViewById(R.id.tvSPO2);
        tvPR = findViewById(R.id.tvPR);
        tvSBP = findViewById(R.id.tvSBP);
        tvDBP = findViewById(R.id.tvDBP);
        tvTEMP = findViewById(R.id.tvTEMP);
        findViewById(R.id.btnDiagnosis).setOnClickListener(this);
        static_chart_line_layout = (RelativeLayout) findViewById(R.id.static_chart_line_layout);

    }

    private void initChart() {


        tvRESP.setText(record[0]);
        tvHR.setText(record[1]);
        tvSPO2.setText(record[2]);
        tvPR.setText(record[3]);
        tvSBP.setText(record[4]);
        tvDBP.setText(record[5]);
        tvTEMP.setText(record[6]);

        chart = ChartFactory.getLineChartView(this, getDataset(), getRender());
        static_chart_line_layout.addView(chart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

    }


    private XYMultipleSeriesDataset getDataset() {
        XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("折线");
        for (int i = 0;i<ecg.length;i++)
            series.add(i,ecg[i]);
        mDataset.addSeries(series);
        return mDataset;
    }
    public XYMultipleSeriesRenderer getRender(){
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        //设置图表中曲线本身的样式，包括颜色、点的大小以及线的粗细等
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.RED);
        r.setPointStyle(PointStyle.POINT);
        r.setFillPoints(true);
        r.setLineWidth(3);
        renderer.addSeriesRenderer(r);
        renderer.setChartTitle(title);
        renderer.setXTitle("x");
        renderer.setYTitle("y");
        renderer.setXAxisMin(0);
        renderer.setXAxisMax(1000);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(200);
        renderer.setAxesColor(Color.GREEN);
        renderer.setLabelsColor(Color.GRAY);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GREEN);
        renderer.setXLabels(40);
        renderer.setYLabels(20);
        renderer.setLabelsTextSize(15);
        renderer.setPanEnabled(true,false);//设置X,Y轴是否被拖动
        renderer.setPanLimits(new double[]{0,1000000,0,0}); //设置拖动范围
        renderer.setXTitle("时间（ms）");
        renderer.setYTitle("电压(mv)");
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setPointSize((float) 2);
        renderer.setShowLabels(false); //设置是否显示坐标轴
        renderer.setShowLegend(false);
        return renderer;
    }
}
