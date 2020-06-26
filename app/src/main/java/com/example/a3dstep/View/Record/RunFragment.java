package com.example.a3dstep.View.Record;

        import android.Manifest;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.ServiceConnection;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.graphics.Color;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.Handler;
        import android.os.IBinder;
        import android.preference.PreferenceManager;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.a3dstep.Data.DatabaseClient;
        import com.example.a3dstep.Data.Excercise;
        import com.example.a3dstep.R;
        import com.example.a3dstep.View.Community.CommunityFragment;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.PolylineOptions;
        import com.google.maps.android.SphericalUtil;
        import com.karumi.dexter.Dexter;
        import com.karumi.dexter.MultiplePermissionsReport;
        import com.karumi.dexter.PermissionToken;
        import com.karumi.dexter.listener.PermissionRequest;
        import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

        import org.greenrobot.eventbus.EventBus;
        import org.greenrobot.eventbus.Subscribe;
        import org.greenrobot.eventbus.ThreadMode;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.nio.ByteBuffer;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;

        import androidx.appcompat.app.AlertDialog;
        import androidx.core.app.ActivityCompat;
        import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RunFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener, OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txtDistance,txtDuration,txtPace,txtAvg;

    Button btnStart,btnStop;
    MyBackgroundService mService = null;
    boolean mBound = false;
    SupportMapFragment mapFrag;
    GoogleMap mGoogleMap;
    double distance = 0;
    List<LatLng> path = new ArrayList();
    long startTime = 0;
    byte[] byteArray;
    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            double second = seconds;
            int minutes = seconds / 60;
            int hour = minutes /60;
            seconds = seconds % 60;

            if(distance != 0){
                double PaceSeconds = (1000/distance) * second;
                int minute = (int) (PaceSeconds / 60);
                PaceSeconds =PaceSeconds % 60;
                int Pace = (int) PaceSeconds;
                txtPace.setText(minute+"'"+Pace);
            }
            double avg = (distance/second) *3600 ;
            int avgHour = (int) (avg /3600);
            int avgMin = (int) (avg % 60);
            txtAvg.setText(avgHour+"."+avgMin);
            txtDuration.setText(String.format("%02d:%02d:%02d",hour, minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };


    public RunFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RunFragment newInstance(String param1, String param2) {
        RunFragment fragment = new RunFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MyBackgroundService.LocalBinder binder = (MyBackgroundService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_run, container, false);
        txtDistance = view.findViewById(R.id.txtDistance);
        txtDuration= view.findViewById(R.id.txtDuration);
        txtPace= view.findViewById(R.id.txtPace);
        txtAvg = view.findViewById(R.id.txtAvg);
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        Dexter.withActivity(getActivity()).withPermissions(Arrays.asList(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        )).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                btnStart = view.findViewById(R.id.btnStart);
                btnStop = view.findViewById(R.id.btnStop);
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mService.requestLocationUpdates();
                        startTime = System.currentTimeMillis();
                        timerHandler.postDelayed(timerRunnable, 0);

                    }
                });

                btnStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mService.removeLocationUpdate();
                        timerHandler.removeCallbacks(timerRunnable);
                        distance = 0;
                        path = new ArrayList();
                        excercise = new Excercise();
                        snapShot();

                        showAlertDialog();
                    }
                });

                setButtonState((Common.requestingLocationUpdate(getActivity())));

                getActivity().bindService(new Intent(getActivity(),
                                MyBackgroundService.class), mServiceConnection,
                        Context.BIND_AUTO_CREATE);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences((getActivity())).registerOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            getActivity().unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences((getActivity())).unregisterOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().unregister(this);

        // stop timer
     //   timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(Common.KEY_REQUESTING_LOCATION_UPDATE)) {
            setButtonState(sharedPreferences.getBoolean(Common.KEY_REQUESTING_LOCATION_UPDATE, false));
        }
    }

    private void setButtonState(boolean aBoolean) {
        if (aBoolean) {
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
        } else {
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onListionLocation(SendLocationToActivity event) {
        if (event != null) {
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("//")
                    .append(event.getLocation().getLongitude())
                    .toString();
           // Toast.makeText(mService, data, Toast.LENGTH_SHORT).show();
            path.add(new LatLng(event.getLocation().getLatitude(), event.getLocation().getLongitude()));
            if (path.size() > 0) {
                PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
                mGoogleMap.addPolyline(opts);
            }
            LatLng location = new LatLng(event.getLocation().getLatitude(), event.getLocation().getLongitude());
            //   mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
             distance = SphericalUtil.computeLength(path) ;
            Log.e("DIEP", String.valueOf(distance) + " m");
            int distanceKm = (int) (distance /1000);
            int distanceM = (int) (distance %1000) /10;
            txtDistance.setText(distanceKm +"."+distanceM);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Do you want to finish exercise ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             //   snapShot();
                saveExcercise();
                dialogInterface.dismiss();
            }


        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    Excercise excercise = new Excercise();
    private void saveExcercise() {
        final String distance = txtDistance.getText().toString().trim();
        final String pace = txtPace.getText().toString().trim();
        final String time = txtDuration.getText().toString().trim();


        class SaveTask extends AsyncTask<Void, Void, Void> {
            Date currentTime;
            String Time;
            int timecurr;
            String Noon;
            @Override
            protected void onPreExecute() {
                 currentTime = Calendar.getInstance().getTime();
                 Log.e("TUAN",currentTime +"");
                 Time = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
                timecurr = Integer.parseInt(Time);
                Log.e("TUAN",Time +"");
                if(timecurr >=4 && timecurr < 11){
                    Noon = "Morning Run";
                }else if(timecurr >= 11 && timecurr <20){
                    Noon = "Afternoon Run";
                }else if(timecurr >= 20 && timecurr <4){
                    Noon = "Night Run";
                }
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                //creating a task

                excercise.setDistance(distance);
                excercise.setPace(pace);
                excercise.setTime(time);
                excercise.setNoon(Noon);
                excercise.setDate_time(currentTime +"");
             //   excercise.setImage(byteArray);
                //adding to database
                DatabaseClient.getInstance(getContext()).getAppDatabase()
                        .excerciseDao()
                        .insert(excercise);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
               // finish();
               // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    public void snapShot(){
        GoogleMap.SnapshotReadyCallback callback=new GoogleMap.SnapshotReadyCallback () {
            private File file;
            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                Bitmap bitmap=snapshot;
                try{

                    file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"map.png");
                    FileOutputStream fout=new FileOutputStream (file);
                    bitmap.compress (Bitmap.CompressFormat.PNG,90,fout);
                    Date currentTime = Calendar.getInstance().getTime();
                    String map  = currentTime+"";
                    File sdCardDirectory = Environment.getExternalStorageDirectory();
                    File image = new File(sdCardDirectory, map+".png");
                    boolean success = false;

                    // Encode the file as a PNG image.
                    FileOutputStream outStream;
                    try {
                        outStream = new FileOutputStream(image);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                        /* 100 to keep full quality of the image */

                        outStream.flush();
                        outStream.close();
                        success = true;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (success) {
                        excercise.setImage(image+"");
                        Toast.makeText(getActivity(), "Image saved with success",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(),
                                "Error during image saving", Toast.LENGTH_LONG).show();
                    }
                //    excercise.setImage(byteArray);
                    Toast.makeText (getActivity(), "Capture", Toast.LENGTH_SHORT).show ();
                }catch (Exception e){
                    e.printStackTrace ();
                    Toast.makeText (getActivity(), "Not Capture", Toast.LENGTH_SHORT).show ();
                }


            }
        };
        mGoogleMap.snapshot (callback);


    }
}