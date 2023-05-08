package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.RoomTypeService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class RoomInfoSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public RoomInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.roominfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_roomNumber = (TextView)convertView.findViewById(R.id.tv_roomNumber);
	  holder.tv_roomTypeObj = (TextView)convertView.findViewById(R.id.tv_roomTypeObj);
	  holder.tv_roomPrice = (TextView)convertView.findViewById(R.id.tv_roomPrice);
	  holder.tv_position = (TextView)convertView.findViewById(R.id.tv_position);
	  holder.iv_roomPhoto = (ImageView)convertView.findViewById(R.id.iv_roomPhoto);
	  /*设置各个控件的展示内容*/
	  holder.tv_roomNumber.setText("房间编号：" + mData.get(position).get("roomNumber").toString());
	  holder.tv_roomTypeObj.setText("房间类型：" + (new RoomTypeService()).GetRoomType(Integer.parseInt(mData.get(position).get("roomTypeObj").toString())).getRoomTypeName());
	  holder.tv_roomPrice.setText("价格(元/天)：" + mData.get(position).get("roomPrice").toString());
	  holder.tv_position.setText("所处位置：" + mData.get(position).get("position").toString());
	  holder.iv_roomPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener roomPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_roomPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("roomPhoto"),roomPhotoLoadListener);  
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_roomNumber;
    	TextView tv_roomTypeObj;
    	TextView tv_roomPrice;
    	TextView tv_position;
    	ImageView iv_roomPhoto;
    }
} 
