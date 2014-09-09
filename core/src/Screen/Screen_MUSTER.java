package Screen;

import Manager.ScreenManager.Screen_Type;
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
public class Screen_MUSTER extends BaseScreen {

	public Screen_MUSTER(ProjectRMain ProjectR) {
		super(ProjectR,0,0);
	}

	@Override
	public Screen_Type getScene_Type() {
		// TODO Typ der Screen ändern
		return Screen_Type.IS_Splash;
	}

	@Override
	protected void OnCreate() {

	}

	@Override
	public void Update() {

	}

	@Override
	protected void Render() {

	}

	@Override
	protected void OnPopulate() {

	}

	@Override
	protected void OnDispose() {
		stage.dispose();
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
