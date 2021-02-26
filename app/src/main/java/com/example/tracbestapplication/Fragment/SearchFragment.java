package com.example.tracbestapplication.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tracbestapplication.Class.UserClass;
import com.example.tracbestapplication.Class.bussinessClass;
import com.example.tracbestapplication.Class.categoryClass;
import com.example.tracbestapplication.Model.RetrofitClient;
import com.example.tracbestapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    MapView mapView;
    FusedLocationProviderClient client;
    String strlatitude, strlongitute;
    bussinessClass res;
    EditText edtzipcode;
    Spinner spinnercategory;
    Button btnsearch;
    TextView tvcurrent;
    HashMap<String,String> strcategory = new HashMap<String, String>();
    ArrayList<String> arrcategory=new ArrayList<>();
    ProgressDialog dialog;
    String id,strbname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_frag, container, false);

        mapView = view.findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        tvcurrent=view.findViewById(R.id.txtcurrent5);
        edtzipcode=view.findViewById(R.id.edtpostcode);
        spinnercategory=view.findViewById(R.id.spinnercategory);

        btnsearch=view.findViewById(R.id.btnsearch);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading Map...");
        dialog.setCancelable(false);
        dialog.show();
        getCurrentLocation();

        getallcategories();

        spinnercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               id = strcategory.get(spinnercategory.getSelectedItem());
               strbname=(String) spinnercategory.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
              id=strcategory.get(spinnercategory.getSelectedItem());
            }
        });

        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zipcode=edtzipcode.getText().toString();
                businessbycategory(zipcode);
                tvcurrent.setText(strbname);
            }
        });

        return view;
    }

    private void businessbycategory(String zipcode) {
        Toast.makeText(getContext(), ""+zipcode+id, Toast.LENGTH_SHORT).show();
        if(zipcode!=null || id!=null) {
            Call<bussinessClass> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .businessbycategory(id, zipcode);
            call.enqueue(new Callback<bussinessClass>() {
                @Override
                public void onResponse(Call<bussinessClass> call, Response<bussinessClass> response) {
                    if (response.isSuccessful()) {
                        res = response.body();
                        Toast.makeText(getContext(), ""+res.message, Toast.LENGTH_SHORT).show();
                        drawmarker(res);
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<bussinessClass> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void getallcategories() {


        Call<categoryClass> call = RetrofitClient
                .getInstance()
                .getApi()
                .get_categories();
        call.enqueue(new Callback<categoryClass>() {
            @Override
            public void onResponse(Call<categoryClass> call, Response<categoryClass> response) {
                if (response.isSuccessful()) {
                    categoryClass result=response.body();
                    for(int i=0;i<result.data.size();i++) {
                        arrcategory.add(result.data.get(i).categoryName);
                        strcategory.put(result.data.get(i).categoryName, String.valueOf(result.data.get(i).id));
                    }

                    if(arrcategory.size()!=0) {
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrcategory);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinnercategory.setAdapter(adapter);
                        dialog.cancel();
                    }
                } else {
                    dialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<categoryClass> call, Throwable t) {
               dialog.cancel();
            }
        });
    }


    private void getallbussinessmap() {
        if (strlatitude != null && strlongitute != null) {
            Call<bussinessClass> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .nearby(strlatitude, strlongitute);
            call.enqueue(new Callback<bussinessClass>() {
                @Override
                public void onResponse(Call<bussinessClass> call, Response<bussinessClass> response) {
                    if (response.isSuccessful()) {
                        res = response.body();
                        drawmarker(res);
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<bussinessClass> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void drawmarker(bussinessClass res) {
        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                for (int i = 0; i < res.data.size(); i++){
                                    LatLng latLng1 = new LatLng(Double.parseDouble(res.data.get(i).latitude), Double.parseDouble(res.data.get(i).longitude));
                                    MarkerOptions markerOpt = new MarkerOptions().position(latLng1).title(res.data.get(i).businessAdress);
                                    googleMap.addMarker(markerOpt);
                                   // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15));
                                } }});
    }


    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            return;
        }
        else {
            Task<Location> tasks = client.getLastLocation();

            tasks.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                //Initialze lat long
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                                strlatitude = String.valueOf(location.getLatitude());
                                strlongitute = String.valueOf(location.getLongitude());


                                getallbussinessmap();
                                //Create Marker Options
                                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("My Location");

                                //Zoom Map
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                                googleMap.getUiSettings().setZoomControlsEnabled(true);
                                //Add Marker to GoogleMap
                                googleMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation)));
                            }
                        });
                    }
                }
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==44)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocation();
            }


        }
    }
}
