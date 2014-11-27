package sdu.information.school.news.instruments;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.view.Display;

public class DirverInformations {
	
	public static double weight(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) < 13 ) {
			return (display.getWidth());
		}
		else{
			Point size = new Point();
			display.getSize(size);
			return (size.x);
		}
		
	}
	
	public static double height(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) < 13 ) {
			return (display.getHeight());
		}
		else{
			Point size = new Point();
			display.getSize(size);
			return (size.y);
		}
		
	}
	
	public static int theNextPager(int i){
		if(i==4){
			return 0;
		}
		else{
			return i=i+1;
		}
	}
	
	public static int versionCode(Context context){
		PackageInfo info=null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info.versionCode;
	}
	
}
