package teamup.rivile.com.teamup.ui.Project;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamup.rivile.com.teamup.R;

public class ShareDialogFragment extends BottomSheetDialogFragment {
    public static final String FACEBOOK_PACKAGE = "com.facebook.katana";
    public static final String FACEBOOK_LITE_PACKAGE = "com.facebook.lite";
    public static final String TWITTER_PACKAGE = "com.twitter.android";
    public static final String TWITTER_LITE_PACKAGE = "com.twitter.android.lite";
    public static final String WHATSAPP_PACKAGE = "com.whatsapp";
    public static final String WHATSAPP_BUSINESS_PACKAGE = "com.whatsapp.w4b";

    public static boolean isFacebookAvailable = false;
    public static boolean isFacebookLiteAvailable = false;
    public static boolean isTwitterAvailable = false;
    public static boolean isTwitterLiteAvailable = false;
    public static boolean isWhatsAppAvailable = false;
    public static boolean isWhatsAppBusinessAvailable = false;

    private Helper mHelper;

    public static ShareDialogFragment getInstance(Helper helper) {
        ShareDialogFragment fragment = new ShareDialogFragment();
        fragment.setHelper(helper);

        return fragment;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFacebookAvailable = isAppInstalled(FACEBOOK_PACKAGE);
        if (!isFacebookAvailable)
            isFacebookLiteAvailable = isAppInstalled(FACEBOOK_LITE_PACKAGE);

        isTwitterAvailable = isAppInstalled(TWITTER_PACKAGE);
        if (!isTwitterAvailable)
            isTwitterLiteAvailable = isAppInstalled(TWITTER_LITE_PACKAGE);

        isWhatsAppAvailable = isAppInstalled(WHATSAPP_PACKAGE);
        if (!isTwitterAvailable)
            isWhatsAppBusinessAvailable = isAppInstalled(WHATSAPP_BUSINESS_PACKAGE);

        return inflater.inflate(R.layout.fragment_bottomsheet_share, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConstraintLayout facebook = view.findViewById(R.id.cl_facebook);
        if (isFacebookAvailable || isFacebookLiteAvailable)
            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHelper.onClick(R.id.cl_facebook);
                    ShareDialogFragment.this.dismiss();
                }
            });
        else facebook.setVisibility(View.GONE);

        ConstraintLayout twitter = view.findViewById(R.id.cl_twitter);
        if (isTwitterAvailable || isTwitterLiteAvailable)
            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHelper.onClick(R.id.cl_twitter);
                    ShareDialogFragment.this.dismiss();
                }
            });
        else twitter.setVisibility(View.GONE);

        ConstraintLayout whatsapp = view.findViewById(R.id.cl_whatsapp);
        if (isWhatsAppAvailable || isWhatsAppBusinessAvailable)
            whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHelper.onClick(R.id.cl_whatsapp);
                    ShareDialogFragment.this.dismiss();
                }
            });
        else whatsapp.setVisibility(View.GONE);

        if (!isFacebookAvailable &&
                !isTwitterAvailable &&
                !isWhatsAppAvailable &&
                !isFacebookLiteAvailable &&
                !isTwitterLiteAvailable &&
                !isWhatsAppBusinessAvailable)
            view.findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
    }

    private boolean isAppInstalled(String uri) {
        PackageManager pm = getContext().getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException ignored) {
            return false;
        }

        return app_installed;
    }

    public void setHelper(Helper mHelper) {
        this.mHelper = mHelper;
    }

    public interface Helper {
        void onClick(int viewId);
    }
}
