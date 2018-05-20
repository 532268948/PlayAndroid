package com.zust.playandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zust.playandroid.R;
import com.zust.playandroid.bean.NaviBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/18
 * 时 间： 14:09
 * 项 目： PlayAndroid
 * 描 述：
 */


public class NaviAdapter extends RecyclerView.Adapter<NaviAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private Context context;
    private List<NaviBean> naviBeanList;
    private int mPosition=0;
    private List<Integer> mActiveList=new ArrayList<>();

    public NaviAdapter(Context context, List<NaviBean> naviBeanList){
        this.context=context;
        this.naviBeanList=naviBeanList;
        for(int i=0;i<this.naviBeanList.size();i++){
            if (i==0){
                mActiveList.add(1);
            }else {
                mActiveList.add(0);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.navi_title_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (mActiveList.get(position)==1){
            holder.mTextView.setActivated(true);
        }else {
            holder.mTextView.setActivated(false);
        }
        holder.mTextView.setText(naviBeanList.get(position).getName());
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.mTextView.isActivated()){
                    mActiveList.set(mPosition,0);
                    notifyItemChanged(mPosition);
                    holder.mTextView.setActivated(true);
                    mPosition=position;
                }
                onItemClickListener.OnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return naviBeanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView=(TextView)itemView.findViewById(R.id.title);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnClick(int position);
    }

//    public void setActived(int position,int b){
//        mActiveList.set(mPosition,0);
//        mActiveList.set(position,b);
//        mPosition=position;
//    }
}
