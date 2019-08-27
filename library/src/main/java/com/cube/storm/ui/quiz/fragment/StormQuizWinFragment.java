package com.cube.storm.ui.quiz.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.activity.StormActivity;
import com.cube.storm.ui.activity.StormInterface;
import com.cube.storm.ui.lib.helper.ImageHelper;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.quiz.R;
import com.cube.storm.ui.quiz.lib.manager.BadgeManager;
import com.cube.storm.ui.quiz.model.page.QuizPage;
import com.cube.storm.ui.quiz.model.property.BadgeProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import lombok.Getter;

public class StormQuizWinFragment extends Fragment implements OnClickListener, StormInterface
{
	@Getter protected QuizPage page;
	@Getter protected TextView winTitle;
	@Getter protected TextView winDescription;
	@Getter protected Button share;
	@Getter protected ImageView badge;
	@Getter protected ViewGroup embeddedLinksContainer;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflater.inflate(getLayoutResource(), container, false);

		winTitle = (TextView)v.findViewById(R.id.win_title);
		winDescription = (TextView)v.findViewById(R.id.win_description);
		share = (Button)v.findViewById(R.id.share_button);
		badge = (ImageView)v.findViewById(R.id.badge_icon);
		embeddedLinksContainer = (ViewGroup)v.findViewById(R.id.related_container);

		share.setOnClickListener(this);

		return v;
	}

	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

	@Override public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (getArguments().containsKey(StormActivity.EXTRA_URI))
		{
			String pageUri = getArguments().getString(StormActivity.EXTRA_URI);
			loadPage(pageUri);
		}
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.menu, menu);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.menu_home)
		{
			if (getActivity() != null)
			{
				getActivity().finish();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	protected Uri saveBadgeToTemp(BadgeProperty badgeProperty)
	{
		Bitmap badgeBitmap = null;
		InputStream stream = UiSettings.getInstance().getFileFactory().loadFromUri(Uri.parse(ImageHelper.getImageSrc(badgeProperty.getIcon())));

		if (stream != null)
		{
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inScaled = false;
			badgeBitmap = BitmapFactory.decodeStream(stream, new Rect(0, 0, 0, 0), opt);
		}

		File tmpFile = null;
		try
		{
			tmpFile = new File(getActivity().getExternalCacheDir(), "badge-" + System.currentTimeMillis() + ".png");
			FileOutputStream out = new FileOutputStream(tmpFile);

			if (badgeBitmap != null)
			{
				badgeBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			}

			out.close();

			return FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", tmpFile);
		}
		catch (Exception e)
		{
			Log.d(getClass().getName(), "Failed to save the badge");
		}

		//Fallback to default app's icon
		return Uri.parse("android.resource://"+getActivity().getPackageName()+"/drawable/ic_launcher");
	}

	public void loadBadge()
	{
		final BadgeProperty badgeProperty = BadgeManager.getInstance().getBadgeById(page.getBadgeId());

		if (badgeProperty != null)
		{
			badgeProperty.setAchieved(getActivity(), true);
			ImageHelper.displayImage(badge, badgeProperty.getIcon());

			if (badgeProperty.getTitle() != null)
			{
				if (winTitle.getVisibility() == View.GONE)
				{
					winTitle.setText(UiSettings.getInstance().getTextProcessor().process(badgeProperty.getTitle()));
					winTitle.setVisibility(View.VISIBLE);
				}

				winDescription.setText(UiSettings.getInstance().getTextProcessor().process(badgeProperty.getCompletion()));
			}
		}
	}

	@Override public void onClick(View v)
	{
		if (v == share)
		{
			BadgeProperty badge = BadgeManager.getInstance().getBadgeById(getPage().getBadgeId());
			Context context = v.getContext();

			if (badge != null)
			{
				String badgeTitle = "a badge";

				if (badge.getTitle() != null)
				{
					badgeTitle = UiSettings.getInstance().getTextProcessor().process(badge.getTitle());
				}

				String shareText = String.format("I've just earned %s on the %s app!", badgeTitle, getString(R.string.app_name));

				if (badge.getShareMessage() != null)
				{
					shareText = UiSettings.getInstance().getTextProcessor().process(badge.getShareMessage());
				}

				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("*/*");
				shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

				Uri badgeUri = saveBadgeToTemp(badge);

				if (badgeUri != null)
				{
					shareIntent.putExtra(Intent.EXTRA_STREAM, badgeUri);
				}

				startActivity(shareIntent);
			}
		}
	}

	@Override public int getLayoutResource()
	{
		return R.layout.quiz_win_view;
	}

	@Override public void loadPage(String pageUri)
	{
		page = (QuizPage)UiSettings.getInstance().getViewBuilder().buildPage(Uri.parse(pageUri));

		if (page != null)
		{
			if (page.getTitle() != null)
			{
				winTitle.setText(UiSettings.getInstance().getTextProcessor().process(page.getTitle()));
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
						button.setText(UiSettings.getInstance().getTextProcessor().process(property.getTitle()));

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
	}

	@Override public void onLoadFail()
	{
		Toast.makeText(getActivity(), "Failed to load page", Toast.LENGTH_SHORT).show();
		getActivity().finish();
	}
}
