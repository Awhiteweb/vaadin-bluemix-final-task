package org.vaadin.presentation.views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;





import javax.inject.Inject;





import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.vaadin.backend.ImageData;
import org.vaadin.backend.session.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MMarginInfo;
import org.vaadin.viritin.layouts.MVerticalLayout;





import com.ibm.watson.developer_cloud.util.BluemixUtils;
import com.ibm.watson.developer_cloud.visual_insights.v1.VisualInsights;
import com.ibm.watson.developer_cloud.visual_insights.v1.model.Summary;
import com.ibm.watson.developer_cloud.visual_insights.v1.model.Summary.SummaryItem;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.PopupView.Content;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * An example view that just make some simple analysis for the data and displays
 * it in various charts.
 */
@CDIView( "analyze" )
@ViewMenuItem( icon = FontAwesome.BAR_CHART_O, order = 1 )
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

		// grid to calculate number of images to fit in available space and generate grid for them 
		
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
					w.center();
					UI.getCurrent().addWindow( w );
				} );
				int[] size = images.get( iN ).getThumbnailSize();
				userGrid.getComponent( j, i ).setWidth( size[0], Unit.PIXELS );
				userGrid.getComponent( j, i ).setHeight( size[1], Unit.PIXELS );
				userGrid.getComponent( j, i ).setStyleName( "cell" );
				iN++;
			}
		}
		add( userGrid );
		visualInsights();
	}

	private void visualInsights()
	{
		List<Summary> imageSummaries = new ArrayList<Summary>();
		VisualInsights visual = new VisualInsights();
		visual.setApiKey( BluemixUtils.getAPIKey( "v-insights-fm", BluemixUtils.PLAN_FREE ) );
		add( new Label( visual.getName() ) );
//		for ( ImageData image : images )
//		{
//			File file = new File( image.getStandardResolution() );
//			imageSummaries.add( visual.getSummary( file ) );
//		}
//		for ( Summary sum : imageSummaries )
//			for ( SummaryItem si : sum.getSummary() )
//				add( new Label( String.format( "Summary: %s, Score: %d", si.getName(), si.getScore() ) ) );

		// word cloud https://github.com/jasondavies/d3-cloud
	}

}
