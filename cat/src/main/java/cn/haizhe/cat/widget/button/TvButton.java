package cn.haizhe.cat.widget.button;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CheckedTextView;

import androidx.core.content.res.ResourcesCompat;

import com.blankj.utilcode.util.AdaptScreenUtils;

import cn.haizhe.cat.R;

public class TvButton extends CheckedTextView {

    public TvButton(Context context) {
        this(context, null);
    }

    public TvButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setBackgroundResource(R.drawable.btn_bg_card1);
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setTextAlignment(TEXT_ALIGNMENT_GRAVITY);
        setGravity(Gravity.CENTER);
        setSingleLine(true);
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setTextColor(ResourcesCompat.getColorStateList(getResources(), R.color.btn_text_color, null));
        int padding = AdaptScreenUtils.pt2Px(30);
        setPadding(padding, 0, padding, 0);
    }

}
