package com.cube.storm.ui.quiz.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.activity.StormActivity;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.lib.manager.BadgeManager;
import com.cube.storm.ui.quiz.model.page.QuizPage;
import com.cube.storm.ui.quiz.model.property.BadgeProperty;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import lombok.Getter;

public class StormQuizWinFragment extends Fragment implements OnClickListener
{
	@Getter private QuizPage page;
	protected TextView winTitle;
	protected TextView winDescription;
	protected Button home;
	protected ImageView badge;
	protected ViewGroup embeddedLinksContainer;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.quiz_win_view, container, false);

		winTitle = (TextView)v.findViewById(R.id.win_title);
		winDescription = (TextView)v.findViewById(R.id.win_description);
		home = (Button)v.findViewById(R.id.home_button);
		badge = (ImageView)v.findViewById(R.id.badge_icon);
		embeddedLinksContainer = (ViewGroup)v.findViewById(R.id.related_container);

		home.setText("Home");
		home.setOnClickListener(this);

		return v;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (getArguments().containsKey(StormActivity.EXTRA_PAGE))
		{
			page = (QuizPage)getArguments().get(StormActivity.EXTRA_PAGE);
		}
		else if (getArguments().containsKey(StormActivity.EXTRA_URI))
		{
			String pageUri = getArguments().getString(StormActivity.EXTRA_URI);
			page = (QuizPage)UiSettings.getInstance().getViewBuilder().buildPage(Uri.parse(pageUri));
		}

		if (page == null)
		{
			Toast.makeText(getActivity(), "Failed to load page", Toast.LENGTH_SHORT).show();
			getActivity().finish();

			return;
		}

		if (page.getTitle() != null && !TextUtils.isEmpty(page.getTitle().getContent()))
		{
			winTitle.setText(UiSettings.getInstance().getTextProcessor().process(page.getTitle().getContent()));
		}
		else
		{
			winTitle.setVisibility(View.GONE);
		}

		loadBadge();

		if (page.getWinRelatedLinks() != null)
		{
			embeddedLinksContainer.removeAllViews();

			for (LinkProperty link : page.getWinRelatedLinks())
			{
				final LinkProperty property = link;

				View embeddedLinkView = LayoutInflater.from(embeddedLinksContainer.getContext()).inflate(R.layout.button_embedded_link, embeddedLinksContainer, false);
				if (embeddedLinkView != null)
				{
					Button button = (Button)embeddedLinkView.findViewById(R.id.button);
					button.setText(property.getTitle().getContent());

					button.setOnClickListener(new View.OnClickListener()
					{
						@Override public void onClick(View v)
						{
							UiSettings.getInstance().getLinkHandler().handleLink(v.getContext(), property);
						}
					});

					embeddedLinksContainer.setVisibility(View.VISIBLE);
					embeddedLinksContainer.addView(button);
				}
			}
		}
	}

	public void loadBadge()
	{
		final BadgeProperty badgeProperty = BadgeManager.getInstance().getBadgeById(page.getBadgeId());

		if (badgeProperty != null)
		{
			UiSettings.getInstance().getImageLoader().displayImage(badgeProperty.getIcon().getSrc(), badge, new SimpleImageLoadingListener()
			{
				@Override public void onLoadingStarted(String imageUri, View view)
				{
					badge.setVisibility(View.INVISIBLE);
				}

				@Override public void onLoadingFailed(String imageUri, View view, FailReason failReason)
				{
					if (!imageUri.equalsIgnoreCase(badgeProperty.getIcon().getFallbackSrc()))
					{
						UiSettings.getInstance().getImageLoader().displayImage(badgeProperty.getIcon().getFallbackSrc(), badge, this);
					}

					badge.setVisibility(View.VISIBLE);
				}

				@Override public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
				{
					badge.setVisibility(View.VISIBLE);
				}
			});

			if (badgeProperty.getTitle() != null && !TextUtils.isEmpty(badgeProperty.getTitle().getContent()))
			{
				winTitle.setText(UiSettings.getInstance().getTextProcessor().process(badgeProperty.getCompletion().getContent()));
				winDescription.setText(UiSettings.getInstance().getTextProcessor().process(badgeProperty.getCompletion().getContent()));
			}
		}
	}

	@Override public void onClick(View v)
	{
		if (v == home)
		{
			getActivity().finish();
		}
	}
}
