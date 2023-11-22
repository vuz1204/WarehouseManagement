package fpoly.vunvph33438.warehousemanagement.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.DAO.ThuKhoDAO;
import fpoly.vunvph33438.warehousemanagement.Model.ThuKho;
import fpoly.vunvph33438.warehousemanagement.R;

public class ThuKhoAdapter extends RecyclerView.Adapter<ThuKhoAdapter.ThuKhoViewHolder> {

    Context context;
    ArrayList<ThuKho> list;
    ThuKhoDAO thuKhoDAO;
    EditText edIdTV, edUsernameTV, edFullnameTV, edEmailTV, edPasswordTV;

    public ThuKhoAdapter(Context context, ArrayList<ThuKho> list) {
        this.context = context;
        this.list = list;
        thuKhoDAO = new ThuKhoDAO(context);
    }

    @NonNull
    @Override
    public ThuKhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanh_vien, parent, false);
        return new ThuKhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuKhoViewHolder holder, int position) {
        holder.tvMaTV.setText("Mã TV: " + list.get(position).getId_thuKho());
        holder.tvUsername.setText("Username: " + list.get(position).getUsername());
        holder.tvFullnameTV.setText("Họ và tên: " + list.get(position).getFullname());
        holder.tvEmailTV.setText("Email: " + list.get(position).getEmail());
        holder.tvPasswordTV.setText("Password: " + list.get(position).getPassword());
        holder.imgDeleteTV.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            showUpdateDialog(position);
            return true;
        });
    }

    private void showUpdateDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_thanh_vien, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        edIdTV = dialogView.findViewById(R.id.edIdTV);
        edUsernameTV = dialogView.findViewById(R.id.edUsernameTV);
        edFullnameTV = dialogView.findViewById(R.id.edFullnameTV);
        edEmailTV = dialogView.findViewById(R.id.edEmailTV);
        edPasswordTV = dialogView.findViewById(R.id.edPasswordTV);

        edIdTV.setEnabled(false);
        edUsernameTV.setEnabled(false);

        edIdTV.setText(String.valueOf(list.get(position).getId_thuKho()));
        edUsernameTV.setText(list.get(position).getUsername());
        edFullnameTV.setText(list.get(position).getFullname());
        edEmailTV.setText(list.get(position).getEmail());
        edPasswordTV.setText(list.get(position).getPassword());

        dialogView.findViewById(R.id.btnSaveTV).setOnClickListener(v -> {
            String fullname = edFullnameTV.getText().toString().trim();
            String email = edEmailTV.getText().toString().trim();
            String password = edPasswordTV.getText().toString().trim();

            if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            } else {
                ThuKho thuKho = list.get(position);
                thuKho.setFullname(fullname);
                thuKho.setEmail(email);
                thuKho.setPassword(password);
                try {
                    if (thuKhoDAO.update(thuKho)) {
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        list.set(position, thuKho);
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogView.findViewById(R.id.btnCancelTV).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public void showDeleteDialog(int position) {
        ThuKho thuKho = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa thủ thư " + thuKho.getFullname() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (thuKhoDAO.delete(thuKho)) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        list.remove(thuKho);
                        notifyItemChanged(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
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

    class ThuKhoViewHolder extends RecyclerView.ViewHolder {

        TextView tvMaTV, tvUsername, tvFullnameTV, tvEmailTV, tvPasswordTV;
        ImageView imgDeleteTV;

        public ThuKhoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTV = itemView.findViewById(R.id.tvMaTV);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvFullnameTV = itemView.findViewById(R.id.tvFullnameTV);
            tvEmailTV = itemView.findViewById(R.id.tvEmailTV);
            tvPasswordTV = itemView.findViewById(R.id.tvPasswordTV);
            imgDeleteTV = itemView.findViewById(R.id.imgDeleteTV);
        }
    }
}
