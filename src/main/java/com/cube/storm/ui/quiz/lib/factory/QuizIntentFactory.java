package com.cube.storm.ui.quiz.lib.factory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.data.FragmentIntent;
import com.cube.storm.ui.lib.factory.IntentFactory;
import com.cube.storm.ui.model.Model;
import com.cube.storm.ui.model.descriptor.PageDescriptor;
import com.cube.storm.ui.quiz.activity.StormQuizActivity;
import com.cube.storm.ui.quiz.model.page.QuizPage;

/**
 * Delegate intent factory used to inject quiz functionality into ui settings module.
 * <p/>
 * This module overrides the standard {@link com.cube.storm.UiSettings} intent factory and adds small
 * additional functionality to handle quiz classes. The original {@link com.cube.storm.ui.lib.factory.IntentFactory} is retained
 * to allow hierarchy completion.
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class QuizIntentFactory extends IntentFactory
{
	private IntentFactory superFactory;

	public QuizIntentFactory(IntentFactory superFactory)
	{
		this.superFactory = superFactory;
	}

	@Nullable @Override public FragmentIntent getFragmentIntentForPageDescriptor(@NonNull PageDescriptor pageDescriptor)
	{
		FragmentIntent ret = super.getFragmentIntentForPageDescriptor(pageDescriptor);
//		Class<? extends Model> pageType = UiSettings.getInstance().getViewFactory().getModelForView(pageDescriptor.getType());
//
//		if (QuizPage.class.isAssignableFrom(pageType))
//		{
//			if (ret != null)
//			{
//				ret.setFragment(StormQuizFragment.class);
//			}
//			else
//			{
//				Bundle args = new Bundle();
//				args.putSerializable(StormActivity.EXTRA_URI, pageDescriptor.getSrc());
//
//				ret = new FragmentIntent(StormQuizFragment.class, null, args);
//			}
//		}

		return ret;
	}

	@Nullable @Override public Intent getIntentForPageDescriptor(@NonNull Context context, @NonNull PageDescriptor pageDescriptor)
	{
		Intent ret = super.getIntentForPageDescriptor(context, pageDescriptor);
		Class<? extends Model> pageType = UiSettings.getInstance().getViewFactory().getModelForView(pageDescriptor.getType());

		if (pageType != null)
		{
			if (QuizPage.class.isAssignableFrom(pageType))
			{
				Bundle extras = ret.getExtras();
				ret = new Intent(context, StormQuizActivity.class);
				ret.putExtras(extras);
			}
		}

		return ret;
	}
}
