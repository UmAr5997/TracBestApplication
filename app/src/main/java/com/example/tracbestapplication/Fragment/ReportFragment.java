package com.example.tracbestapplication.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tracbestapplication.Class.UserClass;
import com.example.tracbestapplication.Model.RetrofitClient;
import com.example.tracbestapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ReportFragment extends Fragment {
    public TextView startdate,todate;
    Spinner spinnergender,spinnerreport,spinnerdiagnose;
    EditText edtfname,edtlname,edtcontactno,edtaddress,edtzipcode,edtlocalgp,edtemail,edtmobileno,edtsymptomstatus,edtsymptomstartdate;
    Button btnsubmit,btnmale,btnfemale;
    String strgender,strhigh="no",strnew="no",strchangesmell="no",strlosstaste="no",strdiagnose,strreport;
    ProgressDialog dialog;
    CheckBox chkhigh,chknew,chkchangesmell,chklosstaste;
    SharedPreferences sh;
    SharedPreferences.Editor editor;
    final Calendar myCalendar = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_frag, container, false);

        sh= this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        editor=sh.edit();
        spinnergender=view.findViewById(R.id.spinnergender);
        spinnerreport=view.findViewById(R.id.spinnerreport);
        spinnerdiagnose=view.findViewById(R.id.spinnerdiagnose);

        edtfname=view.findViewById(R.id.edtfirstname);
        edtlname=view.findViewById(R.id.edtlastname);
        edtcontactno=view.findViewById(R.id.edtmobileno);
        edtaddress=view.findViewById(R.id.edtaddress);
        edtzipcode=view.findViewById(R.id.edtzipcode);
        edtsymptomstartdate=view.findViewById(R.id.edtsymptomstartdate);
        edtemail=view.findViewById(R.id.edtemail);
        edtlocalgp=view.findViewById(R.id.edtlocal);
        edtmobileno=view.findViewById(R.id.edtmobileno);
        edtsymptomstatus=view.findViewById(R.id.edtsymptomstatus);

        chkhigh=view.findViewById(R.id.chkhigh);
        chknew=view.findViewById(R.id.chknew);
        chkchangesmell=view.findViewById(R.id.chkchangesmell);
        chklosstaste=view.findViewById(R.id.chklosstaste);


        btnfemale=view.findViewById(R.id.btnfemale);
        btnmale=view.findViewById(R.id.btnmale);
        btnsubmit=view.findViewById(R.id.btnsubmit);
        

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergender.setAdapter(adapter);

        spinnergender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // strgender=(String) spinnergender.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
               // strgender=(String) spinnergender.getSelectedItem();
            }
        });

        ArrayAdapter<CharSequence> adapterreport = ArrayAdapter.createFromResource(getContext(),R.array.report, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerreport.setAdapter(adapterreport);

        spinnerreport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strreport=(String) spinnerreport.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                strreport=(String) spinnerreport.getSelectedItem();
            }
        });
        ArrayAdapter<CharSequence> adapterdiagnose = ArrayAdapter.createFromResource(getContext(),R.array.diagnose, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerdiagnose.setAdapter(adapterdiagnose);

        spinnerdiagnose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strdiagnose=(String) spinnerdiagnose.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                strdiagnose=(String) spinnerdiagnose.getSelectedItem();
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        edtsymptomstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

        chkhigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chkhigh.isChecked())
                    strhigh="yes";
                else
                    strhigh="no";
            }
        });
        chknew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chknew.isChecked())
                    strnew="yes";
                else
                    strnew="no";
            }
        });
        chkchangesmell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chkchangesmell.isChecked())
                    strchangesmell="yes";
                else
                    strchangesmell="no";
            }
        });
        chklosstaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chklosstaste.isChecked())
                    strlosstaste="yes";
                else
                    strlosstaste="no";
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postreport();
            }
        });

        return  view;
    }
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtsymptomstartdate.setText(sdf.format(myCalendar.getTime()));
    }
    private void postreport() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Report...");
        dialog.setCancelable(false);
        dialog.show();
        String email=sh.getString("email","");
        String fname,lname,contactno,address,zipcode,localgp,symptomstatus,symptomdate;
        fname=edtfname.getText().toString();
        lname=edtlname.getText().toString();
        contactno=edtcontactno.getText().toString();
        address=edtaddress.getText().toString();
        zipcode=edtzipcode.getText().toString();
        localgp=edtlocalgp.getText().toString();
        symptomstatus=edtsymptomstatus.getText().toString();
        symptomdate=edtsymptomstartdate.getText().toString();
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
        if(localgp.isEmpty())
        {edtlocalgp.setError("Enter Local GP/Authority");
        return; }
        Call<UserClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .report(strreport,strgender,fname,lname,address,zipcode,localgp,contactno,email,strdiagnose,symptomstatus,strhigh,
                        strnew,strchangesmell,strlosstaste,symptomdate);
        call.enqueue(new Callback<UserClass>() {
            @Override
            public void onResponse(Call<UserClass> call, Response<UserClass> response) {
                if(response.isSuccessful())
                {
                    UserClass userClass=response.body();
                    if(userClass.code.equals("200")) {
                        Toast.makeText(getContext(), ""+userClass.message, Toast.LENGTH_SHORT).show();
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

