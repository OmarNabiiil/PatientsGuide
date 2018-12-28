package com.example.fouaad.hospitalstracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.MyViewHolder>{

    private List<Department> departmentsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView depName, docName, docStartTime, docEndTime, doctorPriceRange;

        public MyViewHolder(View view) {
            super(view);
            depName = view.findViewById(R.id.txt_department_name);
            docName = view.findViewById(R.id.txt_doctor_name);
            docStartTime = view.findViewById(R.id.txt_doctor_start_time);
            docEndTime = view.findViewById(R.id.txt_doctor_end_time);
            doctorPriceRange = view.findViewById(R.id.txt_doctor_prices_range);
        }

    }

    public DepartmentsAdapter(List<Department> departmentsList) {
        this.departmentsList = departmentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.departments_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Department d = departmentsList.get(holder.getAdapterPosition());
        holder.depName.setText("Department : " + d.getDepartmentName());
        holder.docName.setText("Doctor : " + d.getDoctorName());
        holder.docStartTime.setText("Start Time : " + d.getDoctorStartTime());
        holder.docEndTime.setText("End Time : " + d.getDoctorEndTime());
        holder.doctorPriceRange.setText("Price Range : " + d.getDoctorPriceRange() +" LE");
    }

    @Override
    public int getItemCount() {
        return departmentsList.size();
    }


}
