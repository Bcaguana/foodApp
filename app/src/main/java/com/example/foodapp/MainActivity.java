package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    foodItemAdapter foodItemAdapter;
    Context thisContext;
    ListView listOfData;
    Map<String, String> foodMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        listOfData = findViewById(R.id.listOfData);
        thisContext = this;


        Button btn = findViewById(R.id.getInventoryButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                GetData retrieveData = new GetData();
                retrieveData.execute("");
            }
        });
    }
    private class GetData extends AsyncTask<String, String, String>{

        String msg = "";
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

        static final String DB_URL = "jdbc:mysql://"+
                databaseConnections.Database_Url+ "/" +
                databaseConnections.Database_Name;

        //Option to Create an event before execution
        /*
        @Override
        protected void onPreExecute(){

        }*/

        @Override
        protected String doInBackground(String... strings) {
            //Needs initiation for the 'finally' clause
            Connection conn = null;
            Statement stmt = null;

            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, databaseConnections.Username, databaseConnections.Password);

                stmt = conn.createStatement();
                String sqlCommand = "Select * From user1fooddata";
                ResultSet r = stmt.executeQuery(sqlCommand);

                while (r.next()){
                    String foodName = r.getString("foodName");
                    String expirationDate = r.getString("expirationDate");

                    foodMap.put(foodName, expirationDate);
                }

                r.close();
                stmt.close();
                conn.close();

                msg = "Data successfully retrieved.";

            } catch (SQLException e) {
                msg = "Exception thrown for JDBC.";
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                msg = "Class not found exception thrown.";
                e.printStackTrace();
            } finally {

                try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    if (conn != null){
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

                return null;
        }

        @Override
        protected void onPostExecute(String msg){
            if (foodMap.size() > 0){
                foodItemAdapter = new foodItemAdapter(thisContext,foodMap);
                listOfData.setAdapter(foodItemAdapter);
            }
        }
    }
}
