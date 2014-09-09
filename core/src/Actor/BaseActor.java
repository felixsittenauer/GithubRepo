package Actor;
import ScreenResolution.ProjectionHandler;
import Sprite.BaseSprite;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class BaseActor extends Image {

	// -----------------------------------------------
	// FIELDS
	// -----------------------------------------------

	private BaseSprite baseSprite;

	// -----------------------------------------------
	// Konstruktor
	// -----------------------------------------------
	public BaseActor(BaseSprite baseSprite) {
		super(baseSprite.getRegion());
		this.baseSprite = baseSprite;
		setRealBounds(0, 0, baseSprite.getRealWidth(), baseSprite.getRealHeight());
		setRealPos();
		this.setVisible(true); 
	}



	// -----------------------------------------------
	// public Methods
	// -----------------------------------------------

	public void setRealPos() {
		float x = ProjectionHandler.Project_Real_to_Virtualviewport_X(baseSprite.getRealX());
		float y = ProjectionHandler.Project_Real_to_Virtualviewport_Y(baseSprite.getRealY(), baseSprite.getRealHeight());
		this.setPosition(x, y);
		
	}
	
	public float getRealX(){
		return baseSprite.getRealX();
	}
	
	public float getRealY(){
		return baseSprite.getRealY();
	}
	
	public BaseSprite getBaseSprite(){
		return baseSprite;
	}

	// -----------------------------------------------
	// private Methods
	// -----------------------------------------------

	private void setRealBounds(float X, float Y, float width, float height) {
		float x = ProjectionHandler.Project_Real_to_Virtualviewport_X(X);
		float y = ProjectionHandler.Project_Real_to_Virtualviewport_Y(Y, height);
		setBounds(x, y, width, height);
	}

}
