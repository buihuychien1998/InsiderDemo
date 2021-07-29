package com.example.insiderapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.useinsider.insider.Insider;
import com.useinsider.insider.InsiderEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnLog).setOnClickListener((view)->{
// You can create an event without parameters and call the build method
//            Insider.Instance.tagEvent("eventname").build();

// You can create an event then add parameters and call the build method
            Insider.Instance.tagEvent("eventname").addParameterWithInt("key", 10).build();

// You can create an object and add the parameters later
            InsiderEvent insiderExampleEvent = Insider.Instance.tagEvent("eventname");

// Do not forget to call build method once you are done with parameters.
//Otherwise your event will be ignored.
            insiderExampleEvent.build();
        });

        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    // Log and toast
                    Log.d("TAG", token);
                    Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                }
            });
    }
}