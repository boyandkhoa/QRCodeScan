package vision.google.com.qrcodescanner;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
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



}
