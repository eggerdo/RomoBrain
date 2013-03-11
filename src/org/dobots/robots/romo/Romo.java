package org.dobots.robots.romo;

import java.io.IOException;

import org.dobots.robots.BaseRobot;
import org.dobots.robots.DifferentialRobot;
import org.dobots.robots.RobotType;

import com.romotive.library.RomoCommandInterface;

public class Romo extends DifferentialRobot {
	
	private double m_dblBaseSpeed = 100.0;
	
	private RomoCommandInterface m_oController;
	
	public Romo() {
		super(RomoTypes.AXLE_WIDTH, RomoTypes.MIN_VELOCITY, RomoTypes.MAX_VELOCITY, RomoTypes.MIN_RADIUS, RomoTypes.MAX_RADIUS);
		
		m_oController = new RomoCommandInterface();
	}

	@Override
	public RobotType getType() {
		return RobotType.RBT_ROMO;
	}

	@Override
	public String getAddress() {
		return "";
	}

	@Override
	public void destroy() {
		m_oController.shutdown();
	}

	@Override
	public void connect() throws IOException {
		// nothing to do, Romo has to be connected over the audio wire
	}

	@Override
	public void disconnect() {
		// nothing to do, see connect
		
	}

	@Override
	public boolean isConnected() {
		return true;
	}

	@Override
	public void enableControl(boolean i_bEnable) {
		// nothing to do, control always enabled
	}
	
	private void sendMotorCommand(int i_nLeftVel, int i_nRightVel) {
		// front and back velocity range is not equally distributed so we need to
		// cap the top front velocity to 255
		i_nLeftVel = Math.min(i_nLeftVel, 255);
		i_nRightVel = Math.min(i_nRightVel, 255);
		m_oController.playMotorCommand(i_nLeftVel, i_nRightVel);
	}

	@Override
	public void moveForward(double i_dblSpeed) {
		int nVelocity = calculateVelocity(i_dblSpeed);
		
		// velocity has to be VELOCITY_OFFSET by 128 (which is the value for stop), 
		// since forward velocity is from [129..255]
		nVelocity += RomoTypes.VELOCITY_OFFSET;
		sendMotorCommand(nVelocity, nVelocity);
	}

	@Override
	public void moveForward(double i_dblSpeed, int i_nRadius) {
		DriveVelocityLR oVelocity = calculateVelocity(i_dblSpeed, i_nRadius);
		
		// velocities have to be VELOCITY_OFFSET by 128 (which is the value for stop),
		// since forward velocity is from [129..255]
		oVelocity.left += RomoTypes.VELOCITY_OFFSET;
		oVelocity.right += RomoTypes.VELOCITY_OFFSET;
		sendMotorCommand(oVelocity.left, oVelocity.right);
	}

	@Override
	public void moveBackward(double i_dblSpeed) {
		int nVelocity = calculateVelocity(i_dblSpeed);
		
		// velocity has to be subtracted from 128 (which is the value for stop), 
		// since backward velocity is from [127..0]
		nVelocity = RomoTypes.VELOCITY_OFFSET - nVelocity;
		sendMotorCommand(nVelocity, nVelocity);
	}

	@Override
	public void moveBackward(double i_dblSpeed, int i_nRadius) {
		DriveVelocityLR oVelocity = calculateVelocity(i_dblSpeed, i_nRadius);

		// velocities have to be subtracted from 128 (which is the value for stop), 
		// since backward velocity is from [127..0]
		oVelocity.left = RomoTypes.VELOCITY_OFFSET - oVelocity.left;
		oVelocity.right = RomoTypes.VELOCITY_OFFSET - oVelocity.right;
		sendMotorCommand(oVelocity.left, oVelocity.right);
	}

	@Override
	public void rotateClockwise(double i_dblSpeed) {
		int nVelocity = calculateVelocity(i_dblSpeed);
		
		// velocities have to be VELOCITY_OFFSET by 128 (for forward) and subtracted from
		// 128 (for backward)
		DriveVelocityLR oVelocity = new DriveVelocityLR();
		oVelocity.left = RomoTypes.VELOCITY_OFFSET + nVelocity;
		oVelocity.right = RomoTypes.VELOCITY_OFFSET - nVelocity;
		sendMotorCommand(oVelocity.left, oVelocity.right);
	}

	@Override
	public void rotateCounterClockwise(double i_dblSpeed) {
		int nVelocity = calculateVelocity(i_dblSpeed);
		
		// velocities have to be VELOCITY_OFFSET by 128 (for forward) and subtracted from
		// 128 (for backward)
		DriveVelocityLR oVelocity = new DriveVelocityLR();
		oVelocity.left = RomoTypes.VELOCITY_OFFSET - nVelocity;
		oVelocity.right = RomoTypes.VELOCITY_OFFSET + nVelocity;
		sendMotorCommand(oVelocity.left, oVelocity.right);
	}

	@Override
	public void moveStop() {
		sendMotorCommand(RomoTypes.STOP, RomoTypes.STOP);
	}

	@Override
	public void executeCircle(double i_dblTime, double i_dblSpeed) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setBaseSpeed(double i_dblSpeed) {
		m_dblBaseSpeed = i_dblSpeed;
	}

	@Override
	public double getBaseSped() {
		return m_dblBaseSpeed;
	}

	@Override
	public void moveForward() {
		moveForward(m_dblBaseSpeed);
	}

	@Override
	public void moveBackward() {
		moveBackward(m_dblBaseSpeed);
	}

	@Override
	public void rotateCounterClockwise() {
		rotateCounterClockwise(m_dblBaseSpeed);
	}

	@Override
	public void rotateClockwise() {
		rotateClockwise(m_dblBaseSpeed);
	}

	@Override
	public void moveLeft() {
		assert false : "not available";
	}

	@Override
	public void moveRight() {
		assert false : "not available";
	}
	
}
