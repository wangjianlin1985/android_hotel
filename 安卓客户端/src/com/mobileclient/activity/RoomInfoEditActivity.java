package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.RoomInfo;
import com.mobileclient.service.RoomInfoService;
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

public class RoomInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明房间编号TextView
	private TextView TV_roomNumber;
	// 声明房间类型下拉框
	private Spinner spinner_roomTypeObj;
	private ArrayAdapter<String> roomTypeObj_adapter;
	private static  String[] roomTypeObj_ShowText  = null;
	private List<RoomType> roomTypeList = null;
	/*房间类型管理业务逻辑层*/
	private RoomTypeService roomTypeService = new RoomTypeService();
	// 声明价格(元/天)输入框
	private EditText ET_roomPrice;
	// 声明所处位置输入框
	private EditText ET_position;
	// 声明房间介绍输入框
	private EditText ET_introduction;
	// 声明房间照片图片框控件
	private ImageView iv_roomPhoto;
	private Button btn_roomPhoto;
	protected int REQ_CODE_SELECT_IMAGE_roomPhoto = 1;
	private int REQ_CODE_CAMERA_roomPhoto = 2;
	protected String carmera_path;
	/*要保存的房间信息信息*/
	RoomInfo roomInfo = new RoomInfo();
	/*房间信息管理业务逻辑层*/
	private RoomInfoService roomInfoService = new RoomInfoService();

	private String roomNumber;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.roominfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑房间信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_roomNumber = (TextView) findViewById(R.id.TV_roomNumber);
		spinner_roomTypeObj = (Spinner) findViewById(R.id.Spinner_roomTypeObj);
		// 获取所有的房间类型
		try {
			roomTypeList = roomTypeService.QueryRoomType(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int roomTypeCount = roomTypeList.size();
		roomTypeObj_ShowText = new String[roomTypeCount];
		for(int i=0;i<roomTypeCount;i++) { 
			roomTypeObj_ShowText[i] = roomTypeList.get(i).getRoomTypeName();
		}
		// 将可选内容与ArrayAdapter连接起来
		roomTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomTypeObj_ShowText);
		// 设置图书类别下拉列表的风格
		roomTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_roomTypeObj.setAdapter(roomTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_roomTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				roomInfo.setRoomTypeObj(roomTypeList.get(arg2).getRoomTypeId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_roomTypeObj.setVisibility(View.VISIBLE);
		ET_roomPrice = (EditText) findViewById(R.id.ET_roomPrice);
		ET_position = (EditText) findViewById(R.id.ET_position);
		ET_introduction = (EditText) findViewById(R.id.ET_introduction);
		iv_roomPhoto = (ImageView) findViewById(R.id.iv_roomPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_roomPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RoomInfoEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_roomPhoto);
			}
		});
		btn_roomPhoto = (Button) findViewById(R.id.btn_roomPhoto);
		btn_roomPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_roomPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_roomPhoto);  
			}
		});
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		roomNumber = extras.getString("roomNumber");
		/*单击修改房间信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取价格(元/天)*/ 
					if(ET_roomPrice.getText().toString().equals("")) {
						Toast.makeText(RoomInfoEditActivity.this, "价格(元/天)输入不能为空!", Toast.LENGTH_LONG).show();
						ET_roomPrice.setFocusable(true);
						ET_roomPrice.requestFocus();
						return;	
					}
					roomInfo.setRoomPrice(Float.parseFloat(ET_roomPrice.getText().toString()));
					/*验证获取所处位置*/ 
					if(ET_position.getText().toString().equals("")) {
						Toast.makeText(RoomInfoEditActivity.this, "所处位置输入不能为空!", Toast.LENGTH_LONG).show();
						ET_position.setFocusable(true);
						ET_position.requestFocus();
						return;	
					}
					roomInfo.setPosition(ET_position.getText().toString());
					/*验证获取房间介绍*/ 
					if(ET_introduction.getText().toString().equals("")) {
						Toast.makeText(RoomInfoEditActivity.this, "房间介绍输入不能为空!", Toast.LENGTH_LONG).show();
						ET_introduction.setFocusable(true);
						ET_introduction.requestFocus();
						return;	
					}
					roomInfo.setIntroduction(ET_introduction.getText().toString());
					if (!roomInfo.getRoomPhoto().startsWith("upload/")) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						RoomInfoEditActivity.this.setTitle("正在上传图片，稍等...");
						String roomPhoto = HttpUtil.uploadFile(roomInfo.getRoomPhoto());
						RoomInfoEditActivity.this.setTitle("图片上传完毕！");
						roomInfo.setRoomPhoto(roomPhoto);
					} 
					/*调用业务逻辑层上传房间信息信息*/
					RoomInfoEditActivity.this.setTitle("正在更新房间信息信息，稍等...");
					String result = roomInfoService.UpdateRoomInfo(roomInfo);
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
	    roomInfo = roomInfoService.GetRoomInfo(roomNumber);
		this.TV_roomNumber.setText(roomNumber);
		for (int i = 0; i < roomTypeList.size(); i++) {
			if (roomInfo.getRoomTypeObj() == roomTypeList.get(i).getRoomTypeId()) {
				this.spinner_roomTypeObj.setSelection(i);
				break;
			}
		}
		this.ET_roomPrice.setText(roomInfo.getRoomPrice() + "");
		this.ET_position.setText(roomInfo.getPosition());
		this.ET_introduction.setText(roomInfo.getIntroduction());
		byte[] roomPhoto_data = null;
		try {
			// 获取图片数据
			roomPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + roomInfo.getRoomPhoto());
			Bitmap roomPhoto = BitmapFactory.decodeByteArray(roomPhoto_data, 0, roomPhoto_data.length);
			this.iv_roomPhoto.setImageBitmap(roomPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_roomPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_roomPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_roomPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_roomPhoto.setImageBitmap(booImageBm);
				this.iv_roomPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.roomInfo.setRoomPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_roomPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_roomPhoto.setImageBitmap(bm); 
				this.iv_roomPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			roomInfo.setRoomPhoto(filename); 
		}
	}
}
