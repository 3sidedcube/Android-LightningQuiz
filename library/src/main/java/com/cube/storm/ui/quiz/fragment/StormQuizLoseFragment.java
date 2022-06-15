package com.cube.storm.ui.quiz.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.cube.storm.UiSettings;
import com.cube.storm.ui.activity.StormActivity;
import com.cube.storm.ui.activity.StormInterface;
import com.cube.storm.ui.model.descriptor.PageDescriptor;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.activity.StormQuizResultsActivity;
import com.cube.storm.ui.quiz.model.page.QuizPage;
import com.cube.storm.ui.quiz.model.quiz.QuizItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StormQuizLoseFragment extends Fragment implements OnClickListener, StormInterface
{
	@Getter protected QuizPage page;
	@Getter protected TextView loseTitle;
	@Getter protected Button retake;
	@Getter protected ViewGroup remember;
	@Getter protected ViewGroup embeddedLinksContainer;
	@Getter protected boolean[] answers;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(getLayoutResource(), container, false);

		loseTitle = (TextView)v.findViewById(R.id.lose_title);
		retake = (Button)v.findViewById(R.id.retake);
		remember = (ViewGroup)v.findViewById(R.id.remember_container);
		embeddedLinksContainer = (ViewGroup)v.findViewById(R.id.related_container);

		retake.setOnClickListener(this);

		return v;
	}

	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (getArguments().containsKey(StormQuizResultsActivity.EXTRA_RESULTS))
		{
			answers = getArguments().getBooleanArray(StormQuizResultsActivity.EXTRA_RESULTS);
		}

		if (getArguments().containsKey(StormQuizResultsActivity.EXTRA_QUIZ_PAGE))
		{
			page = (QuizPage) getArguments().getSerializable(StormQuizResultsActivity.EXTRA_QUIZ_PAGE);
		}

		if (getArguments().containsKey(StormActivity.EXTRA_URI))
		{
			String pageUri = getArguments().getString(StormActivity.EXTRA_URI);
			loadPage(pageUri);
		}
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu, menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.menu_home)
		{
			if (getActivity() != null)
			{
				getActivity().finish();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override public void onClick(View v)
	{

		if (v == retake)
		{
			PageDescriptor quiz = UiSettings.getInstance().getApp().findPageDescriptor(getPage());

			if (quiz != null)
			{
				Intent quizIntent = UiSettings.getInstance().getIntentFactory().getIntentForPageDescriptor(getActivity(), quiz);

				if (quizIntent != null)
				{
					getActivity().startActivity(quizIntent);
					getActivity().finish();
				}
			}
		}
		else if (v.getId() == R.id.button)
		{
			int index = (Integer)v.getTag();

			List<LinkProperty> list = new ArrayList<LinkProperty>(page.getLoseRelatedLinks());

			LinkProperty link = list.get(index);
			UiSettings.getInstance().getLinkHandler().handleLink(v.getContext(), link);
		}
	}

	@Override public int getLayoutResource()
	{
		return R.layout.quiz_lose_view;
	}

	@Override public void loadPage(String pageUri)
	{
		if (page == null)
		{
			// Use the page from the intent bundle in onActivityCreated method as this has the correct question order.
			page = (QuizPage)UiSettings.getInstance().getViewBuilder().buildPage(Uri.parse(pageUri));
		}

		if (page != null)
		{
			if (page.getLoseMessage() != null)
			{
				loseTitle.setText(UiSettings.getInstance().getTextProcessor().process(page.getLoseMessage()));
			}
			else
			{
				loseTitle.setVisibility(View.GONE);
			}

			if (answers != null)
			{
				int index = 0;
				for (QuizItem question : page.getChildren())
				{
					View row = LayoutInflater.from(getActivity()).inflate(R.layout.quiz_remember_row, remember, false);
					if (row != null && question != null)
					{
						((TextView)row.findViewById(R.id.annotation)).setText("" + (index + 1));

						((TextView)row.findViewById(R.id.title)).setText(UiSettings.getInstance().getTextProcessor().process(question.getTitle()));
						((TextView)row.findViewById(R.id.description)).setText(UiSettings.getInstance().getTextProcessor().process(answers[index] ? question.getCompletion() : question.getFailure()));
						row.findViewById(R.id.description).setVisibility(View.VISIBLE);

						remember.addView(row);
					}

					index++;
				}
			}

			if (page.getLoseRelatedLinks() != null)
			{
				embeddedLinksContainer.removeAllViews();

				int index = 0;
				for (LinkProperty link : page.getLoseRelatedLinks())
				{
					if (link == null)
					{
						continue;
					}
					final LinkProperty property = link;

					View embeddedLinkView = LayoutInflater.from(embeddedLinksContainer.getContext()).inflate(R.layout.button_embedded_link, embeddedLinksContainer, false);
					if (embeddedLinkView != null)
					{
						Button button = (Button)embeddedLinkView.findViewById(R.id.button);
						button.setText(UiSettings.getInstance().getTextProcessor().process(property.getTitle()));

						button.setOnClickListener(new View.OnClickListener()
						{
							@Override public void onClick(View v)
							{
								UiSettings.getInstance().getLinkHandler().handleLink(v.getContext(), property);
							}
						});

						embeddedLinksContainer.setVisibility(View.VISIBLE);
						embeddedLinksContainer.addView(button);
					}
				}

				if (page.getLoseRelatedLinks().size() > 0)
				{
					embeddedLinksContainer.setVisibility(View.VISIBLE);
				}
			}
		}
		else
		{
			onLoadFail();
		}
	}

	@Override public void onLoadFail()
	{
		Toast.makeText(getActivity(), "Failed to load page", Toast.LENGTH_SHORT).show();
		getActivity().finish();
	}
}
