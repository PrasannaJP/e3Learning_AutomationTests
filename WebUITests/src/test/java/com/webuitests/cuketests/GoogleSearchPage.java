package com.webuitests.cuketests;

import com.webuiframework.oua.uitests.control.Button;
import com.webuiframework.oua.uitests.control.Elements;
import com.webuiframework.oua.uitests.control.Image;
import com.webuiframework.oua.uitests.control.Input;
import com.webuiframework.oua.uitests.panel.BasePanel;

public class GoogleSearchPage extends BasePanel<GoogleSearchPage> {
	
	private static GoogleSearchPage googlesearchPage;
	
	public static String logoImageID = "hplogo";
	public static String inputfieldID = "lst-ib";
	public static String SearchButtonID = "btnK";
	public static String Search2ButtonID = "btnG";
	public static String resultsLinksXPATH = "//ol[@id='rso']/div[@class='srg']/div/div/h3/a";

    public final Image<GoogleSearchPage> logo = new Image<GoogleSearchPage>("logo", logoImageID,"id", this);
    public final Input<GoogleSearchPage> textField = new Input<GoogleSearchPage>("textField", inputfieldID,"id", this);
    public final Button<GoogleSearchPage> searchBtn = new Button<GoogleSearchPage>("searchBtn", SearchButtonID,"name", this);
    public final Button<GoogleSearchPage> searchBtn2 = new Button<GoogleSearchPage>("searchBtn2", Search2ButtonID,"name", this);
    public final Elements<GoogleSearchPage> resultsLinks = new Elements<GoogleSearchPage>("resultsLinks", resultsLinksXPATH,"xpath", this);
    

    public static GoogleSearchPage get() {
        if (googlesearchPage == null) {
        	googlesearchPage = new GoogleSearchPage();
        }
        return googlesearchPage;
    }

}
