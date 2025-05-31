package com.example.uesapp.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uesapp.Myapi.ApiInterface;
import com.example.uesapp.Myapi.RegistrationResponse;
import com.example.uesapp.Myapi.RetrofitInstance;
import com.example.uesapp.R;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RegistrationAdapter adapter;
    private List<RegistrationResponse> registrationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_user);

        recyclerView = findViewById(R.id.registrationRecyclerView);
        registrationList = new ArrayList<>();
        adapter = new RegistrationAdapter(registrationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fetchRegistrations();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchRegistrations() {
        ApiInterface apiService = RetrofitInstance.getInstance(this).getApiInterface();
        Call<List<RegistrationResponse>> call = apiService.getAllRegistrations();
        call.enqueue(new Callback<List<RegistrationResponse>>() {
            @Override
            public void onResponse(Call<List<RegistrationResponse>> call, Response<List<RegistrationResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    registrationList.clear();
                    registrationList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        Log.e("API Error", "Unsuccessful response: Code " + response.code() + " - " + errorBody);
                        Toast.makeText(ViewUserActivity.this, "Failed to load registrations: " + response.message(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("API Error", "Error reading response: " + e.getMessage(), e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RegistrationResponse>> call, Throwable t) {
                Log.e("API Error", "Failure: " + t.getMessage() + "\nStacktrace: ", t);
                Toast.makeText(ViewUserActivity.this, "Failed to load registrations: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}