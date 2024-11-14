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

import java.lang.Math;

/**
 * The math core to the Pure Pursuit alogorithm not provided by the Java Math class.
 *
 * @author Alex Bryan
 */
public final class PursuitMath {
	/** You can't instantate this class. It wouldn't make any sense, for the same reason of why you can't instantate the Math class. */
	private PursuitMath () {}

	/**
	 * The quadratic formula, though it only returns the + form, because it is the closer one.
	 * @param a the a of the formula
	 * @param b the b of the formula
	 * @param c the c of the formula
	 *
	 * @author Alex Bryan
	 */
	public static double pureQuadForm (double a, double b, double c) {
		double disc = (b * b) - (4 * a * c);
		//Thanks to Team 23423 for the information that the sum is the closer point
		return (0 > disc) ? Double.NaN : (-b + Math.sqrt(disc)) / (2 * a);
	}

	/**
	 * Ensures the inputted number is within its boundaries
	 * @param value a numeric value
	 * @param min The minimum the value can be
	 * @param max The maximum the value can be
	 */
	public static double Clamp (double value, double min, double max) {
		if (value > max)
			return max;
		return Math.max(value, min);
	}

	/**
	 * Ensures the inputted number is within the closed interval of -1 and 1
	 * @param value a numeric value
	 */
	public static double Clamp (double value) {
		return Clamp(value, -1, 1);
	}

	/** 
 	* Calculates the linear interpolation to determine parts of the waypoints
  	* @param c1 The first point
   	* @param c2 The second point
    	* @param t The parameter
 	*/
	public static double lerp (double c1, double c2, double t) {
		return c1 + t * (c2 - c1);
	}

	/**
	 * Calculates a full waypoint for the path.
	 * @param obj The location of the robot, AKA the center of the circle
	 * @param look The lookahead distance, AKA the radius of the circle
	 * @param p1 The first of two preceding points
	 * @param p2 The second of two preceding points
	 *
	 * @author Alex Bryan
	 */
	public static Pose2D waypointCalc (Pose2D obj, double look, Pose2D p1, Pose2D p2) {
		double t = pureQuadForm (
					(p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y),
					2 * ((p1.x - obj.x) * (p2.x - p1.x) + (p1.y - obj.y) * (p2.y - p1.y)),
					p1.x * p1.x - 2 * obj.x * p1.x + obj.x * obj.x + p1.y * p1.y - 2 * obj.y * p1.y + obj.y * obj.y - look * look
				);
		
		if (t == t) //check if non-NaN
			return new Pose2D (
					lerp (p1.x, p2.x, t),
					lerp (p1.y, p2.y, t),
					lerp (p1.h, p2.h, t)
				);
		else
			return new Pose2D (
					Double.NaN,
					Double.NaN,
					Double.NaN
					);
	}
}
