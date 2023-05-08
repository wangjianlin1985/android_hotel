package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.RoomInfo;
public class RoomInfoListHandler extends DefaultHandler {
	private List<RoomInfo> roomInfoList = null;
	private RoomInfo roomInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (roomInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("roomNumber".equals(tempString)) 
            	roomInfo.setRoomNumber(valueString); 
            else if ("roomTypeObj".equals(tempString)) 
            	roomInfo.setRoomTypeObj(new Integer(valueString).intValue());
            else if ("roomPrice".equals(tempString)) 
            	roomInfo.setRoomPrice(new Float(valueString).floatValue());
            else if ("position".equals(tempString)) 
            	roomInfo.setPosition(valueString); 
            else if ("introduction".equals(tempString)) 
            	roomInfo.setIntroduction(valueString); 
            else if ("roomPhoto".equals(tempString)) 
            	roomInfo.setRoomPhoto(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("RoomInfo".equals(localName)&&roomInfo!=null){
			roomInfoList.add(roomInfo);
			roomInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		roomInfoList = new ArrayList<RoomInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("RoomInfo".equals(localName)) {
            roomInfo = new RoomInfo(); 
        }
        tempString = localName; 
	}

	public List<RoomInfo> getRoomInfoList() {
		return this.roomInfoList;
	}
}
