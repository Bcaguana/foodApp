package com.example.foodapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Adapters.MainMenuAdapter;
import com.example.foodapp.MainMenuItems;
import com.example.foodapp.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MainMenuAdapter.OnClickListener {

private RecyclerView recyclerView;
private RecyclerView.Adapter mAdapter;
private RecyclerView.LayoutManager layoutManager;
private TextView usernameTextView;
//Variable 'username' would be used to store a username, for now by default holds guest for testing
private String username = "guest";
private final static int REQUEST_CODE_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Retrieve SavedUsername from some source if available to autofill login screen
        //Empty string will be used if no SavedUsername Exists
        String temp = loginPage();

        //Username used for display in Homepage
        String helloUserString = "Hello, " + temp;

        //Arraylist that store MainMenuItem objects
        ArrayList<MainMenuItems> menuList = new ArrayList<>();
        menuList.add(new MainMenuItems("Account", "Manage User Login Information"));
        menuList.add(new MainMenuItems("Food Inventory", "View, Sort, and Manage Food Inventory"));
        menuList.add(new MainMenuItems("Recipes", "View Available Recipes"));

        recyclerView = findViewById(R.id.main_recycler_view);
        usernameTextView = findViewById(R.id.usernameTextView);
        usernameTextView.setText(helloUserString);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        //Sends the menu cards to be displayed in Cards in the Recyclerview
        mAdapter = new MainMenuAdapter(menuList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        }
    //An override to provide Recyclerview with Onclicklistner functionality, allows Cards to be clicked
    @Override
    public void onItemClick(int position) {
        final Intent intent;
        switch (position){
            case 0:
                intent =  new Intent(MainActivity.this , ManageUserActivity.class);
                //Sends saved username
                intent.putExtra("user", username);
                break;

            case 1:
                intent =  new Intent(MainActivity.this, FoodItemListActivity.class);
                break;

            default:
                //For testing, default sends you to the main menu
                intent =  new Intent(MainActivity.this, MainActivity.class);

        }
        startActivity(intent);
    }
    public String loginPage(){
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        //If a username was stored, the name would be sent and autofilled in the username field
        loginIntent.putExtra("user", username);
        startActivityForResult(loginIntent, REQUEST_CODE_1);
        return loginIntent.getStringExtra("user");
    }
}


