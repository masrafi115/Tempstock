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
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import com.startapp.sdk.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class DownloadsActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String check = "";
	private HashMap<String, Object> hmap = new HashMap<>();
	private double n = 0;
	private String mFilePath = "";
	private  int ACTIVITY_CODE;
	
	private ArrayList<HashMap<String, Object>> Downloads = new ArrayList<>();
	private ArrayList<String> files = new ArrayList<>();
	
	private LinearLayout linear1;
	private GridView gridview1;
	
	private SharedPreferences downloads;
	private Intent i = new Intent();
	private AlertDialog.Builder d;
	private TimerTask timer;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.downloads);
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
		downloads = getSharedPreferences("downloads", Activity.MODE_PRIVATE);
		d = new AlertDialog.Builder(this);
	}
	
	private void initializeLogic() {
		setTitle("My Downloads");
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
		
		_toolbar.setTitleTextColor(Color.parseColor("#505050"));
		gridview1.setNumColumns(3); 
		gridview1.setColumnWidth(GridView.AUTO_FIT); 
		gridview1.setStretchMode(GridView.STRETCH_COLUMN_WIDTH); 
		gridview1.setVerticalScrollBarEnabled(false); 
		mFilePath = FileUtil.getExternalStorageDir().concat("/Tempstock/");
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						FileUtil.writeFile(mFilePath.concat(".appdata".concat(".txt")), "1");
					}
				});
			}
		};
		_timer.schedule(timer, (int)(100));
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						check = FileUtil.readFile(mFilePath.concat(".appdata".concat(".txt")));
					}
				});
			}
		};
		_timer.schedule(timer, (int)(200));
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (!check.equals("1")) {
							d.setTitle("Permission Request");
							d.setMessage("Tempstock App uses Permission to Upload And Download Data's on your storage");
							d.setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									
								}
							});
							d.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									
								}
							});
							d.create().show();
						}
					}
				});
			}
		};
		_timer.schedule(timer, (int)(1000));
		FileUtil.listDir(mFilePath, files);
		for(int _repeat25 = 0; _repeat25 < (int)(files.size()); _repeat25++) {
			if (files.get((int)(n)).endsWith(".jpg") || (files.get((int)(n)).endsWith(".jpeg") || (files.get((int)(n)).endsWith(".png") || (files.get((int)(n)).endsWith(".3gp") || files.get((int)(n)).endsWith(".mp4"))))) {
				hmap = new HashMap<>();
				hmap.put("path", files.get((int)(n)));
				Downloads.add(hmap);
			}
			n++;
		}
		_getData();
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
		gridview1.setAdapter(new Gridview1Adapter(Downloads));
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
			
			if (Downloads.get((int)_position).get("path").toString().endsWith(".jpg") || (Downloads.get((int)_position).get("path").toString().endsWith(".jpeg") || Downloads.get((int)_position).get("path").toString().endsWith(".png"))) {
				imageview1.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(Downloads.get((int)_position).get("path").toString(), 1024, 1024));
				cardview2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						i.setAction(Intent.ACTION_VIEW);
						java.io.File f = new java.io.File(Downloads.get((int)_position).get("path").toString()); 
						if (f.isFile()) { 
								MediaScannerConnection.scanFile(DownloadsActivity.this, new String[] { 
										f.toString() 
								}, null, new MediaScannerConnection.OnScanCompletedListener() {
										@Override public void onScanCompleted(String path, Uri uri) {
												Intent intent = new Intent(Intent.ACTION_VIEW); 
												intent.setDataAndType(uri, "image/*"); 
												DownloadsActivity.this.startActivityForResult(intent, DownloadsActivity.this.ACTIVITY_CODE); 
										}
										
								});}
					}
				});
				imageview2.setVisibility(View.GONE);
			}
			else {
				if (Downloads.get((int)_position).get("path").toString().endsWith(".mp4") || Downloads.get((int)_position).get("path").toString().endsWith(".3gp")) {
					Bitmap thumb = ThumbnailUtils.createVideoThumbnail(Downloads.get((int)_position).get("path").toString(),android.provider.MediaStore.Images.Thumbnails.MINI_KIND);
					imageview1.setImageBitmap(thumb);
					cardview2.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
							i.setAction(Intent.ACTION_VIEW);
							java.io.File f = new java.io.File(Downloads.get((int)_position).get("path").toString()); 
							if (f.isFile()) { 
									MediaScannerConnection.scanFile(DownloadsActivity.this, new String[] { 
											f.toString() 
									}, null, new MediaScannerConnection.OnScanCompletedListener() {
											@Override public void onScanCompleted(String path, Uri uri) {
													Intent intent = new Intent(Intent.ACTION_VIEW); 
													intent.setDataAndType(uri, "video/*"); 
													DownloadsActivity.this.startActivityForResult(intent, DownloadsActivity.this.ACTIVITY_CODE); 
											}
											
									});}
						}
					});
					imageview2.setVisibility(View.VISIBLE);
				}
			}
			
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