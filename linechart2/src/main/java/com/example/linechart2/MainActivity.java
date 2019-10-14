package com.example.linechart2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LineChart lineChart = findViewById(R.id.lineChart);

        // 设置x轴和y轴的点
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new Entry(i, new Random().nextInt(300)));
        }
        // 把数据赋值到线条
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setColor(Color.parseColor("#7d7d7d"));  //线条颜色
        dataSet.setCircleColor(Color.parseColor("#7d7d7d"));
        dataSet.setLineWidth(1f);

        // 设置数据刷新图表
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart = setAxis(lineChart);
        lineChart.invalidate();
    }

    /**
     * 设置x和y轴的格式
     */
    public LineChart setAxis(LineChart lineChart){
        // 设置Y轴
        YAxis rightAxis = lineChart.getAxisRight();

        rightAxis.setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setEnabled(false);

        //设置y轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int)value + 1).concat("月");
            }
        });
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);    //是否绘制轴线
        xAxis.setDrawGridLines(false);  //设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);  //绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //设置x轴的显示位置
        xAxis.setGranularity(1f);

        //透明化图例，隐藏备注
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);

        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
        return lineChart;
    }
}
