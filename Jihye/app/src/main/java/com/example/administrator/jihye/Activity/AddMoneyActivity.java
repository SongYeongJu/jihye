package com.example.administrator.jihye.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jihye.DBHelper.DBHelper;
import com.example.administrator.jihye.DataStructure.Item;
import com.example.administrator.jihye.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMoneyActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private Item item;

    private String travelName;
    private EditText itemCostEditText;

    private static int PICK_IMAGE_REQUEST = 1;
    private final int CAMERA_CODE = 1111;

    private Uri photoUri;
    private String currentPhotoPath;//실제 사진 파일 경로
    private String mImageCaptureName;//이미지 이름
    private String imageFilePath;

    private ImageView itemImageView;
    private Bitmap bitmapImage;

    private ImageButton plusButton;
    private ImageButton minusButton;
    private boolean checkExp = true;

    private TextView convertedCostTextView;

    private Button types0;
    private Button types1;
    private Button types2;
    private Button types3;
    private Button types4;
    private Button types5;

    private String cType = "FOOD";
    private EditText itemNameEditText;

    private Button cancle;
    private Button save;

    private TextView dayTextView;

    private ImageButton cal;
    private ImageButton img;
    private ImageButton map;


    private void firstLow() {
        convertedCostTextView = (TextView) findViewById(R.id.ConvertedCostTextView);
        convertedCostTextView.setText("KRW 0");

        itemCostEditText = (EditText) findViewById(R.id.ItemCostEditText);
        itemCostEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                double krw = 0.0;
                try {
                    krw = Double.parseDouble(itemCostEditText.getText().toString()) * 1300.0;
                } catch (Exception e) {
                }
                convertedCostTextView.setText("KRW " + krw);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        plusButton = (ImageButton) findViewById(R.id.PlusImageButton);
        minusButton = (ImageButton) findViewById(R.id.MinusImageButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusButton.setImageResource(R.drawable.dark_plus);
                minusButton.setImageResource(R.drawable.light_minus);
                checkExp = false;
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusButton.setImageResource(R.drawable.light_plus);
                minusButton.setImageResource(R.drawable.dark_minus);
                checkExp = true;
            }
        });

    }
    private void secondLow() {
        types0 = (Button) findViewById(R.id.FoodButton);
        types1 = (Button) findViewById(R.id.ShoppingButton);
        types2 = (Button) findViewById(R.id.TourismButton);
        types3 = (Button) findViewById(R.id.TransButton);
        types4 = (Button) findViewById(R.id.AccButton);
        types5 = (Button) findViewById(R.id.EtcButton);

        types0.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
        types1.setTextColor(getApplication().getResources().getColor(R.color.black));
        types2.setTextColor(getApplication().getResources().getColor(R.color.black));
        types3.setTextColor(getApplication().getResources().getColor(R.color.black));
        types4.setTextColor(getApplication().getResources().getColor(R.color.black));
        types5.setTextColor(getApplication().getResources().getColor(R.color.black));
        cType = "FOOD";

        types0.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                types0.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                types1.setTextColor(getApplication().getResources().getColor(R.color.black));
                types2.setTextColor(getApplication().getResources().getColor(R.color.black));
                types3.setTextColor(getApplication().getResources().getColor(R.color.black));
                types4.setTextColor(getApplication().getResources().getColor(R.color.black));
                types5.setTextColor(getApplication().getResources().getColor(R.color.black));
                cType = "FOOD";
            }
        });
        types1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                types1.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                types0.setTextColor(getApplication().getResources().getColor(R.color.black));
                types2.setTextColor(getApplication().getResources().getColor(R.color.black));
                types3.setTextColor(getApplication().getResources().getColor(R.color.black));
                types4.setTextColor(getApplication().getResources().getColor(R.color.black));
                types5.setTextColor(getApplication().getResources().getColor(R.color.black));
                cType = "SHOPPING";
            }
        });
        types2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                types2.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                types1.setTextColor(getApplication().getResources().getColor(R.color.black));
                types0.setTextColor(getApplication().getResources().getColor(R.color.black));
                types3.setTextColor(getApplication().getResources().getColor(R.color.black));
                types4.setTextColor(getApplication().getResources().getColor(R.color.black));
                types5.setTextColor(getApplication().getResources().getColor(R.color.black));
                cType = "TOUR";
            }
        });
        types3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                types3.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                types1.setTextColor(getApplication().getResources().getColor(R.color.black));
                types2.setTextColor(getApplication().getResources().getColor(R.color.black));
                types0.setTextColor(getApplication().getResources().getColor(R.color.black));
                types4.setTextColor(getApplication().getResources().getColor(R.color.black));
                types5.setTextColor(getApplication().getResources().getColor(R.color.black));
                cType = "TRANS";
            }
        });
        types4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                types4.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                types1.setTextColor(getApplication().getResources().getColor(R.color.black));
                types2.setTextColor(getApplication().getResources().getColor(R.color.black));
                types3.setTextColor(getApplication().getResources().getColor(R.color.black));
                types0.setTextColor(getApplication().getResources().getColor(R.color.black));
                types5.setTextColor(getApplication().getResources().getColor(R.color.black));
                cType = "ACC";
            }
        });
        types5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                types5.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                types1.setTextColor(getApplication().getResources().getColor(R.color.black));
                types2.setTextColor(getApplication().getResources().getColor(R.color.black));
                types3.setTextColor(getApplication().getResources().getColor(R.color.black));
                types4.setTextColor(getApplication().getResources().getColor(R.color.black));
                types0.setTextColor(getApplication().getResources().getColor(R.color.black));
                cType = "ETC";
            }
        });

    }
    private void thirdLow() {
        setButtons();
        setDates();
        setImages();
        setMaps();
    }

    private void setMaps(){
        map=(ImageButton)findViewById(R.id.MapImageButton);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddMoneyActivity.this,MapsActivity.class);
                startActivityForResult(intent,1234);
            }
        });
    }
    private void setButtons(){

        cancle = (Button) findViewById(R.id.CancleButton);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save = (Button) findViewById(R.id.SaveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setTravelName(travelName);
                item.setItemName(itemNameEditText.getText().toString());

                double money;
                try {
                    money = Double.parseDouble(itemCostEditText.getText().toString());
                } catch (Exception e) {
                    money = 0;
                }

                if (checkExp)
                    item.setMoney("" + -1.0 * money);
                else
                    item.setMoney("" + money);

                item.setType(cType);
                item.setDay(dayTextView.getText().toString());

                if (bitmapImage == null) {
                    item.setImage(null);
                } else {
                    String filename = item.getTravelName() + "_" + item.getItemName() + ".png";
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/jihye_app/" + filename);
                    File fileCacheItem = path;
                    String abpath = path.getAbsolutePath();
                    Log.d("abpath", "abpath: " + abpath);
                    item.setImage(abpath);
                    OutputStream out = null;
                    try {
                        fileCacheItem.createNewFile();
                        out = new FileOutputStream(fileCacheItem);
                        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, out);
                        Log.d("song", "success to save bitmap img");
                    } catch (Exception e) {
                        Log.d("song", "err to save bitmap img");
                        e.printStackTrace();
                    } finally {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                dbHelper.addItem(item);
                finish();
            }
        });
    }
    private void setImages(){
        itemImageView = (ImageView) findViewById(R.id.itemPictureImageView);
        img = (ImageButton) findViewById(R.id.ImgImageButton);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AddMoneyActivity.this).setTitle("").setItems(
                        new String[]{"Gallery", "Camera"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_PIC과 차이점?
                                    intent.setType("image/*"); //이미지만 보이게
                                    //Intent 시작 - 갤러리앱을 열어서 원하는 이미지를 선택할 수 있다.
                                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                                } else {
                                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                        File photoFile = null;
                                        try {
                                            photoFile = createImageFile();
                                        } catch (IOException ex) {
                                            // Error occurred while creating the File
                                        }
                                        if (photoFile != null) {
                                            photoUri = FileProvider.getUriForFile(AddMoneyActivity.this, getPackageName(), photoFile);

                                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                            startActivityForResult(takePictureIntent, CAMERA_CODE);
                                        }
                                    }
                                }
                            }
                        }).create().show();

            }
        });
    }
    private void setDates(){
        dayTextView = (TextView) findViewById(R.id.ItemDayTextView);

        SimpleDateFormat mSimpleDateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.KOREA);
        }
        Date currentTime = new Date();
        String mTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mTime = mSimpleDateFormat.format(currentTime);
        }
        dayTextView.setText(mTime);

        cal = (ImageButton) findViewById(R.id.CalImageButton);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int nYear = cal.get(Calendar.YEAR);
                int nMon = cal.get(Calendar.MONTH);
                int nDay = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener mDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                String strDate = String.valueOf(dayOfMonth) + "-";
                                strDate += String.valueOf(monthOfYear + 1) + "-";
                                strDate += String.valueOf(year);
                                dayTextView.setText(strDate);
                                //Toast.makeText(getApplicationContext(), strDate, Toast.LENGTH_SHORT).show();
                            }
                        };

                DatePickerDialog oDialog = new DatePickerDialog(v.getContext(),
                        android.R.style.Theme_DeviceDefault_Light_Dialog,
                        mDateSetListener, nYear, nMon, nDay);
                oDialog.show();
            }
        });
    }

    private File createImageFile() throws IOException {
        String timeStamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        }
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }
    //after select img
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);
                itemImageView.setImageBitmap(scaled);
                bitmapImage = scaled;
            }
            if (requestCode == CAMERA_CODE && resultCode == RESULT_OK ) {
                Log.d("song","camera code");
                itemImageView.setImageURI(photoUri);
                Drawable drawable = itemImageView.getDrawable();
                bitmapImage= ((BitmapDrawable)drawable).getBitmap();
                Log.d("song","camera code finish");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            Log.d("song", "이미지 로딩 오류??");
            e.printStackTrace();
        }

        try {
            if(requestCode==1234) {
                LatLng cur=(LatLng) data.getExtras().get("location");
                item.setLat(cur.latitude+"");
                item.setLon(cur.longitude+"");
                Log.d("mmap","cur location: "+cur);
            }
        }catch (Exception e) {
            Log.d("mmap","fail to get cur location");
        }
    }

    private void init() {
        Intent intent = getIntent();
        travelName = intent.getStringExtra("travelName");
        item = new Item();
        itemNameEditText = (EditText) findViewById(R.id.NameEditText);
        dbHelper = DBHelper.getInstance(this);

        firstLow();
        secondLow();
        thirdLow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        init();
    }

}
