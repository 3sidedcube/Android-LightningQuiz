package com.cube.storm.ui.quiz.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.activity.StormActivity;
import com.cube.storm.ui.data.FragmentIntent;
import com.cube.storm.ui.data.FragmentPackage;
import com.cube.storm.ui.lib.adapter.StormPageAdapter;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.fragment.StormQuizFragment;
import com.cube.storm.ui.quiz.lib.adapter.StormQuizPageAdapter;
import com.cube.storm.ui.quiz.model.page.QuizPage;
import com.cube.storm.ui.quiz.model.quiz.QuizItem;

import java.util.ArrayList;
import java.util.Arrays;
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
public class StormQuizActivity extends ActionBarActivity implements OnPageChangeListener, OnClickListener
{
	public static final String EXTRA_PAGE = "stormui.page";
	public static final String EXTRA_URI = "stormui.uri";
	public static final String EXTRA_QUESTION = "stormquiz.question";

	protected StormPageAdapter pageAdapter;
	protected QuizPage page;
	protected ViewPager viewPager;
	protected Button previous;
	protected Button next;
	protected View progressFill;
	protected View progressEmpty;

	@Getter private boolean[] correctAnswers;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.quiz_view);

		pageAdapter = new StormQuizPageAdapter(this, getSupportFragmentManager());

		viewPager = (ViewPager)findViewById(R.id.view_pager);
		progressFill = findViewById(R.id.progress_fill);
		progressEmpty = findViewById(R.id.progress_empty);
		previous = (Button)findViewById(R.id.previous);
		next = (Button)findViewById(R.id.next);
		previous.setOnClickListener(this);
		next.setOnClickListener(this);

		if (getIntent().getExtras() == null)
		{
			Toast.makeText(this, "Failed to load page", Toast.LENGTH_SHORT).show();
			finish();

			return;
		}

		if (getIntent().getExtras().containsKey(EXTRA_PAGE))
		{
			page = (QuizPage)getIntent().getExtras().get(EXTRA_PAGE);
		}
		else if (getIntent().getExtras().containsKey(EXTRA_URI))
		{
			String pageUri = String.valueOf(getIntent().getExtras().get(EXTRA_URI));
			page = (QuizPage)UiSettings.getInstance().getViewBuilder().buildPage(Uri.parse(pageUri));
		}

		if (page != null)
		{
			loadQuiz();
		}
		else
		{
			// TODO: Error
			finish();
		}
	}

	protected void loadQuiz()
	{
		if (page.getTitle() != null)
		{
			String title = UiSettings.getInstance().getTextProcessor().process(page.getTitle().getContent());

			if (!TextUtils.isEmpty(title))
			{
				setTitle(title);
			}
		}

		Collection<FragmentPackage> fragmentPages = new ArrayList<FragmentPackage>();

		for (QuizItem question : page.getChildren())
		{
			if (question != null)
			{
				Bundle args = new Bundle();
				args.putSerializable(EXTRA_QUESTION, question);

				FragmentIntent intent = new FragmentIntent();
				intent.setFragment(StormQuizFragment.class); // TODO: Use UiSettings#intentFactory to resolve this instead
				intent.setArguments(args);

				FragmentPackage fragmentPackage = new FragmentPackage(intent, null);//UiSettings.getInstance().getApp().findPageDescriptor(page));
				fragmentPages.add(fragmentPackage);
			}
		}

		((StormPageAdapter)pageAdapter).setPages(fragmentPages);
		viewPager.setAdapter(pageAdapter);
		viewPager.setOnPageChangeListener(this);
		viewPager.setCurrentItem(0);
		pageAdapter.setIndex(0);
		updateProgress((int)((1d / pageAdapter.getCount()) * 100));

		correctAnswers = new boolean[page.getChildren().size()];
		Arrays.fill(correctAnswers, false);
	}

	protected void updateProgress(int progress)
	{
		LayoutParams fillParams = progressFill.getLayoutParams();
		LayoutParams emptyParams = progressEmpty.getLayoutParams();

		if (fillParams instanceof LinearLayout.LayoutParams && emptyParams instanceof LinearLayout.LayoutParams)
		{
			((LinearLayout.LayoutParams)fillParams).weight = 100 - progress;
			((LinearLayout.LayoutParams)emptyParams).weight = progress;
		}
	}

	@Override public void onPageScrolled(int i, float v, int i2)
	{

	}

	@Override public void onPageSelected(int pageIndex)
	{
		checkAnswers();
		updateProgress((int)(((pageIndex + 1d) / pageAdapter.getCount()) * 100));

		if (pageIndex == pageAdapter.getCount() - 1)
		{
			next.setText("Finish");
		}
		else
		{
			next.setText("Next");
		}

		if (pageIndex == 0)
		{
			previous.setEnabled(false);
		}
		else
		{
			previous.setEnabled(true);
		}
	}

	public void checkAnswers()
	{
		int pageIndex = viewPager.getCurrentItem();

		if (pageIndex - 1 > -1)
		{
			Fragment question = getSupportFragmentManager().getFragments().get(pageIndex - 1);

			if (question instanceof StormQuizFragment)
			{
				correctAnswers[pageIndex - 1] = ((StormQuizFragment)question).isCorrectAnswer();
			}
		}

		if (pageIndex > -1 && pageIndex < pageAdapter.getCount())
		{
			Fragment question = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + pageIndex);

			if (question instanceof StormQuizFragment)
			{
				correctAnswers[pageIndex] = ((StormQuizFragment)question).isCorrectAnswer();
			}
		}

		if (pageIndex + 1 < pageAdapter.getCount())
		{
			Fragment question = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + (pageIndex + 1));

			if (question instanceof StormQuizFragment)
			{
				correctAnswers[pageIndex + 1] = ((StormQuizFragment)question).isCorrectAnswer();
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
			checkAnswers();

			if (viewPager.getCurrentItem() == pageAdapter.getCount() - 1)
			{
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
			checkAnswers();

			viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
		}
	}

	public void finishQuiz()
	{
		Intent finishIntent = new Intent(this, StormQuizResultsActivity.class);
		finishIntent.putExtra(StormActivity.EXTRA_PAGE, page);
		finishIntent.putExtra(StormQuizResultsActivity.EXTRA_RESULTS, correctAnswers);
		startActivity(finishIntent);
	}
}
