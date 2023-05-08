package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.MoneyRecord;
import com.mobileclient.service.MoneyRecordService;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class MoneyRecordAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明充值的用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*充值的用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明充值金额输入框
	private EditText ET_moneyValue;
	// 声明附加信息输入框
	private EditText ET_memo;
	// 声明充值时间输入框
	private EditText ET_happenTime;
	protected String carmera_path;
	/*要保存的充值信息信息*/
	MoneyRecord moneyRecord = new MoneyRecord();
	/*充值信息管理业务逻辑层*/
	private MoneyRecordService moneyRecordService = new MoneyRecordService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.moneyrecord_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加充值信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的充值的用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// 设置下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				moneyRecord.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_moneyValue = (EditText) findViewById(R.id.ET_moneyValue);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		ET_happenTime = (EditText) findViewById(R.id.ET_happenTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加充值信息按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取充值金额*/ 
					if(ET_moneyValue.getText().toString().equals("")) {
						Toast.makeText(MoneyRecordAddActivity.this, "充值金额输入不能为空!", Toast.LENGTH_LONG).show();
						ET_moneyValue.setFocusable(true);
						ET_moneyValue.requestFocus();
						return;	
					}
					moneyRecord.setMoneyValue(Float.parseFloat(ET_moneyValue.getText().toString()));
					/*验证获取附加信息*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(MoneyRecordAddActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					moneyRecord.setMemo(ET_memo.getText().toString());
					/*验证获取充值时间*/ 
					if(ET_happenTime.getText().toString().equals("")) {
						Toast.makeText(MoneyRecordAddActivity.this, "充值时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_happenTime.setFocusable(true);
						ET_happenTime.requestFocus();
						return;	
					}
					moneyRecord.setHappenTime(ET_happenTime.getText().toString());
					/*调用业务逻辑层上传充值信息信息*/
					MoneyRecordAddActivity.this.setTitle("正在上传充值信息信息，稍等...");
					String result = moneyRecordService.AddMoneyRecord(moneyRecord);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
