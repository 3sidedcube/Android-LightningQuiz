package com.cube.storm.ui.quiz.lib.factory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.activity.StormActivity;
import com.cube.storm.ui.data.FragmentIntent;
import com.cube.storm.ui.lib.factory.IntentFactory;
import com.cube.storm.ui.model.App;
import com.cube.storm.ui.model.Model;
import com.cube.storm.ui.model.descriptor.PageDescriptor;
import com.cube.storm.ui.model.page.Page;
import com.cube.storm.ui.quiz.activity.StormQuizActivity;
import com.cube.storm.ui.quiz.activity.StormQuizResultsActivity;
import com.cube.storm.ui.quiz.fragment.StormQuizFragment;
import com.cube.storm.ui.quiz.fragment.StormQuizLoseFragment;
import com.cube.storm.ui.quiz.fragment.StormQuizWinFragment;
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

	@Nullable @Override public FragmentIntent getFragmentIntentForPageUri(@NonNull Uri pageUri)
	{
		Class<?> fragment = null;

		if (pageUri.equals(Uri.parse(StormQuizResultsActivity.URI_QUIZ_WIN)))
		{
			fragment = StormQuizWinFragment.class;
		}
		else if (pageUri.equals(Uri.parse(StormQuizResultsActivity.URI_QUIZ_LOSE)))
		{
			fragment = StormQuizLoseFragment.class;
		}

		FragmentIntent ret = superFactory.getFragmentIntentForPageUri(pageUri);

		// Override the fragment if we need to
		if (fragment != null && (ret == null || ret.getFragment() == null))
		{
			if (ret == null)
			{
				Bundle args = new Bundle();
				args.putString(StormActivity.EXTRA_URI, pageUri.toString());

				ret = new FragmentIntent(fragment, null, args);
			}
			else
			{
				ret.setFragment(fragment);
			}
		}

		return ret;
	}

	@Nullable @Override public FragmentIntent getFragmentIntentForPageDescriptor(@NonNull PageDescriptor pageDescriptor)
	{
		Class<?> fragment = null;
		Class<? extends Model> pageType = UiSettings.getInstance().getViewFactory().getModelForView(pageDescriptor.getType());

		if (pageType != null)
		{
			if (QuizPage.class.isAssignableFrom(pageType))
			{
				fragment = StormQuizFragment.class;
			}
		}

		FragmentIntent ret = superFactory.getFragmentIntentForPageDescriptor(pageDescriptor);

		// Override the fragment if we need to
		if (fragment != null && (ret == null || ret.getFragment() == null))
		{
			if (ret == null)
			{
				Bundle args = new Bundle();
				args.putString(StormActivity.EXTRA_URI, pageDescriptor.getSrc());

				ret = new FragmentIntent(fragment, null, args);
			}
			else
			{
				ret.setFragment(fragment);
			}
		}

		return ret;
	}

	@Nullable @Override public Intent getIntentForPageDescriptor(@NonNull Context context, @NonNull PageDescriptor pageDescriptor)
	{
		Intent ret = superFactory.getIntentForPageDescriptor(context, pageDescriptor);
		Class<? extends Model> pageType = UiSettings.getInstance().getViewFactory().getModelForView(pageDescriptor.getType());

		if (pageType != null)
		{
			if (QuizPage.class.isAssignableFrom(pageType))
			{
				Bundle extras = new Bundle();

				if (ret != null)
				{
					extras = ret.getExtras();
				}

				ret = new Intent(context, StormQuizActivity.class);
				ret.putExtras(extras);
			}
		}

		return ret;
	}

	@Nullable @Override public FragmentIntent getFragmentIntentForPage(@NonNull Page pageData)
	{
		return superFactory.getFragmentIntentForPage(pageData);
	}

	@Nullable @Override public Intent getIntentForPage(@NonNull Context context, @NonNull Page pageData)
	{
		return superFactory.getIntentForPage(context, pageData);
	}

	@Nullable @Override public Intent getIntentForPageUri(@NonNull Context context, @NonNull Uri pageUri)
	{
		Intent ret = superFactory.getIntentForPageUri(context, pageUri);
		App app = UiSettings.getInstance().getApp();

		if (app != null)
		{
			for (PageDescriptor pageDescriptor : app.getMap())
			{
				if (pageUri.toString().equalsIgnoreCase(pageDescriptor.getSrc()))
				{
					return getIntentForPageDescriptor(context, pageDescriptor);
				}
			}
		}
		else
		{
			Page page = UiSettings.getInstance().getViewBuilder().buildPage(pageUri);

			if (page != null)
			{
				return getIntentForPage(context, page);
			}
		}

		return ret;
	}
}
