package com.example.familybook.utils;
/**
 * 这是查看总览账目的列表帮助类
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.familybook.R;
import com.example.familybook.entity.Bill;

import java.util.List;

public class ShowBillsAdapter extends BaseAdapter {
    private List<Bill> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ShowBillsAdapter(List<Bill> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 返回适配器中数据集中的个数
     * @return
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * 获取数据数据集中与指定索引对应的数据项
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    /**
     * 获取指定行对应的ID
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            /**
             * 将表单的标题与布局中的控件对应起来
             */
            viewHolder=new ViewHolder();
            convertView=mLayoutInflater.inflate(R.layout.activity_queryallbill,null);
            viewHolder.mBillType=(TextView)convertView.findViewById(R.id.show_bill_type);
            viewHolder.mBillMoney=(TextView)convertView.findViewById(R.id.show_bill_money);
            viewHolder.mBillDate=(TextView)convertView.findViewById(R.id.show_bill_date);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        //获取一个bill实体类，将bill的数据封装到表格的内容中

        Bill bill=mList.get(position);
        //这里获取数据返回给布局
        viewHolder.mBillType.setText(bill.getType());
        viewHolder.mBillMoney.setText(bill.getMoney());
        viewHolder.mBillDate.setText(bill.getDate());

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bill bill=mList.get(position);
//                int bill_id=bill.get_id();
//                String username=bill.getUsername();
//                Intent intent=new Intent();
//                intent.putExtra("username",username);
//                intent.putExtra("bill_id",bill_id);
//                intent.setClass(mContext, InfoActivity.class);
//                mContext.startActivity(intent);
//            }
//        });


        return convertView;
    }
}
