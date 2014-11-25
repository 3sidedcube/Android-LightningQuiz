package com.cube.storm.ui.quiz.view;

import com.cube.storm.ui.model.Model;
import com.cube.storm.ui.quiz.view.holder.ImageQuizItemHolder;
import com.cube.storm.ui.quiz.view.holder.TextQuizItemHolder;
import com.cube.storm.ui.quiz.view.holder.grid.QuizGridItemHolder;
import com.cube.storm.ui.quiz.view.holder.list.QuizCollectionItemHolder;
import com.cube.storm.ui.view.holder.ViewHolderController;

/**
 * This is the enum class with the list of all supported view types, their model classes and their
 * corresponding view holder class. This list should not be modified or overridden
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public enum Quiz
{
	/**
	 * Badge properties
	 */
	Badge(com.cube.storm.ui.quiz.model.property.BadgeProperty.class, null),

	/**
	 * Quiz questions
	 */
	TextQuizItem(com.cube.storm.ui.quiz.model.quiz.TextQuizItem.class, TextQuizItemHolder.class),
	QuizGridItem(com.cube.storm.ui.quiz.model.grid.QuizGridItem.class, QuizGridItemHolder.class),
	QuizCollectionItem(com.cube.storm.ui.quiz.model.list.collection.QuizCollectionItem.class, QuizCollectionItemHolder.class),
	ImageQuizItem(com.cube.storm.ui.quiz.model.quiz.ImageQuizItem.class, ImageQuizItemHolder.class),

	/**
	 * Quiz page
	 */
	QuizPage(com.cube.storm.ui.quiz.model.page.QuizPage.class, null);

	private Class<? extends Model> model;
	private Class<? extends ViewHolderController> holder;

	private Quiz(Class<? extends Model> model, Class<? extends ViewHolderController> holder)
	{
		this.model = model;
		this.holder = holder;
	}

	/**
	 * @return Gets the holder class of the view
	 */
	public Class<? extends ViewHolderController> getHolderClass()
	{
		return holder;
	}

	/**
	 * @return Gets the model class of the view
	 */
	public Class<? extends Model> getModelClass()
	{
		return model;
	}
}
