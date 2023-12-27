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
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import fpoly.vunvph33438.warehousemanagement.DAO.ChiTietHoaDonDAO;
import fpoly.vunvph33438.warehousemanagement.DAO.SanPhamDAO;
import fpoly.vunvph33438.warehousemanagement.Model.ChiTietHoaDon;
import fpoly.vunvph33438.warehousemanagement.Model.SanPham;
import fpoly.vunvph33438.warehousemanagement.R;

public class ChiTietHoaDonAdapter extends RecyclerView.Adapter<ChiTietHoaDonAdapter.ChiTietHoaDonViewHolder> {

    Context context;
    ArrayList<ChiTietHoaDon> list;
    ChiTietHoaDonDAO chiTietHoaDonDAO;
    private HashMap<Integer, Integer> quantityMap;

    public ChiTietHoaDonAdapter(Context context, ArrayList<ChiTietHoaDon> list) {
        this.context = context;
        this.list = list;
        chiTietHoaDonDAO = new ChiTietHoaDonDAO(context);
        quantityMap = new HashMap<>();
    }

    @NonNull
    @Override
    public ChiTietHoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chi_tiet_hoa_don, parent, false);
        return new ChiTietHoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietHoaDonViewHolder holder, int position) {
        ChiTietHoaDon chiTietHoaDon = list.get(position);

        SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
        SanPham sanPham = sanPhamDAO.selectID(String.valueOf(chiTietHoaDon.getId_sanPham()));

        Integer[] quantity = {quantityMap.get(chiTietHoaDon.getId_sanPham())};
        if (quantity[0] == null) {
            quantity[0] = 1;
            quantityMap.put(chiTietHoaDon.getId_sanPham(), quantity[0]);
        }

        if (sanPham != null) {
            holder.tvMaSPCTHD.setText("Mã sản phẩm: " + sanPham.getId_sanPham());
            holder.tvSoLuongCTHD.setText("Số lượng: " + quantity[0]);
            holder.tvDonGiaCTHD.setText("Giá: " + sanPham.getGia());
        }

        holder.tvPlus.setOnClickListener(v -> {
            quantity[0]++;
            holder.tvSoLuongCTHD.setText("Số lượng: " + quantity[0]);
            quantityMap.put(sanPham.getId_sanPham(), quantity[0]);
        });

        holder.tvMinus.setOnClickListener(v -> {
            if (quantity[0] == 1) {
                showDeleteDialog(holder.getAdapterPosition(), sanPham, quantity[0]);
            } else {
                quantity[0]--;
                holder.tvSoLuongCTHD.setText("Số lượng: " + quantity[0]);
                quantityMap.put(sanPham.getId_sanPham(), quantity[0]);
            }
        });

        holder.imgDeleteSPCTHD.setOnClickListener(v -> {
            showDeleteDialog(holder.getAdapterPosition(), sanPham, quantity[0]);
        });
    }

    public HashMap<Integer, Integer> getQuantityMap() {
        return quantityMap;
    }

    public void showDeleteDialog(int position, SanPham sanPham, int quantity) {
        ChiTietHoaDon chiTietHoaDon = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + sanPham.getTenSanPham() + " không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (chiTietHoaDonDAO.delete(chiTietHoaDon)) {
                        if (quantity > 0) {
                            sanPham.setSoLuong(sanPham.getSoLuong() + quantity);
                            SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
                            sanPhamDAO.update(sanPham);
                        }
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(position);
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
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    class ChiTietHoaDonViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaSPCTHD, tvDonGiaCTHD, tvSoLuongCTHD, tvPlus, tvMinus;
        ImageView imgDeleteSPCTHD;

        public ChiTietHoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSPCTHD = itemView.findViewById(R.id.tvMaSPCTHD);
            tvDonGiaCTHD = itemView.findViewById(R.id.tvDonGiaCTHD);
            tvSoLuongCTHD = itemView.findViewById(R.id.tvSoLuongCTHD);
            tvPlus = itemView.findViewById(R.id.tvPlus);
            tvMinus = itemView.findViewById(R.id.tvMinus);
            imgDeleteSPCTHD = itemView.findViewById(R.id.imgDeleteCTHD);
        }
    }
}
