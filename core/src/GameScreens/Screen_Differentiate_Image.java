package GameScreens;

import java.util.ArrayList;

import Actor.AnimatedActor;
import Actor.BaseActor;
import Input.BaseActorButton;
import Manager.ResourceManager;
import Manager.ScreenManager;
import Manager.ScreenManager.Screen_Type;
import Screen.BaseScreen;
import ScreenResolution.ProjectionHandler;
import Sprite.BaseSprite;
import Sprite.BaseSprite.FloatOrientation;
import StringFiles.Differentiate_Image_Strings;
import Tag.DataTags;
import Tag.GfxTags;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.Infinity.ProjectR.ProjectRMain;

public class Screen_Differentiate_Image extends BaseScreen {

	// ---------------------------------------------
	// Fields
	// ---------------------------------------------

	// +private
	// ++ Rect
	private int D_count, cur_D_count;

	boolean TouchDown, TouchButtonDown;
	int SequenceIndex;

	private String TextureName_1, TextureName_2, AtlasLvlName;

	

	Group Panel_Group;

	AnimatedActor testAnimation;

	private BaseActor PanelBackground, Panel_1, Panel_2;
	private BaseActorButton Backbutton,StartButton;

	private BaseSprite Background;

	private ArrayList<Vector2> AL_Differentiate_Pos; // Positionen der
														// Unterscheidungen
														// innerhalb der Rects
	ArrayList<BaseActor> AL_Pos_Sprites; // Liste der PanelObjekte
	private ArrayList<String> AL_Levels;

	private enum Screenstate {
		SwitchForNewLevel, normal
	}

	Screenstate screenstate;

	// ---------------------------------------------
	// ScreenMethods
	// ---------------------------------------------

	public Screen_Differentiate_Image(ProjectRMain nProjectR) {
		super(nProjectR, 0, 0);
	}

	@Override
	public Screen_Type getScene_Type() {
		return Screen_Type.Differentiate_Image;
	}

	@Override
	protected void OnCreate() {
		AL_Differentiate_Pos = new ArrayList<Vector2>();
		AL_Pos_Sprites = new ArrayList<BaseActor>();
		AL_Levels = new ArrayList<String>();
		
		FormatSequenceString();
		
		TouchDown = false;

		AtlasLvlName = "";
		cur_D_count = 0;
		SequenceIndex = -1;

	

		screenstate = Screenstate.SwitchForNewLevel;

		Backbutton = new BaseActorButton(new BaseSprite(GfxTags.TAG_ATLAS_DIFFERENTIATE, "ExitButton", 0, 0), new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if (!TouchButtonDown) {
					Manager.ResourceManager.getInstance().Menu_load();
					Manager.ResourceManager.getInstance().Menu_get();
					Manager.ScreenManager.getInstance().show_Menu();
					TouchButtonDown = true;
				}
			}
		});

		stage.addActor(Backbutton);
		
		StartButton = new BaseActorButton(new BaseSprite(GfxTags.TAG_ATLAS_DIFFERENTIATE, "StartButton", 0, 0), new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if (!TouchButtonDown) {
					screenstate = Screenstate.SwitchForNewLevel;
					StartButton.remove();
				}
			}
		});
		
		stage.addActor(StartButton);

		Background = new BaseSprite(GfxTags.TAG_ATLAS_DIFFERENTIATE, "DifferentiateBackground", 0, 0);

		screenstate = Screenstate.normal;
	}

	@Override
	protected void Update() {

		switch (screenstate) {

		case SwitchForNewLevel: {
			if (Panel_Group == null || Panel_Group.getActions().size == 0) {
				System.out.println(1);
				load_next_Level();
				init_next_Level();
				Panel_Group.addAction(Actions.moveBy(0, 1200, 2.0f, Interpolation.exp5));
				screenstate = Screenstate.normal;
			}
		}
		default: {

		}

		}

		if (!Gdx.input.isTouched()) {
			TouchDown = false;
			TouchButtonDown = false;
		} else {
			if (!TouchDown) {
				TouchDown = true;
				System.out.println("Touch");
				Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touch);

				float TouchX = ProjectionHandler.Project_Input_to_Real_X(touch.x);
				float TouchY = ProjectionHandler.Project_Input_to_Real_Y(touch.y);
				checkTouched(TouchX, TouchY);
			}

		}
	}

	@Override
	protected void Render() {
		Background.draw(batch);
	}

	@Override
	protected void RelocateFloat() {
		Backbutton.setFloatingPos(10, 20, FloatOrientation.Left, FloatOrientation.Bottom, virtualViewport);
		StartButton.setFloatingPos(0, 0, FloatOrientation.Middle, FloatOrientation.Middle, virtualViewport);
	}

	@Override
	protected void OnPopulate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void OnDispose() {
		ScreenManager.getInstance().Dispose_Differentiate_image();
	}

	@Override
	protected void OnPause() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void OnResume() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void OnBackButtonPressed() {
		// TODO Auto-generated method stub

	}

	// ---------------------------------------------
	// Private
	// ---------------------------------------------

	private void load_next_Level() {
		SequenceIndex++;
		resetLists();
		FormatLvlString();
	}

	private void init_next_Level() {
		removeOldPanelActors();
		initPanelActors();
		initCheckActors(Panel_1);
		initCheckActors(Panel_2);
	}

	private void removeOldPanelActors() {
		if (Panel_Group != null) {
			Panel_Group.remove();
			Panel_Group.clear();
		}
	}

	private void resetLists() {
		AL_Differentiate_Pos = new ArrayList<Vector2>();
		AL_Pos_Sprites = new ArrayList<BaseActor>();
		TouchDown = false;
		cur_D_count = 0;
	}

	private void initCheckActors(BaseActor panel) {
		BaseSprite right_1 = new BaseSprite(GfxTags.TAG_ATLAS_DIFFERENTIATE, "right_yellow", 0, 0);

		for (int h = 0; h < AL_Differentiate_Pos.size(); h++) {
			BaseSprite right = new BaseSprite(GfxTags.TAG_ATLAS_DIFFERENTIATE, "right_yellow", panel.getRealX() + AL_Differentiate_Pos.get(h).x - (right_1.getWidth() / 2), panel.getRealY()
					+ AL_Differentiate_Pos.get(h).y - (right_1.getHeight() / 2));
			BaseActor tmpActor = new BaseActor(right);
			tmpActor.setVisible(false);

			AL_Pos_Sprites.add(tmpActor);
			Panel_Group.addActor(tmpActor);
		}
		// Panel_Group.setPosition(ProjectionHandler.Project_Real_to_Virtualviewport_X(500),
		// ProjectionHandler.Project_Real_to_Virtualviewport_Y(PanelBackground.getRealY()+1200,PanelBackground.getHeight()));
		stage.addActor(Panel_Group);
	}

	private void initPanelActors() {
		Panel_1 = new BaseActor(new BaseSprite(AtlasLvlName, TextureName_1, 355, 202 + 1200));
		Panel_2 = new BaseActor(new BaseSprite(AtlasLvlName, TextureName_2, 961, 202 + 1200));
		PanelBackground = new BaseActor(new BaseSprite(GfxTags.TAG_Atlas_DIFFERENTIATE_IMAGE, "CardBackground", 280, 154 + 1200));

		Panel_Group = new Group();
		Panel_Group.addActor(PanelBackground);
		Panel_Group.addActor(Panel_1);
		Panel_Group.addActor(Panel_2);

	}

	private void FormatSequenceString() {
		String Sequence = ResourceManager.getInstance().returnValue(DataTags.tmp_Differentiate_Image_lvlSequenceString);
		String Lines[] = Sequence.split(":");
		for(int i = 0;i<Lines.length;i++){
			AL_Levels.add(Lines[i]);
		}
	}
	
	private void FormatLvlString() {
		int lvlIndex = Integer.parseInt(AL_Levels.get(SequenceIndex));
		String lvl = Differentiate_Image_Strings.getString(lvlIndex);

		String TextureNames = lvl.split("#")[0];

		String TxLines[] = TextureNames.split(":");

		AtlasLvlName = Differentiate_Image_Strings.getAtlasName(lvl);
		TextureName_1 = TxLines[1];
		TextureName_2 = TxLines[2];

		lvl = lvl.split("#")[1];
		String[] lines = lvl.substring(0, lvl.length()).split(";");

		int i_lvl = 0;
		while (lines[i_lvl].charAt(0) != '%' || i_lvl > 200) {
			String line = lines[i_lvl];
			int x_Pos = Integer.parseInt(line.split(":")[0]);
			int y_Pos = Integer.parseInt(line.split(":")[1]);

			Vector2 vec = new Vector2(x_Pos, y_Pos);
			AL_Differentiate_Pos.add(vec);

			i_lvl++;
		}

		D_count = AL_Differentiate_Pos.size();
	}

	private void checkTouched(float TouchX, float TouchY) {

		for (int i = 0; i < AL_Pos_Sprites.size() / 2; i++) {
			BaseActor ac_1 = AL_Pos_Sprites.get(i);
			BaseActor ac_2 = AL_Pos_Sprites.get(i + D_count);

			if (((TouchX > ac_1.getRealX() && TouchX < ac_1.getRealX() + ac_1.getWidth()) && (TouchY > ac_1.getRealY() - 1200 && TouchY < ac_1.getRealY() - 1200 + ac_1.getHeight()))
					|| ((TouchX > ac_2.getRealX() && TouchX < ac_2.getRealX() + ac_2.getWidth()) && (TouchY > ac_2.getRealY() - 1200 && TouchY < ac_2.getRealY() - 1200 + ac_2.getHeight()))) {

				ac_1.setVisible(true);
				ac_2.setVisible(true);
				cur_D_count++;

				if (D_count == cur_D_count) {
					Panel_Group.addAction(Actions.moveBy(0, -1200, 1.0f, Interpolation.exp5));
					screenstate = Screenstate.SwitchForNewLevel;
				}
			}
		}

	}
}
