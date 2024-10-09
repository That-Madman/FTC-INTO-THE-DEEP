/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2024, Franklin Academy Robotics
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
 * A point in 2D space, alongside the heading of the point.
 *
 * @author Alex Bryan
 */
public class Pose2D {
	/** The x-coordinate */
	public double x;

	/** The y-coordinate */
	public double y;

	/** The heading */
	public double h;

	/**
	 * The constructor for Pose2D, sans heading initialization, which defaults to 0.
	 * @param x The x component of the starting point
	 * @param y The y component of the starting point
	 * 
	 * @author Alex Bryan
	 */
	public Pose2D (double x, double y) {
		this.x = x;
		this.y = y;
		h = 0;
	}

	/**
	 * The constructor for Pose2D.
	 * @param x The x component of the starting point
	 * @param y The y component of the starting point
	 * @param h The initial heading
	 *
	 * @author Alex Bryan
	 */
	public Pose2D (double x, double y, double h) {
		this.x = x;
		this.y = y;
		this.h = h;
	}
}
