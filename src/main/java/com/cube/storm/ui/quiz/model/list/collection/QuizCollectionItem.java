package com.cube.storm.ui.quiz.model.list.collection;

import android.os.Parcel;

import com.cube.storm.ui.model.list.collection.CollectionItem;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.model.property.TextProperty;

import lombok.Getter;

/**
 * // TODO: Add class description
 *
 * @author Alan Le Fournis
 * @project Storm
 */
public class QuizCollectionItem extends CollectionItem
{
	@Getter protected TextProperty title;
	@Getter protected LinkProperty link;
	@Getter protected String badgeId;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
