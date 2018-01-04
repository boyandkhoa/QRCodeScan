package vision.google.com.qrcodescanner;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    TextView dangky;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dangky = (TextView)findViewById(R.id.dangky);
        login = (Button) findViewById(R.id.login);

        dangky.setPaintFlags(dangky.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Đăng ký", Toast.LENGTH_SHORT).show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, HomeActivity.class);
                startActivityForResult(intent,1);
                finish();
            }
        });


    }
}
