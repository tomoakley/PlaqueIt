package com.example.tom.plaqueit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tom.plaqueit.StickyHeadersRecyclerView.StickyRecyclerHeadersAdapter;
import com.example.tom.plaqueit.StickyHeadersRecyclerView.StickyRecyclerHeadersDecoration;
import com.example.tom.plaqueit.StickyHeadersRecyclerView.StickyRecyclerHeadersTouchListener;

import java.util.List;

/**
 * Created by Tom on 08/12/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PlaqueViewHolder> implements StickyRecyclerHeadersAdapter {

    class PlaqueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout plaque_item;
        //Creating variables for holding the items that will be in the CardViews
        TextView plaqueTitle;
        TextView plaqueDesc;
        TextView listHeader;
        TextView plaquePoints;

        /*
         * Taking the View we passed it when inflating shout_item.xml and then finding
         * all of the individual views inside of it (the TextViews and ImageView at the moment)
         *
         * @param itemView the shout_item.xml inflated view (containing TextViews and ImageView)
         */
        PlaqueViewHolder(View itemView) {
            super(itemView);
            plaque_item = (RelativeLayout) itemView.findViewById(R.id.plaque_item);
            listHeader = (TextView) itemView.findViewById(R.id.list_header);
            plaqueTitle = (TextView) itemView.findViewById(R.id.plaque_title);
            plaqueDesc = (TextView) itemView.findViewById(R.id.plaque_description);
            plaquePoints = (TextView) itemView.findViewById(R.id.plaque_points);

            plaque_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() >= 0) {

                Context currentContext = view.getContext();

                Intent showPlaquePage = new Intent(currentContext, plaquePage.class);

                int position = getAdapterPosition();
                showPlaquePage.putExtra("title", plaques.get(position).title);
                showPlaquePage.putExtra("description", plaques.get(position).description);
                showPlaquePage.putExtra("points", plaques.get(position).points);

                currentContext.startActivity(showPlaquePage);
            }
        }
    }

    List<Plaque> plaques;

    RecyclerViewAdapter(List<Plaque> plaques) {
        this.plaques = plaques;
    }

    @Override
    public PlaqueViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_item, viewGroup, false);
        return new PlaqueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PlaqueViewHolder plaqueViewHolder, final int i) {
        plaqueViewHolder.plaqueTitle.setText(plaques.get(i).title);
        plaqueViewHolder.plaqueDesc.setText(plaques.get(i).description);
        plaqueViewHolder.plaquePoints.setText(plaques.get(i).points);
    }


    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_list_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return plaques.size();
    }

}
