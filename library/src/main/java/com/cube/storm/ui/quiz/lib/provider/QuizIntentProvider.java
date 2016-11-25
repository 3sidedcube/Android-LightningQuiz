package com.cube.storm.ui.quiz.lib.provider;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.data.FragmentIntent;
import com.cube.storm.ui.lib.provider.IntentProvider;
import com.cube.storm.ui.lib.resolver.ViewResolver;
import com.cube.storm.ui.model.Model;
import com.cube.storm.ui.model.descriptor.PageDescriptor;
import com.cube.storm.ui.quiz.activity.StormQuizActivity;
import com.cube.storm.ui.quiz.activity.StormQuizResultsActivity;
import com.cube.storm.ui.quiz.fragment.StormQuizFragment;
import com.cube.storm.ui.quiz.fragment.StormQuizLoseFragment;
import com.cube.storm.ui.quiz.fragment.StormQuizWinFragment;
import com.cube.storm.ui.quiz.model.page.QuizPage;

/**
 * // TODO: Add class description
 *
 * @author Callum Taylor
 */
public class QuizIntentProvider extends IntentProvider
{
	/**
	 * Loads a fragment intent from a page descriptor by finding the model of the page type defined in {@link com.cube.storm.ui.model.descriptor.PageDescriptor#getType()}
	 * <p/>
	 * To register a specific URI or page ID, use {@link UiSettings#getIntentResolver()} instead. Order of resolve priority is
	 * `pageDescriptor` -> `pageSrc` -> `pageId` -> `pageName`
	 *
	 * @param pageDescriptor The page descriptor to load from
	 *
	 * @return The intent, or null if one was not suitable enough
	 */
	@Override @Nullable
	public FragmentIntent provideFragmentIntentForPageDescriptor(@NonNull PageDescriptor pageDescriptor)
	{
		FragmentIntent intent = null;
		ViewResolver viewResolver = UiSettings.getInstance().getViewResolvers().get(pageDescriptor.getType());
		Class<? extends Model> pageType = null;

		if (viewResolver != null)
		{
			pageType = viewResolver.resolveModel();
		}

		// Fallback to default resolution
		if (pageType != null)
		{
			if (QuizPage.class == pageType)
			{
				intent = new FragmentIntent(StormQuizFragment.class);
			}
		}

		// Handle win/lose pages
		if (StormQuizResultsActivity.URI_QUIZ_WIN.equals(pageDescriptor.getSrc()))
		{
			intent = new FragmentIntent(StormQuizWinFragment.class);
		}
		else if (StormQuizResultsActivity.URI_QUIZ_LOSE.equals(pageDescriptor.getSrc()))
		{
			intent = new FragmentIntent(StormQuizLoseFragment.class);
		}

		return intent;
	}

	/**
	 * Loads an intent from a page descriptor by finding the model of the page type defined in {@link com.cube.storm.ui.model.descriptor.PageDescriptor#getType()}
	 * <p/>
	 * To register a specific URI or page ID, use {@link UiSettings#getIntentResolver()} instead. Order of resolve priority is
	 * `pageDescriptor` -> `pageSrc` -> `pageId` -> `pageName`
	 *
	 * @param context The context used to create the intent
	 * @param pageDescriptor The page descriptor to load from
	 *
	 * @return The intent, or null if one was not suitable enough
	 */
	@Override @Nullable
	public Intent provideIntentForPageDescriptor(@NonNull Context context, @NonNull PageDescriptor pageDescriptor)
	{
		Intent intent = null;
		ViewResolver viewResolver = UiSettings.getInstance().getViewResolvers().get(pageDescriptor.getType());
		Class<? extends Model> pageType = null;

		if (viewResolver != null)
		{
			pageType = viewResolver.resolveModel();
		}

		// Fallback to default resolution
		if (pageType != null && (QuizPage.class == pageType))
		{
			intent = new Intent(context, StormQuizActivity.class);
		}

		return intent;
	}
}
