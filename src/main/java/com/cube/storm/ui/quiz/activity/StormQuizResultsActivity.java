package com.cube.storm.ui.quiz.activity;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.activity.StormActivity;
import com.cube.storm.ui.data.FragmentIntent;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.model.page.QuizPage;

/**
 * Base storm activity that hosts a single fragment to host any {@link com.cube.storm.ui.model.page.Page} subclass.
 *
 * @author Callum Taylor
 * @project StormUI
 */
public class StormQuizResultsActivity extends ActionBarActivity
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

	protected QuizPage page;
	protected boolean[] results;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_view);

		if (getIntent().getExtras() == null)
		{
			Toast.makeText(this, "Failed to load page", Toast.LENGTH_SHORT).show();
			finish();

			return;
		}

		if (getIntent().getExtras().containsKey(EXTRA_RESULTS))
		{
			results = getIntent().getExtras().getBooleanArray(EXTRA_RESULTS);
		}
		else
		{
			Toast.makeText(this, "Failed to load page", Toast.LENGTH_SHORT).show();
			Log.e("LightningQuiz", "No result set was provided for StormQuizResultsActivity");
			finish();

			return;
		}

		if (getIntent().getExtras().containsKey(StormActivity.EXTRA_PAGE))
		{
			page = (QuizPage)getIntent().getExtras().get(StormActivity.EXTRA_PAGE);
		}
		else if (getIntent().getExtras().containsKey(StormActivity.EXTRA_URI))
		{
			String pageUri = String.valueOf(getIntent().getExtras().get(StormActivity.EXTRA_URI));
			page = (QuizPage)UiSettings.getInstance().getViewBuilder().buildPage(Uri.parse(pageUri));
		}

		loadResultsPage();
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

			Fragment fragment = Fragment.instantiate(this, fragmentIntent.getFragment().getName(), fragmentIntent.getArguments());
			getFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment).commit();

			if (!TextUtils.isEmpty(fragmentIntent.getTitle()))
			{
				setTitle(fragmentIntent.getTitle());
			}
		}
	}
}
