package org.vaadin.presentation.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.list.TreeList;
import org.vaadin.backend.data.Feedback;
import org.vaadin.backend.data.ImageData;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Background;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Pane;
import com.vaadin.addon.charts.model.PlotOptionsSeries;
import com.vaadin.addon.charts.model.PointPlacement;
import com.vaadin.addon.charts.model.Stacking;
import com.vaadin.addon.charts.model.TickmarkPlacement;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;

public class RoseChart extends Chart
{
	private static final long serialVersionUID = 1L;
	private List<ImageData> images;
//	private HashMap<Label, Number[]> seriesData;
	private List<String> labels;
	private Number[] minSeriesData;
	private Number[] lightSeriesData;
	private Number[] aveSeriesData;
	private Number[] highSeriesData;
	
	public RoseChart( List<ImageData> images )
	{
		super( ChartType.COLUMN );
		this.images = images;
		setHeight( "400px" );
		setWidth( "100%" );
		loadData();
	}
	
	private void loadData()
	{
		labels = new LinkedList<String>();
//		seriesData = new HashMap<Label, Number[]>();
		
		generateData();
		
		Configuration conf = getConfiguration();
		conf.getChart().setPolar( true );
		conf.getChart().setInverted( false );
		conf.setTitle( "Parent and Child regularity" );
		
		Pane pane = new Pane();
        pane.setSize("85%");
        conf.addPane(pane);
        pane.setBackground(new Background[] {});
        
        conf.getLegend().setReversed(false);
        conf.getLegend().setHorizontalAlign(HorizontalAlign.RIGHT);
        conf.getLegend().setVerticalAlign(VerticalAlign.TOP);
        conf.getLegend().setY(100);
        conf.getLegend().setLayout(LayoutDirection.VERTICAL);
        
        XAxis axis = new XAxis();
        axis.setCategories( labels.toArray( new String[labels.size()] ) );
        axis.setTickmarkPlacement(TickmarkPlacement.ON);
        
        YAxis yaxs = new YAxis();
        yaxs.setMin(0);
        yaxs.setEndOnTick(false);
        yaxs.setShowLastLabel(true);
        yaxs.setTitle("Frequency (%)");
        yaxs.getLabels().setFormatter("function() {return this.value + '%';}");
        conf.addxAxis(axis);
        conf.addyAxis(yaxs);
        
        PlotOptionsSeries series = new PlotOptionsSeries();
        series.setStacking(Stacking.NORMAL);
        series.setShadow(false);
        series.setGroupPadding(0);
        series.setPointPlacement(PointPlacement.ON);
        conf.setPlotOptions(series);

        conf.addSeries( new ListSeries( Label.MIN.toString(), minSeriesData ) );
        conf.addSeries( new ListSeries( Label.LIGHT.toString(), lightSeriesData ) );
        conf.addSeries( new ListSeries( Label.AVE.toString(), aveSeriesData ) );
        conf.addSeries( new ListSeries( Label.HIGH.toString(), highSeriesData ) );

        conf.reverseListSeries();
        drawChart( conf );
        
	}
	
	private void generateData()
	{
		List<HashMap<String, Number[]>> children = new LinkedList<HashMap<String, Number[]>>();
		List<HashMap<String, Number>> adults = new LinkedList<HashMap<String, Number>>();
		for ( ImageData image : images )
		{
			HashMap<String, Number> parents = image.getParentPercent();
			for ( String parent : parents.keySet() )
			{
				children.add( getChildren( image, parent ) );				
			}
			adults.add( parents );
		}
		matchToEnum( mergeLists( adults, children ) );
		
	}
	
	private void matchToEnum( HashMap<String, Number[]> results )
	{
		labels.addAll( results.keySet() );
//		Number[] nn = initNumberArray( labels.size() );
//		seriesData.put( Label.MIN, nn );
//		seriesData.put( Label.LIGHT, nn );
//		seriesData.put( Label.AVE, nn );
//		seriesData.put( Label.HIGH, nn );
		
		minSeriesData = new Number[labels.size()];
		lightSeriesData = new Number[labels.size()];
		aveSeriesData = new Number[labels.size()];
		highSeriesData = new Number[labels.size()];
		
		int i = 0;
		for ( String r : results.keySet() )
		{	
			Number[] n = results.get( r );
			double percent = Math.floor( n[0].doubleValue() / n[1].doubleValue() );			
			switch ( Label.getLabel( percent ) )
			{
				case MIN:
					minSeriesData[i] = percent;
//					Number[] a = seriesData.get( Label.MIN );
//					a[i] = percent;
					break;
				case LIGHT:
					lightSeriesData[i] = percent;
//					Number[] b = seriesData.get( Label.LIGHT );
//					b[i] = percent;
					break;
				case AVE:
					aveSeriesData[i] = percent;
//					Number[] c = seriesData.get( Label.AVE );
//					c[i] = percent;
					break;
				case HIGH:
					highSeriesData[i] = percent;
//					Number[] d = seriesData.get( Label.HIGH );
//					d[i] = percent;
					break;
				default:
					break;
			}
			i++;
		}

	}
	
	private Number[] initNumberArray( int num )
	{
		List<Number> n = new ArrayList<Number>();
		for ( int i = 0; i < num; i++ )
			n.add( 0 );
		return n.toArray( new Number[num] );
	}
	
//	private List<Mapping> matchToEnum( HashMap<String, Number[]> results )
//	{
//		List<Mapping> map = new LinkedList<Mapping>();
//		for ( String r : results.keySet() )
//		{
//			Number[] n = results.get( r );
//			double percent = ( n[0].doubleValue() / n[1].doubleValue() ) * 100;
//			map.add( new Mapping( r, Label.getLabel( percent ) ) );
//		}
//		return map;
//	}

	private HashMap<String, Number[]> mergeLists( List<HashMap<String, Number>> adults, List<HashMap<String, Number[]>> children )
	{
		HashMap<String, Number[]> map = new HashMap<String, Number[]>();
		for ( HashMap<String, Number> adult : adults )
		{
			for ( String a : adult.keySet() )
			{
				double[] v = { adult.get( a ).doubleValue(), 1 };
				if ( map.containsKey( a ) )
				{
					Number[] n = map.get( a );
					v[0] += n[0].doubleValue();
					v[1] += n[1].doubleValue();
				}
				map.put( a, new Number[]{ v[0], v[1] } );
			}
		}
		for ( HashMap<String, Number[]> child : children )
		{
			for ( String c : child.keySet() )
			{
				double[] v = { child.get( c )[0].doubleValue(), child.get( c )[1].doubleValue() };
				if ( map.containsKey( c ) )
				{
					Number[] n = map.get( c );
					v[0] += n[0].doubleValue();
					v[1] += n[1].doubleValue();
				}
				map.put( c, new Number[]{ v[0], v[1] } );
			}
		}
		return map;
	}

	private HashMap<String, Number[]> getChildren( ImageData image, String parent )
	{
		HashMap<String, Number> children = image.getChildren( parent );
		HashMap<String, Number[]> map = new HashMap<String, Number[]>();
		for ( String c : children.keySet() )
		{
			double[] v = { children.get( c ).doubleValue(), 1 };
			if ( map.containsKey( c ) )
			{
				Number[] n = map.get( c );
				v[0] += n[0].doubleValue();
				v[1] += n[1].doubleValue();
			}
			map.put( c, new Number[]{ v[0], v[1] } );
		}
		return map;
	}
	
	class Mapping
	{
		private int[] data;
		private String name;
		
		Mapping( String name, Label value )
		{
			this.name = name;
			switch ( value )
			{
				case MIN:
					data = new int[]{ 1, 0, 0, 0 };
					break;
				case LIGHT:
					data = new int[]{ 0, 1, 0, 0 };
					break;
				case AVE:
					data = new int[]{ 0, 0, 1, 0 };
					break;
				case HIGH:
					data = new int[]{ 0, 0, 0, 1 };
					break;
				default:
					break;
			}
		}
		
		public String getName()
		{
			return name;
		}
		public int[] getValues()
		{
			return data;
		}
	}

	enum Label
	{
		MIN ( "Minimal" , 25 ),
		LIGHT ( "OK", 50 ),
		AVE ( "Good", 75 ),
		HIGH ( "High", 100 );
		
		private String name;
		private double max;
		
		Label( String name, double max )
		{
			this.name = name;
			this.max = max;
		}
		
		public static Label getLabel( double num )
		{
			if ( num < MIN.max )
				return MIN;
			else if ( num < LIGHT.max )
				return LIGHT;
			else if ( num < AVE.max )
				return AVE;
			else
				return HIGH;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
}
