package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.RoomInfo;
import com.mobileclient.service.RoomInfoService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class OrderInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明订单编号TextView
	private TextView TV_orderNumber;
	// 声明预订的房间下拉框
	private Spinner spinner_roomObj;
	private ArrayAdapter<String> roomObj_adapter;
	private static  String[] roomObj_ShowText  = null;
	private List<RoomInfo> roomInfoList = null;
	/*预订的房间管理业务逻辑层*/
	private RoomInfoService roomInfoService = new RoomInfoService();
	// 声明预订的用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*预订的用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明预订开始时间输入框
	private EditText ET_startTime;
	// 声明离开时间输入框
	private EditText ET_endTime;
	// 声明订单金额输入框
	private EditText ET_orderMoney;
	// 声明附加信息输入框
	private EditText ET_orderMemo;
	// 声明下单时间输入框
	private EditText ET_orderAddTime;
	protected String carmera_path;
	/*要保存的房间预订信息*/
	OrderInfo orderInfo = new OrderInfo();
	/*房间预订管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	private String orderNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑房间预订信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_orderNumber = (TextView) findViewById(R.id.TV_orderNumber);
		spinner_roomObj = (Spinner) findViewById(R.id.Spinner_roomObj);
		// 获取所有的预订的房间
		try {
			roomInfoList = roomInfoService.QueryRoomInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int roomInfoCount = roomInfoList.size();
		roomObj_ShowText = new String[roomInfoCount];
		for(int i=0;i<roomInfoCount;i++) { 
			roomObj_ShowText[i] = roomInfoList.get(i).getRoomNumber();
		}
		// 将可选内容与ArrayAdapter连接起来
		roomObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomObj_ShowText);
		// 设置图书类别下拉列表的风格
		roomObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_roomObj.setAdapter(roomObj_adapter);
		// 添加事件Spinner事件监听
		spinner_roomObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setRoomObj(roomInfoList.get(arg2).getRoomNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_roomObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的预订的用户
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
		// 设置图书类别下拉列表的风格
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_userObj.setAdapter(userObj_adapter);
		// 添加事件Spinner事件监听
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_startTime = (EditText) findViewById(R.id.ET_startTime);
		ET_endTime = (EditText) findViewById(R.id.ET_endTime);
		ET_orderMoney = (EditText) findViewById(R.id.ET_orderMoney);
		ET_orderMemo = (EditText) findViewById(R.id.ET_orderMemo);
		ET_orderAddTime = (EditText) findViewById(R.id.ET_orderAddTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		orderNumber = extras.getString("orderNumber");
		/*单击修改房间预订按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取预订开始时间*/ 
					if(ET_startTime.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "预订开始时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_startTime.setFocusable(true);
						ET_startTime.requestFocus();
						return;	
					}
					orderInfo.setStartTime(ET_startTime.getText().toString());
					/*验证获取离开时间*/ 
					if(ET_endTime.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "离开时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_endTime.setFocusable(true);
						ET_endTime.requestFocus();
						return;	
					}
					orderInfo.setEndTime(ET_endTime.getText().toString());
					/*验证获取订单金额*/ 
					if(ET_orderMoney.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "订单金额输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderMoney.setFocusable(true);
						ET_orderMoney.requestFocus();
						return;	
					}
					orderInfo.setOrderMoney(Float.parseFloat(ET_orderMoney.getText().toString()));
					/*验证获取附加信息*/ 
					if(ET_orderMemo.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "附加信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderMemo.setFocusable(true);
						ET_orderMemo.requestFocus();
						return;	
					}
					orderInfo.setOrderMemo(ET_orderMemo.getText().toString());
					/*验证获取下单时间*/ 
					if(ET_orderAddTime.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "下单时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_orderAddTime.setFocusable(true);
						ET_orderAddTime.requestFocus();
						return;	
					}
					orderInfo.setOrderAddTime(ET_orderAddTime.getText().toString());
					/*调用业务逻辑层上传房间预订信息*/
					OrderInfoEditActivity.this.setTitle("正在更新房间预订信息，稍等...");
					String result = orderInfoService.UpdateOrderInfo(orderInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderNumber);
		this.TV_orderNumber.setText(orderNumber);
		for (int i = 0; i < roomInfoList.size(); i++) {
			if (orderInfo.getRoomObj().equals(roomInfoList.get(i).getRoomNumber())) {
				this.spinner_roomObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < userInfoList.size(); i++) {
			if (orderInfo.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_startTime.setText(orderInfo.getStartTime());
		this.ET_endTime.setText(orderInfo.getEndTime());
		this.ET_orderMoney.setText(orderInfo.getOrderMoney() + "");
		this.ET_orderMemo.setText(orderInfo.getOrderMemo());
		this.ET_orderAddTime.setText(orderInfo.getOrderAddTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
