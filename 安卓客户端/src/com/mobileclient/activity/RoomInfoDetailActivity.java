package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.RoomInfo;
import com.mobileclient.service.RoomInfoService;
import com.mobileclient.domain.RoomType;
import com.mobileclient.service.RoomTypeService;
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
public class RoomInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明房间编号控件
	private TextView TV_roomNumber;
	// 声明房间类型控件
	private TextView TV_roomTypeObj;
	// 声明价格(元/天)控件
	private TextView TV_roomPrice;
	// 声明所处位置控件
	private TextView TV_position;
	// 声明房间介绍控件
	private TextView TV_introduction;
	// 声明房间照片图片框
	private ImageView iv_roomPhoto;
	/* 要保存的房间信息信息 */
	RoomInfo roomInfo = new RoomInfo(); 
	/* 房间信息管理业务逻辑层 */
	private RoomInfoService roomInfoService = new RoomInfoService();
	private RoomTypeService roomTypeService = new RoomTypeService();
	private String roomNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.roominfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看房间信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_roomNumber = (TextView) findViewById(R.id.TV_roomNumber);
		TV_roomTypeObj = (TextView) findViewById(R.id.TV_roomTypeObj);
		TV_roomPrice = (TextView) findViewById(R.id.TV_roomPrice);
		TV_position = (TextView) findViewById(R.id.TV_position);
		TV_introduction = (TextView) findViewById(R.id.TV_introduction);
		iv_roomPhoto = (ImageView) findViewById(R.id.iv_roomPhoto); 
		Bundle extras = this.getIntent().getExtras();
		roomNumber = extras.getString("roomNumber");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RoomInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    roomInfo = roomInfoService.GetRoomInfo(roomNumber); 
		this.TV_roomNumber.setText(roomInfo.getRoomNumber());
		RoomType roomTypeObj = roomTypeService.GetRoomType(roomInfo.getRoomTypeObj());
		this.TV_roomTypeObj.setText(roomTypeObj.getRoomTypeName());
		this.TV_roomPrice.setText(roomInfo.getRoomPrice() + "");
		this.TV_position.setText(roomInfo.getPosition());
		this.TV_introduction.setText(roomInfo.getIntroduction());
		byte[] roomPhoto_data = null;
		try {
			// 获取图片数据
			roomPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + roomInfo.getRoomPhoto());
			Bitmap roomPhoto = BitmapFactory.decodeByteArray(roomPhoto_data, 0,roomPhoto_data.length);
			this.iv_roomPhoto.setImageBitmap(roomPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
