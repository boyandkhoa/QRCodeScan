package vision.google.com.qrcodescanner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

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
    public void OpenProfileActivity(View view) {
        Intent myIntent = new Intent(view.getContext(), ProfileActivity.class);
        view.getContext().startActivity(myIntent);
    }
    public void OpenManagerEmployeeActivity(View view) {
        Intent myIntent = new Intent(view.getContext(), ManagerEmployeeActivity.class);
        view.getContext().startActivity(myIntent);
    }
    public void Logout(View view, Activity activity) {
        SharedPreferences prefs =activity.getSharedPreferences("infoUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username","");
        editor.commit();
        Intent myIntent = new Intent(activity, Login.class);
        activity.startActivity(myIntent);
        activity.finish();

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
