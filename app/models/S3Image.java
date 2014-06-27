package models;

import support.Arguments;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.File;
import java.util.UUID;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
@Entity
public class S3Image extends BookstoreModel {

    public String key;
    public String bucket;

    @Transient
    public File file;

    public S3Image(File file, String bucket) {
        this.file = Arguments.requireNonNull(file, "Image file must not be null.");
        this.bucket = bucket;
        this.key = UUID.randomUUID().toString() + ".jpg";
    }

    public String getURI() {
        return "https://s3.amazonaws.com/" + bucket + "/" + key;
    }

}
