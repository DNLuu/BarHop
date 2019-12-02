package com.example.BarHop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements LocationListener {
    public String host = "";
    public final int port = 0;
    public static BufferedReader in;
    public static PrintWriter out = null;

    public final Context context = this;
    String alertMessage = "";

    public static Socket socket;
    private static LocationManager locationManager;

    public static Double longitude;
    public static Double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2);
//            return;
        }

        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);

        communicate();

        Fragment fr;
        fr = new login();  // render login page by default
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_main, fr);
        fragmentTransaction.commit();

    }

    // Demo thread/socket code
    public static void sendMessageToServer(final String str, final Socket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintWriter out;

                if (socket == null) {
                    // error message
                } else {
                    try {
                        out = new PrintWriter(
                                new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
                        if (!str.isEmpty()) {
                            out.println(str);
                            out.flush();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }

    // Start socket listener thread
    public void communicate() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    socket = new Socket(host, port);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

                    while (true) {
                        String msg = null;

                        try {
                            msg = in.readLine();
                            Log.d("", msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (msg == null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        } else if (msg.equals("0")) {
                            alertMessage = "0";

                            // Successful login
                            // Open bar browsing page
                            androidx.fragment.app.Fragment fr = new browse_bars();

                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();


                            fragmentTransaction.replace(R.id.fragment_main, fr);
                            fragmentTransaction.commit();
                        } else if (msg.equals("1")) {
                            alertMessage = "Wrong ID or Password";
                            mHandler.sendEmptyMessage(0);
                        } else {
                            Log.d("Messages", msg);
                            mHandler.sendEmptyMessage(0);
                            // do some more
                        }
                    } // end while
                } catch (UnknownHostException e) {
                    Log.d("", "unknown host*");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d("", "io exception*");
                    e.printStackTrace();
                }
            }  // end run
        }).start();
    }

    // GPS lifecycle methods
    @Override
    public void onLocationChanged(Location location) {
        this.longitude = location.getLongitude();
        this.latitude= location.getLatitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg){
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Alert")
                    .setMessage(alertMessage)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {

                        }
                    }).show();
        }
    };
}

