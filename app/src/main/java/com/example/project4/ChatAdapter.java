package com.example.project4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project4.databinding.ChatViewBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context;
    ArrayList<Map<String, String>> chatList;


    public ChatAdapter(Context context, ArrayList<Map<String, String>> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new ViewHolder(ChatViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String epoch = chatList.get(position).get("time");
        Date date = new Date(Long.parseLong(epoch));
        String strDateFormat = "h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String time = sdf.format(date).toLowerCase();


        if (chatList.get(position).containsKey("image")) {


            holder.binding.messageText.setVisibility(View.GONE);
            holder.binding.messageUser.setText(chatList.get(position).get("username"));
            Glide.with(context).load(chatList.get(position).get("profile")).into(holder.binding.profileImage);
            holder.binding.messageTime.setText(time);
            Glide.with(context).load(chatList.get(position).get("image")).into(holder.binding.messageImage);
            holder.binding.messageImage.setVisibility(View.VISIBLE);


        } else {

            holder.binding.messageText.setVisibility(View.VISIBLE);
            holder.binding.messageUser.setText(chatList.get(position).get("username"));
            holder.binding.messageText.setText(chatList.get(position).get("text"));
            holder.binding.messageTime.setText(time);
            holder.binding.messageImage.setVisibility(View.GONE);
            Glide.with(context).load(chatList.get(position).get("profile")).into(holder.binding.profileImage);

        }


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ChatViewBinding binding;

        public ViewHolder(ChatViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
