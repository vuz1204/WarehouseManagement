package fpoly.vunvph33438.warehousemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import fpoly.vunvph33438.warehousemanagement.DAO.ThuKhoDAO;
import fpoly.vunvph33438.warehousemanagement.Fragment.ChiTietHoaDonFragment;
import fpoly.vunvph33438.warehousemanagement.Fragment.DoiMatKhauFragment;
import fpoly.vunvph33438.warehousemanagement.Fragment.HoaDonFragment;
import fpoly.vunvph33438.warehousemanagement.Fragment.SanPhamFragment;
import fpoly.vunvph33438.warehousemanagement.Fragment.ThanhVienFragment;
import fpoly.vunvph33438.warehousemanagement.Fragment.TheLoaiFragment;
import fpoly.vunvph33438.warehousemanagement.Fragment.ThemThuKhoFragment;
import fpoly.vunvph33438.warehousemanagement.Fragment.TonKhoFragment;
import fpoly.vunvph33438.warehousemanagement.Fragment.XuatKhoFragment;
import fpoly.vunvph33438.warehousemanagement.Model.ThuKho;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    ActionBarDrawerToggle drawerToggle;

    View mHeaderView;

    ThuKhoDAO thuKhoDAO;

    TextView tvFullname, tvEmail;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý kho hàng");

        drawerLayout = findViewById(R.id.drawer_Layout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, new HoaDonFragment()).commit();

        navigationView = findViewById(R.id.nvView);
        mHeaderView = navigationView.getHeaderView(0);
        tvFullname = mHeaderView.findViewById(R.id.tvFullname);
        tvEmail = mHeaderView.findViewById(R.id.tvEmail);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String role = intent.getStringExtra("role");
        thuKhoDAO = new ThuKhoDAO(this);
        ThuKho thuKho = thuKhoDAO.selectID(username);
        String fullname = thuKho.getFullname();
        String email = thuKho.getEmail();
        tvFullname.setText("Welcome " + fullname + "!");
        tvEmail.setText(email);
        if (role != null && role.equalsIgnoreCase("admin")) {
            navigationView.getMenu().findItem(R.id.sub_addUser).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_thanhVien).setVisible(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_hoaDon) {
                    getSupportActionBar().setTitle("Quản lý hóa đơn");
                    HoaDonFragment hoaDonFragment = new HoaDonFragment();
                    replaceFragment(hoaDonFragment);
                }
                if (item.getItemId() == R.id.nav_theLoai) {
                    getSupportActionBar().setTitle("Quản lý thể loại");
                    TheLoaiFragment theLoaiFragment = new TheLoaiFragment();
                    replaceFragment(theLoaiFragment);
                }
                if (item.getItemId() == R.id.nav_sanPham) {
                    getSupportActionBar().setTitle("Quản lý sản phẩm");
                    SanPhamFragment sanPhamFragment = new SanPhamFragment();
                    replaceFragment(sanPhamFragment);
                }
                if (item.getItemId() == R.id.nav_chiTietHoaDon) {
                    getSupportActionBar().setTitle("Chi tiết hóa đơn");
                    ChiTietHoaDonFragment chiTietHoaDonFragment = new ChiTietHoaDonFragment();
                    replaceFragment(chiTietHoaDonFragment);
                }
                if (item.getItemId() == R.id.nav_thanhVien) {
                    getSupportActionBar().setTitle("Quản lý thành viên");
                    ThanhVienFragment thanhVienFragment = new ThanhVienFragment();
                    replaceFragment(thanhVienFragment);
                }
                if (item.getItemId() == R.id.sub_xuatKho) {
                    getSupportActionBar().setTitle("Xuất kho theo tháng");
                    XuatKhoFragment xuatKhoFragment = new XuatKhoFragment();
                    replaceFragment(xuatKhoFragment);
                }
                if (item.getItemId() == R.id.sub_tonKho) {
                    getSupportActionBar().setTitle("Tồn kho theo tháng");
                    TonKhoFragment tonKhoFragment = new TonKhoFragment();
                    replaceFragment(tonKhoFragment);
                }
                if (item.getItemId() == R.id.sub_nhapKho) {
                    getSupportActionBar().setTitle("Nhập kho theo tháng");
                    TonKhoFragment tonKhoFragment = new TonKhoFragment();
                    replaceFragment(tonKhoFragment);
                }
                if (item.getItemId() == R.id.sub_addUser) {
                    getSupportActionBar().setTitle("Thêm thủ kho");
                    ThemThuKhoFragment themThuKhoFragment = new ThemThuKhoFragment();
                    replaceFragment(themThuKhoFragment);
                }
                if (item.getItemId() == R.id.sub_changePass) {
                    getSupportActionBar().setTitle("Đổi mật khẩu");
                    DoiMatKhauFragment doiMatKhauFragment = new DoiMatKhauFragment();
                    replaceFragment(doiMatKhauFragment);
                }
                if (item.getItemId() == R.id.sub_Logout) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    Toast.makeText(MainActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                    finish();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!fragment.equals(fragmentManager.findFragmentById(R.id.flContent))) {
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) drawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }
}