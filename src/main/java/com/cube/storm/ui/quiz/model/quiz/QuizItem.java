package com.cube.storm.ui.quiz.model.quiz;

import android.os.Parcel;

import com.cube.storm.ui.model.Model;
import com.cube.storm.ui.model.property.TextProperty;

import lombok.Getter;

/**
 * Base model for quiz question models
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class QuizItem extends Model
{
	@Getter private TextProperty title;
	@Getter private TextProperty failure;
	@Getter private TextProperty completion;
	@Getter private TextProperty hint;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
