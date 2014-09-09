package StringFiles;

import Tag.GfxTags;

public class Differentiate_Letter_Strings {
	public static String getSequenceString(int SequenceID) {
		switch (SequenceID) {
		case (1):
			return "1:1";
		}
		return "-1";
	}

	public static String getString(int LvlID) {
		switch (LvlID) {
		// "AtlasID:Texture_1_name:Texture_2_name:Texture_3_name:(...)
		case (1):
			return "1:Test1:Test2:Test3";
		}

		return "-1";
	}

	public static String getAtlasName(int numb) {
		String Name = "-1";
		switch (numb) {
		case (1):
			Name = GfxTags.TAG_ATLAS_DIFFERENTIATE_LETTER_LVL_1;
			break;
		}
		return Name;
	}
}
