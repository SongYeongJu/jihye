package com.example.administrator.jihye.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jihye.DBHelper.DBHelper;
import com.example.administrator.jihye.DataStructure.Travel;
import com.example.administrator.jihye.R;

import java.util.Calendar;

public class AddTravelActivity extends AppCompatActivity {
    private DBHelper dbHelper;

    private String travelName;
    private TextView travelNameTextView;

    private ImageView startCalImageView;
    private TextView startDayTextView;

    private ImageView finishCalImageView;
    private TextView finishDayTextView;

    private TextView countryTextView;
    private ImageView countryImageView;

    private Button confirmButton;

    public void clickSetDay(String c){
        if(c.equals("start")){
            Calendar cal = Calendar.getInstance();
            int nYear = cal.get(Calendar.YEAR);
            int nMon = cal.get(Calendar.MONTH);
            int nDay = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            String strDate = String.valueOf(dayOfMonth) + "-";
                            strDate += String.valueOf(monthOfYear+1) + "-";
                            strDate += String.valueOf(year);

                            startDayTextView.setText(strDate);
                            //Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_SHORT).show();
                        }
                    };

            DatePickerDialog oDialog = new DatePickerDialog(this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog,
                    mDateSetListener, nYear, nMon, nDay);
            oDialog.show();
        }else if(c.equals("finish")){
            Calendar cal = Calendar.getInstance();
            int nYear = cal.get(Calendar.YEAR);
            int nMon = cal.get(Calendar.MONTH);
            int nDay = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            String strDate = String.valueOf(dayOfMonth) + "-";
                            strDate += String.valueOf(monthOfYear+1) + "-";
                            strDate += String.valueOf(year);

                            finishDayTextView.setText(strDate);
                            //Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_SHORT).show();
                        }
                    };

            DatePickerDialog oDialog = new DatePickerDialog(this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog,
                    mDateSetListener, nYear, nMon, nDay);
            oDialog.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel);

        dbHelper=DBHelper.getInstance(this);

        travelName=getIntent().getStringExtra("travelName");

        travelNameTextView=(TextView)findViewById(R.id.TravelNameTextView);
        travelNameTextView.setText(travelName);

        startDayTextView=(TextView)findViewById(R.id.StartDayTextView);
        startCalImageView=(ImageView)findViewById(R.id.StartDayCalImageView);
        startDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSetDay("start");
            }
        });
        startCalImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSetDay("start");
            }
        });

        finishDayTextView=(TextView)findViewById(R.id.FinishDayTextView);
        finishCalImageView=(ImageView)findViewById(R.id.FinishDayCalImageView);

        finishDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSetDay("finish");
            }
        });
        finishCalImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSetDay("finish");
            }
        });

        confirmButton=(Button)findViewById(R.id.ConfirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String travelName=travelNameTextView.getText().toString();
                String startDay=startDayTextView.getText().toString();
                String finishDay=finishDayTextView.getText().toString();
                String country="country";

                dbHelper.addTravel(new Travel(travelName,startDay,finishDay,country));
                Intent intent=new Intent(AddTravelActivity.this,MoneyListActivity.class);
                intent.putExtra("travelName",travelName);
                startActivity(intent);
                finish();
            }
        });

    }
}
