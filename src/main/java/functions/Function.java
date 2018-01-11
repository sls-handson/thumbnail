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


public class Function implements java.util.function.Function<Input, Boolean> {

	private final AmazonS3 s3;
	private final Properties properties;

	public Function(AmazonS3 s3, Properties properties) {
		this.s3 = s3;
		this.properties = properties;
	}

	@Override
	public Boolean apply(final Input request) {

		System.out.println(request);

		S3Object s3Object = s3.getObject(request.getS3Bucket(), request.getS3Key());

		try {
			BufferedImage source = ImageIO.read(s3Object.getObjectContent());
			ResampleOp resampleOp = new ResampleOp(
					DimensionConstrain.createMaxDimension(properties.getMaxWidth(), properties.getMaxHeight(), true));
			resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
			resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
			BufferedImage resized = resampleOp.filter(source, null);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(resized, StringUtils.getFilenameExtension(s3Object.getKey()), os);

			InputStream is = new ByteArrayInputStream(os.toByteArray());
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(os.size());

			s3.putObject(properties.getS3BucketPrefix() + properties.getThumbnailBucket(), s3Object.getKey(), is, meta);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return Boolean.TRUE;
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
}
