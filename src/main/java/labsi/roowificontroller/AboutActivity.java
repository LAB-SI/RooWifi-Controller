package labsi.roowificontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TinyDB tinyDB = new TinyDB(this);
        tinyDB.putInt("premierefois", 0);
        TextView versiontext = (TextView) findViewById(R.id.textView25);
        versiontext.setText("RooWifi Controller est en version " + version.version+".");
    }

    public void Link(View view) {
        TextView t2 = (TextView) findViewById(R.id.textView10);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
    }
}