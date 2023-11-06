package com.example.databaseprac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.databaseprac.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHandler db;

    EditText eId, eName, eAge,eCity;
    Button bSave,bView,bUpdate,bDelete,bSearch;

    String id;
    String name;
    String age;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eId = findViewById(R.id.id);
        eName = findViewById(R.id.name);
        eAge = findViewById(R.id.age);
        eCity = findViewById(R.id.city);

        bSave = findViewById(R.id.save);
        bView = findViewById(R.id.view);
        bUpdate = findViewById(R.id.update);
        bDelete = findViewById(R.id.delete);
        bSearch = findViewById(R.id.search);

        bSave.setOnClickListener(this);
        bView.setOnClickListener(this);
        bUpdate.setOnClickListener(this);
        bDelete.setOnClickListener(this);
        bSearch.setOnClickListener(this);

        db = new DatabaseHandler(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.save) {
            name = eName.getText().toString();
            age = eAge.getText().toString();
            city = eCity.getText().toString();

            if (name.equals("") || age.equals("") || city.equals("")) {
                Toast.makeText(this, "Please fill the fields.", Toast.LENGTH_SHORT).show();
            } else {
                db.addEmployee(name, age, city);
                eId.setText("");
                eName.setText("");
                eAge.setText("");
                eCity.setText("");
                Toast.makeText(this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (viewId == R.id.view) {
            String data = db.getEmployee();

            if (data.equals("")) {
                Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
            }
        }
        else if (viewId == R.id.update) {
            id = eId.getText().toString().trim();
            name = eName.getText().toString().trim();
            age = eAge.getText().toString();
            city = eCity.getText().toString();

            if (id.equals("") || name.equals("") || age.equals("") || city.equals("")) {
                Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
            } else {
                long l = Long.parseLong(id);
                db.updateEmployee(l, name, age, city);
                eId.setText("");
                eName.setText("");
                eAge.setText("");
                eCity.setText("");
                Toast.makeText(this, "Data updated successfully.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (viewId == R.id.delete) {
            id = eId.getText().toString();

            if (id.equals("")) {
                Toast.makeText(this, "Please fill the Id.", Toast.LENGTH_SHORT).show();
            } else {
                long l = Long.parseLong(id);
                db.deleteEmployee(l);
                eId.setText("");
                eName.setText("");
                eAge.setText("");
                eCity.setText("");
                Toast.makeText(this, "Data deleted successfully.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (viewId == R.id.search) {
            id = eId.getText().toString().trim();

            if (id.equals("")) {
                Toast.makeText(this, "Please fill the Id.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    long l1 = Long.parseLong(id);
                    name = db.getName(l1);
                    age = db.getAge(l1);
                    city = db.getCity(l1);

                    eName.setText(name);
                    eAge.setText(age);
                    eCity.setText(city);

                    Toast.makeText(this, "Data found successfully.", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(this, "ID is not valid.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}