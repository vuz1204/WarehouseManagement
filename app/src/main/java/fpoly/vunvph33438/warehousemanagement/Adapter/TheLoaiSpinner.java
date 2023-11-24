package fpoly.vunvph33438.warehousemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Model.TheLoai;
import fpoly.vunvph33438.warehousemanagement.R;

public class TheLoaiSpinner extends BaseAdapter {

    Context context;
    ArrayList<TheLoai> list;

    public TheLoaiSpinner(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class TheLoaiViewHolder {
        TextView tvMaTLSpinner, tvTenTLSpinner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TheLoaiViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_the_loai, parent, false);
            viewHolder = new TheLoaiViewHolder();
            viewHolder.tvMaTLSpinner = convertView.findViewById(R.id.tvMaTLSpinner);
            viewHolder.tvTenTLSpinner = convertView.findViewById(R.id.tvTenTLSpinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TheLoaiViewHolder) convertView.getTag();
        }
        TheLoai theLoai = list.get(position);
        viewHolder.tvMaTLSpinner.setText(String.valueOf(theLoai.getId_theLoai()));
        viewHolder.tvTenTLSpinner.setText(theLoai.getTenTheLoai());
        return convertView;
    }
}
