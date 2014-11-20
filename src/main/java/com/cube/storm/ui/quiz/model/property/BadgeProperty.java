package com.cube.storm.ui.quiz.model.property;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;

import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.model.property.Property;
import com.cube.storm.ui.model.property.TextProperty;

import lombok.Getter;

/**
 * // TODO: Add class description
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class BadgeProperty extends Property
{
	@Getter private TextProperty title;
	@Getter private TextProperty completion;
	@Getter private TextProperty how;
	@Getter private TextProperty shareMessage;
	@Getter private ImageProperty icon;

	/**
	 * Returns if the badge has been achieved or not
	 *
	 * @param context The context where the preferences are stored for the achieved badges
	 *
	 * @return True if achieved, false if not
	 */
	public boolean hasAchieved(Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences("badges", Context.MODE_PRIVATE);
		String[] completedBadges = prefs.getAll().keySet().toArray(new String[prefs.getAll().keySet().size()]);

		for (String completedBadge : completedBadges)
		{
			if (getId().equals(completedBadge))
			{
				return true;
			}
		}

		return false;
	}

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
