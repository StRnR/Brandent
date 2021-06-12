package com.brandent.clinitick;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.brandent.clinitick.adapters.ClinicAdapter;

public class SwipeToDeleteClinicsCallback extends ItemTouchHelper.SimpleCallback {
    private final ClinicAdapter mClinicAdapter;

    private final Drawable icon;
    private final ColorDrawable background;

    public SwipeToDeleteClinicsCallback(ClinicAdapter adapter, Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mClinicAdapter = adapter;
        icon = ContextCompat.getDrawable(context, R.drawable.ic_trash);
        background = new ColorDrawable(ContextCompat.getColor(context, R.color.ignoreRed));
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView
            , @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
    }
}
