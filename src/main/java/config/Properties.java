package config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public class Properties {
	private String region, thumbnailBucketBase,thumbnailBucketPrefix, thumbnailBucketSuffix;
	private int maxWidth,maxHeight;
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public String getThumbnailBucketBase() {
		return thumbnailBucketBase;
	}

	public void setThumbnailBucketBase(String thumbnailBucketBase) {
		this.thumbnailBucketBase = thumbnailBucketBase;
	}

	public String getThumbnailBucketPrefix() {
		return thumbnailBucketPrefix;
	}

	public void setThumbnailBucketPrefix(String thumbnailBucketPrefix) {
		this.thumbnailBucketPrefix = thumbnailBucketPrefix;
	}

	public String getThumbnailBucketSuffix() {
		return thumbnailBucketSuffix;
	}

	public void setThumbnailBucketSuffix(String thumbnailBucketSuffix) {
		this.thumbnailBucketSuffix = thumbnailBucketSuffix;
	}

}
