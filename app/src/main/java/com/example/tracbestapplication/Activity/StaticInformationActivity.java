package com.example.tracbestapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tracbestapplication.R;

import org.w3c.dom.Text;

public class StaticInformationActivity extends AppCompatActivity {

    TextView tvinfo,tvstaticinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_information);

        tvinfo=findViewById(R.id.tvinformation);
        tvstaticinfo=findViewById(R.id.tvstaticinformation);
        String txt=getIntent().getStringExtra("title");
        tvinfo.setText(txt);

        if(txt.equals("Privacy Notice"));
        {
            tvstaticinfo.setText("Platform requirements aside, under the vast majority of legislations (and particularly under the GDPR) privacy notices are legally required.\n" +
                    "If your app handles personal or sensitive user data, or is in the “Designed for Families” program, you need to add a valid privacy policy in two places: your app’s Store listing page and within your app.\n" +
                    "If applicable, you have to disclose how you treat sensitive user and device data.\n" +
                    "If your app processes personal data for reasons unrelated to its functionality, you must highlight – prior to the collection and transmission – how the user data will be used and collect user consent.\n" +
                    "If your app is likely to be used by kids, you are subject to additional safety requirements.\n" +
                    "With iubenda you can create a privacy policy (and a Terms and Conditions document) for your Android app.");
        }
    }
}