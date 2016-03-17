package com.cube.storm.ui.quiz.view.holder.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.lib.manager.BadgeManager;
import com.cube.storm.ui.quiz.model.list.collection.QuizCollectionItem;
import com.cube.storm.ui.quiz.model.property.BadgeProperty;
import com.cube.storm.ui.view.ImageView;
import com.cube.storm.ui.view.TextView;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderFactory;

/**
 * // TODO: Add class description
 *
 * @author Alan Le Fournis
 * @project Storm
 */
public class QuizCollectionItemViewHolder extends ViewHolder<QuizCollectionItem> implements OnClickListener
{
	public static class Factory extends ViewHolderFactory
	{
		@Override public QuizCollectionItemViewHolder createViewHolder(ViewGroup parent)
		{
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_collection_item, parent, false);
			return new QuizCollectionItemViewHolder(view);
		}
	}

	protected ImageView image;
	protected TextView title;
	protected LinkProperty link;

	public QuizCollectionItemViewHolder(View view)
	{
		super(view);

		view.setOnClickListener(this);

		image = (ImageView)view.findViewById(R.id.badge_icon);
		title = (TextView)view.findViewById(R.id.title);
	}

	@Override public void populateView(final QuizCollectionItem model)
	{
		link = model.getQuiz();
		final BadgeProperty badge = BadgeManager.getInstance().getBadgeById(model.getBadgeId());

		if (badge != null)
		{
			image.populate(badge.getIcon());
			title.populate(badge.getTitle());

			if (badge.hasAchieved(image.getContext()))
			{
				image.setAlpha(255);
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
