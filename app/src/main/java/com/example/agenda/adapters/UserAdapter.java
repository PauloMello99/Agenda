package com.example.agenda.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_user,viewGroup,false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        User user = userList.get(i);
        String[] name = user.getName().trim().split(" ");
        viewHolder.nameTextView.setText(name[0]);
        viewHolder.phoneTextView.setText(user.getPhone());
        if(user.getPhotoUri() != null){
            Bitmap bitmap = BitmapFactory.decodeFile(user.getPhotoUri());
            Bitmap redux = Bitmap.createScaledBitmap(bitmap,300,300,true);
            viewHolder.imageView.setImageBitmap(redux);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private Context context;

        @BindView(R.id.card_name)
        TextView nameTextView;

        @BindView(R.id.card_phone)
        TextView phoneTextView;

        @BindView(R.id.card_image)
        ImageView imageView;

        @BindView(R.id.card_view)
        CardView cardView;

        private ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            ButterKnife.bind(this,itemView);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(context.getString(R.string.options));
            menu.add(this.getAdapterPosition(),0,0,context.getString(R.string.call));
            menu.add(this.getAdapterPosition(),1,0,context.getString(R.string.send_sms));
            menu.add(this.getAdapterPosition(),2,0,context.getString(R.string.see_maps));
            menu.add(this.getAdapterPosition(),3,0,context.getString(R.string.go_to_site));
            menu.add(this.getAdapterPosition(),4,0,context.getString(R.string.edit));
            menu.add(this.getAdapterPosition(),5,0,context.getString(R.string.delete));
        }
    }
}