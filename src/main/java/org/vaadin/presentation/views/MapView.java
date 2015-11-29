package org.vaadin.presentation.views;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addon.leaflet.LMap;
import org.vaadin.addon.leaflet.LMarker;
import org.vaadin.addon.leaflet.LOpenStreetMapLayer;
import org.vaadin.addon.leaflet.LeafletClickEvent;
import org.vaadin.addon.leaflet.LeafletClickListener;
import org.vaadin.addon.leaflet.control.LZoom;
import org.vaadin.addon.leaflet.shared.ControlPosition;
import org.vaadin.backend.data.ImageData;
import org.vaadin.backend.session.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@CDIView("map")
@ViewMenuItem(icon = FontAwesome.GLOBE, order = 3 )
public class MapView extends MVerticalLayout implements View 
{
	private static final long serialVersionUID = 1L;
	@Inject
	UserSession userSession;
	List<ImageData> images;
	LMap worldMap = new LMap();

    @PostConstruct
    void init() 
    {
    	try
    	{
    		images = userSession.getImages();
    		loadData();
    	}
    	catch ( Exception e )
    	{
    		e.printStackTrace();
    	}
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

	private void loadData()
	{
        add(new Header("Your photo locations").setHeaderLevel(2));
        expand(worldMap);
        setMargin(new MarginInfo(false, true, true, true));
        LZoom zoom = new LZoom();
        zoom.setPosition(ControlPosition.topright);
        worldMap.addControl(zoom);
		worldMap.removeAllComponents();
        LOpenStreetMapLayer osm = new LOpenStreetMapLayer();
        osm.setDetectRetina(true);
        int counter = 0;
        for ( ImageData data : images )
        {
        	if ( data.getLocation() != null)
        	{
	        	LMarker marker = new LMarker( data.getLocation() );
	        	marker.addClickListener( new LCL( data ) );
	        	worldMap.addComponent( marker );
	        	counter++;
        	}
        }
        worldMap.addComponent(osm);
        if ( counter > 0 )
        	worldMap.zoomToContent();
        else
        	new Notification( "No data to map " + FontAwesome.WARNING, "You have no image location data to map", 
        			Type.WARNING_MESSAGE, true ).show( Page.getCurrent() );
        	
	}
    
    class LCL implements LeafletClickListener
    {
    	private ImageData data;
    	LCL( ImageData data )
    	{
    		this.data = data;
    	}

		@Override
		public void onClick( LeafletClickEvent event )
		{
			Window w = new Window();
			VerticalLayout v = new VerticalLayout();
			Image image = new Image();
			image.setSource( new ExternalResource( data.getThumbnailUrl() ) );
			image.setId( data.getId() );
			image.addClickListener( new ICL( data ) );
			v.addComponent( image );
			w.setContent( v );
			w.setResizable( false );
			w.setClosable( true );
			w.setModal( true );
			w.center();
			UI.getCurrent().addWindow( w );
		}
    }
    
    class ICL implements ClickListener
    {
		private static final long serialVersionUID = 1L;
		private ImageData data;
    	ICL( ImageData data )
    	{
    		this.data = data;
    	}

		@Override
		public void click( ClickEvent event )
		{
			Window w = new Window();
			VerticalLayout v = new VerticalLayout();
			Image image = new Image();
			image.setSource( new ExternalResource( data.getStandardResolution() ) );
			image.setId( data.getId() );
			v.addComponent( image );
			w.setContent( v );
			w.setResizable( false );
			w.setClosable( true );
			w.setModal( true );
			w.center();
			UI.getCurrent().addWindow( w );
		}
    }
}
