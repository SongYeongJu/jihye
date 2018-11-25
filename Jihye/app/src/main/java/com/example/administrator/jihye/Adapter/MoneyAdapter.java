package com.example.administrator.jihye.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.jihye.Activity.MoneyListActivity;
import com.example.administrator.jihye.DataStructure.Item;
import com.example.administrator.jihye.R;

import java.util.ArrayList;

public class MoneyAdapter  extends BaseAdapter {
    private Context context;
    private ArrayList<Item> itemList;

    private static LayoutInflater inflater=null;

    public MoneyAdapter(Activity mainActivity, ArrayList<Item> itemList) {
        context=mainActivity;
        this.itemList=itemList;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if(itemList==null) return 0;
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        if(itemList==null) return null;
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if(itemList==null) return 0;
        return itemList.get(position).getId();
    }

    public class Holder
    {
        TextView money;
        TextView where;
    }
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        MoneyAdapter.Holder holder=new MoneyAdapter.Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.money_layout, null);
        holder.money =(TextView) rowView.findViewById(R.id.MoneyTextView);
        holder.where=(TextView) rowView.findViewById(R.id.MoneyTextView2);

        double money=Double.parseDouble(itemList.get(position).getMoney());
        if(money>0) {
            holder.where.setText("입금 - "+itemList.get(position).getItemName());
            holder.money.setText(money+"");
        }
        else if(money<0) {
            holder.where.setText("지출 - "+itemList.get(position).getItemName());
            holder.money.setText(""+ (-1*money));
        } else {
            holder.where.setText(itemList.get(position).getItemName());
            holder.money.setText(""+ money);
        }


        return rowView;
    }
}
