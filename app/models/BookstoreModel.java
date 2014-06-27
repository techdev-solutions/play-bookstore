package models;

import play.db.ebean.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author <a href="mailto:alexander.hanschke@techdev.de">Alexander Hanschke</a>
 */
@MappedSuperclass
public abstract class BookstoreModel extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Date created;

    protected BookstoreModel() {
        this.created = new Date();
    }

    public Date getCreated() {
        if (created == null) {
            return null;
        }

        return new Date(created.getTime());
    }

}
