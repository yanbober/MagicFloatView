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

import android.animation.TypeEvaluator;
import android.graphics.Bitmap;

/**
 * An abstract Animator Evaluator define class, assigned to {@link ValueState}.
 * You can extends this class to define yourself customer flying animator,
 * When you extends this class, please add your new animator type to {@link AnimatorCreater}
 * and {@link MagicFlyLinearLayout} attrs.
 */

public abstract class AbsAnimatorEvaluator implements TypeEvaluator<ValueState> {
    private int mMeasuredWith;
    private int mMeasuredHeigh;
    private Bitmap mBitmap;

    public AbsAnimatorEvaluator(int width, int heigh, Bitmap bitmap) {
        this.mMeasuredWith = width;
        this.mMeasuredHeigh = heigh;
        this.mBitmap = bitmap;
    }

    public int getMeasuredWith() {
        return mMeasuredWith;
    }

    public int getMeasuredHeigh() {
        return mMeasuredHeigh;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public abstract ValueState createAnimatorStart();
    public abstract ValueState createAnimatorEnd();
}
