package com.cube.storm.ui.quiz.view.holder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.QuizSettings;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.lib.QuizEventHook;
import com.cube.storm.ui.quiz.model.quiz.SliderQuizItem;
import com.cube.storm.ui.view.ImageView;
import com.cube.storm.ui.view.TextView;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderFactory;

/**
 * View holder for {@link com.cube.storm.ui.quiz.model.quiz.SliderQuizItem} in the adapter
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class SliderQuizItemViewHolder extends ViewHolder<SliderQuizItem>
{
	public static class Factory extends ViewHolderFactory
	{
		@Override public SliderQuizItemViewHolder createViewHolder(ViewGroup parent)
		{
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_quiz_item_view, parent, false);
			return new SliderQuizItemViewHolder(view);
		}
	}

	protected TextView title;
	protected TextView hint;
	protected ImageView image;
	protected SeekBar slider;
	protected TextView sliderText;

	public SliderQuizItemViewHolder(View view)
	{
		super(view);

		title = (TextView)view.findViewById(R.id.title);
		hint = (TextView)view.findViewById(R.id.hint);
		image = (ImageView)view.findViewById(R.id.image);
		slider = (SeekBar)view.findViewById(R.id.slider);
		sliderText = (TextView)view.findViewById(R.id.slider_text);
	}

	@Override public void populateView(SliderQuizItem model)
	{
		slider.setOnSeekBarChangeListener(new ModelSeekBarListener(model));

		int progress = model.getInitialPosition() - model.getRange().getStart();
		title.populate(model.getTitle());
		hint.populate(model.getHint());
		image.populate(model.getImage());

		if (model.getRange() != null)
		{
			slider.setMax(model.getRange().getLength() - 1);
			slider.setProgress(progress);

			if (model.getTitle() != null)
			{
				String content = UiSettings.getInstance().getTextProcessor().process(model.getUnit());

				if (!TextUtils.isEmpty(content))
				{
					sliderText.setText(model.getInitialPosition() + " " + content);
				}
			}
		}
	}

	private class ModelSeekBarListener implements SeekBar.OnSeekBarChangeListener
	{
		private SliderQuizItem model;

		private ModelSeekBarListener(SliderQuizItem model)
		{
			this.model = model;
		}

		@Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
			if (fromUser)
			{
				int pos = progress + model.getRange().getStart();
				model.setInitialPosition(pos);
				model.setCorrect(pos == model.getAnswer());

				TextView sliderText = (TextView)((View)seekBar.getParent()).findViewById(R.id.slider_text);

				if (model.getTitle() != null)
				{
					String content = UiSettings.getInstance().getTextProcessor().process(model.getUnit());

					if (!TextUtils.isEmpty(content))
					{
						sliderText.setText(pos + " " + content);
					}
				}
			}
		}

		@Override public void onStartTrackingTouch(SeekBar seekBar){}
		@Override public void onStopTrackingTouch(SeekBar seekBar)
		{
			for (QuizEventHook quizEventHook : QuizSettings.getInstance().getEventHooks())
			{
				quizEventHook.onQuizOptionSelected(seekBar.getContext(), itemView, model, seekBar.getProgress());
			}
		}
	}
}
