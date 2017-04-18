package anton.obdandroidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends Fragment {
TextView tv;

    public LiveFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live,container,false);
        tv = (TextView) view.findViewById(R.id.rpmValueID);

        // updat tv each second or somth
        tv.setText(((MainActivity)getActivity()).getRPM());
        return view;
    }
}
