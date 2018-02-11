package vision.google.com.qrcodescanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SellActivity extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        // Write a message to the database
        Button button = (Button) findViewById(R.id.btn);
        final TextView textView1 = (TextView) findViewById(R.id.txt1);
        final TextView textView2 = (TextView) findViewById(R.id.txt2);
        final EditText editText = (EditText) findViewById(R.id.edt);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final TextView textView = (TextView) findViewById(R.id.txt);
//                final EditText editText = (EditText) findViewById(R.id.edt);
                final DatabaseReference dinosaursRef = database.getReference("dinosaurs");

//                dinosaursRef.orderByChild("height").addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
//                        Dinosaur dinosaur = dataSnapshot.getValue(Dinosaur.class);
//                        textView.setText(dataSnapshot.getKey() + " was " + dinosaur.height + " meters tall.");
////                        System.out.println(dataSnapshot.getKey() + " was " + dinosaur.height + " meters tall.");
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//
//                    // ...
//                });


                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                final Query query = reference
                        .child("Kho")
                        .child("Kho")
                        .orderByChild("Loai")
                        .equalTo(editText.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String a = dataSnapshot.getValue().toString();
//                            String a = dataSnapshot.toString();
                            textView2.setText(a);// do something with the individual "issues"
                            // dataSnapshot is the "issue" node with all children with id 0
//                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
//                                if (dataSnapshot.hasChild("Ten"))
//                                    textView1.setText(dataSnapshot.toString());// do something with the individual "issues"
//                            }

                        } else {
                            textView2.setText("Deo co cai cho gi");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });
//
            }
        });


    }






}
