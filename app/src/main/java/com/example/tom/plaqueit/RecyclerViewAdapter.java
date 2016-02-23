package com.example.tom.plaqueit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tom on 08/12/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PlaqueViewHolder> {

    class PlaqueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout plaque_item;
        TextView plaqueTitle;
        TextView plaqueDesc;
        // ImageView plaqueImage;
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
            plaqueTitle = (TextView) itemView.findViewById(R.id.plaque_title);
            plaqueDesc = (TextView) itemView.findViewById(R.id.plaque_description);
            plaquePoints = (TextView) itemView.findViewById(R.id.plaque_points);
            // plaqueImage = (ImageView) itemView.findViewById(R.id.plaque_image);

            plaque_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() >= 0) {

                Context currentContext = view.getContext();

                Intent showPlaquePage = new Intent(currentContext, plaquePage.class);

                int position = getAdapterPosition();
                showPlaquePage.putExtra("id", plaques.get(position).id);
                showPlaquePage.putExtra("title", plaques.get(position).title);
                showPlaquePage.putExtra("description", plaques.get(position).description);
                showPlaquePage.putExtra("points", plaques.get(position).points);
                showPlaquePage.putExtra("latitude", plaques.get(position).latitude);
                showPlaquePage.putExtra("longitude", plaques.get(position).longtitude);

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
    public int getItemCount() {
        return plaques.size();
    }

}
