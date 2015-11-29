package org.vaadin.presentation.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.vaadin.backend.data.ISO;
import org.vaadin.backend.data.ImageData;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;

public class DataChart extends Chart
{
	private static final long serialVersionUID = 1L;
	private List<ImageData> images;
	private HashMap<String, ISO[]> parentMap;
	
	public DataChart( List<ImageData> list )
	{
		super( ChartType.COLUMN );
		setHeight( "400px" );
		setWidth( "100%" );
		this.images = list;
		loadData();
	}
	
	private void loadData()
	{
		Set<String> parents = getParents();
		
		generateData( parents );
		
		Configuration conf = getConfiguration();
		conf.setTitle( "Your photo breakdown by parent insight" );
//		conf.setSubTitle( "Click a column to view a break down for that picture" );
		conf.addxAxis( getXAxis( parents ) );
		conf.addyAxis( getYAxis() );
		conf.setLegend( getLegend() );
		conf.setPlotOptions( getPlotOptions() );
		conf.setTooltip( getToolTip() );
		for ( DataSeries ds : getSeries() )
			conf.addSeries( ds );
		drawChart( conf );
	}
	
	private void generateData( Set<String> parents )
	{
		int numImages = images.size();

		parentMap = new HashMap<String, ISO[]>();
		for ( String s: parents )
		{
			parentMap.put( s, initNumberArray( numImages ) );
		}
		
		for ( int i = 0; i < numImages; i++ )
		{
			HashMap<String, Number> map = images.get( i ).getParentPercent();
			String id = images.get( i ).getId();
			for ( String s : map.keySet() )
			{
				ISO[] n = parentMap.get( s );
				n[i] = new ISO( id, map.get( s ) );
				parentMap.put( s, n );
			}			
		}
		// map should now create series for image
	}

	private List<DataSeries> getSeries()
	{
		List<DataSeries> seriesList = new ArrayList<DataSeries>();
		for ( String parent : parentMap.keySet() )
		{
			DataSeries series = new DataSeries();
			series.setName( parent );
			for ( ISO iso : parentMap.get( parent ) )
				series.add( getItem( iso ) );
			seriesList.add( series );
		}
		return seriesList;
	}
	
	private DataSeriesItem getItem( ISO iso )
	{
		DataSeriesItem item = new DataSeriesItem();
		item.setName( iso.getId() );
		item.setY( iso.getValue() );
		return item;
	}
	
	private YAxis getYAxis()
	{
		YAxis yAxis = new YAxis();
		yAxis.setMin( 0 );
		yAxis.setMax( 100 );
		yAxis.setTitle( "%" );
		return yAxis;
	}
	
	private XAxis getXAxis(Set<String> parents)
	{		
		XAxis xAxis = new XAxis();
		String[] cats = new String[images.size()];
		for ( int i = 0; i < images.size(); i++ )
		{
			if ( images.get( i ).getCaptionId() != null )
				cats[i] = images.get( i ).getCaptionId();
		}
		xAxis.setCategories( cats );
		Labels xlabels = xAxis.getLabels();
		xlabels.setAlign(HorizontalAlign.RIGHT);
		xlabels.setRotation(-45);
		return xAxis;
	}
	
	private Legend getLegend()
	{
		Legend legend = new Legend();
		legend.setLayout( LayoutDirection.VERTICAL );
        legend.setBackgroundColor( "#FFFFFF" );
        legend.setHorizontalAlign( HorizontalAlign.LEFT );
        legend.setVerticalAlign( VerticalAlign.TOP );
        legend.setX( 75 );
        legend.setY( 10 );
        legend.setFloating( true );
        legend.setShadow( true );
        return legend;
	}
	
	private PlotOptionsColumn getPlotOptions()
	{
		PlotOptionsColumn plot = new PlotOptionsColumn();
        plot.setPointPadding(0.1);
        plot.setBorderWidth(0);
        return plot;
	}
	
	private Tooltip getToolTip()
	{
		Tooltip tt = new Tooltip();
		tt.setPointFormat( "{point.y:.2f}%" );
		return tt;
	}
	
	private Set<String> getParents()
	{
		Set<String> parents = new TreeSet<String>();
		for ( ImageData image : images )
		{
			parents.addAll( image.getParents() );
		}
		return parents;
	}

	private ISO[] initNumberArray( int num )
	{
		List<ISO> n = new ArrayList<ISO>();
		for ( int i = 0; i < num; i++ )
			n.add( new ISO() );
		return n.toArray( new ISO[num] );
	}

}
