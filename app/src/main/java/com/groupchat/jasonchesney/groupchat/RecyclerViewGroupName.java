package com.groupchat.jasonchesney.groupchat;

import android.content.Context;
import android.location.GnssNavigationMessage;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewGroupName extends RecyclerView.Adapter<RecyclerViewGroupName.ViewHolder> {



    private ArrayList<String> Gnames=new ArrayList<>();
    private ArrayList<String> Gnames12=new ArrayList<>();
    private Context mContext;


  public RecyclerViewGroupName(ArrayList<String> Gnames1,ArrayList<String> Gnames11,Context c1)
  {
         mContext=c1;
         Gnames=Gnames1;
         Gnames12=Gnames11;
  }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.editgroupnamecard,parent,false);
          ViewHolder vw=new ViewHolder(view);


      return vw;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
      holder.t1.setText(Gnames.get(position));
       holder.t2.setText(Gnames12.get(position));
      holder.t1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(mContext,Gnames.get(position),Toast.LENGTH_LONG).show();
          }
      });

    }

    @Override
    public int getItemCount() {
        return Gnames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

      TextView t1,t2;
      RelativeLayout r1;

      public ViewHolder(View itemView) {
          super(itemView);
         t1=(TextView) itemView.findViewById(R.id.t1);
         r1=(RelativeLayout) itemView.findViewById(R.id.r1);
         t2=(TextView) itemView.findViewById(R.id.t2);
      }
  }



}
