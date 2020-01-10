package dev.onekode.adronhomes.Models;

import androidx.annotation.NonNull;

import dev.onekode.adronhomes.Foundation.Pluralizer;
import dev.onekode.adronhomes.Foundation.Slugify;
import dev.onekode.adronhomes.RootInterface;

public abstract class Model implements RootInterface {

    public static String getNode(@NonNull Class<? extends Model> child) {
        return Pluralizer.build(
                new Slugify.Builder().input(child.getSimpleName().toLowerCase()).seperator("-").make()
        );
    }
}
