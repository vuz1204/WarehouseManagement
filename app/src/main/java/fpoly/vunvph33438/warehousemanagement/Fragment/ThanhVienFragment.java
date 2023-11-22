package fpoly.vunvph33438.warehousemanagement.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.Adapter.ThuKhoAdapter;
import fpoly.vunvph33438.warehousemanagement.DAO.ThuKhoDAO;
import fpoly.vunvph33438.warehousemanagement.Model.ThuKho;
import fpoly.vunvph33438.warehousemanagement.R;

public class ThanhVienFragment extends Fragment {

    View view;
    ThuKhoDAO thuKhoDAO;
    RecyclerView recyclerView;
    ArrayList<ThuKho> list = new ArrayList<>();
    ThuKhoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        recyclerView = view.findViewById(R.id.rcvThanhVien);
        thuKhoDAO = new ThuKhoDAO(getContext());
        list = thuKhoDAO.selectAll();
        adapter = new ThuKhoAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}