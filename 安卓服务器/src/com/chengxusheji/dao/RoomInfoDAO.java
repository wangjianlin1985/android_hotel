package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.RoomType;
import com.chengxusheji.domain.RoomInfo;

@Service @Transactional
public class RoomInfoDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddRoomInfo(RoomInfo roomInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(roomInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RoomInfo> QueryRoomInfoInfo(String roomNumber,RoomType roomTypeObj,String position,String introduction,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RoomInfo roomInfo where 1=1";
    	if(!roomNumber.equals("")) hql = hql + " and roomInfo.roomNumber like '%" + roomNumber + "%'";
    	if(null != roomTypeObj && roomTypeObj.getRoomTypeId()!=0) hql += " and roomInfo.roomTypeObj.roomTypeId=" + roomTypeObj.getRoomTypeId();
    	if(!position.equals("")) hql = hql + " and roomInfo.position like '%" + position + "%'";
    	if(!introduction.equals("")) hql = hql + " and roomInfo.introduction like '%" + introduction + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List roomInfoList = q.list();
    	return (ArrayList<RoomInfo>) roomInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RoomInfo> QueryRoomInfoInfo(String roomNumber,RoomType roomTypeObj,String position,String introduction) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RoomInfo roomInfo where 1=1";
    	if(!roomNumber.equals("")) hql = hql + " and roomInfo.roomNumber like '%" + roomNumber + "%'";
    	if(null != roomTypeObj && roomTypeObj.getRoomTypeId()!=0) hql += " and roomInfo.roomTypeObj.roomTypeId=" + roomTypeObj.getRoomTypeId();
    	if(!position.equals("")) hql = hql + " and roomInfo.position like '%" + position + "%'";
    	if(!introduction.equals("")) hql = hql + " and roomInfo.introduction like '%" + introduction + "%'";
    	Query q = s.createQuery(hql);
    	List roomInfoList = q.list();
    	return (ArrayList<RoomInfo>) roomInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RoomInfo> QueryAllRoomInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From RoomInfo";
        Query q = s.createQuery(hql);
        List roomInfoList = q.list();
        return (ArrayList<RoomInfo>) roomInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String roomNumber,RoomType roomTypeObj,String position,String introduction) {
        Session s = factory.getCurrentSession();
        String hql = "From RoomInfo roomInfo where 1=1";
        if(!roomNumber.equals("")) hql = hql + " and roomInfo.roomNumber like '%" + roomNumber + "%'";
        if(null != roomTypeObj && roomTypeObj.getRoomTypeId()!=0) hql += " and roomInfo.roomTypeObj.roomTypeId=" + roomTypeObj.getRoomTypeId();
        if(!position.equals("")) hql = hql + " and roomInfo.position like '%" + position + "%'";
        if(!introduction.equals("")) hql = hql + " and roomInfo.introduction like '%" + introduction + "%'";
        Query q = s.createQuery(hql);
        List roomInfoList = q.list();
        recordNumber = roomInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public RoomInfo GetRoomInfoByRoomNumber(String roomNumber) {
        Session s = factory.getCurrentSession();
        RoomInfo roomInfo = (RoomInfo)s.get(RoomInfo.class, roomNumber);
        return roomInfo;
    }

    /*更新RoomInfo信息*/
    public void UpdateRoomInfo(RoomInfo roomInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(roomInfo);
    }

    /*删除RoomInfo信息*/
    public void DeleteRoomInfo (String roomNumber) throws Exception {
        Session s = factory.getCurrentSession();
        Object roomInfo = s.load(RoomInfo.class, roomNumber);
        s.delete(roomInfo);
    }

}
