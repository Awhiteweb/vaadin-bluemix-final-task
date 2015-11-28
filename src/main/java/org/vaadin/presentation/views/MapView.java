package org.vaadin.presentation.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.addon.leaflet.LMap;
import org.vaadin.addon.leaflet.LMarker;
import org.vaadin.addon.leaflet.LOpenStreetMapLayer;
import org.vaadin.addon.leaflet.LeafletClickEvent;
import org.vaadin.addon.leaflet.LeafletClickListener;
import org.vaadin.addon.leaflet.control.LZoom;
import org.vaadin.addon.leaflet.shared.ControlPosition;
import org.vaadin.backend.ImageData;
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
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Image;
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
	LMap worldMap = new LMap();

    @PostConstruct
    void init() 
    {
        add(new Header("Customers on map").setHeaderLevel(2));
        expand(worldMap);
        setMargin(new MarginInfo(false, true, true, true));

        LZoom zoom = new LZoom();
        zoom.setPosition(ControlPosition.topright);
        worldMap.addControl(zoom);
        
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        worldMap.removeAllComponents();
        LOpenStreetMapLayer osm = new LOpenStreetMapLayer();
        osm.setDetectRetina(true);
        for ( ImageData data : userSession.getImages() )
        {
        	LMarker marker = new LMarker( data.getLocation() );
        	marker.addClickListener( new LCL( data ) );
        	worldMap.addComponent( marker );
        }
        worldMap.addComponent(osm);
        worldMap.zoomToContent();
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
