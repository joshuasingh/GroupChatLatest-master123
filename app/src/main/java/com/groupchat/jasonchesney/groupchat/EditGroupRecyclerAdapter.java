package com.groupchat.jasonchesney.groupchat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditGroupRecyclerAdapter extends FirebaseRecyclerAdapter<EditModel, EditGroupRecyclerAdapter.EditGroupViewHolder> {

    private OnItemClickListener listener;
    private deleteItem lisdel;

    public EditGroupRecyclerAdapter(@NonNull FirebaseRecyclerOptions<EditModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EditGroupViewHolder holder, int position, @NonNull EditModel model) {

        holder.regroupname.setText(model.getNewgrouptitle());
    }

    @NonNull
    @Override
    public EditGroupViewHolder onCreateViewHolder(@NonNull ViewGroup vvvgrp, int viewType) {
        View view = LayoutInflater.from(vvvgrp.getContext())
                .inflate(R.layout.editgroup_recyclerview, vvvgrp, false);
        return new EditGroupViewHolder(view);
    }

    public void delItem(int position){
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }

    class EditGroupViewHolder extends RecyclerView.ViewHolder{

        TextView regroupname;
        //CircleImageView regroupimage;
        LinearLayout dell;

        public EditGroupViewHolder(View itemview) {
            super(itemview);

            regroupname = itemView.findViewById(R.id.sgroup_name);
            String a ="dsfsd";
            String v="sdfdsf";

            //regroupimage = itemView.findViewById(R.id.sowners_group_image);
            //dell = itemview.findViewById(R.id.dellay);

//            itemview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION && listener != null){
//                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
//                    }
//                }
//            });

//            dell.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    if(pos != RecyclerView.NO_POSITION && lisdel != null) {
//                        lisdel.onItemDelete(getSnapshots().getSnapshot(pos));
//                    }
//                }
//            });

        }
    }


    public interface OnItemClickListener{
        void onItemClick(DataSnapshot dataSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public interface deleteItem{
        void onItemDelete(DataSnapshot dataSnapshot);
    }
    public void setItemDeleteListener(deleteItem listener){

        this.lisdel = listener;
    }
}
