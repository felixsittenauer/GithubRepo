package Screen;

import java.util.ArrayList;

import Actor.AnimatedActor;
import Actor.AnimationSequence;
import Input.BaseActorButton;
import Input.BaseButton;
import Manager.ResourceManager;
import Manager.ScreenManager;
import Manager.ScreenManager.Screen_Type;
import ScreenResolution.ProjectionHandler;
import Sprite.BaseSprite;
import Sprite.BaseSprite.FloatOrientation;
import Tag.GfxTags;
import Tag.MiscTags;
import Tag.AudioTags;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.sun.org.glassfish.gmbal.ManagedObjectManager;

import de.Infinity.ProjectR.ProjectRMain;

/**
 * @author Christoph
 * @date 12.08.14
 * 
 * @zuletzt_bearbeitet: Christoph | 14.08.14
 */

public class Screen_Menu extends BaseScreen {

	private BaseSprite Menu;
	private BaseSprite Branch;
	private BaseSprite Cloud1, Cloud2;

	private boolean TouchDown, OwlAwake;

	private enum Menu_State {
		Default, DomainSlct, SwitchforGame, SwitchforDomain, GameSlct, SwitchforOptions, Options
	};

	private Menu_State state;

	// Sprites/Buttons des MenuInterfaces
	private BaseSprite MI01;
	private BaseButton MI02, MI03, MI04, MI05;
	
	private BaseButton SleepingOwl;
	private AnimatedActor sleepingZanimation;
	

	// MI01 : Hintergrund
	// MI02 : Test
	// MI03 : Spielen
	// MI04 : Impressum
	// MI05 : Optionen

	// Scrollable Liste für die verfügbaren Spielbereiche ...
	private Table Container;
	private Table table;
	private Table GameTable;
	private Table OptionTable;
	private ScrollPane scroll;

	// Icons für die Spielbereiche
	private ArrayList<ImageButton> DomainIcons;

	// Zurückbutton
	private ImageButton BackButton;

	private int ListIndex;

	public Screen_Menu(ProjectRMain nProjectR) {
		super(nProjectR, 0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Screen_Type getScene_Type() {
		return Screen_Type.Menu;
	}

	@Override
	protected void OnCreate() {
		batch = new SpriteBatch();

		// Sound m =
		// Manager.ResourceManager.getInstance().returnSound(AudioTags.TAG_MUSIC_test);
		// long Sound_instance_id = m.play(0.4f);
		// m.setPitch(Sound_instance_id, 0.7f);
		//

		TouchDown = false;

		state = Menu_State.Default;

		// Initialisierung des Hintergrunds
		Menu = new BaseSprite(GfxTags.TAG_ATLAS_MENU_BACKGROUND, "MenuBackground", 0, 0);

		Branch = new BaseSprite(GfxTags.TAG_ATLAS_MENU_BACKGROUND, "ast", -65, 90);

		// Initialisierung der Wolken :3
		Cloud1 = new BaseSprite(GfxTags.TAG_ATLAS_MENU_BACKGROUND, "Cloud1", 20, 170);
		Cloud2 = new BaseSprite(GfxTags.TAG_ATLAS_MENU_BACKGROUND, "Cloud2", 700, 150);

		Cloud1.setXSpeed(100);
		Cloud2.setXSpeed(50);

		// Initialisierung des Interfaces
		MI01 = new BaseSprite(GfxTags.TAG_ATLAS_MENU_INTERFACE, "MI_Background", 800, 0);
		// relative Positionen = floating elements
		MI02 = new BaseButton(this, GfxTags.TAG_ATLAS_MENU_INTERFACE, "MI_Test", 0, 0);
		MI03 = new BaseButton(this, GfxTags.TAG_ATLAS_MENU_INTERFACE, "MI_Spielen", 0, 0);
		MI04 = new BaseButton(this, GfxTags.TAG_ATLAS_MENU_INTERFACE, "MI_Impressum", 0, 0);
		MI05 = new BaseButton(this, GfxTags.TAG_ATLAS_MENU_INTERFACE, "MI_Optionen", 0, 0);

		SleepingOwl = new BaseButton(this,GfxTags.TAG_ATLAS_MENU_SleepingOwl, "sleepingowl", 200, 250);

		AnimationSequence sleepingZ = new AnimationSequence("Z", ResourceManager.getInstance().returntextureatlas(GfxTags.TAG_ATLAS_MENU_SleepingZ), "sleepingZ_", 0.112f);

		sleepingZanimation = new AnimatedActor(310, 150, sleepingZ);
		sleepingZanimation.runAnimation();

		stage.addActor(sleepingZanimation);
		// Initialisierung der DomainIcons

		DomainIcons = new ArrayList<ImageButton>();

		// Scroll Zeug ....
		Container = new Table();
		table = new Table();

		Container.setFillParent(false);
		Container.setBackground(new SpriteDrawable(new BaseSprite(GfxTags.TAG_ATLAS_MENU_INTERFACE, "MenuWindow", 0, 0)));

		Container.setHeight(900);
		Container.setWidth(1100);

		Container.row();

		skin = new Skin(Gdx.files.internal(MiscTags.UI_SKIN_JSON));

		// FreeTypeFont erstellen
		/*
		 * FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
		 * Gdx.files.internal(MiscTags.FONT_GABRIOLA));
		 * 
		 * FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		 * parameter.size = 40;
		 * 
		 * ScrollFont = generator.generateFont(parameter);
		 */
		//

		// Labels = new ArrayList<Label>();

		InitDomainTable();

		scroll = new ScrollPane(table, skin);
		scroll.setScrollingDisabled(true, false);
		scroll.setSmoothScrolling(true);

		scroll.addListener(stopTouchDown);

		Container.add(scroll).width(975f).height(750f);
		Container.setPosition(50 - 1200 - virtualViewport.getVirtualWidth() / 2, 175 - virtualViewport.getVirtualHeight() / 2);
		Container.setVisible(true);

		stage.addActor(Container);

		// Spielauswahl initialisieren
		GameTable = new Table();
		OptionTable = new Table();

		BackButton = new ImageButton(new SpriteDrawable(new BaseSprite(GfxTags.TAG_ATLAS_MENU_INTERFACE, "BackButton", 0, 0)));

		BackButton.addListener(new ClickListener() {
			private int number = ListIndex;

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Container.getActions().size == 0) {
					state = Menu_State.SwitchforDomain;

					Container.addAction(Actions.moveBy(-1200, 0, 1.2f, Interpolation.pow2Out));

					System.out.println(MiscTags.GameDomains[number]);
					super.clicked(event, x, y);
				}
			}
		});

		InitGameTable();
		InitOptionsTable();
	}

	public void SwitchTables() {
		Container.clear();

		if (state == Menu_State.GameSlct) {
			scroll = new ScrollPane(GameTable, skin);
			scroll.setScrollingDisabled(true, false);
			scroll.setSmoothScrolling(true);

			scroll.addListener(stopTouchDown);
		} else if (state == Menu_State.DomainSlct) {
			scroll = new ScrollPane(table, skin);
			scroll.setScrollingDisabled(true, false);
			scroll.setSmoothScrolling(true);

			scroll.addListener(stopTouchDown);

		} else if (state == Menu_State.Options) {
			scroll = new ScrollPane(OptionTable, skin);
			scroll.setScrollingDisabled(true, false);
			scroll.setSmoothScrolling(true);

			scroll.addListener(stopTouchDown);
		}
		Container.row();

		Container.add(scroll).width(975f).height(750f);
	}

	// Tables init
	public void InitDomainTable() {
		// table.reset();

		table.pad(10).defaults().expandX().space(20);

		for (int i = 0; i < MiscTags.GameDomains.length; i++) {
			ListIndex = i;

			table.row();

			ImageButton tmpimg = new ImageButton(new SpriteDrawable(new BaseSprite(GfxTags.TAG_ATLAS_GAME_DOMAINS, "DomainIcon0" + i, 0, 0)));

			tmpimg.scaleBy(0.25f);

			tmpimg.addListener(new ClickListener() {
				private int number = ListIndex;

				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (Container.getActions().size == 0) {
						state = Menu_State.SwitchforGame;
						ListIndex = number;
						Container.addAction(Actions.moveBy(-1200, 0, 1.2f, Interpolation.pow2Out));

						System.out.println(MiscTags.GameDomains[number]);
						super.clicked(event, x, y);
					}
				}
			});

			DomainIcons.add(tmpimg);

			table.add(tmpimg).padBottom(100);

		}
	}

	public void InitGameTable() {
		GameTable.reset();

		GameTable.pad(10).defaults().expandX().space(20);

		// Immer als erstes den Zurückbutton setzen
		GameTable.row();

		GameTable.add(BackButton);

		GameTable.row();

		ImageButton tmpimg = new ImageButton(null, null, null);

		switch (ListIndex) 
		{

		case (1): {
			GameTable.row();
			tmpimg = new ImageButton(new SpriteDrawable(new BaseSprite(GfxTags.TAG_ATLAS_GAME_DOMAINS, "DomainIcon01", 0, 0)));

			tmpimg.addListener(new ClickListener() {
				private int number = ListIndex;

				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (Container.getActions().size == 0) 
					{
						ResourceManager.getInstance().Memory_load();
						ResourceManager.getInstance().Memory_get();
						ScreenManager.getInstance().show_Memory();
						

						super.clicked(event, x, y);
					}
				}
			});
		}
			break;

		case (2): {

			tmpimg = new ImageButton(new SpriteDrawable(new BaseSprite(GfxTags.TAG_ATLAS_GAME_DOMAINS, "DomainIcon03", 0, 0)));

			tmpimg.addListener(new ClickListener() {
				private int number = ListIndex;

				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (Container.getActions().size == 0) 
					{
						super.clicked(event, x, y);
						ResourceManager.getInstance().Differentiate_image_load();
						ResourceManager.getInstance().Differentiate_image_get();
						ScreenManager.getInstance().show_Differentiat_image();
							
//						ResourceManager.getInstance().Differentiate_letter_load();
//						ResourceManager.getInstance().Differentiate_letter_get();
//						ScreenManager.getInstance().show_Differentiat_letter();
						
					}
				}
			});

		}
			break;
		}

		GameTable.add(tmpimg);
	}

	public void InitOptionsTable() {
		OptionTable.reset();

		OptionTable.pad(10).defaults().expandX().space(20);

		// Immer als erstes den Zurückbutton setzen
		OptionTable.row();

		OptionTable.add(new BaseActorButton(new BaseSprite(GfxTags.TAG_ATLAS_MENU_INTERFACE, "Adult", 0, 0), new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		})).padBottom(100);;

		OptionTable.row();
		OptionTable.add(new BaseActorButton(new BaseSprite(GfxTags.TAG_ATLAS_MENU_INTERFACE, "MusikPuffer", 0, 0), new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		}));

	}

	@Override
	public void Update() {
		if (Container.getActions().size == 0) {
			if ((state == Menu_State.SwitchforDomain || state == Menu_State.SwitchforGame)) {
				if (state == Menu_State.SwitchforDomain) {
					state = Menu_State.DomainSlct;
				} else {
					state = Menu_State.GameSlct;
					InitGameTable();
				}
				SwitchTables();
				Container.addAction(Actions.moveBy(1200, 0, 1f, Interpolation.pow5Out));
			} else if (state == Menu_State.SwitchforOptions) {
				state = Menu_State.Options;
				SwitchTables();
				Container.addAction(Actions.moveBy(1200, 0, 1f, Interpolation.pow5Out));
			}
		}

		if (Gdx.input.isTouched() == true) {
			// Wenn noch gedrückt dann nichts ausführen
			if (!TouchDown) {

				TouchDown = true;
				System.out.println("Touch");
				Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touch);

				float TouchX = ProjectionHandler.Project_Input_to_Real_X(touch.x);
				float TouchY = ProjectionHandler.Project_Input_to_Real_Y(touch.y);
				

				if (MI02.IsClicked(TouchX, TouchY)) {
					System.out.println("Test");
				} else if (MI03.IsClicked(TouchX, TouchY) == true) {
					if (state == Menu_State.Default) {
						state = Menu_State.DomainSlct;
						SwitchTables();
						Container.addAction(Actions.moveBy(1200, 0, 1.0f, Interpolation.bounceOut));

					} else if (state == Menu_State.DomainSlct) {
						Container.addAction(Actions.moveBy(-1200, 0, 1.2f, Interpolation.pow2Out));
						state = Menu_State.Default;
					} else if (state == Menu_State.GameSlct) {
						state = Menu_State.SwitchforDomain;
						Container.addAction(Actions.moveBy(-1200, 0, 1.2f, Interpolation.pow2Out));
					} else if (state == Menu_State.Options) {
						state = Menu_State.SwitchforDomain;
						Container.addAction(Actions.moveBy(-1200, 0, 1.2f, Interpolation.pow2Out));
					}

					System.out.println("Spielen");
				} else if (MI04.IsClicked(TouchX, TouchY) == true) {
					System.out.println("Impressum");

				} else if (MI05.IsClicked(TouchX, TouchY) == true) {
					System.out.println("Optionen");
					if (state == Menu_State.Default) {
						state = Menu_State.Options;
						SwitchTables();
						Container.addAction(Actions.moveBy(1200, 0, 1.0f, Interpolation.bounceOut));
					} else if (state == Menu_State.DomainSlct || state == Menu_State.GameSlct) {
						state = Menu_State.SwitchforOptions;
						Container.addAction(Actions.moveBy(-1200, 0, 1.2f, Interpolation.pow2Out));
					} else if (state == Menu_State.Options) {
						Container.addAction(Actions.moveBy(-1200, 0, 1.2f, Interpolation.pow2Out));
						state = Menu_State.Default;
					}
				} else if(SleepingOwl.IsClicked(TouchX, TouchX)){
					if(state == Menu_State.Default && !OwlAwake) {
						SleepingOwl = new BaseButton(this, GfxTags.TAG_ATLAS_MENU_SleepingOwl,"Owl",  200, 250);
						sleepingZanimation.setVisible(false);
						sleepingZanimation.remove();
						
						OwlAwake = true;
					} 
				}
			}
		} else {
			// TouchUp
			TouchDown = false;
		}

		// Wolken
		Cloud1.UpdatePosition();
		Cloud2.UpdatePosition();

		if (Cloud1.getRealX() > 1708) {
			Cloud1.setRealPos(-Cloud1.getRealHeight() - 100, (float) ((Math.random() * 200) + 80));
			Cloud1.setXSpeed((float) (Math.random() * 100 + 50));
		}

		if (Cloud2.getRealX() > 1708) {
			Cloud2.setRealPos(-Cloud2.getRealWidth() - 100, (float) ((Math.random() * 200) + 80));
			Cloud2.setXSpeed((float) (Math.random() * 100 + 50));
		}
		//

	}

	@Override
	protected void Render() {
		Menu.draw(batch);

		Cloud1.draw(batch);
		Cloud2.draw(batch);

		Branch.draw(batch);

		MI01.draw(batch);
		MI02.draw(batch);
		MI03.draw(batch);
		MI04.draw(batch);
		MI05.draw(batch);

		SleepingOwl.draw(batch);

		// System.out.println("X:" + Gdx.input.getX() + " Y:" +
		// Gdx.input.getY());
	}

	@Override
	protected void RelocateFloat() {
		MI02.set_FloatingPos(20, 140, FloatOrientation.Right, FloatOrientation.Top, virtualViewport);
		MI03.set_FloatingPos(20, -(MI03.getHeight() / 2), FloatOrientation.Right, FloatOrientation.first_Third, virtualViewport);
		MI04.set_FloatingPos(20, (MI04.getHeight() / 2), FloatOrientation.Right, FloatOrientation.second_Third, virtualViewport);
		MI05.set_FloatingPos(20, 140, FloatOrientation.Right, FloatOrientation.Bottom, virtualViewport);

		MI01.setRealSize(540, virtualViewport.getHeight() - 160);

		MI01.set_FloatingPos(-80, 80, FloatOrientation.Right, FloatOrientation.Top, virtualViewport);

		// Container.setY(175- virtualViewport.getVirtualHeight()/2);
		Container.setY(-Container.getHeight() / 2);

	}

	@Override
	protected void OnPopulate() {

	}

	@Override
	protected void OnDispose() {
		stage.dispose();
		ScreenManager.getInstance().Dispose_Menu();
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
}
