package com.appsnipp.creativelogindesigns;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase rootnode;
    Context context=this;
    DatabaseReference reference;
    private EditText pass,SapId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SapId=findViewById(R.id.editTextSapId);
        pass=findViewById(R.id.editTextPassword);
        rootnode = FirebaseDatabase.getInstance();
        reference=rootnode.getReference("users");
    }



    public void sign_up(View view) {
        startActivity(new Intent(this,RegisterActivity.class));

    }
    public void scan(View view) {
        Query query = reference.orderByKey().equalTo(SapId.getText().toString().trim());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Toast.makeText(getApplicationContext(),"P2",Toast.LENGTH_SHORT).show();
                    // dataSnapshot is the "issue" node with all children with id 0

                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"

                        Toast.makeText(getApplicationContext(),"P3",Toast.LENGTH_SHORT).show();
                        UserHelper usersBean = user.getValue(UserHelper.class);

                        if (usersBean.password.equals(pass.getText().toString().trim())) {
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),"Password is wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"user not found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                Toast.makeText(getApplicationContext(),"P2",Toast.LENGTH_SHORT).show();
                UserHelper newPost = dataSnapshot.getValue(UserHelper.class);
                if (newPost.password.equals(pass.getText().toString().trim())) {

                    Toast.makeText(getApplicationContext(),"P3",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Password is wrong",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });*/



    }
}
