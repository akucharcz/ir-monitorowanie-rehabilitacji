package com.ibm.ir;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.ibm.ir.model.ChartStructure;
import com.ibm.ir.model.LoginStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class HistoryMenuActivity extends AppCompatActivity {
    String period;
    LineChartView lineChartView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_menu);
        Button hourButton = (Button) findViewById(R.id.button);
        Button dayButton = (Button) findViewById(R.id.button2);
        Button weekButton = (Button) findViewById(R.id.button3);
        Button monthButton = (Button) findViewById(R.id.button4);
        lineChartView = (LineChartView) findViewById(R.id.chart);
        hourButton.setOnClickListener(v -> {
            resetChart();
            period = "1";
            ChartStructure chartStructure = new ChartStructure(LoginActivity.username, period);
            ApiUtils.getAPIService().postLogin(chartStructure)
                    .enqueue(new Callback<List<Integer>>() {
                        @Override
                        public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                            if (response.isSuccessful()) {
                                drawChart(response.body());
                            } else
                                Toast.makeText(HistoryMenuActivity.this, "Problem z pobraniem danych", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<List<Integer>> call, Throwable t) {
                            Toast.makeText(HistoryMenuActivity.this, "Problem z pobraniem danych", Toast.LENGTH_LONG).show();
                        }
                    });
        });
        dayButton.setOnClickListener(v -> {
            resetChart();
            period = "2";
            ChartStructure chartStructure = new ChartStructure(LoginActivity.username, period);
            ApiUtils.getAPIService().postLogin(chartStructure)
                    .enqueue(new Callback<List<Integer>>() {
                        @Override
                        public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                            if (response.isSuccessful()) {
                                drawChart(response.body());
                            } else
                                Toast.makeText(HistoryMenuActivity.this, "Problem z pobraniem danych", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<List<Integer>> call, Throwable t) {
                            Toast.makeText(HistoryMenuActivity.this, "Problem z pobraniem danych", Toast.LENGTH_LONG).show();
                        }
                    });
        });
        weekButton.setOnClickListener(v -> {
            resetChart();
            period = "3";
            ChartStructure chartStructure = new ChartStructure(LoginActivity.username, period);
            ApiUtils.getAPIService().postLogin(chartStructure)
                    .enqueue(new Callback<List<Integer>>() {
                        @Override
                        public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                            if (response.isSuccessful()) {
                                drawChart(response.body());
                            } else
                                Toast.makeText(HistoryMenuActivity.this, "Problem z pobraniem danych", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<List<Integer>> call, Throwable t) {
                            Toast.makeText(HistoryMenuActivity.this, "Problem z pobraniem danych", Toast.LENGTH_LONG).show();
                        }
                    });
        });
        monthButton.setOnClickListener(v -> {
            resetChart();
            period = "4";
            ChartStructure chartStructure = new ChartStructure(LoginActivity.username, period);
            ApiUtils.getAPIService().postLogin(chartStructure)
                    .enqueue(new Callback<List<Integer>>() {
                        @Override
                        public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                            if (response.isSuccessful()) {
                                drawChart(response.body());

                            } else
                                Toast.makeText(HistoryMenuActivity.this, "Problem z pobraniem danych", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<List<Integer>> call, Throwable t) {
                            Toast.makeText(HistoryMenuActivity.this, "Problem z pobraniem danych", Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }

    private void drawChart(List<Integer> call) {
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));
        int[] hrvalue_array = new int[call.size()];
        for (int i = 1; i < call.size(); i++) {
            hrvalue_array[i] = call.get(i);
        }
        for (int i = 1; i < hrvalue_array.length; i++){
            yAxisValues.add(new PointValue(i, hrvalue_array[i]));
        }
        for (int i = 1; i < hrvalue_array.length; i++){
            yAxisValues.add(new PointValue(i, hrvalue_array[i]));
        }

        List lines = new ArrayList();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(axis);
        Axis yAxis = new Axis();
        yAxis.setName("EMG");
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);
        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 1000;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }
    private void resetChart() {
        lineChartView.invalidate();
    }
}
