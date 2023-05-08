package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.domain.RoomInfo;
import com.mobileclient.service.RoomInfoService;
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
public class OrderInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明订单编号控件
	private TextView TV_orderNumber;
	// 声明预订的房间控件
	private TextView TV_roomObj;
	// 声明预订的用户控件
	private TextView TV_userObj;
	// 声明预订开始时间控件
	private TextView TV_startTime;
	// 声明离开时间控件
	private TextView TV_endTime;
	// 声明订单金额控件
	private TextView TV_orderMoney;
	// 声明附加信息控件
	private TextView TV_orderMemo;
	// 声明下单时间控件
	private TextView TV_orderAddTime;
	/* 要保存的房间预订信息 */
	OrderInfo orderInfo = new OrderInfo(); 
	/* 房间预订管理业务逻辑层 */
	private OrderInfoService orderInfoService = new OrderInfoService();
	private RoomInfoService roomInfoService = new RoomInfoService();
	private UserInfoService userInfoService = new UserInfoService();
	private String orderNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看房间预订详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_orderNumber = (TextView) findViewById(R.id.TV_orderNumber);
		TV_roomObj = (TextView) findViewById(R.id.TV_roomObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_startTime = (TextView) findViewById(R.id.TV_startTime);
		TV_endTime = (TextView) findViewById(R.id.TV_endTime);
		TV_orderMoney = (TextView) findViewById(R.id.TV_orderMoney);
		TV_orderMemo = (TextView) findViewById(R.id.TV_orderMemo);
		TV_orderAddTime = (TextView) findViewById(R.id.TV_orderAddTime);
		Bundle extras = this.getIntent().getExtras();
		orderNumber = extras.getString("orderNumber");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderNumber); 
		this.TV_orderNumber.setText(orderInfo.getOrderNumber());
		RoomInfo roomObj = roomInfoService.GetRoomInfo(orderInfo.getRoomObj());
		this.TV_roomObj.setText(roomObj.getRoomNumber());
		UserInfo userObj = userInfoService.GetUserInfo(orderInfo.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_startTime.setText(orderInfo.getStartTime());
		this.TV_endTime.setText(orderInfo.getEndTime());
		this.TV_orderMoney.setText(orderInfo.getOrderMoney() + "");
		this.TV_orderMemo.setText(orderInfo.getOrderMemo());
		this.TV_orderAddTime.setText(orderInfo.getOrderAddTime());
	} 
}
