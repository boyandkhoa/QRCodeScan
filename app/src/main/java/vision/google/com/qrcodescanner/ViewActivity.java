package vision.google.com.qrcodescanner;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ViewActivity extends AppCompatActivity {
    DatabaseReference infoPhone = FirebaseDatabase.getInstance().getReference();
    ListView lvHinhAnh;
    ArrayList<ClassAddPhone> mangHinhAnh;
    HinhAnhRow adapter = null;
    String string, AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        AnhXa();
        adapter= new HinhAnhRow(this, R.layout.row_view, mangHinhAnh);
        lvHinhAnh.setAdapter(adapter);
        LoadData();


    }

    public void LoadData() {
        infoPhone.child("Kho").child("Kho").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ClassAddPhone classAddPhone = dataSnapshot.getValue(ClassAddPhone.class);
                mangHinhAnh.add(new ClassAddPhone(classAddPhone.Ten, classAddPhone.Imei, classAddPhone.GiaBan, classAddPhone.LinkHinh));
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
