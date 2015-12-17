package com.cube.storm.quiz.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.cube.storm.UiSettings;

/**
 * @author Callum Taylor
 * @project LightningUtil
 */
public class ExampleActivity extends Activity
{
	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Load the initial page
		Intent start = UiSettings.getInstance().getIntentFactory().getIntentForPageUri(this, Uri.parse(UiSettings.getInstance().getApp().getVector()));

		if (start != null)
		{
			startActivity(start);
		}
	}
}
