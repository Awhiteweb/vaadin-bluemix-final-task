package org.vaadin.presentation.views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
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
	private List<MediaFeedData> mediaFeed;

	@Override
	public void enter( ViewChangeListener.ViewChangeEvent viewChangeEvent )
	{
		removeAllComponents();

		setMargin( new MMarginInfo( false, true ) );
		add( new Header( "Viewing the data" ).setHeaderLevel( 2 ) );
		MediaFeed mf = userSession.getMyRecentMediaFeed();
		mediaFeed = mf.getData();
		GridLayout userGrid = new GridLayout( 2, mediaFeed.size() );
		for ( int i = 0; i < mediaFeed.size(); i++ )
		{
			userGrid.addComponent( new Label( mediaFeed.get( i ).getId() ), 0, i );
			Image image = new Image( "",
					new ExternalResource( mediaFeed.get( i ).getImages().getThumbnail().getImageUrl() ) );
			userGrid.addComponent( image, 1, i );
			int h = mediaFeed.get( i ).getImages().getThumbnail().getImageHeight();
			userGrid.getComponent( 1, i ).setHeight( h, Unit.PIXELS );
		}
		add( userGrid );
		visualInsights();
	}

	private void visualInsights()
	{
		List<Summary> imageSummaries = new ArrayList<Summary>();
		VisualInsights visual = new VisualInsights();
		visual.setApiKey( BluemixUtils.getAPIKey( "v-insights-fm", BluemixUtils.PLAN_FREE ) );
		for ( MediaFeedData mfd : mediaFeed )
		{
			File image = new File( mfd.getImages().getStandardResolution().getImageUrl() );
			imageSummaries.add( visual.getSummary( image ) );
		}
		for ( Summary sum : imageSummaries )
			for ( SummaryItem si : sum.getSummary() )
				add( new Label( String.format( "Summary: %s, Score: %d", si.getName(), si.getScore() ) ) );

		// word cloud https://github.com/jasondavies/d3-cloud
	}

}
