package fcm.push.com;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/*It is a mainactivity*/

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    Button post;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        post = (Button)findViewById(R.id.post);
        post.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.post :

                message();

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        sendMsg();
//                    }
//                }).start();

//                sendNotification();
                break;
        }
    }

    private void sendNotification() {
        try {
            JSONObject request = new JSONObject();
//            request.put("to", "/topics/news");
            request.put("to","eZfuUTc4_eA:APA91bG3JuE4_fYRamOJNqC2RvNodTlnttY01-CLVUPwFJtEMosTxcFUGRhnVVruGPotAZQkXBqvl8xnhaUI5GrWlDUtcVSEwOfFOyW4X4e6-JDUPWIiMLzWJDjUBdNCPUxmM2JFUeAt");
            JSONObject data = new JSONObject();

            data.put("message", "This is a Firebase Cloud Messaging Topic Message!");


            request.put("data", data);

            // Tag used to cancel the request
            String tag_json_obj = request.toString();

            String url = "https://fcm.googleapis.com/fcm/send";

            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            pDialog.hide();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    pDialog.hide();
                }
            }) {

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "key=AIzaSyA_CYatUMpq6pxxZ7BA2arUw_c9Px_546o");
                    return headers;
                }

            };

            Log.d(TAG,"request : "+tag_json_obj);

// Adding request to request queue
            FCMApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sendMsg() {
        try {
////        DBManager dbManager = DBManager.getInstance();
//        ArrayList<String> firebaseIds;
//
//        try {
//            ResultSet rs= dbManager.getRegisteredFirebaseDevice();
//            while(rs.next()){
//                System.out.println(rs.getString(1));
//                firebaseIds.add(rs.getString(1));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

            String url = "https://fcm.googleapis.com/fcm/send";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "key=AAAAzjCghwI:APA91bF7IsUiAZsTF11_iHIKDP4cj3Rv7w9CQ92nNnJq16JMLwOfacaF4OAOLnYhpFM-ovh4qy-a_AAPDZbJp8XhLuLrn607Un1is2_VMgVxaGODHoxB_xgL_lSpCQnJJBsQIcKRVneA");
            con.setRequestProperty("Content-Type", "application/json");

            // String msg="New design added in "+getCategory(designCategory)+". Design no."+designNo;
            // String urlParameters = "data.msg="+msg+"&registration_id="+firebaseIds.get(0);

            JSONObject msg = new JSONObject();
            msg.put("msg", "New design added in ");


            JSONObject parent = new JSONObject();

            parent.put("to", "cr4F-xxAU7M:APA91bHAG149-q9tbZW0t72nyZaGpEWTDWES7KB9IjT3G1OM-5dlhPQje8m9fBM69RpEtKk-ZZQT7iKuYTF8B3pjO9147dbl9Q21Zx8ttzKyPp_xxralbA17ZALbowiLusNn12cciUR_");
            parent.put("data", msg);

            con.setRequestProperty( "Content-Length", getParent().toString().length()+"");
            // String urlParameters = "registration_id="+firebaseIds.get(0);
            // Send post request
            con.setDoOutput(true);


            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(parent.toString());

            // DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            // wr.writeBytes(urlParameters);
            // wr.flush();
            // wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + parent.toString());
            System.out.println("Response Code : " + responseCode + " " + con.getResponseMessage());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void message(){
        String data = "{\"to\":\"cr4F-xxAU7M:APA91bHAG149-q9tbZW0t72nyZaGpEWTDWES7KB9IjT3G1OM-5dlhPQje8m9fBM69RpEtKk-ZZQT7iKuYTF8B3pjO9147dbl9Q21Zx8ttzKyPp_xxralbA17ZALbowiLusNn12cciUR_\"}";
        String type = "application/json";
        try {
            URL u = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty( "Authorization", "key=" + "AAAAzjCghwI:APA91bF7IsUiAZsTF11_iHIKDP4cj3Rv7w9CQ92nNnJq16JMLwOfacaF4OAOLnYhpFM-ovh4qy-a_AAPDZbJp8XhLuLrn607Un1is2_VMgVxaGODHoxB_xgL_lSpCQnJJBsQIcKRVneA" );
            conn.setRequestProperty( "Content-Type", type );
            conn.setRequestProperty( "Content-Length", String.valueOf(data.length()));
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            System.out.println(conn.getResponseCode() + " " + conn.getResponseMessage() );
            conn.disconnect();
        } catch (Exception e) {
            System.err.println("Something went wrong");
        }
    }
}
