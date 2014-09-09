package GameScreens;

import java.util.ArrayList;
import java.util.Collections;

import Actor.AnimatedActor;
import Actor.AnimationSequence;
import Manager.ResourceManager;
import Manager.ScreenManager;
import Manager.ScreenManager.Screen_Type;
import Screen.BaseScreen;
import ScreenResolution.ProjectionHandler;
import Sprite.BaseSprite;
import Tag.GfxTags;
import Tag.MiscTags;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import de.Infinity.ProjectR.ProjectRMain;

/*
 * 
 * @author Christoph Pröschel
 * @date 21.08.14
 * @zuletzt_bearbeitet:	CP | 24.08.14 
 */

public class Screen_Memory extends BaseScreen 
{
	private ProjectionHandler project;
	// Master Layout
	private Table container; // beinhaltet CardTable und ButtonGroup
	
	// Buttons
	private VerticalGroup PanelGroup;
	
	// Spielkarten
	private ArrayList<AnimatedActor>	cards;

	// Kartennummer
	private ArrayList<Integer>		cardcipher;
	
	// welche Karte liegt offen?
	private ArrayList<Integer>		flipped;
	
	private Table	CardTable;
	
	private ScrollPane	CardScroll;
	
	
	// Muss quadratisch sein
	private static int CARDNUMBER = 12;
	private static int ROWS = 3;
	private static int COLUMNS = 4;

	private int	  CardIndex;
	
	public Screen_Memory(ProjectRMain nProjectR) 
	{
		super(nProjectR,0,0);
	}

	@Override
	public Screen_Type getScene_Type() {
		// TODO Auto-generated method stub
		return ScreenManager.Screen_Type.Memory;
	}

	@Override
	protected void OnCreate() 
	{
		project = new ProjectionHandler();
		batch = new SpriteBatch();
		
		cards = new ArrayList<AnimatedActor>();
		flipped = new ArrayList<Integer>();
		
		CardTable = new Table();
		PanelGroup = new VerticalGroup();
		container = new Table();
		CardScroll = new ScrollPane (CardTable,new Skin(Gdx.files.internal(MiscTags.UI_SKIN_JSON)));
		
		container.setFillParent(false);
		container.setSize(virtualViewport.getWidth(), virtualViewport.getHeight());
		
		generateCardCipher();

		LoadCards();
		
		PanelGroup.setSize(120, virtualViewport.getHeight());
		
		LoadPanel();
		
		container.add(CardScroll);
		container.add(PanelGroup);
		
		stage.addActor(container);
		container.setPosition(-container.getWidth()/2, -container.getHeight()/2);
		
	}
	
	private void LoadCards()
	{
		CardTable.clear();
		CardTable.setPosition(0, 0);
		CardTable.setClip(true);
		
		CardTable.setBackground(new SpriteDrawable(new BaseSprite(GfxTags.TAG_ATLAS_MEMORY_UI,"WoodPattern",0,0)));
		
		CardIndex = 0;
		
		cards.clear();
		
		for ( int g = 0; g<ROWS; g++ )
		{	
			
			for ( int h = 0; h<COLUMNS; h++)
			{
				AnimationSequence tmpseqA = new AnimationSequence("A", 
						ResourceManager.getInstance().returntextureatlas(GfxTags.TAG_ATLAS_MEMORY_BACKFLIP),
						"Backside",0.08f, false);	
				
				AnimatedActor tmpcard = new AnimatedActor(tmpseqA);
				System.out.println(GfxTags.TAG_ATLAS_MEMORY_FLIPSHEET 
						+ "0" + cardcipher.get(CardIndex).toString() + ".atlas");
				AnimationSequence tmpseqB = new AnimationSequence("B", 
						ResourceManager.getInstance().returntextureatlas(GfxTags.TAG_ATLAS_MEMORY_FLIPSHEET 
						+ "0" + cardcipher.get(CardIndex).toString() + ".atlas"),
						"Flip",0.08f, false);	
				
				tmpcard.addAnimationSequence(tmpseqB);
				
				cards.add(tmpcard);
				
				cards.get(CardIndex).addListener(new ClickListener ()
				{
					private int number = CardIndex;
					
					@Override
					public void clicked(InputEvent event, float x, float y) 
					{
						super.clicked(event, x, y);
					
						if (!cards.get(number).getAnimation().IsRunning())
						{
							FlipCard(number);
							
							if (flipped.size()>=2 & !flipped.contains(number))
							{								
								FlipCard(flipped.get(0));
								flipped.remove(0);
								flipped.add(number);
							} else if (flipped.contains(number))
							{
								flipped.remove(flipped.indexOf(number));
							} else
							{
								flipped.add(number);
							}
		
						}
					}
					
				});
				
				CardTable.add(cards.get(CardIndex));
				CardIndex ++;
			}
			CardTable.row();
		}
	
		CardScroll.setScrollingDisabled(true,true);
		//CardScroll.setSmoothScrolling(true);
		CardScroll.setSize(1588, container.getHeight());
	}

	private void LoadPanel ()
	{
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

		ImageButton tmpNewGame = new ImageButton(new SpriteDrawable(new BaseSprite(GfxTags.TAG_ATLAS_MEMORY_UI,"NewGame",0,0)));
		ImageButton tmpExitMem = new ImageButton(new SpriteDrawable(new BaseSprite(GfxTags.TAG_ATLAS_MEMORY_UI,"ExitMem",0,0)));
		
		tmpNewGame.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) 
			{	
				flipped.clear();
				generateCardCipher();
				LoadCards();
				super.clicked(event, x, y);
			}
		});
		
		tmpExitMem.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) 
			{	
				ResourceManager.getInstance().Menu_load();
				ResourceManager.getInstance().Menu_get();
				ScreenManager.getInstance().show_Menu();
				super.clicked(event, x, y);
			}
		});
		
		PanelGroup.addActor(tmpNewGame);
		PanelGroup.addActor(tmpExitMem);
	}
	
	@Override
	public void Update() 
	{
		handleAnimTransition();
	}

	@Override
	public void Render() {
		//Test.draw(batch);

	}
	
	private void handleAnimTransition ()
	{
		for(int k = 0;k<cards.size(); k++)
		{
			if (cards.get(k).IsAnimationfinished() & cards.get(k).getAnimation().IsRunning())
			{
				if (cards.get(k).getAnimName().contentEquals("A"))
				{
					if (cards.get(k).getAnimation().IsReversed())
					{
						cards.get(k).getAnimation().reverseLoop();
						cards.get(k).runAnimation();						
					} else
					{
						cards.get(k).runAnimation();
						cards.get(k).setAnimation("B");
						cards.get(k).getAnimation().reverseLoop();
						cards.get(k).runAnimation();
					}
				} else
				{
	
					if (cards.get(k).getAnimation().IsReversed())
					{
						cards.get(k).getAnimation().reverseLoop();
						cards.get(k).runAnimation();
					} else
					{
						cards.get(k).runAnimation();
						cards.get(k).setAnimation("A");
						cards.get(k).getAnimation().reverseLoop();
						cards.get(k).runAnimation();
					}

				}
				
			} 
		}
	}

	private void generateCardCipher ()
	{
		cardcipher = new ArrayList<Integer>();

		// Feld mit Werten füllen
		for( int e = 0; e < CARDNUMBER/2; e++ )
		{
			cardcipher.add(e);
			cardcipher.add(e);
		}
		
		
		
		Collections.shuffle(cardcipher);
	}
	
	private void FlipCard (int Index)
	{
		cards.get(Index).runAnimation();
	}

	@Override
	protected void OnPopulate() {

	}

	@Override
	protected void OnDispose() {
		stage.dispose();
		ScreenManager.getInstance().Dispose_Memory();
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
	protected void RelocateFloat() 
	{
		container.setSize(virtualViewport.getWidth(), virtualViewport.getHeight());
		container.setPosition(-virtualViewport.getWidth()/2, -virtualViewport.getHeight()/2);
	}


}
