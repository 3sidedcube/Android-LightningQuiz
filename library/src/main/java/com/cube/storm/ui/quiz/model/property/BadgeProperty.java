package com.cube.storm.ui.quiz.model.property;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import com.cube.storm.ui.QuizSettings;
import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.model.property.Property;
import com.cube.storm.ui.model.property.TextProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;

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
	private static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
	private static final int UNSET_TIME = -1;
	public static final String PREFERENCE_BADGE_COMPLETION_DATE = "badge_completion_date_";

	protected TextProperty title;
	protected TextProperty completion;
	protected TextProperty how;
	protected TextProperty shareMessage;
	protected ArrayList<ImageProperty> icon;
	protected long validFor; // days for badge to expire once completed

	/**
	 * Returns if the badge has been achieved or not
	 *
	 * @param context The context where the preferences are stored for the achieved badges
	 *
	 * @return True if achieved, false if not
	 */
	public boolean hasAchieved(Context context)
	{
		boolean isMarkedAsAchieved = context.getSharedPreferences("badges", Context.MODE_PRIVATE).getBoolean(getId(), false);
		return isMarkedAsAchieved && !hasExpired(context);
	}

	@Override public int describeContents()
	{
		return 0;
	}

	public long getCompletionTime(Context context)
	{
		SharedPreferences badgePreferences = context.getSharedPreferences("badges", Context.MODE_PRIVATE);
		String badgePreferenceKey = PREFERENCE_BADGE_COMPLETION_DATE + getId();
		return badgePreferences.getLong(badgePreferenceKey, UNSET_TIME);
	}

	public long getExpiryTime(Context context)
	{
		return getCompletionTime(context) + (getValidFor() * ONE_DAY_IN_MILLIS);
	}

	/**
	 * Days until the validity period for this badge ends, rounded up to the nearest integer.
	 *
	 * Returns zero if the badge is not achieved
	 */
	public long getTimeRemaining(Context context)
	{
		if (!hasAchieved(context))
		{
			return 0L;
		}

		long now = System.currentTimeMillis();
		long expiryTime = getExpiryTime(context);
		return (expiryTime - now) / ONE_DAY_IN_MILLIS + 1L;
	}

	public boolean hasCompletionTime(Context context)
	{
		return getCompletionTime(context) != UNSET_TIME;
	}

	public boolean hasExpired(Context context)
	{
		return hasExpiryTime(context) && System.currentTimeMillis() >= getExpiryTime(context);
	}

	public boolean hasExpiryTime(Context context)
	{
		return QuizSettings.getInstance().getBadgeExpiry() && hasValidityPeriod() && hasCompletionTime(context);
	}

	/**
	 * Flag indicating whether or not this badge will expire once achieved.
	 */
	public boolean hasValidityPeriod()
	{
		return getValidFor() > 0;
	}

	public void setAchieved(Context context, boolean isAchieved)
	{
		SharedPreferences badgePreferences = context.getSharedPreferences("badges", Context.MODE_PRIVATE);
		if (isAchieved)
		{
			String badgePreferenceKey = PREFERENCE_BADGE_COMPLETION_DATE + getId();
			badgePreferences.edit().putLong(badgePreferenceKey, System.currentTimeMillis()).apply();
		}
		// This preference is still used in badge expiry via the hasAchieved method.
		badgePreferences.edit().putBoolean(getId(), isAchieved).apply();
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
