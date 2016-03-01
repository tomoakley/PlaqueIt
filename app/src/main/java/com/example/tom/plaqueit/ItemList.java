package com.example.tom.plaqueit;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ItemList extends Fragment implements ItemFragment.OnFragmentInteractionListener {

    private OnFragmentInteractionListener mListener;
    ArrayList<Plaque> plaques = new ArrayList<>();

    public ItemList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_item_list, container, false);

        RecyclerView recyclerView = (RecyclerView) inflatedView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(true);

        plaques = (ArrayList<Plaque>) getArguments().getSerializable("plaques");

        PlaqueRecyclerView adapter = new PlaqueRecyclerView(plaques);
        recyclerView.setAdapter(adapter);

        return inflatedView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
