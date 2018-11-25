package com.example.administrator.jihye.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.jihye.R;

public class AddTravelNameActivity extends AppCompatActivity {

    public EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_travel_name);

        nameText=(EditText)findViewById(R.id.TravelNameEditText);

        Button button=(Button)findViewById(R.id.TravelNameButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String travelName=nameText.getText().toString();
                if(travelName.equals("")) {
                    Toast.makeText(getApplicationContext(),"Put your travel name!!",Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(AddTravelNameActivity.this, AddTravelActivity.class);
                    intent.putExtra("travelName",travelName);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
