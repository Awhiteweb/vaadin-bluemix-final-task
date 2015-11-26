package org.vaadin.presentation.views;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;





import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
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

import wordcloud.CollisionMode;
import wordcloud.WordCloud;
import wordcloud.WordFrequency;
import wordcloud.bg.RectangleBackground;
import wordcloud.font.scale.FontScalar;
import wordcloud.font.scale.LinearFontScalar;
import wordcloud.nlp.FrequencyAnalyzer;
import wordcloud.palette.ColorPalette;

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
		add( userGrid );
		visualInsights();
	}

	private void visualInsights()
	{
		List<Summary> imageSummaries = new ArrayList<Summary>();
		VisualInsights visual = new VisualInsights();
		visual.setApiKey( BluemixUtils.getAPIKey( "v-insights-fm", BluemixUtils.PLAN_FREE ) );
		add( new Label( visual.getName() ) );
		for ( ImageData image : images )
		{
			try
			{
				File file = File.createTempFile( "tmpImage", "jpg" );
				URL url = new URL( image.getStandardResolution() );
				FileUtils.copyURLToFile( url, file );
				Summary summary = visual.getSummary( file );
//				imageSummaries.add( summary );
			}
			catch ( MalformedURLException e )
			{
				e.printStackTrace();
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
//		for ( Summary sum : imageSummaries )
//			for ( SummaryItem si : sum.getSummary() )
//				add( new Label( String.format( "Summary: %s, Score: %d", si.getName(), si.getScore() ) ) );
		
		// word cloud https://github.com/jasondavies/d3-cloud
	}
	
//	private void analyser( List<String> summary )
//	{
//		final FrequencyAnalyzer fq = new FrequencyAnalyzer();
//		final List<WordFrequency> wf = fq.load( summary );
//		final WordCloud wc = new WordCloud( 600, 300, CollisionMode.RECTANGLE );
//		wc.setPadding( 0 );
//		wc.setBackground( new RectangleBackground( 600, 300 ) );
//		wc.setColorPalette( buildPalette() );
//		wc.setFontScalar( new LinearFontScalar( 10, 40 ) );
//		wc.build( wf );
//		wc.writeToFile( "resources/cloud.png" );
//	}
//	
//	private ColorPalette buildPalette()
//	{
//		return new ColorPalette( 
//				new Color( 0x4055F1 ), 
//				new Color( 0x408DF1 ), 
//				new Color( 0x40AAF1 ), 
//				new Color( 0x40C5F1 ), 
//				new Color( 0x40D3F1 ), 
//				new Color( 0xFFFFFF ) 
//				);
//	}

}
