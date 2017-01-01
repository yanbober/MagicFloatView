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
import android.graphics.PointF;
import java.util.Random;

/**
 * Animator from View's top to bottom, like rain.
 */

public class T2BRainEvaluator extends B2TScatterEvaluator {
    private PointF pointF1, pointF2;

    public T2BRainEvaluator(int width, int heigh, Bitmap bitmap) {
        super(width, heigh, bitmap);
        int realH = getMeasuredHeigh() - getBitmap().getHeight();
        pointF1 = getBezierP01PointF(0, realH / 2);
        pointF2 = getBezierP01PointF(realH / 2, realH);
    }

    private PointF getBezierP01PointF(int min, int max) {

        PointF pointF = new PointF();
        pointF.x = new Random().nextInt(getMeasuredWith() - getBitmap().getWidth());
        pointF.y = randomRange(min, max);
        return pointF;
    }

    private int randomRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    @Override
    public ValueState evaluate(float fraction, ValueState startValue, ValueState endValue) {
        float timeStart = 1.0f - fraction;

        ValueState valueState = new ValueState();
        PointF point = new PointF();
        point.x = timeStart * timeStart * timeStart * (startValue.pointF.x) + 3
                * timeStart * timeStart * fraction * (pointF1.x) + 3 * timeStart
                * fraction * fraction * (pointF2.x) + fraction * fraction * fraction * (endValue.pointF.x);

        point.y = timeStart * timeStart * timeStart * (startValue.pointF.y) + 3
                * timeStart * timeStart * fraction * (pointF1.y) + 3 * timeStart
                * fraction * fraction * (pointF2.y) + fraction * fraction * fraction * (endValue.pointF.y);
        valueState.pointF = point;
        valueState.scale = 1;
        valueState.alpha = 255;
        valueState.bitmap = getBitmap();
        return valueState;
    }

    @Override
    public ValueState createAnimatorStart() {
        ValueState valueState = new ValueState();
        valueState.bitmap = getBitmap();
        valueState.alpha = 255;
        valueState.scale = 1f;
        valueState.pointF = new PointF(new Random().nextInt(getMeasuredWith()), -getBitmap().getHeight());
        return valueState;
    }

    @Override
    public ValueState createAnimatorEnd() {
        ValueState valueState = new ValueState();
        valueState.bitmap = getBitmap();
        valueState.alpha = 255;
        valueState.scale = 1f;
        valueState.pointF = new PointF(new Random().nextInt(getMeasuredWith()), getMeasuredHeigh());
        return valueState;
    }
}
