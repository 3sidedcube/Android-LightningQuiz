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

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor @AllArgsConstructor(suppressConstructorProperties = true)
@Accessors(chain = true) @Data
public class ZoneProperty extends Property
{
	protected ArrayList<CoordinateProperty> coordinates;

	public boolean contains(float x, float y)
	{
		Polygon.Builder polyBuilder = new Polygon.Builder();

		if (coordinates != null)
		{
			for (int index = 0; index < coordinates.size(); index++)
			{
				CoordinateProperty point = coordinates.get(index);
				polyBuilder.addVertex(new Point(point.getX(), point.getY()));
			}
		}

		return polyBuilder.build().contains(new Point(x, y));
	}

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
