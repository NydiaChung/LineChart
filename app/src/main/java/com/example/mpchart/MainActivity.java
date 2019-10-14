package com.example.mpchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BarChart barChart = findViewById(R.id.ChartTest);   //这里我们定义一下我们界面的控件
        barChart = initBarChart(barChart);//这里调用方法初始化柱状图
        BarData barData = setBarData(); //这里调用方法初始化模拟数据
        barChart.setData(barData);  //这里将模拟数据用于柱状图，在柱状图显示
        barChart.invalidate();      //这里让柱状图在填充数据后刷新
    }

    /**
     * 这个方法用于设置柱状图的数据
     * @return
     */
    public BarData setBarData(){
        List<BarEntry> entries = new ArrayList<>();//这里定义一个存放数据的容器
        //这里用for循环为容器填充数据
        //这里填充了12个200以内的随机数
        for(int i = 0;i < 12;i++){
            entries.add(new BarEntry(i,new Random().nextInt(200)));
        }
        BarDataSet barDataSet = new BarDataSet(entries,"测试用数据");    //这里设置了数据集
        BarData barData = new BarData(barDataSet);
        return barData;//返回可用于柱状图的数据
    }

    /**
     * 这个方法用于初始化柱状图
     * @return
     */
    public BarChart initBarChart(BarChart barChart){    //首先传入一个柱状图实例
        barChart.setDescription(null);  //这里设置柱状图的解释性文字为空
        barChart.setDrawBarShadow(false);   //设置柱状图每一条柱子的阴影不显示
        barChart.setDrawValueAboveBar(true);//设置柱状图每一条柱子的数值显示
        XAxis xAxis = barChart.getXAxis();  //获取柱状图的x轴
        YAxis yAxisLeft = barChart.getAxisLeft();   //这里获取了柱状图左侧的y轴
        YAxis yAxis1Right = barChart.getAxisRight();//这里获取了柱状图右侧的y轴
        setAxis(xAxis,yAxisLeft,yAxis1Right);   //这里调用方法设置柱状图的轴线
        return barChart;        //这里返回初始化完成的柱状图实例
    }

    /**
     * 这个方法用于设置柱状图的X轴Y轴
     */
    public void setAxis(XAxis xAxis, YAxis leftYaxis,YAxis rightYaxis){
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  //这里设置x轴在柱状图底部显示
        xAxis.setAxisLineWidth(1);  //这里设置x轴宽度
        xAxis.setAxisMinimum(0);    //这里设置x轴从0刻度开始绘画
        xAxis.setDrawAxisLine(true);//这里设置x轴显示轴线
        xAxis.setDrawGridLines(false);//这里设置x轴的表格线不显示
        xAxis.setEnabled(true);     //这里设置x轴显示

        leftYaxis.setAxisMinimum(0);//设置y轴从0刻度开始
        leftYaxis.setDrawGridLines(false);//这里设置y轴的表格线不显示
        leftYaxis.setDrawAxisLine(true);//这里设置y轴显示轴线
        leftYaxis.setAxisLineWidth(1);//这里设置y轴宽度
        leftYaxis.setEnabled(true);//这里设置左侧的y轴显示

        rightYaxis.setAxisMinimum(0);//设置y轴从0刻度开始
        rightYaxis.setDrawGridLines(false);//这里设置y轴的表格线不显示
        rightYaxis.setDrawAxisLine(true);//这里设置y轴显示轴线
        rightYaxis.setAxisLineWidth(1);//这里设置y轴宽度
        rightYaxis.setEnabled(false);//这里设置右侧的y轴不显示
    }
}
