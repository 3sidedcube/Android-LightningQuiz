package com.cube.storm.ui.quiz.model.page;

import android.os.Parcel;

import com.cube.storm.ui.model.page.Page;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.model.property.TextProperty;
import com.cube.storm.ui.quiz.model.quiz.QuizItem;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Basic list page model which has an array of {@link com.cube.storm.ui.quiz.model.quiz.QuizItem} models
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
@NoArgsConstructor @AllArgsConstructor(suppressConstructorProperties = true)
@Accessors(chain = true) @Data
public class QuizPage extends Page
{
	/**
	 * The ID of the badge object that is awarded at the end of the quiz
	 */
	protected String badgeId;

	/**
	 * Collection of related links to display at the end of the quiz if the user passed the quiz
	 */
	protected Collection<LinkProperty> winRelatedLinks;

	/**
	 * Collection of related links to display at the end of the quiz if the user failed the quiz
	 */
	protected Collection<LinkProperty> loseRelatedLinks;

	/**
	 * Message to display if the user passed the quiz
	 */
	protected TextProperty winMessage;

	/**
	 * Message to display if the user failed the quiz
	 */
	protected TextProperty loseMessage;

	/**
	 * The array list of children {@link com.cube.storm.ui.quiz.model.quiz.QuizItem}
	 */
	protected Collection<QuizItem> children;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
