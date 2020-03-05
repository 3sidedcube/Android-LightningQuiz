package com.cube.storm.ui.quiz.model.list;

import android.os.Parcel;

import com.cube.storm.ui.model.list.ListItem;
import com.cube.storm.ui.model.property.TextProperty;

import lombok.Getter;

public class QuizProgressListItem extends ListItem
{
	@Getter private String[] quizzes;
	@Getter private TextProperty progressMessage;
	@Getter private TextProperty finishMessage;

	@Override public String getClassName()
	{
		return "QuizProgressListItemView";
	}

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
