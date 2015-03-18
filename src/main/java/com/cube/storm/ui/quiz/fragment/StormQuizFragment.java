package com.cube.storm.ui.quiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube.storm.ui.controller.adapter.StormListAdapter;
import com.cube.storm.ui.lib.helper.RecycledViewPoolHelper;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.activity.StormQuizActivity;
import com.cube.storm.ui.quiz.model.quiz.QuizItem;

import java.util.Arrays;

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
public class StormQuizFragment extends Fragment
{
	@Getter private RecyclerView listView;
	@Getter private StormListAdapter adapter;
	@Getter private QuizItem question;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.list_page_fragment_view, container, false);

		listView = (RecyclerView)v.findViewById(R.id.recyclerview);
		listView.setRecycledViewPool(RecycledViewPoolHelper.getInstance().getRecycledViewPool());
		listView.setLayoutManager(new LinearLayoutManager(getActivity()));
		listView.setItemAnimator(new DefaultItemAnimator());

		return v;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		adapter = new StormListAdapter();

		if (getArguments().containsKey(StormQuizActivity.EXTRA_QUESTION))
		{
			question = (QuizItem)getArguments().get(StormQuizActivity.EXTRA_QUESTION);
		}
		else
		{
			Toast.makeText(getActivity(), "Failed to load page", Toast.LENGTH_SHORT).show();
			getActivity().finish();
			return;
		}

		if (question != null)
		{
			adapter.setItems(Arrays.asList(question));
		}

		listView.setAdapter(adapter);
	}

	public boolean isCorrectAnswer()
	{
		// get the view from the adapter - THIS MAY CHANGE
		return ((QuizItem)adapter.getItem(adapter.getItemCount() - 1)).isCorrect();
	}
}
