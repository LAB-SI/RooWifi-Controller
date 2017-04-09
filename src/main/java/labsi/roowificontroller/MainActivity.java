package labsi.roowificontroller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Connexion(View view) {
        final EditText ip = (EditText) findViewById(R.id.editText);
        if (ip.getText().toString().equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setMessage("Vous avez oublié de renseigner 'IP !")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
        else {
    Ion.with(getApplicationContext())
            .load("http://" + ip.getText().toString() + "/roomba.xml")
            .noCache()
            .asString()
            .setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    if (result == null) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Erreur")
                                .setMessage("Impossible de se connecter à la carte RooWifi !\nAvez vous renseigné la bonne adresse IP ?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                    else {
                        if (result.startsWith("<response>")) {
                            TinyDB tinyDB = new TinyDB(MainActivity.this);
                            tinyDB.putInt("installation", 1);
                            tinyDB.putString("ip", ip.getText().toString());
                            Intent intent;
                            intent = new Intent(MainActivity.this, SecondActivity.class);
                            startActivity(intent);
                        } else {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Erreur")
                                    .setMessage("Impossible de se connecter à la carte RooWifi !\nAvez vous renseigné la bonne adresse IP ?")
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