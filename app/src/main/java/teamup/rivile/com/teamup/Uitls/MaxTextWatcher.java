package teamup.rivile.com.teamup.Uitls;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import teamup.rivile.com.teamup.R;

public class MaxTextWatcher implements TextWatcher {
    private EditText mEditText;
    private RangeSeekBar mRangeSeekBar;
    private int mMaxVal;
    private final String MAX_ERROR_MESSAGE;
    private final Context mContext;

    public MaxTextWatcher(EditText editText, int maxVal, RangeSeekBar rangeSeekBar) {
        this.mEditText = editText;
        this.mRangeSeekBar = rangeSeekBar;
        this.mMaxVal = maxVal;

        mContext = editText.getContext();
        MAX_ERROR_MESSAGE = mContext.getString(R.string.max_error_val) + String.valueOf(mMaxVal) + ".";
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String val = s.toString();
        if (val.isEmpty()) val = String.valueOf(mMaxVal);
        if (Integer.valueOf(val) <= mMaxVal) {
            mRangeSeekBar.setSelectedMaxValue(Integer.valueOf(val));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        String val = s.toString();
        if (val.isEmpty()) val = String.valueOf(mMaxVal);
        if (Integer.valueOf(val) > mMaxVal) {
            Toast.makeText(mContext, MAX_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
            mRangeSeekBar.setSelectedMaxValue(mMaxVal);
            changeTextToMax();
        }
    }

    private void changeTextToMax() {
        mEditText.removeTextChangedListener(this);
        mEditText.setText(String.valueOf(mMaxVal));
        mEditText.addTextChangedListener(this);
    }
}

