package com.example.linechart3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.SystemClock.sleep;
import static com.github.mikephil.charting.utils.ColorTemplate.VORDIPLOM_COLORS;

/**
 * @author david
 */
public class MainActivity extends AppCompatActivity {

    private float xData = 0;
    private static boolean status = false;
    private LineChart lineChart;
    private Button startButton;
    private Button stopButton;
    private List<Float> timeQueue = new ArrayList<Float>();
    private List<Float> temperatureQueue = new ArrayList<Float>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChart = findViewById(R.id.lineChart);
        lineChart = setAxis(lineChart);
        status = true;
        flush();//清空
        /*一般主要用在IO中，即清空缓冲区数据，就是说你用读写流的时候，
        其实数据是先被读到了内存中，然后用数据写到文件中，当你数据读完
        的时候不代表你的数据已经写完了，因为还有一部分有可能会留在内存
        这个缓冲区中。这时候如果你调用了 close()方法关闭了读写流，那么
        这部分数据就会丢失，所以应该在关闭读写流之前先flush()，先清空数据。 */

        // 关闭
        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = false;

            }
        });
        // 重新开始
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = true;
                flush();
            }
        });

    }

    public void flush(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 写子线程中的操作
                while (status){
                    try {
                        sleep(100);
                        xData += 0.1;
                        timeQueue.add(xData);
                        temperatureQueue.add((float) (Math.random()));
                        //消息要先传进Message中，再由Message传递给Handler处理
                        Message msg = Message.obtain();
                        msg.what = 1;//Message类有属性字段arg1、arg2、what...
                        mHandler.sendMessage(msg);//sendMessage()用来传送Message类的值到mHandler
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        }).start();
    }

    Handler mHandler = new Handler(){

        //handleMessage为处理消息的方法
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setLineChart(lineChart);
            lineChart.invalidate();
        }
    };


    /**
     * 设置axis
     */
    public LineChart setAxis(LineChart lineChart){
        Description description = new Description();
        description.setTextColor(Color.WHITE);
        description.setTextSize(0);
        lineChart.setDescription(description);
        // 隐藏右边y轴
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);   //设置为true，则绘制网格线。

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //设置x轴的显示位置
        xAxis.setDrawGridLines(false);   //设置为true，则绘制网格线。
        xAxis.setLabelCount(1000);
        xAxis.setDrawLabels(false);
        xAxis.setGranularity(1);
        //折线图例 标签 设置

        Legend legend =lineChart.getLegend();

        legend.setTextSize(12);

        // 图例间隔
        legend.setXEntrySpace(30);

        // 图例和文字间隔
        legend.setFormToTextSpace(10);

        // 线高
        legend.setFormLineWidth(4);

        // 线宽
        legend.setFormSize(30);

        //标签中文字的颜色
        legend.setTextColor(Color.parseColor("#333333"));

        legend.setForm(Legend.LegendForm.LINE);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        return lineChart;
    }

    /**
     * 设置数据
     */
    public LineChart setLineChart(LineChart lineChart){
        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < timeQueue.size(); i++) {
            values1.add(new Entry(timeQueue.get(i),temperatureQueue.get(i)));
        }

        //LineDataSet每一个对象就是一条连接线
        LineDataSet set1;

        //判断图表中原来是否有数据
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0){
            // 获得数据
            set1 = (LineDataSet)lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            // 刷新数据
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        }else {
            //设置数据1  参数1：数据源 参数2：图例名称
            set1 = new LineDataSet(values1, "硫含量曲线 单位 y:mA");
            set1.setColor(Color.BLACK);
            set1.setLineWidth(0.5f);  //设置线宽
            set1.setCircleRadius(1f);   //设置焦点圆心的大小
//            set1.enableDashedLine(10f, 5f, 0f);   //点击后的高亮线的显示样式
            set1.setHighlightLineWidth(2f); //设置点击交点后显示高亮线宽
            set1.setHighLightColor(Color.BLACK);  //是否禁用点击高亮线
            set1.setDrawFilled(false);  //设置禁用范围背景填充
            set1.setValueTextSize(0f);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);


            //保存LineDataSet集合
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            //创建LineData对象 属于LineChart折线图的数据集合
            LineData data = new LineData(dataSets);
            // 添加到图表中
            lineChart.setData(data);
        }
        return lineChart;
    }
}
