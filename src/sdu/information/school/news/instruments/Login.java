package sdu.information.school.news.instruments;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Login {
	private Elements e;
	private Element e1;
	
	
	public  Login(Elements e){
		this.e = e;
	}
	
	public  Login(Element e1){
		this.e1 = e1;
	}
	
	public int getnum(int i){
		int tem=0;
		Elements j = e.select("tr");
		Elements k = j.get(i).select("td");
		String l=k.get(7).text().replace(Jsoup.parse("&nbsp").text(), "").replace("-", "");
		try{
			tem = Integer.parseInt(l);
			tem = tem%10;
		}
		catch (NumberFormatException e) {
			tem=20;
		}
		return tem;
	}
	
	public int getweek(int i){
		int tem=0;
		Elements j = e.select("tr");
		Elements k = j.get(i).select("td");
		String l=k.get(7).text().replace(Jsoup.parse("&nbsp").text(), "").replace("-", "");
		try{
			tem = Integer.parseInt(l);
			tem = tem/10;
		}
		catch (NumberFormatException e) {
			tem=20;
		}
		return tem;
		
	}
	
	public int trs(){
		Elements trs = e.select("tr");
		return trs.size();
	}
	
	public int trs1(){
		Elements trs = e1.select("tr");
		return trs.size();
	}
	
	public String jousp(int a,int b){
		Elements trs = e.select("tr");
		Elements tds = trs.get(a).select("td");
		return tds.get(b).text().replace(Jsoup.parse("&nbsp;").text(), "");
	}
	
	public String jousp_term(int a,int b){
		Elements trs = e.select("tr");
		Elements tds = trs.get(a).select("td");
		String test=tds.get(b).text().replace(Jsoup.parse("&nbsp;").text(), "");
		if(test.equals("")){
			return "Î´³ö";
		}
		else{
			return test;
		}
	}
	
	public String jousp1(int a,int b){
		Elements trs = e1.select("TR");
		Elements tds = trs.get(a).select("TD");
		return tds.get(b).text().replace(Jsoup.parse("&nbsp;").text(), "");
	}
	
	
}
