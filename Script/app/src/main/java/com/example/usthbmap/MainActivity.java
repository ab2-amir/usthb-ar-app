package com.example.usthbmap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button b1,b2;
    EditText ed1;

    TextView tx1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);

        b2 = (Button)findViewById(R.id.button2);
        //tx1.setVisibility(View.GONE);

        b1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(ed1.getText().toString().equals("1") || ed1.getText().toString().equals("2")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                    openActivity2();
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_LONG).show();
                    //tx1.setVisibility(View.VISIBLE);
                    //tx1.setBackgroundColor(Color.RED);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void openActivity2(){
        Intent intent = new Intent(this, ar_map_activity.class);
        startActivity(intent);
    }
}