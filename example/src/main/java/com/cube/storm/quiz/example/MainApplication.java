package com.cube.storm.quiz.example;

import android.net.Uri;
import com.cube.storm.UiSettings;
import com.cube.storm.ui.QuizSettings;
import com.cube.storm.ui.lib.migration.LegacyImageViewProcessor;
import com.cube.storm.ui.model.App;
import com.cube.storm.ui.model.property.ImageProperty;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import androidx.multidex.MultiDexApplication;

public class MainApplication extends MultiDexApplication
{
	@Override public void onCreate()
	{
		super.onCreate();

		// Initiate settings
		UiSettings uiSettings = new UiSettings.Builder(this)
			.registerType(new TypeToken<ArrayList<ImageProperty>>(){}.getType(), new LegacyImageViewProcessor())
			.youtubeApiKey(null)
			.build();

		QuizSettings quizSettings = new QuizSettings.Builder(uiSettings)
			.randomiseQuestionOrder(true) // used for gdpc blended learning (badge expiry)
			.useBadgeExpiryFeature(true) // used for gdpc blended learning (badge expiry)
			.build();

		// Loading app json
		String appUri = "assets://app.json";
		App app = UiSettings.getInstance().getViewBuilder().buildApp(Uri.parse(appUri));

		if (app != null)
		{
			UiSettings.getInstance().setApp(app);
		}
	}
}
