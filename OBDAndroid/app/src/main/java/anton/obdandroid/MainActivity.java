package anton.obdandroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_ENABLE_BT=1;
    private ListView listView;
    private String selectedFromList;
    private BluetoothAdapter mBluetoothAdapter;
    private Button refresher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        refresher = (Button) findViewById(R.id.refresh);
        bluetoothConnection();

        refresher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updatePairedDevices();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {

                String tempString= (String) (listView.getItemAtPosition(myItemInt));
                String parts[] = tempString.split("-");
                selectedFromList=parts[1];
                System.out.println(selectedFromList);

                BluetoothDevice d = mBluetoothAdapter.getRemoteDevice(selectedFromList);
                ConnectThread t = new ConnectThread(d,mBluetoothAdapter);
                t.start();
            }
        });
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

    public void updatePairedDevices(){
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
        listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, al));
    }
}


