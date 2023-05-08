package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Evaluate;
import com.mobileclient.service.EvaluateService;
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

public class EvaluateEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明记录编号TextView
	private TextView TV_evaluateId;
	// 声明评价的房间下拉框
	private Spinner spinner_roomObj;
	private ArrayAdapter<String> roomObj_adapter;
	private static  String[] roomObj_ShowText  = null;
	private List<RoomInfo> roomInfoList = null;
	/*评价的房间管理业务逻辑层*/
	private RoomInfoService roomInfoService = new RoomInfoService();
	// 声明评价的用户下拉框
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*评价的用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	// 声明评分(5分制)输入框
	private EditText ET_evalueScore;
	// 声明评价内容输入框
	private EditText ET_evaluateContent;
	// 声明评价时间输入框
	private EditText ET_evaluateTime;
	protected String carmera_path;
	/*要保存的评价信息信息*/
	Evaluate evaluate = new Evaluate();
	/*评价信息管理业务逻辑层*/
	private EvaluateService evaluateService = new EvaluateService();

	private int evaluateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.evaluate_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑评价信息信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_evaluateId = (TextView) findViewById(R.id.TV_evaluateId);
		spinner_roomObj = (Spinner) findViewById(R.id.Spinner_roomObj);
		// 获取所有的评价的房间
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
				evaluate.setRoomObj(roomInfoList.get(arg2).getRoomNumber()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_roomObj.setVisibility(View.VISIBLE);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// 获取所有的评价的用户
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
				evaluate.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_evalueScore = (EditText) findViewById(R.id.ET_evalueScore);
		ET_evaluateContent = (EditText) findViewById(R.id.ET_evaluateContent);
		ET_evaluateTime = (EditText) findViewById(R.id.ET_evaluateTime);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		evaluateId = extras.getInt("evaluateId");
		/*单击修改评价信息按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取评分(5分制)*/ 
					if(ET_evalueScore.getText().toString().equals("")) {
						Toast.makeText(EvaluateEditActivity.this, "评分(5分制)输入不能为空!", Toast.LENGTH_LONG).show();
						ET_evalueScore.setFocusable(true);
						ET_evalueScore.requestFocus();
						return;	
					}
					evaluate.setEvalueScore(Integer.parseInt(ET_evalueScore.getText().toString()));
					/*验证获取评价内容*/ 
					if(ET_evaluateContent.getText().toString().equals("")) {
						Toast.makeText(EvaluateEditActivity.this, "评价内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_evaluateContent.setFocusable(true);
						ET_evaluateContent.requestFocus();
						return;	
					}
					evaluate.setEvaluateContent(ET_evaluateContent.getText().toString());
					/*验证获取评价时间*/ 
					if(ET_evaluateTime.getText().toString().equals("")) {
						Toast.makeText(EvaluateEditActivity.this, "评价时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_evaluateTime.setFocusable(true);
						ET_evaluateTime.requestFocus();
						return;	
					}
					evaluate.setEvaluateTime(ET_evaluateTime.getText().toString());
					/*调用业务逻辑层上传评价信息信息*/
					EvaluateEditActivity.this.setTitle("正在更新评价信息信息，稍等...");
					String result = evaluateService.UpdateEvaluate(evaluate);
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
	    evaluate = evaluateService.GetEvaluate(evaluateId);
		this.TV_evaluateId.setText(evaluateId+"");
		for (int i = 0; i < roomInfoList.size(); i++) {
			if (evaluate.getRoomObj().equals(roomInfoList.get(i).getRoomNumber())) {
				this.spinner_roomObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < userInfoList.size(); i++) {
			if (evaluate.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_evalueScore.setText(evaluate.getEvalueScore() + "");
		this.ET_evaluateContent.setText(evaluate.getEvaluateContent());
		this.ET_evaluateTime.setText(evaluate.getEvaluateTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
