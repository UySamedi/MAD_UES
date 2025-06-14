package com.example.uesapp.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uesapp.Myapi.RegistrationResponse;
import com.example.uesapp.R;
import java.util.List;

public class RegistrationAdapter extends RecyclerView.Adapter<RegistrationAdapter.RegistrationViewHolder> {

    private List<RegistrationResponse> registrationList;

    public RegistrationAdapter(List<RegistrationResponse> registrationList) {
        this.registrationList = registrationList;
    }

    @NonNull
    @Override
    public RegistrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_registration, parent, false);
        return new RegistrationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistrationViewHolder holder, int position) {
        RegistrationResponse registration = registrationList.get(position);
        if (registration != null) {
            // Personal details
            if (registration.getPersonalDetail() != null) {
                String fullName = (registration.getPersonalDetail().getFirstname() != null ? registration.getPersonalDetail().getFirstname() : "")
                        + " " + (registration.getPersonalDetail().getLastname() != null ? registration.getPersonalDetail().getLastname() : "");
                holder.nameTextView.setText(fullName.trim().isEmpty() ? "N/A" : fullName.trim());
                String phone = registration.getPersonalDetail().getPhone() != null ? registration.getPersonalDetail().getPhone() : "N/A";
                holder.phoneTextView.setText("Phone: " + phone);
            } else {
                holder.nameTextView.setText("N/A");
                holder.phoneTextView.setText("Phone: N/A");
            }

            // Education details
            if (registration.getEducationDetail() != null) {
                String education = (registration.getEducationDetail().getEducationName() != null ? registration.getEducationDetail().getEducationName() : "")
                        + " (" + (registration.getEducationDetail().getEducationGrade() != null ? registration.getEducationDetail().getEducationGrade() : "") + ")";
                holder.educationTextView.setText("Education: " + (education.trim().isEmpty() ? "N/A" : education.trim()));
            } else {
                holder.educationTextView.setText("Education: N/A");
            }

            // Department details
            if (registration.getDepartment() != null) {
                String department = registration.getDepartment().getName() != null ? registration.getDepartment().getName() : "N/A";
                holder.departmentTextView.setText("Department: " + department);
            } else {
                holder.departmentTextView.setText("Department: N/A");
            }
        }
    }

    @Override
    public int getItemCount() {
        return registrationList != null ? registrationList.size() : 0;
    }

    public static class RegistrationViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, phoneTextView, educationTextView, departmentTextView;

        public RegistrationViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            educationTextView = itemView.findViewById(R.id.educationTextView);
            departmentTextView = itemView.findViewById(R.id.departmentTextView);
        }
    }
}