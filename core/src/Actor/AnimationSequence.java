package Actor;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

public class AnimationSequence extends BaseDrawable 
{
	private Animation animation;
	private String AnimationName;
	private float stateTime;
	private boolean looping, runsequence;

	public AnimationSequence(String AnimationName, TextureAtlas atlas, String SequenceName, float Frameduration) {
		initAnimation(AnimationName,atlas,SequenceName,Frameduration);
		setAnimation(animation, true);
		runsequence = false;
		looping = true;
		stateTime = 0;
	}
	


	public AnimationSequence(String AnimationName, TextureAtlas atlas, String SequenceName, float Frameduration, boolean Looping) {
		initAnimation(AnimationName,atlas,SequenceName,Frameduration);
		setAnimation(animation, Looping);
		looping = Looping;
		stateTime = 0;
	}

	@Override
	public void draw(Batch batch, float x, float y, float width, float height) {
		super.draw(batch, x, y, width, height);

		if(runsequence)
		{
			stateTime += Gdx.graphics.getDeltaTime();
			batch.draw(animation.getKeyFrame(stateTime, this.looping), x, y, width, height);
		}
		else
			batch.draw(animation.getKeyFrame(0, this.looping), x, y, width, height); // zeichnet erstes Frame
	}

	private void initAnimation(String animationName, TextureAtlas atlas, String sequenceName, float frameduration) {
		AnimationName = animationName;
		animation = new Animation(frameduration, atlas.findRegions(sequenceName));
	}
	
	public void setAnimation(Animation animation, boolean looping) {
		this.animation = animation;
		this.looping = looping;
		setMinWidth(Math.abs(animation.getKeyFrame(0).getRegionWidth()));
		setMinHeight(Math.abs(animation.getKeyFrame(0).getRegionHeight()));
	}

	public Animation getAnimation() {
		return animation;
	}

	public void reset() {
		stateTime = 0;
	}
	
	public void run ()
	{
		runsequence = !runsequence;
		reset ();
	}
	
	public boolean IsRunning()
	{
		return runsequence;
	}
	
	public int retFrameIndex ()
	{
		return animation.getKeyFrameIndex(stateTime);
	}
	
	public boolean IsFinished()
	{
		return animation.isAnimationFinished(stateTime);
	}
	
	public void reverseLoop ()
	{
		if(animation.getPlayMode() != Animation.PlayMode.LOOP_REVERSED)
			animation.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
		else
			animation.setPlayMode(Animation.PlayMode.NORMAL);
	}
	
	public boolean IsReversed ()
	{
		return animation.getPlayMode() == Animation.PlayMode.LOOP_REVERSED;
	}
	
	public String getAnimationName(){
		return AnimationName;
	}
}
