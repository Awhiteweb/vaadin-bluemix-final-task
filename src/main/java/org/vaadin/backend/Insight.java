package org.vaadin.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	/**
	 * 
	 * @param max number of items to receive
	 * @return 
	 */
	public List<Feedback> getFeedback( int max )
	{
		return feedback.subList( 0, max ); 
	}
	
	public int getFeedbackSize()
	{
		return feedback.size();
	}
}
