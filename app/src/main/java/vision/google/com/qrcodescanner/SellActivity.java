package vision.google.com.qrcodescanner;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SellActivity extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText edtImei;
    TextView txtName, txtImei, txtGia, txtLoai, txtNgayNhap, txtNguoiNhap;
    ImageView imgHinh;
    LinearLayout layoutShow;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        AnhXa();


    }

    public void AnhXa() {
        layoutShow = (LinearLayout) findViewById(R.id.layoutShow);
        imgHinh = (ImageView) findViewById(R.id.imgHinh);
        edtImei = (EditText) findViewById(R.id.edtImei);
        txtName = (TextView) findViewById(R.id.txtName);
        txtImei = (TextView) findViewById(R.id.txtImei);
        txtLoai = (TextView) findViewById(R.id.txtLoai);
        txtGia = (TextView) findViewById(R.id.txtGia);
        txtNgayNhap = (TextView) findViewById(R.id.txtNgayNhap);
        txtNguoiNhap = (TextView) findViewById(R.id.txtNguoiNhap);

    }

    public void Search(final View view) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final Query query = reference
                .child("Kho")
                .child("Kho")
                .orderByChild("Imei")
                .equalTo(edtImei.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    layoutShow.setVisibility(View.VISIBLE);
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        ClassAddPhone classAddPhone = singleSnapshot.getValue(ClassAddPhone.class);
                        Picasso.with(SellActivity.this).load(classAddPhone.LinkHinh).into(imgHinh);
                        txtName.setText("Tên: " + classAddPhone.Ten);
                        txtImei.setText("Imei: " + classAddPhone.Imei);
                        txtLoai.setText("Loại: " + classAddPhone.Loai);
                        txtGia.setText("Giá: " + classAddPhone.GiaBan);
                        txtNguoiNhap.setText("Người nhập: " + classAddPhone.NguoiNhap);
                        txtNgayNhap.setText("Ngày nhập: " + classAddPhone.NgayNhap);
                        Toast.makeText(SellActivity.this, singleSnapshot.getKey(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    layoutShow.setVisibility(View.GONE);
                    Toast.makeText(SellActivity.this, "Không tìm thấy điện thoại", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    public void Scan(View view) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
                Intent intent = new Intent(SellActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

    }

    public void Sell(View view) {
        Toast.makeText(this, "Bán", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                edtImei.post(new Runnable() {
                    @Override
                    public void run() {
                        edtImei.setText(barcode.displayValue);
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
