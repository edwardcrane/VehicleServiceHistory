package com.airanza.vehicleservicehistory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {\@link ManageVehiclesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageVehiclesFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageVehiclesFragment extends Fragment {

    public static int MANAGE_VEHICLES_ACTIVITY_REQUEST = 101;
    public static String VEHICLE_INTENT_DATA = "vehicle_data";

    private VehicleServiceHistoryDataSource vshDataSource;
    private List<Vehicle> vehicles = null;
    private ListView listView = null;
    private VehiclesArrayAdapter adapter = null;

    private int nDefaultSearchTextColor = Color.TRANSPARENT;

    public ManageVehiclesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_manage_vehicles, container, false);

        nDefaultSearchTextColor = ((EditText) v.findViewById(R.id.findString)).getTextColors().getDefaultColor();

        // hide keyboard unless explicitly required by user clicking:
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        try {
            vshDataSource = new VehicleServiceHistoryDataSource(getActivity());
            vshDataSource.open();
            vehicles = vshDataSource.getAllVehicles();
            System.out.println("Retrieved: " + vehicles.size() + " vehicles from datasource.");

            adapter = new VehiclesArrayAdapter(getActivity().getApplicationContext(), vehicles);
            listView = (ListView) v.findViewById(R.id.listMain);
            listView.setAdapter(adapter);
            System.out.println("after setting adapter");

        } catch (SQLException e) {
            Log.w(this.getClass().getName(), e);
        }

        ListView listView = (ListView) v.findViewById(R.id.listMain);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), VehicleActivity.class);
                        intent.putExtra(VEHICLE_INTENT_DATA, vehicles.get(position));
                        startActivity(intent);

                        adapter.notifyDataSetChanged();
                    }
                }
        );

        addTextChangedListener(v);

        return (v);
    }


//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }
//

    /**
     * implement the auto-search functionality by typing in the search field without need for button press.
     */
    public void addTextChangedListener(View v) {
        // get editText component:
        EditText editText = (EditText) v.findViewById(R.id.findString);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                findResource(listView);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void findResource(View view) {
        String findString = ((EditText) getActivity().findViewById(R.id.findString)).getText().toString();
        adapter.clear();

        for (Vehicle v : vshDataSource.findVehicles(findString)) {
            adapter.add(v);
        }

        // values array is updated via the adapter:
        if (vehicles.isEmpty() && !findString.equals("")) {
            ((EditText) getActivity().findViewById(R.id.findString)).setTextColor(Color.RED);
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), String.format(getString(R.string.manage_vehicles_no_matching_records_found), findString), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            // SET TEXT TO DEFAULT COLOR (saved in onCreate):
            ((EditText) getActivity().findViewById(R.id.findString)).setTextColor(nDefaultSearchTextColor);
        }
    }
}
