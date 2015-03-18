package com.cube.storm.ui.quiz.view;

import com.cube.storm.ui.model.Model;
import com.cube.storm.ui.quiz.view.holder.ImageQuizItemViewHolder;
import com.cube.storm.ui.quiz.view.holder.SliderQuizItemViewHolder;
import com.cube.storm.ui.quiz.view.holder.TextQuizItemViewHolder;
import com.cube.storm.ui.quiz.view.holder.grid.QuizGridItemViewHolder;
import com.cube.storm.ui.quiz.view.holder.list.QuizCollectionItemViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderFactory;

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
	TextQuizItem(com.cube.storm.ui.quiz.model.quiz.TextQuizItem.class, TextQuizItemViewHolder.Factory.class),
	QuizGridItem(com.cube.storm.ui.quiz.model.grid.QuizGridItem.class, QuizGridItemViewHolder.Factory.class),
	QuizCollectionItem(com.cube.storm.ui.quiz.model.list.collection.QuizCollectionItem.class, QuizCollectionItemViewHolder.Factory.class),
	ImageQuizItem(com.cube.storm.ui.quiz.model.quiz.ImageQuizItem.class, ImageQuizItemViewHolder.Factory.class),
	SliderQuizItem(com.cube.storm.ui.quiz.model.quiz.SliderQuizItem.class, SliderQuizItemViewHolder.Factory.class),

	/**
	 * Quiz page
	 */
	QuizPage(com.cube.storm.ui.quiz.model.page.QuizPage.class, null);

	private Class<? extends Model> model;
	private Class<? extends ViewHolderFactory> holder;

	private Quiz(Class<? extends Model> model, Class<? extends ViewHolderFactory> holder)
	{
		this.model = model;
		this.holder = holder;
	}

	/**
	 * @return Gets the holder class of the view
	 */
	public Class<? extends ViewHolderFactory> getHolderClass()
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
