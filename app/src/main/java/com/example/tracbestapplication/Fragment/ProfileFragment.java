package com.example.tracbestapplication.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tracbestapplication.Activity.OTPActivity;
import com.example.tracbestapplication.Activity.RegisterEmailActivity;
import com.example.tracbestapplication.Activity.RegistrationActivity;
import com.example.tracbestapplication.Class.UserClass;
import com.example.tracbestapplication.Model.RetrofitClient;
import com.example.tracbestapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    public TextView startdate,todate;
    Spinner spinnergender;
    EditText edtfname,edtlname,edtcontactno,edtaddress,edtzipcode,edtjoineddate;
    Button btnsubmit,btnmale,btnfemale,btnedit,btnupdate;
    String strgender,strsurname,strcreatepass,strconfpass;
    ProgressDialog dialog;
    SharedPreferences sh;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_frag, container, false);

        sh= this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        editor=sh.edit();
        spinnergender=view.findViewById(R.id.spinnergender);


        edtfname=view.findViewById(R.id.edtfirstname);
        edtlname=view.findViewById(R.id.edtlastname);
        edtcontactno=view.findViewById(R.id.edtmobileno);
        edtaddress=view.findViewById(R.id.edtaddress);
        edtzipcode=view.findViewById(R.id.edtzipcode);
        edtjoineddate=view.findViewById(R.id.edtjoineddate);


        btnfemale=view.findViewById(R.id.btnfemale);
        btnmale=view.findViewById(R.id.btnmale);
        btnsubmit=view.findViewById(R.id.btnsubmit);

        btnedit=view.findViewById(R.id.btnedit);
        btnupdate=view.findViewById(R.id.btnupdate);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergender.setAdapter(adapter);
        setuserdata();


        return  view;
    }

    private void setuserdata() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Profile Data...");
        dialog.setCancelable(false);
        dialog.show();
        String email=sh.getString("email","");
        Call<UserClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .profile(email);
        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
                if(response.isSuccessful())
                {
                    UserClass userClass=response.body();
                    if(userClass.code.equals("200")) {
                        if(userClass.data.gender.equals("Male")) {
                            btnmale.setBackgroundColor(Color.parseColor("#0B4275"));
                            btnfemale.setBackgroundColor(Color.parseColor("#E4E2E2"));
                        }
                        else {
                            btnfemale.setBackgroundColor(Color.parseColor("#0B4275"));
                            btnmale.setBackgroundColor(Color.parseColor("#E4E2E2"));
                        }
                        edtfname.setText(userClass.data.fname);
                        edtlname.setText(userClass.data.lname);
                        edtcontactno.setText(userClass.data.contact);
                        edtaddress.setText(userClass.data.address);
                        edtzipcode.setText(userClass.data.postCode);
                        edtjoineddate.setText(userClass.data.createdAt);
                        dialog.dismiss();
                    }else if (userClass.code.equals("201")){
                        Toast.makeText(getContext(), ""+userClass.message, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else
                        Toast.makeText(getContext(), userClass.message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserClass> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

}
