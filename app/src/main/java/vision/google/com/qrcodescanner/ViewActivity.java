package vision.google.com.qrcodescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    DatabaseReference infoPhone = FirebaseDatabase.getInstance().getReference();
    ListView lvHinhAnh;
    ArrayList<ClassAddPhone> mangHinhAnh;
    HinhAnhAdapter adapter = null;
    String string, AES = "AES";

    ArrayList<String> danhSach;
    ArrayAdapter adapter2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        AnhXa();

//        danhSach = new ArrayList<String>();
//        adapter2 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,danhSach);
//        lvHinhAnh.setAdapter(adapter2);
//        infoPhone.child("Kho").child("Kho").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                ClassAddPhone classAddPhone = dataSnapshot.getValue(ClassAddPhone.class);
//                danhSach.add(classAddPhone.Imei +": "+classAddPhone.Ten);
//                adapter2.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        mangHinhAnh = new ArrayList<ClassAddPhone>();
        adapter= new HinhAnhAdapter(this, R.layout.dong_hinh_anh, mangHinhAnh);
        lvHinhAnh.setAdapter(adapter);
        LoadData();

    }

    private void LoadData() {
        infoPhone.child("Kho").child("Kho").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ClassAddPhone classAddPhone = dataSnapshot.getValue(ClassAddPhone.class);
//                Toast.makeText(ViewActivity.this, classAddPhone.Imei, Toast.LENGTH_LONG).show();
                mangHinhAnh.add(new ClassAddPhone(classAddPhone.Imei,classAddPhone.Ten,classAddPhone.Loai,  classAddPhone.GiaBan,classAddPhone.NguoiNhap,classAddPhone.NgayNhap, classAddPhone.LinkHinh));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void AnhXa() {
        lvHinhAnh = (ListView) findViewById(R.id.lisviewHinhAnh);
    }

//    private String encrypt(String string) throws Exception {
//        SecretKeySpec key = genarateKey((string));
//        Cipher cipher = Cipher.getInstance(AES);
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] bytes = cipher.doFinal(string.getBytes());
//        String encryptedValue = Base64.encodeToString(bytes, Base64.DEFAULT);
//        return encryptedValue;
//    }
//    private SecretKeySpec genarateKey(String string) throws Exception {
//        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] bytes = string.getBytes("UTF-8");
//        digest.update(bytes, 0, bytes.length);
//        byte[] key = digest.digest();
//        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
//        return secretKeySpec;
//    }

}
