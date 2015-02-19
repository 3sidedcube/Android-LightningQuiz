package com.cube.storm.ui.quiz.model.list.collection;

import android.os.Parcel;

import com.cube.storm.ui.model.list.collection.CollectionItem;
import com.cube.storm.ui.model.property.LinkProperty;

import lombok.Getter;

/**
 * // TODO: Add class description
 *
 * @author Alan Le Fournis
 * @project Storm
 */
public class QuizCollectionItem extends CollectionItem
{
	@Getter protected LinkProperty quiz;
	@Getter protected String badgeId;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
