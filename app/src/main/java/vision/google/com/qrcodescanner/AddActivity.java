package vision.google.com.qrcodescanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    DatabaseReference infoPhone;
    Button scanbtn, submit;
    TextView result;
    EditText name, imei, giaban;
    Spinner spinnerLoai;
    String LoaiPhone = "";
    ImageView imgHinh;
    int REQUEST_CODE_IMGHinh =1;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add);
        name = (EditText) findViewById(R.id.Name);

        imei = (EditText) findViewById(R.id.result);
        giaban = (EditText) findViewById(R.id.GiaBan);
        scanbtn = (Button) findViewById(R.id.scanbtn);
        submit = (Button) findViewById(R.id.submit);
        spinnerLoai = (Spinner) findViewById(R.id.spinnerLoai);
        result = (TextView) findViewById(R.id.result);
        giaban.addTextChangedListener(onTextChangedListener());
        imgHinh = (ImageView)findViewById(R.id.imgHinh);
        final ArrayList<String> Loai = new ArrayList<String>();
        Loai.add("SmartPhone");
        Loai.add("Tablet");
        Loai.add("Normal");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_item, Loai);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerLoai.setAdapter(arrayAdapter);
        spinnerLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(AddActivity.this,Loai.get(i), Toast.LENGTH_SHORT).show();
                LoaiPhone = Loai.get(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoPhone = FirebaseDatabase.getInstance().getReference();
                ClassAddPhone addPhone = new ClassAddPhone(name.getText().toString(), LoaiPhone, Integer.parseInt(giaban.getText().toString()), "Nguyễn Văn A", "02/01/2018");
                infoPhone.child("Kho").child("Kho").child(imei.getText().toString()).setValue(addPhone);

            }
        });

        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_IMGHinh);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(barcode.displayValue);
                    }
                });
            }
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.exit(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //Number Format
    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                giaban.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    giaban.setText(formattedString);
                    giaban.setSelection(giaban.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                giaban.addTextChangedListener(this);
            }
        };
    }
}
