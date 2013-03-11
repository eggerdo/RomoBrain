package org.dobots.robots;

public abstract class DifferentialRobot extends BaseRobot {
	
	protected double m_dblAxleWidth = -1;
	protected int m_nMinVelocity = -1;
	protected int m_nMaxVelocity = -1;
	protected int m_nMinRadius = -1;
	protected int m_nMaxRadius = -1;
	
	public class DriveVelocityLR {
		public int left = 0;
		public int right = 0;
	}

	public DifferentialRobot(double i_dblAxleWidth, int i_nMinVelocity, int i_nMaxVelocity, int i_nMinRadius, int i_nMaxRadius) {
		m_dblAxleWidth = i_dblAxleWidth;
		m_nMinVelocity = i_nMinVelocity;
		m_nMaxVelocity = i_nMaxVelocity;
		m_nMinRadius = i_nMinRadius;
		m_nMaxRadius = i_nMaxRadius;
	}

	private int capRadius(int i_nRadius) {
		i_nRadius = Math.min(i_nRadius, m_nMaxRadius);
		i_nRadius = Math.max(i_nRadius, -m_nMaxRadius);

		return i_nRadius;
	}
	
	protected int calculateVelocity(double i_dblSpeed) {
		i_dblSpeed = capSpeed(i_dblSpeed);
		return (int)(i_dblSpeed / 100.0 * (m_nMaxVelocity - m_nMinVelocity)) + m_nMinVelocity;
	}

	// radius has to be != 0 because 0 can't have a sign and thus we can't determine 
	// if the robot is supposed to drive left or right
	protected DriveVelocityLR calculateVelocity(double i_dblSpeed, int i_nRadius) {
		if (i_nRadius == 0) {
			error("DifferentialRobot", "radius is 0");
			return new DriveVelocityLR();
		}
		
		int nBaseVelocity = calculateVelocity(i_dblSpeed);
		int nRadius = capRadius(i_nRadius);
		int nVelocity1, nVelocity2;
		
		int nAbsRadius = Math.abs(nRadius);
		
		nVelocity1 = (int) Math.round(nBaseVelocity * (nAbsRadius + m_dblAxleWidth) / (nAbsRadius + m_dblAxleWidth / 2.0));
		nVelocity2 = (int) Math.round(nBaseVelocity * nAbsRadius / (nAbsRadius + m_dblAxleWidth / 2.0));
		
		// we have to make sure that the higher velocity of the two wheels (velocity1) cannot be more than the MAX_VELOCITY
		// if it is more, we need to scale both values down so that the higher velocity equals MAX_VELOCITY. if the lower
		// velocity would fall below 0 we set it to 0
		int nOffset = nVelocity1 - m_nMaxVelocity;
		if (nOffset > 0) {
			nVelocity1 = m_nMaxVelocity;
			nVelocity2 = Math.max(nVelocity2 - nOffset, 0);
		}
		// for the same reason we have to make sure that the lower velocity of the two wheels cannot be less than 0. if the
		// higher velocity would go above MAX_VELOCITY we set it to MAX_VELOCITY
		nOffset = -nVelocity2;
		if (nOffset > 0) {
			nVelocity1 = Math.min(nVelocity1 + nOffset, m_nMaxVelocity);
			nVelocity2 = 0;
		}
		
		DriveVelocityLR vel = new DriveVelocityLR();
		if (nRadius > 0) {
			vel.left = nVelocity2;
			vel.right = nVelocity1;
		} else if (nRadius < 0) {
			vel.left = nVelocity1;
			vel.right = nVelocity2;
		}
		return vel;
	}

	// for the time being, we just map the angle from the range [0 - 90] to the
	// range [m_nMinRadius - m_nMaxRadius] without taking into considerations
	// the real relation between radius and angle
	protected int angleToRadius(double i_dblAngle) {
		double dblAngle = (90 - Math.abs(i_dblAngle));
		int nRadius = (int) (Math.signum(i_dblAngle) * (m_nMinRadius + (m_nMaxRadius - m_nMinRadius) / 90.0 * dblAngle));
		
		return nRadius;
	}

	@Override
	public void moveForward(double i_dblSpeed, double i_dblAngle) {
		int nRadius = angleToRadius(i_dblAngle);
		moveForward(i_dblSpeed, nRadius);
	}

	@Override
	public void moveBackward(double i_dblSpeed, double i_dblAngle) {
		int nRadius = angleToRadius(i_dblAngle);
		moveBackward(i_dblSpeed, nRadius);
	}

	
}
