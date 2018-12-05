package com.example.administrator.jihye.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.jihye.Adapter.DayAdapter;
import com.example.administrator.jihye.Adapter.MoneyAdapter;
import com.example.administrator.jihye.Adapter.TravelAdapter;
import com.example.administrator.jihye.DBHelper.DBHelper;
import com.example.administrator.jihye.DataStructure.Item;
import com.example.administrator.jihye.DataStructure.Travel;
import com.example.administrator.jihye.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public class MoneyListActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private boolean checkEUR=true;
    private Travel travel;
    private Context context;

    public static ListView dayListView;
    public static ListView moneyListView;
    private TextView sumText1;
    private TextView sumText2;

    private FloatingActionButton addMoneyButton;

    private String TravelName;

    @Override
    protected void onStart() {
        super.onStart();
        moneyListView.setAdapter(new MoneyAdapter(MoneyListActivity.this,dbHelper.getItems(TravelName)));
        moneyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(MoneyListActivity.this,MoneyDetailActivity.class);
                intent.putExtra("ItemID",id);
                startActivity(intent);
            }
        });
        try {
            travel=dbHelper.getTravelByName(TravelName);
            dayListView.setAdapter(new DayAdapter(MoneyListActivity.this,travel.getStartDay(),travel.getFinishDay()));
            dayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Calendar cal1 = Calendar.getInstance();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        try {
                            if(position==0) {
                                moneyListView.setAdapter(new MoneyAdapter(MoneyListActivity.this,dbHelper.getItems(TravelName)));

                            } else{
                                cal1.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(travel.getStartDay()));
                                cal1.add(Calendar.DAY_OF_MONTH,position-1);
                                moneyListView.setAdapter(new MoneyAdapter(MoneyListActivity.this,dbHelper.getItemsByDay(TravelName,cal1)));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }catch (Exception e){}


        sumText1.setText("Total : EUR "+dbHelper.getTravelMoneySum(TravelName));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_list);
        context=this;

        Intent intent=getIntent();

        dbHelper=DBHelper.getInstance(this);
        TravelName=intent.getStringExtra("travelName");

        dayListView=(ListView)findViewById(R.id.dayListView);
        moneyListView=(ListView)findViewById(R.id.moneyListView);

        sumText1=(TextView)findViewById(R.id.SumTextView1);
        sumText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double eur=dbHelper.getTravelMoneySum(TravelName);
                if(checkEUR){
                    sumText1.setText( "Total : EUR " + eur );
                }else {
                    sumText1.setText( "Total : KRW " + eur * 1300 );
                }
                checkEUR= !checkEUR;
            }
        });
        addMoneyButton=(FloatingActionButton)findViewById(R.id.AddMoneyButton);
        addMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MoneyListActivity.this,AddMoneyActivity.class);
                intent.putExtra("travelName",TravelName);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.moneyListToolBar);
        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Delete this travel");

            alertDialogBuilder.setMessage("Do you really want to delete this trip?")
                    .setCancelable(false)
                    .setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dbHelper.delTravelById(travel.getId());
                                    finish();
                                }
                            })
                    .setNegativeButton("no",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
