package anton.obdandroidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    private ListView listView;
    private String selectedFromList;
    private Button refresher;

    public SecondFragment() {
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
                String parts[] = tempString.split("-"); // Indicator to separate name from MAC-Address
                selectedFromList=parts[1];
                System.out.println(selectedFromList);
                ((MainActivity)getActivity()).startThread(selectedFromList);

            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
