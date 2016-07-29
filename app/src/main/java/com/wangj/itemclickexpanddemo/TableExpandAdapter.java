package com.wangj.itemclickexpanddemo;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.wangj.itemclickexpanddemo.bean.CommentListBean;

/**
 * 点击item展开隐藏部分,再次点击收起
 * 只可展开一条记录
 *
 * @author WangJ
 */
public class TableExpandAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CommentListBean> list;
    private int currentItem = -1; //用于记录点击的 Item 的 position
    private static final int MAX_COUNT =5;

    public TableExpandAdapter(Context context,
                              ArrayList<CommentListBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId( int position) {
        return position;
    }

    ViewHolder holder = null;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_1, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CommentListBean item = list.get(position);

        setItemCountVisible(item);

        // 注意：我们在此给响应点击事件的区域（我的例子里是 table 布局）添加Tag，为了记录点击的 position，我们正好用 position 设置 Tag
//        holder.table.setTag(position);

        //根据 currentItem 记录的点击位置设置"对应Item"的可见性
        if (currentItem == position) {

            Log.e(">>>>>","position:"+position);
            setViewsVisibility(((ArrayList<View>)holder.table.getTag()), true);
            holder.imgMore.setVisibility(View.GONE); //item展开时让箭头不可见
            currentItem=-1;
        }else {
            setViewsVisibility(((ArrayList<View>)holder.table.getTag()), false);
            holder.imgMore.setVisibility(View.VISIBLE); //item展开时让箭头不可见
        }

        holder.table.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                //用 currentItem 记录点击位置
                if(position != currentItem){
                    currentItem=position;
                }

                for(int i=MAX_COUNT;i<list.get(position).getReplyBeans().size();i++){
                    View item=LayoutInflater.from(context).inflate(R.layout.item_table_row,null);
                    TextView tv_target = (TextView) item.findViewById(R.id.tv_target);
                    TextView tv_content = (TextView) item.findViewById(R.id.tv_content);
                    tv_target.setText(list.get(position).getReplyBeans().get(i).getTarget());
                    tv_content.setText(list.get(position).getReplyBeans().get(i).getContent());
                    ((TableLayout)view).addView(item);
                    ((ArrayList<View>)holder.table.getTag()).add(item);
                }

                view.setEnabled(false);

                //通知adapter数据改变需要重新加载
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    /**
     * 目前是最多显示MAX_COUNT=5条，要覆盖所有小于5的情况
     * @param item
     */
    private void setItemCountVisible(CommentListBean item){

        int count=item.getReplyBeans().size();

        if (count>=MAX_COUNT){
            setVisibility(MAX_COUNT,item);
            holder.imgMore.setVisibility(View.VISIBLE);
        }else if(count==(MAX_COUNT-1)){
            setVisibility((MAX_COUNT-1),item);
            holder.imgMore.setVisibility(View.GONE);
        }else if(count==(MAX_COUNT-2)){
            setVisibility((MAX_COUNT-2),item);
            holder.imgMore.setVisibility(View.GONE);
        }else if(count==(MAX_COUNT-3)){
            setVisibility((MAX_COUNT-3),item);
            holder.imgMore.setVisibility(View.GONE);
        }else if(count==(MAX_COUNT-4)){
            setVisibility((MAX_COUNT-4),item);
            holder.imgMore.setVisibility(View.GONE);
        }
    }

    private void setVisibility(int count,CommentListBean item){
        for(int i=0;i<MAX_COUNT;i++){
            if(i<count){
                holder.rowList.get(i).setVisibility(View.VISIBLE);
            }else{
                holder.rowList.get(i).setVisibility(View.GONE);
            }
        }

        for(int i=0;i<count;i++){
            holder.targetList.get(i).setText(item.getReplyBeans().get(i).getTarget());
            holder.contentList.get(i).setText(item.getReplyBeans().get(i).getContent());
        }
    }

    /**
     * 一次性设置一系列控件的可见性
     *
     * @param views      ArrayList<View>类型，要设置可见性的控件封装
     * @param visivility boolean类型，true表示可见，false表示不可见
     */
    private void setViewsVisibility(ArrayList<View> views, boolean visivility) {
        for (View view : views) {
            view.setVisibility(visivility ? View.VISIBLE : View.GONE);
        }
    }

    private static class ViewHolder {
        private TableLayout table;

        private TextView tvTarget01;
        private TextView tvTarget02;
        private TextView tvTarget03;
        private TextView tvTarget04;
        private TextView tvTarget05;

        private TextView tvContent01;
        private TextView tvContent02;
        private TextView tvContent03;
        private TextView tvContent04;
        private TextView tvContent05;

        //** 需要隐藏控件——开始 **
        private View splitLine1;
        private TableRow tablerow01;
        private View splitLine2;
        private TableRow tablerow02;
        private View splitLine3;
        private TableRow tablerow03;
        private TableRow tablerow04;
        private TableRow tablerow05;
        //** 需要隐藏控件——结束 **
        private ArrayList<View> hideViews = new ArrayList<View>(); //用来封装隐藏的控件，使便于管理
        private ArrayList<View> rowList = new ArrayList<View>();
        private ArrayList<TextView> contentList = new ArrayList<TextView>();
        private ArrayList<TextView> targetList = new ArrayList<TextView>();

        private TextView imgMore; //向下展开的箭头

        ViewHolder(View convertView) {
            table = (TableLayout) convertView.findViewById(R.id.table);

            tvTarget01 = (TextView) convertView .findViewById(R.id.tv01);
            tvTarget02 = (TextView) convertView .findViewById(R.id.tv02);
            tvTarget03 = (TextView) convertView .findViewById(R.id.tv03);
            tvTarget04 = (TextView) convertView .findViewById(R.id.tv04);
            tvTarget05 = (TextView) convertView .findViewById(R.id.tv05);

            tvContent01 = (TextView) convertView .findViewById(R.id.tv_repayCycle);
            tvContent02 = (TextView) convertView.findViewById(R.id.tv_total);
            tvContent03 = (TextView) convertView .findViewById(R.id.tv_repayDate);
            tvContent04 = (TextView) convertView.findViewById(R.id.tv_notRepayPrincipal);
            tvContent05 = (TextView) convertView.findViewById(R.id.tv_notRepayInterest);
            imgMore = (TextView) convertView.findViewById(R.id.img_more);
            table.setTag(hideViews);

            //** 把要隐藏的控件"装起来"——开始 **
            splitLine1 = convertView.findViewById(R.id.splitLine1);
            tablerow01 = (TableRow) convertView.findViewById(R.id.tablerow01);
            splitLine2 = convertView.findViewById(R.id.splitLine2);
            tablerow02 = (TableRow) convertView.findViewById(R.id.tablerow02);
            splitLine3 = convertView.findViewById(R.id.splitLine3);
            tablerow03 = (TableRow) convertView .findViewById(R.id.tablerow03);
            tablerow04 = (TableRow) convertView .findViewById(R.id.tablerow04);
            tablerow05 = (TableRow) convertView .findViewById(R.id.tablerow05);

            rowList.add(tablerow01);
            rowList.add(tablerow02);
            rowList.add(tablerow03);
            rowList.add(tablerow04);
            rowList.add(tablerow05);

            contentList.add(tvContent01);
            contentList.add(tvContent02);
            contentList.add(tvContent03);
            contentList.add(tvContent04);
            contentList.add(tvContent05);

            targetList.add(tvTarget01);
            targetList.add(tvTarget02);
            targetList.add(tvTarget03);
            targetList.add(tvTarget04);
            targetList.add(tvTarget05);

        }
    }
}
