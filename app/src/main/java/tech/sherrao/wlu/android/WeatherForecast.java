package tech.sherrao.wlu.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WeatherForecast extends AppCompatActivity {

    class ForecastQuery extends AsyncTask<String, Integer, String> {

        public static final String API_KEY = "dcb4c99a5df9107c36f398032741e86c";
        public static final String BASE_QUERY_URL = "https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=";
        public static final String BASE_ICON_URL = "https://openweathermap.org/img/w/";
        public static final String QUERY_URL = BASE_QUERY_URL + API_KEY + "&mode=xml&units=metric";

        private Bitmap currentWeatherImage;
        private String minTemperature;
        private String maxTemperature;
        private String currentTemperature;

        @Override
        protected String doInBackground(String... strings) {
            Log.i(this.getClass().getSimpleName(), "doInBackground called");
            Log.i(this.getClass().getSimpleName(), "Using API Key w/ URL: " + QUERY_URL);
            parseXml();
            return null;
        }

        @Override
        public void onProgressUpdate(Integer... values) {
            if(progressBar == null || values.length <= 0)
                return;

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            if(progressBar == null)
                return;

            progressBar.setVisibility(View.INVISIBLE);
            weatherImage.setImageBitmap(currentWeatherImage);
            minTemperatureText.setText("Min Temp: " + minTemperature);
            maxTemperatureText.setText("Max Temp: " + maxTemperature);
            currentTemperatureText.setText("Current Temp: " + currentTemperature);
            super.onPostExecute(result);
        }

        private void parseXml() {
            try {
                InputStream in = downloadUrl();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(in));

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_TAG) {
                        String tag = xpp.getName();
                        if (tag.equals("temperature")) {
                            this.currentTemperature = xpp.getAttributeValue(null, "value");
                            super.publishProgress(25);
                            Log.i(this.getClass().getSimpleName(), "Found value from XML for current temperature=" + currentTemperature);

                            this.minTemperature = xpp.getAttributeValue(null, "min");
                            super.publishProgress(50);
                            Log.i(this.getClass().getSimpleName(), "Found value from XML for min temperature=" + currentTemperature);

                            this.maxTemperature = xpp.getAttributeValue(null, "max");
                            super.publishProgress(75);
                            Log.i(this.getClass().getSimpleName(), "Found value from XML for max temperature=" + currentTemperature);

                        } else if (tag.equals("weather")) {
                            String iconName = xpp.getAttributeValue(null, "icon");
                            Log.i(this.getClass().getSimpleName(), "Found value from XML for icon=" + iconName);

                            String fileName = iconName + ".png";
                            Bitmap bitmap = null;
                            File file = getBaseContext().getFileStreamPath(fileName);
                            if (!file.exists()) {
                                bitmap = downloadImage(iconName);
                                FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

                                bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();

                            } else {
                                try {
                                    FileInputStream fis = openFileInput(fileName);
                                    bitmap = BitmapFactory.decodeStream(fis);

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }

                            super.publishProgress(100);
                            currentWeatherImage = bitmap;
                        }
                    }

                    eventType = xpp.next();
                }

                Log.d(this.getClass().getSimpleName(),"End of parsing XML data!");

            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
        }

        private InputStream downloadUrl() {
            try {
                URL url = new URL(QUERY_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                return conn.getInputStream();

            } catch(IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private Bitmap downloadImage(String iconName) {
            try {
                URL url = new URL(BASE_ICON_URL + iconName + ".png");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                int responseCode = connection.getResponseCode();
                Bitmap bitmap = responseCode == 200 ? BitmapFactory.decodeStream(connection.getInputStream()) : null;
                connection.disconnect();
                return bitmap;

            } catch (Exception e) { e.printStackTrace(); }

            return null;
        }
    }

    private ProgressBar progressBar;
    private ImageView weatherImage;
    private TextView minTemperatureText;
    private TextView maxTemperatureText;
    private TextView currentTemperatureText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_weather_forecast);

        progressBar = super.findViewById(R.id.weatherProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        weatherImage = super.findViewById(R.id.weatherImage);
        minTemperatureText = super.findViewById(R.id.minTemperatureText);
        maxTemperatureText = super.findViewById(R.id.maxTemperatureText);
        currentTemperatureText = super.findViewById(R.id.currentTemperatureText);

        new ForecastQuery().execute();
    }

}