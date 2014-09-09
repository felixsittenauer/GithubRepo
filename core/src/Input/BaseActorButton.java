package Input;

import ScreenResolution.ProjectionHandler;
import ScreenResolution.VirtualViewport;
import Sprite.BaseSprite;
import Sprite.BaseSprite.FloatOrientation;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class BaseActorButton extends ImageButton {

	// -----------------------------------------------
	// FIELDS
	// -----------------------------------------------
	private BaseSprite baseSprite;
	private boolean isTouched, isTouchedDown;

	// -----------------------------------------------
	// Konstruktor
	// -----------------------------------------------

	public BaseActorButton(BaseSprite baseSprite, ClickListener Listener) {
		super(new SpriteDrawable(baseSprite));
		this.baseSprite = baseSprite;
		addListener(Listener);
		setRealBounds(baseSprite.getRealX(), baseSprite.getRealY(), baseSprite.getRealWidth(), baseSprite.getRealHeight());
		isTouched = false;
		isTouchedDown = false;
	}

	// -----------------------------------------------
	// public Methods
	// -----------------------------------------------

	public void setFloatingPos(float X_Distance, float Y_Distance, FloatOrientation XOrientation, FloatOrientation YOrientation, VirtualViewport virtualViewport) {
		baseSprite.set_FloatingPos(X_Distance, Y_Distance, XOrientation, YOrientation, virtualViewport);
		setRealPos();
	}

	public void setRealPos(float X, float Y) {
		baseSprite.setRealPos(X, Y);
		setRealPos();
	}

	public void setRealPos() {
		float x = ProjectionHandler.Project_Real_to_Virtualviewport_X(baseSprite.getRealX());
		float y = ProjectionHandler.Project_Real_to_Virtualviewport_Y(baseSprite.getRealY(), baseSprite.getRealHeight());
		this.setPosition(x, y);
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
