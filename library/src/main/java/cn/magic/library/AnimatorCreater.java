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

import android.graphics.Bitmap;

/**
 * When customer add a new animator type assigned {@link AbsAnimatorEvaluator},
 * here need to be add a new type.
 */

public class AnimatorCreater {
    public static final int TYPE_B2T_SCATTER = 0;
    public static final int TYPE_T2B_RAIN_NORMAL = 1;

    public static AbsAnimatorEvaluator create(int animType, int width, int heigh, Bitmap bitmap) {
        AbsAnimatorEvaluator evaluator = null;
        switch (animType) {
            case TYPE_B2T_SCATTER:
                evaluator = new B2TScatterEvaluator(width, heigh, bitmap);
                break;
            case TYPE_T2B_RAIN_NORMAL:
                evaluator = new T2BRainEvaluator(width, heigh, bitmap);
                break;
            default:
                evaluator = new B2TScatterEvaluator(width, heigh, bitmap);
                break;
        }
        return evaluator;
    }
}
