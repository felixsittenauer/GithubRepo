package Screen;

import Manager.ResourceManager;
import Manager.ScreenManager;
import Manager.ScreenManager.Screen_Type;
import Sprite.BaseSprite;
import Tag.GfxTags;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import de.Infinity.ProjectR.ProjectRMain;

/**
 * 
 * @author Felix
 * @Date 1.8.14
 * @version 1.0.0
 * 
 * @zuletzt_bearbeitet: Felix | 01.08.14
 */

// Musterscreen, welche zum Kopieren gedacht ist
public class Screen_Allegro extends BaseScreen {

	private BaseSprite sp_Allegro_Logo;

	public Screen_Allegro(ProjectRMain nProjectR) {
		super(nProjectR,0,0);
	}

	@Override
	public Screen_Type getScene_Type() {
		return Screen_Type.Allegro_Splash;
	}

	@Override
	protected void OnCreate() {
		sp_Allegro_Logo = new BaseSprite(GfxTags.TAG_GFX_Allegro_Logo,0,0);
	}
	
	@Override
	public void Update(){
		
	}

	@Override
	protected void Render() {

		sp_Allegro_Logo.draw(batch);
	}

	@Override
	protected void OnPopulate() {
		Timer.schedule(new Task() {
			@Override
			public void run() {
				ResourceManager.getInstance().Menu_get();
				ScreenManager.getInstance().show_Menu();
			}
		}, 0.5f);
	}

	@Override
	protected void OnDispose() {
		stage.dispose();
		ScreenManager.getInstance().Dispose_Allegro_Splash();
	}

	@Override
	protected void OnPause() {

	}

	@Override
	protected void OnResume() {

	}

	@Override
	protected void OnBackButtonPressed() {

	}

	@Override
	protected void RelocateFloat() {
		// TODO Auto-generated method stub

	}

}
