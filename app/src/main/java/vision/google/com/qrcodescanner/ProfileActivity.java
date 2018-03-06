package vision.google.com.qrcodescanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    TextView txtUserName, txtName, txtPhone, txtActive, txtAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        AnhXa();
        ShowInfo();

    }

    public void AnhXa() {
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtName = (TextView) findViewById(R.id.txtName);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtActive = (TextView) findViewById(R.id.txtActive);
        txtAdmin = (TextView) findViewById(R.id.txtAdmin);
    }

    public void ShowInfo() {
        SharedPreferences prefs = this.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        txtUserName.setText("Username:  " + prefs.getString("username",""));
        txtName.setText("Họ tên:  " + prefs.getString("name",""));
        txtPhone.setText("Phone:  " + prefs.getString("phone",""));
        if (prefs.getInt("admin",0)==0)
            txtAdmin.setText("Chức vụ:  Nhân viên");
        else
            txtAdmin.setText("Chức vụ:  Admin");

    }
    public void btChangePassword(View view){
        Toast.makeText(this, "Đổi mật khẩu", Toast.LENGTH_SHORT).show();
    }
}
