package fpoly.vunvph33438.warehousemanagement.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("USERNAME", "");

        holder.imgDeleteTV.setOnClickListener(v -> {
            showDeleteDialog(position, loggedInUsername);
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
                Toast.makeText(context, R.string.empty_field_warning, Toast.LENGTH_SHORT).show();
            } else {
                ThuKho thuKho = list.get(position);
                thuKho.setFullname(fullname);
                thuKho.setEmail(email);
                thuKho.setPassword(password);
                try {
                    if (thuKhoDAO.update(thuKho)) {
                        Toast.makeText(context, R.string.edit_success, Toast.LENGTH_SHORT).show();
                        list.set(position, thuKho);
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogView.findViewById(R.id.btnCancelTV).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public void showDeleteDialog(int position, String loggedInUsername) {
        ThuKho thuKho = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("Thông báo");

        if (thuKho.getUsername().equals(loggedInUsername)) {
            builder.setMessage("Bạn không thể xóa tài khoản của chính mình.");
            builder.setPositiveButton("OK", null);
        } else {
            builder.setMessage("Bạn có chắc chắn muốn xóa thủ thư " + thuKho.getFullname() + " không?");
            builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (thuKhoDAO.delete(thuKho)) {
                            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                            list.remove(thuKho);
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
        }

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
