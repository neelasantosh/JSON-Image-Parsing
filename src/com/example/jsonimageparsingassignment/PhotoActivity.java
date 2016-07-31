package com.example.jsonimageparsingassignment;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoActivity extends Activity {

	ImageView img;
	Button buttonLoad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_view);

		img = (ImageView) findViewById(R.id.imageView1);
		Intent in = getIntent();

		String url = in.getStringExtra("url");
		LoadImageTask task = new LoadImageTask();
		task.execute(url);
		buttonLoad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});

	}
	class LoadImageTask extends AsyncTask<String, Void, Bitmap>
    {

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=params[0];
			//create GET request
			HttpGet getRequset=new HttpGet(url);
			HttpClient client=new DefaultHttpClient();
			
			//send request to server and get reponse
			Bitmap bmp=null;
			try {
				HttpResponse response=client.execute(getRequset);
				InputStream is=response.getEntity().getContent();
				bmp=BitmapFactory.decodeStream(is);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bmp;
		}//eof doInBack where result of doI
		@Override
		protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		img.setImageBitmap(result);
		}
}

}
