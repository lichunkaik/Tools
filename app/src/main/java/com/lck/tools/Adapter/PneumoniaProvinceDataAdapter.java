package com.lck.tools.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lck.tools.Class.PneumoniaData.ProvinceData;
import com.lck.tools.R;

import java.util.ArrayList;

/**
 * created by lucky on 2020/2/13
 * 疫情实时数据适配器（省级）
 */
public class PneumoniaProvinceDataAdapter extends RecyclerView.Adapter<PneumoniaProvinceDataAdapter.ViewHolder> {

    private ArrayList<ProvinceData> mList;
    private OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout LinearLayout_main;
        private TextView textView_provinceName;//省
        private TextView textView_confirmedCount;//确诊人数
        private TextView textView_suspectedCount;//疑似病例
        private TextView textView_curedCount;//治愈病例
        private TextView textView_deadCount;//死亡病例


        public ViewHolder( View itemView) {
            super(itemView);
            LinearLayout_main = (LinearLayout)itemView.findViewById(R.id.LinearLayout_main);
            textView_provinceName = (TextView)itemView.findViewById(R.id.textView_provinceName);
            textView_confirmedCount = (TextView)itemView.findViewById(R.id.textView_confirmedCount);
            textView_suspectedCount = (TextView)itemView.findViewById(R.id.textView_suspectedCount);
            textView_curedCount = (TextView)itemView.findViewById(R.id.textView_curedCount);
            textView_deadCount = (TextView)itemView.findViewById(R.id.textView_deadCount);
        }
    }

    public PneumoniaProvinceDataAdapter(ArrayList<ProvinceData> dataList) {
        mList = dataList;
    }

    //第一步 定义接口（点击播放/菜单）
    public interface OnItemClickListener {
        void onClick(int position);
    }

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pneumoniaprovincedata,parent,false);
        ViewHolder holder =  new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        ProvinceData provinceData = mList.get(position);

        holder.textView_provinceName.setText(provinceData.getProvinceShortName());
        holder.textView_confirmedCount.setText(provinceData.getConfirmedCount()+"");
        holder.textView_suspectedCount.setText(provinceData.getSuspectedCount()+"");
        holder.textView_curedCount.setText(provinceData.getCuredCount()+"");
        holder.textView_deadCount.setText(provinceData.getDeadCount()+"");

        //点击评论内容，弹出操作
        holder.LinearLayout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}