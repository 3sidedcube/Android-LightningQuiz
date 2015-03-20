package com.cube.storm.ui.quiz.view.holder.grid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.lib.manager.BadgeManager;
import com.cube.storm.ui.quiz.model.grid.QuizGridItem;
import com.cube.storm.ui.quiz.model.property.BadgeProperty;
import com.cube.storm.ui.view.ImageView;
import com.cube.storm.ui.view.TextView;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderFactory;

/**
 * // TODO: Add class description
 *
 * @author Luke Reed
 * @project Storm
 */
public class QuizGridItemViewHolder extends ViewHolder<QuizGridItem> implements OnClickListener
{
	public static class Factory extends ViewHolderFactory
	{
		@Override public ViewHolder createViewHolder(ViewGroup parent)
		{
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.standard_grid_cell_view, parent, false);
			return new QuizGridItemViewHolder(view);
		}
	}

	protected ImageView image;
	protected TextView title;
	protected LinkProperty link;
	protected ProgressBar progress;

	public QuizGridItemViewHolder(View view)
	{
		super(view);

		image = (ImageView)view.findViewById(R.id.image);
		title = (TextView)view.findViewById(R.id.title);
		progress = (ProgressBar)view.findViewById(R.id.progress);
	}

	@Override public void populateView(QuizGridItem model)
	{
		link = model.getLink();
		final BadgeProperty badgeProperty = BadgeManager.getInstance().getBadgeById(model.getBadgeId());

		if (badgeProperty != null)
		{
			image.populate(badgeProperty.getIcon(), progress);
			title.populate(model.getTitle());

			if (badgeProperty.hasAchieved(image.getContext()))
			{
				image.setAlpha(255);
				image.setImageBitmap(null);
			}
			else
			{
				image.setAlpha(100);
			}
		}
	}

	@Override public void onClick(View v)
	{
		if (link != null)
		{
			UiSettings.getInstance().getLinkHandler().handleLink(image.getContext(), link);
		}
	}
}
