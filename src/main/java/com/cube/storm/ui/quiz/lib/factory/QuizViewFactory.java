package com.cube.storm.ui.quiz.lib.factory;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.storm.ui.lib.factory.ViewFactory;
import com.cube.storm.ui.model.Model;
import com.cube.storm.ui.quiz.view.Quiz;
import com.cube.storm.ui.view.holder.ViewHolderController;

/**
 * This is the factory class which is used by Storm to help with getting the correct view holder/controller
 * for a specific view class name.
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class QuizViewFactory extends ViewFactory
{
	private ViewFactory superFactory;

	public QuizViewFactory(ViewFactory superFactory)
	{
		this.superFactory = superFactory;
	}

	/**
	 * Gets the view holder class for a specific view name
	 *
	 * @param viewName The name of the view to lookup
	 *
	 * @return The view holder class or null if one was not found.
	 */
	@Override @Nullable
	public Class<? extends ViewHolderController> getHolderForView(String viewName)
	{
		try
		{
			return Quiz.valueOf(viewName).getHolderClass();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}

		return superFactory.getHolderForView(viewName);
	}

	/**
	 * Gets the model class for a specific view name
	 *
	 * @param viewName The name of the view to lookup
	 *
	 * @return The view model class or null if one was not found.
	 */
	@Override @Nullable
	public Class<? extends Model> getModelForView(@NonNull String viewName)
	{
		try
		{
			return Quiz.valueOf(viewName).getModelClass();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}

		return superFactory.getModelForView(viewName);
	}
}
