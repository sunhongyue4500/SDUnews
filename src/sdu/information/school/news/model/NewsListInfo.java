package sdu.information.school.news.model;

public class NewsListInfo {
	private String newsId;
	private String title;
	private String content;
	private String time;
	private String imagesurl;
	private String TitleLink;
	
	public String getTitleLink() {
		return TitleLink;
	}
	
	public void setTitleLink(String TitleLink) {
		this.TitleLink = TitleLink;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImagesurl() {
		return imagesurl;
	}

	public void setImagesurl(String imagesurl) {
		this.imagesurl = imagesurl;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
}
