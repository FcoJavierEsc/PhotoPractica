package com.utad.photopractica.recycler_view;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utad.photopractica.ModelImplement;
import com.utad.photopractica.ModelInterfaz;
import com.utad.photopractica.modelo.scope.ScopeApp;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private int mRowLayout;
    private View.OnLongClickListener mOnLongClickListener;
    private static Application sAplication;


    public RecyclerViewAdapter(Application application,int rowLayout,View.OnLongClickListener clickListener) {
        if (ScopeApp.getListPhoto() == null) {
            throw new IllegalArgumentException("modelData must not be null");

        }
        if (sAplication == null) {
            synchronized (RecyclerViewAdapter.class) {
                if (sAplication == null) {
                    sAplication = application;

                }
            }
        }
        mRowLayout=rowLayout;
        mOnLongClickListener = clickListener;
    }

    public void posSeBorra(int position){
        notifyItemRemoved(position);
    }

    public void posConCambios(int position){
        notifyItemChanged(position);
    }

    public void addModelToList(ModelImplement newModelData, int position) {
        ScopeApp.addPhotoToList(newModelData, position);
        notifyItemInserted(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(mRowLayout, viewGroup, false);
        v.setOnLongClickListener(mOnLongClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        ModelInterfaz model = ScopeApp.getByPosition(position);
        Log.v("QUE", "pues veo que es " + position);
        viewHolder.itemRecyclerView.setPhotoItemViewers(model, true);

    }

    @Override
    public int getItemCount() {
        return ScopeApp.size();
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {


        private RecyclerViewItem itemRecyclerView = null;

        public ViewHolder(View itemView) {
            super(itemView);
            itemRecyclerView = new RecyclerViewItem(sAplication,itemView);

        }
    }


}
