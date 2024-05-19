package com.example.appthoitrang.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthoitrang.Adapter.SoldAdapter;
import com.example.appthoitrang.Domain.PurchasedDomain;
import com.example.appthoitrang.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FragmentSold extends Fragment {
    private EditText startDay, endDay;
    private AppCompatButton searchBtn, allBtn;
    private SoldAdapter soldAdapter;
    private RecyclerView soldView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sold,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startDay=view.findViewById(R.id.startDay);
        endDay=view.findViewById(R.id.endDay);
        searchBtn=view.findViewById(R.id.searchBtn);
        soldView=view.findViewById(R.id.soldView);
        allBtn=view.findViewById(R.id.allBtn);
        soldAdapter= new SoldAdapter();
        Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog1=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDay.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                dialog1.show();
            }
        });
        endDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog2=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDay.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                dialog2.show();
            }
        });
        initSold();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference metaData=database.getReference("Purchased");
                ArrayList<PurchasedDomain> purchasedDomains=new ArrayList<>();
                ArrayList<PurchasedDomain> purchasedDomains2=new ArrayList<>();
                metaData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot issue : snapshot.getChildren()) {
                                purchasedDomains.add(issue.getValue(PurchasedDomain.class));
                            }
                            for (PurchasedDomain i: purchasedDomains){
                                if (isDateInRange(i.getDate(),startDay.getText().toString(),endDay.getText().toString())){
                                    purchasedDomains2.add(i);
                                }
                            }
                            if (!purchasedDomains2.isEmpty()){
                                soldView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                                soldAdapter.setList(purchasedDomains2);
                                soldView.setAdapter(soldAdapter);
                            }
                            else{
                                Toast.makeText(getContext(),"There are no products for sale yet", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSold();
            }
        });
    }

    private void initSold() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference metaData=database.getReference("Purchased");
        ArrayList<PurchasedDomain> purchasedDomains=new ArrayList<>();
        metaData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        purchasedDomains.add(issue.getValue(PurchasedDomain.class));
                    }
                    if (!purchasedDomains.isEmpty()) {
                        soldView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                        soldAdapter.setList(purchasedDomains);
                        soldView.setAdapter(soldAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Phương thức kiểm tra xem ngày có nằm trong khoảng thời gian cho trước không
    private boolean isDateInRange(String dateString, String startDateString, String endDateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateString);
            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);
            return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
