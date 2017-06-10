package com.example.princ.navigationbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class profileActivity extends AppCompatActivity {

    private Button create;
    private EditText age;
    private EditText weight;
    private CheckBox male;
    private CheckBox female;
    private Spinner mySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        age = (EditText) findViewById(R.id.age);
        weight = (EditText) findViewById(R.id.weight);
        male = (CheckBox) findViewById(R.id.male);
        female = (CheckBox) findViewById(R.id.female);
        mySpinner = (Spinner) findViewById(R.id.spinner);
        //mySpinner.setOnItemSelectedListener();
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(profileActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ActiveLevel));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        //mySpinner.setOnItemSelectedListener(onItemSelected());
        //mySpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TEXT FOR SPINNER", mySpinner.getSelectedItem().toString());
                String selected = mySpinner.getSelectedItem().toString();
                //if(!age.getText().toString().isEmpty() && !weight.getText().toString().isEmpty() && (male.isChecked() || female.isChecked()) && mySpinner.isSelected() ) {
                if(age.getText().toString().isEmpty() || weight.getText().toString().isEmpty() || (!male.isChecked() && !female.isChecked()) || selected.equals("SELECT"))
                {
                    Toast.makeText(profileActivity.this, "Not Everything is Entered", Toast.LENGTH_LONG).show();
                    // Toast.makeText(profileActivity.this, "Everything Entered", Toast.LENGTH_LONG).show();
                    // Intent i = new Intent(profileActivity.this, MainActivity.class);
                    // startActivity(i);
                }
                else
                //else if(age.getText().toString().isEmpty() || weight.getText().toString().isEmpty() || (!male.isChecked() && !female.isChecked()) || !mySpinner.isSelected() )
                {
                    Toast.makeText(profileActivity.this, "Everything Entered", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(profileActivity.this, MainActivity.class);
                    startActivity(i);
                    //  Toast.makeText(profileActivity.this, "Not Everything is Entered", Toast.LENGTH_LONG).show();
                }
            }
        });




    }


}
