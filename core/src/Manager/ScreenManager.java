package Manager;

import com.badlogic.gdx.Gdx;

import GameScreens.Screen_Differentiate_Image;
import GameScreens.Screen_Differentiate_letter;
import GameScreens.Screen_Memory;
import Screen.BaseScreen;
import Screen.Screen_Allegro;
import Screen.Screen_IS_splash;
import Screen.Screen_Menu;
import Tag.DataTags;
import de.Infinity.ProjectR.ProjectRMain;

/**
 * 
 * @author Felix
 * @Date 1.8.14
 * @version 1.0.0
 * 
 * @zuletzt_bearbeitet: Christoph | 12.08.14
 */
public class ScreenManager {
	// *** Singleton class ***

	// -----------------------------------------------
	// FIELDS
	// -----------------------------------------------

	// + Singleton
	private static final ScreenManager INSTANCE = new ScreenManager();

	// + Variables private
	private BaseScreen currentScreen;
	private Screen_Type currentScreen_Type;
	private BaseScreen IS_Splash, Allegro_Splash, MainMenu, Differentiate_image,Differentiate_letter, Memory;

	private ProjectRMain ProjectR;

	// + enum
	public enum Screen_Type {
		IS_Splash, Allegro_Splash, Menu, Memory, Differentiate_letter, Differentiate_Image
	}

	// ---------------------------------------------
	// Init SCREENS
	// ---------------------------------------------

	// + IS_Splash

	public void show_IS_Splash() {
		IS_Splash = new Screen_IS_splash(ProjectR);
		this.setCurrentScreen(Screen_Type.IS_Splash);
	}

	public void Dispose_IS_Splash() {
		ResourceManager.getInstance().unload_gfx_Resources(ResourceManager.getInstance().IS_splash_gfx);

		IS_Splash = null;
	}

	// + Allegro_Splash

	public void show_Allegro_Splash() {
		Allegro_Splash = new Screen_Allegro(ProjectR);
		this.setCurrentScreen(Screen_Type.Allegro_Splash);
	}

	public void Dispose_Allegro_Splash() {
		ResourceManager.getInstance().unload_gfx_Resources(ResourceManager.getInstance().Allegro_splash_gfx);
		Allegro_Splash = null;
	}

	// + Menu_Screen

	public void show_Menu() {
		MainMenu = new Screen_Menu(ProjectR);
		this.setCurrentScreen(Screen_Type.Menu);
	}

	public void Dispose_Menu() {
		ResourceManager.getInstance().unload_gfx_Resources(ResourceManager.getInstance().Menu_atlas);
		ResourceManager.getInstance().unload_music_Resources(ResourceManager.getInstance().Menu_Sound);
		MainMenu = null;
	}

	// ////////////////////////////////////////////////
	// GAME SCREENS
	// ------------------------------------------------

	// MEMORY
	//
	public void show_Memory() {
		Memory = new Screen_Memory(ProjectR);
		this.setCurrentScreen(Screen_Type.Memory);
	}

	public void Dispose_Memory() {
		Memory = null;
		ResourceManager.getInstance().unload_gfx_Resources(ResourceManager.getInstance().Memory_atlas);
	}

	// DIFFERENTIATE

	public void show_Differentiat_image() {
		Differentiate_image = new Screen_Differentiate_Image(ProjectR);
		this.setCurrentScreen(Screen_Type.Differentiate_Image);
	}

	public void Dispose_Differentiate_image() {
		ResourceManager.getInstance().unload_gfx_Resources(ResourceManager.getInstance().Differentiate_image_atlas);
		Differentiate_image = null;
	}

	public void show_Differentiat_letter() {
		Differentiate_letter = new Screen_Differentiate_letter(ProjectR, 0, 0);
		this.setCurrentScreen(Screen_Type.Differentiate_letter);
	}

	public void Dispose_Differentiate_letter() {
		ResourceManager.getInstance().unload_gfx_Resources(ResourceManager.getInstance().Differentiate_letter_atlas);
		Differentiate_letter = null;
	}

	// -----------------------------------------------
	// Logic
	// -----------------------------------------------

	/**
	 * setCurrentScreen()
	 * 
	 * @param currentScreen
	 * @author Felix
	 */
	// - Entscheidet welche Screen gesetzt wird
	public void setCurrentScreen(Screen_Type currentScreen_t) {
		currentScreen_Type = currentScreen_t;
		switch (currentScreen_t) {
		case IS_Splash:
			setScreen(IS_Splash);
			break;
		case Allegro_Splash:
			setScreen(Allegro_Splash);
			break;
		case Menu:
			setScreen(MainMenu);
			break;
		case Differentiate_Image:
			setScreen(Differentiate_image);
			break;

		case Differentiate_letter:
			setScreen(Differentiate_letter);
			break;
			
		case Memory:
			setScreen(Memory);
			break;

		default:
			break;
		}

	}

	/**
	 * setScreen()
	 * 
	 * @param nScreen
	 * @author Felix
	 */
	// - setzt Screen
	private void setScreen(BaseScreen nScreen) {
		currentScreen = nScreen;
		this.ProjectR.setScreen(currentScreen);
		System.out.println("Set Screen to: " + nScreen.getScene_Type().toString());
	}

	// -----------------------------------------------
	// GETTERS
	// -----------------------------------------------

	public Screen_Type getCurrentSceneType() {
		return currentScreen_Type;
	}

	public static ScreenManager getInstance() {
		return INSTANCE;
	}

	// -----------------------------------------------
	// ResourceManager - Singleton prepare
	// -----------------------------------------------

	public ScreenManager() {

	}

	public static void prepareManager(ProjectRMain nProjectR) {
		getInstance().ProjectR = nProjectR;
		getInstance().currentScreen_Type = Screen_Type.IS_Splash;
		System.out.println("ScreenManager prepared");
	}
}
