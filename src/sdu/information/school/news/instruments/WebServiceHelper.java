package sdu.information.school.news.instruments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import sdu.information.school.news.model.NewsListInfo;

public class WebServiceHelper {
	
	// WSDL�ĵ��е������ռ�
	private static final String targetNameSpace = "http://android.googlepages.com/";
	
	// WSDL�ĵ��е�URL
	private static final String WSDL = "http://202.194.20.94:8180/hysoftServer/services/MyService";
	
	private static final String getnews = "getnews";
	
	private static final String getContexttext = "getContexttext";
	
	private SQLiteDatabase   db=null;
	//��ȡ�����б�ĺ���(�ֱ��� newsId title time)
	public List<NewsListInfo> getnews(String typeId,int start,Context context) {
		List<NewsListInfo> news = new ArrayList<NewsListInfo>();
		SoapObject request = new SoapObject(targetNameSpace, getnews);
		
		request.addProperty("typeId", typeId);
		request.addProperty("start", start+"");
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		try {
			envelope.bodyOut = request;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			envelope.encodingStyle = "UTF-8";

			HttpTransportSE ht = new HttpTransportSE(WSDL);
			ht.debug = true;
			ht.call(targetNameSpace + getnews, envelope);

			SoapObject soapObject = (SoapObject) envelope.getResponse();
			// �����ȡ���Ǹ����ϣ��Ͷ�����������Ĳ���
			
			SQLiteOpenHelper helper=new LoadedSQLHelper(context);
			db    =helper.getWritableDatabase();
			db.delete("loaded", "newstype=?", new String[]{typeId});
			
			if (soapObject.getName() == "anyType") {
				// ����Web Service��õļ���
				for (int i = 0; i < soapObject.getPropertyCount(); i++) {
					NewsListInfo task = new NewsListInfo();
					// ��ȡ����������
					SoapObject soapChilds = (SoapObject) soapObject
							.getProperty(i);
					// �Ե��������ݽ����ٴα�����������ÿ�����ݶ�ȡ����
					
					String NewsId = soapChilds.getProperty("newsId").toString();
					String Title  = soapChilds.getProperty("title").toString();
					String Time   = soapChilds.getProperty("time").toString();
					String Link   = soapChilds.getProperty("titleLink").toString();
					
					
					ContentValues cv = new ContentValues();
					cv.put("newsid", NewsId);
					cv.put("newstitle", Title);
					cv.put("newstime", Time);
					cv.put("newstype", typeId);
					db.insert("loaded", null, cv);
//					db.close();
					
					task.setNewsId(NewsId);
					task.setTitle(Title);
					task.setTime(Time);
					task.setTitleLink(Link);
					news.add(task);
					
					
					
					System.out.println("success" + task.getTitle());
				}
			}
			db.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return news;
	}

	//��ȡ�������ݵĺ���
	public String getContexttext(String Id,Context context) {
		String result = null;
		SoapObject request = new SoapObject(targetNameSpace, getContexttext);
		request.addProperty("Id", Id);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		try {
			envelope.bodyOut = request;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			envelope.encodingStyle = "UTF-8";
			HttpTransportSE ht = new HttpTransportSE(WSDL);
			ht.debug = true;
			ht.call(targetNameSpace + getContexttext, envelope);
			if (envelope.getResponse() != null) {
				SoapObject soapObject = (SoapObject) envelope.getResponse();
				SoapPrimitive Primitive = (SoapPrimitive) soapObject
						.getProperty("content");
				if (Primitive != null) {
					result = Primitive.toString();
					
					SQLiteOpenHelper helper=new LoadedSQLHelper(context);
					db    =helper.getWritableDatabase();
					String whereClause = "newsid=?";
					String whereArgs[]=new String[]{Id};
					ContentValues cv = new ContentValues();
					cv.put("newscontent", result);
					db.update("loaded", cv, whereClause, whereArgs);
					db.close();
				} else {
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		return result;
	}
}
