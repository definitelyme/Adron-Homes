package dev.onekode.adronhomes;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.annotations.NotNull;

public interface RootInterface {
    String TAG = "log-tag";

    String MAINTENANCE_TEXT = "Whoops! This is a demo version.";

    default void sendToast(Activity context, String msg) {
        Toast.makeText(context, msg != null && !TextUtils.isEmpty(msg) ? msg : "No Message", Toast.LENGTH_LONG).show();
    }

    default void sendSnackbar(View rootView, String msg) {
        sendSnackbar(rootView, msg, null);
    }

    default void sendSnackbar(View rootView, String msg, String actionMsg) {
        Snackbar sn = Snackbar.make(rootView,
                msg != null && !TextUtils.isEmpty(msg) ? msg : "No Message",
                BaseTransientBottomBar.LENGTH_LONG);
        sn.setAction(actionMsg != null && !TextUtils.isEmpty(actionMsg) ? actionMsg : "Okay", v -> sn.dismiss());
        sn.setActionTextColor(Color.WHITE);
        sn.show();
    }

    default void sendResponse(String msg) {
        sendResponse(null, msg, null);
    }

    default void sendResponse(@Nullable Activity context, String msg) {
        sendResponse(context, msg, null);
    }

    default void sendResponse(String msg, Exception ex) {
        sendResponse(null, msg, ex);
    }

    default void sendResponse(@Nullable Activity context, String msg, @Nullable Exception ex) {
        Log.i(TAG, "sendResponse: " + msg, ex);
        if (context != null)
            Toast.makeText(context, msg != null && !TextUtils.isEmpty(msg) ? msg : "No Message", Toast.LENGTH_LONG).show();
    }

    default void sendFullResponse(@NonNull Context ctx, @NonNull View rootView, String msg) {
        sendFullResponse(ctx, rootView, msg, null);
    }

    default void sendFullResponse(@Nullable Context ctx, @Nullable View rootView, String msg, @Nullable Exception ex) {
        if (ctx != null)
            Toast.makeText(ctx, msg != null && !TextUtils.isEmpty(msg) ? msg : "No Message", Toast.LENGTH_LONG).show();
        if (rootView != null) {
            Snackbar sn = Snackbar.make(rootView,
                    msg != null && !TextUtils.isEmpty(msg) ? msg : "No Message",
                    BaseTransientBottomBar.LENGTH_LONG);
            sn.setAction(msg != null && !TextUtils.isEmpty(msg) ? msg : "Okay", v -> sn.dismiss());
            sn.setActionTextColor(Color.WHITE);
            sn.show();
        }
        if (ex != null)
            Log.i(TAG, "sendResponse: " + msg, ex);
    }

    default void setEnabled(@NotNull View v, boolean enabled) {
        v.setEnabled(enabled);
    }

    default void setEnabled(boolean enabled, @NotNull View... views) {
        for (View v : views) {
            v.setEnabled(enabled);
        }
    }

    default void setVisibility(@NotNull View v, boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE; // If "visible = true", visibility = VISIBLE, else = GONE
        v.setVisibility(visibility);
    }

    default void setVisibility(boolean visible, @NotNull View... views) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        for (View v : views) {
            v.setVisibility(visibility);
        }
    }

    default AppCompatActivity getActivityFromContext(Context context) {
        if (context == null)
            return null;
        else if (context instanceof Activity)
            return (AppCompatActivity) context;
        else if (context instanceof ContextWrapper)
            return getActivityFromContext(((ContextWrapper) context).getBaseContext());

        return null;
    }

    enum PROPERTY_TYPE {
        FLAT("flat", 0),
        ROOM("room", 1),
        HOUSE("house", 2);

        private String property;
        private int ordinal;

        PROPERTY_TYPE(String property, int ordinal) {
            this.property = property;
            this.ordinal = ordinal;
        }

        @NonNull
        @Override
        public String toString() {
            return property;
        }

        public int getOrdinal() {
            return ordinal;
        }
    }

    enum TENURE_TYPE {
        LEASE("lease", 0),
        SALE("sale", 1),
        RENT("rent", 2);

        private String name;
        private int ordinal;

        TENURE_TYPE(String name, int ordinal) {
            this.name = name;
            this.ordinal = ordinal;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }

        public int getOrdinal() {
            return ordinal;
        }
    }
}
