package vision.google.com.qrcodescanner;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    TextView dangky;
    EditText edtUserName, edtPass;
    CheckInternet check = new CheckInternet();
    Login_Function login_function = new Login_Function();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        dangky.setPaintFlags(dangky.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        check.CheckToast(this, this);
        SharedPreferences prefs = this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        if(prefs.getString("username","").equals("")==false){
            Intent myIntent = new Intent(this, HomeActivity.class);
            this.startActivity(myIntent);
            finish();
        }
    }

    public void btLogin(final View view) {
        if (check.isNetworkConnected(this) == false) {
            Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            return;
        }
        final Dialog uploading = new
                Dialog(this);
        uploading.setCancelable(false);
        uploading.setContentView(R.layout.uploading);
        uploading.show();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final Query query = reference
                .child("User")
                .orderByChild("UserName")
                .equalTo(edtUserName.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        ClassAddUser classAddUser = singleSnapshot.getValue(ClassAddUser.class);
                        if (edtPass.getText().toString().equals(classAddUser.PassWord.toString())) {
                            SharedPreferences prefs = getSharedPreferences("infoUser", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("username", classAddUser.UserName);
                            editor.putString("password", classAddUser.PassWord);
                            editor.putString("name", classAddUser.HoTen);
                            editor.putString("phone", classAddUser.NumberPhone);
                            editor.putInt("admin", Integer.parseInt(classAddUser.Admin));
                            editor.putInt("active", Integer.parseInt(classAddUser.Active));
                            editor.commit();
                            Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                            view.getContext().startActivity(myIntent);
                            uploading.cancel();
                            finish();
                        } else if (edtPass.getText().toString().equals(classAddUser.PassWord.toString()) == false) {
                            uploading.cancel();
                            Toast.makeText(Login.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                        }

                    }
//                            }


                } else {
                    uploading.cancel();
                    Toast.makeText(Login.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }

    public void txtDangKy(View view) {
        login_function.txtDangKy(this, this, view);
    }

    public void AnhXa() {
        dangky = (TextView) findViewById(R.id.dangky);
        edtUserName = (EditText) findViewById(R.id.editTextUserName);
        edtPass = (EditText) findViewById(R.id.editTextPassword);
    }
}
