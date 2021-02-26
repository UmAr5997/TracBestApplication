package com.example.tracbestapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tracbestapplication.Class.UserClass;
import com.example.tracbestapplication.Model.RetrofitClient;
import com.example.tracbestapplication.R;

public class RegistrationActivity extends AppCompatActivity {

    TextView tvsignin;
    RadioButton rdbprivacy,rdbterms;
    Spinner spinnergender;
    EditText edtfname,edtlname,edtcontactno,edtaddress,edtzipcode,edtcreatepassword,edtconfirmpass;
    Button btnsubmit,btnmale,btnfemale;
    String strgender,strsurname,strcreatepass,strconfpass;
    int igen=0;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        spinnergender=findViewById(R.id.spinnergender);

        tvsignin=findViewById(R.id.tvsignin);

        edtfname=findViewById(R.id.edtfirstname);
        edtlname=findViewById(R.id.edtlastname);
        edtcontactno=findViewById(R.id.edtmobileno);
        edtaddress=findViewById(R.id.edtaddress);
        edtzipcode=findViewById(R.id.edtzipcode);
        edtcreatepassword=findViewById(R.id.edtcreatepass);
        edtconfirmpass=findViewById(R.id.edtconfirmpass);

        btnfemale=findViewById(R.id.btnfemale);
        btnmale=findViewById(R.id.btnmale);
        btnsubmit=findViewById(R.id.btnsubmit);

        rdbprivacy=findViewById(R.id.rdbprivacypolicy);
        rdbterms=findViewById(R.id.rdbtermsncon);


        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergender.setAdapter(adapter);

        spinnergender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strsurname=(String) spinnergender.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                strsurname=(String) spinnergender.getSelectedItem();
            }
        });

        btnmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    btnmale.setBackgroundColor(Color.parseColor("#0B4275"));
                    btnfemale.setBackgroundColor(Color.parseColor("#E4E2E2"));
                    strgender = "Male";
            }
        });
        btnfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    btnfemale.setBackgroundColor(Color.parseColor("#0B4275"));
                    btnmale.setBackgroundColor(Color.parseColor("#E4E2E2"));
                    strgender = "Female";
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              registeruser();
            }
        });
    }

    private void registeruser() {

        String email,fname,lname,contactno,address,zipcode,createpass,confirmpass;
        email=getIntent().getStringExtra("email");
        fname=edtfname.getText().toString();
        lname=edtlname.getText().toString();
        contactno=edtcontactno.getText().toString();
        address=edtaddress.getText().toString();
        zipcode=edtzipcode.getText().toString();
        createpass=edtcreatepassword.getText().toString();
        confirmpass=edtconfirmpass.getText().toString();
        if(fname.isEmpty())
        { edtfname.setError("Enter First Name");
            return; }
        if(lname.isEmpty())
        { edtlname.setError("Enter Last Name");
            return; }
        if(contactno.isEmpty())
        { edtcontactno.setError("Enter Contact Number");
            return; }
        if(address.isEmpty())
        { edtaddress.setError("Enter your Address");
            return; }
        if(zipcode.isEmpty())
        { edtzipcode.setError("Enter Zip/Post Code");
            return; }
        if(createpass.isEmpty())
        { edtcreatepassword.setError("Enter Password");
            return; }
        if(confirmpass.isEmpty())
        { edtconfirmpass.setError("Enter Confirm Password");
            return; }
        if(!createpass.equals(confirmpass))
        {
            edtconfirmpass.setError("Password does not match");
            return;
        }
        if(!rdbprivacy.isChecked())
        {
            Toast.makeText(this, "Agree Term & Conditions", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!rdbterms.isChecked())
        {
            Toast.makeText(this, "Agree Term & Conditions", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog = new ProgressDialog(RegistrationActivity.this);
        dialog.setMessage("Registration...");
        dialog.setCancelable(false);
        dialog.show();
        Call<UserClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .profileupdate(email,fname,lname,strgender,contactno,address,zipcode,createpass);
        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
                if(response.isSuccessful())
                {
                    UserClass userClass=response.body();
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    if(userClass.code.equals("200")) {
                        startActivity(intent);
                    }else if (userClass.code.equals("201")){
                        Toast.makeText(RegistrationActivity.this, ""+userClass.message, Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(RegistrationActivity.this, userClass.message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else {
                    dialog.dismiss();
                    //Toast.makeText(LoginActivity.this, "Failed"+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserClass> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}