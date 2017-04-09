package labsi.roowificontroller;

import android.app.AlertDialog;
//import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class ListActivity extends AppCompatActivity {
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
    //ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TinyDB tinyDB = new TinyDB(ListActivity.this);
        final String IP = tinyDB.getString("ip");

        class DownloadXML extends AsyncTask<String, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Create a progressbar
                //pDialog = new ProgressDialog(ListActivity.this);
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
                    URL url = new URL("http://" + IP + "/roomba.xml");
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    // Download the XML file
                    Document doc = db.parse(new InputSource(url.openStream()));
                    doc.getDocumentElement().normalize();
                    // Locate the Tag Name
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
                    nodelist10= doc.getElementsByTagName("r10");
                    nodelist11= doc.getElementsByTagName("r11");
                    nodelist12= doc.getElementsByTagName("r12");
                    nodelist13= doc.getElementsByTagName("r13");
                    nodelist14= doc.getElementsByTagName("r14");
                    nodelist15= doc.getElementsByTagName("r15");
                    nodelist16= doc.getElementsByTagName("r16");
                    nodelist17= doc.getElementsByTagName("r17");
                    nodelist18= doc.getElementsByTagName("r18");
                    nodelist19= doc.getElementsByTagName("r19");

                } catch (Exception e) {
                    new AlertDialog.Builder(ListActivity.this)
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
                                                                                            for (int temp19 = 0; temp19 < nodelist19.getLength(); temp19++){

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


                        String[] data = {"Bumps Wheeldrops\n" + getNode("value", eElement) , "Wall\n" +getNode("value", eElement1), "Cliff Left\n" + getNode("value", eElement2), "Cliff Front Left\n" +getNode("value", eElement3), "Cliff Front Right\n" +getNode("value", eElement4), "Cliff Right\n" +getNode("value", eElement5), "Virtual Wall\n" +getNode("value", eElement6), "Motor Overcurrents\n" +getNode("value", eElement7), "Dirt Detector - Left\n" +getNode("value", eElement8), "Dirt Detector - Right\n" +getNode("value", eElement9), "Remote Opcode\n" +getNode("value", eElement10), "Buttons\n" +getNode("value", eElement11), "Distance\n" +getNode("value", eElement12), "Angle\n" +getNode("value", eElement13), "Charging State\n" +getNode("value", eElement14), "Voltage\n" +getNode("value", eElement15), "Current\n" +getNode("value", eElement16), "Temperature\n" +getNode("value", eElement17), "Charge\n" +getNode("value", eElement18), "Capacity\n" +getNode("value", eElement19)};
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ListActivity.this, android.R.layout.simple_list_item_1, data);
                        ListView listView = (ListView)findViewById(R.id.list);

                        listView.setAdapter(adapter);
                    }
                }
                // Close progressbar
               // pDialog.dismiss();

            }
        }

        TinyDB tinyDB1 = new TinyDB(ListActivity.this);
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



    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }

}