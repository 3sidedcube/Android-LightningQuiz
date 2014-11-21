package com.cube.storm.ui.quiz.model.grid;

import android.os.Parcel;

import com.cube.storm.ui.model.grid.GridItem;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.model.property.TextProperty;

import lombok.Getter;

/**
 * // TODO: Add class description
 *
 * @author Luke Reed
 * @project Storm
 */
public class QuizGridItem extends GridItem
{
	@Getter private TextProperty title;
	@Getter private LinkProperty link;
	@Getter private String badgeId;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
