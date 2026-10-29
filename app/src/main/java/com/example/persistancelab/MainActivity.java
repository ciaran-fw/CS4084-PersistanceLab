package com.example.persistancelab;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NameStore nameStore = new StringNameStore();
        //NameStore nameStore = new JSONNameStore();
        //NameStore nameStore = new DBNameStore(this);

        ArrayList<Name> values = nameStore.getNames(this);

        values.add(new Name("Ciaran", "Fernandez"));
        values.add(new Name("John", "Smith"));
        values.add(new Name("Rory", "Fitzgerald"));

        nameStore.writeNames(this, values);

        values = nameStore.getNames(this);

        StringBuilder names = new StringBuilder();

        for (Name name : values) {
            names.append("\n").append(name.getFirst()).append(" ").append(name.getLast());
        }

        TextView textView = findViewById(R.id.greetingView);
        textView.setText("Persistence says hello," + names + "!");
    }
}