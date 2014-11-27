package sdu.information.school.news.teacher;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AboutActivity extends Activity {
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		img=(ImageView)findViewById(R.id.about_img_1);
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.change_activity_left_in, R.anim.change_activity_left_out);
			}
		});
	}
	public void finish(){
		super.finish();
		overridePendingTransition(R.anim.change_activity_left_in, R.anim.change_activity_left_out);
	}
}
