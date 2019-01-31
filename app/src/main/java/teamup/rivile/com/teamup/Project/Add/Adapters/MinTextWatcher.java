package teamup.rivile.com.teamup.Project.Add.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import teamup.rivile.com.teamup.R;

public class MinTextWatcher implements TextWatcher {
    private EditText mEditText;
    private RangeSeekBar mRangeSeekBar;
    private int mMinVal;
    private final String MIN_ERROR_MESSAGE;
    private final Context mContext;

    public MinTextWatcher(@NonNull EditText editText, int minVal, RangeSeekBar rangeSeekBar) {
        this.mEditText = editText;
        this.mMinVal = minVal;
        this.mRangeSeekBar = rangeSeekBar;

        mContext = editText.getContext();
        MIN_ERROR_MESSAGE = mContext.getString(R.string.min_val_error) + String.valueOf(mMinVal) + ".";
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String val = s.toString();
        if (val.isEmpty()) val = String.valueOf(mMinVal);
        if (Integer.valueOf(val) >= mMinVal) {
            mRangeSeekBar.setSelectedMinValue(Integer.valueOf(val));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        String val = s.toString();
        if (val.isEmpty()) val = String.valueOf(mMinVal);
        if (Integer.valueOf(val) < mMinVal) {
            Toast.makeText(mContext, MIN_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
            mRangeSeekBar.setSelectedMinValue(mMinVal);
            changeTextToMin();
        }
    }

    private void changeTextToMin() {
        mEditText.removeTextChangedListener(this);
        mEditText.setText(String.valueOf(mMinVal));
        mEditText.addTextChangedListener(this);
    }
}