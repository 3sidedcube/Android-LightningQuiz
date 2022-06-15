package com.cube.storm.ui.quiz.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.cube.storm.ui.activity.StormInterface;
import com.cube.storm.ui.fragment.StormFragment;
import com.cube.storm.ui.model.list.ListItem;
import com.cube.storm.ui.model.page.ListPage;
import com.cube.storm.ui.quiz.activity.StormQuizActivity;
import com.cube.storm.ui.quiz.model.quiz.QuizItem;

import java.util.Collections;

import lombok.Getter;

/**
 * Storm quiz fragment used for displaying individual quiz questions. Use this class to display each
 * quiz question as a separate fragment in a view pager in conjunction to {@link com.cube.storm.ui.quiz.activity.StormQuizActivity}.
 * <p/>
 * To display a quiz page in a single list, use {@link com.cube.storm.ui.fragment.StormFragment} with {@link com.cube.storm.ui.activity.StormActivity}.
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class StormQuizFragment extends StormFragment implements StormInterface
{
	@Getter protected QuizItem question;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// If question is null after this then the failure will be reported in loadPage
		if (savedInstanceState == null)
		{
			question = (QuizItem)getArguments().get(StormQuizActivity.EXTRA_QUESTION);
		}
		else
		{
			question = (QuizItem) savedInstanceState.getSerializable("question");
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putSerializable("question", question);
	}

	public boolean isCorrectAnswer()
	{
		return question.isCorrect();
	}

	/**
	 * Override loadPage for StormQuizFragment to supply a constructued page consisting solely of the QuizItem(s) this fragment is associated with
	 *
	 * @param pageUri
	 */
	@Override public void loadPage(String pageUri)
	{
		if (question != null)
		{
			page = new ListPage(Collections.<ListItem>singleton(question));
			setAdapter();
			setTitle();
		}
		else
		{
			onLoadFail();
		}
	}
}
