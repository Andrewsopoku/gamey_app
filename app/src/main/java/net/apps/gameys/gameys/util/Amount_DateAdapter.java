package net.apps.gameys.gameys.util;

/**
 * Created by andrews on 9/11/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.apps.gameys.gameys.R;

import java.util.List;

public class Amount_DateAdapter extends RecyclerView.Adapter<Amount_DateAdapter.MyViewHolder> {


    private List<Amount_Date> amount_dateList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amount, date;

        public MyViewHolder(View view) {
            super(view);
            amount = (TextView) view.findViewById(R.id.amount);
            date = (TextView) view.findViewById(R.id.date);

        }
    }


    public Amount_DateAdapter(List<Amount_Date> amount_dateList1) {
        this.amount_dateList = amount_dateList1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.amount_date_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Amount_Date amount_date = amount_dateList.get(position);
        holder.amount.setText(amount_date.getAmount());
        holder.date.setText(amount_date.getDate());

    }

    @Override
    public int getItemCount() {
        return amount_dateList.size();
    }
}