package com.ahmed.m.hassaan.candleapp.utils;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import com.ahmed.m.hassaan.candleapp.R;


import static com.ahmed.m.hassaan.candleapp.utils.Tools.error;


public class App extends Application {

    public static App mACTIVITY;
    public static String APP_ACTION = "com.ahmed.m.hassaan.APP_ACTION";

    public static final String NOTIFICATION_CHANEL_ID = "NakadaChannel";
    public static final String LOGOUT_ALERTS_NOTIFICATION_CHANEL_ID = "ALERT";  // for after logout 3 days alerts

    @Override
    public void onCreate() {
        super.onCreate();
        mACTIVITY = this;

//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        createNotificationChanel();


//        CaocConfig.Builder.create()
//                .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
//                .enabled(false) //default: true
//                .showErrorDetails(true) //default: true
//                .showRestartButton(true) //default: true
//                .logErrorOnRestart(true) //default: true
//                .trackActivities(true) //default: false
//                .minTimeBetweenCrashesMs(2000) //default: 3000
//                .errorDrawable(R.drawable.error_illustrator) //default: bug image
//                .errorActivity(null) //default: null (default error activity)
//                .eventListener(null)
//                .apply();


    }

    private void createNotificationChanel() {

        createNotChannel();
        createLogoutNotChannel();
    }

    private void createNotChannel() {
        createChannel(NOTIFICATION_CHANEL_ID, "الإشعارات الخاصة بالتطبيق\nلا تقم بإلغائها حتي لا تفقد الإشعارات");
    }

    private void createLogoutNotChannel() {
        createChannel(LOGOUT_ALERTS_NOTIFICATION_CHANEL_ID, "تنبيهات");
    }

    private void createChannel(String channelId, String channelDesc){
//        Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.notification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Nakada-News", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(channelDesc);
            notificationChannel.setBypassDnd(true);
            notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setLightColor(Color.WHITE);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationChannel.setShowBadge(true);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400, 500, 100});



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                notificationChannel.setAllowBubbles(true);
            }

            NotificationManager manager = getSystemService(NotificationManager.class);
//            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (manager != null) manager.createNotificationChannel(notificationChannel);
            else error("Not Created !");
        }
    }




}
