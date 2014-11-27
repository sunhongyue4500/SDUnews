package sdu.information.school.news.teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;











import com.slidingmenu.lib.SlidingMenu;

import sdu.information.school.news.teacher.R;
import sdu.information.school.news.adapter.ViewPagerAdapter;
import sdu.information.school.news.instruments.DirverInformations;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageView[] button = new ImageView[8];
	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	
	private int pager_state_now=0;
	
	//时间服务
	private ScheduledExecutorService scheduledExecutorService;
	
	// 记录当前选中位置
	private int currentIndex;
	// 底部小点图片
	private ImageView[] dots;
	
	private int height_sub;
	
	private int slidingmenu_weight;
	
	private  SlidingMenu menu = null;
	
	private long exitTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menu = new SlidingMenu(this);
		setContentView(R.layout.activity_main);
		
		SharedPreferences config = getSharedPreferences("config", Activity.MODE_PRIVATE);
		Editor edit = config.edit();
		edit.putInt("versionCode", DirverInformations.versionCode(this)).commit();
		edit.putBoolean("isFirst", false).commit(); 
		
		button[0]=(ImageView)findViewById(R.id.main_button_11);
		button[1]=(ImageView)findViewById(R.id.main_button_12);
		button[2]=(ImageView)findViewById(R.id.main_button_13);
		button[3]=(ImageView)findViewById(R.id.main_button_21);
		button[4]=(ImageView)findViewById(R.id.main_button_22);
		button[5]=(ImageView)findViewById(R.id.main_button_23);
		button[6]=(ImageView)findViewById(R.id.main_button_31);
		button[7]=(ImageView)findViewById(R.id.main_button_32);
		
		for(int i=0;i<button.length;i++){
			button[i].setOnClickListener(new ButtonListener(i));
		}
		
		// 初始化页面
		
		double weight = DirverInformations.weight(this);
		Log.v("tag", "weight is "+weight);
		double height = (weight/56.0)*36.0;
		Log.v("tag", "height is"+height);
		height_sub=(int)height+1;
		Log.v("tag", "height_sub is"+height_sub);
		
		slidingmenu_weight=(int)(weight/2);
		Log.v("tag", "slidingmenu_weight is"+slidingmenu_weight);
		
		initViews();
		initDots();
		
		
		
		menu.setMode(SlidingMenu.RIGHT);//设置菜单的位置
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);//设置菜单滑动的样式
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);//菜单滑动时阴影部分
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setBehindWidth(slidingmenu_weight);//菜单宽带
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidingmenu_layout);//添加菜单
        menuinit();
        
		
     /*   
		Button testbutton=(Button)findViewById(R.id.main_slidingmenu_button);
		testbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.v("tag", "ttt");
				Toast.makeText(MainActivity.this,"点击了按钮", Toast.LENGTH_SHORT).show();
			}
		});*/
        
	}
	
	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3500, 3500,
				TimeUnit.MILLISECONDS);
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}
	
	
	public void finish() {
//		RelativeLayout layout_all=(RelativeLayout)findViewById(R.id.main_activity_all);
//		Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.exit_anim);
//		layout_all.startAnimation(animation);
		super.finish();
	    overridePendingTransition(0, R.anim.exit_anim);
	}

	class ButtonListener implements OnClickListener{
		private int i;
		public ButtonListener(int i){
			this.i=i;
		}

		@Override
		public void onClick(View v) {
			if(i==0){
				Intent intent = new Intent();  
	            intent.setClass(MainActivity.this, ListViewActivity.class);  
	            intent.putExtra("newsType", "0103");
	            startActivity(intent);
	            overridePendingTransition(R.anim.change_activity_right_in, R.anim.change_activity_right_out);
			}
			else if(i==1){
				Intent intent = new Intent();  
	            intent.setClass(MainActivity.this, ListViewActivity.class);  
	            intent.putExtra("newsType", "0104");
	            startActivity(intent);
	            overridePendingTransition(R.anim.change_activity_right_in, R.anim.change_activity_right_out);
			}
			else if(i==2){
				Intent intent = new Intent();  
	            intent.setClass(MainActivity.this, ListViewActivity.class);  
	            intent.putExtra("newsType", "0105");
	            startActivity(intent);
	            overridePendingTransition(R.anim.change_activity_right_in, R.anim.change_activity_right_out);
			}
			else if(i==3){
				Intent intent = new Intent();  
	            intent.setClass(MainActivity.this, ListViewActivity.class);  
	            intent.putExtra("newsType", "0106");
	            startActivity(intent);
	            overridePendingTransition(R.anim.change_activity_right_in, R.anim.change_activity_right_out);
			}
			else if(i==4){
				Intent intent = new Intent();  
	            intent.setClass(MainActivity.this, ListViewActivity.class);  
	            intent.putExtra("newsType", "0107");
	            startActivity(intent);
	            overridePendingTransition(R.anim.change_activity_right_in, R.anim.change_activity_right_out);
			}
			else if(i==5){
				Intent intent = new Intent();  
	            intent.setClass(MainActivity.this, ListViewActivity.class);  
	            intent.putExtra("newsType", "0108");
	            startActivity(intent);
	            overridePendingTransition(R.anim.change_activity_right_in, R.anim.change_activity_right_out);
			}
			else if(i==6){
				Intent intent = new Intent();  
	            intent.setClass(MainActivity.this, ListViewActivity.class);  
	            intent.putExtra("newsType", "0102");
	            startActivity(intent);
	            overridePendingTransition(R.anim.change_activity_right_in, R.anim.change_activity_right_out);
			}
			else if(i==7){
				menu.toggle();
			}
			else if(i==21){
				if(menu.isShown()){
					menu.toggle();
				}
				Intent intent = new Intent();  
	            intent.setClass(MainActivity.this, AboutActivity.class); 
	            startActivity(intent);
	            overridePendingTransition(R.anim.change_activity_right_in, R.anim.change_activity_right_out);
			}
		}
	}
	
	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		// 初始化引导图片列表
		views.add(inflater.inflate(R.layout.viewpager_1, null));
		views.add(inflater.inflate(R.layout.viewpager_2, null));
		views.add(inflater.inflate(R.layout.viewpager_3, null));
		views.add(inflater.inflate(R.layout.viewpager_4, null));
		views.add(inflater.inflate(R.layout.viewpager_5, null));

		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views, this);
		
		vp = (ViewPager) findViewById(R.id.main_viewpager);
		
		FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) vp.getLayoutParams();
		linearParams.height=height_sub;
		vp.setLayoutParams(linearParams);
		
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(new MyPageChangeListener());
	}
	
	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.main_viewpager_dots);

		dots = new ImageView[views.size()];

		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}
	
	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}
	
	
	class MyPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			pager_state_now=vp.getCurrentItem();
			setCurrentDot(arg0);
		}
		
	}
	
	//自动滚动
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (vp) {
				// System.out.println("currentItem: " + currentItem);
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			vp.setCurrentItem(DirverInformations.theNextPager(pager_state_now));// 切换当前显示的图片
		};
	};
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(!menu.isMenuShowing()){
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){ 
   	
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } 
	        else {
	            finish();
	            overridePendingTransition(0, R.anim.exit_anim);
	            //System.exit(0);
	            
	        }
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
		}
		else{
			menu.toggle();
			return false;
		}
	}
	
	private void menuinit(){
		
		
		TextView[] tex = new TextView[3];
		
		tex[0]=(TextView)findViewById(R.id.sliding_menu_tex_1);
		
		tex[0].setOnClickListener(new ButtonListener(21));
	}
	
}
