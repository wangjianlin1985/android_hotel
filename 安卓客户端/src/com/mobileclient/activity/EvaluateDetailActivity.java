package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Evaluate;
import com.mobileclient.service.EvaluateService;
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
public class EvaluateDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明记录编号控件
	private TextView TV_evaluateId;
	// 声明评价的房间控件
	private TextView TV_roomObj;
	// 声明评价的用户控件
	private TextView TV_userObj;
	// 声明评分(5分制)控件
	private TextView TV_evalueScore;
	// 声明评价内容控件
	private TextView TV_evaluateContent;
	// 声明评价时间控件
	private TextView TV_evaluateTime;
	/* 要保存的评价信息信息 */
	Evaluate evaluate = new Evaluate(); 
	/* 评价信息管理业务逻辑层 */
	private EvaluateService evaluateService = new EvaluateService();
	private RoomInfoService roomInfoService = new RoomInfoService();
	private UserInfoService userInfoService = new UserInfoService();
	private int evaluateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.evaluate_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看评价信息详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_evaluateId = (TextView) findViewById(R.id.TV_evaluateId);
		TV_roomObj = (TextView) findViewById(R.id.TV_roomObj);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_evalueScore = (TextView) findViewById(R.id.TV_evalueScore);
		TV_evaluateContent = (TextView) findViewById(R.id.TV_evaluateContent);
		TV_evaluateTime = (TextView) findViewById(R.id.TV_evaluateTime);
		Bundle extras = this.getIntent().getExtras();
		evaluateId = extras.getInt("evaluateId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EvaluateDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    evaluate = evaluateService.GetEvaluate(evaluateId); 
		this.TV_evaluateId.setText(evaluate.getEvaluateId() + "");
		RoomInfo roomObj = roomInfoService.GetRoomInfo(evaluate.getRoomObj());
		this.TV_roomObj.setText(roomObj.getRoomNumber());
		UserInfo userObj = userInfoService.GetUserInfo(evaluate.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_evalueScore.setText(evaluate.getEvalueScore() + "");
		this.TV_evaluateContent.setText(evaluate.getEvaluateContent());
		this.TV_evaluateTime.setText(evaluate.getEvaluateTime());
	} 
}
