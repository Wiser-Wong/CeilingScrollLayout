package com.wiser.ceiling;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Wiser
 * 
 *         吸顶RecyclerView ItemDecoration
 */
public class CeilingItemDecoration extends RecyclerView.ItemDecoration {

	private static final String	TAG			= "CeilingItemDecoration";

	private Context				context;

	private int					groupDividerHeight;						// 分组分割线高度

	private int					itemDividerHeight;						// 分组内item分割线高度

	private int					dividerColor;							// 分割线颜色

	private int					dividerGroupColor;						// 分割线颜色

	private Paint				dividerPaint;							// 绘制分割线画笔

	private Paint				textPaint;								// 绘制文字画笔

	private int					textPaddingLeft;

	private int					textPaddingTop;

	private int					textSize;

	private int					textColor;

	private Paint				groupDividerPaint;

	private Rect				textRect	= new Rect();

	public CeilingItemDecoration(Context context, int itemDividerHeight, int dividerColor, int dividerGroupColor, int textColor, int textPaddingLeft, int textPaddingTop, int textSize,
			OnGroupListener listener) {
		this.context = context;
		this.listener = listener;
		this.itemDividerHeight = dp2Px(itemDividerHeight);
		this.dividerColor = dividerColor;
		this.textColor = textColor;
		this.dividerGroupColor = dividerGroupColor;
		this.textSize = sp2Px(textSize);
		this.textPaddingLeft = dp2Px(textPaddingLeft);
		this.textPaddingTop = dp2Px(textPaddingTop);
		initPaint();
	}

	public CeilingItemDecoration(Context context, OnGroupListener listener) {
		this.context = context;
		this.listener = listener;
		groupDividerHeight = dp2Px(24);
		this.itemDividerHeight = dp2Px(1);
		this.dividerColor = Color.GRAY;
		this.textColor = Color.WHITE;
		this.dividerGroupColor = Color.RED;
		this.textSize = sp2Px(14);
		initPaint();
	}

	private void initPaint() {
		dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		dividerPaint.setColor(dividerColor);
		dividerPaint.setStyle(Paint.Style.FILL);

		groupDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		groupDividerPaint.setColor(dividerGroupColor);
		groupDividerPaint.setStyle(Paint.Style.FILL);

		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);

	}

	@Override public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		int position = parent.getChildAdapterPosition(view);
		// 获取组名
		String groupName = getGroupName(position);
		if (groupName == null) {
			return;
		}
		if (position == 0 || isGroupFirst(position)) {
			textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
			groupDividerHeight = textSize + textPaddingTop * 2;
			outRect.top = groupDividerHeight;
		} else {
			outRect.top = dp2Px(itemDividerHeight);
		}
	}

	@Override public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
		super.onDraw(canvas, parent, state);
		// getChildCount() 获取的是当前屏幕可见 item 数量，而不是 RecyclerView 所有的 item 数量
		int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = parent.getChildAt(i);
			// 获取当前itemview在adapter中的索引
			int childAdapterPosition = parent.getChildAdapterPosition(childView);
			// 由于分割线是绘制在每一个 itemview 的顶部，所以分割线矩形 rect.bottom = itemview.top, rect.top
			// =itemview.top - groupDividerHeight
			int bottom = childView.getTop();
			int left = parent.getPaddingLeft();
			int right = parent.getPaddingRight();
			if (isGroupFirst(childAdapterPosition)) { // 是分组第一个，则绘制分组分割线
				int top = bottom - groupDividerHeight;
				Log.d(TAG, "onDraw: top = " + top + ",bottom = " + bottom);
				// 绘制分组分割线矩形
				canvas.drawRect(left, top, childView.getWidth() - right, bottom, groupDividerPaint);
				// 绘制分组分割线中的文字
				float baseLine = (top + bottom) / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
				canvas.drawText(getGroupName(childAdapterPosition), left + textPaddingLeft, baseLine, textPaint);
			} else { // 不是分组中第一个，则绘制常规分割线
				int top = bottom - itemDividerHeight;
				canvas.drawRect(left, top, childView.getWidth() - right, bottom, dividerPaint);
			}
		}
	}

	@Override public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
		super.onDrawOver(canvas, parent, state);
		View firstVisibleView = parent.getChildAt(0);
		int firstVisiblePosition = parent.getChildAdapterPosition(firstVisibleView);
		String groupName = getGroupName(firstVisiblePosition);
		int left = parent.getPaddingLeft();
		int right = firstVisibleView.getWidth() - parent.getPaddingRight();
		// 第一个itemview(firstVisibleView) 的 bottom 值小于分割线高度，分割线随着 recyclerview 滚动，
		// 分割线top固定不变，bottom=firstVisibleView.bottom
		if (firstVisibleView.getBottom() <= groupDividerHeight && isGroupFirst(firstVisiblePosition + 1)) {
			canvas.drawRect(left, 0, right, firstVisibleView.getBottom(), groupDividerPaint);
			float baseLine = firstVisibleView.getBottom() / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
			canvas.drawText(groupName, left + textPaddingLeft, baseLine, textPaint);
		} else {
			canvas.drawRect(left, 0, right, groupDividerHeight, groupDividerPaint);
			float baseLine = groupDividerHeight / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
			canvas.drawText(groupName, left + textPaddingLeft, baseLine, textPaint);
		}
	}

	private OnGroupListener listener;

	public interface OnGroupListener {

		// 获取分组中第一个文字
		String getGroupName(int position);
	}

	public String getGroupName(int position) {
		if (listener != null) {
			return listener.getGroupName(position);
		}
		return null;
	}

	/**
	 * 是否是某组中第一个item
	 *
	 * @param position
	 * @return
	 */
	private boolean isGroupFirst(int position) {
		// 第一个 itemView 肯定是新的一个分组
		if (position == 0) {
			return true;
		} else {
			String preGroupName = getGroupName(position - 1);
			String groupName = getGroupName(position);
			return !TextUtils.equals(preGroupName, groupName);
		}
	}

	private int dp2Px(int dpValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
	}

	private int sp2Px(int spValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
	}
}
