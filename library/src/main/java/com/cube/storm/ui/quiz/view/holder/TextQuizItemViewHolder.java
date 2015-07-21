package com.cube.storm.ui.quiz.view.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.cube.storm.ui.model.property.TextProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.model.quiz.TextQuizItem;
import com.cube.storm.ui.view.TextView;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderFactory;

/**
 * View holder for {@link com.cube.storm.ui.quiz.model.quiz.TextQuizItem} in the adapter
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class TextQuizItemViewHolder extends ViewHolder<TextQuizItem>
{
	public static class Factory extends ViewHolderFactory
	{
		@Override public TextQuizItemViewHolder createViewHolder(ViewGroup parent)
		{
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_quiz_item_view, parent, false);
			return new TextQuizItemViewHolder(view);
		}
	}

	protected TextView title;
	protected TextView hint;
	protected ViewGroup options;

	public TextQuizItemViewHolder(View view)
	{
		super(view);

		title = (TextView)view.findViewById(R.id.title);
		hint = (TextView)view.findViewById(R.id.hint);
		options = (ViewGroup)view.findViewById(R.id.options_container);
	}

	@Override public void populateView(TextQuizItem model)
	{
		title.populate(model.getTitle());
		hint.populate(model.getHint());

		options.removeAllViewsInLayout();

		for (TextProperty option : model.getOptions())
		{
			View row = LayoutInflater.from(options.getContext()).inflate(R.layout.text_quiz_item_item, options, false);
			((TextView)row.findViewById(R.id.title)).populate(option);
			row.setOnClickListener(new ModelClickListener(model));
			options.addView(row);
		}
	}

	private class ModelClickListener implements OnClickListener
	{
		private TextQuizItem model;

		private ModelClickListener(TextQuizItem model)
		{
			this.model = model;
		}

		@Override public void onClick(View v)
		{
			int index = options.indexOfChild(v);
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
				((CheckBox)options.getChildAt(remIndex).findViewById(R.id.checkbox)).setChecked(false);
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
