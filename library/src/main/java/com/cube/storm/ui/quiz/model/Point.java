package com.cube.storm.ui.quiz.model;

/**
 * // TODO: Add class description
 *
 * @author Matt Allen
 * @documentation // TODO Reference flow doc
 * @project Hazards
 */
public class Point
{
	public Point(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public float x;
	public float y;

	@Override
	public String toString()
	{
		return String.format("(%.2f,%.2f)", x, y);
	}
}
