package com.cube.storm.ui;

import android.support.annotation.NonNull;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.lib.parser.ViewProcessor;
import com.cube.storm.ui.lib.resolver.DefaultViewResolver;
import com.cube.storm.ui.lib.resolver.ViewResolver;
import com.cube.storm.ui.model.Model;
import com.cube.storm.ui.quiz.lib.provider.QuizIntentProvider;
import com.cube.storm.ui.quiz.model.quiz.QuizItem;
import com.cube.storm.ui.quiz.view.holder.AreaQuizItemViewHolder;
import com.cube.storm.ui.quiz.view.holder.ImageQuizItemViewHolder;
import com.cube.storm.ui.quiz.view.holder.SliderQuizItemViewHolder;
import com.cube.storm.ui.quiz.view.holder.TextQuizItemViewHolder;
import com.cube.storm.ui.quiz.view.holder.grid.QuizGridItemViewHolder;
import com.cube.storm.ui.quiz.view.holder.list.BadgeCollectionItemViewHolder;
import com.cube.storm.ui.quiz.view.holder.list.QuizCollectionItemViewHolder;

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
		 * Temp store for skipping adding default quiz intent provider
		 */
		private boolean skipIntentProvider = false;

		/**
		 * Default constructor
		 *
		 * @param uiSettings The settings object to use to enable quiz module
		 */
		public Builder(@NonNull UiSettings uiSettings)
		{
			this.uiSettings = uiSettings;

			// Register quiz views into view resolver
			this.uiSettings.getViewResolvers().put("Badge", new DefaultViewResolver(com.cube.storm.ui.quiz.model.property.BadgeProperty.class, null));
			this.uiSettings.getViewResolvers().put("TextQuizItem", new DefaultViewResolver(com.cube.storm.ui.quiz.model.quiz.TextQuizItem.class, TextQuizItemViewHolder.Factory.class));
			this.uiSettings.getViewResolvers().put("QuizGridItem", new DefaultViewResolver(com.cube.storm.ui.quiz.model.grid.QuizGridItem.class, QuizGridItemViewHolder.Factory.class));
			this.uiSettings.getViewResolvers().put("QuizCollectionItem", new DefaultViewResolver(com.cube.storm.ui.quiz.model.list.collection.QuizCollectionItem.class, QuizCollectionItemViewHolder.Factory.class));
			this.uiSettings.getViewResolvers().put("BadgeCollectionItem", new DefaultViewResolver(com.cube.storm.ui.quiz.model.list.collection.BadgeCollectionItem.class, BadgeCollectionItemViewHolder.Factory.class));
			this.uiSettings.getViewResolvers().put("ImageQuizItem", new DefaultViewResolver(com.cube.storm.ui.quiz.model.quiz.ImageQuizItem.class, ImageQuizItemViewHolder.Factory.class));
			this.uiSettings.getViewResolvers().put("SliderQuizItem", new DefaultViewResolver(com.cube.storm.ui.quiz.model.quiz.SliderQuizItem.class, SliderQuizItemViewHolder.Factory.class));
			this.uiSettings.getViewResolvers().put("AreaQuizItem", new DefaultViewResolver(com.cube.storm.ui.quiz.model.quiz.AreaQuizItem.class, AreaQuizItemViewHolder.Factory.class));
			this.uiSettings.getViewResolvers().put("QuizPage", new DefaultViewResolver(com.cube.storm.ui.quiz.model.page.QuizPage.class, null));

			ViewProcessor<? extends Model> baseProcessor = new ViewProcessor<Model>()
			{
				@Override public Class<? extends Model> getClassFromName(String name)
				{
					ViewResolver resolver = UiSettings.getInstance().getViewResolvers().get(name);

					if (resolver != null)
					{
						return resolver.resolveModel();
					}

					return null;
				}
			};

			this.uiSettings.getViewProcessors().put(QuizItem.class, baseProcessor);
		}

		/**
		 * Call this to prevent the default {@link com.cube.storm.ui.quiz.lib.provider.QuizIntentProvider} from being added to {@link UiSettings#getIntentProviders()}
		 *
		 * @param skip Set this to true if you are already handling quiz intents
		 *
		* @return The {@link Builder} instance for chaining
		 */
		public Builder skipIntentProvider(boolean skip)
		{
			skipIntentProvider = skip;
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
			if (!skipIntentProvider)
			{
				uiSettings.getIntentProviders().add(new QuizIntentProvider());
			}

			return (QuizSettings.instance = construct);
		}
	}
}
