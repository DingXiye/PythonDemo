/**
 * Copyright (c) 2013
 * Tomasz Choma, Olgierd Grodzki, 艁ukasz Pot臋pa, Monika Rakoczy, Pawe艂 Synowiec, 艁ukasz Szarkowicz
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package action;

import gui.TSAFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Reset
 * 该类实现ActionListener接口
 */
public class ResetChartsAndParameters implements ActionListener {
	
	TSAFrame window;
	
	public ResetChartsAndParameters(TSAFrame window) {
		this.window = window;
	}

	/**
	 * 事件处理方法，负责清除图形并将算法参数重置为默认值
	 */
	public void actionPerformed(ActionEvent arg0) {
		window.getTimeWindowField().setText(null);
		window.getPopulSizeField().setText("100");
		window.getIterNumberField().setText("1000");
		window.getPeriodOfPredField().setText(null);
		window.getRdBtnRoulette().setSelected(true);
		window.getRdbtnLinearCombination().setSelected(true);
		window.getSliderSelekcji().setValue(40);
		window.getSliderKrzyzowania().setValue(40);
		window.getSliderMutacji().setValue(20);
		window.getSliderKrzyzowania().setValue(40);
		window.getSliderSelekcji().setValue(40);
		window.getSliderProbOfCross().setValue(50);
		window.getSliderProbOfMutat().setValue(50);
		
		window.getTimeSeriesChartDataIn().clear();
		window.getTimeSeriesChartWithForecast().clear();
		window.getFitnessChart().clear();
		
	}

}
