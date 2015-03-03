package com.cube.storm.ui.quiz.model.property;

import android.os.Parcel;

import com.cube.storm.ui.model.property.Property;

import lombok.Getter;

/**
 * // TODO: Add class description
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class RangeProperty extends Property
{
	@Getter private int start;
	@Getter private int length;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
