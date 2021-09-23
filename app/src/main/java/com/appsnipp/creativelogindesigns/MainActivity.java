package com.appsnipp.creativelogindesigns;




import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Date;

import java.util.concurrent.TimeUnit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {
    private Button generate,scan;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    private ImageView qr_code;
    public static String global="";

    public static String Sap="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generate=findViewById(R.id.generate);
        scan=findViewById(R.id.scan);
        qr_code=findViewById(R.id.qrcode);
        checkDB();
        generate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                // Date currentTime = Calendar.getInstance().getTime();

                Calendar calendar = Calendar.getInstance();
                int sec;
                int x, cnt;

                // String text=mytext.getText().toString();
                cnt = 0;
                sec = calendar.get(Calendar.SECOND)+10;
                String text = Integer.toString(sec);
                rootnode = FirebaseDatabase.getInstance();
                reference=rootnode.getReference("random");
                random helper=new random(text);
                reference.setValue(helper);
                checkDB();
                if (text != null && !text.isEmpty()) {
                    try {
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        qr_code.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator=new IntentIntegrator(MainActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });
    }

    private void checkDB() {
        FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        DatabaseReference reference=rootnode.getReference("random");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=(dataSnapshot.getValue().toString().trim());
                data=data.replace("{a=","");
                data=data.replace("}","");
                data=data.replaceAll(" ","");
                global=data;
                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final IntentResult result= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null && result.getContents()!=null ){
            String resultstring=result.toString().substring(26,28);
            resultstring=resultstring.replaceAll(" ","");
            if (resultstring.equals(global)) {
                rootnode = FirebaseDatabase.getInstance();
                reference = rootnode.getReference("randomname");
                random helper = new random(Sap);
                reference.setValue(helper);
            }
            else {
                Toast.makeText(getApplicationContext(),"Data doesn't match"+resultstring,Toast.LENGTH_SHORT).show();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}