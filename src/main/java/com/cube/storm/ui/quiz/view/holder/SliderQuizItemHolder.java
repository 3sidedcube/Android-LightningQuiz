package com.cube.storm.ui.quiz.view.holder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.model.quiz.SliderQuizItem;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderController;

/**
 * View holder for {@link com.cube.storm.ui.quiz.model.quiz.SliderQuizItem} in the adapter
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class SliderQuizItemHolder extends ViewHolderController
{
	@Override public ViewHolder createViewHolder(ViewGroup parent)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_quiz_item_view, parent, false);
		mViewHolder = new SliderQuizItemViewHolder(view);

		return mViewHolder;
	}

	private class SliderQuizItemViewHolder extends ViewHolder<SliderQuizItem>
	{
		protected TextView title;
		protected TextView hint;

		public SliderQuizItemViewHolder(View view)
		{
			super(view);

			title = (TextView)view.findViewById(R.id.title);
			hint = (TextView)view.findViewById(R.id.hint);
		}

		@Override public void populateView(SliderQuizItem model)
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
		}
	}
}
