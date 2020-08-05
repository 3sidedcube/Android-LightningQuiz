package com.cube.storm.ui.quiz.activity;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.QuizSettings;
import com.cube.storm.ui.activity.StormActivity;
import com.cube.storm.ui.activity.StormInterface;
import com.cube.storm.ui.data.FragmentIntent;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.lib.QuizEventHook;
import com.cube.storm.ui.quiz.model.page.QuizPage;

import lombok.Getter;

/**
 * Base storm activity that hosts a single fragment to host any {@link com.cube.storm.ui.model.page.Page} subclass.
 *
 * @author Callum Taylor
 * @project StormUI
 */
public class StormQuizResultsActivity extends AppCompatActivity implements StormInterface
{
	/**
	 * Reserved URI for won quizes
	 */
	public static final String URI_QUIZ_WIN = "storm://pages/quiz_win";

	/**
	 * Reserved URI for lost quizes
	 */
	public static final String URI_QUIZ_LOSE = "storm://pages/quiz_lose";

	/**
	 * Results extra key for results from quiz questions. Must be a boolean array of true/false the
	 * same length as the number of questions in the quiz.
	 */
	public static final String EXTRA_RESULTS = "storm_quiz.results";

	/**
	 * Results extra key for results from quiz questions. Must be a boolean array of true/false the
	 * same length as the number of questions in the quiz.
	 */
	public static final String EXTRA_QUIZ_PAGE = "storm_quiz.page";

	@Getter protected QuizPage page;
	@Getter protected boolean[] results;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(getLayoutResource());

		if (getIntent().getExtras() != null)
		{
			if (getIntent().getExtras().containsKey(StormActivity.EXTRA_URI))
			{
				String pageUri = String.valueOf(getIntent().getExtras().get(StormActivity.EXTRA_URI));
				loadPage(pageUri);
			}

			if (getIntent().getExtras().containsKey(EXTRA_RESULTS))
			{
				results = getIntent().getExtras().getBooleanArray(EXTRA_RESULTS);
			}

			if (getIntent().getExtras().containsKey(EXTRA_QUIZ_PAGE))
			{
				page = (QuizPage) getIntent().getExtras().getSerializable(EXTRA_QUIZ_PAGE);
			}

			loadResultsPage();
		}
		else
		{
			onLoadFail();
		}
	}

	protected void loadResultsPage()
	{
		boolean win = true;

		for (boolean result : results)
		{
			if (!result)
			{
				win = false;
				break;
			}
		}

		FragmentIntent fragmentIntent = UiSettings.getInstance().getIntentFactory().getFragmentIntentForPageUri(Uri.parse(win ? URI_QUIZ_WIN : URI_QUIZ_LOSE));

		if (fragmentIntent != null)
		{
			if (fragmentIntent.getArguments() == null)
			{
				fragmentIntent.setArguments(new Bundle());
			}

			fragmentIntent.getArguments().putAll(getIntent().getExtras());
			fragmentIntent.getArguments().putBooleanArray(EXTRA_RESULTS, results);
			fragmentIntent.getArguments().putSerializable(EXTRA_QUIZ_PAGE, page);

			Fragment fragment = Fragment.instantiate(this, fragmentIntent.getFragment().getName(), fragmentIntent.getArguments());
			getFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment).commit();

			if (!TextUtils.isEmpty(fragmentIntent.getTitle()))
			{
				setTitle(fragmentIntent.getTitle());
			}
		}

		for (QuizEventHook quizEventHook : QuizSettings.getInstance().getEventHooks())
		{
			if (win)
			{
				quizEventHook.onQuizWon(this, page);
			}
			else
			{
				quizEventHook.onQuizLost(this, page, results);
			}
		}
	}

	@Override public int getLayoutResource()
	{
		return R.layout.activity_view;
	}

	@Override public void loadPage(String pageUri)
	{
		if (page == null)
		{
			// Use the page from the intent bundle in onActivityCreated method as this has the correct question order.
			page = (QuizPage)UiSettings.getInstance().getViewBuilder().buildPage(Uri.parse(pageUri));
		}
	}

	@Override public void onLoadFail()
	{
		Toast.makeText(this, "Failed to load page", Toast.LENGTH_SHORT).show();
		finish();
	}
}
