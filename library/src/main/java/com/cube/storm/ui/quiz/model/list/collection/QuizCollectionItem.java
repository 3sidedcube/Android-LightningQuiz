package com.cube.storm.ui.quiz.model.list.collection;

import android.os.Parcel;

import com.cube.storm.ui.model.list.collection.CollectionItem;
import com.cube.storm.ui.model.property.LinkProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * // TODO: Add class description
 *
 * @author Alan Le Fournis
 * @project Storm
 */
@NoArgsConstructor @AllArgsConstructor(suppressConstructorProperties = true)
 @Accessors(chain = true) @Data
public class QuizCollectionItem extends CollectionItem
{
	protected LinkProperty quiz;
	protected String badgeId;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
