package labsi.roowificontroller;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.TabHost;
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

    NodeList nodelist;
    NodeList nodelist1;
    NodeList nodelist2;
    NodeList nodelist3;
    NodeList nodelist4;
    NodeList nodelist5;
    NodeList nodelist6;
    NodeList nodelist7;
    NodeList nodelist8;
    NodeList nodelist9;
    NodeList nodelist10;
    NodeList nodelist11;
    NodeList nodelist12;
    NodeList nodelist13;
    NodeList nodelist14;
    NodeList nodelist15;
    NodeList nodelist16;
    NodeList nodelist17;
    NodeList nodelist18;
    NodeList nodelist19;

    RelativeLayout layout_joystick;
    TextView textView1, textView2, textView5;
    JoyStickClass js;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final TinyDB tinyDB = new TinyDB(this);
        final String IP = tinyDB.getString("ip");

        CheckConnection(IP);

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        TabHost.TabSpec spec = host.newTabSpec("");
        spec.setContent(R.id.tab1);
        spec.setIndicator("");
        host.addTab(spec);
        spec = host.newTabSpec("");
        spec.setContent(R.id.tab2);
        spec.setIndicator("");
        host.addTab(spec);


        TextView textView_IP = (TextView) findViewById(R.id.textView_IP);
        textView_IP.setText("IP : " + IP);

        int premierefois = tinyDB.getInt("premierefois");

        if (premierefois ==1){
            new AlertDialog.Builder(SecondActivity.this)
                    .setTitle("Information")
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.activity_second_information))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            tinyDB.putInt("premierefois", 0);
                        }
                    })
                    .show();
        }

        TinyDB tinyDB1 = new TinyDB(SecondActivity.this);
        int modeavance = tinyDB1.getInt("modeavance");
        if (modeavance == 0){
            TextView textView12 = (TextView)findViewById(R.id.textView12);
            TextView textView1 = (TextView)findViewById(R.id.textView1);
            TextView textView2 = (TextView)findViewById(R.id.textView2);
            TextView textView5 = (TextView)findViewById(R.id.textView5);
            TextView textView61 = (TextView)findViewById(R.id.textView61);
            TextView textView19 = (TextView)findViewById(R.id.textView19);
            textView12.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView5.setVisibility(View.GONE);
            textView61.setVisibility(View.GONE);
            textView19.setVisibility(View.GONE);
        }

        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView5 = (TextView)findViewById(R.id.textView5);
        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);
        js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(800, 800);
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
                    TinyDB tinyDB = new TinyDB(SecondActivity.this);
                    String IP = tinyDB.getString("ip");
                    WebView myWebView = (WebView) findViewById(R.id.webview);

                    int direction = js.get8Direction();
                    if(direction == JoyStickClass.STICK_UP) {
                        textView5.setText(R.string.activity_second_direction_front);
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=a");
                    } else if(direction == JoyStickClass.STICK_UPRIGHT) {
                        textView5.setText(R.string.activity_second_direction_frontright);
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=a");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=f");
                    } else if(direction == JoyStickClass.STICK_RIGHT) {
                        textView5.setText(R.string.activity_second_direction_right);
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=f");
                    } else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
                        textView5.setText(R.string.activity_second_direction_rearright);
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=l");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=f");
                    } else if(direction == JoyStickClass.STICK_DOWN) {
                        textView5.setText(R.string.activity_second_direction_rear);
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=l");
                    } else if(direction == JoyStickClass.STICK_DOWNLEFT) {
                        textView5.setText(R.string.activity_second_direction_rearleft);
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=l");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=c");
                    } else if(direction == JoyStickClass.STICK_LEFT) {
                        textView5.setText(R.string.activity_second_direction_left);
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=c");
                    } else if(direction == JoyStickClass.STICK_UPLEFT) {
                        textView5.setText(R.string.activity_second_direction_frontleft);
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=a");
                        myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=c");
                    } else if(direction == JoyStickClass.STICK_NONE) {
                        textView5.setText(R.string.activity_second_direction_center);
                        myWebView.loadUrl(null);
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    textView1.setText("X : 0");
                    textView2.setText("Y : 0");
                    textView5.setText(R.string.activity_second_direction_center);
                }
                return true;
            }
        });
    }

    private void CheckConnection(final String IP) {
        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(SecondActivity.this);
        pDialog.setTitle(R.string.pdialog_tryconnect);
        pDialog.setCancelable(false);
        pDialog.setMessage(getResources().getString(R.string.pdialog_wait));
        pDialog.setIndeterminate(false);
        pDialog.show();

        Ion.with(getApplicationContext())
                .load("http://" + IP + "/roomba.xml")
                .noCache()
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (result == null) {
                            pDialog.dismiss();
                            new AlertDialog.Builder(SecondActivity.this)
                                    .setTitle(R.string.error)
                                    .setCancelable(false)
                                    .setMessage(getResources().getString(R.string.cantconnect1))
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            System.exit(0);
                                        }
                                    })
                                    .show();
                        }
                        else {
                            if (result.startsWith("<response>")) {
                                pDialog.dismiss();
                                Init();
                            } else {
                                pDialog.dismiss();
                                new AlertDialog.Builder(SecondActivity.this)
                                        .setTitle(R.string.error)
                                        .setCancelable(false)
                                        .setMessage(getResources().getString(R.string.cantconnect1))
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                System.exit(0);
                                            }
                                        })
                                        .show();
                            }
                        }
                    }
                });
    }

    private void Init() {
        TinyDB tinyDB1 = new TinyDB(SecondActivity.this);
        int downloadeachtick = tinyDB1.getInt("downloadeachtick");
        if (downloadeachtick == 1) {
            TinyDB tinyDB2 = new TinyDB(this);
            int ticknomber = tinyDB2.getInt("eachtick");
            ticknomber = ticknomber*1000;
            Timer timerAsync = new Timer();
            TimerTask timerTaskAsync = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                                 new DownloadXML().execute();
                        }
                    });
                }
            };
            timerAsync.schedule(timerTaskAsync, 0, ticknomber);
        }
        else {
             new DownloadXML().execute();
        }
    }

    public void Commander(View view) {
        TinyDB tinyDB = new TinyDB(SecondActivity.this);
        final String IP = tinyDB.getString("ip");
        final WebView myWebView = (WebView) findViewById(R.id.webview);
        Ion.with(getApplicationContext())
                .load("http://" + IP + "/rwr.cgi?exec=h")
                .noCache()
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            if (result.equals("0")) {
                                new AlertDialog.Builder(SecondActivity.this)
                                        .setTitle(R.string.error)
                                        .setMessage(getResources().getString(R.string.activity_second_failbutconnected))
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                            else if (result.equals("1")) {
                                myWebView.loadUrl("http://" + IP + "/rwr.cgi?exec=h");
                            }
                            else {
                                new AlertDialog.Builder(SecondActivity.this)
                                        .setTitle(R.string.error)
                                        .setMessage(getResources().getString(R.string.activity_second_unknownerror) + "\n" + getResources().getString(R.string.activity_second_result) + result +"'")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        }
                        catch (Exception e1){
                            new AlertDialog.Builder(SecondActivity.this)
                                    .setTitle(R.string.error)
                                    .setMessage(getResources().getString(R.string.activity_second_cantconnect))
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
                        }
                    }
                });
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
                                        .setTitle(R.string.error)
                                        .setMessage(getResources().getString(R.string.activity_second_failbutconnected))
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
                                        .setTitle(R.string.error)
                                        .setMessage(getResources().getString(R.string.activity_second_unknownerror) + "\n" + getResources().getString(R.string.activity_second_result) + result +"'")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        }
                        catch (Exception e1){
                            new AlertDialog.Builder(SecondActivity.this)
                                    .setTitle(R.string.error)
                                    .setMessage(getResources().getString(R.string.activity_second_cantconnect))
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
                                        .setTitle(R.string.error)
                                        .setMessage(getResources().getString(R.string.activity_second_failbutconnected))
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
                                        .setTitle(R.string.error)
                                        .setMessage(getResources().getString(R.string.activity_second_unknownerror) + "\n" + getResources().getString(R.string.activity_second_result) + result +"'")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        }
                        catch (Exception e1){
                            new AlertDialog.Builder(SecondActivity.this)
                                    .setTitle(R.string.error)
                                    .setMessage(getResources().getString(R.string.activity_second_cantconnect))
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
                                        .setTitle(R.string.error)
                                        .setMessage(getResources().getString(R.string.activity_second_failbutconnected))
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
                                        .setTitle(R.string.error)
                                        .setMessage(getResources().getString(R.string.activity_second_unknownerror) + "\n" + getResources().getString(R.string.activity_second_result) + result +"'")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                            }
                        }
                        catch (Exception e1){
                            new AlertDialog.Builder(SecondActivity.this)
                                    .setTitle(R.string.error)
                                    .setMessage(getResources().getString(R.string.activity_second_cantconnect))
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }

    class DownloadXML extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... Url) {
            try {
                TinyDB tinyDB = new TinyDB(SecondActivity.this);
                final String IP = tinyDB.getString("ip");
                URL url = new URL("http://" + IP + "/roomba.xml");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();
                nodelist = doc.getElementsByTagName("r0");
                nodelist1 = doc.getElementsByTagName("r1");
                nodelist2 = doc.getElementsByTagName("r2");
                nodelist3 = doc.getElementsByTagName("r3");
                nodelist4 = doc.getElementsByTagName("r4");
                nodelist5 = doc.getElementsByTagName("r5");
                nodelist6 = doc.getElementsByTagName("r6");
                nodelist7 = doc.getElementsByTagName("r7");
                nodelist8 = doc.getElementsByTagName("r8");
                nodelist9 = doc.getElementsByTagName("r9");
                nodelist10 = doc.getElementsByTagName("r10");
                nodelist11 = doc.getElementsByTagName("r11");
                nodelist12 = doc.getElementsByTagName("r12");
                nodelist13 = doc.getElementsByTagName("r13");
                nodelist14 = doc.getElementsByTagName("r14");
                nodelist15 = doc.getElementsByTagName("r15");
                nodelist16 = doc.getElementsByTagName("r16");
                nodelist17 = doc.getElementsByTagName("r17");
                nodelist18 = doc.getElementsByTagName("r18");
                nodelist19 = doc.getElementsByTagName("r19");

            } catch (Exception e) {
                new AlertDialog.Builder(SecondActivity.this)
                        .setTitle(R.string.error)
                        .setMessage(getResources().getString(R.string.error) + " = " + e)
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
            for (int temp = 0; temp < nodelist.getLength(); temp++)
                for (int temp1 = 0; temp1 < nodelist1.getLength(); temp1++)
                    for (int temp2 = 0; temp2 < nodelist2.getLength(); temp2++)
                        for (int temp3 = 0; temp3 < nodelist3.getLength(); temp3++)
                            for (int temp4 = 0; temp4 < nodelist4.getLength(); temp4++)
                                for (int temp5 = 0; temp5 < nodelist5.getLength(); temp5++)
                                    for (int temp6 = 0; temp6 < nodelist6.getLength(); temp6++)
                                        for (int temp7 = 0; temp7 < nodelist7.getLength(); temp7++)
                                            for (int temp8 = 0; temp8 < nodelist8.getLength(); temp8++)
                                                for (int temp9 = 0; temp9 < nodelist9.getLength(); temp9++)
                                                    for (int temp10 = 0; temp10 < nodelist10.getLength(); temp10++)
                                                        for (int temp11 = 0; temp11 < nodelist11.getLength(); temp11++)
                                                            for (int temp12 = 0; temp12 < nodelist12.getLength(); temp12++)
                                                                for (int temp13 = 0; temp13 < nodelist13.getLength(); temp13++)
                                                                    for (int temp14 = 0; temp14 < nodelist14.getLength(); temp14++)
                                                                        for (int temp15 = 0; temp15 < nodelist15.getLength(); temp15++)
                                                                            for (int temp16 = 0; temp16 < nodelist16.getLength(); temp16++)
                                                                                for (int temp17 = 0; temp17 < nodelist17.getLength(); temp17++)
                                                                                    for (int temp18 = 0; temp18 < nodelist18.getLength(); temp18++)
                                                                                        for (int temp19 = 0; temp19 < nodelist19.getLength(); temp19++) {

                                                                                            Node nNode = nodelist.item(temp);
                                                                                            Node nNode1 = nodelist1.item(temp1);
                                                                                            Node nNode2 = nodelist2.item(temp2);
                                                                                            Node nNode3 = nodelist3.item(temp3);
                                                                                            Node nNode4 = nodelist4.item(temp4);
                                                                                            Node nNode5 = nodelist5.item(temp5);
                                                                                            Node nNode6 = nodelist6.item(temp6);
                                                                                            Node nNode7 = nodelist7.item(temp7);
                                                                                            Node nNode8 = nodelist8.item(temp8);
                                                                                            Node nNode9 = nodelist9.item(temp9);
                                                                                            Node nNode10 = nodelist10.item(temp10);
                                                                                            Node nNode11 = nodelist11.item(temp11);
                                                                                            Node nNode12 = nodelist12.item(temp12);
                                                                                            Node nNode13 = nodelist13.item(temp13);
                                                                                            Node nNode14 = nodelist14.item(temp14);
                                                                                            Node nNode15 = nodelist15.item(temp15);
                                                                                            Node nNode16 = nodelist16.item(temp16);
                                                                                            Node nNode17 = nodelist17.item(temp17);
                                                                                            Node nNode18 = nodelist18.item(temp18);
                                                                                            Node nNode19 = nodelist19.item(temp19);

                                                                                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                                                                                                Element eElement = (Element) nNode;
                                                                                                Element eElement1 = (Element) nNode1;
                                                                                                Element eElement2 = (Element) nNode2;
                                                                                                Element eElement3 = (Element) nNode3;
                                                                                                Element eElement4 = (Element) nNode4;
                                                                                                Element eElement5 = (Element) nNode5;
                                                                                                Element eElement6 = (Element) nNode6;
                                                                                                Element eElement7 = (Element) nNode7;
                                                                                                Element eElement8 = (Element) nNode8;
                                                                                                Element eElement9 = (Element) nNode9;
                                                                                                Element eElement10 = (Element) nNode10;
                                                                                                Element eElement11 = (Element) nNode11;
                                                                                                Element eElement12 = (Element) nNode12;
                                                                                                Element eElement13 = (Element) nNode13;
                                                                                                Element eElement14 = (Element) nNode14;
                                                                                                Element eElement15 = (Element) nNode15;
                                                                                                Element eElement16 = (Element) nNode16;
                                                                                                Element eElement17 = (Element) nNode17;
                                                                                                Element eElement18 = (Element) nNode18;
                                                                                                Element eElement19 = (Element) nNode19;

                                                                                                TextView textView35 = (TextView) findViewById(R.id.textView35);
                                                                                                TextView textView36 = (TextView) findViewById(R.id.textView36);
                                                                                                TextView textView37 = (TextView) findViewById(R.id.textView37);
                                                                                                TextView textView38 = (TextView) findViewById(R.id.textView38);
                                                                                                TextView textView39 = (TextView) findViewById(R.id.textView39);
                                                                                                TextView textView40 = (TextView) findViewById(R.id.textView40);
                                                                                                TextView textView41 = (TextView) findViewById(R.id.textView41);
                                                                                                TextView textView42 = (TextView) findViewById(R.id.textView42);
                                                                                                TextView textView44 = (TextView) findViewById(R.id.textView44);
                                                                                                TextView textView45 = (TextView) findViewById(R.id.textView45);

                                                                                                TextView textView46 = (TextView) findViewById(R.id.textView46);
                                                                                                TextView textView47 = (TextView) findViewById(R.id.textView47);
                                                                                                TextView textView48 = (TextView) findViewById(R.id.textView48);
                                                                                                TextView textView49 = (TextView) findViewById(R.id.textView49);
                                                                                                TextView textView50 = (TextView) findViewById(R.id.textView50);
                                                                                                TextView textView51 = (TextView) findViewById(R.id.textView51);
                                                                                                TextView textView52 = (TextView) findViewById(R.id.textView52);
                                                                                                TextView textView53 = (TextView) findViewById(R.id.textView53);
                                                                                                TextView textView54 = (TextView) findViewById(R.id.textView54);
                                                                                                TextView textView55 = (TextView) findViewById(R.id.textView55);

                                                                                                textView35.setText("Bumps Wheeldrops : " + getNode("value", eElement));
                                                                                                textView36.setText("Wall : " +getNode("value", eElement1));
                                                                                                textView37.setText("Cliff Left : " + getNode("value", eElement2));
                                                                                                textView38.setText("Cliff Front Left : " +getNode("value", eElement3));
                                                                                                textView39.setText("Cliff Front Right : " +getNode("value", eElement4));
                                                                                                textView40.setText("Cliff Right : " +getNode("value", eElement5));
                                                                                                textView41.setText("Virtual Wall : " +getNode("value", eElement6));
                                                                                                textView42.setText("Motor Overcurrents : " +getNode("value", eElement7));
                                                                                                textView44.setText("Dirt Detector - Left : " +getNode("value", eElement8));
                                                                                                textView45.setText("Dirt Detector - Right : " +getNode("value", eElement9));

                                                                                                textView46.setText("Remote Opcode : " +getNode("value", eElement10));
                                                                                                textView47.setText("Buttons : " +getNode("value", eElement11));
                                                                                                textView48.setText("Distance : " +getNode("value", eElement12));
                                                                                                textView49.setText("Angle : " +getNode("value", eElement13));
                                                                                                textView50.setText("Charging State : " +getNode("value", eElement14));
                                                                                                textView51.setText("Voltage : " +getNode("value", eElement15));
                                                                                                textView52.setText("Current : " +getNode("value", eElement16));
                                                                                                textView53.setText("Temperature : " +getNode("value", eElement17));
                                                                                                textView54.setText("Charge : " +getNode("value", eElement18));
                                                                                                textView55.setText("Capacity : " +getNode("value", eElement19));

                                                                                                TextView textView14 = (TextView) findViewById(R.id.textView14);
                                                                                                textView14.setText(getResources().getString(R.string.activity_second_temperature) + " " +getNode("value", eElement17)+"°C" );
                                                                                                TextView textView20 = (TextView) findViewById(R.id.textView20);
                                                                                                String cap = getNode("value", eElement19);
                                                                                                String cha = getNode("value", eElement18);

                                                                                                int Capacity = Integer.parseInt(cap);
                                                                                                int Charge = Integer.parseInt(cha);
                                                                                                int a = Charge * 100;
                                                                                                if (Capacity == 0){
                                                                                                }
                                                                                                else {
                                                                                                    int percent = a/Capacity;
                                                                                                    textView20.setText(getResources().getString(R.string.activity_second_batterylevel)+" "+percent+" %");
                                                                                                }

                                                                                                int chargingstate = Integer.parseInt(getNode("value", eElement14));
                                                                                                TextView textView7 = (TextView) findViewById(R.id.textView7);
                                                                                                if (chargingstate == 2){

                                                                                                    textView7.setText(getResources().getString(R.string.activity_second_chargingstate2));
                                                                                                }
                                                                                                else if (chargingstate == 4){
                                                                                                    textView7.setText(getResources().getString(R.string.activity_second_chargingstate4));
                                                                                                }
                                                                                                else {
                                                                                                    textView7.setText(getResources().getString(R.string.activity_second_chargingstateunknown));
                                                                                                }

                                                                                                TinyDB tinyDB = new TinyDB(SecondActivity.this);
                                                                                                int actualisation = tinyDB.getInt("actualisation");
                                                                                                tinyDB.putInt("actualisation", actualisation + 1);
                                                                                                int nbactualisation = tinyDB.getInt("actualisation");
                                                                                                TextView textView = (TextView)findViewById(R.id.textView19);
                                                                                                textView.setText(getResources().getString(R.string.activity_second_nbsensorupdate) + " " + nbactualisation);
                                                                                            }
                                                                                        }

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
            case R.id.action_settings:
                Intent intent;
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                View aboutview = View.inflate(this, R.layout.view_about, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("RooWifi Controller v"+ version.version);
                builder.setIcon(R.drawable.logo);
                builder.setView(aboutview)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    public boolean onTouchEvent(MotionEvent event) //Tabs
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    if (x2 > x1)
                    {
                        TabHost host = (TabHost)findViewById(R.id.tabHost);
                        host.setCurrentTab(0);
                        setTitle(R.string.activity_second_title1);
                    }
                    else
                    {
                        TabHost host = (TabHost)findViewById(R.id.tabHost);
                        host.setCurrentTab(1);
                        setTitle(R.string.activity_second_title2);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}