package org.blissos.powermanagerclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import org.blissos.powermanager.BlissPowerManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rebootBtn = findViewById(R.id.rebootBtn);
        Button shutdownBtn = findViewById(R.id.shutdownBtn);

        BlissPowerManager blissPowerManager = BlissPowerManager.getInstance(this);

        rebootBtn.setOnClickListener(v -> blissPowerManager.reboot());
        shutdownBtn.setOnClickListener(v -> blissPowerManager.shutdown());
    }
}
