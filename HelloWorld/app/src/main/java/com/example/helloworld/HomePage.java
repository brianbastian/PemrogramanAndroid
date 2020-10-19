package com.example.helloworld;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class HomePage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        final TabLayout tabLayout = findViewById(R.id.tab);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText("Notifikasi akan muncul jika wifi di nyalakan atau di matikan");
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(MyBroadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(MyBroadcastReceiver);
    }

    private final BroadcastReceiver MyBroadcastReceiver = new BroadcastReceiver() {
        private static final String TAG = "MyBroadcastReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
            int extra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

            switch (extra){
                case WifiManager.WIFI_STATE_ENABLED:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotifikasiWifi("Wifi Nyala", context);
                    }
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotifikasiWifi("Wifi Mati", context);
                    }
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void NotifikasiWifi(String notif,Context context){
        String CHANEL_ID = "WIFI_NOTIFICATION_ID";
        int NOTIFICATION_ID = 0;
        NotificationChannel mChannel = new NotificationChannel(
                CHANEL_ID,"Wifi Channel", NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(mChannel);
        Notification builder = new NotificationCompat.Builder(context,CHANEL_ID)
                .setSmallIcon(R.drawable.icon_wifi)
                .setContentTitle("Notifikasi Wifi")
                .setAutoCancel(true)
                .setContentText(notif)
                .build();
        notificationManager.notify(NOTIFICATION_ID,builder);
    }

}