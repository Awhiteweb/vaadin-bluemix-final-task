package org.vaadin.presentation.components;

import java.util.List;

import org.vaadin.backend.data.ImageData;

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ImageGrid extends GridLayout
{
	private static final long serialVersionUID = 1L;
	public List<ImageData> images;

	public ImageGrid( List<ImageData> images )
	{
		super( 4, ( images.size() / 4 ) );
		this.images = images;
		loadData();
	}
	
	private void loadData()
	{
		int iN = 0;
		for ( int i = 0; i < ( images.size() / 4 ); i++ )
		{
			for ( int j = 0; j < 4; j++ )
			{
				
				Image image = new Image();
				image.setSource( new ExternalResource( images.get( iN ).getThumbnailUrl() ) );
				image.setId( images.get( iN ).getId() );
				addComponent( image, j, i );
				image.addClickListener( new ImageClick( images.get( iN ) ) );
				int[] size = images.get( iN ).getThumbnailSize();
				getComponent( j, i ).setWidth( size[0], Unit.PIXELS );
				getComponent( j, i ).setHeight( size[1], Unit.PIXELS );
				iN++;
			}
		}
		setSpacing( true );
	}
	
	class ImageClick implements ClickListener
	{
		private static final long serialVersionUID = 1L;
		private ImageData data;
		
		public ImageClick( ImageData data )
		{
			this.data = data;
		}
		@Override
		public void click( ClickEvent event )
		{
			if ( !event.getButton().equals( MouseButton.LEFT ) )
				return;
			if ( data == null )
				return;
			Window w = new Window();
			VerticalLayout v = new VerticalLayout();
			v.setMargin( true );
			w.setContent( v );
			Image im = new Image();
			im.setSource( new ExternalResource( 
					data.getStandardResolution() ) );
			v.addComponent( im );
			w.setClosable( true );
			w.setResizable( false );
			w.setModal( true );
			w.center();
			UI.getCurrent().addWindow( w );
		}
	}
}