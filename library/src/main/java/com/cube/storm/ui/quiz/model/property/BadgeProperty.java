package com.cube.storm.ui.quiz.model.property;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.text.format.DateUtils;

import com.cube.storm.ui.QuizSettings;
import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.model.property.Property;
import com.cube.storm.ui.model.property.TextProperty;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * // TODO: Add class description
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
@NoArgsConstructor @AllArgsConstructor
@Accessors(chain = true) @Data
public class BadgeProperty extends Property
{
	protected TextProperty title;
	protected TextProperty completion;
	protected TextProperty how;
	protected TextProperty shareMessage;
	protected ArrayList<ImageProperty> icon;
	protected long validFor; // days for badge to expire once completed

	public static final String PREFERENCE_BADGE_COMPLETION_DATE = "badge_completion_date_";

	/**
	 * Returns if the badge has been achieved or not
	 *
	 * @param context The context where the preferences are stored for the achieved badges
	 *
	 * @return True if achieved, false if not
	 */
	public boolean hasAchieved(Context context)
	{
		SharedPreferences badgePreferences = context.getSharedPreferences("badges", Context.MODE_PRIVATE);
		String badgePreferenceKey = PREFERENCE_BADGE_COMPLETION_DATE + getId();
		long badgeCompleteTime = badgePreferences.getLong(badgePreferenceKey, -1);
		if (badgeCompleteTime != -1)
		{
			long now = new Date().getTime();
			long oneDay = 24 * 60 * 60 * 1000;
			long expiresTime = badgeCompleteTime + (getValidFor() * oneDay);
			return now < expiresTime; // Return true only if the badge has not expired
		}

		return context.getSharedPreferences("badges", Context.MODE_PRIVATE).getBoolean(getId(), false);
	}

	@Override public int describeContents()
	{
		return 0;
	}

	public void setAchieved(Context context, boolean isAchieved)
	{
		SharedPreferences badgePreferences = context.getSharedPreferences("badges", Context.MODE_PRIVATE);
		if (isAchieved && QuizSettings.getInstance().getBadgeExpiry())
		{
			String badgePreferenceKey = PREFERENCE_BADGE_COMPLETION_DATE + getId();
			badgePreferences.edit().putLong(badgePreferenceKey, new Date().getTime()).apply();
		}
		// This preference is still used in badge expiry via the hasAchieved method.
		badgePreferences.edit().putBoolean(getId(), isAchieved).apply();
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
