package com.mobileclient.activity;

import java.util.Date;
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
public class RoomTypeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_roomTypeId;
	// 声明房间类型控件
	private TextView TV_roomTypeName;
	/* 要保存的房间类型信息 */
	RoomType roomType = new RoomType(); 
	/* 房间类型管理业务逻辑层 */
	private RoomTypeService roomTypeService = new RoomTypeService();
	private int roomTypeId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.roomtype_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看房间类型详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_roomTypeId = (TextView) findViewById(R.id.TV_roomTypeId);
		TV_roomTypeName = (TextView) findViewById(R.id.TV_roomTypeName);
		Bundle extras = this.getIntent().getExtras();
		roomTypeId = extras.getInt("roomTypeId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RoomTypeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    roomType = roomTypeService.GetRoomType(roomTypeId); 
		this.TV_roomTypeId.setText(roomType.getRoomTypeId() + "");
		this.TV_roomTypeName.setText(roomType.getRoomTypeName());
	} 
}
