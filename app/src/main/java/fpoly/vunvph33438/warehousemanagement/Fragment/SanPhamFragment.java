package fpoly.vunvph33438.warehousemanagement.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Adapter.SanPhamAdapter;
import fpoly.vunvph33438.warehousemanagement.Adapter.TheLoaiSpinner;
import fpoly.vunvph33438.warehousemanagement.DAO.SanPhamDAO;
import fpoly.vunvph33438.warehousemanagement.DAO.TheLoaiDAO;
import fpoly.vunvph33438.warehousemanagement.Interface.ItemClickListener;
import fpoly.vunvph33438.warehousemanagement.Model.SanPham;
import fpoly.vunvph33438.warehousemanagement.Model.TheLoai;
import fpoly.vunvph33438.warehousemanagement.R;

public class SanPhamFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    SanPhamDAO sanPhamDAO;
    ArrayList<SanPham> list = new ArrayList<>();
    EditText edIdSP, edTenSP, edGiaSP, edSoLuongSP;
    Spinner spinnerTheLoaiSP;
    TheLoaiDAO theLoaiDAO;
    int selectedPosition;
    TheLoaiSpinner theLoaiSpinner;
    ArrayList<TheLoai> listTheLoai = new ArrayList<>();
    int maTheLoai;
    SanPhamAdapter sanPhamAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_san_pham, container, false);

        recyclerView = view.findViewById(R.id.rcvSP);
        sanPhamDAO = new SanPhamDAO(getContext());
        list = sanPhamDAO.selectAll();
        sanPhamAdapter = new SanPhamAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sanPhamAdapter);

        view.findViewById(R.id.fabSP).setOnClickListener(v -> {
            showAddOrUpdateDialog(getContext(), 0, null);
        });

        sanPhamAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                SanPham sanPham = list.get(position);
                showAddOrUpdateDialog(getContext(), 1, sanPham);
            }
        });
        return view;
    }

    private void showAddOrUpdateDialog(Context context, int type, SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_san_pham, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        edIdSP = view.findViewById(R.id.edIdSP);
        edTenSP = view.findViewById(R.id.edTenSP);
        edGiaSP = view.findViewById(R.id.edGiaSP);
        edSoLuongSP = view.findViewById(R.id.edSoLuongSP);
        spinnerTheLoaiSP = view.findViewById(R.id.spinnerTheLoaiSP);

        edIdSP.setEnabled(false);

        theLoaiDAO = new TheLoaiDAO(context);
        listTheLoai = theLoaiDAO.selectAll();

        if (listTheLoai.isEmpty()) {
            Toast.makeText(context, "Không thể thêm sản phẩm mà không có thể loại. Vui lòng thêm thể loại trước", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
            return;
        }

        theLoaiSpinner = new TheLoaiSpinner(context, listTheLoai);
        spinnerTheLoaiSP.setAdapter(theLoaiSpinner);

        if (type != 0) {
            edIdSP.setText(String.valueOf(sanPham.getId_sanPham()));
            edTenSP.setText(sanPham.getTenSanPham());
            edGiaSP.setText(String.valueOf(sanPham.getGia()));
            edSoLuongSP.setText(String.valueOf(sanPham.getSoLuong()));

            for (int i = 0; i < listTheLoai.size(); i++) {
                if (sanPham.getId_theLoai() == listTheLoai.get(i).getId_theLoai()) {
                    selectedPosition = i;
                }
            }

            spinnerTheLoaiSP.setSelection(selectedPosition);
        }

        spinnerTheLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTheLoai = listTheLoai.get(position).getId_theLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.btnSaveSP).setOnClickListener(v -> {
            String tenSP = edTenSP.getText().toString();
            String giaSP = edGiaSP.getText().toString();
            String soLuongSP = edSoLuongSP.getText().toString();

            if (validate(tenSP, giaSP, soLuongSP)) {
                if (type == 0) {
                    SanPham newSP = new SanPham();
                    newSP.setTenSanPham(tenSP);
                    newSP.setGia(Integer.parseInt(giaSP));
                    newSP.setSoLuong(Integer.parseInt(soLuongSP));
                    newSP.setId_theLoai(maTheLoai);

                    try {
                        if (sanPhamDAO.insertData(newSP)) {
                            Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                            list.add(newSP);
                            sanPhamAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, R.string.add_not_success, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.add_not_success, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    sanPham.setTenSanPham(tenSP);
                    sanPham.setGia(Integer.parseInt(giaSP));
                    sanPham.setSoLuong(Integer.parseInt(soLuongSP));
                    sanPham.setId_theLoai(maTheLoai);
                    try {
                        if (sanPhamDAO.update(sanPham)) {
                            Toast.makeText(context, R.string.edit_success, Toast.LENGTH_SHORT).show();
                            updateList();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        view.findViewById(R.id.btnCancelSP).setOnClickListener(v -> {
            clearForm();
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private boolean validate(String tenSP, String giaSP, String soLuongSP) {
        try {
            if (tenSP.isEmpty() || giaSP.isEmpty() || soLuongSP.isEmpty()) {
                Toast.makeText(getContext(), R.string.empty_field_warning, Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void updateList() {
        list.clear();
        list.addAll(sanPhamDAO.selectAll());
        sanPhamAdapter.notifyDataSetChanged();
    }

    private void clearForm() {
        edIdSP.setText("");
        edTenSP.setText("");
        edGiaSP.setText("");
        edSoLuongSP.setText("");
    }
}