package gr.uom.exams.androidexamsjan17;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Thodoris on 24-Jan-17.
 */

public class FetchMerchantsTask extends AsyncTask<String,Void,ArrayList<Merchant>> {

    private final String LOG_TAG = FetchMerchantsTask.class.getSimpleName();
    private MerchantAdapter merchantAdapter;
    public static final String YUMMY_BASE_DOMAIN = "http://dev.savecash.gr:3000";

    public FetchMerchantsTask( MerchantAdapter merchantAdapter){
        this.merchantAdapter = merchantAdapter;

    }

    private ArrayList<Merchant> getMerchantsFromJson(String merchantJsonStr) throws JSONException {
        ArrayList<Merchant> merchants = new ArrayList<>();
        try{
            JSONArray merchantsArray = new JSONArray(merchantJsonStr);
            for(int i=0; i<merchantsArray.length(); i++){
                JSONObject jsonMerchant = merchantsArray.getJSONObject(i);
                String merId = jsonMerchant.getString("id");
                String merName = jsonMerchant.getString("legalName");
                String merImageUrl = YUMMY_BASE_DOMAIN+jsonMerchant.getString("image");
                JSONObject jsonAddress = jsonMerchant.getJSONObject("contactPoint");
                String merAddress = jsonAddress.getString("streetAddress");
                JSONObject jsonReview = jsonMerchant.getJSONObject("aggregateRating");
                String review = jsonReview.getString("ratingValue");

                JSONObject jsonMerCategory = jsonMerchant.getJSONObject("merchantCategory");
                String category = jsonMerCategory.getString("name");

                merchants.add(new Merchant(merId, merName, category, merAddress, merImageUrl,review));
            }

            Log.d(LOG_TAG, "Merchant Fetching Complete. " + merchants.size() + "merchants inserted");
            return  merchants;
        }catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            return  merchants;
        }
    }

    @Override
    protected ArrayList<Merchant> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String merchantJsonStr = null;

        try {
            final String YUMMY_MERCHANTS_URL =
                    "/Merchant/index.json?$orderby=dateCreated%20desc";

            Uri builtUri = Uri.parse(YUMMY_BASE_DOMAIN+YUMMY_MERCHANTS_URL);

            URL url = new URL(builtUri.toString());

            // Create the request to Yummy Wallet server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            merchantJsonStr = buffer.toString();
            return  getMerchantsFromJson(merchantJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Merchant> merchants) {
        if(merchants.size() > 0){
            this.merchantAdapter.clear();
            for(Merchant m : merchants){
                this.merchantAdapter.add(m);
            }
        }
    }
}
