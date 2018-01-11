package config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public class Properties {
	private String region,s3BucketPrefix, thumbnailBucket;
	private int maxWidth,maxHeight;
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getS3BucketPrefix() {
		return s3BucketPrefix;
	}

	public void setS3BucketPrefix(String s3BucketPrefix) {
		this.s3BucketPrefix = s3BucketPrefix;
	}

	public String getThumbnailBucket() {
		return thumbnailBucket;
	}

	public void setThumbnailBucket(String thumbnailBucket) {
		this.thumbnailBucket = thumbnailBucket;
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

}
