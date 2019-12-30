package com.wiser.ceiling;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * @author Wiser
 * <p>
 * NestedScrollView吸顶布局
 */
public class CeilingScrollLayout extends NestedScrollView implements NestedScrollView.OnScrollChangeListener {

    private int ceilingLayoutId, oCeilingLayoutId;

    private View ceilingView, oCeilingView;

    private int ceilingHeight;

    private boolean isCeilingRunning;

    public CeilingScrollLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CeilingScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CeilingScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CeilingScrollLayout);
        if (attrs != null) {
            ceilingLayoutId = ta.getResourceId(R.styleable.CeilingScrollLayout_csl_layoutId, -1);
            oCeilingLayoutId = ta.getResourceId(R.styleable.CeilingScrollLayout_csl_oLayoutId, -1);
        }
        ta.recycle();

        setOnScrollChangeListener(this);
    }

    public boolean isCeilingRunning() {
        return isCeilingRunning;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (ceilingView != null && oCeilingView != null) {
            if (scrollY >= ceilingHeight) {
                isCeilingRunning = true;
                if (oCeilingView.getVisibility() == View.VISIBLE)
                    oCeilingView.setVisibility(View.INVISIBLE);
                if (ceilingView.getVisibility() == View.INVISIBLE)
                    ceilingView.setVisibility(View.VISIBLE);
            } else {
                isCeilingRunning = false;
                if (oCeilingView.getVisibility() == View.INVISIBLE)
                    oCeilingView.setVisibility(View.VISIBLE);
                if (ceilingView.getVisibility() == VISIBLE)
                    ceilingView.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            if (oCeilingLayoutId != -1) oCeilingView = findViewById(oCeilingLayoutId);
            if (ceilingLayoutId != -1 && getRootView() != null)
                ceilingView = getRootView().findViewById(ceilingLayoutId);
            if (oCeilingView != null) {
                ceilingHeight = oCeilingView.getTop();
            }
        }
    }
}
