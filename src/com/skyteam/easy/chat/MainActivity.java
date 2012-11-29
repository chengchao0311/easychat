package com.skyteam.easy.chat;

import org.jivesoftware.smack.RosterEntry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.android.Facebook;
import com.skyteam.easy.chat.MessagesFragment.MessagesFragmentListener;
import com.skyteam.easy.chat.PeopleFragment.PeopleFragmentListener;

public class MainActivity extends FragmentActivity 
    implements PeopleFragmentListener, MessagesFragmentListener {

    private static final String TAG = "MainActivity";
    private final Facebook facebook = new Facebook(FacebookHelper.APPID);
    private final FacebookHelper mFacebookHelper = new FacebookHelper(this, facebook);
    
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main_activity);
        
        // Log In to Facebook
        mFacebookHelper.login();
        
        // Start ChatService
        Intent intent = new Intent(this, ChatService.class);
        startService(intent);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        facebook.extendAccessToken(this, null);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        if (isFinishing()) {
            // Log Out from Facebook
            /*mFacebookHelper.logout();*/
            // Stop ChatService 
            /*Intent intent = new Intent(this, ChatService.class);
            stopService(intent);*/
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.authorizeCallback(requestCode, resultCode, data);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get menu items
        MenuItem loginItem = menu.findItem(R.id.menu_login);
        MenuItem logoutItem = menu.findItem(R.id.menu_logout);
        
        // Set visibility
        if (facebook.isSessionValid()) {
            loginItem.setVisible(false);
            logoutItem.setVisible(true);
        } else {
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
        }
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //FragmentTransaction transaction;

        switch (item.getItemId()) {
            
        // Log In
        case R.id.menu_login:
            mFacebookHelper.login();
            
            /*if (findViewById(R.id.first_pane) != null) {
                new ShowPeopleTask().execute();
            }
            
            if (findViewById(R.id.second_pane) != null) {
                new ShowMessagesTask().execute();
            }*/
            
            return true;
            
        // Log Out
        case R.id.menu_logout:
            // Chat & Facebook logout 
            mFacebookHelper.logout();
            //mChat.logout();  
            
            // Clear fragments
            /*if (peopleFragment != null)
                peopleFragment.clear();
            if (messagesFragment != null)
                messagesFragment.clear();
            if (conversationFragment != null)
                conversationFragment.clear();*/
            
            return true;
        
        // Exit option only for testing purposes
        case R.id.menu_exit:
            //android.os.Process.killProcess(android.os.Process.myPid());
            finish();
            
            return true;
            
        // Settings
        case R.id.menu_settings:
            /* TODO what happens if user click on settings button */            
            return true;
        
        default:
            return super.onOptionsItemSelected(item);
            
        }
    }
    
    @Override
    public void onPeopleSelected(RosterEntry entry) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onMessageSelected(FacebookData data) {
        // TODO Auto-generated method stub
        
    }
    
}
