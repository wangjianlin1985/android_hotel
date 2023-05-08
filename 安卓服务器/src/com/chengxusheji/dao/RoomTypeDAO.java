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

@Service @Transactional
public class RoomTypeDAO {

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
    public void AddRoomType(RoomType roomType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(roomType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RoomType> QueryRoomTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RoomType roomType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List roomTypeList = q.list();
    	return (ArrayList<RoomType>) roomTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RoomType> QueryRoomTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From RoomType roomType where 1=1";
    	Query q = s.createQuery(hql);
    	List roomTypeList = q.list();
    	return (ArrayList<RoomType>) roomTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<RoomType> QueryAllRoomTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From RoomType";
        Query q = s.createQuery(hql);
        List roomTypeList = q.list();
        return (ArrayList<RoomType>) roomTypeList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From RoomType roomType where 1=1";
        Query q = s.createQuery(hql);
        List roomTypeList = q.list();
        recordNumber = roomTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public RoomType GetRoomTypeByRoomTypeId(int roomTypeId) {
        Session s = factory.getCurrentSession();
        RoomType roomType = (RoomType)s.get(RoomType.class, roomTypeId);
        return roomType;
    }

    /*更新RoomType信息*/
    public void UpdateRoomType(RoomType roomType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(roomType);
    }

    /*删除RoomType信息*/
    public void DeleteRoomType (int roomTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object roomType = s.load(RoomType.class, roomTypeId);
        s.delete(roomType);
    }

}
