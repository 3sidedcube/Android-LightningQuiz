package com.cube.storm.ui.quiz.model.property;

/**
 * // TODO: Add class description
 *
 * @author Matt Allen
 * @project Hazards
 */

import android.os.Parcel;

import com.cube.storm.ui.model.property.Property;
import com.cube.storm.ui.quiz.model.Point;
import com.cube.storm.ui.quiz.model.Polygon;

import lombok.Getter;

public class ZoneProperty extends Property
{
	@Getter private CoordinateProperty[] coordinates;

	public boolean contains(float x, float y)
	{
		Polygon.Builder polyBuilder = new Polygon.Builder();

		if (coordinates != null)
		{
			for (int index = 0; index < coordinates.length; index++)
			{
				CoordinateProperty point = coordinates[index];
				polyBuilder.addVertex(new Point(point.getX(), point.getY()));
			}
		}

		boolean ret = polyBuilder.build().contains(new Point(x, y));
		return ret;
	}

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
