package com.utad.photopractica.recycler_view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.utad.photopractica.ModelImplement;
import com.utad.photopractica.R;
import com.utad.photopractica.modelo.scope.ScopeApp;
import com.utad.photopractica.PhotoActivity;

public class UsoRecyclerView {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private Context mContext;
    private Activity mActivity;
    private View.OnLongClickListener mOnLongClickListener;
    private boolean mList[];
    private int mContador;
    private static int mColorFondoSuelto;
    private static int mColorFondoPresionado;

    private UsoRecyclerViewListener mUsoRecyclerViewListener;

    public interface UsoRecyclerViewListener {
        public void startIntent(Intent intent);
    }

    public UsoRecyclerView(Activity activity, Context context, View.OnLongClickListener clickListener, UsoRecyclerViewListener usoRecyclerViewListener) {

        mActivity = activity;
        mContext = context;
        mOnLongClickListener = clickListener;
        mColorFondoSuelto = context.getResources().getColor(R.color.primary_light);
        mColorFondoPresionado = context.getResources().getColor(R.color.primary_dark);
        mUsoRecyclerViewListener = usoRecyclerViewListener;
    }

    /*
        public UsoRecyclerView(Activity activity, Context context) {

            mActivity = activity;
            mContext = context;
            mOnLongClickListener = null;
            mColorFondoSuelto = context.getResources().getColor(R.color.primary_light);
            mColorFondoPresionado = context.getResources().getColor(R.color.primary_dark);
        }
    */
    public void activa() {
        mRecyclerView = (RecyclerView) mActivity.findViewById(R.id.recyclerView);
        if (mOnLongClickListener != null)
            mRecyclerView.setOnLongClickListener(mOnLongClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new RecyclerViewAdapter(
                mActivity.getApplication(),
                R.layout.card_item_show,
                mOnLongClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.v("ALGO ES ", "position " + position);
                if (mList != null) {
                    mList[position] = !mList[position];

                    decora(position);
                    Log.v("ALGO ES presion", "position " + position + " cambio ");
                    mContador += mList[position] ? 1 : -1;
                    mAdapter.notifyItemChanged(position);

                } else {
                    ScopeApp.setTransfer(position);
                    Intent intent = new Intent(mContext, PhotoActivity.class);
                    mUsoRecyclerViewListener.startIntent(intent);
                }

            }
        }));
    }


    public void posSeBorra(int position) {
        mAdapter.posSeBorra(position);
    }

    public void posConCambios(int position) {
        mAdapter.posConCambios(position);
    }

    public void addModel(ModelImplement modelInterfaz) {
        mAdapter.addModelToList(modelInterfaz, 0);
    }

    private void decora(int position) {
        int color = mList[position] ? mColorFondoPresionado : mColorFondoSuelto;
        RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForPosition(position);
        if (viewHolder == null) return;
        View qView = viewHolder.itemView.findViewById(R.id.container_inner_item);
        if (qView == null) return;
        qView.setBackgroundColor(color);
    }

    public void masivoUpdate(String cual, int cat, int sub) {
        if (!cual.equals("") || (cat != -1) || (sub != -1)) {
            for (int i = 0; i < mList.length; i++) {
                if (mList[i]) {
                    ModelImplement modelImplement = ScopeApp.getByPosition(i);
                    if (!cual.equals("")) modelImplement.setTitulo(cual);
                    if (cat != -1) modelImplement.setCategoria(cat);
                    if (sub != -1) modelImplement.setSubCategoria(sub);
                    ScopeApp.updatePhoto(modelImplement);
                    mAdapter.posConCambios(i);
                    Log.v("SELEC", "este " + i);
                }
            }

        }

    }

    public void modoSeleccion(boolean selec) {
        if (selec) {

            mList = new boolean[mAdapter.getItemCount()];
            for (int i = 0; i < mList.length; i++) {
                mList[i] = false;
            }
            mContador = 0;
        } else {
            Log.v("SELEC", "Total" + mContador);
            for (int i = 0; i < mList.length; i++) {
                if (mList[i]) {
                    mList[i] = false;
                    decora(i);
                    Log.v("SELEC", "este " + i);
                }
            }
            mList = null;
        }
    }

    public boolean[] getList() {
        return mList;
    }
}
