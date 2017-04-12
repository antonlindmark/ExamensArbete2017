package anton.obdandroidapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int REQUEST_ENABLE_BT=1;
    private BluetoothAdapter mBluetoothAdapter;
    private  Fragment[] arrayFrag = new Fragment[5];
    public List<String> trip_list =new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayFrag[0]=new FirstFragment();
        arrayFrag[1]=new SecondFragment();
        arrayFrag[2]=new LiveFragment();
        arrayFrag[3]=new ListFragment();
        arrayFrag[4]=new HelpFragment();

        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_content,arrayFrag[0],"fragment1");
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to leave", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.startMenu) {
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content,arrayFrag[0],"fragment1");
            fragmentTransaction.commit();
        } else if (id == R.id.btopt) {
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content,arrayFrag[1],"fragment2");
            fragmentTransaction.commit();
        }
        else if (id == R.id.livevalues) {
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content,arrayFrag[2],"fragment3");
            fragmentTransaction.commit();
        }
        else if (id == R.id.trips) {
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content,arrayFrag[3],"fragment4");
            fragmentTransaction.commit();
        }
        else if (id == R.id.help) {
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_content,arrayFrag[4],"fragment5");
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void bluetoothConnection(){
        // Check whether the device has bluetooth
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
        //Makes request for enabliing bluetooth if not enabled
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    public void updatePairedDevices(ListView l){
        ArrayList<String> al = new ArrayList<>() ;
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                al.add(deviceName+"-"+deviceHardwareAddress);
            }
        }
        l.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, al));
    }
    public void startThread(String listSelection){
        BluetoothDevice d = mBluetoothAdapter.getRemoteDevice(listSelection);
        ConnectedThread t = new ConnectedThread(d,mBluetoothAdapter);
        t.start();
    }
    public List getList(){
        return trip_list;
    }
    public void changeList(String str){
        trip_list.add(str);
    }
}
