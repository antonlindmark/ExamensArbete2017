package anton.obdandroidapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private ListView listView;
    private int counter = 3;

    public ListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list,container,false);

        listView = (ListView) view.findViewById(R.id.triplist);

        String[] trips = new String[] { // Array of all the trips
                "Trip  1",
                "Trip  2",
                "Trip  3"
        };
        // Create a List from String Array elements
        final List<String> trip_list = new ArrayList<String>(Arrays.asList(trips));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (view.getContext(), android.R.layout.simple_list_item_1, trip_list);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {

                String tempString= (String) (listView.getItemAtPosition(myItemInt));
                System.out.println(tempString);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                trip_list.add("Trip  "+counter);
                arrayAdapter.notifyDataSetChanged();


            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with a upload function", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return view;
    }
}
