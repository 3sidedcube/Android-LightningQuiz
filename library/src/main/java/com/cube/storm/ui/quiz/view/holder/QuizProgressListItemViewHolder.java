package com.cube.storm.ui.quiz.view.holder;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.model.property.InternalLinkProperty;
import com.cube.storm.ui.model.property.TextProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.lib.manager.BadgeManager;
import com.cube.storm.ui.quiz.model.list.QuizProgressListItem;
import com.cube.storm.ui.quiz.model.property.BadgeProperty;
import com.cube.storm.ui.view.TextView;
import com.cube.storm.ui.view.holder.ViewHolderFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class QuizProgressListItemViewHolder extends com.cube.storm.ui.view.holder.ViewHolder<QuizProgressListItem>
{
	public static class Factory extends ViewHolderFactory
	{
		public QuizProgressListItemViewHolder createViewHolder(ViewGroup parent)
		{
			return new QuizProgressListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_progress_list_item, parent, false));
		}
	}

	private TextView label;
	private TextView quizName;
	private android.widget.TextView counter;

	public QuizProgressListItemViewHolder(View itemView)
	{
		super(itemView);

		label = (TextView)itemView.findViewById(R.id.label);
		quizName = (TextView)itemView.findViewById(R.id.quiz_name);
		counter = (android.widget.TextView)itemView.findViewById(R.id.counter);
	}

	@Override public void populateView(QuizProgressListItem item)
	{
		int completedCounter = 0;
		if (item.getQuizzes() != null)
		{
			boolean populatedNext = false;
			for (String quiz : item.getQuizzes())
			{
				try
				{
					InputStream pageStream = UiSettings.getInstance().getFileFactory().loadFromUri(Uri.parse(quiz));

					if (pageStream != null)
					{
						JsonElement pageJson = new JsonParser().parse(new JsonReader(new InputStreamReader(pageStream, "UTF-8")));

						String badgeId = pageJson.getAsJsonObject().get("badgeId").getAsString();
						JsonObject title = pageJson.getAsJsonObject().get("title").getAsJsonObject();

						final InternalLinkProperty linkProperty = new InternalLinkProperty();
						linkProperty.setDestination(quiz);

						TextProperty pageTitle = UiSettings.getInstance().getViewBuilder().build(title, TextProperty.class);
						BadgeProperty badgeById = BadgeManager.getInstance().getBadgeById(badgeId);

						if (badgeById != null)
						{
							completedCounter += badgeById.hasAchieved(itemView.getContext()) ? 1 : 0;

							if (!badgeById.hasAchieved(itemView.getContext()) && !populatedNext)
							{
								populatedNext = true;
								label.populate(item.getProgressMessage());
								label.setText(label.getText());
								quizName.populate(pageTitle);

								itemView.setOnClickListener(new View.OnClickListener()
								{
									@Override public void onClick(View v)
									{
										UiSettings.getInstance().getLinkHandler().handleLink(v.getContext(), linkProperty);
									}
								});
							}
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		counter.setText(completedCounter + " / " + item.getQuizzes().length);

		if (completedCounter >= item.getQuizzes().length)
		{
			label.populate(item.getFinishMessage());
		}
	}
}
