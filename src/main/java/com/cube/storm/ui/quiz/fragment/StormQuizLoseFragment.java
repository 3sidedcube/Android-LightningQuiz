package com.cube.storm.ui.quiz.fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.activity.StormActivity;
import com.cube.storm.ui.model.page.Page;
import com.cube.storm.ui.quiz.R;

import lombok.Getter;

public class StormQuizLoseFragment extends Fragment
{
	@Getter private Page page;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.quiz_lose_view, container, false);
		return v;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (getArguments().containsKey(StormActivity.EXTRA_PAGE))
		{
			page = (Page)getArguments().get(StormActivity.EXTRA_PAGE);
		}
		else if (getArguments().containsKey(StormActivity.EXTRA_URI))
		{
			String pageUri = getArguments().getString(StormActivity.EXTRA_URI);
			page = UiSettings.getInstance().getViewBuilder().buildPage(Uri.parse(pageUri));
		}
		else
		{
			Toast.makeText(getActivity(), "Failed to load page", Toast.LENGTH_SHORT).show();
			getActivity().finish();

			return;
		}
	}
}
