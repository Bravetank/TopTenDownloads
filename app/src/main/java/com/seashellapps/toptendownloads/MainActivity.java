package com.seashellapps.toptendownloads;


import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; //For logging

    private ListView listApps; //To display parsed XML data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Sets layout
        listApps = findViewById(R.id.xmlListView); //Wires up the variable ListAppswith the xmlListView we've created in the layout

        downloadUrl("https://rss.itunes.apple.com/api/v1/us/ios-apps/top-free/all/10/explicit.rss");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String feedUrl;

        switch(id){
            case R.id.mnuFree:
                feedUrl="https://rss.itunes.apple.com/api/v1/us/ios-apps/top-free/all/10/explicit.rss";
                break;
            case R.id.mnuPaid:
                feedUrl="https://rss.itunes.apple.com/api/v1/us/ios-apps/top-paid/all/10/explicit.rss";
                break;
            case R.id.mnuSongs:
                feedUrl="https://rss.itunes.apple.com/api/v1/us/itunes-music/top-songs/all/10/explicit.rss";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        downloadUrl(feedUrl);
        return true;
    }

    private void downloadUrl(String feedUrl) {
        Log.d(TAG, "downloadUrl: starting AsyncTask"); //Starts the other thread to get the data
        DownloadData downloadData = new DownloadData();
        downloadData.execute(feedUrl);
        Log.d(TAG, "downloadUrl: done");
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        private static final String TAG = "DownloadData"; //Logging

        @Override
        protected void onPostExecute(String s) { //Back on Main Thread
            super.onPostExecute(s); //Has the XMLData String so can move onto the parsing
          //  Log.d(TAG, "onPostExecute: parameter is " + s);

            ParseApplications parseApplications = new ParseApplications(); //object created
            parseApplications.parse(s);//Calls the parse method (see class)

            FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this, R.layout.list_record, parseApplications.getApplications() );
            listApps.setAdapter(feedAdapter); //Using custome feed adaptee

//            ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, parseApplications.getApplications()); //Tells array adaptee about list_item view and applications array list //This is default Array Adapter
//            listApps.setAdapter(arrayAdapter); //ListView will show above
        }

        @Override
        protected String doInBackground(String... strings) {
          //  Log.d(TAG, "doInBackground: starts with " + strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
         //       Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
            //    Log.d(TAG, "downloadXML: The response code was " + response);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500];
                while(true){
                    charsRead = reader.read(inputBuffer);
                    if(charsRead < 0){
                        break;
                    }
                    if (charsRead > 0){
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();

                return xmlResult.toString();
            } catch (MalformedURLException e) {
         //       Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
         //       Log.e(TAG, "downloadXML: IO Exception reading data " + e.getMessage());
            } catch (SecurityException e) {
         //       Log.e(TAG, "downloadXML: Security Exception " + e.getMessage());
                //e.printStackTrace();
            }

            return null;
        }
    }
}





