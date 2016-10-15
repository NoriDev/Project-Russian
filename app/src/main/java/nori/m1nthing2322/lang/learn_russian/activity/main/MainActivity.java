package nori.m1nthing2322.lang.learn_russian.activity.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import nori.m1nthing2322.lang.learn_russian.R;
import nori.m1nthing2322.lang.learn_russian.activity.learn.Alphabet;
import nori.m1nthing2322.lang.learn_russian.activity.learn.Keyboard;
import nori.m1nthing2322.lang.learn_russian.tool.Preference;

public class MainActivity extends AppCompatActivity {

    private int ver= 10002;
    String xml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setSubtitle(R.string.beta);}
        {Button Button_Alphabet = (Button) findViewById(R.id.bt_alphabet);
            Button_Alphabet.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this,Alphabet.class);
                    startActivity(i);}
            });}
        {Button Button_Keyboard = (Button) findViewById(R.id.bt_keyboard);
            Button_Keyboard.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this,Keyboard.class);
                    startActivity(i);}});}
        showUpdateNotification();
    }private void showUpdateNotification() {
        try {
            Preference mPref = new Preference(getApplicationContext());
            PackageManager packageManager = getPackageManager();
            PackageInfo info = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
            int versionCode = info.versionCode;
            if (mPref.getInt("versionCode", 0) != versionCode) {
                mPref.putInt("versionCode", versionCode);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle(R.string.changeLog_title_beta);
                builder.setMessage(R.string.changeLog_msg_beta);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.show();
            }
        }catch (PackageManager.NameNotFoundException e) {e.printStackTrace();}
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        StringBuilder sBuffer = new StringBuilder();
        try{//Start Try
            String urlAddr = "http://devhost.iptime.org:1479/html/learn_russian.xml";
            URL url = new URL(urlAddr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn != null){//Start if
                conn.setConnectTimeout(20000);
                //conn.setUseCaches(false);
                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){//Start if
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    while(true){//Start While
                        String line = br.readLine();
                        if(line==null){//Start if
                            break;
                        }//end if
                        sBuffer.append(line);
                    }//end while
                    br.close();
                    conn.disconnect();
                }//end if
            }//end if
            xml = sBuffer.toString();
            CountDownTimer _timer = new CountDownTimer(1000, 1000){
                public void onTick(long millisUntilFinished) {}
                public void onFinish(){
                    if(Integer.parseInt(xml)==ver){//new version
                        Toast.makeText(getApplicationContext(), R.string.latest_version, Toast.LENGTH_SHORT).show();
                    }else if(Integer.parseInt(xml)>ver){//현재버전보다 서버버전이 높을때
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(R.string.low_version);
                        builder.setMessage(R.string.plz_update);
                        builder.setCancelable(false);
                        builder.setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }});builder.setPositiveButton(R.string.update_now, new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                                                ("https://play.google.com/store/apps/details?id=nori.m1nthing2322.lang.learn_russian"));
                                        startActivity(myIntent);
                                    }});
                        builder.show();
                    }else {//현재버전보다 서버 버전이 낮을때
                        Toast.makeText(getApplicationContext(), R.string.crack_contents, Toast.LENGTH_LONG).show();
                    }
                }
            };
            _timer.start();
        }//end try
        catch (Exception e) {//네트워크가 올바르지 않을때
            Toast.makeText(getApplicationContext(), R.string.offline, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dev) {
            AlertDialog.Builder dev = new AlertDialog.Builder(MainActivity.this);
            dev.setTitle(R.string.developer_title);
            dev.setMessage(R.string.developer_msg);
            dev.setPositiveButton(R.string.ok, null);
            dev.setCancelable(false);
            dev.show();
            return true;
        }
        if (id == R.id.action_feedback) {
            Uri uri = Uri.parse("mailto:noridevdroid@gmail.com");
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(it);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}