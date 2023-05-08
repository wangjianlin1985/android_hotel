package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.MoneyRecord;
import com.mobileclient.service.MoneyRecordService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class MoneyRecordDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_recordId;
	// 声明充值的用户控件
	private TextView TV_userObj;
	// 声明充值金额控件
	private TextView TV_moneyValue;
	// 声明附加信息控件
	private TextView TV_memo;
	// 声明充值时间控件
	private TextView TV_happenTime;
	/* 要保存的充值信息信息 */
	MoneyRecord moneyRecord = new MoneyRecord(); 
	/* 充值信息管理业务逻辑层 */
	private MoneyRecordService moneyRecordService = new MoneyRecordService();
	private UserInfoService userInfoService = new UserInfoService();
	private int recordId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.moneyrecord_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看充值信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_recordId = (TextView) findViewById(R.id.TV_recordId);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_moneyValue = (TextView) findViewById(R.id.TV_moneyValue);
		TV_memo = (TextView) findViewById(R.id.TV_memo);
		TV_happenTime = (TextView) findViewById(R.id.TV_happenTime);
		Bundle extras = this.getIntent().getExtras();
		recordId = extras.getInt("recordId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MoneyRecordDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    moneyRecord = moneyRecordService.GetMoneyRecord(recordId); 
		this.TV_recordId.setText(moneyRecord.getRecordId() + "");
		UserInfo userObj = userInfoService.GetUserInfo(moneyRecord.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_moneyValue.setText(moneyRecord.getMoneyValue() + "");
		this.TV_memo.setText(moneyRecord.getMemo());
		this.TV_happenTime.setText(moneyRecord.getHappenTime());
	} 
}
