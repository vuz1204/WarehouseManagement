package fpoly.vunvph33438.warehousemanagement.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.DAO.SanPhamDAO;
import fpoly.vunvph33438.warehousemanagement.Interface.ItemClickListener;
import fpoly.vunvph33438.warehousemanagement.Model.SanPham;
import fpoly.vunvph33438.warehousemanagement.R;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {

    Context context;
    ArrayList<SanPham> list;
    SanPhamDAO sanPhamDAO;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        sanPhamDAO = new SanPhamDAO(context);
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        if (sanPham != null) {
            holder.tvMaSP.setText("Mã sản phẩm: " + sanPham.getId_sanPham());
            holder.tvTenSP.setText("Tên sản phẩm: " + sanPham.getTenSanPham());
            String formattedPrice = sanPham.getPriceFormatted();
            holder.tvGiaSP.setText("Giá: " + formattedPrice + " ₫");
            holder.tvSoLuongSP.setText("Số lượng: " + sanPham.getSoLuong());
            holder.tvMaTLSP.setText("Mã loại: " + sanPham.getId_theLoai());
        }
        holder.imgDeleteSP.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }

    public void showDeleteDialog(int position) {
        SanPham sanPham = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + sanPham.getTenSanPham() + " không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (sanPhamDAO.delete(sanPham)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(sanPham);
                        notifyItemChanged(position);
                        notifyItemRemoved(position);
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

    class SanPhamViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaSP, tvTenSP, tvGiaSP, tvSoLuongSP, tvMaTLSP;
        ImageView imgDeleteSP;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSP = itemView.findViewById(R.id.tvMaSP);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSP);
            tvSoLuongSP = itemView.findViewById(R.id.tvSoLuongSP);
            tvMaTLSP = itemView.findViewById(R.id.tvMaTLSP);
            imgDeleteSP = itemView.findViewById(R.id.imgDeleteSP);
        }
    }
}
