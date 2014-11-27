package sdu.information.school.news.teacher;


import sdu.information.school.news.teacher.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextPaint;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class CourseDetailActivity extends Activity {
	
	private TextView[] course_detail =new TextView[16] ;
	private ImageButton img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_detail);
		
		img=(ImageButton)findViewById(R.id.course_detail_img_1);
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CourseDetailActivity.this.finish();
			}
		});
		
		course_detail[0] = (TextView)findViewById(R.id.course_detail_name);
		course_detail[1] = (TextView)findViewById(R.id.course_detail_num_1);
		course_detail[2] = (TextView)findViewById(R.id.course_detail_num_2);
		course_detail[3] = (TextView)findViewById(R.id.course_detail_loc);
		course_detail[4] = (TextView)findViewById(R.id.course_detail_week);
		course_detail[5] = (TextView)findViewById(R.id.course_detail_price);
		course_detail[6] = (TextView)findViewById(R.id.course_detail_how_time);
		course_detail[7] = (TextView)findViewById(R.id.course_detail_teacher_1);
		course_detail[8] = (TextView)findViewById(R.id.course_detail_teacher_2);
		course_detail[9] = (TextView)findViewById(R.id.course_detail_property);
		course_detail[10] = (TextView)findViewById(R.id.course_detail_exam);
		course_detail[11] = (TextView)findViewById(R.id.course_detail_how_week);
		course_detail[12] = (TextView)findViewById(R.id.course_detail_space);
		course_detail[13] = (TextView)findViewById(R.id.course_detail_school);
		course_detail[14] = (TextView)findViewById(R.id.course_detail_grade);
		course_detail[15] = (TextView)findViewById(R.id.course_detail_class);
		
		Intent intent = getIntent();
		String week = intent.getStringExtra("week");
		String num = intent.getStringExtra("num");
		
		SharedPreferences share = getSharedPreferences("course", Activity.MODE_PRIVATE);
		
		course_detail[0].setText(share.getString(week+num+"name", ""));
		course_detail[1].setText(share.getString(week+num+"num_1", ""));
		course_detail[2].setText(share.getString(week+num+"num_2", ""));
		course_detail[3].setText(share.getString(week+num+"loc", ""));
		course_detail[4].setText("ÖÜ"+" "+share.getString(week+num+"week", "")+" "+"µÚ"+" "+share.getString(week+num+"week_sub", "")+" "+"½Ú");
		course_detail[5].setText(share.getString(week+num+"price", ""));
		course_detail[6].setText(share.getString(week+num+"how_time", ""));
		course_detail[7].setText(share.getString(week+num+"teacher_1", ""));
		course_detail[8].setText(share.getString(week+num+"teacher_2", ""));
		course_detail[9].setText(share.getString(week+num+"property", ""));
		course_detail[10].setText(share.getString(week+num+"exam", ""));
		course_detail[11].setText(share.getString(week+num+"how_week", ""));
		course_detail[12].setText(share.getString(week+num+"space", ""));
		course_detail[13].setText(share.getString(week+num+"school", ""));
		course_detail[14].setText(share.getString(week+num+"grade", ""));
		course_detail[15].setText(share.getString(week+num+"class", ""));
		
		TextPaint tp = course_detail[0].getPaint();
		tp.setFakeBoldText(true);
		
		
	}



}
