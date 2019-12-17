package com.example.foodapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodapp.Adapters.foodItemAdapter;
import com.example.foodapp.FoodItemCard;
import com.example.foodapp.R;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodItemListActivity extends AppCompatActivity implements foodItemAdapter.OnClickListener {

    private RecyclerView foodRecyclerView;
    private RecyclerView.Adapter mFoodAdapter;
    private RecyclerView.LayoutManager foodLayoutManager;

    public Context thisContext;
    private ArrayList<String> foodNamesArrayList = new ArrayList<>();
    private ArrayList<String> expirationDatesArrayList = new ArrayList<>();
    private ArrayList<String> glutenFreeArrayList = new ArrayList<>();
    //Used to make the FoodItemCards
    private ArrayList<FoodItemCard> myFoodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_list);

        //Button Sends Sql Query and displays the information obtained
        Button getFoodBtn = findViewById(R.id.getFoodBtn);
        getFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodItemListActivity.GetData retrieveData = new FoodItemListActivity.GetData();
                retrieveData.execute("");
                displayFoodData();
            }
        });
        thisContext = this;
    }

    public void displayFoodData(){
        foodLayoutManager = new LinearLayoutManager(this);
        mFoodAdapter = new foodItemAdapter(myFoodList, this);

        foodRecyclerView = findViewById(R.id.food_recycler_view);
        foodRecyclerView.setHasFixedSize(true);
        foodRecyclerView.setLayoutManager(foodLayoutManager);
        foodRecyclerView.setAdapter(mFoodAdapter);
    }
    //This Override was used to make the cards all lead to a 'testactivity' to test functionality
    @Override
    public void onItemClick(int position) {
        final Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(FoodItemListActivity.this, TestActivity.class);
                break;

                default: intent = new Intent(FoodItemListActivity.this, TestActivity.class);

            }
            startActivity(intent);
    }


    private class GetData extends AsyncTask<String, String, String> {
        String msg = "";
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://" +
                databaseConnections.Database_Url + "/" +
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

                //Enter the Schema
                stmt = conn.createStatement();
                String enterSchema = "USE myfooddata";
                stmt.executeQuery(enterSchema);

                //Load table of user
                //Want to turn into 'String Variable' + 'FoodDataTable' to separate user tables
                String sqlCommand = "Select * from guest1FoodDataTable";
                ResultSet r = stmt.executeQuery(sqlCommand);

                //Send data from each coloumn into the their respective ArrayList
                while (r.next()) {
                    String foodName = r.getString("foodName");
                    String expirationDate =  r.getString("expirationDate");
                    String isGlutenFree = r.getString("glutenFree");

                    foodNamesArrayList.add(foodName);
                    expirationDatesArrayList.add(expirationDate);
                    glutenFreeArrayList.add(isGlutenFree);

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
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String msg) {
            if (foodNamesArrayList.size() > 0) {
                for (int i = 0; i < foodNamesArrayList.size(); i++) {
                    //Create FoodItemCards to put in the myFoodList ArrayList
                    myFoodList.add(new FoodItemCard(foodNamesArrayList.get(i),  expirationDatesArrayList.get(i), glutenFreeArrayList.get(i)));
                }
            }
        }

    }
}