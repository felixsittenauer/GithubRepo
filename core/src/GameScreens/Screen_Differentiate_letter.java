package GameScreens;

import java.util.ArrayList;
import java.util.HashMap;

import Actor.BaseActor;
import Input.BaseActorButton;
import Manager.ResourceManager;
import Manager.ScreenManager.Screen_Type;
import Screen.BaseScreen;
import Sprite.BaseSprite;
import StringFiles.Differentiate_Letter_Strings;
import Tag.DataTags;
import Tag.GfxTags;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.Infinity.ProjectR.ProjectRMain;

public class Screen_Differentiate_letter extends BaseScreen {

	// ---------------------------------------------
	// Fields
	// ---------------------------------------------

	// +private

	// ++ Integer
	private int SequenceIndex, RightID, TouchedID;

	// ++ boolean
	private boolean TouchButtonDown;

	// ++ Strings
	private String SequenceString, AtlasLvlName;

	// ++ Sprites
	private BaseSprite background;

	// ++ Stage
	// +++ Group
	private Group PanelGroup;
	// +++ Actors
	private BaseActor ActorToFind;

	// ++ Lists
	private HashMap<Integer, BaseActorButton> HM_Panels;
	private ArrayList<String> AL_TextureNames;

	// + Enum
	ScreenState state;

	private enum ScreenState {
		normal, switchfornext
	}

	// ---------------------------------------------
	// ScreenMethods
	// ---------------------------------------------
	public Screen_Differentiate_letter(ProjectRMain nProjectR, int XGravity, int YGravity) {
		super(nProjectR, XGravity, YGravity);
	}

	@Override
	public Screen_Type getScene_Type() {
		return Screen_Type.Differentiate_letter;
	}

	@Override
	protected void OnCreate() {
		// Init
		HM_Panels = new HashMap<Integer, BaseActorButton>();
		SequenceString = ResourceManager.getInstance().returnValue(DataTags.tmp_Differentiate_Image_lvlSequenceString);
		state = ScreenState.switchfornext;
		SequenceIndex = -1;


		RightID = 0;

		// Init Sprites
		background = new BaseSprite(GfxTags.TAG_ATLAS_DIFFERENTIATE, "DifferentiateBackground", 0, 0);

		// Inits Actors
		// ActorToFind = new BaseActorButton(new BaseSprite, Listener);

		// Init Level

	}

	@Override
	protected void Update() {
		switch (state) {

		case switchfornext: {
			if (PanelGroup == null || PanelGroup.getActions().size == 0) {
				load_next_Level();
				init_next_Level();
				//PanelGroup.addAction(Actions.moveBy(0, 1200, 2.0f, Interpolation.exp5));
				state = ScreenState.normal;
			}
		}
		default: {

		}

		}

		if (!Gdx.input.isTouched()) {
			TouchButtonDown = false;
		}
	}

	@Override
	protected void Render() {
		background.draw(batch);

	}

	@Override
	protected void RelocateFloat() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void OnPopulate() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void OnDispose() {
		// TODO Auto-generated method stub

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
		unloadOldLevel();
		initPanelActors();
	}

	private void initPanelActors() {
		PanelGroup = new Group();
		if (AL_TextureNames.size() == 3) {
			int order = (int) (Math.random() * 3);
			int order_2 = (int) (Math.random() * 3);
			int order_3 = 0;
			while (order_2 == order) {
				order_2 = (int) (Math.random() * 3);
			}
			if (order + order_2 == 1) {
				order_3 = 2;
			} else if (order + order_2 == 2) {
				order_3 = 1;
			} else if (order + order_2 == 3) {
				order_3 = 0;
			}

			int ORDER[] = {order,order_2,order_3};
			for (int i = 0; i < 3; i++) {
				TouchedID = i;
				BaseActorButton tmp_Button = new BaseActorButton(new BaseSprite(AtlasLvlName, AL_TextureNames.get(i), 0,0), new ClickListener() {
					
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						if (!TouchButtonDown) {
							System.out.println("touch: " + TouchedID + " " + RightID);
							if(TouchedID == RightID){
								System.out.println("yihaaa");
							}
							TouchButtonDown = true;
						}
					}
				});

				HM_Panels.put(ORDER[i], tmp_Button);
				
			}
			
		}
		
		for(int i =0;i< HM_Panels.size();i++){
			HM_Panels.get(i).setRealPos(300+(300*i), 600);
			PanelGroup.addActor(HM_Panels.get(i));
		}
		
		stage.addActor(PanelGroup);
	}

	private void unloadOldLevel() {
		if (PanelGroup != null) {
			PanelGroup.remove();
			PanelGroup.clear();
		}
	}

	// ++
	private void resetLists() {
		HM_Panels = new HashMap<Integer, BaseActorButton>();
		AL_TextureNames = new ArrayList<String>();
	}

	private void FormatLvlString() {
		int lvlIndex = Integer.parseInt(SequenceString.split(":")[SequenceIndex]);
		String lvl = Differentiate_Letter_Strings.getString(lvlIndex);

		String TxLines[] = lvl.split(":");

		AtlasLvlName = Differentiate_Letter_Strings.getAtlasName(Integer.parseInt(TxLines[0]));

		for (int i = 1; i < TxLines.length; i++) {
			AL_TextureNames.add(TxLines[i]);
		}
	}

}
