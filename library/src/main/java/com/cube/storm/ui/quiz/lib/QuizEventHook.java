package com.cube.storm.ui.quiz.lib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.cube.storm.ui.lib.EventHook;
import com.cube.storm.ui.quiz.model.page.QuizPage;
import com.cube.storm.ui.quiz.model.quiz.QuizItem;

/**
 * Hook class used for registering for different user events within the framework
 */
public class QuizEventHook extends EventHook
{
	/**
	 * Called when a quiz has been started
	 *
	 * @param pageContext The context of the activity
	 * @param page The quiz page started
	 */
	public void onQuizStarted(@NonNull Context pageContext, @NonNull QuizPage page)
	{

	}

	/**
	 * Called when a quiz option has been selected
	 *
	 * @param pageContext The context of the activity
	 * @param itemView The view of the item selected
	 * @param item The quiz model
	 * @param answer The object of the answer selected
	 */
	public void onQuizOptionSelected(@NonNull Context pageContext, @NonNull View itemView, @NonNull QuizItem item, @NonNull Object answer)
	{

	}

	/**
	 * Called when a quiz item's answers have changed
	 *
	 * @param pageContext
	 * @param item
	 */
	public void onQuizItemAnswersChanged(@NonNull Context pageContext, @NonNull QuizItem item)
	{

	}

	/**
	 * Called when a quiz failed
	 *
	 * @param pageContext The context of the activity
	 * @param page The quiz page started
	 * @param answers The array of true/false answers
	 */
	public void onQuizLost(@NonNull Context pageContext, @NonNull QuizPage page, @NonNull boolean[] answers)
	{

	}

	/**
	 * Called when a quiz passed
	 *
	 * @param pageContext The context of the activity
	 * @param page The quiz page started
	 */
	public void onQuizWon(@NonNull Context pageContext, @NonNull QuizPage page)
	{

	}
}
