package com.wiser.ceiling;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CeilingRecycleView extends RecyclerView{

	private int						headerId;

	private int						dividerId;

	private int						dividerColor;

	public CeilingRecycleView(@NonNull Context context) {
		super(context);
		init(context, null);
	}

	public CeilingRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CeilingRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CeilingRecycleView);
		headerId = ta.getResourceId(R.styleable.CeilingRecycleView_crv_headerId, -1);
		dividerId = ta.getResourceId(R.styleable.CeilingRecycleView_crv_divider_height, 0);
		dividerColor = ta.getResourceId(R.styleable.CeilingRecycleView_crv_divider_color,0);
		ta.recycle();

	}

	public interface OnGroupView {

		View groupView();
	}
}
