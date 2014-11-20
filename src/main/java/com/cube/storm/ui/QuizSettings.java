package com.cube.storm.ui;

import android.support.annotation.NonNull;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.lib.factory.IntentFactory;
import com.cube.storm.ui.lib.factory.ViewFactory;
import com.cube.storm.ui.lib.parser.ViewProcessor;
import com.cube.storm.ui.model.Model;
import com.cube.storm.ui.quiz.lib.factory.QuizIntentFactory;
import com.cube.storm.ui.quiz.lib.factory.QuizViewFactory;
import com.cube.storm.ui.quiz.model.quiz.QuizItem;

/**
 * Settings class for the quiz module.
 * <p/>
 * This class is used for setting up the module to enable the use of Quizes and Quiz objects in Storm. This class must be
 * instantiated <b>after</b> the {@link com.cube.storm.UiSettings} has been created as this class requires a reference
 * of the settings class to be passed to it to ensure that it can properly parse the right objects/models into the right
 * classes.
 * <p/>
 * To enable the use of the library, you must instantiate
 * a new {@link QuizSettings.Builder} object in your {@link android.app.Application} singleton class.
 * <p/>
 * This class should not be directly instantiated.
 *
 * @author Callum Taylor
 * @project LightningQuiz
 */
public class QuizSettings
{
	/**
	 * The singleton instance of the settings
	 */
	private static QuizSettings instance;

	/**
	 * Gets the instance of the {@link QuizSettings} class
	 * Throws a {@link java.lang.IllegalAccessError} if the singleton has not been instantiated properly
	 *
	 * @return The instance
	 */
	public static QuizSettings getInstance()
	{
		if (instance == null)
		{
			throw new IllegalAccessError("You must build the QuizSettings settings object first using QuizSettings$Builder");
		}

		return instance;
	}

	/**
	 * Default private constructor
	 */
	private QuizSettings(){}

	/**
	 * The builder class for {@link com.cube.storm.UiSettings}. Use this to create a new {@link com.cube.storm.UiSettings} instance
	 * with the customised properties specific for your project.
	 *
	 * Call {@link #build()} to build the settings object.
	 */
	public static class Builder
	{
		/**
		 * The temporary instance of the {@link com.cube.storm.ui.QuizSettings} object.
		 */
		private QuizSettings construct;

		/**
		 * The temporary instance of the {@link com.cube.storm.UiSettings} object to inject into.
		 */
		private UiSettings uiSettings;

		/**
		 * Default constructor
		 *
		 * @param uiSettings The settings object to use to enable quiz module
		 */
		public Builder(@NonNull UiSettings uiSettings)
		{
			this.uiSettings = uiSettings;

			ViewProcessor<? extends Model> baseProcessor = new ViewProcessor<Model>()
			{
				@Override public Class<? extends Model> getClassFromName(String name)
				{
					return UiSettings.getInstance().getViewFactory().getModelForView(name);
				}
			};

			this.uiSettings.getViewProcessors().put(QuizItem.class, baseProcessor);

			viewFactory(new QuizViewFactory(uiSettings.getViewFactory()));
			intentFactory(new QuizIntentFactory(uiSettings.getIntentFactory()));
		}

		/**
		 * Replaces the intent factory set in {@link com.cube.storm.UiSettings}. You should not use this
		 * method, but instead set your custom intent factory in {@link com.cube.storm.UiSettings.Builder#intentFactory}
		 *
		 * @param intentFactory The intent factory used to catch Quiz models
		 *
		 * @return The {@link com.cube.storm.ui.QuizSettings.Builder} instance for chaining
		 */
		public Builder intentFactory(@NonNull IntentFactory intentFactory)
		{
			uiSettings.setIntentFactory(intentFactory);

			return this;
		}

		/**
		 * Replaces the view factory set in {@link com.cube.storm.UiSettings}. You should not use this
		 * method, but instead set your custom view factory in {@link com.cube.storm.UiSettings.Builder#viewFactory}
		 *
		 * @param viewFactory The view factory used to catch Quiz models
		 *
		 * @return The {@link com.cube.storm.ui.QuizSettings.Builder} instance for chaining
		 */
		public Builder viewFactory(@NonNull ViewFactory viewFactory)
		{
			uiSettings.setViewFactory(viewFactory);

			return this;
		}

		/**
		 * Builds the final settings object and sets its instance. Use {@link #getInstance()} to retrieve the settings
		 * instance.
		 *
		 * @return The newly set {@link com.cube.storm.UiSettings} instance
		 */
		public QuizSettings build()
		{
			return (QuizSettings.instance = construct);
		}
	}
}
