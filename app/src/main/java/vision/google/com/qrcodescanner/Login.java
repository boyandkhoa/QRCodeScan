package vision.google.com.qrcodescanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    TextView dangky, quenmatkhau;
    CheckInternet check = new CheckInternet();
    Login_Function login_function = new Login_Function();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        dangky.setPaintFlags(dangky.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        check.CheckToast(this, this);
    }

    public void btLogin(View view) {
        login_function.btLogin(this, this, view);
        finish();
    }

    public void txtDangKy(View view) {
        login_function.txtDangKy(this, this, view);
    }

    public void AnhXa() {
        dangky = (TextView) findViewById(R.id.dangky);

    }
}
