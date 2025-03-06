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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import com.bumptech.glide.Glide;
import com.startapp.sdk.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import com.startapp.sdk.adsbase.*;
import com.startapp.sdk.adsbase.adlisteners.*;
import com.startapp.sdk.ads.banner.*;

public class VideosFragmentActivity extends  Fragment  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	private String tmp1 = "";
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> tempList = new ArrayList<>();
	
	private GridView gridview1;
	private LinearLayout linear1;
	
	private DatabaseReference list_videos = _firebase.getReference("list_videos");
	private ChildEventListener _list_videos_child_listener;
	private Intent i = new Intent();
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.videos_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		com.google.firebase.FirebaseApp.initializeApp(getContext());
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		
		gridview1 = (GridView) _view.findViewById(R.id.gridview1);
		linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
		
		_list_videos_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				list.add(_childValue);
				_refresh();
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
		gridview1.setNumColumns(3); 
		gridview1.setColumnWidth(GridView.AUTO_FIT); 
		gridview1.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
		gridview1.setVerticalScrollBarEnabled(false); 
		StartAppSDK.init(getContext(), "211962955", false);
		Banner iklan1 = new Banner(getContext());
		iklan1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		linear1.addView(iklan1);
		StartAppAd.disableSplash();
	}
	
	@Override
	public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _refresh () {
		gridview1.setAdapter(new Gridview1Adapter(list));
		((BaseAdapter)gridview1.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _setData (final String _text) {
		/*
textview1.setText(_text);
*/
		if (_text.equals("popularity")) {
			SketchwareUtil.sortListMap(list, "views", false, true);
			_refresh();
		}
		else {
			SketchwareUtil.sortListMap(list, "time", false, true);
			_refresh();
		}
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
			LayoutInflater _inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.custom, null);
			}
			
			final androidx.cardview.widget.CardView cardview2 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview2);
			final RelativeLayout linear1 = (RelativeLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			
			Glide.with(getContext()).load(Uri.parse(_data.get((int)_position).get("thumbnail").toString())).into(imageview1);
			cardview2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
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
							map.put("views", String.valueOf((long)(Double.parseDouble(tmp1) + 1.0d)));
							list_videos.child(_data.get((int)_position).get("key").toString()).updateChildren(map);
							map.clear();
							tempList.clear();
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
						}
					});
					i.setClass(getContext(), VideoViewActivity.class);
					i.putExtra("title", _data.get((int)_position).get("title").toString());
					i.putExtra("category", _data.get((int)_position).get("category").toString());
					i.putExtra("url", _data.get((int)_position).get("url").toString());
					i.putExtra("type", _data.get((int)_position).get("type").toString());
					i.putExtra("thumbnail", _data.get((int)_position).get("thumbnail").toString());
					startActivity(i);
				}
			});
			
			return _view;
		}
	}
	
	
}