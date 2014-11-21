package com.cube.storm.ui.quiz.view.holder.grid;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.lib.manager.BadgeManager;
import com.cube.storm.ui.quiz.model.grid.QuizGridItem;
import com.cube.storm.ui.quiz.model.property.BadgeProperty;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderController;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * // TODO: Add class description
 *
 * @author Luke Reed
 * @project Storm
 */
public class QuizGridItemHolder extends ViewHolderController
{
	@Override public ViewHolder createViewHolder(ViewGroup parent)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.standard_grid_cell_view, parent, false);
		mViewHolder = new QuizGridItemViewHolder(view);

		return mViewHolder;
	}

	private class QuizGridItemViewHolder extends ViewHolder<QuizGridItem> implements OnClickListener
	{
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

			UiSettings.getInstance().getImageLoader().displayImage(badgeProperty.getIcon().getSrc(), image, new SimpleImageLoadingListener()
			{
				@Override public void onLoadingStarted(String imageUri, View view)
				{
					image.setVisibility(View.INVISIBLE);
					progress.setVisibility(View.VISIBLE);
				}

				@Override public void onLoadingFailed(String imageUri, View view, FailReason failReason)
				{
					if (!imageUri.equalsIgnoreCase(badgeProperty.getIcon().getFallbackSrc()))
					{
						UiSettings.getInstance().getImageLoader().displayImage(badgeProperty.getIcon().getFallbackSrc(), image, this);
					}

					image.setVisibility(View.VISIBLE);
					progress.setVisibility(View.GONE);
				}

				@Override public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
				{
					image.setVisibility(View.VISIBLE);
					progress.setVisibility(View.GONE);
				}
			});

			if (badgeProperty.hasAchieved(image.getContext()))
			{
				image.setAlpha(255);
				image.setImageBitmap(null);
			}
			else
			{
				image.setAlpha(100);
			}

			if (model.getTitle() != null && !TextUtils.isEmpty(model.getTitle().getContent()))
			{
				title.setText(model.getTitle().getContent());
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
}
