package vision.google.com.qrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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
    ArrayList<ClassSellPhone> mangHinhAnh2;
    HinhAnhAdapter adapter = null;
    HinhAnhAdapter2 adapter2 = null;
    String string, AES = "AES";
    Spinner spinner;
    Long[] AddPhone = new Long[100000];
    Long[] SellPhone = new Long[100000];
    int addphone = 0, sellphone = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        AnhXa();

        final ArrayList<String> Loai = new ArrayList<String>();
        Loai.add("Kho");
        Loai.add("Đã bán");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spiner_item2, Loai);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(AddActivity.this,Loai.get(i), Toast.LENGTH_SHORT).show();
                String a = Loai.get(i).toString();
                if (a == "Kho") {
                    LoadData();
                } else {
                    LoadData2();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        lvHinhAnh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String s = (AddPhone[i]).toString();
//                Toast.makeText(ViewActivity.this,s, Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(ViewActivity.this,ManagerEmployeeActivity.class);
////                startActivity(intent);
//            }
//        });

    }

    private void LoadData() {
        mangHinhAnh = new ArrayList<ClassAddPhone>();
        adapter = new HinhAnhAdapter(this, R.layout.dong_hinh_anh, mangHinhAnh);
        lvHinhAnh.setAdapter(adapter);
        infoPhone.child("Kho").child("Kho").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ClassAddPhone classAddPhone = dataSnapshot.getValue(ClassAddPhone.class);
//                Toast.makeText(ViewActivity.this, classAddPhone.Imei, Toast.LENGTH_LONG).show();
                mangHinhAnh.add(new ClassAddPhone(classAddPhone.Imei, classAddPhone.Ten, classAddPhone.Loai, classAddPhone.GiaBan, classAddPhone.NguoiNhap, classAddPhone.NgayNhap, classAddPhone.LinkHinh, classAddPhone.BaoHanh));
                AddPhone[addphone] = Long.parseLong(classAddPhone.Imei);
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

    private void LoadData2() {
        mangHinhAnh2 = new ArrayList<ClassSellPhone>();
        adapter2 = new HinhAnhAdapter2(this, R.layout.dong_hinh_anh2, mangHinhAnh2);
        lvHinhAnh.setAdapter(adapter2);
        infoPhone.child("Kho").child("Ban").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ClassSellPhone classAddPhone = dataSnapshot.getValue(ClassSellPhone.class);
                mangHinhAnh2.add(new ClassSellPhone(classAddPhone.Imei, classAddPhone.Ten, classAddPhone.Loai, classAddPhone.GiaBan, classAddPhone.NguoiBan, classAddPhone.NgayBan, classAddPhone.LinkHinh, classAddPhone.BaoHanh, classAddPhone.NguoiMua, classAddPhone.SDTNguoiMua));
                adapter2.notifyDataSetChanged();
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
        spinner = (Spinner) findViewById(R.id.spinner1);
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
