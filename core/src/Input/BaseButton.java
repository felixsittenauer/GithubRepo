package Input;

import Screen.BaseScreen;
import Sprite.BaseSprite;

import com.badlogic.gdx.Screen;

/**
 * 
 * @author Christoph
 * @date 14.08.14
 * @zuletzt_bearbeitet: Christoph | 14.08.14
 */

public class BaseButton extends BaseSprite
{

	private	BaseScreen MotherScreen;
	
	public BaseButton(BaseScreen Mother, String textureTag, float realX, float realY)
	{
		super(textureTag, realX, realY);

		MotherScreen = Mother; 
	}

	public BaseButton(BaseScreen Mother, String AtlasTag, String RegionName, float realX,
			float realY) 
	{
		super(AtlasTag, RegionName,realX,realY);
		
		MotherScreen = Mother;
	}
	
	public boolean IsClicked (float RealTouchX, float RealTouchY)
	{
		//System.out.println("TouchX:" + TouchX + " TouchY:" + TouchY + " RTouchX" + 
		///					RealTouchX + " RTouchY:" + RealTouchY);
		
		if ( (RealTouchX >= RealX & RealTouchX <= RealX + SpriteWidth) &
			 (RealTouchY >= RealY & RealTouchY <= RealY + SpriteHeight))
		{
			return true;
		} else
		{
			return false;
		}
		
	}
}
