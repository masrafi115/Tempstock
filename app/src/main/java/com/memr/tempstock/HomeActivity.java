package com.memr.tempstock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.HashMap;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.startapp.sdk.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class HomeActivity extends  AppCompatActivity  { 
	
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private DrawerLayout _drawer;
	private boolean bool = false;
	
	private ArrayList<HashMap<String, Object>> categories_list = new ArrayList<>();
	
	private TabLayout tablayout1;
	private ViewPager viewpager1;
	private NavigationView _drawer_navigation;
	
	private Intent i = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
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
		_drawer = (DrawerLayout) findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(HomeActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		tablayout1 = (TabLayout) findViewById(R.id.tablayout1);
		viewpager1 = (ViewPager) findViewById(R.id.viewpager1);
		_drawer_navigation = (NavigationView) _nav_view.findViewById(R.id.navigation);
		
		viewpager1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageSelected(int _position) {
				if (_position == 0) {
					bool = false;
				}
				if (_position == 1) {
					bool = false;
				}
				if (_position == 2) {
					bool = true;
				}
				HomeActivity.this.invalidateOptionsMenu();
			}
			
			@Override
			public void onPageScrollStateChanged(int _scrollState) {
				
			}
		});
	}
	
	private void initializeLogic() {
		viewpager1.setAdapter(new MyFragmentAdapter(getApplicationContext(), getSupportFragmentManager(), 3));
		tablayout1.setupWithViewPager(viewpager1);
		com.google.android.material.appbar.AppBarLayout appBarLayout = (com.google.android.material.appbar.AppBarLayout) _toolbar.getParent();
		appBarLayout.setStateListAnimator(null);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_grey);
		
		_toolbar.setTitleTextColor(Color.parseColor("#505050"));
		Drawable drawable = androidx.core.content.ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_sort_black);
		        _toolbar.setOverflowIcon(drawable);
		_setDrawerWidth(250);
		//_drawer_navigation.addHeaderView(_drawer_linear1);
		_drawer_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() { 
				@Override public boolean onNavigationItemSelected(MenuItem item) {
						int id = item.getItemId();
						if (id == R.id.downloads){
					i.setClass(getApplicationContext(), DownloadsActivity.class);
					startActivity(i);
				} else if (id == R.id.favorites){
					i.setClass(getApplicationContext(), FavoritesActivity.class);
					startActivity(i);
				} else if (id == R.id.share){
					_drawer.closeDrawer(GravityCompat.START);
				} else if (id == R.id.rate){
					_drawer.closeDrawer(GravityCompat.START);
				}
				_drawer.closeDrawer(GravityCompat.START);
				return false; 
						
				} 
		});
		
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public class MyFragmentAdapter extends FragmentStatePagerAdapter {
		Context context;
		int tabCount;
		
		public MyFragmentAdapter(Context context, FragmentManager fm, int tabCount) {
			super(fm);
			this.context = context;
			this.tabCount = tabCount;
		}
		
		@Override
		public int getCount(){
			return tabCount;
		}
		
		@Override
		public CharSequence getPageTitle(int _position) {
			if (_position == 0) {
				return "MEME IMAGES";
			}
			if (_position == 1) {
				return "MEME VIDEOS";
			}
			if (_position == 2) {
				return "CATEGORIES";
			}
			return null;
		}
		
		@Override
		public Fragment getItem(int _position) {
			if (_position == 0) {
				return new ImagesFragmentActivity();
			}
			if (_position == 1) {
				return new VideosFragmentActivity();
			}
			if (_position == 2) {
				return new CategoriesFragmentActivity();
			}
			return null;
		}
		
	}
	
	@Override
	public void onBackPressed() {
		if (_drawer.isDrawerOpen(GravityCompat.START)) {
			_drawer.closeDrawer(GravityCompat.START);
		}
		else {
			super.onBackPressed();
		}
	}
	public void _Menu () {
		//You Cant add item 
	}
	@Override
	public boolean onCreateOptionsMenu (Menu menu){
		menu.add(0, 0, 0, "Popularity");
		menu.add(0, 1, 1, "Recently Added");
		if(bool){
			//menu.clear();
			for (int i = 0; i < menu.size(); i++)
			            menu.getItem(i).setVisible(false);
		} else {
			for (int i = 0; i < menu.size(); i++)
			            menu.getItem(i).setVisible(true);
		}
		
		return true;
	}
	//In Hare
	
	 @Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case 0:
			// your code or block
			_method1();
			break;
			case 1:
			// your code or block
			_method2();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	public void _setDrawerWidth (final double _num) {
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		_nav_view.setBackgroundColor(Color.parseColor("#FFFFFF"));
		
		androidx.drawerlayout.widget.DrawerLayout.LayoutParams params = (androidx.drawerlayout.widget.DrawerLayout.LayoutParams)_nav_view.getLayoutParams();
		
		params.width = (int)getDip((int)_num);
		
		params.height = androidx.drawerlayout.widget.DrawerLayout.LayoutParams.MATCH_PARENT;
		
		_nav_view.setLayoutParams(params);
	}
	
	
	public void _method1 () {
		
		if(viewpager1.getCurrentItem() == 0){
			ImagesFragmentActivity fragment = (ImagesFragmentActivity)getSupportFragmentManager().getFragments().get(0);
			fragment._setData("popularity");
		} else if (viewpager1.getCurrentItem()==1) {
			
			VideosFragmentActivity fragment1 = (VideosFragmentActivity)getSupportFragmentManager().getFragments().get(1);
			fragment1._setData("popularity");
		}
	}
	
	
	public void _method2 () {
		if(viewpager1.getCurrentItem() == 0){
				ImagesFragmentActivity fragment = (ImagesFragmentActivity)getSupportFragmentManager().getFragments().get(0);
				fragment._setData("recently");
		} else if (viewpager1.getCurrentItem()==1) {
				
				VideosFragmentActivity fragment1 = (VideosFragmentActivity)getSupportFragmentManager().getFragments().get(1);
				fragment1._setData("recently");
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