package anton.obdandroidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends Fragment {
    private TextView RPMView;

    public LiveFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live,container,false);
        RPMView = (TextView) view.findViewById(R.id.textView5);
        return view;
    }
    public void updateValues(String value){
        RPMView.setText(value);
    }
}
