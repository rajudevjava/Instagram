package com.instagram;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class InstagramImagesAdapter extends BaseAdapter {
	private HashMap<ImageView, InstagramImagesDownload> map;

	private JSONObject images;
	private Context ctx;
	private int size = 0;

	public InstagramImagesAdapter(Context c, JSONObject imageData, int number) {
		this.ctx = c;
		this.images = imageData;
		map = new HashMap<ImageView, InstagramImagesDownload>();
		size = number;

	}

	@Override
	public int getCount() {
		try {
			return images.getJSONArray("data").length();
		} catch (JSONException e) {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView displayImage;

		if (convertView == null) {

			if (size == 0) {
				size++;
				displayImage = new ImageView(ctx);
				displayImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
				displayImage.setLayoutParams(new GridView.LayoutParams(100, 100));
			} else if (size == 1) {
				size++;

				displayImage = new ImageView(ctx);
				displayImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
				displayImage.setLayoutParams(new GridView.LayoutParams(100, 100));
			} else {
				size = 0;
				displayImage = new ImageView(ctx);
				displayImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
				displayImage.setLayoutParams(new GridView.LayoutParams(220, 220));

			}
		} else {
			displayImage = (ImageView) convertView;
		}

		displayImage.setImageBitmap(null);

		try {
			InstagramImagesDownload task = new InstagramImagesDownload(displayImage);

			InstagramImagesDownload other = map.put(displayImage, task);

			if (other != null)
				other.cancel(false);

			String url = images.getJSONArray("data").getJSONObject(position)
					.getJSONObject("images").getJSONObject("thumbnail")
					.getString("url");

			if (!task.searchCache(url))
				task.execute(url);

		} catch (JSONException e) {

		}

		return displayImage;
	}

}
