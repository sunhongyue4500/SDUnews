package sdu.information.school.news.adapter;

import java.util.List;

import sdu.information.school.news.teacher.R;
import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class ViewPagerAdapter extends PagerAdapter {
	private List<View> views;
	private Activity activity;

	public ViewPagerAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}
	
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		
		((ViewPager) arg0).removeView((View) arg2);
		arg0=null;
	//	((ViewPager) arg0).removeView(views.get(arg1));
	}
	
	@Override
	public Object instantiateItem(View arg0, int arg1) {
		
		((ViewPager) arg0).addView(views.get(arg1), 0);
		
		if(arg1==0){
			ImageView img=(ImageView)arg0.findViewById(R.id.main_viewpager_1);
			img.setImageResource(R.drawable.view_pager_1);
		}
		else if(arg1==1){
			ImageView img=(ImageView)arg0.findViewById(R.id.main_viewpager_2);
			img.setImageResource(R.drawable.view_pager_2);
		}
		else if(arg1==2){
			ImageView img=(ImageView)arg0.findViewById(R.id.main_viewpager_3);
			img.setImageResource(R.drawable.view_pager_3);
		}
		else if(arg1==3){
			ImageView img=(ImageView)arg0.findViewById(R.id.main_viewpager_4);
			img.setImageResource(R.drawable.view_pager_4);
		}
		else if(arg1==4){
			ImageView img=(ImageView)arg0.findViewById(R.id.main_viewpager_5);
			img.setImageResource(R.drawable.view_pager_5);
		}
		
		
		return views.get(arg1);
	}
	
	@Override
	public void finishUpdate(View arg0) {
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0 == arg1);
	}
	
	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}
	
	@Override
	public Parcelable saveState() {
		return null;
	}
	
	@Override
	public void startUpdate(View arg0) {
	}
	
}
