package com.instagram;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

public class InstagramZoomActivity extends Activity {

	public class DemoImagesDownloader extends AsyncTask<String, Void, Bitmap> {
		private HashMap<String, Bitmap> cached;

		private final WeakReference<ImageView> demoReferenceImages;

		private String demoUrl;

		/**
		 * Downloads bitmap and sets to ImageView
		 */
		public DemoImagesDownloader(ImageView imageView) {
			demoReferenceImages = new WeakReference<ImageView>(imageView);

			if (cached == null) {
				cached = new HashMap<String, Bitmap>();
			}
		}

		protected Bitmap doInBackground(String... params) {
			demoUrl = params[0];

			if (cached.containsKey(demoUrl)) {
				return cached.get(demoUrl);
			}

			return WebView.retriveBitmap(demoUrl);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			cached.put(demoUrl, bitmap);

			if (isCancelled()) {
				return;
			}

			if (demoReferenceImages != null) {
				ImageView imageView = demoReferenceImages.get();
				if (imageView != null) {

					imageView.setImageBitmap(bitmap);
				}
			}
		}

		public boolean searchCache(String url) {
			if (demoReferenceImages != null) {
				ImageView imageView = demoReferenceImages.get();
				if (imageView != null) {

					if (cached.containsKey(url)) {

						imageView.setImageBitmap(cached.get(url));

						return true;
					}
				}
			}

			return false;
		}

	}

	InstagramImages image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zoom_activity);

		Intent intent = getIntent();
		String url = intent.getExtras().getString("url");

		if (url.length() > 0) {

			image = (InstagramImages) findViewById(R.id.insta_image);

			DemoImagesDownloader downloadTask = new DemoImagesDownloader(image);
			if (!downloadTask.searchCache(url))
				downloadTask.execute(url);

		}
	}
}
