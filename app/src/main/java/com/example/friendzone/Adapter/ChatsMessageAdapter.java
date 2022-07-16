package com.example.friendzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendzone.Model.ChatsMessageModel;
import com.example.friendzone.R;

import java.util.ArrayList;

public class ChatsMessageAdapter extends RecyclerView.Adapter<ChatsMessageAdapter.MyViewHolder> {
    Context context;
    ArrayList<ChatsMessageModel> arrayList;
    int i = 0;
    String receiverId;

    public ChatsMessageAdapter(Context context, ArrayList<ChatsMessageModel> arrayList, String receiverId) {
        this.context = context;
        this.arrayList = arrayList;
        this.receiverId = receiverId;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_container_receive_message, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatsMessageModel model = arrayList.get(position);
        if (model.SendId.equals(receiverId)) {
            holder.message.setText(model.message);

            holder.message1.setVisibility(View.GONE);

        } else {
            holder.message1.setText(model.message);

            holder.message.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView message1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.tv_send_message);

            message1 = itemView.findViewById(R.id.tv_send_message2);


        }
    }
}
