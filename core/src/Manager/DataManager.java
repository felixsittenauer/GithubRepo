package Manager;

import java.util.HashMap;
import java.util.Map;

import Tag.DataTags;
import DataHandler.CF_FileHandler;

/**
 * 
 * @author Felix
 * @Date 18.8.14
 * @version 1.0.0
 * 
 * @zuletzt_bearbeitet: F | 18.08.14
 */
public class DataManager {
	// -----------------------------------------------
	// FIELDS
	// -----------------------------------------------

	// + Variables private
	private Map<String, String> CF_DataList;

	// -----------------------------------------------
	// Konstuktor
	// -----------------------------------------------

	public DataManager() {
		CF_DataList = new HashMap<String, String>();
	}

	// -----------------------------------------------
	// Add/Read/Save
	// -----------------------------------------------

	public void addTmp(String key, String value) {
		CF_DataList.put(key, value);
	}

	public void addToFile(String[] readKeys, String[] readValues) {
		HashMap<String, String> m = new HashMap<String, String>();
		for (int i = 0; i < readKeys.length; i++) {
			m.put(readKeys[i], readValues[i]);
		}
		CF_FileHandler.Handle_Add(m);
	}

	public void Save(String[] readKeys) {
		CF_FileHandler.Handle_read(readKeys);
	}

	public void read(String[] readKeys) {
		CF_FileHandler.Handle_read(readKeys);
	}

	public void createFile() {
		CF_FileHandler.writeTxt(DataTags.Local_Data_Txt_File_Path + DataTags.CF_Key_Value, "#key:value;#+");
	}

	public String getValue(String Key) {
		return CF_DataList.get(Key);
	}
}
