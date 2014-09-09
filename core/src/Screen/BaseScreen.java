package Screen;

import Manager.ScreenManager.Screen_Type;
import ScreenResolution.MultipleVirtualViewportBuilder;
import ScreenResolution.OrthographicCameraWithVirtualViewport;
import ScreenResolution.VirtualViewport;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import de.Infinity.ProjectR.ProjectRMain;

/**
 * 
 * @author Felix
 * @Date 1.8.14
 * @version 1.0.0
 * 
 * @zuletzt_bearbeitet: Felix | 14.08.14
 */
public abstract class BaseScreen implements Screen{

	// -----------------------------------------------
	// FIELDS
	// -----------------------------------------------
	
	private final float Stepfloat = 1 / 60f;
	private final int velocityIterationsfloat = 6;
	private final int positionIterationsfloat = 2;

	// + Variables private
	private ProjectRMain ProjectR;
	private MultipleVirtualViewportBuilder multipleVirtualViewportBuilder;

	private World PhWorld;
	Box2DDebugRenderer debugRenderer;

	// + Variables protected
	protected OrthographicCameraWithVirtualViewport camera;
	protected SpriteBatch batch;

	protected Stage stage;
	protected Skin skin;

	protected InputListener stopTouchDown;

	protected VirtualViewport virtualViewport;

	// -----------------------------------------------
	// BaseScreen
	// -----------------------------------------------

	// Mit PhysicsWorld
	public BaseScreen(ProjectRMain nProjectR, int XGravity, int YGravity) {

		this.ProjectR = nProjectR;
		
		stopTouchDown = new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.stop();
				return false;
			}
		};

		// Initialisierung der Komponenten zum DeviceRescaling
		//
		multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(
				1600, 960, 1708, 1200);
		virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		camera = new OrthographicCameraWithVirtualViewport(virtualViewport);
		// centers the camera at 0, 0 (the center of the virtual viewport)
		camera.position.set(0f, 0f, 0f);

		stage = new Stage();// new ExtendViewport(1600, 960,
											// 1708, 1200, camera));

		Gdx.input.setInputProcessor(stage);
		// Render Batch
		batch = new SpriteBatch();

		// Initialisierung der Physikworld
		PhWorld = new World(new Vector2(XGravity, YGravity), true);
		debugRenderer = new Box2DDebugRenderer();

		this.OnCreate();
	}

	// -----------------------------------------------
	// ABSTRACT
	// -----------------------------------------------

	public abstract Screen_Type getScene_Type();

	protected abstract void OnCreate(); // Wird im Konstruktor aufgerufen

	protected abstract void Update(); // Updates before Render

	protected abstract void Render(); // Render-loop

	protected abstract void RelocateFloat();

	protected abstract void OnPopulate(); // called when this screen is set as the screen
								// with game.setScreen()

	protected abstract void OnDispose(); // wird aufgerufen wenn anderes Screen mit
								// game.setScreen() aufgerufen wird

	protected abstract void OnPause(); // wird aufgerufen wenn App in "Pausemodus"
								// geändert wird

	protected abstract void OnResume(); // wird aufgerufen wenn App von "Pausemodus"
								// zurückkehrt

	protected abstract void OnBackButtonPressed(); // wird aufgerufen wenn der
											// "Zurück-Knopf" gedrückt wurde

	//protected abstract void Resize(); // Resize methode des
	// Screens

	// -----------------------------------------------
	// SCREEN OVERRIDE
	// -----------------------------------------------
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Updates
		this.Update();
		camera.update();

		
		
		// Renders
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		this.Render();
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {

		virtualViewport = multipleVirtualViewportBuilder
				.getVirtualViewport(Gdx.graphics.getWidth(),
						Gdx.graphics.getHeight());
		camera.setVirtualViewport(virtualViewport);

		camera.updateViewport();
		// centers the camera at 0, 0 (the center of the virtual viewport)
		camera.position.set(0f, 0f, 0f);

		//stage.getViewport().update(width, height, true);
		
		stage.getViewport().setCamera(camera);

		this.RelocateFloat();

		// relocate floating stuff
		// floatingButtonSprite.setPosition(virtualViewport.getVirtualWidth() *
		// 0.5f - 80, virtualViewport.getVirtualHeight() * 0.5f - 80);
		//this.Resize();
	}

	@Override
	public void show() {
		// called when this screen is set as the screen with game.setScreen();
		this.OnPopulate();
	}

	@Override
	public void hide() {
		// called when current screen changes from this to a different screen
		this.OnDispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		Manager.ResourceManager.getInstance().reloadResources();
		this.OnResume();
		
	}

	@Override
	public void dispose() {
		// never called automatically
	}
}
