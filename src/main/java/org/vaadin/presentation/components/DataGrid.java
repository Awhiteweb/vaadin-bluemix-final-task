package org.vaadin.presentation.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.vaadin.backend.data.Feedback;
import org.vaadin.backend.data.ImageData;
import org.vaadin.backend.data.ListMap;

import com.vaadin.ui.Grid;

public class DataGrid extends Grid
{
	private static final long serialVersionUID = 1L;

	private HashMap<String, ListMap> map;
	
	public DataGrid( List<ImageData> images )
	{
		map = new HashMap<String, ListMap>();
		setSizeFull();
		addColumn( "Parent", String.class );
		addColumn( "Child", String.class );
		addColumn( "Occurence", String.class );
		addColumn( "Name", String.class );
		List<ListMap> list = new ArrayList<ListMap>();
		list = mergeLists( images );
		try {
			createContents( list );			
		}
		catch ( Exception e )
		{
			System.out.println( "error creating lists" );
			e.printStackTrace();
		}
	}
	
	private List<ListMap> mergeLists( List<ImageData> data )
	{
		for ( ImageData imageData : data )
		{
			for ( Feedback f : imageData.getTopFiveFeedback() )
			{
				if ( map.containsKey( f.getParent() ) )
				{
					ListMap item = map.get( f.getParent() );
					item.addFeedback( f );
					map.put( f.getParent(),	item );
				}
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

	private void createContents( List<ListMap> list )
	{
		for (int i = 0; i < list.size(); i++ )
		{
			addRow( list.get( i ).getParent(), null, null, null );
			for ( Feedback f : list.get( i ).getFeedback() )
			{
				addRow( null, f.getChild(), String.format( "%1$.2f%%", f.getValue()*100 ), f.toString() );
			}
		}
	}

}
