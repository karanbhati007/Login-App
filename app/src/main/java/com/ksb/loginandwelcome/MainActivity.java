package com.ksb.loginandwelcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static ArrayList<String> userArray;
    static ArrayList<String> passArray;

    EditText ed1,ed2;
    Button but1,but2;
    int status;
    SharedPreferences sharedPreferences;
    String user,pass;

    public void login() {
        int f=0;
        user = ed1.getText().toString();
        pass = ed2.getText().toString();

        if (user.equals("") || pass.equals("")) {
            Toast.makeText(this, "Username and Password Both Required !!", Toast.LENGTH_SHORT).show();
        } else {
            try {
                userArray = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("username", ObjectSerializer.serialize(new ArrayList<String>())));
                passArray = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("pass", ObjectSerializer.serialize(new ArrayList<String>())));


                for(int i=0;i<userArray.size();i++) {
                    if (userArray.get(i).equals(user) && passArray.get(i).equals(pass)) {
                        f = 1;
                        Intent intent = new Intent(MainActivity.this, Welcome.class);
                        intent.putExtra("username1","Welcome "+ userArray.get(i));
                        startActivity(intent);

                    }
                }

                if(f==0)
                    Toast.makeText(this, "Either user not Exist or Password is incorrect !!", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


        public void signUp () {
            user = ed1.getText().toString();
            pass = ed2.getText().toString();

            if (user.equals("") || pass.equals("")) {
                Toast.makeText(this, "Username and Password Both Required !!", Toast.LENGTH_SHORT).show();
            } else {
                try {

                    userArray = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("username", ObjectSerializer.serialize(new ArrayList<String>())));
                    passArray = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("pass", ObjectSerializer.serialize(new ArrayList<String>())));


                    if (userArray.size() > 0) {
                        for (String s : userArray) {
                            if (s.equals(user)) {
                                Toast.makeText(this, "User Already Exist !!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }

                    userArray.add(user);
                    passArray.add(pass);

                    Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

                    sharedPreferences.edit().putString("username", ObjectSerializer.serialize(userArray)).apply();
                    sharedPreferences.edit().putString("pass", ObjectSerializer.serialize(passArray)).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Cannot Sign Up", Toast.LENGTH_SHORT).show();
                }


            }


        }










    public void action(View v)
    {
        if(status==1)
            login();
        else
            signUp();

    }



    public void change(View v)
    {

        if(status==1) {
            but1.setText("SIGN UP");
            but2.setText("or , LOGIN");
            status = 0;
        }
        else
        {
            but1.setText("LOGIN");
            but2.setText("or , SIGN UP");
            status = 1;
        }

    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.ksb.loginandwelcome",MODE_PRIVATE);

        status=1;
        ed1 =(EditText) findViewById(R.id.editText1);
        ed2 =(EditText) findViewById(R.id.editText2);
        but1 = (Button) findViewById(R.id.button1);
        but2 = (Button) findViewById(R.id.button2);

    }

}
