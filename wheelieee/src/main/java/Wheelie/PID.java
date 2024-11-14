/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2024, Franklin Academy Robotics
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package Wheelie;

/**
 * A PID class for Wheelie.
 * Interestingly enough, it was originally written for the 2023-2024 season of FTC.
 *
 * @author Alex Bryan 
 */
public class PID {
	/** The P value of the equation */
	public double kP;

	/** The value used to determine the growth of I in the equation */
	public double kI;

	/** The D value of the equation */
	public double kD;

	/**
	 * The constructor for the PID class
	 * @param p The P value of the equation
	 * @param i The value used to determine the growth of I in the equation. Setting this to 0 will disable I from growing.
	 * @param d The D value of the equation
	 */
	public PID (double p, double i, double d) {
		kP = p;
		kI = i;
		kD = d;
	}

	/** The I value of the equation. It's growth is determined by kI */
	private double i = 0;

	/**
	 * The maximum value I is allowed to grow to (and its opposite is the minimum).
	 * It begins at NaN (Not a Number), which the program as interprets as no limits on growth.
	 * Use capI to set it and uncapI to return it to NaN.
	 */
	private double maxI = Double.NaN;

	/**
	 * Sets the maximum value I can grow to.
	 * @param max The maximum value of I. Its opposite is the minimum value of I, so don't set it to a negative number.
	 */
	public void capI (double max) {
		maxI = max;
	}

	/** Makes it so I has no limits on its growth. */
	public void uncapI () {
		maxI = Double.NaN;
	}

	/** Resets the I value. */
	public void resetI () {
		i = 0.0;
	}

	/**
	 * Changes the values of kP, kI, and D.
	 * @param p The P value of the equation
         * @param i The value used to determine the growth of I in the equation. Setting this to 0 will disable I from growing.
         * @param d The D value of the equation
	 */
	public void changePidVals (double p, double i, double d) {
		kP = p;
		kI = i;
		kD = d;
	}

	/** Returns an array of the kP, kI, and kD variables, in that order. */
	public double[] getPidVals () {
		return new double[] {kP, kI, kD};
	}

	/** The last time recorded */
	private double prevTime = 0.0;

	/** The last error recordeed */
	private double prevErr = 0.0;

	/** Sets the start time, just in case the PID program does not start at the beginning of the program */
	public void setStartTime (double startTime) {
		prevTime = startTime;
	}

	/** Sets the start time, utilizing the System clock. */
	public void setStartTime () {
		prevTime = (System.nanoTime() / 1e9);
	}


	/**
	 * Utilizes the PID values given to calculate the output needed to reach the desired endpoint.
	 * @param target The target value
	 * @param currPos The current value
	 * @param currTime The current time since the program started or the PID class was first used (based on how it was used). DO NOT GIVE THIS AS TIME PASSED SINCE LAST CALCULATION.
	 * @return The output that would be gained from plugging these values into the PID equation
	 *
	 * @author Alex Bryan
	 */
	public double pidCalc (double target, double currPos, double currTime) {
		double currErr = target - currPos;
		double p = kP * currErr;

		i += kI * currErr * (currTime - prevTime);

		if (!(maxI == maxI)) {
			i = (i > maxI) ? maxI : i;
			i = (i < -maxI) ? -maxI : i;
		}

		double d = kD * (currErr - prevErr) / (currTime - prevTime);
		
		prevErr = currErr;
		prevTime = currTime;

		return p + i + d;
	}

	/**
	 * Utilizes the PID values given to calculate the output needed to reach the desired endpoint.
	 * @param target The target value
	 * @param currPos The current value
	 * @return The output that would be gained from plugging these values into the PID equation
	 * @author Alex Bryan
	 */
	public double pidCalc (double target, double currPos) {
                double currErr = target - currPos;
                double p = kP * currErr;

                i += kI * ((currErr * System.nanoTime() / 1e9) - prevTime);

                if (maxI == maxI) {
                        i = (i > maxI) ? maxI : i;
                        i = (i < -maxI) ? -maxI : i;
                }

                double d = kD * (currErr - prevErr) / ((System.nanoTime() / 1e9) - prevTime);

                prevErr = currErr;
                prevTime = (System.nanoTime() / 1e9);

                return p + i + d;
        }
}
