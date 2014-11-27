package sdu.information.school.news.teacher;



import sdu.information.school.news.teacher.R;
import sdu.information.school.news.instruments.DirverInformations;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CourseActivity extends Activity {
	
	private TextView[][] course = new TextView[8][8];
	private TextView tex_import;
	private ImageButton img;
	private TextView title;
	public static CourseActivity instance  = null;
	public static boolean  isMainExist  = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);
		
		isMainExist  = true;
		instance = this;
		
		tex_import=(TextView)findViewById(R.id.course_activity_tex_import1);
		img=(ImageButton)findViewById(R.id.course_activity_img_1);
		title=(TextView)findViewById(R.id.course_activity_all_title);
				
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		course[0][0] = (TextView)findViewById(R.id.c0_0);
		course[0][1] = (TextView)findViewById(R.id.c0_1);
		course[0][2] = (TextView)findViewById(R.id.c0_2);
		course[0][3] = (TextView)findViewById(R.id.c0_3);
		course[0][4] = (TextView)findViewById(R.id.c0_4);
		course[0][5] = (TextView)findViewById(R.id.c0_5);
		course[0][6] = (TextView)findViewById(R.id.c0_6);
		course[0][7] = (TextView)findViewById(R.id.c0_7);
		
		course[1][0] = (TextView)findViewById(R.id.c1_0);
		course[1][1] = (TextView)findViewById(R.id.c1_1);
		course[1][2] = (TextView)findViewById(R.id.c1_2);
		course[1][3] = (TextView)findViewById(R.id.c1_3);
		course[1][4] = (TextView)findViewById(R.id.c1_4);
		course[1][5] = (TextView)findViewById(R.id.c1_5);
		course[1][6] = (TextView)findViewById(R.id.c1_6);
		course[1][7] = (TextView)findViewById(R.id.c1_7);
		
		course[2][0] = (TextView)findViewById(R.id.c2_0);
		course[2][1] = (TextView)findViewById(R.id.c2_1);
		course[2][2] = (TextView)findViewById(R.id.c2_2);
		course[2][3] = (TextView)findViewById(R.id.c2_3);
		course[2][4] = (TextView)findViewById(R.id.c2_4);
		course[2][5] = (TextView)findViewById(R.id.c2_5);
		course[2][6] = (TextView)findViewById(R.id.c2_6);
		course[2][7] = (TextView)findViewById(R.id.c2_7);
		
		course[3][0] = (TextView)findViewById(R.id.c3_0);
		course[3][1] = (TextView)findViewById(R.id.c3_1);
		course[3][2] = (TextView)findViewById(R.id.c3_2);
		course[3][3] = (TextView)findViewById(R.id.c3_3);
		course[3][4] = (TextView)findViewById(R.id.c3_4);
		course[3][5] = (TextView)findViewById(R.id.c3_5);
		course[3][6] = (TextView)findViewById(R.id.c3_6);
		course[3][7] = (TextView)findViewById(R.id.c3_7);
		
		course[4][0] = (TextView)findViewById(R.id.c4_0);
		course[4][1] = (TextView)findViewById(R.id.c4_1);
		course[4][2] = (TextView)findViewById(R.id.c4_2);
		course[4][3] = (TextView)findViewById(R.id.c4_3);
		course[4][4] = (TextView)findViewById(R.id.c4_4);
		course[4][5] = (TextView)findViewById(R.id.c4_5);
		course[4][6] = (TextView)findViewById(R.id.c4_6);
		course[4][7] = (TextView)findViewById(R.id.c4_7);
		
		course[5][0] = (TextView)findViewById(R.id.c5_0);
		course[5][1] = (TextView)findViewById(R.id.c5_1);
		course[5][2] = (TextView)findViewById(R.id.c5_2);
		course[5][3] = (TextView)findViewById(R.id.c5_3);
		course[5][4] = (TextView)findViewById(R.id.c5_4);
		course[5][5] = (TextView)findViewById(R.id.c5_5);
		course[5][6] = (TextView)findViewById(R.id.c5_6);
		course[5][7] = (TextView)findViewById(R.id.c5_7);
		
		course[6][0] = (TextView)findViewById(R.id.c6_0);
		course[6][1] = (TextView)findViewById(R.id.c6_1);
		course[6][2] = (TextView)findViewById(R.id.c6_2);
		course[6][3] = (TextView)findViewById(R.id.c6_3);
		course[6][4] = (TextView)findViewById(R.id.c6_4);
		course[6][5] = (TextView)findViewById(R.id.c6_5);
		course[6][6] = (TextView)findViewById(R.id.c6_6);
		course[6][7] = (TextView)findViewById(R.id.c6_7);
		
		course[7][0] = (TextView)findViewById(R.id.c7_0);
		course[7][1] = (TextView)findViewById(R.id.c7_1);
		course[7][2] = (TextView)findViewById(R.id.c7_2);
		course[7][3] = (TextView)findViewById(R.id.c7_3);
		course[7][4] = (TextView)findViewById(R.id.c7_4);
		course[7][5] = (TextView)findViewById(R.id.c7_5);
		course[7][6] = (TextView)findViewById(R.id.c7_6);
		course[7][7] = (TextView)findViewById(R.id.c7_7);
		
		SharedPreferences share = getSharedPreferences("course", Activity.MODE_PRIVATE);
		double width;
		width=DirverInformations.weight(this);
		
		for(int i=0;i<=7;i++){
			for(int j=0;j<=7;j++){
				if(i!=0){
					if(j!=0){
						course[i][j].setWidth((int)(width/8));
						if(share.contains(i+""+j+"name")){
							if(share.getString(i+""+j+"property", "").equals("必修")){
								double price = Double.parseDouble(share.getString(i+""+j+"price", ""));
								if(price>=3){
									course[i][j].setBackgroundColor(Color.parseColor("#ff1769"));
									course[i][j].setTextColor(Color.parseColor("#ffffff"));
								}
								else{
									course[i][j].setBackgroundColor(Color.parseColor("#00a1f1"));
									course[i][j].setTextColor(Color.parseColor("#ffffff"));
								}
							}
							else if(share.getString(i+""+j+"property", "").equals("限选")){
								course[i][j].setBackgroundColor(Color.parseColor("#ffbb00"));
								course[i][j].setTextColor(Color.parseColor("#ffffff"));
							}
							else if(share.getString(i+""+j+"property", "").equals("任选")){
								course[i][j].setBackgroundColor(Color.parseColor("#7cbb00"));
								course[i][j].setTextColor(Color.parseColor("#ffffff"));
							}
							else{
								course[i][j].setBackgroundColor(Color.parseColor("#00a1f1"));
								course[i][j].setTextColor(Color.parseColor("#ffffff"));
							}
							course[i][j].setText(share.getString(i+""+j+"name", ""));
							course[i][j].setOnClickListener(new Course_detail(i,j));
							
						}
					}
					else if(j==0){
						course[i][j].setWidth((int)(width/8));
					}
				}
				else if(i==0){
					course[i][j].setWidth((int)(width/22));
				}
				
			}			
		}
		
		tex_import.setOnTouchListener(new MyOnTouchListener());
		
		if(share.getInt("isExist", 0)!=1){
			Dialog dialog = new AlertDialog.Builder(CourseActivity.this)
				.setTitle("没有导入记录")
				.setMessage("检测到您还没有进行过导入，是否现在进行导入？")
				.setPositiveButton("导入", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();  
			            intent.setClass(CourseActivity.this, ImportCourseActivity.class);  
			            startActivity(intent); 
					}
				})
				.setNegativeButton("下次吧", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).create();
			dialog.show();
		}
		title.setText("课程表");
		
	}
	
	public void onDestroy(){
		super.onDestroy();
		isMainExist=false;
	}
	
	class Course_detail implements OnClickListener{
		private int week;
		private int num;
		public Course_detail(int week,int num){
			this.week=week;
			this.num =num;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();  
            intent.setClass(CourseActivity.this, CourseDetailActivity.class);  
            intent.putExtra("week", ""+week);
            intent.putExtra("num", ""+num);
            startActivity(intent);
            
		}
		
	}
	
	class MyOnTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch (event.getAction()) { 	  
	        case MotionEvent.ACTION_DOWN:	//按下
	        	tex_import.setBackgroundColor(Color.parseColor("#023e6b"));
	            break;
	        case MotionEvent.ACTION_UP://抬起
	        	tex_import.setBackgroundColor(Color.parseColor("#006699"));
	        	Intent intent = new Intent();  
	            intent.setClass(CourseActivity.this, ImportCourseActivity.class);  
	            startActivity(intent);
	            break;
	        default:
	            break;
	  }
			
			return true;
		}
		
	}

}
