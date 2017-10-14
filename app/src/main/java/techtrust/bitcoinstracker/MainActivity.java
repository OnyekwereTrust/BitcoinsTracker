package techtrust.bitcoinstracker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void , String>{


        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{

               url = new URL(urls[0]);

                urlConnection =(HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }
                return result;

        }

        catch (Exception e){

            e.printStackTrace();

            return "failed";
        }




        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DownloadTask Task = new DownloadTask();
        String result = null;
        try {
            result = Task.execute("https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=USD,EUR,NGN," +
                    "RUB,CAD,JPY,GBP,AUD,INR,HKD,IDR,SGD,CHF,CNY,ZAR,THB,SAR,KRW,GHS,BRL").get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }

       Log.i("Contents of URL", result);



    }
}
