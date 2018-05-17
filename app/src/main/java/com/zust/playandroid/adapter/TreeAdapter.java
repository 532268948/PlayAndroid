package com.zust.playandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zust.playandroid.R;
import com.zust.playandroid.bean.KnowledgeTreeBean;
import com.zust.playandroid.utils.CommonUtil;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/17
 * 时 间： 19:24
 * 项 目： PlayAndroid
 * 描 述：
 */


public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHolder> {

    private Context context;
    private List<KnowledgeTreeBean> knowledgeTreeBeanList;

    public TreeAdapter(Context context,List<KnowledgeTreeBean> knowledgeTreeBeanList){
        this.context=context;
        this.knowledgeTreeBeanList=knowledgeTreeBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView.setText(knowledgeTreeBeanList.get(position).getName());
        holder.mTextView.setTextColor(CommonUtil.randomColor());
        String itemTitle="";
        for(int i=0;i<knowledgeTreeBeanList.get(position).getChildren().size();i++){
            itemTitle=itemTitle+knowledgeTreeBeanList.get(position).getChildren().get(i).getName()+"   ";
        }
        holder.mItemTextView.setText(itemTitle);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return knowledgeTreeBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        TextView mTextView;
        TextView mItemTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView=(CardView)itemView.findViewById(R.id.card_view);
            mTextView=(TextView)itemView.findViewById(R.id.title);
            mItemTextView=(TextView)itemView.findViewById(R.id.item_title);
        }
    }
}
