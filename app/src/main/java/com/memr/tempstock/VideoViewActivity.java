package com.memr.tempstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.graphics.Typeface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.startapp.sdk.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import com.startapp.sdk.adsbase.*;
import com.startapp.sdk.adsbase.adlisteners.*;
import com.startapp.sdk.ads.banner.*;

public class VideoViewActivity extends  AppCompatActivity  { 
	
	
	private String Title = "";
	private String Category = "";
	private String myurl = "";
	private String path = "";
	private String filename = "";
	private String result = "";
	private double sumCount = 0;
	private double size = 0;
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> Favorites = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private LinearLayout linear3;
	private BottomNavigationView bottomnavigation1;
	private ImageView imageview2;
	private LinearLayout linear5;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear_play;
	private LinearLayout linear_loading;
	private WebView webview1;
	private TextView tx_load;
	
	private SharedPreferences favoritesData;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.video_view);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		bottomnavigation1 = (BottomNavigationView) findViewById(R.id.bottomnavigation1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		linear_play = (LinearLayout) findViewById(R.id.linear_play);
		linear_loading = (LinearLayout) findViewById(R.id.linear_loading);
		webview1 = (WebView) findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		tx_load = (TextView) findViewById(R.id.tx_load);
		favoritesData = getSharedPreferences("favoritesData", Activity.MODE_PRIVATE);
		
		bottomnavigation1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				final int _itemId = item.getItemId();
				if (_itemId == 1) {
					myurl = getIntent().getStringExtra("url");
					SketchwareUtil.showMessage(getApplicationContext(), "Downloading");
					new DownloadTask().execute(myurl);
					StartAppAd.showAd(VideoViewActivity.this);
				}
				if (_itemId == 2) {
					if (Favorites.contains(map)) {
						Favorites.remove(map);
						favoritesData.edit().putString("key", new Gson().toJson(Favorites)).commit();
						//bottom navigation icon change
						item.setIcon(R.drawable.favourites);
					}
					else {
						Favorites.add(map);
						favoritesData.edit().putString("key", new Gson().toJson(Favorites)).commit();
						//bottom navigation icon change
						item.setIcon(R.drawable.favorite);
					}
				}
				return true;
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
	}
	
	private void initializeLogic() {
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.ttf"), 0);
		Category = getIntent().getStringExtra("category");
		Title = getIntent().getStringExtra("title");
		textview1.setText(Title);
		textview2.setText(Category);
		map = new HashMap<>();
		map.put("title", getIntent().getStringExtra("title"));
		map.put("category", getIntent().getStringExtra("category"));
		map.put("url", getIntent().getStringExtra("url"));
		map.put("type", getIntent().getStringExtra("type"));
		map.put("thumbnail", getIntent().getStringExtra("thumbnail"));
		if (!favoritesData.getString("key", "").equals("")) {
			Favorites = new Gson().fromJson(favoritesData.getString("key", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		}
		bottomnavigation1.getMenu().add(0, 1, 0, "Save").setIcon(R.drawable.download2);
		if (Favorites.contains(map)) {
			bottomnavigation1.getMenu().add(0, 2, 0, "Favourite").setIcon(R.drawable.favorite);
		}
		else {
			bottomnavigation1.getMenu().add(0, 2, 0, "Favourite").setIcon(R.drawable.favourites);
		}
		/*
*/
		bottomnavigation1.setItemIconTintList(null);
		webview1.setWebChromeClient(new CustomWebClient());
		_Play_video_At(webview1, getIntent().getStringExtra("url"));
		_IfWebViewFailedToLd(webview1);
		linear_loading.setVisibility(View.GONE);
		StartAppSDK.init(VideoViewActivity.this, "211962955", false);
		StartAppAd.disableSplash();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _Play_video_At (final WebView _video, final String _Path) {
		_video.loadUrl(_Path);
	}
	
	
	public void _extra () {
	}
	public class CustomWebClient extends WebChromeClient {
			private View mCustomView;
			private WebChromeClient.CustomViewCallback mCustomViewCallback;
			protected FrameLayout frame;
			
			// Initially mOriginalOrientation is set to Landscape
			private int mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
			private int mOriginalSystemUiVisibility;
			
			// Constructor for CustomWebClient
			public CustomWebClient() {}
			
			public Bitmap getDefaultVideoPoster() {
					if (VideoViewActivity.this == null) {
							return null; 
					}
					return BitmapFactory.decodeResource(VideoViewActivity.this.getApplicationContext().getResources(), 2130837573); 
				
			}
			
			public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback viewCallback) {
					if (this.mCustomView != null) {
							onHideCustomView();
							return; 
					}
					this.mCustomView = paramView;
					this.mOriginalSystemUiVisibility = VideoViewActivity.this.getWindow().getDecorView().getSystemUiVisibility();
					// When CustomView is shown screen orientation changes to mOriginalOrientation (Landscape).
					VideoViewActivity.this.setRequestedOrientation(this.mOriginalOrientation);
					// After that mOriginalOrientation is set to portrait.
					this.mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
					this.mCustomViewCallback = viewCallback; ((FrameLayout)VideoViewActivity.this.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1)); VideoViewActivity.this.getWindow().getDecorView().setSystemUiVisibility(3846);
			}
			
			public void onHideCustomView() {
					((FrameLayout)VideoViewActivity.this.getWindow().getDecorView()).removeView(this.mCustomView);
					this.mCustomView = null;
					VideoViewActivity.this.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
					// When CustomView is hidden, screen orientation is set to mOriginalOrientation (portrait).
					VideoViewActivity.this.setRequestedOrientation(this.mOriginalOrientation);
					// After that mOriginalOrientation is set to landscape.
					this.mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; this.mCustomViewCallback.onCustomViewHidden();
					this.mCustomViewCallback = null;
			}
	}
		
	{
	}
	
	
	public void _IfWebViewFailedToLd (final WebView _wb) {
		_wb.setWebViewClient(new WebViewClient(){ 
				@Override public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) { 
						super.onReceivedError(view, request, error);
						
				linear_loading.setVisibility(View.VISIBLE);
			} 
		});
	}
	
	
	public void _extra_downloader () {
	}
	private class DownloadTask extends AsyncTask<String, Integer, String> {
		 @Override
		protected void onPreExecute() {
		}
		protected String doInBackground(String... address) {
			try {
				filename= URLUtil.guessFileName(address[0], null, null);
				int resCode = -1;
				java.io.InputStream in = null;
				java.net.URL url = new java.net.URL(address[0]);
				java.net.URLConnection urlConn = url.openConnection();
				if (!(urlConn instanceof java.net.HttpURLConnection)) {
					throw new java.io.IOException("URL is not an Http URL"); }
				java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) urlConn; httpConn.setAllowUserInteraction(false); httpConn.setInstanceFollowRedirects(true); httpConn.setRequestMethod("GET"); httpConn.connect();
				resCode = httpConn.getResponseCode();
				if (resCode == java.net.HttpURLConnection.HTTP_OK) {
					in = httpConn.getInputStream();
					size = httpConn.getContentLength();
					
				} else { result = "There was an error"; }
				path = FileUtil.getExternalStorageDir().concat("/".concat("Tempstock")).concat("/".concat(filename));
				FileUtil.writeFile(path, "");
				java.io.File file = new java.io.File(path);
				
				java.io.OutputStream output = new java.io.FileOutputStream(file);
				try {
					int bytesRead;
					sumCount = 0;
					byte[] buffer = new byte[1024];
					while ((bytesRead = in.read(buffer)) != -1) {
						output.write(buffer, 0, bytesRead);
						sumCount += bytesRead;
						if (size > 0) {
							publishProgress((int)Math.round(sumCount*100 / size));
						}
					}
				} finally {
					output.close();
				}
				result = filename + " Downloaded to the Tempstock folder";
				in.close();
			} catch (java.net.MalformedURLException e) {
				result = e.getMessage();
			} catch (java.io.IOException e) {
				result = e.getMessage();
			} catch (Exception e) {
				result = e.toString();
			}
			return result;
			
		}
		protected void onProgressUpdate(Integer... values) {
			//textview16.setText(values[values.length - 1] + "% Descargada");
			//progressbar2.setProgress(values[values.length - 1]);
			
		}
		protected void onPostExecute(String s){
			
			showMessage(s);
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}