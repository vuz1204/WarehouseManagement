package fpoly.vunvph33438.warehousemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Model.HoaDon;
import fpoly.vunvph33438.warehousemanagement.Model.TheLoai;
import fpoly.vunvph33438.warehousemanagement.R;

public class HoaDonSpinner extends BaseAdapter {

    Context context;
    ArrayList<HoaDon> list;

    public HoaDonSpinner(Context context, ArrayList<HoaDon> list) {
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

    private static class HoaDonViewHolder {
        TextView tvMaHDSpinenr, tvSoHDSpinner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoaDonViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_hoa_don, parent, false);
            viewHolder = new HoaDonViewHolder();
            viewHolder.tvMaHDSpinenr = convertView.findViewById(R.id.tvMaHDSpinenr);
            viewHolder.tvSoHDSpinner = convertView.findViewById(R.id.tvSoHDSpinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HoaDonViewHolder) convertView.getTag();
        }
        HoaDon hoaDon = list.get(position);
        viewHolder.tvMaHDSpinenr.setText(String.valueOf(hoaDon.getId_hoaDon()));
        viewHolder.tvSoHDSpinner.setText(hoaDon.getSoHoaDon());
        return convertView;
    }
}
