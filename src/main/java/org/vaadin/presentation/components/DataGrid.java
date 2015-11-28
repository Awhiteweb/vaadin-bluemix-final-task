package org.vaadin.presentation.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.vaadin.backend.Feedback;
import org.vaadin.backend.ImageData;
import org.vaadin.backend.data.ListMap;
import org.vaadin.backend.session.UserSession;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class DataGrid extends GridLayout
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	UserSession userSession;
	private HashMap<String, ListMap> map;
	
	public DataGrid()
	{
		map = new HashMap<String, ListMap>();
		setColumns( 4 );
		setWidth( "90%" );
		List<ListMap> list = new ArrayList<ListMap>();
		try {
			list = mergeLists( userSession.getImages() );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		try {
			createContents( list );			
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	private void createContents( List<ListMap> list )
	{
		int row = 0;
		for (int i = 0; i < list.size(); i++ )
		{
			addComponent( new Label( list.get( i ).getParent() ), 0, row, 0, ( row + list.get( i ).getQuantity() - 1 ) );
			for ( Feedback f : list.get( i ).getFeedback() )
			{
				addComponent( new Label( f.getChild() ), 1, row );
				addComponent( new Label( String.format( "%1$.2f%%", f.getValue()*100 ) ), 2, row );
				addComponent( new Label( f.toString() ), 3, row );
				row++;
			}
		}
	}

	private List<ListMap> mergeLists( List<ImageData> data )
	{
		for ( ImageData imageData : data )
		{
			for ( Feedback f : imageData.getTopFiveFeedback() )
			{
				if ( map.containsKey( f.getParent() ) )
					map.get( f.getParent() ).addFeedback( f );
				else
					map.put( f.getParent(), new ListMap( f ) );
			}
		}
		List<ListMap> list = new ArrayList<ListMap>( map.size() );
		for ( String s : map.keySet() )
			list.add( map.get( s ) );
		list.sort( ( ListMap m1, ListMap m2 ) -> ( m2.getQuantity() - m1.getQuantity() ) );
		return list;
	}

}
