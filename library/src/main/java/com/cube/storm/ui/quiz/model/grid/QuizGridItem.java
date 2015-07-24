package com.cube.storm.ui.quiz.model.grid;

import android.os.Parcel;

import com.cube.storm.ui.model.grid.GridItem;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.model.property.TextProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * // TODO: Add class description
 *
 * @author Luke Reed
 * @project Storm
 */
@NoArgsConstructor @AllArgsConstructor(suppressConstructorProperties = true)
@Accessors(chain = true) @Data
public class QuizGridItem extends GridItem
{
	private TextProperty title;
	private LinkProperty link;
	private String badgeId;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
