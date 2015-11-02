package com.cube.storm.ui.quiz.lib.manager;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.quiz.model.property.BadgeProperty;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;

/**
 * // TODO: Add class description
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class BadgeManager
{
	@Getter private HashMap<String, BadgeProperty> badges;

	private static BadgeManager instance;

	public static BadgeManager getInstance()
	{
		if (instance == null)
		{
			synchronized (BadgeManager.class)
			{
				if (instance == null)
				{
					instance = new BadgeManager();
				}
			}
		}

		return instance;
	}

	/**
	 * Loads badges from a path into a map of {@link com.cube.storm.ui.quiz.model.property.BadgeProperty}
	 *
	 * @param path The uri to load the badges from
	 */
	public void loadBadges(Uri path)
	{
		ArrayList<BadgeProperty> badgeList = loadBadgesFromPath(path);

		if (badgeList != null)
		{
			badges = new HashMap<String, BadgeProperty>(badgeList.size());

			for (BadgeProperty badge : badgeList)
			{
				badges.put(badge.getId(), badge);
			}
		}
	}

	/**
	 * Loads a list of badges into an array from a Uri source
	 *
	 * @param path The path of the file to load
	 *
	 * @return Loads the list of badges from the bundle if it exists, or null
	 */
	@Nullable
	public ArrayList<BadgeProperty> loadBadgesFromPath(Uri path)
	{
		try
		{
			InputStream stream = UiSettings.getInstance().getFileFactory().loadFromUri(path);

			if (stream != null)
			{
				return UiSettings.getInstance().getViewBuilder().build(stream, new TypeToken<ArrayList<BadgeProperty>>(){}.getType());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Finds a badge in the map based on the id and returns it.
	 *
	 * @param id The id of the badge to look up
	 *
	 * @return The found badge, or null
	 */
	@Nullable
	public BadgeProperty getBadgeById(String id)
	{
		return badges == null ? null : badges.get(id);
	}
}
