package com.taufiqhidayah.tasekretaris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class CustemAdapterMemo extends BaseAdapter {
    ArrayList<Data> data;
    Context context;

    public CustemAdapterMemo(DetailSuratMemoActivity detailSuratMemoActivity, ArrayList<Data> data) {
        this.data =data;
        this.context = detailSuratMemoActivity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.list_item, null);
        TextView tvnmbrg = (TextView) view.findViewById(R.id.tv1);
        TextView tvjumlh = (TextView) view.findViewById(R.id.tv2);
        TextView tvjumlh1 = (TextView) view.findViewById(R.id.tv3);
        TextView tvjumlh2 = (TextView) view.findViewById(R.id.tv4);
        TextView tvjumlh3 = (TextView) view.findViewById(R.id.tv5);
//        TextView tvhrg = (TextView) view.findViewById(R.id.tvHarga);

        tvnmbrg.setText("" + data.get(position).getNo_dok());
        tvjumlh.setText("" + data.get(position).getTgl_masuk());
        tvjumlh1.setText("" + data.get(position).getPengirim());
        tvjumlh2.setText("" + data.get(position).getDeskripsi());
        tvjumlh2.setText("" + data.get(position).getPerihal());
//         v.txtItemTgl.setText(data.get(position).get());
//        tvhrg.setText(data.get(position).getNamuser());
        return view;
    }
}
