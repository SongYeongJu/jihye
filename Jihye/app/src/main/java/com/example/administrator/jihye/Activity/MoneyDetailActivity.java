package com.example.administrator.jihye.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jihye.DBHelper.DBHelper;
import com.example.administrator.jihye.DataStructure.Item;
import com.example.administrator.jihye.R;
import com.google.android.gms.maps.model.LatLng;

public class MoneyDetailActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private Item item;

    private TextView itemType;
    private TextView itemName;
    private TextView itemDay;
    private TextView itemCost;
    private TextView itemCost2;
    private TextView itemNote;
    private TextView del;

    private ImageView itemImage;
    private Button closeButton;
    private ImageView mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_detail);

        Intent intent = getIntent();
        long id = intent.getLongExtra("ItemID", 0);

        dbHelper = DBHelper.getInstance(this);
        item = dbHelper.getItemById(id);

        itemType = (TextView) findViewById(R.id.ItemTypeTextView);
        itemType.setText(item.getType());
        itemName = (TextView) findViewById(R.id.ItemNameTextView);
        itemName.setText(item.getItemName());
        itemDay = (TextView) findViewById(R.id.ItemDayTextView);
        itemDay.setText(item.getDay());
        itemCost = (TextView) findViewById(R.id.ItemCostTextView);
        itemCost.setText("EUR  " + item.getMoney());

        itemCost2 = (TextView) findViewById(R.id.ItemCostTextView2);
        itemCost2.setText("KRW  " + Double.parseDouble(item.getMoney())*1300);

        del = (TextView) findViewById(R.id.DeleteTextView);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setTitle("Delete this item");

                alertDialogBuilder.setMessage("Do you really want to delete this item??")
                        .setCancelable(false)
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dbHelper.delItemById(item.getId());
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
            }
        });

        closeButton = (Button) findViewById(R.id.CloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        itemImage=(ImageView)findViewById(R.id.ItemImageImageView);
        if(item.getImage()!=null) {
            try {
                String imgpath = item.getImage();
                Log.d("load file", "path: " + imgpath);
                Bitmap bm = BitmapFactory.decodeFile(imgpath);
                itemImage.setImageBitmap(bm);
                Log.d("load file", "success to load image file");
            } catch (Exception e) {
                Log.d("load file", "err to load image file");
                e.printStackTrace();
            }
        }

        mapButton=(ImageButton)findViewById(R.id.GoMapButton);
        if(item.getLat()!=null && item.getLon()!=null) {
            mapButton.setImageResource(R.drawable.colored_map);
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent(getApplicationContext(), MapsActivity2.class);
                    mapIntent.putExtra("info", item);
                    startActivity(mapIntent);
                }
            });
        }
    }
}
