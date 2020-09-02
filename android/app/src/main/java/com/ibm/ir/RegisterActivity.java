package com.ibm.ir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    private String username, password;
    private EditText editTextUsername, editTextPassword;
    private Button saveData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = (EditText) findViewById(R.id.login);
        editTextPassword = (EditText) findViewById(R.id.password);
        saveData = (Button) findViewById(R.id.saveData);

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();
                JSONObject logindata = new JSONObject();
                try {
                    logindata.put("login", username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    logindata.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String server_url = "http://192.168.0.66:8081/register";
                new RegisterActivity.RaportOperation().execute(server_url, logindata.toString());
            }
        });
    }

    public void doSomethingElse() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        RegisterActivity.this.finish();
    }

    private class RaportOperation extends AsyncTask<String, Void, String> {

        private String jsonResponse;
        private ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);

        @Override
        protected String doInBackground(String... params) {
            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
                receivedata(httpURLConnection);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        private void receivedata(HttpURLConnection connection) throws Exception {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String returnString = "";
            StringBuilder allData = new StringBuilder("");

            while ((returnString = in.readLine()) != null) {
                allData.append(returnString);
            }
            in.close();

            Log.e("TAG", allData.toString());
        }

        protected void onPostExecute(String result) {
            Log.e("TAG", result);
            if (result.contains("accepted")) {
                doSomethingElse();
            }
            else {
                Toast.makeText(RegisterActivity.this, "Nie udało się utworzyć konta z powyższych danych", Toast.LENGTH_LONG).show();
            }
        }
    }
}
