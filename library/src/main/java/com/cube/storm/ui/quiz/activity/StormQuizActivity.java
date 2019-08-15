package com.cube.storm.ui.quiz.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.QuizSettings;
import com.cube.storm.ui.activity.StormActivity;
import com.cube.storm.ui.activity.StormInterface;
import com.cube.storm.ui.data.FragmentIntent;
import com.cube.storm.ui.data.FragmentPackage;
import com.cube.storm.ui.lib.adapter.StormPageAdapter;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.fragment.StormQuizFragment;
import com.cube.storm.ui.quiz.lib.QuizEventHook;
import com.cube.storm.ui.quiz.lib.adapter.StormQuizPageAdapter;
import com.cube.storm.ui.quiz.model.page.QuizPage;
import com.cube.storm.ui.quiz.model.quiz.QuizItem;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;

/**
 * Storm quiz fragment used for displaying individual quiz questions. Use this class to display each
 * quiz question as a separate fragment in a view pager in conjunction to {@link com.cube.storm.ui.quiz.activity.StormQuizActivity}.
 * <p/>
 * To display a quiz page in a single list, use {@link com.cube.storm.ui.fragment.StormFragment} with {@link com.cube.storm.ui.activity.StormActivity}.
 *
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class StormQuizActivity extends AppCompatActivity implements OnPageChangeListener, OnClickListener, StormInterface
{
	public static final String EXTRA_QUESTION = "stormquiz.question";

	@Getter protected View customActionBar;

	@Getter protected StormPageAdapter pageAdapter;
	@Getter protected QuizPage page;
	@Getter protected ViewPager viewPager;
	@Getter protected Button previous;
	@Getter protected Button next;
	@Getter protected ProgressBar progressBar;

	@Getter protected String pageUri;
	@Getter protected int currentPage = 0;
	@Getter protected boolean[] correctAnswers;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(getLayoutResource());

		setCustomActionBar();

		pageAdapter = new StormQuizPageAdapter(this, getSupportFragmentManager());

		viewPager = (ViewPager)findViewById(R.id.view_pager);
		progressBar = customActionBar.findViewById(R.id.quiz_progress_bar);
		previous = (Button)findViewById(R.id.previous);
		next = (Button)findViewById(R.id.next);
		previous.setOnClickListener(this);
		next.setOnClickListener(this);

		if (savedInstanceState == null)
		{
			if (getIntent().getExtras().containsKey(StormActivity.EXTRA_URI))
			{
				pageUri = String.valueOf(getIntent().getExtras().get(StormActivity.EXTRA_URI));
				loadPage(pageUri);
			}
			else
			{
				onLoadFail();
				return;
			}
		}
		else
		{
			pageUri = savedInstanceState.getString("pageUri");
			page = (QuizPage) savedInstanceState.getSerializable("page");
			currentPage = savedInstanceState.getInt("currentPage");
			correctAnswers = savedInstanceState.getBooleanArray("correctAnswers");
		}

		loadQuiz();
	}

	private void setCustomActionBar()
	{
		if (getSupportActionBar() != null)
		{
			// Show custom logo toolbar
			ActionBar actionBar = getSupportActionBar();
			actionBar.setDisplayShowCustomEnabled(true);

			customActionBar = LayoutInflater.from(actionBar.getThemedContext()).inflate(R.layout.quiz_view_progress_nav_bar, null);
			actionBar.setCustomView(customActionBar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

			ImageView backIcon = customActionBar.findViewById(R.id.quiz_back);
			ImageView closeIcon = customActionBar.findViewById(R.id.quiz_close);

			backIcon.setOnClickListener(new OnClickListener()
			{
				@Override public void onClick(View view)
				{
					if (viewPager.getCurrentItem() == 0)
					{
						finish();
					}
					else
					{
						viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
					}
				}
			});

			closeIcon.setOnClickListener(new OnClickListener()
			{
				@Override public void onClick(View view)
				{
					finish();
				}
			});
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putSerializable("page", page);
		outState.putString("pageUri", pageUri);
		outState.putInt("currentPage", currentPage);
		outState.putBooleanArray("correctAnswers", correctAnswers);
	}

	protected void loadQuiz()
	{
		Collection<FragmentPackage> fragmentPages = new ArrayList<FragmentPackage>();

		for (QuizItem question : page.getChildren())
		{
			if (question != null)
			{
				FragmentIntent fragmentIntent = UiSettings.getInstance().getIntentFactory().getFragmentIntentForPageUri(Uri.parse(pageUri));
				fragmentIntent.getArguments().putSerializable(EXTRA_QUESTION, question);

				FragmentPackage fragmentPackage = new FragmentPackage(fragmentIntent, null);
				fragmentPages.add(fragmentPackage);
			}
		}

		pageAdapter.setPages(fragmentPages);
		viewPager.setAdapter(pageAdapter);
		viewPager.setCurrentItem(currentPage);
		viewPager.setOnPageChangeListener(this);
		updateProgress(currentPage);
	}

	/**
	 * Updates UI state to reflect a given page index. Should be called every time the page index changes
	 *
	 * @param pageIndex
	 */
	protected void updateProgress(int pageIndex)
	{
		int progress = (int)(((pageIndex + 1d) / pageAdapter.getCount()) * 100);
		progressBar.setProgress(progress);
		previous.setEnabled(pageIndex != 0);
	}

	@Override public void onPageScrolled(int i, float v, int i2)
	{

	}

	@Override public void onPageSelected(int pageIndex)
	{
		checkAnswers(currentPage);
		currentPage = pageIndex;
		updateProgress(pageIndex);
	}

	public void checkAnswers(int pageIndex)
	{
		if (pageIndex > -1 && pageIndex < pageAdapter.getCount())
		{
			Fragment question = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + pageIndex);

			if (question instanceof StormQuizFragment)
			{
				correctAnswers[pageIndex] = ((StormQuizFragment)question).isCorrectAnswer();
			}
		}
	}

	@Override public void onPageScrollStateChanged(int i)
	{

	}

	@Override public void onClick(View view)
	{
		if (view == next)
		{
			if (viewPager.getCurrentItem() == pageAdapter.getCount() - 1)
			{
				// force check last answer
				checkAnswers(viewPager.getCurrentItem());
				finishQuiz();
				finish();
			}
			else
			{
				viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
			}
		}
		else if (view == previous)
		{
			viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
		}
	}

	public void finishQuiz()
	{
		Intent finishIntent = new Intent(this, StormQuizResultsActivity.class);
		finishIntent.putExtras(getIntent().getExtras());
		finishIntent.putExtra(StormQuizResultsActivity.EXTRA_RESULTS, correctAnswers);
		startActivity(finishIntent);
	}

	@Override public int getLayoutResource()
	{
		return R.layout.quiz_view;
	}

	@Override public void loadPage(String pageUri)
	{
		page = (QuizPage)UiSettings.getInstance().getViewBuilder().buildPage(Uri.parse(pageUri));

		if (page != null)
		{
			correctAnswers = new boolean[page.getChildren().size()];
			onPageLoaded();
		}
		else
		{
			onLoadFail();
		}
	}

	@Override public void onLoadFail()
	{
		Toast.makeText(this, "Failed to load page", Toast.LENGTH_SHORT).show();
		finish();
	}

	protected void onPageLoaded()
	{
		for (QuizEventHook quizEventHook : QuizSettings.getInstance().getEventHooks())
		{
			quizEventHook.onQuizStarted(this, page);
		}
	}
}
