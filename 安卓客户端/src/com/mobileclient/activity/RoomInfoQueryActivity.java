package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.RoomInfo;
import com.mobileclient.domain.RoomType;
import com.mobileclient.service.RoomTypeService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class RoomInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明房间编号输入框
	private EditText ET_roomNumber;
	// 声明房间类型下拉框
	private Spinner spinner_roomTypeObj;
	private ArrayAdapter<String> roomTypeObj_adapter;
	private static  String[] roomTypeObj_ShowText  = null;
	private List<RoomType> roomTypeList = null; 
	/*房间类型管理业务逻辑层*/
	private RoomTypeService roomTypeService = new RoomTypeService();
	// 声明所处位置输入框
	private EditText ET_position;
	// 声明房间介绍输入框
	private EditText ET_introduction;
	/*查询过滤条件保存到这个对象中*/
	private RoomInfo queryConditionRoomInfo = new RoomInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.roominfo_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置房间信息查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_roomNumber = (EditText) findViewById(R.id.ET_roomNumber);
		spinner_roomTypeObj = (Spinner) findViewById(R.id.Spinner_roomTypeObj);
		// 获取所有的房间类型
		try {
			roomTypeList = roomTypeService.QueryRoomType(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int roomTypeCount = roomTypeList.size();
		roomTypeObj_ShowText = new String[roomTypeCount+1];
		roomTypeObj_ShowText[0] = "不限制";
		for(int i=1;i<=roomTypeCount;i++) { 
			roomTypeObj_ShowText[i] = roomTypeList.get(i-1).getRoomTypeName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		roomTypeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, roomTypeObj_ShowText);
		// 设置房间类型下拉列表的风格
		roomTypeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_roomTypeObj.setAdapter(roomTypeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_roomTypeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionRoomInfo.setRoomTypeObj(roomTypeList.get(arg2-1).getRoomTypeId()); 
				else
					queryConditionRoomInfo.setRoomTypeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_roomTypeObj.setVisibility(View.VISIBLE);
		ET_position = (EditText) findViewById(R.id.ET_position);
		ET_introduction = (EditText) findViewById(R.id.ET_introduction);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionRoomInfo.setRoomNumber(ET_roomNumber.getText().toString());
					queryConditionRoomInfo.setPosition(ET_position.getText().toString());
					queryConditionRoomInfo.setIntroduction(ET_introduction.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionRoomInfo", queryConditionRoomInfo);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
