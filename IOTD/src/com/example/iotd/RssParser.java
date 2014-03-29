package com.example.iotd;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RssParser extends DefaultHandler {
	// INICIO
	private RssItem item;
	private List<RssItem> list = new ArrayList<RssItem>();

	// MEIO
	private StringBuffer chars;

	// FIM
	// INICIO
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("item")) {
			this.item = new RssItem();
			this.list.add(item);
		} else if (this.item != null) {
				if (localName.equalsIgnoreCase("enclosure")){
					this.item.setImagemUrl(attributes.getValue("url"));
				}
		}
		this.chars = new StringBuffer();
		
	}

	// MEIO

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		this.chars.append(new String(ch, start, length));

	}

	// FIM
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (item != null) {
			if (localName.equalsIgnoreCase("item")) {
				item = null;
			} else if (localName.equalsIgnoreCase("title")){
				item.setTitulo(chars.toString());
			}  else if (localName.equalsIgnoreCase("description")){
				item.setDescricao(chars.toString());
			} else if (localName.equalsIgnoreCase("pubDate")){
				item.setData(chars.toString());
			}
			
		}
	}
	
	public RssItem getFirstItem(){
		return list.get(0);
	}

}
