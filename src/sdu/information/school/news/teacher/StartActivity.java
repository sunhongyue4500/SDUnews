package sdu.information.school.news.teacher;

import java.util.Timer;
import java.util.TimerTask;



import sdu.information.school.news.teacher.R;
import sdu.information.school.news.instruments.DirverInformations;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class StartActivity extends Activity {
	private Handler handler;
	private final double whatImage1=1.7777;
	private final double whatImage2 =1.6666;
	private final double whatImage3 =1.5;
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		final View view = View.inflate(this, R.layout.activity_start, null);
		setContentView(R.layout.activity_start);
		
		RelativeLayout layout_all=(RelativeLayout)findViewById(R.id.activity_start_layout_all);
		Animation animation = AnimationUtils.loadAnimation(StartActivity.this, R.anim.start_anim);
		layout_all.startAnimation(animation);
		
		img=(ImageView)findViewById(R.id.start_activity_img);
		/*
		Animation animation = AnimationUtils.loadAnimation(StartActivity.this, R.anim.start_anim);
		view.startAnimation(animation);
		*/
		/*
		ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(3000);
		view.startAnimation(anim);
		*/
		
		double height = DirverInformations.height(this);
		double weight = DirverInformations.weight(this);
		
		double isImage = height/weight;
		
		Log.v("tag", "height"+height);
		Log.v("tag", "weight"+weight);
		Log.v("tag", "isImage"+isImage);
		
		if(((whatImage1-0.05)<isImage)&&(isImage<(whatImage1+0.05))){
			img.setImageResource(R.drawable.welcome960_540);
			Log.v("tag", "1");
		}
		else if(((whatImage2-0.05)<isImage)&&(isImage<(whatImage2+0.05))){
			img.setImageResource(R.drawable.welcome800_480);
			Log.v("tag", "2");
		}
		else if(((whatImage3-0.08)<isImage)&&(isImage<(whatImage3+0.08))){
			img.setImageResource(R.drawable.welcome960_640);
			Log.v("tag", "3");
		}
		else{
			img.setImageResource(R.drawable.welcome800_480);
			Log.v("tag", "4");
		}
		img.setVisibility(View.VISIBLE);
		
		HandlerThread myThread = new HandlerThread("myHandlerThread");  
        myThread.start();  
        handler = new Handler(){  
            @Override  
            public void handleMessage(Message msg) {  
                if(msg.what == 0){  
//                  Log.i("tag", Thread.currentThread().getName());  
                    Intent intent = new Intent(StartActivity.this, MainActivity.class); 
                    startActivity(intent);
                    overridePendingTransition(R.anim.change_activity_enter_anim, R.anim.change_activity_exit_anim);
                    finish();
                }  
            }  
              
        };  
        tt.run();  
    }
	
	TimerTask tt = new TimerTask() {  
        @Override  
        public void run() {  
//          Log.i("tag", Thread.currentThread().getName());  
            handler.sendMessageDelayed(handler.obtainMessage(0), 3000);  
        }  
    }; 

		

	
	
	
	
	

}
