package cn.haizhe.cat.widget.text;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class AutoMarqueeTextView extends TextView {

    public AutoMarqueeTextView(Context context) {
        this(context, null);
    }

    public AutoMarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoMarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected void initView() {
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
        setFocusable(false);
        setFocusableInTouchMode(false);
        setClickable(false);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

}
