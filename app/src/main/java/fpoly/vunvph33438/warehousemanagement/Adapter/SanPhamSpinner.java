package fpoly.vunvph33438.warehousemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Model.ChiTietHoaDon;
import fpoly.vunvph33438.warehousemanagement.Model.SanPham;
import fpoly.vunvph33438.warehousemanagement.Model.TheLoai;
import fpoly.vunvph33438.warehousemanagement.R;

public class SanPhamSpinner extends BaseAdapter {

    Context context;
    ArrayList<SanPham> list;

    public SanPhamSpinner(Context context, ArrayList<SanPham> list) {
        this.context = context;

        SanPham defaultSanPham = new SanPham();
        defaultSanPham.setId_sanPham(0);
        defaultSanPham.setTenSanPham("--Vui lòng chọn 1 sản phẩm--");
        list.add(0, defaultSanPham);

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SanPhamViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_san_pham, parent, false);
            viewHolder = new SanPhamViewHolder();
            viewHolder.tvTenSPSpinner = convertView.findViewById(R.id.tvTenSPSpinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SanPhamViewHolder) convertView.getTag();
        }
        SanPham sanPham = list.get(position);
        viewHolder.tvTenSPSpinner.setText(sanPham.getTenSanPham());
        return convertView;
    }

    public static class SanPhamViewHolder {
        TextView tvTenSPSpinner;
    }
}
