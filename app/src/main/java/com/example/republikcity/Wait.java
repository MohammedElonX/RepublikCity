package com.example.republikcity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Wait extends AppCompatActivity {
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        mButton = findViewById(R.id.retry_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Checks for network connection
                ConnectivityManager cm
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                //Does not load url if no connection
                if(isConnected) {
                    Intent i = new Intent(Wait.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(Wait.this, "No Connection!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
