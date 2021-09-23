package com.appsnipp.creativelogindesigns;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    private EditText name,mobileno,email,password,SapId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.editTextName);
        mobileno=findViewById(R.id.editTextMobile);
        SapId=findViewById(R.id.editTextSapId);
        email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

    }
    public void already_have(View view) {
        startActivity(new Intent(this,LoginActivity.class));

    }
    public void register(View view) {
        rootnode = FirebaseDatabase.getInstance();
        reference=rootnode.getReference("users");
        String name1=name.getText().toString();
        String mobileno1=mobileno.getText().toString();
        String SapId1=SapId.getText().toString();
        String email1=email.getText().toString();
        String password1=password.getText().toString();

        UserHelper helper=new UserHelper(name1,SapId1,mobileno1,email1,password1);
        reference.child(SapId1).setValue(helper);
        MainActivity mainActivity=new MainActivity();
        mainActivity.Sap=SapId1;
        startActivity(new Intent(this,MainActivity.class));
    }
}
