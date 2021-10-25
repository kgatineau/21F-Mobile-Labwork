package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    ProgressBar progressBarL06;
    ImageView weatherViewL06;
    TextView currentTempL06;
    TextView minTempL06;
    TextView maxTempL06;
    TextView uvRatingL06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
         progressBarL06 = findViewById(R.id.progressBar_L06);
         weatherViewL06 = findViewById(R.id.imageView_L06);
         currentTempL06 = findViewById(R.id.textView1_L06);
         minTempL06 = findViewById(R.id.textView2_L06);
         maxTempL06 = findViewById(R.id.textView3_L06);
         uvRatingL06 = findViewById(R.id.textView4_L06);

        progressBarL06.setVisibility(View.VISIBLE);

        ForecastQuery forecast = new ForecastQuery();
        forecast.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric",
                "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String uv;
        private String min;
        private String max;
        private String currentTemp;
        private String icon;
        private String fileName;
        Bitmap imgPic = null;

        protected String doInBackground(String... args) {
            try {

                URL tempURL = new URL(args[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) tempURL.openConnection();

                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        //If you get here, then you are pointing at a start tag
                        if (xpp.getName().equals("temperature")) {
                            //If you get here, then you are pointing to a <temperature> start tag
                            this.currentTemp = xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            this.min = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            this.max = xpp.getAttributeValue(null, "max");
                            publishProgress(75);

                            Log.i("Current Temp", String.valueOf(this.currentTemp));
                            Log.i("Min", String.valueOf(this.min));
                            Log.i("Max", String.valueOf(this.max));


                        } else if (xpp.getName().equals("weather")) {
                            this.icon = xpp.getAttributeValue(null, "icon");
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable


                }

                this.fileName = icon + ".png";
                Log.i("Icon ID", String.valueOf(icon));
                Log.i("Looking for ", fileName);

                if (fileExistence(fileName)) {
                    try{
                    FileInputStream fis = null;
                    fis = openFileInput(fileName);
                    Log.i("File ", "loaded from local file..");
                    this.imgPic = BitmapFactory.decodeStream(fis);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Bitmap image = null;
                    URL url2 = new URL("http://openweathermap.org/img/w/" + icon + ".png");
                    HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
                    connection.connect();
                    // Log.i("Connection:", String.valueOf(connection));
                    int responseCode = connection.getResponseCode();
                    // Log.i("Response code: ", String.valueOf(responseCode));
                    if (responseCode == 200) {
                        image = BitmapFactory.decodeStream(connection.getInputStream());
                    }

                    publishProgress(100);
                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    this.imgPic = image;
                    outputStream.flush();
                    outputStream.close();
                    Log.i("File", "downloaded from net..");

                }

                int responseCode = urlConnection.getResponseCode();

                // Log.i("JSON Connection", String.valueOf(urlConnection));
                // Log.i("JSON response", String.valueOf(responseCode));

                URL uvURL = new URL(args[1]);
                HttpURLConnection urlConnection2 = (HttpURLConnection) uvURL.openConnection();
                InputStream response1 = urlConnection2.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response1, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                //    Log.i("Reading lines", "true");
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                // Log.i("JSON String", result);

                JSONObject jObject = new JSONObject(result);
                float uvRating = (float) jObject.getDouble("value");
                this.uv = String.valueOf(uvRating);
                // Log.i("UV value", String.valueOf(uvRating));

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return "Done";
        }

        public boolean fileExistence(String fName) {
            this.fileName = fName;
            File file = getBaseContext().getFileStreamPath(fName);
            return file.exists();
        }
        protected void onProgressUpdate(Integer ...value){
            progressBarL06.setVisibility(View.VISIBLE);
            progressBarL06.setProgress(value[0]);


        }
        protected void onPostExecute(String fromDoInBackground){
            weatherViewL06.setImageBitmap(imgPic);
            currentTempL06.setText(getString(R.string.textView_text1_L06, currentTemp));
            minTempL06.setText(getString(R.string.textView_text2_L06, min));
            maxTempL06.setText(getString(R.string.textView_text3_L06, max));
            uvRatingL06.setText(getString(R.string.textView_text4_L06, uv));
            progressBarL06.setVisibility(View.INVISIBLE);
        }


    }
}