package vision.google.com.qrcodescanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    DatabaseReference infoPhone = FirebaseDatabase.getInstance().getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Button scanbtn, submit;
    TextView result,txtTitle;
    EditText name, imei, giaban;
    Spinner spinnerLoai;
    String LoaiPhone = "";
    ImageView imgHinh;
    int REQUEST_CODE_IMGHinh = 1;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add);
        AnhXa();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://phuongnammobile-8106e.appspot.com");
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
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child("img" + calendar.getTimeInMillis() + "jpg");//
                // Get the data from an ImageView as bytes
                imgHinh.setDrawingCacheEnabled(true);
                imgHinh.buildDrawingCache();
                Bitmap bitmap = imgHinh.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(AddActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(AddActivity.this, "Luu hinh thanh cong", Toast.LENGTH_SHORT).show();
                        Log.d("AAAAAAAAAA", downloadUrl + "");
                        ClassAddPhone classAddPhone = new ClassAddPhone(imei.getText().toString(),
                                name.getText().toString(),
                                LoaiPhone,
                                giaban.getText().toString(),
                                "Nguyen Van A",
                                "22/11/2017",
                                String.valueOf(downloadUrl));
                        infoPhone.child("Kho").child("Kho").push().setValue(classAddPhone, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError==null) {
                                    Toast.makeText(AddActivity.this, "Luu du lieu thanh cong", Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    Toast.makeText(AddActivity.this, "Luu du lieu that bai", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

//                ClassAddPhone addPhone = new ClassAddPhone(name.getText().toString(), LoaiPhone, Integer.parseInt(giaban.getText().toString()), "Nguyễn Văn A", "02/01/2018","sdfsfsfs.com"/*String.valueOf(downloadUrl)*/);
//                infoPhone.child("Kho").child("Kho").child(imei.getText().toString()).setValue(addPhone);


            }
        });

        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMGHinh);
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
        if (requestCode == REQUEST_CODE_IMGHinh && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    public void AnhXa() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
//        txtTitle.setTypeface(Typeface.createFromAsset(getAssets(),"VINHAN.TTF"));
        name = (EditText) findViewById(R.id.Name);
        imei = (EditText) findViewById(R.id.result);
        giaban = (EditText) findViewById(R.id.GiaBan);
        scanbtn = (Button) findViewById(R.id.scanbtn);
        submit = (Button) findViewById(R.id.submit);
        spinnerLoai = (Spinner) findViewById(R.id.spinnerLoai);
        result = (TextView) findViewById(R.id.result);
        giaban.addTextChangedListener(onTextChangedListener());
        imgHinh = (ImageView) findViewById(R.id.imgHinh);
    }
}
