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

import com.example.tom.plaqueit.StickyHeadersRecyclerView.StickyRecyclerHeadersDecoration;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ItemList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ItemList extends Fragment implements ItemFragment.OnFragmentInteractionListener, ItemListHeader.OnFragmentInteractionListener {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    public ItemList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_item_list, container, false);

        recyclerView = (RecyclerView) inflatedView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initializeAdapter();

        return inflatedView;
    }

    private void initializeAdapter() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(dashboard_view.plaques);
        recyclerView.setAdapter(adapter);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);
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
