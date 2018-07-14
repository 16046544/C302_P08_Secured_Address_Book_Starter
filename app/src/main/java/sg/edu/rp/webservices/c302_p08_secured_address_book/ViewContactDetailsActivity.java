package sg.edu.rp.webservices.c302_p08_secured_address_book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class ViewContactDetailsActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etMobile;
    private Button btnUpdate, btnDelete;
    private int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_details);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        contactId = getIntent().getExtras().getInt("id");
        final String contactIdS = String.valueOf(contactId);
        String firstName = getIntent().getExtras().getString("firstname");
        String lastName = getIntent().getExtras().getString("lastname");
        String mobile = getIntent().getExtras().getString("mobile");

        etFirstName.setText(firstName);
        etLastName.setText(lastName);
        etMobile.setText(mobile);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2/C302_P08_SecuredCloudAddressBook/updateContact.php";
                HttpRequest request = new HttpRequest(url);

                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("POST");
                request.addData("id",contactIdS);
                request.addData("FirstName", etFirstName.getText().toString());
                request.addData("LastName", etLastName.getText().toString());
                request.addData("Mobile", etMobile.getText().toString());

                request.execute();
                finish();
            }

            private HttpRequest.OnHttpResponseListener mHttpResponseListener =
                    new HttpRequest.OnHttpResponseListener() {
                        @Override
                        public void onResponse(String response) {

                            // process response here
                            try {
                                Log.i("JSON results:", response);
                                JSONObject jsonObj = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), jsonObj.getString("message"),Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2/C302_P08_SecuredCloudAddressBook/deleteContact.php";
                HttpRequest request = new HttpRequest(url);

                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("POST");
                request.addData("id",contactIdS);

                request.execute();
                finish();
            }

            private HttpRequest.OnHttpResponseListener mHttpResponseListener =
                    new HttpRequest.OnHttpResponseListener() {
                        @Override
                        public void onResponse(String response) {

                            // process response here
                            try {
                                Log.i("JSON results:", response);
                                JSONObject jsonObj = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), jsonObj.getString("message"),Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Code for step 1 start
        Intent intent = getIntent();
        contactId = intent.getIntExtra("contact_id", -1);

    }
}