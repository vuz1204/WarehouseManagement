package fpoly.vunvph33438.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import fpoly.vunvph33438.warehousemanagement.Adapter.ChiTietHoaDonAdapter;
import fpoly.vunvph33438.warehousemanagement.Adapter.SanPhamSpinner;
import fpoly.vunvph33438.warehousemanagement.DAO.ChiTietHoaDonDAO;
import fpoly.vunvph33438.warehousemanagement.DAO.SanPhamDAO;
import fpoly.vunvph33438.warehousemanagement.Model.ChiTietHoaDon;
import fpoly.vunvph33438.warehousemanagement.Model.SanPham;

public class ChiTietHoaDonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SanPhamDAO sanPhamDAO;
    ArrayList<SanPham> listSP = new ArrayList<>();
    ArrayList<SanPham> listSPAdded = new ArrayList<>();
    ChiTietHoaDonDAO chiTietHoaDonDAO;
    ArrayList<ChiTietHoaDon> listCTHD = new ArrayList<>();
    Spinner spinnerCTHDSP;
    SanPhamSpinner sanPhamSpinner;
    TextView tvIdHD;
    int donGia;
    ChiTietHoaDonAdapter chiTietHoaDonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);

        tvIdHD = findViewById(R.id.tvIdHD);
        Intent intent = getIntent();
        int hoaDonId = intent.getIntExtra("hoaDonId", -1);
        tvIdHD.setText("Mã hóa đơn: " + hoaDonId);

        recyclerView = findViewById(R.id.rcvCTHD);
        chiTietHoaDonDAO = new ChiTietHoaDonDAO(this);

        listCTHD = chiTietHoaDonDAO.selectByIdHoaDon(String.valueOf(hoaDonId));

        listSPAdded.clear();

        for (ChiTietHoaDon chiTietHoaDon : listCTHD) {
            SanPhamDAO sanPhamDAO = new SanPhamDAO(this);
            SanPham sanPham = sanPhamDAO.selectID(String.valueOf(chiTietHoaDon.getId_sanPham()));

            if (sanPham != null) {
                listSPAdded.add(sanPham);
            }
        }

        chiTietHoaDonAdapter = new ChiTietHoaDonAdapter(this, listCTHD);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chiTietHoaDonAdapter);

        sanPhamDAO = new SanPhamDAO(this);
        listSP = sanPhamDAO.selectAll();

        spinnerCTHDSP = findViewById(R.id.spinnerCTHDSP);
        sanPhamSpinner = new SanPhamSpinner(this, listSP);
        spinnerCTHDSP.setAdapter(sanPhamSpinner);

        spinnerCTHDSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    SanPham selectedSanPham = (SanPham) spinnerCTHDSP.getSelectedItem();
                    donGia = selectedSanPham.getGia();
                    boolean isProductExist = false;

                    for (SanPham sanPham : listSPAdded) {
                        if (sanPham.getId_sanPham() == selectedSanPham.getId_sanPham()) {
                            isProductExist = true;
                            break;
                        }
                    }

                    if (isProductExist) {
                        for (ChiTietHoaDon chiTietHoaDon : listCTHD) {
                            if (chiTietHoaDon.getId_sanPham() == selectedSanPham.getId_sanPham()) {
                                Integer newQuantity = chiTietHoaDonAdapter.getQuantityMap().get(selectedSanPham.getId_sanPham());
                                if (newQuantity != null) {
                                    chiTietHoaDon.setSoLuong(newQuantity);
                                }
                                break;
                            }
                        }
                        chiTietHoaDonAdapter.notifyDataSetChanged();
                    } else {
                        listSPAdded.add(selectedSanPham);
                        Integer newQuantity = chiTietHoaDonAdapter.getQuantityMap().get(selectedSanPham.getId_sanPham());
                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(selectedSanPham.getId_sanPham(), hoaDonId, newQuantity, donGia);
                        listCTHD.add(chiTietHoaDon);
                        chiTietHoaDonAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.btnSaveCTHD).setOnClickListener(v -> {
            if (listSPAdded.isEmpty()) {
                Toast.makeText(this, "Vui lòng thêm sản phẩm", Toast.LENGTH_SHORT).show();
            } else {
                boolean canSave = true;

                for (SanPham sanPham : listSPAdded) {
                    Integer quantity = chiTietHoaDonAdapter.getQuantityMap().get(sanPham.getId_sanPham());

                    if (quantity != null && quantity > sanPham.getSoLuong()) {
                        canSave = false;
                        Toast.makeText(this, "Số lượng " + sanPham.getTenSanPham() + " không đủ", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (canSave) {
                    for (SanPham sanPham : listSPAdded) {
                        Integer quantity = chiTietHoaDonAdapter.getQuantityMap().get(sanPham.getId_sanPham());

                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(sanPham.getId_sanPham(), hoaDonId, quantity, donGia);
                        chiTietHoaDonDAO.insert(chiTietHoaDon);

                        chiTietHoaDonAdapter.getQuantityMap().put(sanPham.getId_sanPham(), quantity);

                        if (quantity != null) {
                            sanPham.setSoLuong(sanPham.getSoLuong() - quantity);
                            SanPhamDAO sanPhamDAO = new SanPhamDAO(this);
                            sanPhamDAO.update(sanPham);
                        }
                    }
                    Toast.makeText(this, "Lưu hóa đơn thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        findViewById(R.id.btnCancelCTHD).setOnClickListener(v -> {
            finish();
        });
    }
}