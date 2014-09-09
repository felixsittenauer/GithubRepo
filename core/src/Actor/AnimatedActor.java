package Actor;

import java.util.HashMap;

import ScreenResolution.ProjectionHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class AnimatedActor extends Image {
	// -----------------------------------------------
	// Fields
	// -----------------------------------------------

	HashMap<String, AnimationSequence> HM_Animations;
	AnimationSequence cur_Animation;
	boolean looping;
	float RealX, RealY;
	int BaseFrame; // Das Frame, das angezeigt werden soll, wenn die Animation
				   // im Stillstand steht

	// -----------------------------------------------
	// Konstuktor
	// -----------------------------------------------
	public AnimatedActor(float X, float Y, AnimationSequence InitAnimation) {
		super(InitAnimation);
		HM_Animations = new HashMap<String, AnimationSequence>();
		addAnimationSequence(InitAnimation);
		RealY = Y;
		RealX = X;
		setAnimationPos(InitAnimation.getAnimationName());
		looping = true;
	}
	
	public AnimatedActor( AnimationSequence InitAnimation) {
		super(InitAnimation);
		HM_Animations = new HashMap<String, AnimationSequence>();
		addAnimationSequence(InitAnimation);
		RealY = 0;
		RealX = 0;
		setAnimation(InitAnimation.getAnimationName());
		looping = true;
	}

	// -----------------------------------------------
	// @Override - Actor
	// -----------------------------------------------
	@Override
	public void act(float delta) {
		super.act(delta);
	}

	// -----------------------------------------------
	// Logic
	// -----------------------------------------------

	public void addAnimationSequence(AnimationSequence Sequence) {
		HM_Animations.put(Sequence.getAnimationName(), Sequence);

	}

	public void setAnimationPos(String AnimName) {
		cur_Animation = HM_Animations.get(AnimName);
		this.setDrawable(cur_Animation);
		setPosition(ProjectionHandler.Project_Real_to_Virtualviewport_X(RealX),
				ProjectionHandler.Project_Real_to_Virtualviewport_Y(RealY, Math.abs(cur_Animation.getAnimation().getKeyFrame(0).getRegionWidth())));
	}
	
	// Für Actors in TableLayouts
	public void setAnimation(String AnimName) {
		cur_Animation = HM_Animations.get(AnimName);
		this.setDrawable(cur_Animation);
	}
	
	public void runAnimation()
	{
		cur_Animation.run();
	}

	public void reset() {
		cur_Animation.reset();
	}
	
	public boolean IsAnimationat(int Index)
	{
		return cur_Animation.retFrameIndex() == Index;
	}
	
	public boolean IsAnimationfinished ()
	{
		return cur_Animation.IsFinished();
	}
	
	public String getAnimName ()
	{
		return cur_Animation.getAnimationName();
	}
	
	public AnimationSequence getAnimation ()
	{
		return cur_Animation;
	}
}
