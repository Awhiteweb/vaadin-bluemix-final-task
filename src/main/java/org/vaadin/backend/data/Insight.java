package org.vaadin.backend.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.ibm.watson.developer_cloud.visual_insights.v1.model.Summary.SummaryItem;

public class Insight
{
	private List<Feedback> feedback;
	
	public Insight( List<SummaryItem> summary )
	{
		feedback = new ArrayList<Feedback>();
		for ( SummaryItem si : summary )
		{
			feedback.add( new Feedback( si.getName(), si.getScore() ) );
		}
		feedback.sort( (f1, f2) -> ( Double.compare( f1.getValue(), f2.getValue() ) ) );
		Collections.reverse( feedback );
	}
	public List<Feedback> getFeedback( int max )
	{
		return trimList( feedback.subList( 0, max ) ); 
	}
	public int getFeedbackSize()
	{
		return feedback.size();
	}

	public List<Feedback> getAllFeedback()
	{
		return trimList( feedback );
	}
	
	private List<Feedback> trimList( List<Feedback> list )
	{
		List<Feedback> trimmed = new ArrayList<Feedback>();
		for( Feedback f : list )
		{
			if ( f.getValue() > 0 )
				trimmed.add( f );
		}
		return trimmed;
	}

	public Set<String> getParents()
	{
		Set<String> set = new TreeSet<String>(); 
		for ( Feedback f : getAllFeedback() )
		{
			set.add( f.getParent() );
		}
		return set;
	}
	public HashMap<String, Number> getParentPercentage()
	{
		HashMap<String, Number[]> map = new HashMap<String, Number[]>();
		for ( Feedback f : getAllFeedback() )
		{
			String p = f.getParent();
			double[] v = { f.getValue(), 1 };
			if ( map.containsKey( p ) )
			{
				Number[] n = map.get( p );
				v[0] += n[0].doubleValue();
				v[1] += n[1].doubleValue();
			}
			map.put( p, new Number[]{ v[0], v[1] } );
		}
		HashMap<String, Number> output = new HashMap<String, Number>();
		for ( String s : map.keySet() )
		{
			Number[] n = map.get( s );
			Number d = ( n[0].doubleValue() / n[1].doubleValue() ) * 100;
			output.put( s, d );
		}
		return output;
	}
	public HashMap<String, Number> getChildren( String parent )
	{
		HashMap<String, Number[]> map = new HashMap<String, Number[]>();
		for ( Feedback f : getAllFeedback() )
		{
			if ( f.getParent().equals( parent ) )
			{
				String c = f.getChild();
				double[] v = { f.getValue(), 1 };
				if ( map.containsKey( c ) )
				{
					Number[] n = map.get( c );
					v[0] += n[0].doubleValue();
					v[1] += n[1].doubleValue();
				}
				map.put( c, new Number[]{ v[0], v[1] } );
			}
		}
		HashMap<String, Number> output = new HashMap<String, Number>();
		for ( String s : map.keySet() )
		{
			Number[] n = map.get( s );
			Number d = ( n[0].doubleValue() / n[1].doubleValue() ) * 100;
			output.put( s, d );
		}
		return output;
	}
}
