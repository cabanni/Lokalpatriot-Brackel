package com.example.cabanni.lokalpatriot_brackel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    private String mPhotoUrl;
    private Bundle bundle;
    private String ort = "brackel";
    private String kategorie = Finals.KATEGORIE_ZU_VERKAUFEN;
    private String gmail = new String();


    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bundle = new Bundle();
        bundle.putString("ort", this.ort);
        bundle.putString("kategorie", kategorie);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT > 21) {
            toolbar.setElevation(25);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //AlarmManager starten eines Service nach reboot, ohne app-start
        AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        Intent startServiceIntent = new Intent(MainActivity.this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, startServiceIntent, 0);
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(System.currentTimeMillis() + 1000 * 60 * 10);
        alarmManager.setRepeating(AlarmManager.RTC, calender.getTimeInMillis(),
                (1000 * 60 * 10), pendingIntent);

        //firebase
        if (checkNetwork()) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();

            // Initialize Firebase Auth
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseUser = mFirebaseAuth.getCurrentUser();

            if (mFirebaseUser == null) {
                // Not signed in, launch the Sign In activity
                startActivity(new Intent(this, Login.class));
                finish();
                return;
            } else {
                // bundle.putString("mUsername", mUsername);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    user.getProviderData();
                        // Id of the provider (ex: google.com)
                    String providerId = user.getProviderId();

                        // UID specific to the provider
                    String uid = user.getUid();

                        // Name, email address, and profile photo Url
                    mUsername = user.getDisplayName();
                    this.gmail = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();
                    //User und gmail adresse in der Datenbank eintragen


                    bundle.putString("mUsername", mUsername);
                    bundle.putString("email", this.gmail);
                        bundle.putString("photoUrl", photoUrl.toString());
                    MainActivity.BackgroundTask backgroundTask = new MainActivity.BackgroundTask();
                    backgroundTask.execute();



                    ;
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Internet ist nicht verfügbar.", Toast.LENGTH_LONG).show();
        }


        //Navigation Drawer
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,
                R.string.drawertaggle_auf, R.string.drawertaggle_zu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //StartFragment
        if (savedInstanceState != null) {
            return;
        }
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment startFragment = new StartFragment();
        startFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.root_layout, startFragment);
        fragmentTransaction.commit();


        //Drawer Listener
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.pinnwand:
                        Fragment pinnwandRootFragment = new PinnwandRootFragment();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.root_layout, pinnwandRootFragment);
                        fragmentTransaction.addToBackStack(null);


                        pinnwandRootFragment.setArguments(bundle);

                        fragmentTransaction.commit();

                        break;
                    case R.id.anzeige_aufgeben_nav:
                        Fragment anzeigeAufgebenFragment = new FragmentAnzeigeAufgeben();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.root_layout, anzeigeAufgebenFragment);
                        fragmentTransaction.addToBackStack(null);
                        anzeigeAufgebenFragment.setArguments(bundle);
                        fragmentTransaction.commit();


                }
                drawerLayout.closeDrawers();
                item.setChecked(true);
                return false;
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.settings) {
            return true;
        }


        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return true;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(new Configuration());
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    /**
     * prüft Internetverfügbarkeit
     *
     * @return
     */
    public boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }


    public class BackgroundTask extends AsyncTask<Void, Void, Void> {

        public String stringUrl = Finals.urlPinnwandAbfrage;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                // Post Variablen vorbereiten
                //  String textParam = "ort=" + URLEncoder.encode(ort, "UTF-8") + "&&kategorie" + URLEncoder.encode(kategorie, "UTF-8");
                URL url = new URL(Finals.URL_USER_INSERT);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                //   httpURLConnection.setFixedLengthStreamingMode(textParam.getBytes().length);

                // Post Variablen zum Server schicken
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                // outputStreamWriter.write(textParam);

                PrintStream ps = new PrintStream(httpURLConnection.getOutputStream());
                // send your parameters to your site
                ps.print("user=" + mUsername);
                ps.print("&gmail=" + gmail);
                outputStreamWriter.flush();
                outputStreamWriter.close();

                //Antwort zurück bekommen
                InputStream inputStream = httpURLConnection.getInputStream();

                httpURLConnection.disconnect();
                inputStream.close();
                ps.flush();
                ps.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //  getActivity().setProgressBarVisibility(false)
        }
    }


}
