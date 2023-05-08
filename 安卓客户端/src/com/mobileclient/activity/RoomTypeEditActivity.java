package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.RoomType;
import com.mobileclient.service.RoomTypeService;
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

public class RoomTypeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_roomTypeId;
	// 声明房间类型输入框
	private EditText ET_roomTypeName;
	protected String carmera_path;
	/*要保存的房间类型信息*/
	RoomType roomType = new RoomType();
	/*房间类型管理业务逻辑层*/
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
		setContentView(R.layout.roomtype_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑房间类型信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_roomTypeId = (TextView) findViewById(R.id.TV_roomTypeId);
		ET_roomTypeName = (EditText) findViewById(R.id.ET_roomTypeName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		roomTypeId = extras.getInt("roomTypeId");
		/*单击修改房间类型按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取房间类型*/ 
					if(ET_roomTypeName.getText().toString().equals("")) {
						Toast.makeText(RoomTypeEditActivity.this, "房间类型输入不能为空!", Toast.LENGTH_LONG).show();
						ET_roomTypeName.setFocusable(true);
						ET_roomTypeName.requestFocus();
						return;	
					}
					roomType.setRoomTypeName(ET_roomTypeName.getText().toString());
					/*调用业务逻辑层上传房间类型信息*/
					RoomTypeEditActivity.this.setTitle("正在更新房间类型信息，稍等...");
					String result = roomTypeService.UpdateRoomType(roomType);
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
	    roomType = roomTypeService.GetRoomType(roomTypeId);
		this.TV_roomTypeId.setText(roomTypeId+"");
		this.ET_roomTypeName.setText(roomType.getRoomTypeName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
