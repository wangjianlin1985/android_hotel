package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.MoneyRecord;
public class MoneyRecordListHandler extends DefaultHandler {
	private List<MoneyRecord> moneyRecordList = null;
	private MoneyRecord moneyRecord;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (moneyRecord != null) { 
            String valueString = new String(ch, start, length); 
            if ("recordId".equals(tempString)) 
            	moneyRecord.setRecordId(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	moneyRecord.setUserObj(valueString); 
            else if ("moneyValue".equals(tempString)) 
            	moneyRecord.setMoneyValue(new Float(valueString).floatValue());
            else if ("memo".equals(tempString)) 
            	moneyRecord.setMemo(valueString); 
            else if ("happenTime".equals(tempString)) 
            	moneyRecord.setHappenTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("MoneyRecord".equals(localName)&&moneyRecord!=null){
			moneyRecordList.add(moneyRecord);
			moneyRecord = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		moneyRecordList = new ArrayList<MoneyRecord>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("MoneyRecord".equals(localName)) {
            moneyRecord = new MoneyRecord(); 
        }
        tempString = localName; 
	}

	public List<MoneyRecord> getMoneyRecordList() {
		return this.moneyRecordList;
	}
}
