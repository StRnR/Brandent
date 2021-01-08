package com.pixium.brandent.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.brandent.adapters.ClinicAdapter;
import com.pixium.brandent.viewmodels.ClinicViewModel;
import com.pixium.brandent.R;
import com.pixium.brandent.db.entities.Clinic;

import java.util.List;
import java.util.UUID;

public class ClinicActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private ClinicViewModel clinicViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinics);

        Button backBtn = findViewById(R.id.btn_back_clinics);
        Button clinicAddBtn = findViewById(R.id.btn_add_clinic_clinics);

        RecyclerView recyclerView = findViewById(R.id.rv_clinics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ClinicAdapter adapter = new ClinicAdapter();
        recyclerView.setAdapter(adapter);

        clinicViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(ClinicViewModel.class);
        clinicViewModel.getAllClinics().observe(this,
                new Observer<List<Clinic>>() {
                    @Override
                    public void onChanged(@Nullable List<Clinic> clinics) {
                        adapter.submitList(clinics);
                    }
                });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder viewHolder
                    , @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                clinicViewModel.delete(adapter.getClinicAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ClinicActivity.this, "Clinic Deleted!"
                        , Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ClinicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Clinic clinic) {
                Intent intent = new Intent(ClinicActivity.this
                        , AddEditClinicActivity.class);
                intent.putExtra(AddEditClinicActivity.EXTRA_ID, clinic.getClinicId());
                intent.putExtra(AddEditClinicActivity.EXTRA_UUID, clinic.getUuid().toString());
                intent.putExtra(AddEditClinicActivity.EXTRA_TITLE, clinic.getTitle());
                intent.putExtra(AddEditClinicActivity.EXTRA_COLOR, clinic.getColor());
                intent.putExtra(AddEditClinicActivity.EXTRA_ADDRESS, clinic.getAddress());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        clinicAddBtn.setOnClickListener(v ->
                startActivityForResult(new Intent(ClinicActivity.this
                        , AddEditClinicActivity.class), ADD_NOTE_REQUEST));

        backBtn.setOnClickListener(v -> super.onBackPressed());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditClinicActivity.EXTRA_TITLE);
            String color = data.getStringExtra(AddEditClinicActivity.EXTRA_COLOR);
            String address = data.getStringExtra(AddEditClinicActivity.EXTRA_ADDRESS);

            Clinic clinic = new Clinic(null, null, title, address, color);
            clinicViewModel.insert(clinic);

            Toast.makeText(this, "Clinic successfully added!", Toast.LENGTH_SHORT)
                    .show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditClinicActivity.EXTRA_ID, -1);

            if (id == -1 || data.getStringExtra(AddEditClinicActivity.EXTRA_UUID) == null) {
                Toast.makeText(this, "Clinic can't be updated"
                        , Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditClinicActivity.EXTRA_TITLE);
            String color = data.getStringExtra(AddEditClinicActivity.EXTRA_COLOR);
            String address = data.getStringExtra(AddEditClinicActivity.EXTRA_ADDRESS);

            UUID uuid = UUID.fromString(data.getStringExtra(AddEditClinicActivity.EXTRA_UUID));

            Clinic clinic = new Clinic(uuid, null, title, address, color);
            clinic.setClinicId(id);
            clinicViewModel.update(clinic);

            Toast.makeText(this, "Clinic Updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Clinic wasn't added successfully"
                    , Toast.LENGTH_SHORT).show();
        }
    }
}