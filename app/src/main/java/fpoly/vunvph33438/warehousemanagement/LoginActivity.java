package fpoly.vunvph33438.warehousemanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import fpoly.vunvph33438.warehousemanagement.DAO.ThuKhoDAO;
import fpoly.vunvph33438.warehousemanagement.Model.ThuKho;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    Button btnLogin;
    CheckBox chkRememberPass;
    String strUser, strPass;
    Spinner spinnerRole;
    String valueRole;
    int rolePosition;
    ThuKhoDAO thuKhoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        thuKhoDAO = new ThuKhoDAO(this);

        spinnerRole = findViewById(R.id.spinnerRole);
        ArrayList<String> list = new ArrayList<>();
        list.add("Admin");
        list.add("Thủ kho");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinnerRole.setAdapter(adapter);
        readFile();
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rolePosition = i;
                valueRole = list.get(i);
                Toast.makeText(LoginActivity.this, valueRole, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }

    private void readFile() {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edUsername.setText(sharedPreferences.getString("USERNAME", ""));
        edPassword.setText(sharedPreferences.getString("PASSWORD", ""));
        chkRememberPass.setChecked(sharedPreferences.getBoolean("REMEMBER", false));
        spinnerRole.setSelection(sharedPreferences.getInt("ROLE", 0));
    }

    public void checkLogin() {
        strUser = edUsername.getText().toString().trim();
        strPass = edPassword.getText().toString().trim();

        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
        } else {
            String loggedInUsername = thuKhoDAO.checkUsername(strUser, String.valueOf(rolePosition));
            if (loggedInUsername != null) {
                ThuKho thuKho = thuKhoDAO.selectID(loggedInUsername);
                if (thuKho.getPassword().equals(strPass)) {
                    if (thuKho.getRole() == 0) {
                        valueRole = "admin";
                    } else {
                        valueRole = "thuKho";
                    }

                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    rememberUser(loggedInUsername, strPass, chkRememberPass.isChecked(), rolePosition);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("username", loggedInUsername);
                    intent.putExtra("role", valueRole);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void rememberUser(String u, String p, boolean status, int rolePosition) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (!status) {
            edit.clear();
        } else {
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
            edit.putInt("ROLE", rolePosition);
        }
        edit.apply();
    }
}