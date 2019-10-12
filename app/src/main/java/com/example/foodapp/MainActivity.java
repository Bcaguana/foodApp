package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.getInventoryButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                GetData retrieveData = new GetData();
                retrieveData.execute("");
            }
        });
    }
    private class GetData extends AsyncTask<String, String, String>{

        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

        static final String DB_URL = "jdbc:mysql://"+
                databaseConnections.Database_Url+ "/" +
                databaseConnections.Database_Name;

        @Override
        protected String doInBackground(String... strings) {
            Connection conn;
            Statement stmt;
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, databaseConnections.Username, databaseConnections.Password);

                stmt = conn.createStatement();
                String sqlCommand = "Select * From foodInventory";
                ResultSet r = stmt.executeQuery(sqlCommand);

                while (r.next()){
                    String foodNames = r.getString("foodNames");
                    String expiirationDates = r.getString("expirationDates");
                };
                r.close();
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
