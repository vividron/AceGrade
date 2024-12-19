package com.example.sgpa.help;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sgpa.R;
import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpViewHolder> {

    private final List<HelpItem> helpItems;

    public HelpAdapter(List<HelpItem> helpItems) {
        this.helpItems = helpItems;
    }

    @NonNull
    @Override
    public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_help_question, parent, false);
        return new HelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpViewHolder holder, int position) {
        HelpItem item = helpItems.get(position);
        holder.questionTextView.setText(item.getQuestion());
        holder.answerTextView.setText(item.getAnswer());

        // Manage visibility
        holder.answerTextView.setVisibility(item.isExpanded() ? View.VISIBLE : View.GONE);

        // Toggle visibility on click
        holder.itemView.setOnClickListener(v -> {
            item.setExpanded(!item.isExpanded());
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return helpItems.size();
    }

    public static class HelpViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView, answerTextView;

        public HelpViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            answerTextView = itemView.findViewById(R.id.answerTextView);
        }
    }
}