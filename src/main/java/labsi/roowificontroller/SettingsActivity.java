package labsi.roowificontroller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class SettingsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        TinyDB tinyDB = new TinyDB(this);
        int tick = tinyDB.getInt("eachtick");
        sb.setProgress(tick);
        TextView tv = (TextView) findViewById(R.id.textView34) ;
        tv.setText("Les données seront télchargées toutes les " + tick + " secondes.");
        sb.setOnSeekBarChangeListener(SettingsActivity.this);

        TinyDB tinyDB1 = new TinyDB(SettingsActivity.this);
        int checked = tinyDB1.getInt("checked");

        CheckBox checkbox =(CheckBox)findViewById(R.id.checkBox);

        if (checked == 1) {
            checkbox.setChecked(true);
        }
        else if (checked == 0){
            checkbox.setChecked(false);
            SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
            seekBar.setVisibility(View.GONE);
            TextView textView = (TextView)findViewById(R.id.textView34);
            textView.setVisibility(View.GONE);
        }
    }

    public void modifip(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String ip = editText.getText().toString();
        Ion.with(getApplicationContext())
                .load("http://" + ip + "/roomba.xml")
                .noCache()
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (result == null) {
                            new AlertDialog.Builder(SettingsActivity.this)
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
                                EditText editText = (EditText) findViewById(R.id.editText);
                                String text = editText.getText().toString();
                                TinyDB tinyDB = new TinyDB(SettingsActivity.this);
                                tinyDB.putString("ip", text);
                                Toast.makeText(SettingsActivity.this, "Connexion à " + text + " en cours...", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SettingsActivity.this,SecondActivity.class);
                                startActivity(intent);
                            } else {
                                new AlertDialog.Builder(SettingsActivity.this)
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
    public void reinitialiser(View view) {
        TinyDB tinyDB = new TinyDB(this);
        tinyDB.clear();
        Toast.makeText(this, "Paramètres réinitialisées !", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SettingsActivity.this,SplashActivity.class);
        startActivity(intent);

    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        TextView tv = (TextView) findViewById(R.id.textView34) ;
        tv.setText("Les données seront téléchargées toutes les " + String.valueOf(progress) + " secondes.");
        TinyDB tinyDB = new TinyDB(this);
        if (progress <2) {
            progress = progress + 2;
        }
        tinyDB.putInt("eachtick", progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        TinyDB tinyDB = new TinyDB(this);
        int eachtick = tinyDB.getInt("eachtick");
        Toast.makeText(this, "Les données seront téléchargées toutes les " + eachtick + " secondes !", Toast.LENGTH_LONG).show();
    }

    public void checkbox_clicked(View v) {
        CheckBox checkbox =(CheckBox)findViewById(R.id.checkBox);
        if(checkbox.isChecked()){
            SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
            seekBar.setVisibility(View.VISIBLE);
            TextView textView = (TextView)findViewById(R.id.textView34);
            textView.setVisibility(View.VISIBLE);
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.putInt("checked", 1);
        }
        else {
            SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
            seekBar.setVisibility(View.GONE);
            TextView textView = (TextView)findViewById(R.id.textView34);
            textView.setVisibility(View.GONE);
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.putInt("checked", 0);
        }
    }
}