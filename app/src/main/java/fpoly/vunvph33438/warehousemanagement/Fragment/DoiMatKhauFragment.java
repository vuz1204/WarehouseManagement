package fpoly.vunvph33438.warehousemanagement.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import fpoly.vunvph33438.warehousemanagement.DAO.ThuKhoDAO;
import fpoly.vunvph33438.warehousemanagement.Model.ThuKho;
import fpoly.vunvph33438.warehousemanagement.R;

public class DoiMatKhauFragment extends Fragment {

    View view;
    EditText edOldPassWord, edNewPassword, edReNewPassword;
    Button btnSaveChangePass;
    ThuKhoDAO thuKhoDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);

        edOldPassWord = view.findViewById(R.id.edOldPassWord);
        edNewPassword = view.findViewById(R.id.edNewPassword);
        edReNewPassword = view.findViewById(R.id.edReNewPassword);
        thuKhoDAO = new ThuKhoDAO(getActivity());

        btnSaveChangePass = view.findViewById(R.id.btnSaveChangePass);
        btnSaveChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("USERNAME", "");

                if (validate() > 0) {
                    ThuKho thuKho = thuKhoDAO.selectID(username);

                    if (thuKho != null) {
                        thuKho.setPassword(edNewPassword.getText().toString());

                        if (thuKhoDAO.updatePass(thuKho)) {
                            Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("PASSWORD", edNewPassword.getText().toString());
                            editor.apply();

                            clearInputFields();
                        } else {
                            Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "User not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    private void clearInputFields() {
        edOldPassWord.setText("");
        edNewPassword.setText("");
        edReNewPassword.setText("");
    }

    public int validate() {
        int check = 1;
        if (edOldPassWord.getText().toString().isEmpty() || edNewPassword.getText().toString().isEmpty() || edReNewPassword.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = sharedPreferences.getString("PASSWORD", "");
            String pass = edNewPassword.getText().toString();
            String rePass = edReNewPassword.getText().toString();
            if (!passOld.equals(edOldPassWord.getText().toString())) {
                Toast.makeText(getActivity(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (pass.equals(passOld)) {
                Toast.makeText(getActivity(), "Mật khẩu mới không được giống với mật khẩu cũ", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)) {
                Toast.makeText(getActivity(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}