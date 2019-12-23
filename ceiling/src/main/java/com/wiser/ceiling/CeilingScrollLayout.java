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
 *
 *         吸顶布局
 */
public class CeilingScrollLayout extends NestedScrollView implements NestedScrollView.OnScrollChangeListener {

	private int		ceilingLayoutId, oCeilingLayoutId;

	private View	ceilingView, oCeilingView;

	private int		ceilingHeight;

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
			ceilingLayoutId = ta.getResourceId(R.styleable.CeilingScrollLayout_cl_layoutId, -1);
			oCeilingLayoutId = ta.getResourceId(R.styleable.CeilingScrollLayout_cl_oLayoutId, -1);
		}
		ta.recycle();

		setOnScrollChangeListener(this);
	}

	@Override public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
		if (ceilingView != null && oCeilingView != null) {
			if (scrollY >= ceilingHeight) {
				if (ceilingView.getVisibility() == View.VISIBLE) ceilingView.setVisibility(View.INVISIBLE);
				if (oCeilingView.getVisibility() == View.INVISIBLE) oCeilingView.setVisibility(View.VISIBLE);
			} else {
				if (ceilingView.getVisibility() == View.INVISIBLE) ceilingView.setVisibility(View.VISIBLE);
				if (oCeilingView.getVisibility() == VISIBLE) oCeilingView.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		if (hasWindowFocus) {
			if (ceilingLayoutId != -1) ceilingView = findViewById(ceilingLayoutId);
			if (oCeilingLayoutId != -1 && getRootView() != null) oCeilingView = getRootView().findViewById(oCeilingLayoutId);
			if (ceilingView != null) {
				ceilingHeight = ceilingView.getTop();
			}
		}
	}
}
