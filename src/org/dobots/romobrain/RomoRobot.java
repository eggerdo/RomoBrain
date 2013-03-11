package org.dobots.romobrain;

import org.dobots.robots.romo.Romo;
import org.dobots.utilities.CameraPreview;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;

public class RomoRobot extends Activity {
	
	private static Activity CONTEXT;
	
	private Romo m_oRomo;
	
	private CameraPreview m_oCamera;
	
	private ImageButton m_btnCameraToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        CONTEXT = this;
        
        m_oRomo = new Romo();
        
        setProperties();
	}
	
	private void setProperties() {

		setContentView(R.layout.main);

		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		m_oCamera = (CameraPreview) findViewById(R.id.svCamera);
		m_oCamera.setScale(false);
	
		m_btnCameraToggle = (ImageButton) findViewById(R.id.btnCameraToggle);
		if (Camera.getNumberOfCameras() <= 1) {
			m_btnCameraToggle.setVisibility(View.GONE);
		} else {
			m_btnCameraToggle.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					m_oCamera.toggleCamera();
				}
			});
		}
	}

	public static Activity getContext() {
		return CONTEXT;
	}

}
