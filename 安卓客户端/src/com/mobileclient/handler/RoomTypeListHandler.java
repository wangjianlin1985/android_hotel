package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.RoomType;
public class RoomTypeListHandler extends DefaultHandler {
	private List<RoomType> roomTypeList = null;
	private RoomType roomType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (roomType != null) { 
            String valueString = new String(ch, start, length); 
            if ("roomTypeId".equals(tempString)) 
            	roomType.setRoomTypeId(new Integer(valueString).intValue());
            else if ("roomTypeName".equals(tempString)) 
            	roomType.setRoomTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("RoomType".equals(localName)&&roomType!=null){
			roomTypeList.add(roomType);
			roomType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		roomTypeList = new ArrayList<RoomType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("RoomType".equals(localName)) {
            roomType = new RoomType(); 
        }
        tempString = localName; 
	}

	public List<RoomType> getRoomTypeList() {
		return this.roomTypeList;
	}
}
