package labsi.roowificontroller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(!ConnexionInternet.isConnectedInternet(SplashActivity.this))
        {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.error)
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.activity_second_nonetwork))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .show();
        }
        else {
            init();
        }
    }

    private void init() {
        TinyDB tinyDB = new TinyDB(SplashActivity.this);
        tinyDB.putInt("actualisation", 0);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    TinyDB tinyDB = new TinyDB(SplashActivity.this);
                    int installed = tinyDB.getInt("installation");

                    if (installed == 1) {
                        Intent intent = new Intent(SplashActivity.this,SecondActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();
    }

    protected void onPause() {
        super.onPause();
        finish();
    }
}
