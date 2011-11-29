package me.brandonc.benchmark.chart;

import java.awt.BasicStroke;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartHelper {

	public static void saveAsFile(JFreeChart chart, String outputPath, int weight, int height) {
		FileOutputStream out = null;
		try {
			java.io.File outFile = new java.io.File(outputPath);
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			out = new FileOutputStream(outputPath);
			ChartUtilities.writeChartAsPNG(out, chart, weight, height);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	public static JFreeChart createChart(CategoryDataset categoryDataset, String rowName, String colName, String chartTitle) {

		JFreeChart jfreechart = ChartFactory.createLineChart(chartTitle, rowName, colName, categoryDataset, PlotOrientation.VERTICAL, true, true, false);

		LegendTitle legend = jfreechart.getLegend();

		legend.setItemFont(new Font("Dotum", Font.BOLD, 16));

		CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();

		LineAndShapeRenderer render = (LineAndShapeRenderer) plot.getRenderer();
		render.setBaseStroke(

		new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)

		);

		render.setBaseShapesFilled(Boolean.TRUE);
		render.setBaseShapesVisible(true);

		CategoryAxis cateaxis = plot.getDomainAxis();

		cateaxis.setLabelFont(new Font("Dotum", Font.BOLD, 16));

		cateaxis.setTickLabelFont(new Font("Dotum", Font.BOLD, 16));

		NumberAxis numaxis = (NumberAxis) plot.getRangeAxis();

		numaxis.setLabelFont(new Font("Dotum", Font.BOLD, 16));
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(new Font("Dotum", Font.BOLD, 16));
		jfreechart.setTitle(title);

		return jfreechart;
	}

	public static void drawChart(File imageDir, int width, int height, ResultSet resultSet, Column series, Column type, Map<Column, String> conditions) {

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (String[] record : resultSet.getData()) {

			boolean matched = true;
			for (Entry<Column, String> condition : conditions.entrySet()) {
				if (!record[condition.getKey().ordinal()].equalsIgnoreCase(condition.getValue())) {
					matched = false;
					break;
				}
			}

			if (matched) {
				dataset.addValue(Double.parseDouble(record[Column.TPS.ordinal()]), record[series.ordinal()], record[type.ordinal()]);
			}
		}

		String chartTitle = getChartTitle(series, type, conditions);
		String fileName = getFileName(series, type, conditions);

		JFreeChart freeChart = ChartHelper.createChart(dataset, type.name, Column.TPS.name, chartTitle);
		ChartHelper.saveAsFile(freeChart, imageDir + "/" + fileName, width, height);
	}

	public static String getFileName(Column series, Column type, Map<Column, String> conditions) {
		StringBuilder sb = new StringBuilder();
		sb.append(series.name);
		sb.append("_");
		sb.append(type.name);

		if (conditions.size() > 0) {
			sb.append("-");
			for (Entry<Column, String> condition : conditions.entrySet()) {
				sb.append(condition.getKey().name);
				sb.append(condition.getValue());
				sb.append("-");
			}
		}

		sb.deleteCharAt(sb.length() - 1);
		sb.append(".png");

		return sb.toString();
	}

	public static String getChartTitle(Column series, Column type, Map<Column, String> conditions) {
		StringBuilder sb = new StringBuilder();
		sb.append(series.name);
		sb.append(" and ");
		sb.append(type.name);

		if (conditions.size() > 0) {
			sb.append("[");

			for (Entry<Column, String> condition : conditions.entrySet()) {
				sb.append(condition.getKey().name);
				sb.append("=");
				sb.append(condition.getValue());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);

			sb.append("]");
		}

		return sb.toString();
	}

}
