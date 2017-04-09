package labsi.roowificontroller;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SecondActivity extends AppCompatActivity {
    //public static final String EXTRA_IP = "var_ip";
    NodeList nodelist;
    NodeList nodelist1;
    NodeList nodelist2;
    NodeList nodelist3;
    NodeList nodelist4;
    //ProgressDialog pDialog;
    RelativeLayout layout_joystick;
    //ImageView image_joystick, image_border;
    //TextView textView1, textView2, textView3, textView4, textView5;
    TextView textView1, textView2, textView5;
    JoyStickClass js;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //Intent intent = getIntent();
        //String IP = intent.getStringExtra(MainActivity.EXTRA_IP);
        TinyDB tinyDB = new TinyDB(this);
        final String IP = tinyDB.getString("ip");
        TextView textView_IP = (TextView) findViewById(R.id.textView_IP);
        textView_IP.setText("IP : " + IP);
        if(!ConnexionInternet.isConnectedInternet(SecondActivity.this))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setMessage("Vous n'êtes pas connecté à Internet !")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent nintent;
                            nintent = new Intent(SecondActivity.this, MainActivity.class);
                            startActivity(nintent);
                        }
                    })
                    .show();
        }
        else {
            TinyDB tinyDB1 = new TinyDB(SecondActivity.this);
            int checked = tinyDB1.getInt("checked");
            if (checked == 1) {
                TinyDB tinyDB2 = new TinyDB(this);
                int ticknomber = tinyDB2.getInt("eachtick");
                ticknomber = ticknomber*1000;
                Timer timerAsync = new Timer();
                TimerTask timerTaskAsync = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override public void run() {
                                new DownloadXML().execute("http://" + IP + "/roomba.xml");
                            }
                        });
                    }
                };
                timerAsync.schedule(timerTaskAsync, 0, ticknomber);
            }
            else {
                new DownloadXML().execute("http://" + IP + "/roomba.xml");
            }
        }

        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
   //     textView3 = (TextView)findViewById(R.id.textView3);
      //  textView4 = (TextView)findViewById(R.id.textView4);
        textView5 = (TextView)findViewById(R.id.textView5);
        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);
        js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(900, 900);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);
        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    textView1.setText("X : " + String.valueOf(js.getX()));
                    textView2.setText("Y : " + String.valueOf(js.getY()));
             //       textView3.setText("Angle : " + String.valueOf(js.getAngle()));
          //          textView4.setText("Distance : " + String.valueOf(js.getDistance()));
                    TinyDB tinyDB = new TinyDB(SecondActivity.this);
                    String IP = tinyDB.getString("ip");
                    WebView myWebView = (WebView) findViewById(R.id.webview);
                    int direction = js.get8Direction();
                    if(direction == JoyStickClass.STICK_UP) {
                        textView5.setText("Direction : Haut");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=a");
                    } else if(direction == JoyStickClass.STICK_UPRIGHT) {
                        textView5.setText("Direction : Haut Droite");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=a");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=f");
                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                        textView5.setText("Direction : Droite");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=f");
                    } else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
                        textView5.setText("Direction : Bas Droite");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=l");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=f");
                    } else if(direction == JoyStickClass.STICK_DOWN) {
                        textView5.setText("Direction : Bas");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=l");
                    } else if(direction == JoyStickClass.STICK_DOWNLEFT) {
                        textView5.setText("Direction : Bas Gauche");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=l");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=c");
                    } else if(direction == JoyStickClass.STICK_LEFT) {
                        textView5.setText("Direction : Gauche");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=c");
                    } else if(direction == JoyStickClass.STICK_UPLEFT) {
                        textView5.setText("Direction : Haut Gauche");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=a");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=c");
                    } else if(direction == JoyStickClass.STICK_NONE) {
                        textView5.setText("Direction : Centre");
                        myWebView.loadUrl(null);
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    textView1.setText("X : 0");
                    textView2.setText("Y : 0");
     //               textView3.setText("Angle :");
     //               textView4.setText("Distance :");
                    textView5.setText("Direction : Centre");
                }
                return true;
            }
        });
    }

    public void Reset(View view) {
        TinyDB tinyDB = new TinyDB(SecondActivity.this);
        String IP = tinyDB.getString("ip");
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://" + IP + "/c.html");
    }

    public void Clean(View view) {
        TinyDB tinyDB = new TinyDB(SecondActivity.this);
        final String IP = tinyDB.getString("ip");
        final WebView myWebView = (WebView) findViewById(R.id.webview);
        Ion.with(getApplicationContext())
                .load("http://" + IP + "/roomba.cgi?button=CLEAN")
                .noCache()
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            if (result.equals("0")) {
                                new AlertDialog.Builder(SecondActivity.this)
                                        .setTitle("Erreur")
                                        .setMessage("Une erreur s'est produite mais la commande a bien été envoyée !" + "\n" + "Verifiez que votre aspirateur Roomba est bien allumé et la carte RooWifi bien branchée.")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                            else if (result.equals("1")) {
                                myWebView.loadUrl("http://" + IP + "/roomba.cgi?button=CLEAN");
                            }
                            else {
                                new AlertDialog.Builder(SecondActivity.this)
                                        .setTitle("Erreur")
                                        .setMessage("Erreur inconnue." + "\n" + "Resultat : '" + result +"'")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        }
                        catch (Exception e1){
                            new AlertDialog.Builder(SecondActivity.this)
                                    .setTitle("Erreur")
                                    .setMessage("Impossible de se connecter à la carte RooWifi.")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }

    public void Dock(View view) {
        TinyDB tinyDB = new TinyDB(SecondActivity.this);
        final String IP = tinyDB.getString("ip");
        final WebView myWebView = (WebView) findViewById(R.id.webview);
        Ion.with(getApplicationContext())
                .load("http://" + IP + "/roomba.cgi?button=DOCK")
                .noCache()
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            if (result.equals("0")) {
                                new AlertDialog.Builder(SecondActivity.this)
                                        .setTitle("Erreur")
                                        .setMessage("Une erreur s'est produite mais la commande a bien été envoyée !" + "\n" + "Verifiez que votre aspirateur Roomba est bien allumé et la carte RooWifi bien branchée.")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                            else if (result.equals("1")) {
                                myWebView.loadUrl("http://" + IP + "/roomba.cgi?button=DOCK");
                            }
                            else {
                                new AlertDialog.Builder(SecondActivity.this)
                                        .setTitle("Erreur")
                                        .setMessage("Erreur inconnue." + "\n" + "Resultat : '" + result +"'")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        }
                        catch (Exception e1){
                            new AlertDialog.Builder(SecondActivity.this)
                                    .setTitle("Erreur")
                                    .setMessage("Impossible de se connecter à la carte RooWifi.")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }

    public void Spot(View view) {
        TinyDB tinyDB = new TinyDB(SecondActivity.this);
        final String IP = tinyDB.getString("ip");
        final WebView myWebView = (WebView) findViewById(R.id.webview);
        Ion.with(getApplicationContext())
                .load("http://" + IP + "/roomba.cgi?button=SPOT")
                .noCache()
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            if (result.equals("0")) {
                                new AlertDialog.Builder(SecondActivity.this)
                                        .setTitle("Erreur")
                                        .setMessage("Une erreur s'est produite mais la commande a bien été envoyée !" + "\n" + "Verifiez que votre aspirateur Roomba est bien allumé et la carte RooWifi bien branchée.")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                            else if (result.equals("1")) {
                                myWebView.loadUrl("http://" + IP + "/roomba.cgi?button=SPOT");
                            }
                            else {
                                new AlertDialog.Builder(SecondActivity.this)
                                        .setTitle("Erreur")
                                        .setMessage("Erreur inconnue." + "\n" + "Resultat : '" + result +"'")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        }
                        catch (Exception e1){
                            new AlertDialog.Builder(SecondActivity.this)
                                    .setTitle("Erreur")
                                    .setMessage("Impossible de se connecter à la carte RooWifi.")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }


    private class DownloadXML extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressbar
            //pDialog = new ProgressDialog(SecondActivity.this);
            // Set progressbar title
            //pDialog.setTitle("Telechargement des données des capteurs");
            // Set progressbar message
            //pDialog.setMessage("Telechargement...");
            //pDialog.setIndeterminate(false);
            // Show progressbar
            //pDialog.show();
        }

        @Override
        protected Void doInBackground(String... Url) {
            try {
                URL url = new URL(Url[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                // Download the XML file
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                // Locate the Tag Name
                nodelist = doc.getElementsByTagName("r17");
                nodelist1 = doc.getElementsByTagName("r15");
                nodelist2 = doc.getElementsByTagName("r14");
                nodelist3 = doc.getElementsByTagName("r18");
                nodelist4 = doc.getElementsByTagName("r19");
            } catch (Exception e) {
                new AlertDialog.Builder(SecondActivity.this)
                        .setTitle("Erreur")
                        .setMessage("Erreur = " + e)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            TextView temperature = (TextView) findViewById(R.id.textView14);
            for (int temp = 0; temp < nodelist.getLength(); temp++) {
                Node nNode = nodelist.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    temperature.setText("Temperature :" + getNode("value", eElement));
                }
            }
            TextView voltage = (TextView) findViewById(R.id.textView15);
            for (int temp = 0; temp < nodelist1.getLength(); temp++) {
                Node nNode = nodelist1.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    voltage.setText("Voltage :" + getNode("value", eElement));
                }
            }
            TextView etatdecharge = (TextView) findViewById(R.id.textView19);
            for (int temp = 0; temp < nodelist2.getLength(); temp++) {
                Node nNode = nodelist2.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    etatdecharge.setText("État de charge :" + getNode("value", eElement));
                }
            }
            TextView niveaudebatterie = (TextView) findViewById(R.id.textView20);
            for (int temp = 0; temp < nodelist3.getLength(); temp++) {
                Node nNode = nodelist3.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    niveaudebatterie.setText("Niveau de batterie :" + getNode("value", eElement));
                }
            }
            TextView capacite = (TextView) findViewById(R.id.textView21);
            for (int temp = 0; temp < nodelist4.getLength(); temp++) {
                Node nNode = nodelist4.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    capacite.setText("Capacité :" + getNode("value", eElement));
                }
            }
            // Close progressbar
            //pDialog.dismiss();
        }
    }

    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_capteur:
                Intent intent;
                intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}