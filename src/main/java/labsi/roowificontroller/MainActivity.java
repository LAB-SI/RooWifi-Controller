package labsi.roowificontroller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText)findViewById(R.id.editText);

        TinyDB tinyDB = new TinyDB(MainActivity.this);
        String IP = tinyDB.getString("ip");
        if (IP == null){
        }
        else {
            editText.setText(IP);
        }
    }

    public void Connexion(View view) {
        final EditText ip = (EditText) findViewById(R.id.editText);
        if (ip.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.error)
                    .setMessage(R.string.activity_main_forgotip)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
        else {
            final ProgressDialog pDialog;
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setTitle(R.string.pdialog_tryconnect);
            pDialog.setCancelable(false);
            pDialog.setMessage(getResources().getString(R.string.pdialog_wait));
            pDialog.setIndeterminate(false);
            pDialog.show();

    Ion.with(getApplicationContext())
            .load("http://" + ip.getText().toString() + "/roomba.xml")
            .noCache()
            .asString()
            .setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    if (result == null) {
                        pDialog.dismiss();
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(R.string.error)
                                .setMessage(getResources().getString(R.string.cantconnect))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                    else {
                        if (result.startsWith("<response>")) {
                            pDialog.dismiss();
                            TinyDB tinyDB = new TinyDB(MainActivity.this);
                            tinyDB.putInt("installation", 1);
                            tinyDB.putInt("premierefois", 1);
                            tinyDB.putInt("actualisation", 0);
                            tinyDB.putString("ip", ip.getText().toString());
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.connected1) + " "+ ip.getText().toString() + " " + getResources().getString(R.string.connected2), Toast.LENGTH_LONG).show();
                            Intent intent;
                            intent = new Intent(MainActivity.this, SecondActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            pDialog.dismiss();
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(R.string.error)
                                    .setMessage(getResources().getString(R.string.cantconnect))
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
                        }
                    }
                }
            });
        }
    }
}