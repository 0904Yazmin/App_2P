package com.example.app_segundoparcial.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_segundoparcial.R;
import com.example.app_segundoparcial.json.MyData;

import java.io.Serializable;
import java.util.List;

public class MyAdapter extends BaseAdapter implements Serializable {
    private List<MyData> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyAdapter(List<MyData> list, Context context)
    {
        this.list = list;
        this.context = context;
        if( context != null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
    }

    public boolean isEmptyOrNull( )
    {
        return list == null || list.size() == 0;
    }

    @Override
    public int getCount()
    {
        if(isEmptyOrNull())
        {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i)
    {
        if(isEmptyOrNull())
        {
            return null;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        view = layoutInflater.inflate(R.layout.activity_list_view, null );

        TextView txt_V1 = null;
        txt_V1 = view.findViewById(R.id.textViewId2);
        txt_V1.setText(list.get(i).getPassword());

        TextView txt_V2 = null;
        txt_V2 = view.findViewById(R.id.textViewId1);
        txt_V2.setText(list.get(i).getRed());

        ImageView imgView = null;
        imgView = view.findViewById(R.id.imageView5);
        imgView.setImageResource(list.get(i).getImg());
        return view;
    }

}

