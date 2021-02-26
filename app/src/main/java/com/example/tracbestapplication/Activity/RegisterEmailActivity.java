package com.example.tracbestapplication.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tracbestapplication.Class.UserClass;
import com.example.tracbestapplication.Model.RetrofitClient;
import com.example.tracbestapplication.R;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterEmailActivity extends AppCompatActivity {

    EditText edtemail;
    Button btnsubtmit;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailregister);

        edtemail=findViewById(R.id.email);
        btnsubtmit=findViewById(R.id.btnsubmit);
        btnsubtmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateemail();
            }
        });
    }

    private void validateemail() {

        String email;
        email=edtemail.getText().toString();
        if(email.isEmpty())
        { edtemail.setError("Enter email");
            return; }
        dialog = new ProgressDialog(RegisterEmailActivity.this);
        dialog.setMessage("Email Registration...");
        dialog.setCancelable(false);
        dialog.show();
        Call<UserClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .emailregister(email);
        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
                if(response.isSuccessful())
                {
                    UserClass userClass=response.body();
                    Intent intent = new Intent(RegisterEmailActivity.this, OTPActivity.class);
                    if(userClass.code.equals("200")) {
                        Toast.makeText(RegisterEmailActivity.this, "" + userClass.message, Toast.LENGTH_SHORT).show();
                        edtemail.setText("");
                        dialog.dismiss();
                        intent.putExtra("email",userClass.data.email);
                        startActivity(intent);
                    }else if (userClass.code.equals("201")){
                        Toast.makeText(RegisterEmailActivity.this, "" + userClass.message, Toast.LENGTH_SHORT).show();
                        edtemail.setText("");
                        dialog.dismiss();
                        intent.putExtra("email",userClass.data.email);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(RegisterEmailActivity.this, "Invalid Email"+response.errorBody(), Toast.LENGTH_SHORT).show();
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