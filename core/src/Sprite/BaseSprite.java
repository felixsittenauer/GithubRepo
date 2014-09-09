package Sprite;

import ScreenResolution.ProjectionHandler;
import ScreenResolution.VirtualViewport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BaseSprite extends Sprite {

	// -----------------------------------------------
	// FIELDS
	// -----------------------------------------------

	public enum FloatOrientation {
		Right, Left, Top, Bottom, Middle, first_Third, second_Third
	}

	// private

	private TextureAtlas txA_Atlas;
	private TextureRegion txR_Region;
	private Texture tx_texture;

	private String TextureTag, AtlasTag, RegionName;

	boolean isRegion;

	protected float SpriteHeight, SpriteWidth, SpriteBatchWidth,
			SpriteBatchHeight;

	protected float VirtualPortX, VirtualPortY, RealX, RealY, XSpeed, YSpeed;

	// -----------------------------------------------
	// Konstruktor
	// -----------------------------------------------
	public BaseSprite(BaseSprite baseSprite){
		super(baseSprite.getRegion());
		
		isRegion = true;
		
		SpriteHeight = SpriteBatchHeight = baseSprite.getHeight();
		SpriteWidth = SpriteBatchWidth = baseSprite.getWidth();
		
		RealX = baseSprite.getRealX();
		RealY = baseSprite.getRealY();
		
		XSpeed = 0;
		YSpeed = 0;
		
		VirtualPortX = ProjectionHandler
				.Project_Real_to_Virtualviewport_X(RealX);
		VirtualPortY = ProjectionHandler.Project_Real_to_Virtualviewport_Y(
				RealY, SpriteHeight);

		setRealPos(RealX, RealY);
	}

	public BaseSprite(String textureTag, float realX, float realY) {
		super(Manager.ResourceManager.getInstance().returnTexture(textureTag));
		TextureTag = textureTag;
		tx_texture = Manager.ResourceManager.getInstance().returnTexture(
				textureTag);

		isRegion = false;

		SpriteHeight = SpriteBatchHeight = this.getHeight();
		SpriteWidth = SpriteBatchWidth = this.getWidth();

		RealX = realX;
		RealY = realY;

		XSpeed = 0;
		YSpeed = 0;

		VirtualPortX = ProjectionHandler
				.Project_Real_to_Virtualviewport_X(RealX);
		VirtualPortY = ProjectionHandler.Project_Real_to_Virtualviewport_Y(
				RealY, SpriteHeight);

		setRealPos(RealX, RealY);
	}

	public BaseSprite(String AtlasTag, String RegionName, float realX,
			float realY) {
		super((Manager.ResourceManager.getInstance()
				.returntextureatlas(AtlasTag)).findRegion(RegionName));
		txA_Atlas = Manager.ResourceManager.getInstance().returntextureatlas(
				AtlasTag);
		txR_Region = txA_Atlas.findRegion(RegionName);

		isRegion = true;

		SpriteHeight = SpriteBatchHeight = this.getHeight();
		SpriteWidth = SpriteBatchWidth = this.getWidth();

		RealX = realX;
		RealY = realY;
		VirtualPortX = ProjectionHandler
				.Project_Real_to_Virtualviewport_X(RealX);
		VirtualPortY = ProjectionHandler.Project_Real_to_Virtualviewport_Y(
				RealY, SpriteHeight);

		XSpeed = 0;
		YSpeed = 0;

		setRealPos(RealX, RealY);

	}

	// -----------------------------------------------
	// Logic
	// -----------------------------------------------

	// -----------------------------------------------
	// Getters & Setters
	// -----------------------------------------------

	public void setXSpeed(float NewX) {
		XSpeed = NewX;
	}

	public void setYSpeed(float NewY) {
		YSpeed = NewY;
	}

	public void SetSpeed(float NewX, float NewY) {
		XSpeed = NewX;
		YSpeed = NewY;
	}

	public void UpdatePosition() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		setRealPos(RealX + (XSpeed * deltaTime), RealY + (YSpeed * deltaTime));
	}

	public void setRealBatch(float width, float height) {
		SpriteBatchHeight = height;
		SpriteBatchWidth = width;
	}

	public void setRealPos(float realX, float realY) {
		RealX = realX;
		RealY = realY;

		VirtualPortX = ProjectionHandler
				.Project_Real_to_Virtualviewport_X(RealX);
		VirtualPortY = ProjectionHandler.Project_Real_to_Virtualviewport_Y(
				RealY, SpriteHeight);

		this.setPosition(VirtualPortX, VirtualPortY);
	}

	public void setRealPosMiddle(float realX_m, float realY_m) {
		setRealPos(realX_m - (this.SpriteBatchWidth / 2), realY_m
				- (this.SpriteBatchHeight / 2));
	}

	public float getRealHeight() {
		return SpriteBatchHeight;
	}

	public float getRealWidth() {
		return SpriteBatchWidth;
	}

	public float getXSpeed() {
		return XSpeed;
	}

	public float getYSpeed() {
		return YSpeed;
	}

	public float getRealX() {
		return ProjectionHandler
				.Project_Virtualviewport_to_Real_X(VirtualPortX);
	}

	public float getRealY() {
		return ProjectionHandler.Project_Virtualviewport_to_Real_Y(
				VirtualPortY, SpriteHeight);
	}

	public void setRealSize(float nWidth, float nHeight) {
		SpriteHeight = SpriteBatchHeight = nHeight;
		SpriteWidth = SpriteBatchWidth = nWidth;

		this.setSize(nWidth, nHeight);
	}
	
	public TextureRegion getRegion(){
		return this.txR_Region;
	}

	// Floating

	public void set_FloatingPos(float X_Distance, float Y_Distance,
			FloatOrientation XOrientation, FloatOrientation YOrientation,
			VirtualViewport virtualViewport) {

		float RealPos_X = 0;
		float RealPos_Y = 0;

		float Screen_W = ProjectionHandler.VirtualScreenWidth;
		float Screen_H = ProjectionHandler.VirtualScreenHeight;

		float a = (Screen_H - virtualViewport.getHeight()) / 2;
		float b = (Screen_W - virtualViewport.getWidth()) / 2;

		switch (XOrientation) {
		case Left:
			RealPos_X = b + X_Distance;
			break;
		case Right:
			RealPos_X = Screen_W - b - X_Distance - this.SpriteWidth;
			break;
		case Middle:
			RealPos_X = b + (virtualViewport.getWidth() / 2) + X_Distance
					- (SpriteWidth / 2);
			break;
		case first_Third:
			RealPos_X = b + (virtualViewport.getWidth() / 3) + X_Distance
					- (SpriteWidth / 2);
			break;
		case second_Third:
			RealPos_X = b + ((virtualViewport.getWidth() / 3) * 2) + X_Distance
					- (SpriteWidth / 2);
			break;
		default:

		}

		switch (YOrientation) {
		case Top:
			RealPos_Y = a + Y_Distance;
			break;
		case Bottom:
			RealPos_Y = Screen_H - a - Y_Distance - this.SpriteHeight;
			break;
		case Middle:
			RealPos_Y = a + (virtualViewport.getHeight() / 2) - Y_Distance
					- (SpriteHeight / 2);
			break;
		case first_Third:
			RealPos_Y = a + ((virtualViewport.getHeight() / 3)) - Y_Distance
					- (SpriteHeight / 2);
			break;
		case second_Third:
			RealPos_Y = a + ((virtualViewport.getHeight() / 3) * 2)
					- Y_Distance - (SpriteHeight / 2);
			break;
		default:

		}

		setRealPos(RealPos_X, RealPos_Y);
	}
}
