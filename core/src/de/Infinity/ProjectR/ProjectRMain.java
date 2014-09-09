package de.Infinity.ProjectR;

import Manager.ResourceManager;
import Manager.ScreenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * @author Felix
 * @Date 1.8.14
 * @version 1.0.0
 * 
 * @zuletzt_bearbeitet: Felix | 01.08.14
 */
public class ProjectRMain extends Game {

	/**
	 * create()
	 * 
	 * @author Felix
	 */
	// - Initialisiert Singletons
	// - Setzt ersten Screen
	@Override
	public void create() 
	{
		System.out.println("Start SUPER GAME");
		// + Init Singletons
		ScreenManager.prepareManager(this);
		ResourceManager.prepareManager(this);
		
		// + Set first Screen
		ResourceManager.getInstance().IS_splash_load();
		ScreenManager.getInstance().show_IS_Splash();
	

	}
}
