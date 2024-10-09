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
 * The Path Following algorithm that takes a list of points and lookahead to determine how
 * the robot should move towards its destination
 *
 * @author Kennedy Brundidge
 */
public class PathFollower {
	public Path path;
	public Pose2D startPt;
	public double look;

	private int wayPoint = 0;

	/** The constructor for the path follower, with the starting Pose2D, lookahead distance, and path
	 * @param startPt The starting location of the robot
	 * @param look The lookahead distance
	 * @param path The Path object of the points the robot will follow
	 *
	 * @author Kennedy Brundidge
	 */

	public PathFollower (Pose2D startPt, double look, Path path) {
		this.startPt = startPt;
		this.look = look;
		this.path = path;

	}

	/** The constructor for the path follower, with the starting Pose2D
	 * and lookahead distance (path will be initialized with no points)
	 * @param startPt The starting location of the robot
	 * @param look The lookahead distance
	 *
	 * @author Kennedy Brundidge
	 */
	public PathFollower (Pose2D startPt, double look) {
		this.startPt = startPt;
		this.look = look;
		path = new Path();
	}


	/**
	 * Returns the movement required the robot is from its next waypoint within its lookahead
	 * @param obj The Pose2D of the robot, AKA the center of the circle
	 *
	 * @author Kennedy Brundidge
	 */
	public Pose2D followPath(Pose2D obj){
		Pose2D next = new Pose2D(Double.NaN,Double.NaN);

		//Checks that robot is not approaching the last point
		if (path.pathLength() != wayPoint+2) {
			//Finds if the circle intersects with the next line/path
			next = PursuitMath.waypointCalc
					(obj, look, path.getPt(wayPoint + 1), path.getPt(wayPoint + 2));
		}

		//If circle intersects with next line, then robot can start approaching the next point
		if(!Double.isNaN(next.x)){
			wayPoint++;
		}

		//Finds a point for robot to approach
		Pose2D target = PursuitMath.waypointCalc
				(obj, look, path.getPt(wayPoint), path.getPt(wayPoint+1));

		//Moves straight to next point if PP math is returning NaN values
		if(Double.isNaN(target.x) && Double.isNaN(next.x)){ //Magic the gathering
			Pose2D t = path.getPt(wayPoint+1);
			return new Pose2D(
					t.x - obj.x,
					t.y - obj.y,
					t.h - obj.h
			);
		}

		//Finds the forward, strafe, and turn values
		Pose2D diff = new Pose2D(target.x - obj.x,
				target.y - obj.y,
				target.h - obj.h);
		double forward =  diff.x,
				strafe =  diff.y,
				turn = diff.h;

		return new Pose2D(forward, strafe, turn);
	}

	/** Returns the index of the current Pose2D in the Path */
	public int getWayPoint(){
		return wayPoint;
	}

	/** Returns the last point of the path */
	public Pose2D getLastPoint(){
		return path.getPt(path.pathLength()-1);
	}
}
