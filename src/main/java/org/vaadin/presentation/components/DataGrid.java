package org.vaadin.presentation.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.vaadin.backend.Feedback;
import org.vaadin.backend.ImageData;
import org.vaadin.backend.session.UserSession;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class DataGrid extends GridLayout
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	UserSession userSession;
	private List<ListMap> map;
	
	public DataGrid()
	{
		map = new ArrayList<ListMap>();
		setColumns( 4 );
		setWidth( "90%" );
		mergeLists( userSession.getImages() );
		createContents();
	}
	
	
	void createContents()
	{
		int row = 0;
		for (int i = 0; i < map.size(); i++ )
		{
			addComponent( new Label( map.get( i ).getParent() ), 0, row, 0, ( row + map.get( i ).getQuantity() - 1 ) );
			for ( Feedback f : map.get( i ).getFeedback() )
			{
				addComponent( new Label( f.getChild() ), 1, row );
				addComponent( new Label( String.format( "%1$.2f%%", f.getValue()*100 ) ), 2, row );
				addComponent( new Label( f.toString() ), 3, row );
				row++;
			}
		}
	}


	void mergeLists( List<ImageData> list )
	{
		for ( ImageData imageData : list )
		{
			for ( Feedback f : imageData.getTopFiveFeedback() )
			{
				addToMap( f );
			}
		}
		map.sort( ( m1, m2 ) -> ( m2.getQuantity() - m1.getQuantity() ) );
	}
	
	void addToMap( Feedback f )
	{
		for ( ListMap m : map )
		{
			if ( m.getParent().equals( f.getParent() ) )
			{
				m.addFeedback( f );
				return;
			}
		}
		map.add( new ListMap( f ) );
	}
	
	class ListMap
	{
		private List<Feedback> feedback;
		private String parent;
		private int quantity;
		ListMap( Feedback feedback )
		{
			this.parent = feedback.getParent();
			this.feedback = Arrays.asList( feedback );
			this.quantity = 1;
		}
		public void addFeedback( Feedback feedback )
		{
			this.feedback.add( feedback );
			quantity++;
		}
		public String getParent()
		{
			return parent;
		}
		public int getQuantity()
		{
			return quantity;
		}
		public List<Feedback> getFeedback()
		{
			return feedback;
		}
	}
}
