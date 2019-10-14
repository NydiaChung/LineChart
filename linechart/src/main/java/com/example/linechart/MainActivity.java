package com.example.linechart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LineChart lineChart = findViewById(R.id.lineChart);
        lineChart = initLineChart(lineChart);
        lineChart = setLineChart(lineChart);
        linechart.notifyDataSetChanged(); // let the chart know it's data changed
        lineChart.invalidate();//刷新
    }

    /**
     * 初始化
     */
    public LineChart initLineChart(LineChart lineChart){
        // 创建描述信息
        Description description = new Description();
        description.setText("测试图表");
        description.setTextColor(Color.RED);
        description.setTextSize(20);
        //设置图表描述信息
        lineChart.setDescription(description);
        //没有数据时显示的文字
        lineChart.setNoDataText("没有数据");
        //没有数据时显示文字的颜色
        lineChart.setNoDataTextColor(Color.BLUE);
        //chart 绘图区后面的背景矩形将绘制
        lineChart.setDrawGridBackground(false);
        //禁止绘制图表边框的线
        lineChart.setDrawBorders(false);
        return lineChart;
    }

    /**
     * 设置数据
     */
    public LineChart setLineChart(LineChart lineChart){
        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();

        values1.add(new Entry(4,10));
        values1.add(new Entry(6,15));
        values1.add(new Entry(9,20));
        values1.add(new Entry(12,25));
        values1.add(new Entry(15,30));

        values2.add(new Entry(3,110));
        values2.add(new Entry(6,115));
        values2.add(new Entry(9,130));
        values2.add(new Entry(12,85));
        values2.add(new Entry(15,90));

        //LineDataSet每一个对象就是一条连接线
        LineDataSet set1;
        LineDataSet set2;

        //判断图表中原来是否有数据
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0){
            // 获得数据
            set1 = (LineDataSet)lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            set2 = (LineDataSet)lineChart.getData().getDataSetByIndex(1);
            set2.setValues(values2);
            // 刷新数据
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        }else {
            //设置数据1  参数1：数据源 参数2：图例名称
            set1 = new LineDataSet(values1, "测试数据");
            set1.setColor(Color.BLACK);
            set1.setLineWidth(1f);   // 设置线宽
            set1.setCircleRadius(0f);   //设置焦点圆心的大小
            set1.enableDashedLine(10f, 5f, 0f);   //点击后的高亮线的显示样式
            set1.setHighlightLineWidth(2f); //设置点击交点后显示高亮线宽
            set1.setHighLightColor(Color.RED);  //是否禁用点击高亮线
            set1.setValueTextSize(9f);  //设置显示值的文字大小
            set1.setDrawFilled(false);  //设置禁用范围背景填充

            //格式化显示数据
            final DecimalFormat format = new DecimalFormat("###,###,##0");
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return format.format(value);
                }
            });

            //设置数据2
            set2 = new LineDataSet(values2, "测试数据2");
            set2.setColor(Color.GRAY);
            set2.setCircleColor(Color.GRAY);
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);
            set2.setDrawCircles(false);
            set2.setValueTextSize(0);

            //保存LineDataSet集合
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            dataSets.add(set2);
            //创建LineData对象 属于LineChart折线图的数据集合
            LineData data = new LineData(dataSets);
            // 添加到图表中
            lineChart.setData(data);
        }
        return lineChart;
    }
}
