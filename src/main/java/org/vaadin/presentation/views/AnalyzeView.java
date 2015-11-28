package org.vaadin.presentation.views;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.backend.Feedback;
import org.vaadin.backend.ImageData;
import org.vaadin.backend.data.ListMap;
import org.vaadin.backend.session.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.presentation.components.DataGrid;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MMarginInfo;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.sun.media.jfxmedia.logging.Logger;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import javafx.collections.transformation.SortedList;

/**
 * An example view that just make some simple analysis for the data and displays
 * it in various charts.
 */
@CDIView( "analyze" )
@ViewMenuItem( icon = FontAwesome.BAR_CHART_O, order = 2 )
public class AnalyzeView extends MVerticalLayout implements View
{
	private static final long serialVersionUID = 1L;
	@Inject
	UserSession userSession;
	private List<ImageData> images;

	@Override
	public void enter( ViewChangeListener.ViewChangeEvent viewChangeEvent )
	{
		removeAllComponents();

		setMargin( new MMarginInfo( false, true ) );
		add( new Header( "Viewing the data" ).setHeaderLevel( 2 ) );

		images = userSession.getImages();
		
		GridLayout userGrid = new GridLayout( 4, ( images.size() / 4 ) );
		int iN = 0;
		for ( int i = 0; i < ( images.size() / 4 ); i++ )
		{
			for ( int j = 0; j < 4; j++ )
			{
				
				Image image = new Image();
				image.setSource( new ExternalResource( images.get( iN ).getThumbnailUrl() ) );
				image.setId( images.get( iN ).getId() );
				userGrid.addComponent( image, j, i );
				image.addClickListener( e -> {
					if ( !e.getButton().equals( MouseButton.LEFT ) )
						return;
					if ( userSession.getImage( image.getId() ) == null )
						return;
					Window w = new Window();
					VerticalLayout v = new VerticalLayout();
					v.setMargin( true );
					w.setContent( v );
					Image im = new Image();
					im.setSource( new ExternalResource( 
							userSession.getImage( image.getId() ).getStandardResolution() ) );
					v.addComponent( im );
					w.setClosable( true );
					w.setResizable( false );
					w.setModal( true );
					w.center();
					UI.getCurrent().addWindow( w );
				} );
				int[] size = images.get( iN ).getThumbnailSize();
				userGrid.getComponent( j, i ).setWidth( size[0], Unit.PIXELS );
				userGrid.getComponent( j, i ).setHeight( size[1], Unit.PIXELS );
				iN++;
			}
		}
		userGrid.setSpacing( true );
		add( userGrid );
		add( new Label( "<hr />",ContentMode.HTML ) );
		add( new DataGrid() );
	}
	
}
