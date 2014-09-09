package Manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import StringFiles.Differentiate_Image_Strings;
import StringFiles.Differentiate_Letter_Strings;
import Tag.DataTags;
import Tag.GfxTags;
import Tag.AudioTags;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import de.Infinity.ProjectR.ProjectRMain;

/**
 * 
 * @author Felix (stinkt)
 * @Date 1.8.14
 * @version 1.0.5
 * 
 * @zuletzt_bearbeitet: CP | 13.08.14
 */
public class ResourceManager {
	// *** Singleton class ***

	// -----------------------------------------------
	// FIELDS
	// -----------------------------------------------

	// + Singleton
	private static final ResourceManager INSTANCE = new ResourceManager();

	// + Variables private
	private ProjectRMain ProjectR;
	private AssetManager AManager;
	private DataManager DataManager;

	// + String_tags

	public final String[] IS_splash_gfx = { GfxTags.TAG_GFX_IS_Logo };

	public final String[] Allegro_splash_gfx = { GfxTags.TAG_GFX_Allegro_Logo };

	public final String[] Menu_atlas = { GfxTags.TAG_ATLAS_MENU_BACKGROUND, GfxTags.TAG_ATLAS_MENU_INTERFACE, GfxTags.TAG_ATLAS_GAME_DOMAINS, GfxTags.TAG_ATLAS_MENU_SleepingOwl,
			GfxTags.TAG_ATLAS_MENU_SleepingZ };
	public final String[] Menu_Sound = { AudioTags.TAG_MUSIC_test };

	public final String[] Differentiate_image_atlas = { GfxTags.TAG_ATLAS_DIFFERENTIATE, GfxTags.TAG_Atlas_DIFFERENTIATE_IMAGE, GfxTags.TAG_ATLAS_DIFFERENTIATE_IMAGE_LVL_1,
			GfxTags.TAG_ATLAS_DIFFERENTIATE_IMAGE_LVL_2 };
	public final String[] Differentiate_letter_atlas = { GfxTags.TAG_ATLAS_DIFFERENTIATE ,GfxTags.TAG_ATLAS_DIFFERENTIATE_LETTER_LVL_1};

	public final String[] Memory_atlas = { GfxTags.TAG_ATLAS_MEMORY_UI, GfxTags.TAG_ATLAS_MEMORY_BACKFLIP,											// Karten
			GfxTags.TAG_ATLAS_MEMORY_FLIPSHEET + "00.atlas",
			GfxTags.TAG_ATLAS_MEMORY_FLIPSHEET + "01.atlas",
			GfxTags.TAG_ATLAS_MEMORY_FLIPSHEET + "02.atlas",
			GfxTags.TAG_ATLAS_MEMORY_FLIPSHEET + "03.atlas",
			GfxTags.TAG_ATLAS_MEMORY_FLIPSHEET + "04.atlas",
			GfxTags.TAG_ATLAS_MEMORY_FLIPSHEET + "05.atlas",
				};
 

	// + TEXTURES & TEXTURE REGIONS
	// ***

	// + Screen: IS_splash
	public Texture tx_IS_splash_Logo;
	public Texture tx_IS_owl;
	public Texture tx_Allegro_splash_Logo;

	public Map<String, Texture> TextureBuffer;
	public Map<String, TextureAtlas> TextureAtlasBuffer;
	public Map<String, Music> MusicBuffer;
	public Map<String, Sound> SoundBuffer;

	// ***

	// ---------------------------------------------
	// lOAD & UNLOAD (gfx, audio, font, ...)
	// ---------------------------------------------

	// + Screen: IS_splash
	public void IS_splash_load() {
		loadtexture(IS_splash_gfx);

		wait_for_it();
		gettexture(IS_splash_gfx);
	}

	// + Screen: Allegro_Splash
	public void Allegro_load() {
		loadtexture(Allegro_splash_gfx);
		wait_for_it();
		gettexture(Allegro_splash_gfx);
	}

	// + Screen: Menu
	public void Menu_load() {
		loadtextureatlas(Menu_atlas);
		loadSound(Menu_Sound);
		wait_for_it();
	}

	public void Menu_get() {
		gettextureatlas(Menu_atlas);
		getSound(Menu_Sound);
	}

	// + Screen: Game - Differentiate
	public void Differentiate_image_load() {
		loadtextureatlas(Differentiate_image_atlas);
		Differentiate_load_Image_LvlSequence(1,0);
		wait_for_it();
	}

	public void Differentiate_image_get() {
		gettextureatlas(Differentiate_image_atlas);

	}

	public void Differentiate_letter_load() {
		loadtextureatlas(Differentiate_letter_atlas);
		Differentiate_load_Image_LvlSequence(1,0);
		wait_for_it();
	}

	public void Differentiate_letter_get() {
		gettextureatlas(Differentiate_letter_atlas);
	}

	public void Differentiate_load_Image_LvlSequence(int ID, int GameID) {
		if (GameID == 0) {
			// Image
			String SequenceString = Differentiate_Image_Strings.getSequenceString(ID);
			DataManager.addTmp(DataTags.tmp_Differentiate_Image_lvlSequenceString, SequenceString);
		} else {
			// Letter
			String SequenceString = Differentiate_Letter_Strings.getSequenceString(ID);
			DataManager.addTmp(DataTags.tmp_Differentiate_Letter_lvlSequenceString, SequenceString);
		}
	}

	// + Screen: Memory
	public void Memory_load() {
		loadtextureatlas(Memory_atlas);
		wait_for_it();
	}

	public void Memory_get() {
		gettextureatlas(Memory_atlas);
	}

	// Textures
	//

	private void loadtexture(String... args) {
		TextureParameter param = new TextureParameter();
		param.genMipMaps = true; // mipmaps angeschaltet

		// Print BS
		System.out.println("load_texture ( " + args.length + " to load)");
		for (int i = 0; i < args.length; i++) {
			System.out.println("	   +(" + i + ") load texture from:" + get_gfx_Path(args[i]));
			AManager.load(get_gfx_Path(args[i]), Texture.class, param);
		}
	}

	public void gettexture(String... args) {
		Texture tmp;
		System.out.println("get_texture (" + args.length + " to get)");
		for (int i = 0; i < args.length; i++) {
			tmp = AManager.get(get_gfx_Path(args[i]));

			// FILTER SETTING für alle Textures
			//
			tmp.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);

			TextureBuffer.put(args[i], tmp);

			System.out.println("	   +(" + i + ") get texture from " + get_gfx_Path(args[i]));
			// tx_IS_splash_Logo =
			// AManager.get(get_gfx_Path(GfxTags.TAG_GFX_bitmap),
			// Texture.class);
			// tx_IS_owl =
			// AManager.get(get_gfx_Path(GfxTags.TAG_GFX_owl),Texture.class);
		}
	}

	//

	// TextureAtlas
	//

	private void loadtextureatlas(String... args) {

		System.out.println("load_atlas ( " + args.length + " to load)");
		for (int i = 0; i < args.length; i++) {
			System.out.println("	   +(" + i + ") load atlas from:" + get_gfx_Path(args[i]));
			AManager.load(get_gfx_Path(args[i]), TextureAtlas.class);
		}
	}

	private void gettextureatlas(String... args) {
		TextureAtlas tmp;
		System.out.println("get_textureatlas (" + args.length + " to get)");
		for (int i = 0; i < args.length; i++) {
			tmp = AManager.get(get_gfx_Path(args[i]));

			TextureAtlasBuffer.put(args[i], tmp);

			System.out.println("	   +(" + i + ") get textureatlas from " + get_gfx_Path(args[i]));

		}
	}

	// Music
	//
	private void loadMusic(String... args) {
		System.out.println("load_Music ( " + args.length + " to load)");
		for (int i = 0; i < args.length; i++) {
			System.out.println("	   +(" + i + ") load atlas from:" + get_music_Path(args[i]));
			AManager.load(get_music_Path(args[i]), Music.class);
		}
	}

	private void getMusic(String... args) {
		Music tmp;
		System.out.println("get_Music (" + args.length + " to get)");
		for (int i = 0; i < args.length; i++) {
			tmp = AManager.get(get_music_Path(args[i]));

			MusicBuffer.put(args[i], tmp);

			System.out.println("	   +(" + i + ") get textureatlas from " + get_music_Path(args[i]));

		}
	}

	// Sound
	//

	private void loadSound(String... args) {
		System.out.println("load_Sound ( " + args.length + " to load)");
		for (int i = 0; i < args.length; i++) {
			System.out.println("	   +(" + i + ") load atlas from:" + get_music_Path(args[i]));
			AManager.load(get_music_Path(args[i]), Sound.class);
		}
	}

	private void getSound(String... args) {
		Sound tmp;
		System.out.println("get_Sound (" + args.length + " to get)");
		for (int i = 0; i < args.length; i++) {
			tmp = AManager.get(get_music_Path(args[i]));

			SoundBuffer.put(args[i], tmp);

			System.out.println("	   +(" + i + ") get textureatlas from " + get_music_Path(args[i]));

		}
	}

	public void reloadResources() {
		AManager.update();
		wait_for_it();
	}

	//

	public void unload_gfx_Resources(String... args) {
		System.out.println("unload_gfx(" + args.length + ") to unload");
		for (int i = 0; i < args.length; i++) {

			AManager.unload(get_gfx_Path(args[i]));

			if (TextureAtlasBuffer.containsKey(args[i]) == true) {
				TextureAtlasBuffer.get(args[i]).dispose();
				TextureAtlasBuffer.remove(args[i]);
			} else if (TextureBuffer.containsKey(args[i]) == true) {
				TextureBuffer.get(args[i]).dispose();
				TextureBuffer.remove(args[i]);
			}

			System.out.println("	   +(" + i + ") " + get_gfx_Path(args[i]) + " unloaded,disposed,removed");
		}
	}

	public void unload_music_Resources(String... args) {
		System.out.println("unload_music(" + args.length + ") to unload");
		for (int i = 0; i < args.length; i++) {
			AManager.unload(get_music_Path(args[i]));
			if (MusicBuffer.containsKey(args[i]) == true) {
				MusicBuffer.get(args[i]).dispose();
				MusicBuffer.remove(args[i]);
			} else if (SoundBuffer.containsKey(args[i]) == true) {
				SoundBuffer.get(args[i]).dispose();
				SoundBuffer.remove(args[i]);
			}
			System.out.println("	   +(" + i + ") " + get_music_Path(args[i]) + " unloaded,disposed,removed");
		}
	}

	// ---------------------------------------------
	// Return Methoden für die Screens
	// ---------------------------------------------

	public Texture returnTexture(String retTag) {
		return TextureBuffer.get(retTag);
	}

	public TextureAtlas returntextureatlas(String retTag) {
		return TextureAtlasBuffer.get(retTag);
	}

	public Music returnMusic(String retTag) {
		return MusicBuffer.get(retTag);
	}

	public Sound returnSound(String retTag) {
		return SoundBuffer.get(retTag);
	}

	public String returnValue(String Key) {
		return DataManager.getValue(Key);
	}

	// ---------------------------------------------
	// GETTERS
	// ---------------------------------------------

	public static ResourceManager getInstance() {
		return INSTANCE;
	}

	public String get_gfx_Path(String gfx_name) {
		String PATH = "";
		PATH += "" + GfxTags.TAG_GFX_ASSETS_PATH;
		PATH += "/high/";

		PATH += gfx_name;
		return PATH;
	}

	public String get_music_Path(String music_name) {
		String PATH = "";
		PATH += "" + AudioTags.TAG_MUSIC_ASSETS_PATH;
		PATH += "/";
		PATH += music_name;
		return PATH;
	}

	public void wait_for_it() {
		AManager.finishLoading();
		System.out.println("All gdx loaded");
	}

	// -----------------------------------------------
	// ResourceManager - Singleton prepare
	// -----------------------------------------------

	public ResourceManager() {

	}

	public static void prepareManager(ProjectRMain nProjectR) {
		getInstance().TextureBuffer = new HashMap<String, Texture>();
		getInstance().TextureAtlasBuffer = new HashMap<String, TextureAtlas>();
		getInstance().MusicBuffer = new HashMap<String, Music>();
		getInstance().SoundBuffer = new HashMap<String, Sound>();
		getInstance().ProjectR = nProjectR;
		getInstance().AManager = new AssetManager();
		getInstance().DataManager = new DataManager();
		System.out.println("ResourceManager prepared");
	}
}
