package teamup.rivile.com.teamup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewWithArabicDigits extends android.support.v7.widget.AppCompatTextView {
    public TextViewWithArabicDigits(Context context) {
        super(context);
    }

    public TextViewWithArabicDigits(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(replaceArabicNumbers(text), type);
    }

    private String replaceArabicNumbers(CharSequence original) {
        if (original != null) {
            return original.toString()
                    .replaceAll("١","1")
                    .replaceAll("٢","2")
                    .replaceAll("٣","3")
                    .replaceAll("٤","4")
                    .replaceAll("٥","5")
                    .replaceAll("٦","6")
                    .replaceAll("٧","7")
                    .replaceAll("٨","8")
                    .replaceAll("٩","9")
                    ;
        }

        return null;
    }
}
