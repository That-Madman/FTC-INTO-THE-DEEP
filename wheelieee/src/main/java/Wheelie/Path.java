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

import java.util.ArrayList;

/**
 * A series of points on an x-y graph with headings, core to the Pure Pursuit algorithm.
 *
 * @author Alex Bryan
 */
public class Path {
	/** The point at which the path starts. That is, the object's initial position. */
	private Pose2D start;

	/** The points that form a rough image of the path */
	private ArrayList<Pose2D> points = new ArrayList<Pose2D>();

	/** The constructor for the path that defaults to an empty path, starting at (0,0) headed at 0 degrees */
	public Path () {
		start = new Pose2D(0,0);
	}

	/** The constructor for the path, with the start position set */
	public Path (Pose2D start) {
		this.start = start;
	}

	/** The constructor for the path, with a path starting at (0,0) at heading 0 degrees and the following points set */
	public Path (Pose2D[] pts) {
		start = new Pose2D(0,0);
		for (Pose2D point: pts) {
			points.add(point);
		}
	}

	/** The constructor for the path, with all points in the path set */
	public Path (Pose2D start, Pose2D[] pts) {
		this.start = start;
		for (Pose2D point: pts) {
			points.add(point);
		}
	}

	/** Sets or changes the start position */
	public void setStart (Pose2D start) {
		this.start = start;
	}

	/** Add a point to the end of the list of points */
	public void appendPt (Pose2D pt) {
		points.add(pt);
	}

	/** Remove all points from the list of points */
	public void clearPts () {
		points.clear();
	}

	/** Returns the start position of the path */
	public Pose2D getStart () {
		return start;
	}

	/** Returns the Nth point on the path */
	public Pose2D getPt (int i) {
		return points.get(i);
	}

	/** Returns the number of waypoints in the path */
	public int pathLength () {
		return points.size();
	}
}
