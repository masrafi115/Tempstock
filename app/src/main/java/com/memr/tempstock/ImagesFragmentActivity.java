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
import java.util.Timer;
import java.util.TimerTask;
import com.bumptech.glide.Glide;
import com.startapp.sdk.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import com.startapp.sdk.adsbase.*;
import com.startapp.sdk.adsbase.adlisteners.*;
import com.startapp.sdk.ads.banner.*;

public class ImagesFragmentActivity extends  Fragment  { 
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String tmp1 = "";
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	private ArrayList<String> keys = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> tempList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> sliderList = new ArrayList<>();
	
	private LinearLayout linear_slider;
	private GridView gridview1;
	private LinearLayout linear1;
	
	private DatabaseReference list_images = _firebase.getReference("list_images");
	private ChildEventListener _list_images_child_listener;
	private Intent i = new Intent();
	private TimerTask t;
	private DatabaseReference app = _firebase.getReference("app");
	private ChildEventListener _app_child_listener;
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.images_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		com.google.firebase.FirebaseApp.initializeApp(getContext());
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		
		linear_slider = (LinearLayout) _view.findViewById(R.id.linear_slider);
		gridview1 = (GridView) _view.findViewById(R.id.gridview1);
		linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
		
		_list_images_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				list.add(_childValue);
				keys.add(_childKey);
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
		list_images.addChildEventListener(_list_images_child_listener);
		
		_app_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals("data")) {
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("urlToImage", _childValue.get("heading1").toString());
						sliderList.add(_item);
					}
					
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("urlToImage", _childValue.get("heading2").toString());
						sliderList.add(_item);
					}
					
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("urlToImage", _childValue.get("heading3").toString());
						sliderList.add(_item);
					}
					
					_sliderview();
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals("data")) {
					sliderList.get((int)0).put("urlToImage", _childValue.get("heading1").toString());
					sliderList.get((int)1).put("urlToImage", _childValue.get("heading2").toString());
					sliderList.get((int)2).put("urlToImage", _childValue.get("heading3").toString());
					//((androidx.viewpager.widget.PagerAdapter)vp.getAdapter()).notifyDataSetChanged();
				}
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
		app.addChildEventListener(_app_child_listener);
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
	
	
	public void _adp () {
	}
	public class SliderAdapter extends androidx.viewpager.widget.PagerAdapter {
		    
		final Intent ik=new Intent();
		private Context context;
		ArrayList<HashMap<String,Object>>maps;
		public SliderAdapter(Context context,ArrayList<HashMap<String,Object>>maps){
			this.context=context;
			this.maps=maps;
			    }
		 @Override
		    public int getCount() {
			        return maps.size();
			    }
		 @Override
		    public Object instantiateItem(ViewGroup container,final int position) {
			LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View slideLayout=inflater.inflate(R.layout.slider,null);
			LinearLayout linear1=slideLayout.findViewById(R.id.linear1);
			LinearLayout linear2=slideLayout.findViewById(R.id.linear2);
			final androidx.cardview.widget.CardView cv = new androidx.cardview.widget.CardView(getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			
			int mgs = (int)getDip(4);
			
			lp.setMargins(mgs,mgs,mgs,mgs);
			cv.setLayoutParams(lp);
			
			cv.setCardBackgroundColor(Color.WHITE);
			
			cv.setRadius((int)getDip(7));
			
			cv.setCardElevation(5);
			
			cv.setMaxCardElevation(12);
			
			cv.setPreventCornerOverlap(true);
			((ViewGroup)linear2.getParent()).removeView(linear2);
			linear1.addView(cv);
			
			cv.addView(linear2);
			ImageView imgSlider=slideLayout.findViewById(R.id.img_slide);
			Glide.with(getContext()).load(sliderList.get(position).get("urlToImage").toString()).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL).placeholder(R.drawable.ic_load).into(imgSlider);
			/*imgSlider.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View p1) {
is.setAction(Intent.ACTION_VIEW);
is.setData(Uri.parse(sliderList.get(position).get("url").toString()));
startActivity(is);
     }
                
            
        });*/
			container.addView(slideLayout);
			        return slideLayout;
			        
			    }
		   @Override
		    public boolean isViewFromObject(View p1, Object p2) {
			        return p1==p2;
			    }
		 @Override
		    public void destroyItem(ViewGroup container, int position, Object object) {
			        container.removeView((View)object);
			    }
		/*
}else if(sliderList.get(position).get("activity").toString().equals("screen")){

startActivity(i);
}
if(sliderList.get(position).get("activity").toString().equals("actionView")){
*/
		
	}
	
	
	public void _sliderview () {
		final androidx.viewpager.widget.ViewPager vp=new androidx.viewpager.widget.ViewPager(getContext());
		        vp.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
		SliderAdapter adapter=new SliderAdapter(getContext(),sliderList);
		vp.setAdapter(adapter);
		Timer timer=new Timer();
		        timer.scheduleAtFixedRate(new SliderTimer((vp)),4000,5000);
		linear_slider.addView(vp);
	}
	
	
	public void _get_Dip () {
	} public float getDip(int _input){
				return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	
	public void _TimeSlider () {
	}
	public class SliderTimer extends TimerTask {
		androidx.viewpager.widget.ViewPager vp;
		
		public SliderTimer(androidx.viewpager.widget.ViewPager vp){
			 this.vp=vp;
		}
		        @Override
		        public void run() {
			            
			            getActivity().runOnUiThread(new Runnable(){
				
				                    @Override
				                    public void run() {
					                        if(vp.getCurrentItem()<sliderList.size()-1){
						                            
						vp.setCurrentItem(vp.getCurrentItem()+1);
						                        }else{
						                            vp.setCurrentItem(0);
						                        }
					                    }
				                    
				                
				            });
			            
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
			
			Glide.with(getContext()).load(Uri.parse(_data.get((int)_position).get("url").toString())).into(imageview1);
			imageview2.setVisibility(View.INVISIBLE);
			cardview2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
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
															tmp1 = (String) hmap.get("views");
										
													}
													
											}
									}
							map.put("views", String.valueOf((long)(Double.parseDouble(tmp1) + 1.0d)));
							list_images.child(keys.get((int)(_position))).updateChildren(map);
							tempList.clear();
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
						}
					});
					i.setClass(getContext(), ViewActivity.class);
					i.putExtra("title", _data.get((int)_position).get("title").toString());
					i.putExtra("category", _data.get((int)_position).get("category").toString());
					i.putExtra("url", _data.get((int)_position).get("url").toString());
					i.putExtra("type", _data.get((int)_position).get("type").toString());
					startActivity(i);
				}
			});
			
			return _view;
		}
	}
	
	
}