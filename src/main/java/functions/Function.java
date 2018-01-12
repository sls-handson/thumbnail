package functions;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.util.StringUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

import config.Properties;
import functions.Function.Input;
import functions.Function.Output;


public class Function implements java.util.function.Function<Input, Output> {

	private final AmazonS3 s3;
	private final Properties properties;

	public Function(AmazonS3 s3, Properties properties) {
		this.s3 = s3;
		this.properties = properties;
	}

	@Override
	public Output apply(final Input input) {

		System.out.println(input);

		final S3Object s3Object = s3.getObject(input.getS3Bucket(), input.getS3Key());
		final Thumbnail thumbnail = new Thumbnail(properties.getThumbnailBucketPrefix() + properties.getThumbnailBucketBase() + properties.getThumbnailBucketSuffix(), input.getS3Key());

		try {
			final BufferedImage source = ImageIO.read(s3Object.getObjectContent());
			final ResampleOp resampleOp = new ResampleOp(
					DimensionConstrain.createMaxDimension(properties.getMaxWidth(), properties.getMaxHeight(), true));
			resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
			resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
			final BufferedImage resized = resampleOp.filter(source, null);
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(resized, StringUtils.getFilenameExtension(s3Object.getKey()), os);

			final InputStream is = new ByteArrayInputStream(os.toByteArray());
			final ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(os.size());

			s3.putObject(thumbnail.s3Bucket, thumbnail.s3Key, is, meta);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return new Output(thumbnail);
	}
	
	public static final class Input {
		private String userId, s3Key, s3Bucket;
		private List<Map<String, Object>> parallelResult;

		public String getS3Key() {
			return s3Key;
		}

		public void setS3Key(String s3Key) {
			this.s3Key = s3Key;
		}

		public String getS3Bucket() {
			return s3Bucket;
		}

		public void setS3Bucket(String s3Bucket) {
			this.s3Bucket = s3Bucket;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public List<Map<String, Object>> getParallelResult() {
			return parallelResult;
		}

		public void setParallelResult(List<Map<String, Object>> parallelResult) {
			this.parallelResult = parallelResult;
		}

	}
	
	public static final class Output {
		private Thumbnail thumbnail;

		public Output(final Thumbnail thumbnail) {
			this.thumbnail = thumbnail;
		}

		public Thumbnail getThumbnail() {
			return thumbnail;
		}

		public void setThumbnail(Thumbnail thumbnail) {
			this.thumbnail = thumbnail;
		}
	}
	
	public static class Thumbnail {
		private String s3Key, s3Bucket;
		
		public Thumbnail(final String s3Bucket, final String s3Key) {
			this.s3Bucket = s3Bucket;
			this.s3Key = s3Key;
		}

		public String getS3Key() {
			return s3Key;
		}

		public void setS3Key(String s3Key) {
			this.s3Key = s3Key;
		}

		public String getS3Bucket() {
			return s3Bucket;
		}

		public void setS3Bucket(String s3Bucket) {
			this.s3Bucket = s3Bucket;
		}
	}
}
