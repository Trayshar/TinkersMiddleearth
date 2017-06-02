package com.thecrafter4000.lotrtc.manual;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import mantle.books.BookData;
import mantle.books.BookDataStore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import tconstruct.TConstruct;

public class ManualRegistry {
	
	public static List<BookRegEntry> list = new ArrayList<BookRegEntry>();
	
	public static void preInitClient(){
		list.add( new BookRegEntry("intro"));
		list.add( new BookRegEntry("material"));
//		list.add( new BookRegEntry("blocks"));
	}
	
	public static void initClient(){
		for(BookRegEntry b : list) b.load();
	}
	
	public static class BookRegEntry{
		public String name;
		public String toolTip;
		public BookData data = new BookData();
		
		public BookRegEntry(String name){
			this.name = name;
			this.toolTip = I18n.format("Manual." + name + ".tooltip.name");
		}
		
		public void load(){
			data.unlocalizedName = "lotrtc.manual." + name;
			data.modID = "lotrtc";
			data.toolTip = toolTip;
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			String cl = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
			Document doc = readManual("/assets/lotrtc/manuals/" + cl + "/" + name + ".xml", dbf);
			if(doc == null) doc = readManual("/assets/lotrtc/manuals/en_US/" + name + ".xml", dbf);
			
			data.doc = doc;
			BookDataStore.addBook(data);
		}
		
	    Document readManual(String location, DocumentBuilderFactory dbFactory) {
	        try {
	            InputStream stream = TConstruct.class.getResourceAsStream(location);
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = dBuilder.parse(stream);
	            doc.getDocumentElement().normalize();
	            return doc;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	}
}
