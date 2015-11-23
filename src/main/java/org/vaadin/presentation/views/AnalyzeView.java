package org.vaadin.presentation.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MMarginInfo;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.visual_insights.v1.VisualInsights;
import com.ibm.watson.developer_cloud.visual_insights.v1.model.Summary;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

/**
 * An example view that just make some simple analysis for the data and displays
 * it in various charts.
 */
@CDIView("analyze")
@ViewMenuItem(icon = FontAwesome.BAR_CHART_O, order = 1)
public class AnalyzeView extends MVerticalLayout implements View {

	private String username = "alex_p_white@yahoo.co.uk";
	private String password = "411\\Ch1lcroft";
	private File audio = new File( "" );
	private File image = new File( "/Users/Alex/Desktop/_DSC2563.JPG" );
	private Label label;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        removeAllComponents();

        setMargin(new MMarginInfo(false, true));
        add(new Header("Customer analysis").setHeaderLevel(2));
        Uploader receiver = new Uploader();
        Upload uploader = new Upload( "Upload a file", receiver );
        uploader.addSucceededListener( receiver );
        uploader.setButtonCaption( "Upload" );
        add( uploader );
        label = new Label();
        visualInsights();
        add( label );
    }

    private void speechToText()
    {
        SpeechToText speech = new SpeechToText();
        speech.setUsernameAndPassword( username, password );
        SpeechResults transcript = speech.recognize( audio, HttpMediaType.AUDIO_WAV) ;
        label.setValue( transcript.toString() );
    }
    
    private void visualInsights()
    {
        VisualInsights visual = new VisualInsights();
        visual.setUsernameAndPassword( username, password );
        
        Summary summary = visual.getSummary( image );
        
        label.setValue( summary.toString() );
    }
    
    class Uploader implements Receiver, SucceededListener
    {
		public File file;
				
		@Override
		public void uploadSucceeded( SucceededEvent event ) 
		{
			
		}
	
		@Override
		public OutputStream receiveUpload( String filename, String mimeType ) 
		{
			FileOutputStream fos = null;
			try 
			{
				file = new File( filename );
				fos = new FileOutputStream( file );
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			return fos;
		} 
	}
}
