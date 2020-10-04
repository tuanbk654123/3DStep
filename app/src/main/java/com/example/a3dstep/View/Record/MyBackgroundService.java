package com.example.a3dstep.View.Record;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.a3dstep.Data.DataRealTime;
import com.example.a3dstep.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class MyBackgroundService extends Service {
    private static final String TAG = "MyBackgroundService";
    private static final String CHANNEL_ID = "my_channel";
    private static final long UPDATE_INTERVAL_IN_MIL = 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MUL = UPDATE_INTERVAL_IN_MIL / 2;

    private static final int NOTI_ID = 1223;
    private static final String EXTRA_STARTED_FROM_NOTIFICATION = "com.example.updatelocationinbcakground" + ".start_from_notification";
    private boolean mChageConfig = false;
    private NotificationManager mNotificationManager;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private Handler mServiceHandler;
    private Location mLocation;

    public MyBackgroundService() {

    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
	 //   DataRealTime.PATH = new ArrayList<>();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());
            }
        };
        creatLocationRequest();
        getLastLocation();
        HandlerThread handlerThread = new HandlerThread("TUAN");
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChanel = new NotificationChannel(CHANNEL_ID,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(mChanel);
        }
	 /*   if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
		    startMyOwnForeground();
	    else
		    startForeground(1, new Notification());*/
    }
	@RequiresApi(Build.VERSION_CODES.O)
	private void startMyOwnForeground()
	{
		String NOTIFICATION_CHANNEL_ID = "example.permanence";
		String channelName = "Background Service";
		NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
		chan.setLightColor(Color.BLUE);
		chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		assert manager != null;
		manager.createNotificationChannel(chan);

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
		Notification notification = notificationBuilder.setOngoing(true)
				.setContentTitle("App is running in background")
				.setPriority(NotificationManager.IMPORTANCE_MIN)
				.setCategory(Notification.CATEGORY_SERVICE)
				.build();
		Log.e(TAG, "startMyOwnForeground");
		startForeground(NOTI_ID, getNotifilecation());
	}
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
	    boolean startFromNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false);
        if (startFromNotification) {
            removeLocationUpdate();
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChageConfig = true;
    }

    public void removeLocationUpdate() {
        try {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            Common.setRequastingLocationUpdate(this, false);
            stopSelf();
        } catch (SecurityException e) {
            Common.setRequastingLocationUpdate(this, false);
            Log.e(TAG, "Lost location" + e);
        }
    }

    private void getLastLocation() {
        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null)
                                mLocation = task.getResult();
                            else
                                Log.e("TUAN", "FAILED to get location");
                        }
                    });
        } catch (SecurityException ex) {
            Log.e("TUAN", "LOST to get location" + ex);
        }
    }

    private void creatLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MIL);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MUL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void onNewLocation(Location lastLocation) {
        mLocation = lastLocation;
	    DataRealTime.PATH.add(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
	    Log.e(TAG,lastLocation.getLatitude()+" \n"+ lastLocation.getLongitude());
	    Log.e(TAG,DataRealTime.PATH.size() + "");
        EventBus.getDefault().postSticky(new SendLocationToActivity(mLocation));

       // sendNotification(String.valueOf(lastLocation.getLatitude()));
       /*   if (serviceIsRuningInForeGround(this)) {
            mNotificationManager.notify(NOTI_ID, getNotifilecation());
       }*/
	    mNotificationManager.notify(NOTI_ID, getNotifilecation());
    }
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, RunFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "3DStep";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.icon)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                        .setContentTitle(channelId)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH)
                        .addAction(new NotificationCompat.Action(
                                android.R.drawable.sym_call_missed,
                                "Cancel",
                                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)))
                        .addAction(new NotificationCompat.Action(
                                android.R.drawable.sym_call_outgoing,
                                "OK",
                                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }

    private Notification getNotifilecation() {
        Intent intent = new Intent(this, MyBackgroundService.class);
        String text = Common.getLocationText(mLocation);
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);
        PendingIntent servicePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent activityPendingIntent = PendingIntent.getService(this, 0, new Intent(this, RunFragment.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .addAction(R.drawable.ic_launcher_background,"launch", activityPendingIntent)
                .addAction(R.drawable.ic_launcher_foreground, "Remove", servicePendingIntent)
                .setContentText(text)
                .setContentTitle(Common.getLocationTitle(this))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(text)
                .setWhen(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }
        return builder.build();
    }

    private boolean serviceIsRuningInForeGround(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
            if (getClass().getName().equals(service.service.getClassName()))
                if (service.foreground)
                    return true;
        return false;
    }

    private final IBinder mIBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        stopForeground(true);
        mChageConfig = false;
        return mIBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG,"onRebind");
        stopForeground(true);
        mChageConfig = false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind");
       // if (mChageConfig && Common.requestingLocationUpdate(this))
	    startForeground(NOTI_ID, getNotifilecation());
        //
	   /* Intent broadcastIntent = new Intent();
	    broadcastIntent.setAction("restartservice");
	    broadcastIntent.setClass(this, Restarter.class);
	    this.sendBroadcast(broadcastIntent);*/
	    //
        return super.onUnbind(intent);
    }

    public void requestLocationUpdates() {
        Common.setRequastingLocationUpdate(this, true);
        //startForegroundService(new Intent(getApplicationContext(), MyBackgroundService.class));
	    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		    Log.e(TAG,"startForegroundService");
		    startForegroundService(new Intent(getApplicationContext(), MyBackgroundService.class));
	    } else {
		    Log.e(TAG,"Start Service");
		    startService(new Intent(getApplicationContext(), MyBackgroundService.class));
	    }*/
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
	        if(fusedLocationProviderClient != null){
		        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
	        }
          //  fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }catch (SecurityException e){
            Log.e("TUAN","LOST location permisstion"+e);
        }
    }

    public class LocalBinder extends Binder {
        MyBackgroundService getService(){
            return MyBackgroundService.this;
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy");
        mServiceHandler.removeCallbacks(null);
        super.onDestroy();
	   // startForeground(NOTI_ID, getNotifilecation());
	   /* Intent broadcastIntent = new Intent();
	    broadcastIntent.setAction("restartservice");
	    broadcastIntent.setClass(this, Restarter.class);
	    this.sendBroadcast(broadcastIntent);*/
    }
}
