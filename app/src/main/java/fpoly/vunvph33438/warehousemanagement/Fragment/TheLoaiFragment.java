package fpoly.vunvph33438.warehousemanagement.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Adapter.TheLoaiAdapter;
import fpoly.vunvph33438.warehousemanagement.DAO.TheLoaiDAO;
import fpoly.vunvph33438.warehousemanagement.Interface.ItemClickListener;
import fpoly.vunvph33438.warehousemanagement.Model.TheLoai;
import fpoly.vunvph33438.warehousemanagement.R;

public class TheLoaiFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    TheLoaiDAO theLoaiDAO;
    EditText edIdTL, edTenLoai;
    ArrayList<TheLoai> list = new ArrayList<>();
    TheLoaiAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_the_loai, container, false);
        recyclerView = view.findViewById(R.id.rcvTheLoai);
        theLoaiDAO = new TheLoaiDAO(getContext());
        list = theLoaiDAO.selectAll();
        adapter = new TheLoaiAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.fabTheLoai).setOnClickListener(v -> {
            showAddOrEditDialog(getContext(), 0, null);
        });

        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                TheLoai theLoai = list.get(position);
                showAddOrEditDialog(getContext(), 1, theLoai);
            }
        });
        return view;
    }

    protected void showAddOrEditDialog(Context context, int type, TheLoai theLoai) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_the_loai, null);
        builder.setView(mView);
        AlertDialog alertDialog = builder.create();

        edIdTL = mView.findViewById(R.id.edIdTL);
        edTenLoai = mView.findViewById(R.id.edTenLoai);
        edIdTL.setEnabled(false);

        if (type != 0) {
            edIdTL.setText(String.valueOf(theLoai.getId_theLoai()));
            edTenLoai.setText(String.valueOf(theLoai.getTenTheLoai()));
        }

        mView.findViewById(R.id.btnCancelTL).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        mView.findViewById(R.id.btnSaveTL).setOnClickListener(v -> {
            String tenLoai = edTenLoai.getText().toString();
            if (tenLoai.isEmpty()) {
                Toast.makeText(context, R.string.empty_field_warning, Toast.LENGTH_SHORT).show();
            } else {
                if (type == 0) {
                    TheLoai newTheLoai = new TheLoai();
                    newTheLoai.setTenTheLoai(tenLoai);
                    try {
                        if (theLoaiDAO.insertData(newTheLoai)) {
                            Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                            list.add(newTheLoai);
                            adapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, R.string.add_not_success, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    theLoai.setTenTheLoai(tenLoai);
                    try {
                        if (theLoaiDAO.update(theLoai)) {
                            Toast.makeText(context, R.string.edit_success, Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            update();
                        } else {
                            Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public void update() {
        list.clear();
        list.addAll(theLoaiDAO.selectAll());
        adapter.notifyDataSetChanged();
    }
}