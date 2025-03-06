package com.memr.tempstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
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
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import com.startapp.sdk.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class FavoritesActivity extends  AppCompatActivity  { 
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> Favorites = new ArrayList<>();
	
	private LinearLayout linear1;
	private GridView gridview1;
	
	private Intent i = new Intent();
	private SharedPreferences favoritesData;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.favorites);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		gridview1 = (GridView) findViewById(R.id.gridview1);
		favoritesData = getSharedPreferences("favoritesData", Activity.MODE_PRIVATE);
	}
	
	private void initializeLogic() {
		setTitle("My Favorite");
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
		
		_toolbar.setTitleTextColor(Color.parseColor("#505050"));
		gridview1.setNumColumns(3); 
		gridview1.setColumnWidth(GridView.AUTO_FIT); 
		gridview1.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
		gridview1.setVerticalScrollBarEnabled(false); 
		if (!favoritesData.getString("key", "").equals("")) {
			Favorites = new Gson().fromJson(favoritesData.getString("key", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			_getData();
		}
		else {
			
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _getData () {
		gridview1.setAdapter(new Gridview1Adapter(Favorites));
		((BaseAdapter)gridview1.getAdapter()).notifyDataSetChanged();
	}
	
	
	public class Gridview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Gridview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.custom, null);
			}
			
			final androidx.cardview.widget.CardView cardview2 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview2);
			final RelativeLayout linear1 = (RelativeLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			
			if (_data.get((int)_position).get("type").toString().equals("image")) {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("url").toString())).into(imageview1);
				imageview2.setVisibility(View.GONE);
			}
			else {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("thumbnail").toString())).into(imageview1);
			}
			cardview2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_data.get((int)_position).get("type").toString().equals("video")) {
						i.setClass(getApplicationContext(), VideoViewActivity.class);
						i.putExtra("title", _data.get((int)_position).get("title").toString());
						i.putExtra("category", _data.get((int)_position).get("category").toString());
						i.putExtra("url", _data.get((int)_position).get("url").toString());
						i.putExtra("type", _data.get((int)_position).get("type").toString());
						i.putExtra("thumbnail", _data.get((int)_position).get("thumbnail").toString());
						startActivity(i);
					}
					else {
						i.setClass(getApplicationContext(), ViewActivity.class);
						i.putExtra("title", _data.get((int)_position).get("title").toString());
						i.putExtra("category", _data.get((int)_position).get("category").toString());
						i.putExtra("url", _data.get((int)_position).get("url").toString());
						i.putExtra("type", _data.get((int)_position).get("type").toString());
						startActivity(i);
					}
				}
			});
			
			return _view;
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