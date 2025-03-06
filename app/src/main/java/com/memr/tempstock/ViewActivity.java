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
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.graphics.Typeface;
import com.bumptech.glide.Glide;
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

public class ViewActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	
	private double nua = 0;
	private String path = "";
	private String filename = "";
	private String myurl = "";
	private String result = "";
	private double size = 0;
	private double position = 0;
	private String description = "";
	private double sumCount = 0;
	private boolean isShowing = false;
	private HashMap<String, Object> favourite = new HashMap<>();
	private String Date = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String Category = "";
	private String Title = "";
	
	private ArrayList<String> options = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> lm = new ArrayList<>();
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
	private ImageView imageview1;
	
	private TimerTask timer;
	private SharedPreferences favoritesData;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.view);
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
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		favoritesData = getSharedPreferences("favoritesData", Activity.MODE_PRIVATE);
		
		bottomnavigation1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				final int _itemId = item.getItemId();
				if (_itemId == 1) {
					myurl = getIntent().getStringExtra("url");
					SketchwareUtil.showMessage(getApplicationContext(), "Downloading");
					new DownloadTask().execute(myurl);
					StartAppAd.showAd(ViewActivity.this);
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
	}
	
	private void initializeLogic() {
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/manrope_bold.ttf"), 0);
		Category = getIntent().getStringExtra("category");
		Title = getIntent().getStringExtra("title");
		Glide.with(getApplicationContext()).load(Uri.parse(getIntent().getStringExtra("url"))).into(imageview1);
		textview1.setText(Title);
		textview2.setText(Category);
		map = new HashMap<>();
		map.put("title", getIntent().getStringExtra("title"));
		map.put("category", getIntent().getStringExtra("category"));
		map.put("url", getIntent().getStringExtra("url"));
		map.put("type", getIntent().getStringExtra("type"));
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
		StartAppSDK.init(ViewActivity.this, "211962955", false);
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
	
	@Override
	public void onBackPressed() {
		
		
		finish();
	}
	
	public void _extra () {
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
	
	
	public void _Elevation (final View _view, final double _number) {
		
		_view.setElevation((int)_number);
	}
	
	
	public void _transparent () {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { 
			Window w = this.getWindow();w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			w.setStatusBarColor(0xFF008375); w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); }
	}
	
	
	public void _roundedCorners (final View _view, final double _one, final double _two, final double _three, final double _four, final String _color, final double _stroke, final String _stColor, final double _num, final String _NOTES) {
		Double left_top = _one;
		Double right_top = _two;
		Double right_bottom = _three;
		Double left_bottom = _four;
		android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
		s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		s.setCornerRadii(new float[] {left_top.floatValue(),left_top.floatValue(), right_top.floatValue(),right_top.floatValue(), left_bottom.floatValue(),left_bottom.floatValue(), right_bottom.floatValue(),right_bottom.floatValue()});
		s.setColor(Color.parseColor(_color));
		s.setStroke((int)_stroke, Color.parseColor(_stColor));
		_view.setBackground(s);
		_view.setElevation((int)_num);
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