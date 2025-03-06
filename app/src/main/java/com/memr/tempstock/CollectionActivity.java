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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import com.startapp.sdk.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import com.startapp.sdk.adsbase.*;
import com.startapp.sdk.adsbase.adlisteners.*;
import com.startapp.sdk.ads.banner.*;

public class CollectionActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String category = "";
	private double num1 = 0;
	private double num2 = 0;
	private double length = 0;
	private double r = 0;
	private String value1 = "";
	private String save = "";
	private String tmp1 = "";
	private String tmp2 = "";
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> map1 = new HashMap<>();
	private HashMap<String, Object> map2 = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> Collection = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> Collection1 = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> tempList = new ArrayList<>();
	
	private LinearLayout base;
	private LinearLayout linear1;
	private GridView grid;
	private LinearLayout linear2;
	
	private Intent i = new Intent();
	private DatabaseReference list_images = _firebase.getReference("list_images");
	private ChildEventListener _list_images_child_listener;
	private DatabaseReference list_videos = _firebase.getReference("list_videos");
	private ChildEventListener _list_videos_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.collection);
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
		base = (LinearLayout) findViewById(R.id.base);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		grid = (GridView) findViewById(R.id.grid);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		
		_list_images_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		list_images.addChildEventListener(_list_images_child_listener);
		
		_list_videos_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		list_videos.addChildEventListener(_list_videos_child_listener);
	}
	
	private void initializeLogic() {
		setTitle(getIntent().getStringExtra("category"));
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
		
		_toolbar.setTitleTextColor(Color.parseColor("#505050"));
		grid.setNumColumns(2); 
		grid.setColumnWidth(GridView.AUTO_FIT); 
		grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
		grid.setVerticalScrollBarEnabled(false); 
		list_videos.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				Collection1 = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						Collection1.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				list_images.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						Collection = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								Collection.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						Collection1.addAll(Collection);
						save = new Gson().toJson(Collection1);
						_Search(getIntent().getStringExtra("category"), save, Collection1);
						SketchwareUtil.sortListMap(Collection1, "views", false, true);
						_refresh();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		/*
*/
		StartAppSDK.init(CollectionActivity.this, "211962955", false);
		Banner iklan1 = new Banner(CollectionActivity.this);
		iklan1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		linear2.addView(iklan1);
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
	
	public void _refresh () {
		grid.setAdapter(new GridAdapter(Collection1));
		((BaseAdapter)grid.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _Elevation (final View _view, final double _number) {
		
		_view.setElevation((int)_number);
	}
	
	
	public void _Search (final String _Keyword, final String _save, final ArrayList<HashMap<String, Object>> _list) {
		Collection1 = new Gson().fromJson(_save, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		length = Collection1.size();
		r = length - 1;
		for(int _repeat17 = 0; _repeat17 < (int)(length); _repeat17++) {
			value1 = Collection1.get((int)r).get("category").toString();
			if (!(_Keyword.length() > value1.length()) && value1.toLowerCase().contains(_Keyword.toLowerCase())) {
				
			}
			else {
				Collection1.remove((int)(r));
				_refresh();
			}
			r--;
		}
		_refresh();
	}
	
	
	public void _Menu () {
		//You Cant add item 
	}
	@Override
	public boolean onCreateOptionsMenu (Menu menu){
		menu.add(0, 0, 0, "Popularity");
		menu.add(0, 1, 1, "Recently Added");
		return true;
	}
	//In Hare
	
	 @Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case 0:
			// your code or block
			SketchwareUtil.showMessage(getApplicationContext(), "item1");
			break;
			case 1:
			// your code or block
			SketchwareUtil.showMessage(getApplicationContext(), "item 2");
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public class GridAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public GridAdapter(ArrayList<HashMap<String, Object>> _arr) {
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
			
			if (_data.get((int)_position).get("type").toString().equals("video")) {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("thumbnail").toString())).into(imageview1);
			}
			else {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("url").toString())).into(imageview1);
				imageview2.setVisibility(View.INVISIBLE);
			}
			cardview2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_data.get((int)_position).get("type").toString().equals("video")) {
						list_videos.addListenerForSingleValueEvent(new ValueEventListener() {
							@Override
							public void onDataChange(DataSnapshot _dataSnapshot) {
								tempList = new ArrayList<>();
								try {
									GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
									for (DataSnapshot _data : _dataSnapshot.getChildren()) {
										HashMap<String, Object> _map = _data.getValue(_ind);
										tempList.add(_map);
									}
								}
								catch (Exception _e) {
									_e.printStackTrace();
								}
								for(HashMap<String,Object> hmap:tempList){
												for(Object str:hmap.values()){
														if (str.equals(_data.get((int)_position).get("url").toString())) {
																tmp1 = (String) hmap.get("views");
											
														}
														
												}
										}
								map1.put("views", String.valueOf((long)(Double.parseDouble(tmp1) + 1.0d)));
								list_videos.child(_data.get((int)_position).get("key").toString()).updateChildren(map1);
								map1.clear();
								tempList.clear();
							}
							@Override
							public void onCancelled(DatabaseError _databaseError) {
							}
						});
						//add viewer function
						i.setClass(getApplicationContext(), VideoViewActivity.class);
						i.putExtra("url", _data.get((int)_position).get("url").toString());
						i.putExtra("category", _data.get((int)_position).get("category").toString());
						i.putExtra("title", _data.get((int)_position).get("title").toString());
						i.putExtra("type", _data.get((int)_position).get("type").toString());
						i.putExtra("thumbnail", _data.get((int)_position).get("thumbnail").toString());
						startActivity(i);
					}
					else {
						list_images.addListenerForSingleValueEvent(new ValueEventListener() {
							@Override
							public void onDataChange(DataSnapshot _dataSnapshot) {
								tempList = new ArrayList<>();
								try {
									GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
									for (DataSnapshot _data : _dataSnapshot.getChildren()) {
										HashMap<String, Object> _map = _data.getValue(_ind);
										tempList.add(_map);
									}
								}
								catch (Exception _e) {
									_e.printStackTrace();
								}
								for(HashMap<String,Object> hmap:tempList){
												for(Object str:hmap.values()){
														if (str.equals(_data.get((int)_position).get("url").toString())) {
																tmp2 = (String) hmap.get("views");
											
														}
														
												}
										}
								map2.put("views", String.valueOf((long)(Double.parseDouble(tmp2) + 1.0d)));
								list_images.child(_data.get((int)_position).get("key").toString()).updateChildren(map2);
								map2.clear();
								tempList.clear();
							}
							@Override
							public void onCancelled(DatabaseError _databaseError) {
							}
						});
						//add viewer function
						i.setClass(getApplicationContext(), ViewActivity.class);
						i.putExtra("url", _data.get((int)_position).get("url").toString());
						i.putExtra("category", _data.get((int)_position).get("category").toString());
						i.putExtra("title", _data.get((int)_position).get("title").toString());
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