package com.cube.storm.ui.quiz.view.holder.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.lib.manager.BadgeManager;
import com.cube.storm.ui.quiz.model.list.collection.BadgeCollectionItem;
import com.cube.storm.ui.quiz.model.property.BadgeProperty;
import com.cube.storm.ui.view.ImageView;
import com.cube.storm.ui.view.TextView;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderFactory;

public class BadgeCollectionItemViewHolder extends ViewHolder<BadgeCollectionItem>
{
	public static class Factory extends ViewHolderFactory
	{
		@Override public BadgeCollectionItemViewHolder createViewHolder(ViewGroup parent)
		{
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.badge_collection_item, parent, false);
			return new BadgeCollectionItemViewHolder(view);
		}
	}

	protected ImageView image;
	protected TextView title;

	public BadgeCollectionItemViewHolder(View view)
	{
		super(view);

		image = (ImageView)view.findViewById(R.id.badge_icon);
		title = (TextView)view.findViewById(R.id.title);
	}

	@Override public void populateView(final BadgeCollectionItem model)
	{
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
}
