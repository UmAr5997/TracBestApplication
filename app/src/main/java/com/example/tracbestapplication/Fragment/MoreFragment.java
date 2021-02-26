package com.example.tracbestapplication.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tracbestapplication.Activity.LoginActivity;
import com.example.tracbestapplication.Activity.StaticInformationActivity;
import com.example.tracbestapplication.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.MODE_PRIVATE;

public class MoreFragment extends Fragment {
    public TextView tvmyprofile,tvguidelines,tvupdates,tvfaqs,tvbroadcast,tvadvice,tvaboutus,tvcontactus,
            tvtermuse,tvprivacynotice,tvuserfeedback,tvsignout;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_frag, container, false);

        sharedPreferences= getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tvmyprofile=view.findViewById(R.id.tvprofile);
        tvsignout=view.findViewById(R.id.tvsignout);
        tvguidelines=view.findViewById(R.id.tvguidelines);

        tvupdates=view.findViewById(R.id.tvupdates);
        tvfaqs=view.findViewById(R.id.tvfaqs);
        tvbroadcast=view.findViewById(R.id.tvbroadcast);
        tvadvice=view.findViewById(R.id.tvadvise);
        tvaboutus=view.findViewById(R.id.tvaboutus);
        tvcontactus=view.findViewById(R.id.tvcontactus);
        tvtermuse=view.findViewById(R.id.tvtermnuse);
        tvprivacynotice=view.findViewById(R.id.tvnotice);
        tvuserfeedback=view.findViewById(R.id.tvuserfeedback);



        tvguidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), StaticInformationActivity.class);
                intent.putExtra("title",tvguidelines.getText().toString());
                startActivity(intent);
            }
        });
        tvupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), StaticInformationActivity.class);
                intent.putExtra("title",tvupdates.getText().toString());
                startActivity(intent);
            }
        });
        tvfaqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), StaticInformationActivity.class);
                intent.putExtra("title",tvfaqs.getText().toString());
                startActivity(intent);
            }
        });
        tvbroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), StaticInformationActivity.class);
                intent.putExtra("title",tvbroadcast.getText().toString());
                startActivity(intent);
            }
        });
        tvadvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), StaticInformationActivity.class);
                intent.putExtra("title",tvadvice.getText().toString());
                startActivity(intent);
            }
        });
        tvaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), StaticInformationActivity.class);
                intent.putExtra("title",tvaboutus.getText().toString());
                startActivity(intent);
            }
        });
        tvcontactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), StaticInformationActivity.class);
                intent.putExtra("title",tvcontactus.getText().toString());
                startActivity(intent);
            }
        });
        tvtermuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), StaticInformationActivity.class);
                intent.putExtra("title",tvtermuse.getText().toString());
                startActivity(intent);
            }
        });
        tvprivacynotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), StaticInformationActivity.class);
                intent.putExtra("title",tvprivacynotice.getText().toString());
                startActivity(intent);
            }
        });
        tvuserfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), StaticInformationActivity.class);
                intent.putExtra("title",tvuserfeedback.getText().toString());
                startActivity(intent);
            }
        });

        tvsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        tvmyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return  view;
    }

}
