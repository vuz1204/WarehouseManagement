package fpoly.vunvph33438.warehousemanagement.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.DAO.ThuKhoDAO;
import fpoly.vunvph33438.warehousemanagement.Model.ThuKho;
import fpoly.vunvph33438.warehousemanagement.R;

public class ThemThuKhoFragment extends Fragment {

    View view;
    EditText edAddUsername, edAddName, edAddEmail, edAddPass, edAddRePass;
    ThuKhoDAO thuKhoDAO;
    Button btnSaveAdd;
    Spinner spinnerRole;
    ArrayList<String> list = new ArrayList<>();
    String valueRole;
    int rolePosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_them_thu_kho, container, false);

        edAddUsername = view.findViewById(R.id.edAddUsername);
        edAddName = view.findViewById(R.id.edAddName);
        edAddEmail = view.findViewById(R.id.edAddEmail);
        edAddPass = view.findViewById(R.id.edAddPass);
        edAddRePass = view.findViewById(R.id.edAddRePass);
        thuKhoDAO = new ThuKhoDAO(getActivity());

        spinnerRole = view.findViewById(R.id.spinnerAddRole);
        list.add("Admin");
        list.add("Thủ kho");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        spinnerRole.setAdapter(adapter);

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rolePosition = position;
                valueRole = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSaveAdd = view.findViewById(R.id.btnSaveAdd);
        btnSaveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() > 0) {
                    ThuKho thuKho = new ThuKho();
                    thuKho.setUsername(edAddUsername.getText().toString());
                    thuKho.setFullname(edAddName.getText().toString());
                    thuKho.setEmail(edAddEmail.getText().toString());
                    thuKho.setPassword(edAddPass.getText().toString());
                    thuKho.setRole(rolePosition);
                    if (thuKhoDAO.insertData(thuKho)) {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                    } else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public int validate() {
        int check = 1;
        String username = edAddUsername.getText().toString();
        String name = edAddName.getText().toString();
        String email = edAddEmail.getText().toString();
        String pass = edAddPass.getText().toString();
        String rePass = edAddRePass.getText().toString();

        if (username.isEmpty() || name.isEmpty() || email.isEmpty() || pass.isEmpty() || rePass.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            ThuKho existingThuthu = thuKhoDAO.selectID(username);
            if (existingThuthu != null) {
                Toast.makeText(getActivity(), "Mã thủ thư đã tồn tại", Toast.LENGTH_SHORT).show();
                check = -1;
            } else if (!pass.equals(rePass)) {
                Toast.makeText(getActivity(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }

    private void clearInputFields() {
        edAddUsername.setText("");
        edAddName.setText("");
        edAddEmail.setText("");
        edAddPass.setText("");
        edAddRePass.setText("");
    }
}