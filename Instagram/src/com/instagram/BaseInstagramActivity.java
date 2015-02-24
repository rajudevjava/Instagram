package com.instagram;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;

public abstract  class BaseInstagramActivity extends Activity implements InstagramConstants {
	
	private JSONObject instagramInformation;
	
	public JSONObject getInstagramInformation() {
		return instagramInformation;
	}


	public void setInstagramInformation(JSONObject information) {
		this.instagramInformation = information;
	}


	protected void downloadImagesFromInstagram(Context context,GridView imageGridView){
		new DownloadImagesFromInstagram(URL_CONSTANT, this, imageGridView).execute();
	}
	
	
	public class DownloadImagesFromInstagram extends AsyncTask<Void, Void, Void> {
		private final String url;
		private final Context ctx;
		private final  GridView imageGridView;
		private int number = 0;

		public DownloadImagesFromInstagram(String instagramUrl, Context ctx,GridView imageGridView) {
			super();
			this.url = instagramUrl;
			this.ctx = ctx;
			this.imageGridView = imageGridView;
		}

		@Override
		protected Void doInBackground(Void... params) {
			instagramInformation = WebView.makeWebServiceCall(url);
			setInstagramInformation(instagramInformation);
			return null;
		}

		@Override
		protected void onPostExecute(Void unused) {
			imageGridView.setAdapter(new InstagramImagesAdapter(ctx,
					instagramInformation, number));
		}

	}

}
