package dev.onekode.adronhomes.Models;

import com.google.firebase.firestore.Exclude;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Locale;

import dev.onekode.adronhomes.Foundation.Pluralizer;
import dev.onekode.adronhomes.Foundation.Slugify;
import dev.onekode.adronhomes.RootInterface;

public abstract class Model implements RootInterface {
    /**
     * Node for this model Class<? extends Model>
     *
     * @var string
     */
    @Exclude
    private static String node = Pluralizer.build(
//            new Slugify.Builder().input(getChildClass().getSimpleName().toLowerCase()).seperator("-").make()
            new Slugify.Builder().input("hello-world").seperator("-").make()
    );
    /**
     * Total number of models in the node
     *
     * @var string
     */
    @Exclude
    private long count;
    /**
     * When the model was created
     *
     * @var string
     */
    private long created_at = System.currentTimeMillis();
    /**
     * When the model was updated
     *
     * @var string
     */
    private long updated_at = System.currentTimeMillis();
    /**
     * When the model was deleted
     *
     * @var string
     */
    private long deleted_at;

    public static String getNode(Class<? extends Model> clazz) {
        return Pluralizer.build(
                new Slugify.Builder().input(clazz.getSimpleName().toLowerCase()).seperator("-").make()
        );
    }

    public String created_at() {
        return DateFormatUtils.format(getCreated_at(), "dd MMM, yyyy", Locale.getDefault());
    }

    public String updated_at() {
        return DateFormatUtils.format(getUpdated_at(), "dd MMM, yyyy", Locale.getDefault());
    }

    public String deleted_at() {
        return DateFormatUtils.format(getDeleted_at(), "dd MMM, yyyy", Locale.getDefault());
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(long updated_at) {
        this.updated_at = updated_at;
    }

    public long getDeleted_at() {
        return deleted_at;
    }

    @Exclude
    public long count() {
        return count;
    }
}
