package net.watoud.demo.http.download.douyin.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import net.watoud.demo.http.download.douyin.model.UserShareVediosQueryResponse.Aweme;

public class MusicShareVediosQueryResponse {
	@JSONField(name = "has_more")
	private int hasMore;
	@JSONField(name = "cursor")
	private int cursor;
	@JSONField(name = "aweme_list")
	List<Aweme> awemeList;

	public int getHasMore() {
		return hasMore;
	}

	public void setHasMore(int hasMore) {
		this.hasMore = hasMore;
	}

	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	public List<Aweme> getAwemeList() {
		return awemeList;
	}

	public void setAwemeList(List<Aweme> awemeList) {
		this.awemeList = awemeList;
	}

}
