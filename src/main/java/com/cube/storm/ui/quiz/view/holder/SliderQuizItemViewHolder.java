package com.cube.storm.ui.quiz.view.holder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.quiz.R;
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

	protected int pos = Integer.MIN_VALUE;

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

		if (pos < 0)
		{
			pos = model.getInitialPosition() - model.getRange().getStart();
		}

		title.populate(model.getTitle());
		hint.populate(model.getHint());
		image.populate(model.getImage());

		if (model.getRange() != null)
		{
			slider.setMax(model.getRange().getLength() - 1);
			slider.setProgress(pos);

			if (model.getTitle() != null)
			{
				String content = UiSettings.getInstance().getTextProcessor().process(model.getUnit().getContent());

				if (!TextUtils.isEmpty(content))
				{
					sliderText.setText("" + (pos + model.getRange().getStart()) + " " + content);
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
				pos = progress;
				model.setCorrect((progress + model.getRange().getStart()) == model.getAnswer());

				TextView sliderText = (TextView)((View)seekBar.getParent()).findViewById(R.id.slider_text);

				if (model.getTitle() != null)
				{
					String content = UiSettings.getInstance().getTextProcessor().process(model.getUnit().getContent());

					if (!TextUtils.isEmpty(content))
					{
						sliderText.setText("" + (pos + model.getRange().getStart()) + " " + content + " : " + model.isCorrect());
					}
				}
			}
		}

		@Override public void onStartTrackingTouch(SeekBar seekBar){}
		@Override public void onStopTrackingTouch(SeekBar seekBar){}
	}
}
