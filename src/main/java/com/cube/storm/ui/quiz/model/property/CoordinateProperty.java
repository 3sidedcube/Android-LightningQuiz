package com.cube.storm.ui.quiz.model.property;

import android.os.Parcel;

import com.cube.storm.ui.model.property.Property;

import lombok.Getter;
import lombok.Setter;

/**
 * // TODO: Add class description
 *
 * @author Matt Allen
 * @project Hazards
 */
public class CoordinateProperty extends Property
{
	@Getter @Setter private float x;
	@Getter @Setter private float y;
	@Getter @Setter private float z;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
