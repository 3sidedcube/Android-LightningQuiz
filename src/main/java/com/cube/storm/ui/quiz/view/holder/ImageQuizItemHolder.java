package com.cube.storm.ui.quiz.view.holder;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.model.property.TextProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.model.quiz.ImageQuizItem;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderController;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * View holder for {@link com.cube.storm.ui.quiz.model.quiz.ImageQuizItem} in the adapter
 *
 * @author Luke Reed
 * @project Storm
 */
public class ImageQuizItemHolder extends ViewHolderController
{
	@Override public ViewHolder createViewHolder(ViewGroup parent)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_quiz_item_view, parent, false);
		mViewHolder = new ImageQuizItemViewHolder(view);

		return mViewHolder;
	}

	private class ImageQuizItemViewHolder extends ViewHolder<ImageQuizItem>
	{
		protected TextView title;
		protected TextView hint;
		protected ViewGroup options;

		public ImageQuizItemViewHolder(View view)
		{
			super(view);

			title = (TextView)view.findViewById(R.id.title);
			hint = (TextView)view.findViewById(R.id.hint);
			options = (ViewGroup)view.findViewById(R.id.options_container);
		}

		@Override public void populateView(ImageQuizItem model)
		{
			title.setVisibility(View.GONE);
			hint.setVisibility(View.GONE);

			if (model.getTitle() != null)
			{
				String content = UiSettings.getInstance().getTextProcessor().process(model.getTitle().getContent());

				if (!TextUtils.isEmpty(content))
				{
					title.setText(content);
					title.setVisibility(View.VISIBLE);
				}
				else
				{
					title.setVisibility(View.GONE);
				}
			}

			if (model.getHint() != null)
			{
				String content = UiSettings.getInstance().getTextProcessor().process(model.getHint().getContent());

				if (!TextUtils.isEmpty(content))
				{
					hint.setText(content);
					hint.setVisibility(View.VISIBLE);
				}
				else
				{
					hint.setVisibility(View.GONE);
				}
			}

			options.removeAllViewsInLayout();

			ArrayList<TextProperty> textOptions = model.getOptions();
			ArrayList<ImageProperty> images = model.getImages();
			int optionLength = textOptions.size();
			View currentRow = null;
			View currentCell = null;

			for(int index = 0;index < optionLength; index++)
			{
				if(index % 2 == 0)
				{
					currentRow = LayoutInflater.from(options.getContext()).inflate(R.layout.image_quiz_item_row, options, false);
					options.addView(currentRow);
					currentCell = currentRow.findViewById(R.id.layout1);
				}
				else
				{
					currentCell = currentRow.findViewById(R.id.layout2);
				}

				if (textOptions.get(index).getContent() != null)
				{
					String title = UiSettings.getInstance().getTextProcessor().process(textOptions.get(index).getContent());

					if (!TextUtils.isEmpty(title))
					{
						((TextView)currentCell.findViewById(R.id.label)).setText(title);
					}
				}

				if(index < images.size())
				{
					final ImageProperty imageProperty = model.getImages().get(index);
					if (imageProperty != null)
					{
						final ImageView image = (ImageView)currentCell.findViewById(R.id.image);

						UiSettings.getInstance().getImageLoader().displayImage(imageProperty.getSrc(), image, new SimpleImageLoadingListener()
						{
							@Override public void onLoadingStarted(String imageUri, View view)
							{
								image.setVisibility(View.INVISIBLE);
							}

							@Override public void onLoadingFailed(String imageUri, View view, FailReason failReason)
							{
								if (!imageUri.equalsIgnoreCase(imageProperty.getFallbackSrc()))
								{
									UiSettings.getInstance().getImageLoader().displayImage(imageProperty.getFallbackSrc(), image, this);
								}

								image.setVisibility(View.VISIBLE);
							}

							@Override public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
							{
								image.setVisibility(View.VISIBLE);
							}
						});
					}
				}

				currentCell.setTag(R.id.checkbox, index);
				currentCell.setOnClickListener(new ModelClickListener(model));
			}
		}

		private class ModelClickListener implements OnClickListener
		{
			private ImageQuizItem model;

			private ModelClickListener(ImageQuizItem model)
			{
				this.model = model;
			}

			@Override public void onClick(View v)
			{
				int index = (Integer)v.getTag(R.id.checkbox);
				CheckBox checker = ((CheckBox)v.findViewById(R.id.checkbox));

				if (!checker.isChecked())
				{
					checker.setChecked(true);
					model.getSelectHistory().add(index);
				}
				else
				{
					checker.setChecked(false);
					model.getSelectHistory().remove((Integer)index);
				}

				// disable select if checked > limit
				if (model.getSelectHistory().size() > model.getLimit())
				{
					int remIndex = model.getSelectHistory().get(0);
					model.getSelectHistory().remove(0);

					if(remIndex % 2 == 0)
					{
						((CheckBox)options.getChildAt((int)Math.floor(remIndex / 2)).findViewById(R.id.layout1).findViewById(R.id.checkbox)).setChecked(false);
					}
					else
					{
						((CheckBox)options.getChildAt((int)Math.floor(remIndex / 2)).findViewById(R.id.layout2).findViewById(R.id.checkbox)).setChecked(false);
					}
				}

				// check the answers in the history
				if (model.getAnswer() != null && model.getAnswer().size() == model.getSelectHistory().size())
				{
					for (int answer : model.getAnswer())
					{
						if (model.getSelectHistory().contains(answer))
						{
							model.setCorrect(true);
						}
						else
						{
							model.setCorrect(false);
							break;
						}
					}
				}
			}
		}
	}
}
