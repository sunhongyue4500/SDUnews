package sdu.information.school.news.instruments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class LoadedSQLHelper extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	public LoadedSQLHelper(Context context,String name,CursorFactory factory, int version){
		super(context,name,factory,version);
	}
	public LoadedSQLHelper(Context context){
		this(context,"loaded",null,VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="CREATE TABLE  loaded"
					+"("
					+"id					INTEGER			PRIMARY KEY,"
					+"newstype              VARCHAR(20)     NULL,"
					+"newsid                VARCHAR(50)     NULL,"
					+"newstitle             VARCHAR(100)    NULL,"
					+"newstime              VARCHAR(20)     NULL,"
					+"newscontent           VARCHAR(3900)   NULL"
					+")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
