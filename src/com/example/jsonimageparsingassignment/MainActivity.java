package com.example.jsonimageparsingassignment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts.Photos;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	ArrayAdapter<Photo> adapter;
	ArrayList<Photo> photolist = new ArrayList<Photo>();
	//Button buttonLoad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		listView = (ListView) findViewById(R.id.listView1);
		String url = "http://jsonplaceholder.typicode.com/albums/1/photos";
		PhotoTask task = new PhotoTask();
		task.execute(url);
		 
		adapter = new ArrayAdapter<Photo>(MainActivity.this,android.R.layout.simple_list_item_1,photolist);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Photo photo = photolist.get(arg2);
				Intent in=  new Intent(MainActivity.this, PhotoActivity.class);
				in.putExtra("url", photo.getUrl());
				startActivity(in);
			}
		});
	}
	
	
	
	class PhotoTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String Url = params[0];
			HttpGet getRequest = new HttpGet(Url);
			String result = "";
			try {
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(getRequest);
				InputStream is = response.getEntity().getContent();

				// convert i/p stream in String
				InputStreamReader reader = new InputStreamReader(is);
				BufferedReader bufferedReader = new BufferedReader(reader);
				while (true) {
					String line = bufferedReader.readLine();
					if (line == null)
						break;
					else {
						result += line;
						Log.e("line", line);
					}
				}// end of while
				bufferedReader.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return result;
		}// end of do IN background

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("JSON", result);
			// JSON Parsing
			try {
				
				JSONArray jarray =new JSONArray(result);
				//JSONObject idObj = wArray.getJSONObject("id");
				for(int i = 0;i<jarray.length();i++)
				{
					JSONObject jobj=jarray.getJSONObject(i);
					String id=jobj.getString("id");
					String title=jobj.getString("title");
					String url=jobj.getString("url");
					photolist.add(new Photo(id, title, url));
			
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
