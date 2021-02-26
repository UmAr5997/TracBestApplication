package com.example.tracbestapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tracbestapplication.Model.RetrofitClient;
import com.example.tracbestapplication.R;
import com.example.tracbestapplication.Class.UserClass;

public class LoginActivity extends AppCompatActivity {

    EditText edtemail,edtpass;
    Button btnlogin;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        edtemail=findViewById(R.id.email);
        edtpass=findViewById(R.id.pass);
        btnlogin=findViewById(R.id.btnLogin);
        TextView tvregisterlink=findViewById(R.id.tvregisterlink);
        String udata="Register Here";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        tvregisterlink.setText(content);

        String s1 = sharedPreferences.getString("user_id", "");
        if(!s1.isEmpty())
        {
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        tvregisterlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegisterEmailActivity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getrecord();
            }
        });
    }

    private void getrecord() {

        String email,pass;
        email=edtemail.getText().toString();
        pass=edtpass.getText().toString();
        if(email.isEmpty())
        { edtemail.setError("Enter email");
            return; }
        if(pass.isEmpty())
        { edtpass.setError("Enter password");
            return; }
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Logging In...");
        dialog.setCancelable(false);
        dialog.show();
        Call<UserClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(email,pass);
        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
                if(response.isSuccessful())
                {
                    UserClass userClass=response.body();
                    if(userClass.code.equals("200")) {
                        Toast.makeText(LoginActivity.this, "" + userClass.message, Toast.LENGTH_SHORT).show();
                        edtemail.setText("");
                        edtpass.setText("");
                        dialog.dismiss();
                        editor.putString("user_id", String.valueOf(userClass.data.id));
                        editor.putString("user_name",userClass.data.fname+" "+userClass.data.lname);
                        editor.putString("email", userClass.data.email);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else
                        Toast.makeText(LoginActivity.this, "Invalid UserName or Password", Toast.LENGTH_SHORT).show();
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