package labsi.roowificontroller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
        tv.setText(getResources().getString(R.string.activity_settings_intervaldownloadinfo4)+ " " + tick + " " +getResources().getString(R.string.activity_settings_intervaldownloadinfo5));
        sb.setOnSeekBarChangeListener(SettingsActivity.this);

        TinyDB tinyDB1 = new TinyDB(SettingsActivity.this);
        int downloadeachtick = tinyDB1.getInt("downloadeachtick");

        CheckBox checkbox =(CheckBox)findViewById(R.id.checkBox);

        if (downloadeachtick == 1) {
            checkbox.setChecked(true);
        }
        else if (downloadeachtick == 0){
            checkbox.setChecked(false);
            SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
            seekBar.setVisibility(View.GONE);
            TextView textView = (TextView)findViewById(R.id.textView34);
            textView.setVisibility(View.GONE);
        }

        int modeavance = tinyDB.getInt("modeavance");
        CheckBox checkbox1 =(CheckBox)findViewById(R.id.checkBox2);
        if (modeavance == 1) {
            checkbox1.setChecked(true);
        }
        else if (modeavance == 0){
            checkbox1.setChecked(false);
        }
    }

    public void modifip(View view) {
        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(SettingsActivity.this);
        pDialog.setTitle(R.string.pdialog_tryconnect);
        pDialog.setCancelable(false);
        pDialog.setMessage(getResources().getString(R.string.pdialog_wait));
        pDialog.setIndeterminate(false);
        pDialog.show();

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
                            pDialog.dismiss();
                            new AlertDialog.Builder(SettingsActivity.this)
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
                                EditText editText = (EditText) findViewById(R.id.editText);
                                String text = editText.getText().toString();
                                TinyDB tinyDB = new TinyDB(SettingsActivity.this);
                                tinyDB.putString("ip", text);
                                Toast.makeText(SettingsActivity.this, getResources().getString(R.string.connected1) + " "+ text + " " + getResources().getString(R.string.connected2), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SettingsActivity.this,SecondActivity.class);
                                startActivity(intent);
                            } else {
                                pDialog.dismiss();
                                new AlertDialog.Builder(SettingsActivity.this)
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

    public void reinitialiser(View view) {

        TinyDB tinyDB = new TinyDB(this);
        tinyDB.clear();
        Toast.makeText(this, getResources().getString(R.string.activity_settings_settinsreseted), Toast.LENGTH_LONG).show();
        System.exit(0);

    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        TextView tv = (TextView) findViewById(R.id.textView34) ;
        tv.setText(getResources().getString(R.string.activity_settings_intervaldownloadinfo4) +" " + String.valueOf(progress) +" " + getResources().getString(R.string.activity_settings_intervaldownloadinfo5));
        TinyDB tinyDB = new TinyDB(this);
        if (progress <1) {
            progress = progress + 1;
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
        Toast.makeText(this, getResources().getString(R.string.activity_settings_intervaldownloadinfo4) +" " + eachtick +" " + getResources().getString(R.string.activity_settings_intervaldownloadinfo5), Toast.LENGTH_LONG).show();
    }

    public void checkbox_clicked(View v) {
        CheckBox checkbox =(CheckBox)findViewById(R.id.checkBox);
        if(checkbox.isChecked()){
            SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
            seekBar.setVisibility(View.VISIBLE);
            TextView textView = (TextView)findViewById(R.id.textView34);
            textView.setVisibility(View.VISIBLE);
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.putInt("downloadeachtick", 1);
        }
        else {
            SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
            seekBar.setVisibility(View.GONE);
            TextView textView = (TextView)findViewById(R.id.textView34);
            textView.setVisibility(View.GONE);
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.putInt("downloadeachtick", 0);
        }
    }

    public void checkbox1_clicked(View v) {
        CheckBox checkbox =(CheckBox)findViewById(R.id.checkBox2);
        if(checkbox.isChecked()){
            Toast.makeText(SettingsActivity.this, getResources().getString(R.string.activity_settings_advancedmodeenabled), Toast.LENGTH_LONG).show();
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.putInt("modeavance", 1);

        }
        else {
            Toast.makeText(SettingsActivity.this, getResources().getString(R.string.activity_settings_advancedmodedisabled), Toast.LENGTH_LONG).show();
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.putInt("modeavance", 0);
        }
    }
}