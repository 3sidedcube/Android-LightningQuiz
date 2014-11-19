package com.cube.storm.ui.quiz.fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.activity.StormActivity;
import com.cube.storm.ui.controller.adapter.StormListAdapter;
import com.cube.storm.ui.lib.helper.RecycledViewPoolHelper;
import com.cube.storm.ui.quiz.model.page.QuizPage;

import lombok.Getter;

/**
 * // TODO: Add class description
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class StormQuizFragment extends Fragment
{
	@Getter private RecyclerView listView;
	@Getter private StormListAdapter adapter;
	@Getter private QuizPage page;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(com.cube.storm.ui.R.layout.list_page_fragment_view, container, false);

		listView = (RecyclerView)v.findViewById(com.cube.storm.ui.R.id.recyclerview);
		listView.setRecycledViewPool(RecycledViewPoolHelper.getInstance().getRecycledViewPool());
		listView.setLayoutManager(new LinearLayoutManager(getActivity()));
		listView.setItemAnimator(new DefaultItemAnimator());

		return v;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		adapter = new StormListAdapter(getActivity());

		if (getArguments().containsKey(StormActivity.EXTRA_PAGE))
		{
			page = (QuizPage)getArguments().get(StormActivity.EXTRA_PAGE);
		}
		else if (getArguments().containsKey(StormActivity.EXTRA_URI))
		{
			String pageUri = getArguments().getString(StormActivity.EXTRA_URI);
			page = (QuizPage)UiSettings.getInstance().getViewBuilder().buildPage(Uri.parse(pageUri));
		}
		else
		{
			Toast.makeText(getActivity(), "Failed to load page", Toast.LENGTH_SHORT).show();
			getActivity().finish();
			return;
		}

		if (page != null)
		{
			adapter.setItems(page.getChildren());
		}

		listView.setAdapter(adapter);
	}
}
