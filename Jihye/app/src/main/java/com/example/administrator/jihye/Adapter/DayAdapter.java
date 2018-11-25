package com.example.administrator.jihye.Adapter;

import android.app.Activity;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.jihye.Activity.MainActivity;
import com.example.administrator.jihye.Activity.MoneyListActivity;
import com.example.administrator.jihye.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DayAdapter extends BaseAdapter {
    private String[] monthName = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec",};
    private String startDay;
    private String finsishDay;

    private ArrayList<String> months;
    private ArrayList<String> days;

    private Date startDate;
    private Date finishDate;
    private Context context;

    private static LayoutInflater inflater=null;

    private void init(){
        months=new ArrayList<String>();
        days=new ArrayList<String>();

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(startDay));

                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(finsishDay));

                days.add("A");
                months.add("all");

                days.add(cal1.get(Calendar.DAY_OF_MONTH)+"");
                months.add(monthName[cal1.get(Calendar.MONTH)]);
                cal1.add(Calendar.DATE,1);

                while(!cal1.equals(cal2))
                {
                    days.add(cal1.get(Calendar.DAY_OF_MONTH)+"");
                    months.add(monthName[cal1.get(Calendar.MONTH)]);
                    cal1.add(Calendar.DATE,1);
                    Log.d("Date","cal :"+ cal1);
                }
            }
        }catch (Exception e){
         Log.d("Date","fail to convert date");
        }
    }
    public DayAdapter(Activity mainActivity, String s,String f) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        startDay=s;
        finsishDay=f;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return months.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return months.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView day;
        TextView month;
    }
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        DayAdapter.Holder holder=new DayAdapter.Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.day_content, null);
        holder.day =(TextView) rowView.findViewById(R.id.DayTextView);
        holder.month=(TextView) rowView.findViewById(R.id.MonthTextView);

        holder.day.setText(days.get(position));
        holder.month.setText(months.get(position));

        return rowView;
    }
}
