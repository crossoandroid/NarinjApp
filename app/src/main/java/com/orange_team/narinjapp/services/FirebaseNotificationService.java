package com.orange_team.narinjapp.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orange_team.narinjapp.R;
import com.orange_team.narinjapp.activities.MainActivity;
import com.orange_team.narinjapp.fragments.MessageFragment;
import com.orange_team.narinjapp.fragments.OrderDetailsFragment;
import com.orange_team.narinjapp.model.Body;
import com.orange_team.narinjapp.model.FirebaseOrderBody;
import com.orange_team.narinjapp.model.Notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FirebaseNotificationService extends Service {

    SharedPreferences sharedPreferences;
    public FirebaseDatabase mDatabase;
    Context context;
    Notification notification;
    String orderID;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mDatabase = FirebaseDatabase.getInstance();
        saveNotificationKey(OrderDetailsFragment.orderKey);
        setupNotificationListener();
    }

    private void saveNotificationKey(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,true);
        editor.commit();
    }

    private void setupNotificationListener() {


        mDatabase.getReference().child("Notifications")
                .orderByChild("status").equalTo(0)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot != null) {
                            //String id = dataSnapshot.child(dataSnapshot.getKey()).child("Orde").getValue(String.class);
                            for (DataSnapshot ds:dataSnapshot.getChildren()){
                                ds.getValue();
                                if(ds.getKey().equals("orderID")){
                                    orderID = (String)ds.getValue();
                                    break;
                                }
                            }


                                if (TextUtils.equals(OrderDetailsFragment.orderKey,orderID)) {
                                    Log.d("TAGTAGTAG", "" + OrderDetailsFragment.orderKey + ":::::" + dataSnapshot.child("orderID").getValue());
                                    notification = dataSnapshot.getValue(Notification.class);
                                    showNotification(context, notification, dataSnapshot.getKey());

                                }

                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(getApplicationContext(), FirebaseNotificationService.class));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification(Context context, Notification notification, String notification_key) {
        flagNotificationAsSent(notification_key);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.getDescription())
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentText(Html.fromHtml(notification.getMessage()
                ))
                .setAutoCancel(true);

        Intent backIntent = new Intent(context, MainActivity.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        final PendingIntent pendingIntent = PendingIntent.getActivities(context, 900,
                new Intent[]{backIntent}, PendingIntent.FLAG_ONE_SHOT);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);

        mBuilder.setContentIntent(pendingIntent);


        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());

    }

    private void flagNotificationAsSent(String notification_key) {
        mDatabase.getReference().child("Notifications")
                .child(notification_key)
                .child("status")
                .setValue(1);
    }
}
