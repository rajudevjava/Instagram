package com.instagram;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class InstagramMainActivity extends BaseInstagramActivity {

	private GridView imageGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imageGridView = (GridView) findViewById(R.id.image_grid_view);
		downloadImagesFromInstagram(this, imageGridView);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		imageGridView.setNumColumns(metrics.widthPixels / WIDTH_OF_TILE);

		imageGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				Intent intent = new Intent(InstagramMainActivity.this,
						InstagramZoomActivity.class);

				try {

					String url = getInstagramInformation().getJSONArray("data")
							.getJSONObject(position).getJSONObject("images")
							.getJSONObject("standard_resolution")
							.getString("url");
					intent.putExtra("url", url);
				} catch (JSONException e) {
					intent.putExtra("url", "");
				}

				startActivity(intent);
			}
		});
		imageGridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}
		});

	}

}