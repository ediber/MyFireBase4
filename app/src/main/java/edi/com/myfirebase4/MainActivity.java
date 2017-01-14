package edi.com.myfirebase4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View addSingle;
    private FirebaseDatabase database;
    private View addArray;
    private EditText value1;
    private EditText key2;
    private EditText key1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addSingle = findViewById(R.id.addSingle);
        addArray = findViewById(R.id.addArray);
        key1 = (EditText)findViewById(R.id.key1);
        value1 = (EditText)findViewById(R.id.value1);
        key2 = (EditText)findViewById(R.id.key2);

        database = FirebaseDatabase.getInstance();



        addSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database
                DatabaseReference messageRef = database.getReference(key1.getText().toString());
                messageRef.setValue(value1.getText().toString());


                // listen to changes
                messageRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                        Log.d("ValueEventListener", "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("ValueEventListener", "Failed to read value.", error.toException());
                    }
                });
            }
        });

        addArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List nameList = new ArrayList<>(Arrays.asList("John,Tim,Sam,Ben"));
                DatabaseReference arrayRef = database.getReference(key2.getText().toString());
                arrayRef.setValue(nameList);


                // listen to changes
                arrayRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

//                String value = dataSnapshot.getValue(String.class);
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        for (DataSnapshot child: children) {
                            String value = child.getValue(String.class);
                            Log.d("array ValueEventListener", "Value is: " + value);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("ValueEventListener", "Failed to read value.", error.toException());
                    }
                });
            }
        });






    }
}
