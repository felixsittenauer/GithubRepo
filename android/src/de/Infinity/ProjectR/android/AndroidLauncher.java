package de.Infinity.ProjectR.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import de.Infinity.ProjectR.ProjectRMain;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		 config.useAccelerometer = false;
	      config.useCompass = false;
		
		initialize(new ProjectRMain(), config);
	}
}
