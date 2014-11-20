package com.cube.storm.ui.quiz.view.holder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.model.property.TextProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.model.quiz.TextQuizItem;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderController;

/**
 * View holder for {@link com.cube.storm.ui.quiz.model.quiz.TextQuizItem} in the adapter
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class TextQuizItemHolder extends ViewHolderController
{
	@Override public ViewHolder createViewHolder(ViewGroup parent)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_quiz_item_view, parent, false);
		mViewHolder = new TextQuizItemViewHolder(view);

		return mViewHolder;
	}

	private class TextQuizItemViewHolder extends ViewHolder<TextQuizItem>
	{
		protected TextView title;
		protected ViewGroup options;

		public TextQuizItemViewHolder(View view)
		{
			super(view);

			title = (TextView)view.findViewById(R.id.title);
			options = (ViewGroup)view.findViewById(R.id.options_container);
		}

		@Override public void populateView(TextQuizItem model)
		{
			options.removeAllViewsInLayout();

			while (model.getOptions().iterator().hasNext())
			{
				TextProperty option = model.getOptions().iterator().next();
				View row = LayoutInflater.from(options.getContext()).inflate(R.layout.text_quiz_item_item, options, false);

				if (option.getContent() != null)
				{
					String title = UiSettings.getInstance().getTextProcessor().process(option.getContent());

					if (!TextUtils.isEmpty(title))
					{
						((TextView)row.findViewById(R.id.title)).setText(title);
					}
				}

				options.addView(row);
			}
		}
	}
}
