package ScreenResolution;

import com.badlogic.gdx.Gdx;

public class ProjectionHandler {

	public static final int VirtualScreenWidth = 1708;
	public static final int VirtualScreenHeight = 1200;

	// +++
	// + Sprites Position
	public static float Project_Real_to_Virtualviewport_X(float Real_X) {
		return Real_X - (VirtualScreenWidth / 2);
	}

	public static float Project_Real_to_Virtualviewport_Y(float Real_Y,
			float SpriteHeight) {
		return (Real_Y - (VirtualScreenHeight / 2) + SpriteHeight) * -1;
	}

	public static float Project_Virtualviewport_to_Real_X(float VP_X) {
		return VP_X + (VirtualScreenWidth / 2);
	}

	public static float Project_Virtualviewport_to_Real_Y(float VP_Y,
			float SpriteHeight) {
		return (VP_Y * -1) + (VirtualScreenHeight / 2) - SpriteHeight;
	}

	// +++
	// + Touch Position

	public static float Project_Input_to_Real_X(float X_Input) {
		return X_Input + (1708 / 2);
	}

	public static float Project_Input_to_Real_Y(float Y_Input) {
		return (Y_Input - (1200 / 2)) * -1;
	}

}
