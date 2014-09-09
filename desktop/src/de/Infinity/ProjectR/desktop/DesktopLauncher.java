package de.Infinity.ProjectR.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.Infinity.ProjectR.ProjectRMain;

public class DesktopLauncher
{
	public static void main (String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = ProjectRMain.class.getName();
		config.width = 1920;  
	    config.height = 1080;  
	    config.fullscreen = false;    
	    config.forceExit = true;  
	    config.vSyncEnabled = true;  
	        
		new LwjglApplication(new ProjectRMain(), config);
	}
}
