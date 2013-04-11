package org.dobots.romobrain;

import org.dobots.robotalk.zmq.ZmqActivity;
import org.dobots.robotalk.zmq.ZmqCoordinator;
import org.dobots.utilities.Utils;

import robots.RobotType;
import robots.gui.RobotLaunchHelper;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;

// this activity is only used as a means to start up the ZMQ Connections
// before displaying the RomoRobot Activity, without having to put that
// functionality inside the Robot Activity. When navigating back from the
// RomoRobot Activity, the onRestart() of this Activity is called and the
// Application is exited.
public class RomoBrain extends ZmqActivity {

	private static Activity CONTEXT;
	
	private ZmqCoordinator m_oZmqCoordinator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		
        CONTEXT = this;
        Utils.setContext(CONTEXT);
        
        m_oZmqCoordinator = new ZmqCoordinator();
        m_oZmqCoordinator.setup(this);

	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		
		finish();
	}

	@Override
	public void ready() {
		RobotLaunchHelper.showRobot(this, RobotType.RBT_ROMO);
	}

	@Override
	public void failed() {
		finish();
	}
	
    public Dialog onCreateDialog(int id) {
    	return m_oZmqCoordinator.onCreateDialog(id);
    }
    
	protected void onPrepareDialog(int id, Dialog dialog) {
		m_oZmqCoordinator.onPrepareDialog(id, dialog);
	}

}
