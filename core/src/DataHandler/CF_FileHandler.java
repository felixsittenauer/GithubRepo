package DataHandler;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Tag.DataTags;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class CF_FileHandler {

	public CF_FileHandler() {

	}

	public static HashMap<String, String> Handle_read(String[] Keys) {
		ArrayList<String> Keylist = new ArrayList<String>();

		for (int i = 0; i < Keys.length; i++) {
			Keylist.add(Keys[i]);
		}

		String unHandledString = readTxt(DataTags.Local_Data_Txt_File_Path
				+ DataTags.CF_Key_Value);

		HashMap<String, String> returnMap = new HashMap<String, String>();

		String[] lines = unHandledString.split("#");

		int lines_i = 1;

		while (lines[lines_i].charAt(0) != '+') {
			String line = lines[lines_i];
			String Key = line.substring(0, line.indexOf(":"));
			String Value = line.substring(line.indexOf(":") + 1,
					line.indexOf(";"));
			if (Keylist.contains(Key)) {
				returnMap.put(Key, Value);
			}
			lines_i++;
		}

		return returnMap;
	}

	public static void Handle_Update(String[] Keys, HashMap<String, String> map) {
		ArrayList<String> Keylist = new ArrayList<String>();

		for (int i = 0; i < Keys.length; i++) {
			Keylist.add(Keys[i]);
		}

		String unHandledString = readTxt(DataTags.Local_Data_Txt_File_Path
				+ DataTags.CF_Key_Value);

		String[] lines = unHandledString.split("#");

		int lines_i = 1;

		String StringToWrite = "";
		while (lines[lines_i].charAt(0) != '+') {
			String line = lines[lines_i];
			String Key = line.substring(0, line.indexOf(":"));
			String Value = line.substring(line.indexOf(":") + 1,
					line.indexOf(";"));

			if (Keylist.contains(Key)) {
				Value = map.get(Key);
				line = Key + ":" + Value + ";";
			}

			StringToWrite += "#" + line;
			lines_i++;
		}
		StringToWrite += "#+";

		writeTxt(DataTags.Local_Data_Txt_File_Path + DataTags.CF_Key_Value,
				StringToWrite);
	}

	public static void Handle_Add(HashMap<String, String> map) {
		ArrayList<String> Keylist = new ArrayList<String>();

		String unHandledString = readTxt(DataTags.Local_Data_Txt_File_Path
				+ DataTags.CF_Key_Value);

		unHandledString = unHandledString.substring(0, unHandledString.indexOf('+')-1);

		String stringToWrite = unHandledString;
		
		Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        stringToWrite+="#"+pairs.getKey()+":"+pairs.getValue()+";";
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    stringToWrite+="#+";
		writeTxt(DataTags.Local_Data_Txt_File_Path + DataTags.CF_Key_Value,
				stringToWrite);
	}

	// -----------------------------------------------
	// read/write
	// -----------------------------------------------

	public static String readTxt(String cf_file) {
		FileHandle file = Gdx.files.internal(cf_file);
		String text = file.readString();
		return text;
	}

	public static void writeTxt(String pathInAssets, String txt) {
		FileHandle file = Gdx.files.local(pathInAssets);
		file.writeString(txt, false);
	}

	public void AppendTxt(String pathInAssets, String txt) {
		FileHandle file = Gdx.files.local(pathInAssets);
		file.writeString(txt, true);
	}

}
