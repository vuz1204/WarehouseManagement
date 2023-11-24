package fpoly.vunvph33438.warehousemanagement.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fpoly.vunvph33438.warehousemanagement.Adapter.HoaDonAdapter;
import fpoly.vunvph33438.warehousemanagement.DAO.HoaDonDAO;
import fpoly.vunvph33438.warehousemanagement.Interface.ItemClickListener;
import fpoly.vunvph33438.warehousemanagement.Model.HoaDon;
import fpoly.vunvph33438.warehousemanagement.R;

public class HoaDonFragment extends Fragment {

    private static final String TAG = "QlHoaDon";
    View view;
    HoaDonDAO hoaDonDAO;
    RecyclerView recyclerView;
    EditText edMaHD, edSoHD;
    RadioGroup radioGroupLoaiHD;
    RadioButton rbNhap, rbXuat;
    TextView tvNgayThang;
    ArrayList<HoaDon> list = new ArrayList<>();
    int tienThueSach, positonSach, positionThanhVien;
    HoaDonAdapter hoaDonAdapter;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        recyclerView = view.findViewById(R.id.rcvHoaDon);
        hoaDonDAO = new HoaDonDAO(getContext());
        list = hoaDonDAO.selectAll();
        hoaDonAdapter = new HoaDonAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(hoaDonAdapter);

        view.findViewById(R.id.fabHoaDon).setOnClickListener(v -> {
            showAddOrEditDialog(getContext(), 0, null);
        });

        hoaDonAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                HoaDon hoaDon = list.get(position);
                showAddOrEditDialog(getContext(), 1, hoaDon);
            }
        });
        return view;
    }

    private void showAddOrEditDialog(Context context, int type, HoaDon hoaDon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_hoa_don, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        edMaHD = dialogView.findViewById(R.id.edMaHD);
        edSoHD = dialogView.findViewById(R.id.edSoHD);
        radioGroupLoaiHD = dialogView.findViewById(R.id.radioGroupLoaiHD);
        rbNhap = dialogView.findViewById(R.id.rbNhap);
        rbXuat = dialogView.findViewById(R.id.rbXuat);
        tvNgayThang = dialogView.findViewById(R.id.tvNgayThang);

        edMaHD.setEnabled(false);
        edSoHD.setEnabled(false);

        if (type != 0) {
            edMaHD.setText(String.valueOf(hoaDon.getId_hoaDon()));
            edSoHD.setText(String.valueOf(hoaDon.getSoHoaDon()));
            tvNgayThang.setText("Ngày thuê: " + hoaDon.getNgayThang());
            if (hoaDon.getLoaiHoaDon() == 0) {
                rbNhap.setChecked(true);
            } else {
                rbXuat.setChecked(true);
            }
        }

        dialogView.findViewById(R.id.btnSaveHD).setOnClickListener(v -> {
            if (type == 0) {
                HoaDon newHoaDon = new HoaDon();
                newHoaDon.setSoHoaDon(newHoaDon.generateSoHoaDon());
                Date date = new Date();
                String ngayThang = simpleDateFormat.format(date);
                newHoaDon.setNgayThang(ngayThang);
                newHoaDon.setLoaiHoaDon(radioButtonSelected() ? 0 : 1);

                try {
                    if (hoaDonDAO.insert(newHoaDon)) {
                        Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                        list.add(newHoaDon);
                        hoaDonAdapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, R.string.add_not_success, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "showAddOrEditDialog: ", e);
                    Toast.makeText(context, R.string.add_not_success, Toast.LENGTH_SHORT).show();
                }
            } else {
                hoaDon.setLoaiHoaDon(radioButtonSelected() ? 0 : 1);
                try {
                    if (hoaDonDAO.update(hoaDon)) {
                        Toast.makeText(context, R.string.edit_success, Toast.LENGTH_SHORT).show();
                        updateList();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "showAddOrEditDialog: ", e);
                    Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogView.findViewById(R.id.btnCancelHD).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private boolean radioButtonSelected() {
        return rbNhap.isChecked();
    }

    public void updateList() {
        if (hoaDonDAO != null) {
            list.clear();
            list.addAll(hoaDonDAO.selectAll());
            hoaDonAdapter.notifyDataSetChanged();
        }
    }
}