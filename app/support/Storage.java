package support;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import models.S3Image;
import org.imgscalr.Scalr;
import play.Configuration;
import play.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
public class Storage {

    public static final String PROP_AWS_S3_BUCKET  = "aws.s3.bucket";
    public static final String PROP_AWS_ACCESS_KEY = "aws.access.key";
    public static final String PROP_AWS_SECRET_KEY = "aws.secret.key";
    public static final String PROP_AWS_MOCK_MODE  = "aws.mock";

    public static boolean MOCK_MODE = false;

    public static String bucket;

    public static void initialize(Configuration config) {
        bucket = config.getString(PROP_AWS_S3_BUCKET);

        String accessKey = config.getString(PROP_AWS_ACCESS_KEY);
        String secretKey = config.getString(PROP_AWS_SECRET_KEY);

        MOCK_MODE = config.getBoolean(PROP_AWS_MOCK_MODE);
        if (!MOCK_MODE) {
            connect(accessKey, secretKey);
        }
    }

    private static AmazonS3 s3;

    private static void connect(String accessKey, String secretKey) {
        Arguments.requireNotEmpty(accessKey, "S3 access key is missing!");
        Arguments.requireNotEmpty(secretKey, "S3 secret key is missing!");

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3 = new AmazonS3Client(credentials);

        if (!s3.doesBucketExist(bucket)) {
            s3.createBucket(bucket);
        }
    }

    public static void store(S3Image image) throws IOException {
        if (!MOCK_MODE) {
            BufferedImage source = ImageIO.read(image.file);
            BufferedImage thumbnail = Scalr.resize(source, 180);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, "jpg", output);
            InputStream input = new ByteArrayInputStream(output.toByteArray());
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType("image/jpg");

            PutObjectRequest request = new PutObjectRequest(image.bucket, image.key, input, meta);
            request.withCannedAcl(CannedAccessControlList.PublicRead);
            s3.putObject(request);
        } else {
            Logger.debug("stored image " + image.getURI());
        }
    }

    public static void remove(S3Image image) {
        if (!MOCK_MODE) {
            s3.deleteObject(image.bucket, image.key);
        } else {
            Logger.debug("removed image " + image.getURI());
        }
    }
}
