package com.cube.storm.ui.quiz.model.page;

import android.os.Parcel;

import com.cube.storm.ui.model.page.Page;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.model.property.TextProperty;
import com.cube.storm.ui.quiz.model.quiz.QuizItem;

import java.util.Collection;

import lombok.Getter;

/**
 * Basic list page model which has an array of {@link com.cube.storm.ui.quiz.model.quiz.QuizItem} models
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class QuizPage extends Page
{
	/**
	 * The ID of the badge object that is awarded at the end of the quiz
	 */
	@Getter private String badgeId;

	/**
	 * Collection of related links to display at the end of the quiz if the user passed the quiz
	 */
	@Getter private Collection<LinkProperty> winRelatedLinks;

	/**
	 * Collection of related links to display at the end of the quiz if the user failed the quiz
	 */
	@Getter private Collection<LinkProperty> loseRelatedLinks;

	/**
	 * Message to display if the user passed the quiz
	 */
	@Getter private TextProperty winMessage;

	/**
	 * Message to display if the user failed the quiz
	 */
	@Getter private TextProperty loseMessage;

	/**
	 * The array list of children {@link com.cube.storm.ui.quiz.model.quiz.QuizItem}
	 */
	@Getter protected Collection<QuizItem> children;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
