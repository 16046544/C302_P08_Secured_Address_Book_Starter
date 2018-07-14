package sg.edu.rp.webservices.c302_p08_secured_address_book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO (1) When Login button is clicked, call doLogin.php web service to check if the user is able to log in
                // What is the web service URL?
                // What is the HTTP method?
                // What parameters need to be provided?

                String url = "http://10.0.2.2/C302_P08_SecuredCloudAddressBook/doLogin.php";
                HttpRequest request = new HttpRequest(url);

                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("POST");
                request.addData("username", etUsername.getText().toString());
                request.addData("password", etPassword.getText().toString());

                request.execute();



            }
        });


    }

    // TODO (2) In the HttpResponseListener, check if the user has been authenticated successfully
    // If the user can log in, extract the id and API Key from the JSON object, set them into Intent and start MainActivity Intent.
    // If the user cannot log in, display a toast to inform user that login has failed.

    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response) {

                    // process response here
                    try {
                        Log.i("JSON results:", response);
                        JSONObject result = new JSONObject(response);
                        Boolean authenticated = result.getBoolean("authenticated");

                        if (authenticated == true){
                            String apikey = result.getString("apikey");
                            String id = result.getString("id");

                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("loginId",id);
                            intent.putExtra("apikey",apikey);

                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "Login failed. Please check your login credentials.", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };

}
