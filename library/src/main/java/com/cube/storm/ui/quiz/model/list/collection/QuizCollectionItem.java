package com.cube.storm.ui.quiz.model.list.collection;

import android.os.Parcel;

import com.cube.storm.ui.model.property.LinkProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor @AllArgsConstructor(suppressConstructorProperties = true)
@Accessors(chain = true) @Data
public class QuizCollectionItem extends BadgeCollectionItem
{
	protected LinkProperty quiz;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
