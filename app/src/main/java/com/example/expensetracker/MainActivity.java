package com.example.expensetracker;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpenseAdapter.OnExpenseChanged {

    private EditText editAmount, editDescription;
    private TextView textTotal;
    private final ArrayList<Expense> expenses = new ArrayList<>();
    private ExpenseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editAmount      = findViewById(R.id.editAmount);
        editDescription = findViewById(R.id.editDescription);
        Button btnAdd   = findViewById(R.id.btnAdd);
        textTotal       = findViewById(R.id.textTotal);
        RecyclerView rv = findViewById(R.id.recyclerView);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseAdapter(expenses, this);
        rv.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> addExpense());
    }

    private void addExpense() {
        String amtStr = editAmount.getText().toString();
        String desc   = editDescription.getText().toString();
        if (amtStr.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Fill both fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        double amt = Double.parseDouble(amtStr);
        expenses.add(new Expense(amt, desc));
        adapter.notifyItemInserted(expenses.size() - 1);
        editAmount.setText("");
        editDescription.setText("");
        refreshTotal();
    }

    @Override
    public void refreshTotal() {
        double total = 0;
        for (Expense e : expenses) total += e.getAmount();
        textTotal.setText("Total: ₹" + total);
    }
}