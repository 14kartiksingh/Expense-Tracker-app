package com.example.expensetracker;


import android.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    public interface OnExpenseChanged {
        void refreshTotal();
    }

    private final ArrayList<Expense> expenseList;
    private final OnExpenseChanged callback;

    public ExpenseAdapter(ArrayList<Expense> expenseList, OnExpenseChanged callback) {
        this.expenseList = expenseList;
        this.callback = callback;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textExpense;
        ViewHolder(View v) {
            super(v);
            textExpense = v.findViewById(android.R.id.text1);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        Expense ex = expenseList.get(pos);
        holder.textExpense.setText("₹" + ex.getAmount() + " • " + ex.getDescription() + "\n" + ex.getDateTime());


        // Edit on click
        holder.itemView.setOnClickListener(v -> showEditDialog(v, ex, pos));

        // Delete on long‑press
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Expense")
                    .setMessage("Are you sure you want to delete this expense?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        expenseList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        callback.refreshTotal();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });

    }

    private void showEditDialog(View v, Expense ex, int position) {
        View dialog = LayoutInflater.from(v.getContext())
                .inflate(R.layout.dialog_edit_expense, null, false);
        EditText edAmt  = dialog.findViewById(R.id.dialogAmount);
        EditText edDesc = dialog.findViewById(R.id.dialogDesc);

        edAmt.setText(String.valueOf(ex.getAmount()));
        edDesc.setText(ex.getDescription());

        new AlertDialog.Builder(v.getContext())
                .setTitle("Edit Expense")
                .setView(dialog)
                .setPositiveButton("Save", (d, which) -> {
                    String a = edAmt.getText().toString();
                    String dsc = edDesc.getText().toString();
                    if (!a.isEmpty() && !dsc.isEmpty()) {
                        ex.setAmount(Double.parseDouble(a));
                        ex.setDescription(dsc);
                        notifyItemChanged(position);
                        callback.refreshTotal();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public int getItemCount() { return expenseList.size(); }
}