package com.cube.storm.ui.quiz.model.property;

import android.content.Context;
import android.os.Parcel;

import com.cube.storm.ui.model.property.ImageProperty;
import com.cube.storm.ui.model.property.Property;
import com.cube.storm.ui.model.property.TextProperty;

import java.util.ArrayList;

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
@NoArgsConstructor @AllArgsConstructor(suppressConstructorProperties = true)
@Accessors(chain = true) @Data
public class BadgeProperty extends Property
{
	protected TextProperty title;
	protected TextProperty completion;
	protected TextProperty how;
	protected TextProperty shareMessage;
	protected ArrayList<ImageProperty> icon;

	/**
	 * Returns if the badge has been achieved or not
	 *
	 * @param context The context where the preferences are stored for the achieved badges
	 *
	 * @return True if achieved, false if not
	 */
	public boolean hasAchieved(Context context)
	{
		return context.getSharedPreferences("badges", Context.MODE_PRIVATE).getBoolean(getId(), false);
	}

	@Override public int describeContents()
	{
		return 0;
	}

	public void setAchieved(Context context, boolean isAchieved)
	{
		context.getSharedPreferences("badges", Context.MODE_PRIVATE).edit().putBoolean(getId(), isAchieved).apply();
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
