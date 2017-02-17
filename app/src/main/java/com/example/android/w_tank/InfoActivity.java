package com.example.android.w_tank;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Button getData=(Button)findViewById(R.id.get_data);
        final EditText clientName=(EditText)findViewById(R.id.client_name);
        final EditText clientKey=(EditText)findViewById(R.id.client_key);
        final EditText UserName=(EditText)findViewById(R.id.user_name);
        final EditText deviceNumber=(EditText)findViewById(R.id.device_number);

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InfoActivity.this,MainActivity.class);
                intent.putExtra(MainActivity.ClientNameTag,clientName.getText());
                intent.putExtra(MainActivity.ClientKEYTag,clientKey.getText());
                intent.putExtra(MainActivity.USERNameTag,UserName.getText());
                intent.putExtra(MainActivity.DeviceNumberTag,deviceNumber.getText());
                startActivity(intent);

            }
        });

    }
}
