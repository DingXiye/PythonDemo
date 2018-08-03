/**
 * Copyright (c) 2013,
 * Tomasz Choma, Olgierd Grodzki, Łukasz Potępa, Monika Rakoczy, Paweł Synowiec, Łukasz Szarkowicz
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package forecasting;

import org.jfree.data.time.TimeSeries;

/**
 * 遗传算法的观察者界面允许观察事件:
 * 
 * <ul>
 *     <li></li>
 *     <li></li>
 *     <li>更新遗传算法迭代结束时的最佳解决方案（带预测的时间序列）遗传算法的结束（解决方案）</li>
 * </ul>
 */
public interface GAObserver {

    /**
     * 每次迭代的算法将这些数据“推”给观察者。
     *
     * @param fitness 此迭代中最佳个体的适应度函数的值
     * @param best 这次迭代中最佳个体的基因
     * @param i 迭代次数
     */
    public void update(double fitness, double[] best, int i);

    /**
     * 在遗传算法操作结束后调用的方法，根据方法初始化/调用，传输包含预测与否的时间序列。
     *
     * @param timeSeries Szereg czasowy
     */
    public void done(TimeSeries timeSeries);

    /**
     * 在遗传算法的操作结束之后调用的方法以依赖于预测方法的形式将解决方案作为双表传递给问题。
     * @param best 染色体是最好的个体，作为双重板
     */
    public void done(double[] best);
}
