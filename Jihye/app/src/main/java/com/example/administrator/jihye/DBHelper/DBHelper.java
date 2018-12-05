package com.example.administrator.jihye.DBHelper;


import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.jihye.DataStructure.Item;
import com.example.administrator.jihye.DataStructure.Travel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    private static Context context;
    private static DBHelper Instance;

    public static DBHelper getInstance(Context c){
        context=c;
        return Instance;
    }
    public static DBHelper getInstance(){
        return Instance;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        Instance=this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();

        try {
            sb.append(" CREATE TABLE TRAVEL ( ");
            sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sb.append(" NAME TEXT, ");
            sb.append(" START_DAY TEXT, ");
            sb.append(" FINISH_DAY TEXT, ");
            sb.append(" COUNTRY TEXT ); ");

            db.execSQL(sb.toString());
            Log.d("song","create travel table");
        }catch (Exception e){
            Log.d("song","already exist travel table");
        }


        try {
            sb = new StringBuffer();
            sb.append(" CREATE TABLE ITEM  ( ");
            sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sb.append(" TRAVEL_NAME TEXT, ");
            sb.append(" ITEM_NAME TEXT, ");
            sb.append(" DAY TEXT, ");
            sb.append(" TYPE TEXT, ");
            sb.append(" MONEY TEXT, ");
            sb.append(" IMAGE TEXT, ");
            sb.append(" LAT TEXT, ");
            sb.append(" LON TEXT) ");
            db.execSQL(sb.toString());

            Log.d("song","create item table");
        } catch (Exception e){
            Log.d("song","fail to create item table");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();
    }
    public void testDB() {
        SQLiteDatabase db = getReadableDatabase();
    }

    public void addTravel(Travel travel){ // insert travel detail in travel table
        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO TRAVEL( ");
        sb.append(" NAME, START_DAY, FINISH_DAY, COUNTRY ) ");
        sb.append(" VALUES ( ");
        sb.append("'"+travel.getName()+"',");
        sb.append("'"+travel.getStartDay()+"',");
        sb.append("'"+travel.getFinishDay()+"',");
        sb.append("'"+travel.getCountry()+"')");
        db.execSQL(sb.toString());
    }
    public void addItem(Item item){ // insert travel detail in travel table
        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO ITEM( ");
        sb.append(" TRAVEL_NAME, ITEM_NAME, ");
        sb.append(" DAY, TYPE, MONEY, IMAGE, ");
        sb.append(" LAT, LON ) ");
        sb.append(" VALUES ( ");
        sb.append("'"+item.getTravelName()+"',");
        sb.append("'"+item.getItemName()+"',");
        sb.append("'"+item.getDay()+"',");
        sb.append("'"+item.getType()+"',");
        sb.append("'"+item.getMoney()+"',");
        sb.append("'"+item.getImage()+"',");
        sb.append("'"+item.getLat()+"',");
        sb.append("'"+item.getLon()+"')");

        Log.d("test","item image :"+item.getImage());

        /*appIcon =getByteArrayFromDrawable(getIcon());
        SQLiteStatement p=db.complieStatement("INSERT  INTO 테이블명 VALUES(?)");
        p.bindBlob(1,appIcon);
        */

        db.execSQL(sb.toString());
    }
    public ArrayList<Item> getItems(String TravelName) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT _ID, TRAVEL_NAME,ITEM_NAME, DAY, TYPE, MONEY, IMAGE, LAT, LON FROM ITEM ");
            sb.append(" WHERE TRAVEL_NAME ='"+TravelName+"' ");

            // make a object for reading db
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(sb.toString(), null);

            ArrayList<Item> itemList = new ArrayList();
            Item item = null;

            while (cursor.moveToNext()) {
                item = new Item();
                item.setId(cursor.getInt(0));
                item.setTravelName((cursor.getString(1)));
                item.setItemName(cursor.getString(2));
                item.setDay(cursor.getString(3));
                item.setType(cursor.getString(4));
                item.setMoney(cursor.getString(5));
                item.setImage(cursor.getString(6));
                item.setLat(cursor.getString(7));
                item.setLon(cursor.getString(8));
                Log.d("getItems",item.getItemName()+","+item.getImage());
                itemList.add(item);
            }
            return itemList;
        } catch(Exception e){}
        return null;
    }
    public ArrayList<Item> getItemsByDay(String TravelName, Calendar cal) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT _ID, TRAVEL_NAME,ITEM_NAME, DAY, TYPE, MONEY, IMAGE, LAT, LON FROM ITEM ");
            sb.append(" WHERE TRAVEL_NAME ='"+TravelName+"'");
            sb.append(" AND DAY ='"+cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR)+"'");

            // make a object for reading db
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(sb.toString(), null);

            ArrayList<Item> itemList = new ArrayList();
            Item item = null;

            while (cursor.moveToNext()) {
                item = new Item();
                item.setId(cursor.getInt(0));
                item.setTravelName((cursor.getString(1)));
                item.setItemName(cursor.getString(2));
                item.setDay(cursor.getString(3));
                item.setType(cursor.getString(4));
                item.setMoney(cursor.getString(5));
                item.setImage(cursor.getString(6));
                item.setLat(cursor.getString(7));
                item.setLon(cursor.getString(8));
                Log.d("getItems",item.getItemName()+","+item.getImage());
                itemList.add(item);
            }
            return itemList;
        } catch(Exception e){}
        return null;
    }
    public Travel getTravelById(long id){
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT _ID, NAME, START_DAY, FINISH_DAY, COUNTRY FROM TRAVEL ");
            sb.append(" WHERE _ID =" + id);

            // make a object for reading db
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(sb.toString(), null);

            ArrayList<Travel> TravelList = new ArrayList();
            Travel travel= null;

            while (cursor.moveToNext()) {
                travel = new Travel();
                travel.setId(cursor.getInt(0));
                travel.setName((cursor.getString(1)));
                travel.setStartDay(cursor.getString(2));
                travel.setFinishDay(cursor.getString(2));
                travel.setCountry(cursor.getString(3));
                TravelList.add(travel);
            }
            return TravelList.get(0);
        } catch(Exception e){}
        return null;
    }
    public Travel getTravelByName(String name){
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT _ID, NAME, START_DAY, FINISH_DAY, COUNTRY FROM TRAVEL ");
            sb.append(" WHERE NAME ='" + name+"'");

            // make a object for reading db
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(sb.toString(), null);

            ArrayList<Travel> TravelList = new ArrayList();
            Travel travel= null;

            while (cursor.moveToNext()) {
                travel = new Travel();
                travel.setId(cursor.getInt(0));
                travel.setName((cursor.getString(1)));
                travel.setStartDay(cursor.getString(2));
                travel.setFinishDay(cursor.getString(3));
                travel.setCountry(cursor.getString(4));
                TravelList.add(travel);
            }
            return TravelList.get(0);
        } catch(Exception e){}
        return null;
    }
    public Item getItemById(long id){
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT _ID, TRAVEL_NAME,ITEM_NAME, DAY, TYPE, MONEY, IMAGE, LAT, LON FROM ITEM ");
            sb.append(" WHERE _ID =" + id);

            // make a object for reading db
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(sb.toString(), null);

            ArrayList<Item> itemList = new ArrayList();
            Item item= null;

            while (cursor.moveToNext()) {
                item = new Item();
                item.setId(cursor.getInt(0));
                item.setTravelName((cursor.getString(1)));
                item.setItemName(cursor.getString(2));
                item.setDay(cursor.getString(3));
                item.setType(cursor.getString(4));
                item.setMoney(cursor.getString(5));
                item.setImage(cursor.getString(6));
                item.setLat(cursor.getString(7));
                item.setLon(cursor.getString(8));
                itemList.add(item);
            }
            return itemList.get(0);
        } catch(Exception e){}
        return null;
    }
    public ArrayList<Item> getItemsByTravelId(long id){
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT _ID, TRAVEL_NAME,ITEM_NAME, DAY, TYPE, MONEY, IMAGE, LAT, LON FROM ITEM ");
            sb.append(" WHERE TRAVEL_NAME IN (" );
            sb.append(" SELECT TRAVEL_NAME FROM TRAVEL " );
            sb.append(" WHERE _ID ="+ id +")");

            // make a object for reading db
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(sb.toString(), null);

            ArrayList<Item> itemList = new ArrayList<Item>();
            Item item= null;

            while (cursor.moveToNext()) {
                item = new Item();
                item.setId(cursor.getInt(0));
                item.setTravelName((cursor.getString(1)));
                item.setItemName(cursor.getString(2));
                item.setDay(cursor.getString(3));
                item.setType(cursor.getString(4));
                item.setMoney(cursor.getString(5));
                item.setImage(cursor.getString(6));
                item.setLat(cursor.getString(7));
                item.setLon(cursor.getString(8));
                itemList.add(item);
            }
            return itemList;
        } catch(Exception e){}
        return null;
    }
    public ArrayList<Travel> getAllTravel() {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT _ID, NAME, START_DAY, FINISH_DAY, COUNTRY FROM TRAVEL ");

            // make a object for reading db
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(sb.toString(), null);

            ArrayList travelList = new ArrayList<Travel>();
            Travel travel = null;

            while (cursor.moveToNext()) {
                travel = new Travel();
                travel.setId(cursor.getInt(0));
                travel.setName(cursor.getString(1));
                travel.setStartDay(cursor.getString(2));
                travel.setFinishDay(cursor.getString(3));
                travelList.add(travel);
            }
            return travelList;
        } catch(Exception e){}
        return null;
    }
    public double getTravelMoneySum(String travelName){
        double sum=0;
        ArrayList<Item> list=getItems(travelName);
        for(Item i: list){
            sum+=Double.parseDouble(i.getMoney());
        }
        return sum;
    }

    public void delTravelById(long id){
        ArrayList<Item> list= getItemsByTravelId(id);
        for(Item i: list){
            delItemById(i.getId());
        }

        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM TRAVEL");
        sb.append(" WHERE _ID ="+ id+";");
        db.execSQL(sb.toString());
    }
    public void delItemById(long id){
        Item item=getItemById(id);
        String filename = item.getTravelName() + "_" + item.getItemName() + ".png";
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/jihye_app/" + filename);
        path.delete();

        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ITEM ");
        sb.append(" WHERE _ID ="+ id+";");
        db.execSQL(sb.toString());
    }
}
