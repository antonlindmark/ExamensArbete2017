package anton.obdandroidapp;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    private int REQUEST_ENABLE_BT=1;
    private ListView listView;
    private String selectedFromList;
    private BluetoothAdapter mBluetoothAdapter;
    private Button refresher;
    private TextView mTextField;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second,container,false);

        listView = (ListView) view.findViewById(R.id.listv);
        refresher = (Button) view.findViewById(R.id.refreshnow);

        refresher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)getActivity()).bluetoothConnection();
                ((MainActivity)getActivity()).updatePairedDevices(listView);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {

                String tempString= (String) (listView.getItemAtPosition(myItemInt));
                String parts[] = tempString.split("-");
                selectedFromList=parts[1];
                System.out.println(selectedFromList);
                ((MainActivity)getActivity()).startThread(selectedFromList);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}
