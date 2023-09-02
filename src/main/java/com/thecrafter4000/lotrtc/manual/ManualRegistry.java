package com.thecrafter4000.lotrtc.manual;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mantle.books.BookData;
import mantle.books.BookDataStore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.w3c.dom.Document;
import tconstruct.TConstruct;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ManualRegistry {
	
	public static List<BookRegEntry> list = new ArrayList<>();
	
	public static void preInit() {
		list.add(new BookRegEntry("intro"));
		list.add(new BookRegEntry("material"));
//		list.add(new BookRegEntry("blocks"));
	}
	
	public static void initClient() {
		for(BookRegEntry b : list) b.load();
	}
	
	public static class BookRegEntry {
		public final String name;
		public String toolTip = "";
		public BookData data = new BookData();
		
		public BookRegEntry(String name){
			this.name = name;
		}

		@SideOnly(Side.CLIENT)
		public void load() {
			this.toolTip = I18n.format("Manual." + name + ".tooltip.name");

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

		@SideOnly(Side.CLIENT)
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
