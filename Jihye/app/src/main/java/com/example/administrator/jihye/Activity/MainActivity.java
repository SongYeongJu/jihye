package com.example.administrator.jihye.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jihye.Adapter.TravelAdapter;
import com.example.administrator.jihye.DBHelper.DBHelper;
import com.example.administrator.jihye.DataStructure.Travel;
import com.example.administrator.jihye.R;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridView travelList;
    private DBHelper dbHelper;
    private TextView infoTextView;

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Travel> travels=dbHelper.getAllTravel();
        if(travels==null || travels.size()==0) {
            infoTextView.setVisibility(View.VISIBLE);
        } else {
            travelList.setAdapter(new TravelAdapter(this, travels));
            travelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, MoneyListActivity.class);
                    String name = dbHelper.getTravelById(id).getName();
                    intent.putExtra("travelName", name);
                    Log.d("song", "travel name: " + name);
                    startActivity(intent);
                }
            });
            infoTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/jihye_app");
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "jihye_app");

        dbHelper = new DBHelper( MainActivity.this, "Jihye_DB", null, 1);
        travelList=(GridView)findViewById(R.id.travelList);

        infoTextView=(TextView)findViewById(R.id.infoTextView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddTravelNameActivity.class);
                startActivity(intent);
            }
        });
    }
}
