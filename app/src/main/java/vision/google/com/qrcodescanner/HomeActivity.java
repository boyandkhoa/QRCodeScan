package vision.google.com.qrcodescanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    private int currentApiVersion;
    LinearLayout linearLayoutAdd,linearLayoutSell,linearLayoutView;
    @Override
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentApiVersion = android.os.Build.VERSION.SDK_INT;


        setContentView(R.layout.activity_home);
        if(isNetworkConnected()==false){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage("Không có kết nối internet, vui lòng kiểm tra và thử lại sau!").setPositiveButton("Yes", dialogClickListener).show();
        }

        linearLayoutAdd = (LinearLayout) findViewById(R.id.layoutADD);
        final CountDownTimer yourCountDownTimer = new CountDownTimer(1000,1){
            public  void onTick(long millisUntilFinished){

            }
            public void onFinish(){
                linearLayoutAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                        startActivityForResult(intent,1);
                    }
                });
            }
        }.start();
        linearLayoutSell = (LinearLayout) findViewById(R.id.layoutSELL);
        linearLayoutSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SellActivity.class);
                startActivityForResult(intent,1);
            }
        });


    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    System.exit(1);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode==KeyEvent.KEYCODE_BACK||keyCode==KeyEvent.KEYCODE_HOME){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage("Bạn có chắc muốn thoát?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
