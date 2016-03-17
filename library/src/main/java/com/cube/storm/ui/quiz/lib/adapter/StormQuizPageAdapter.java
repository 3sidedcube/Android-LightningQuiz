package com.cube.storm.ui.quiz.lib.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.cube.storm.ui.lib.adapter.StormPageAdapter;

/**
 * // TODO: Add class description
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class StormQuizPageAdapter extends StormPageAdapter
{
	public StormQuizPageAdapter(Context context, FragmentManager manager)
	{
		super(context, manager);
	}

	@Override public void destroyItem(ViewGroup container, int position, Object object)
	{
		// disabled to preserve selections in quiz quesions
	}
}
