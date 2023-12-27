package fpoly.vunvph33438.warehousemanagement.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.ChiTietHoaDonActivity;
import fpoly.vunvph33438.warehousemanagement.DAO.HoaDonDAO;
import fpoly.vunvph33438.warehousemanagement.DAO.ThuKhoDAO;
import fpoly.vunvph33438.warehousemanagement.Interface.ItemClickListener;
import fpoly.vunvph33438.warehousemanagement.Model.HoaDon;
import fpoly.vunvph33438.warehousemanagement.Model.ThuKho;
import fpoly.vunvph33438.warehousemanagement.R;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder> {

    Context context;
    ArrayList<HoaDon> list;
    HoaDonDAO hoaDonDAO;
    ThuKhoDAO thuKhoDAO;

    private ItemClickListener itemClickListener;

    public HoaDonAdapter(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
        hoaDonDAO = new HoaDonDAO(context);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoa_don, parent, false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDon hoaDon = list.get(position);

        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("USERNAME", "");

        thuKhoDAO = new ThuKhoDAO(context);
        ThuKho thuKho = thuKhoDAO.selectID(username);

        holder.tvMaHD.setText("Mã HD: " + hoaDon.getId_hoaDon());
        holder.tvMaThuKhoHD.setText("Thành Viên: " + thuKho.getId_thuKho());
        holder.tvSoHD.setText("Số hóa đơn: " + hoaDon.getSoHoaDon());
        holder.tvNgayThangHD.setText("Ngày thuê: " + hoaDon.getNgayThang());

        if (hoaDon.getLoaiHoaDon() == 0) {
            holder.tvLoaiHD.setTextColor(Color.BLUE);
            holder.tvLoaiHD.setText("Nhập");
        } else {
            holder.tvLoaiHD.setTextColor(Color.RED);
            holder.tvLoaiHD.setText("Xuất");
        }

        holder.imgDeleteHD.setOnClickListener(v -> {
            showDeleteDialog(position);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChiTietHoaDonActivity.class);
            intent.putExtra("hoaDonId", hoaDon.getId_hoaDon());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }

    public void showDeleteDialog(int position) {
        HoaDon hoaDon = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa phiếu mượn " + hoaDon.getId_hoaDon() + " không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (hoaDonDAO.delete(hoaDon)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(hoaDon);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HoaDonViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaHD, tvMaThuKhoHD, tvSoHD, tvLoaiHD, tvNgayThangHD;
        ImageView imgDeleteHD;

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHD = itemView.findViewById(R.id.tvMaHD);
            tvMaThuKhoHD = itemView.findViewById(R.id.tvMaThuKhoHD);
            tvSoHD = itemView.findViewById(R.id.tvSoHD);
            tvLoaiHD = itemView.findViewById(R.id.tvLoaiHD);
            tvNgayThangHD = itemView.findViewById(R.id.tvNgayThangHD);
            imgDeleteHD = itemView.findViewById(R.id.imgDeleteHD);
        }
    }
}
