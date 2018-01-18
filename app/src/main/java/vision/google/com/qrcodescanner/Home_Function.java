package vision.google.com.qrcodescanner;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by Khoa Tran on 10-01-2018.
 */

public class Home_Function {


    public void OpenAddPhoneActivity(View view) {
        Intent myIntent = new Intent(view.getContext(), AddActivity.class);
        view.getContext().startActivity(myIntent);
    }

    public void OpenSellPhoneActivity(View view) {
        Intent myIntent = new Intent(view.getContext(), SellActivity.class);
        view.getContext().startActivity(myIntent);
    }
    public void OpenViewActivity(View view) {
        Intent myIntent = new Intent(view.getContext(), ViewActivity.class);
        view.getContext().startActivity(myIntent);
    }

    public boolean onKeyDown(int keyCode, Activity activity) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
            builder.setTitle("Exit")
                    .setMessage("Bạn có chắc muốn thoát?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

            return true;

        }
        return false;
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
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



}
