package Screen;

import Manager.ResourceManager;
import Manager.ScreenManager;
import Manager.ScreenManager.Screen_Type;
import Sprite.BaseSprite;
import Tag.GfxTags;

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
 * @zuletzt_bearbeitet: Felix | 14.08.14
 */

// Musterscreen, welche zum Kopieren gedacht ist
public class Screen_IS_splash extends BaseScreen {

	private BaseSprite sp_IS_Logo;

	public Screen_IS_splash(ProjectRMain nProjectR) {
		super(nProjectR,0,0);
	}

	@Override
	public Screen_Type getScene_Type() {
		return Screen_Type.IS_Splash;
	}

	@Override
	protected void OnCreate() {
		batch = new SpriteBatch();
		sp_IS_Logo = new BaseSprite(GfxTags.TAG_GFX_IS_Logo, 0, 0);
		sp_IS_Logo.setRealBatch(1708, 1200);
	}

	@Override
	protected void Update() {
		//sp_IS_Logo.setRealPosMiddle(getInputRealX(), getInputRealY());
		// System.out.println(" X:"+Gdx.input.getX()+" Y"+getInputRealY());
	}

	@Override
	protected void Render() {
		sp_IS_Logo.draw(batch);
	}

	@Override
	protected void OnPopulate() {
		Timer.schedule(new Task() {
			@Override
			public void run() {
				ResourceManager.getInstance().Allegro_load();
				ResourceManager.getInstance().Menu_load();
				ScreenManager.getInstance().show_Allegro_Splash();
			}
		}, 0.5f);

	}

	@Override
	protected void OnDispose() {
		stage.dispose();
		ScreenManager.getInstance().Dispose_IS_Splash();
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
