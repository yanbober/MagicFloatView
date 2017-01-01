/**
 * MIT License
 *
 * Copyright (c) 2016 yanbo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package cn.magic.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A magic flying view, support various type flying anim and customer anim.
 */

public class MagicFlyLinearLayout extends LinearLayout {
    private SparseArray<ValueState> mSparseArray = new SparseArray<>();
    private List<Bitmap> mBitmapList = new ArrayList<>();

    private int mMeasureH, mMeasureW;
    private Rect mSrcRect, mDestRect;
    private Paint mPaint;

    private int mAnimatorType;
    private int mFlyDuration;

    public MagicFlyLinearLayout(Context context) {
        this(context, null);
    }

    public MagicFlyLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagicFlyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MagicFlyLinearLayout);
        mAnimatorType = array.getInt(R.styleable.MagicFlyLinearLayout_flyAnimatorType, AnimatorCreater.TYPE_B2T_SCATTER);
        mFlyDuration = array.getInt(R.styleable.MagicFlyLinearLayout_flyDuration, 4000);
        array.recycle();

        mSrcRect = new Rect();
        mDestRect = new Rect();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void clearDrawable() {
        mBitmapList.clear();
        postInvalidate();
    }

    public void addDrawable(Bitmap bitmap) {
        mBitmapList.add(bitmap);
    }

    public void addDrawable(int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), resId);
        mBitmapList.add(bitmap);
    }

    public void flying() {
        PlayRunnable runnable = new PlayRunnable();
        if (mMeasureH > 0 || mMeasureW > 0) {
            runnable.run();
        } else {
            this.post(runnable);
        }
    }

    private class PlayRunnable implements Runnable {
        @Override
        public void run() {
            int randomIndex = new Random().nextInt(mBitmapList.size());
            AbsAnimatorEvaluator evaluator = AnimatorCreater.create(mAnimatorType, mMeasureW, mMeasureH, mBitmapList.get(randomIndex));
            ValueAnimator animator = ValueAnimator.ofObject(evaluator, evaluator.createAnimatorStart(), evaluator.createAnimatorEnd());
            animator.setDuration(mFlyDuration);
            animator.setInterpolator(new AccelerateInterpolator());
            MagicFlyLinearLayout.MagicAnimatorListener listener = new MagicFlyLinearLayout.MagicAnimatorListener();
            animator.addUpdateListener(listener);
            animator.addListener(new MagicFlyLinearLayout.MagicListener(listener.hashCode()));
            animator.start();
        }
    }

    private class MagicAnimatorListener implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mSparseArray.put(this.hashCode(), (ValueState) animation.getAnimatedValue());
            postInvalidate();
        }
    }

    private class MagicListener extends AnimatorListenerAdapter {
        private int key;

        public MagicListener(int key) {
            this.key = key;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mSparseArray.remove(key);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            mSparseArray.remove(key);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasureH = this.getMeasuredHeight();
        mMeasureW = this.getMeasuredWidth();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        for (int index = 0; index< mSparseArray.size(); index++) {
            ValueState valueState = mSparseArray.valueAt(index);
            if (valueState != null) {
                mSrcRect.left = 0;
                mSrcRect.top = 0;
                mSrcRect.right = valueState.bitmap.getWidth();
                mSrcRect.bottom = valueState.bitmap.getHeight();

                mDestRect.left = (int) valueState.pointF.x;
                mDestRect.top = (int) valueState.pointF.y;
                mDestRect.right = mDestRect.left + (int) (valueState.scale * valueState.bitmap.getWidth());
                mDestRect.bottom = mDestRect.top + (int) (valueState.scale * valueState.bitmap.getHeight());
                mPaint.setAlpha(valueState.alpha);
                canvas.drawBitmap(valueState.bitmap, mSrcRect, mDestRect, mPaint);
            }
        }
    }
}
