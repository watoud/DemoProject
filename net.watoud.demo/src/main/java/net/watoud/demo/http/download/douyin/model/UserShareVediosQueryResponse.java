package net.watoud.demo.http.download.douyin.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class UserShareVediosQueryResponse {
	@JSONField(name = "has_more")
	private int hasMore;
	@JSONField(name = "max_cursor")
	private long maxCursor;
	@JSONField(name = "aweme_list")
	List<Aweme> awemeList;

	public long getMaxCursor() {
		return maxCursor;
	}

	public void setMaxCursor(long maxCursor) {
		this.maxCursor = maxCursor;
	}
	public List<Aweme> getAwemeList() {
		return awemeList;
	}

	public void setAwemeList(List<Aweme> awemeList) {
		this.awemeList = awemeList;
	}

	public int getHasMore() {
		return hasMore;
	}

	public void setHasMore(int hasMore) {
		this.hasMore = hasMore;
	}

	public static class Aweme {
		@JSONField(name = "aweme_id")
		private String id;
		@JSONField(name = "desc")
		private String desc;
		@JSONField(name = "share_info")
		private ShareInfo shareInfo;
		@JSONField(name = "statistics")
		private Statistic statistics;
		@JSONField(name = "video")
		private VedioInfo vedioInfo;

		public VedioInfo getVedioInfo() {
			return vedioInfo;
		}

		public void setVedioInfo(VedioInfo vedioInfo) {
			this.vedioInfo = vedioInfo;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public ShareInfo getShareInfo() {
			return shareInfo;
		}

		public void setShareInfo(ShareInfo shareInfo) {
			this.shareInfo = shareInfo;
		}

		public Statistic getStatistics() {
			return statistics;
		}

		public void setStatistics(Statistic statistics) {
			this.statistics = statistics;
		}
	}

	public static class ShareInfo {
		@JSONField(name = "share_url")
		private String shareUrl;

		public String getShareUrl() {
			return shareUrl;
		}

		public void setShareUrl(String shareUrl) {
			this.shareUrl = shareUrl;
		}
	}

	public static class VedioInfo {
		@JSONField(name = "play_addr")
		private PlayAddr playAddr;

		public PlayAddr getPlayAddr() {
			return playAddr;
		}

		public void setPlayAddr(PlayAddr playAddr) {
			this.playAddr = playAddr;
		}

		public static class PlayAddr {
			private String uri;

			public String getUri() {
				return uri;
			}

			public void setUri(String uri) {
				this.uri = uri;
			}

		}

	}

	public static class Statistic {
		@JSONField(name = "comment_count")
		private String commentCount;
		@JSONField(name = "digg_count")
		private String diggCount;
		@JSONField(name = "play_count")
		private String playCount;
		@JSONField(name = "share_count")
		private String shareCount;

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("diggCount=").append(diggCount).append(", playCount=").append(playCount)
					.append(", shareCount=").append(shareCount);
			return builder.toString();
		}

		public String getCommentCount() {
			return commentCount;
		}

		public void setCommentCount(String commentCount) {
			this.commentCount = commentCount;
		}

		public String getDiggCount() {
			return diggCount;
		}

		public void setDiggCount(String diggCount) {
			this.diggCount = diggCount;
		}

		public String getPlayCount() {
			return playCount;
		}

		public void setPlayCount(String playCount) {
			this.playCount = playCount;
		}

		public String getShareCount() {
			return shareCount;
		}

		public void setShareCount(String shareCount) {
			this.shareCount = shareCount;
		}
	}


}
