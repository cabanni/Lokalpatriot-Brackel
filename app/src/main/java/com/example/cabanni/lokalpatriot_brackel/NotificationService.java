package com.example.cabanni.lokalpatriot_brackel;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by cabanni on 03.01.17.
 */

public class NotificationService extends Service {

    public static int durchlauf = 0;
    SimpleDateFormat parseFormat = new SimpleDateFormat("dd.MM.yyyy");
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */


    private Calendar calendar = Calendar.getInstance();
    private long mId = calendar.getTimeInMillis() + 8 * 1000 * 60 * 60;
    private long aktuell = calendar.getTimeInMillis();
    private boolean running = false;
    //private SharedPreferences sharedPreferences=getSharedPreferences("warn",0);
    private Integer vorWarnZeitInStunden = 8;    //sharedPreferences.getInt("stunden",8);
    private String[] hausmuell;
    private String[] gelbeSaecke;
    private String[] gruenAbfall;
    private String[] altPapier;
    private ArrayList<String[]> alleTermine = new ArrayList<String[]>();
    private ArrayList<Event> events;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.m("Sevice ist gestartet");
        L.l(getApplicationContext(), "Service ist gestartet");

        ArrayList<Event> naechsteEvents;
        //Intent for oskar_widget
        //   Intent intentForOskarWidget = new Intent(this, oskar_widget.class);
        //prepare for Extras
        //    intentForOskarWidget.setAction(AppWidgetManager.EXTRA_CUSTOM_EXTRAS);
        // look for a termin that is not null in Terminlist
        events = checkForTermin();
        String type = "";
        String date = null;


        if (events.size() > 0) {

            for (Event event : events) {
                type = type + " " + event.getEventName();
            }
            date = events.get(0).getTimestamp();
        }


        // creates a notification
        if (durchlauf < 1 && events.size() > 0) {
            android.support.v4.app.NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.muellauf)
                            .setContentTitle(type)
                            .setContentText(date);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify((int) mId, mBuilder.build());
            //put Extras in there Param Key, Param type of Termin Event


            durchlauf++;

        }
        if (durchlauf > 2) {  //24
            durchlauf = 0;
        }


        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Resources res = getResources();
        hausmuell = res.getStringArray(R.array.hausmuell);
        gelbeSaecke = res.getStringArray(R.array.gelber_sack);
        gruenAbfall = res.getStringArray(R.array.gruenabfall);
        altPapier = res.getStringArray(R.array.altpapier);
        this.alleTermine.add(hausmuell);
        this.alleTermine.add(gelbeSaecke);
        this.alleTermine.add(gruenAbfall);
        this.alleTermine.add(altPapier);
        super.onCreate();

    }


    public ArrayList<Event> checkForTermin() {
        events = new ArrayList<>();


        long zeitPlus8Stunden = calendar.getTimeInMillis() + vorWarnZeitInStunden * 1000 * 60 * 60;
        String zeitPlusVorwarnZeitFormatiert = parseFormat.format(zeitPlus8Stunden);

        events = checkForTermineDeaper(hausmuell, parseFormat, zeitPlusVorwarnZeitFormatiert, "Hausmüll", events);
        events = checkForTermineDeaper(gelbeSaecke, parseFormat, zeitPlusVorwarnZeitFormatiert, "Gelber Sack", events);
        events = checkForTermineDeaper(gruenAbfall, parseFormat, zeitPlusVorwarnZeitFormatiert, "Grünabfall", events);
        events = checkForTermineDeaper(altPapier, parseFormat, zeitPlusVorwarnZeitFormatiert, "Altpapier", events);
        return events;
    }

    private ArrayList<Event> checkForTermineDeaper(String[] events, SimpleDateFormat parseFormat,
                                                   String zeitPlusVorwarnZeitFormatiert, String eventName, ArrayList<Event> eventsOriginal) {
        int eventsLenght = events.length;
        for (int i = 0; i < eventsLenght; i++) {
            Long castetTimespamp = Long.parseLong(events[i]);
            String tempDate = parseFormat.format(castetTimespamp);
            if (tempDate.equals(zeitPlusVorwarnZeitFormatiert)) {
                Event event = new Event();
                event.setEventName(eventName);
                event.setTimestamp(tempDate);
                eventsOriginal.add(event);
            }
        }
        return eventsOriginal;


    }


}
