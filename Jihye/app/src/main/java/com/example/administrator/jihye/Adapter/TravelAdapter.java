package com.example.administrator.jihye.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jihye.Activity.MainActivity;
import com.example.administrator.jihye.Activity.MoneyListActivity;
import com.example.administrator.jihye.DBHelper.DBHelper;
import com.example.administrator.jihye.DataStructure.Travel;
import com.example.administrator.jihye.R;

import java.util.ArrayList;
import java.util.List;

public class TravelAdapter extends BaseAdapter {
    private ArrayList<Travel> travelList;
    private Context context;
    private DBHelper dbHelper;

    private static LayoutInflater inflater=null;

    public TravelAdapter(MainActivity mainActivity, ArrayList<Travel> travelList) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.travelList=travelList;
        dbHelper=DBHelper.getInstance(context);

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(travelList==null) return 0;
        return travelList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if(travelList==null) return null;
        return travelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        if(travelList==null) return 0;
        return travelList.get(position).getId();
    }

    public class Holder
    {
        TextView title;
        TextView period;
        TextView money;
        ImageView flag;
        ImageView back;
    }
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.travel_content, null);
        holder.title =(TextView) rowView.findViewById(R.id.titleTextView);
        holder.period=(TextView) rowView.findViewById(R.id.periodTextView);
        holder.money =(TextView) rowView.findViewById(R.id.moneyTextView);
        holder.flag =(ImageView) rowView.findViewById(R.id.flagView);
        holder.back=(ImageView)rowView.findViewById(R.id.backImageView);

        Travel travel = (Travel) getItem(position);
        holder.title.setText(travel.getName());
        holder.period.setText(travel.getStartDay()+"~"+travel.getFinishDay());
        holder.money.setText("EUR "+dbHelper.getTravelMoneySum(travel.getName()));
        switch (position%4){
            case 0:
                holder.back.setImageResource(R.drawable.seoul);
                break;
            case 1:
                holder.back.setImageResource(R.drawable.back1);
                break;
            case 2:
                holder.back.setImageResource(R.drawable.back2);
                break;
            default:
                holder.back.setImageResource(R.drawable.back3);
        }
        Log.d("song",travel.getName()+"-"+travel.getId());

        return rowView;
    }
}