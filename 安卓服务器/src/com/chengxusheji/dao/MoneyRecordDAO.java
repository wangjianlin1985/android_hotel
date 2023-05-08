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
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.MoneyRecord;

@Service @Transactional
public class MoneyRecordDAO {

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
    public void AddMoneyRecord(MoneyRecord moneyRecord) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(moneyRecord);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MoneyRecord> QueryMoneyRecordInfo(UserInfo userObj,String happenTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From MoneyRecord moneyRecord where 1=1";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and moneyRecord.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!happenTime.equals("")) hql = hql + " and moneyRecord.happenTime like '%" + happenTime + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List moneyRecordList = q.list();
    	return (ArrayList<MoneyRecord>) moneyRecordList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MoneyRecord> QueryMoneyRecordInfo(UserInfo userObj,String happenTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From MoneyRecord moneyRecord where 1=1";
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and moneyRecord.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!happenTime.equals("")) hql = hql + " and moneyRecord.happenTime like '%" + happenTime + "%'";
    	Query q = s.createQuery(hql);
    	List moneyRecordList = q.list();
    	return (ArrayList<MoneyRecord>) moneyRecordList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<MoneyRecord> QueryAllMoneyRecordInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From MoneyRecord";
        Query q = s.createQuery(hql);
        List moneyRecordList = q.list();
        return (ArrayList<MoneyRecord>) moneyRecordList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(UserInfo userObj,String happenTime) {
        Session s = factory.getCurrentSession();
        String hql = "From MoneyRecord moneyRecord where 1=1";
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and moneyRecord.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!happenTime.equals("")) hql = hql + " and moneyRecord.happenTime like '%" + happenTime + "%'";
        Query q = s.createQuery(hql);
        List moneyRecordList = q.list();
        recordNumber = moneyRecordList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public MoneyRecord GetMoneyRecordByRecordId(int recordId) {
        Session s = factory.getCurrentSession();
        MoneyRecord moneyRecord = (MoneyRecord)s.get(MoneyRecord.class, recordId);
        return moneyRecord;
    }

    /*更新MoneyRecord信息*/
    public void UpdateMoneyRecord(MoneyRecord moneyRecord) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(moneyRecord);
    }

    /*删除MoneyRecord信息*/
    public void DeleteMoneyRecord (int recordId) throws Exception {
        Session s = factory.getCurrentSession();
        Object moneyRecord = s.load(MoneyRecord.class, recordId);
        s.delete(moneyRecord);
    }

}
