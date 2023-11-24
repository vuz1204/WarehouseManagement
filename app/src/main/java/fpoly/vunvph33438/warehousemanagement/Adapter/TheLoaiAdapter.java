package fpoly.vunvph33438.warehousemanagement.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
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

import fpoly.vunvph33438.warehousemanagement.DAO.TheLoaiDAO;
import fpoly.vunvph33438.warehousemanagement.Interface.ItemClickListener;
import fpoly.vunvph33438.warehousemanagement.Model.TheLoai;
import fpoly.vunvph33438.warehousemanagement.R;

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.TheLoaiViewHolder> {

    Context context;
    ArrayList<TheLoai> list;
    private ItemClickListener itemClickListener;
    TheLoaiDAO theLoaiDAO;
    private static final String TAG = "TheLoaiAdapter";

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TheLoaiAdapter(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
        theLoaiDAO = new TheLoaiDAO(context);
    }

    @NonNull
    @Override
    public TheLoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_the_loai, parent, false);
        return new TheLoaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaiViewHolder holder, int position) {
        holder.itemView.setOnLongClickListener(v -> {
            try {
                if (itemClickListener != null) {
                    itemClickListener.UpdateItem(position);
                }
            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: " + e);
            }
            return false;
        });
        holder.tvMaTL.setText("Mã loại: " + list.get(position).getId_theLoai());
        holder.tvTenTL.setText("Tên loại: " + list.get(position).getTenTheLoai());
        holder.imgDeleteTL.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
    }

    public void showDeleteDialog(int position) {
        TheLoai theLoai = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + theLoai.getTenTheLoai() + " không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (theLoaiDAO.delete(theLoai)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(theLoai);
                        notifyItemRemoved(position);

                        if (position == list.size()) {
                            notifyItemChanged(position - 1);
                        }
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

    class TheLoaiViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaTL, tvTenTL;
        ImageView imgDeleteTL;

        public TheLoaiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTL = itemView.findViewById(R.id.tvMaTL);
            tvTenTL = itemView.findViewById(R.id.tvTenTL);
            imgDeleteTL = itemView.findViewById(R.id.imgDeleteTL);
        }
    }
}
