package com.doron.personalnotes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Notes, NoteAdapter.NoteViewHolder> {
    Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Notes> options, MainActivity context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull Notes notes) {
        holder.txt_title.setText(notes.title);
        holder.txt_content.setText(notes.content);
        holder.txt_time.setText(Utility.TimeConv(notes.time));

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context, NotesDetails.class);
            intent.putExtra("title",notes.title);
            intent.putExtra("content",notes.content);
            String docId = getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);

            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_content, txt_time;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.title_recycle);
            txt_content = itemView.findViewById(R.id.content_recycle);
            txt_time = itemView.findViewById(R.id.time_recycle);
        }
    }
}
