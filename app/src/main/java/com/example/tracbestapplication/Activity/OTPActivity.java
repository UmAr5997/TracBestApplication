package com.example.tracbestapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tracbestapplication.Class.UserClass;
import com.example.tracbestapplication.Model.RetrofitClient;
import com.example.tracbestapplication.R;

public class OTPActivity extends AppCompatActivity {

    TextView tvtimer,tvresendotp;
    EditText edtopt;
    Button btnsubmit;
    ProgressDialog dialog;
    int i=90000;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        tvtimer=findViewById(R.id.tvtimer);
        tvresendotp=findViewById(R.id.tvresendotp);
        edtopt=findViewById(R.id.otp);
        btnsubmit=findViewById(R.id.btnsubmit);


        timer=new CountDownTimer(i, 1000) {

            public void onTick(long millisUntilFinished) {
                tvtimer.setText( millisUntilFinished / 1000+" seconds");
            }

            public void onFinish() {
                tvtimer.setText("Expire");
                expireotp();
            }
        }.start();

        tvresendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(OTPActivity.this);
                dialog.setMessage("OTP Resending...");
                dialog.setCancelable(false);
                dialog.show();
                timer.start();
                resendotp();
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateopt();

            }
        });

    }

    private void expireotp() {
        String email;
        email=getIntent().getStringExtra("email");
        if(email.isEmpty())
        { return; }
        Call<UserClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .otp_expire(email);
        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
                if(response.isSuccessful())
                {
                    UserClass userClass=response.body();
                    if(userClass.code.equals("200")) {
                        Toast.makeText(OTPActivity.this, "OTP Expire", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(OTPActivity.this, userClass.message, Toast.LENGTH_SHORT).show();
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

    private void resendotp() {
        String email;
        email=getIntent().getStringExtra("email");
        if(email.isEmpty())
        { return; }
        Call<UserClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .resendotp(email);
        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
                if(response.isSuccessful())
                {
                    UserClass userClass=response.body();
                    if(userClass.code.equals("200")) {
                        dialog.dismiss();
                    }
                    else
                        Toast.makeText(OTPActivity.this, "Invalid Email"+response.errorBody(), Toast.LENGTH_SHORT).show();
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

    private void validateopt() {
        String otp,email;
        email=getIntent().getStringExtra("email");
        otp=edtopt.getText().toString();
        if(otp.isEmpty())
        { edtopt.setError("Enter OTP");
            return; }
        dialog = new ProgressDialog(OTPActivity.this);
        dialog.setMessage("OTP Validating...");
        dialog.setCancelable(false);
        dialog.show();
        Call<UserClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .verifyemailotp(email,otp);
        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
                if(response.isSuccessful())
                {
                    UserClass userClass=response.body();
                    Intent intent = new Intent(OTPActivity.this, RegistrationActivity.class);
                    if(userClass.code.equals("200")) {
                        edtopt.setText("");
                        dialog.dismiss();
                        timer.cancel();
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }else if (userClass.code.equals("201")){
                        Toast.makeText(OTPActivity.this, ""+userClass.message, Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(OTPActivity.this, userClass.message, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
    }
}