package com.cube.storm.ui.quiz.view.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;

import com.cube.storm.ui.QuizSettings;
import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.model.property.TextProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.lib.QuizEventHook;
import com.cube.storm.ui.quiz.model.quiz.ImageQuizItem;
import com.cube.storm.ui.view.ImageView;
import com.cube.storm.ui.view.TextView;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderFactory;

import java.util.ArrayList;

/**
 * View holder for {@link com.cube.storm.ui.quiz.model.quiz.ImageQuizItem} in the adapter
 *
 * @author Luke Reed
 * @project Storm
 */
public class ImageQuizItemViewHolder extends ViewHolder<ImageQuizItem>
{
	public static class Factory extends ViewHolderFactory
	{
		@Override public ImageQuizItemViewHolder createViewHolder(ViewGroup parent)
		{
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_quiz_item_view, parent, false);
			return new ImageQuizItemViewHolder(view);
		}
	}

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
		title.populate(model.getTitle());
		hint.populate(model.getHint());

		options.removeAllViewsInLayout();

		ArrayList<TextProperty> textOptions = model.getOptions();
		ArrayList<ArrayList<ImageProperty>> images = model.getImages();
		int optionLength = textOptions.size();
		View currentRow = null;
		View currentCell = null;

		for (int index = 0; index < optionLength; index++)
		{
			if (index % 2 == 0)
			{
				currentRow = LayoutInflater.from(options.getContext()).inflate(R.layout.image_quiz_item_row, options, false);
				options.addView(currentRow);
				currentCell = currentRow.findViewById(R.id.layout1);
			}
			else
			{
				currentCell = currentRow.findViewById(R.id.layout2);
			}

			((TextView)currentCell.findViewById(R.id.label)).populate(textOptions.get(index));

			if (index < images.size())
			{
				((ImageView)currentCell.findViewById(R.id.image)).populate(model.getImages().get(index));
				((Checkable)currentCell.findViewById(R.id.checkbox)).setChecked(model.getSelectHistory().contains(index));
				currentCell.setTag(R.id.checkbox, index);
				currentCell.setOnClickListener(new ModelClickListener(model));
				currentCell.setVisibility(View.VISIBLE);
			}
			else if (index >= images.size())
			{
				currentCell.setVisibility(View.INVISIBLE);
			}
		}

		if (optionLength % 2 != 0)
		{
			currentRow.findViewById(R.id.layout2).setVisibility(View.INVISIBLE);
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

				for (QuizEventHook quizEventHook : QuizSettings.getInstance().getEventHooks())
				{
					quizEventHook.onQuizOptionSelected(v.getContext(), itemView, model, index);
				}
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

				if (remIndex % 2 == 0)
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
