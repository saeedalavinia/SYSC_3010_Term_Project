package server.view;

import java.awt.Color;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;

import server.model.UpdateQueues;

public class ThermometerBarChart {
	private static DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
	private static ThermometerBarChart tbc = null;
	JFreeChart chart;

	private ThermometerBarChart() {
		
		
		chart= ChartFactory.createBarChart3D("",
				"Current Temperature", "Centigrade", categoryDataset, PlotOrientation.VERTICAL,
				true, true, false);
		ChartFrame chartFrame = new ChartFrame("Thermometer", chart);
		chartFrame.setVisible(true);
		chartFrame.setSize(300, 500);
		
		
		
		
		// cutomize plot
		CategoryPlot categoryplot = (CategoryPlot)chart.getPlot();
		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setMaximumBarWidth(0.7);
		renderer.setPaint(ChartColor.LIGHT_BLUE);
		renderer.setBase(-10);
		renderer.setBaseItemLabelPaint(Color.orange);
		categoryplot.setRenderer(renderer);
		
		
		
		// customize number axis
		 NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();
		 numberaxis.setUpperMargin(60);
		 numberaxis.setRange(-10, 50);

		Thread updater = new Thread(new ThermometerBarChart.UpdateChart());
		updater.start();
	}

	public static ThermometerBarChart getInstance() {
		if (tbc == null)
			tbc = new ThermometerBarChart();

		return tbc;

	}

	public void updateChart(Integer data) {
		this.getCategoryDataset().setValue(data, "", "Temp");
		chart.fireChartChanged();
		


	}

	public DefaultCategoryDataset getCategoryDataset() {
		return categoryDataset;
	}

	class UpdateChart implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					Integer newData = new Integer(UpdateQueues.getInstance()
							.getTempQ().take());
					System.out.println("updating Chart...");
					ThermometerBarChart.getInstance().updateChart(newData);

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
