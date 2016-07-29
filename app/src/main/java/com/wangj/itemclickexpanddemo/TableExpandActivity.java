package com.wangj.itemclickexpanddemo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.wangj.itemclickexpanddemo.bean.CommentListBean;
import com.wangj.itemclickexpanddemo.bean.ReplyBean;

public class TableExpandActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_expand);
		requestData();
	}
	
	private void requestData() {
		
		HashMap<String, String> accountInfo = new HashMap<String, String>();
		accountInfo.put("type", "数码产品");
		accountInfo.put("repayAccount", "622200******0000");
		
		ArrayList<CommentListBean> datas = new ArrayList<CommentListBean>();
		for(int i = 1; i <= 10; i++){
			CommentListBean listBean=new CommentListBean();
			ArrayList<ReplyBean> replyBeens=new ArrayList<ReplyBean>();
			for(int j=0;j<10;j++){
				ReplyBean replyBean=new ReplyBean();
				replyBean.setTarget("家长"+j+":");
				replyBean.setContent("多谢老师评价"+j);
				replyBeens.add(replyBean);
			}

			listBean.setReplyBeans(replyBeens);
			datas.add(listBean);
		}

		showData(accountInfo);
		
		ListView lvRepayInfo = (ListView) findViewById(R.id.lv_repayInfoList);
		TableExpandAdapter adapter = new TableExpandAdapter(this, datas);
		lvRepayInfo.setAdapter(adapter);
	}

	private void showData(HashMap<String, String> maps) {
		TextView tvType = (TextView) findViewById(R.id.tv_type);
		TextView tvRepayAccount = (TextView) findViewById(R.id.tv_repayAccount);
		
		if(maps != null){
			tvType.setText(maps.get("type"));
			tvRepayAccount.setText(maps.get("repayAccount"));
		}
	}

}
