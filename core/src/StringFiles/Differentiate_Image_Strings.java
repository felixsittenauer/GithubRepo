package StringFiles;

import Tag.GfxTags;

public class Differentiate_Image_Strings {

	public static String getSequenceString(int SequenceID) {
		switch (SequenceID) {
			case (1):return "1:1:2:1:2";
		}
		return "-1";
	}

	public static String getString(int LvlID) {
		// "AtlasID:Texture_1_name:Texture_2_name#Xpos:YPos;XPos:YPos;(...)"
		switch (LvlID) {
			case (1):return "1:test1:test2#99:176;271:391;295:643;%#";
			case (2):return "2:test3:test4#99:176;%#";
		}
		
		return "-1";
	}

	
	public static String getAtlasName(String lvlString) {
		String Name = "";
		String[] lines = lvlString.split("#");
		String texturesString = lines[0];
		String[] Names = texturesString.split(":");
		int numb = Integer.parseInt(Names[0]);
		switch (numb) {
		case (1):
			Name = GfxTags.TAG_ATLAS_DIFFERENTIATE_IMAGE_LVL_1;
			break;
		case (2):
			Name = GfxTags.TAG_ATLAS_DIFFERENTIATE_IMAGE_LVL_2;
			break;
		}
		return Name;
	}
	


}
