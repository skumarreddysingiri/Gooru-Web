/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package org.ednovo.gooru.client.uc.tooltip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.i18n.CopyOfMessageProperties;
import org.ednovo.gooru.shared.model.library.LibraryUserDo;
import org.ednovo.gooru.shared.util.StringUtil;
import org.ednovo.gooru.shared.util.UAgentInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @fileName : DiscoverToolTip.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 27-Dec-2013
 *
 * @Author: Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class DiscoverToolTip extends PopupPanel implements HasMouseOutHandlers{
	
	private static DiscoverToolTipUiBinder uiBinder = GWT
			.create(DiscoverToolTipUiBinder.class);
	
	private CopyOfMessageProperties i18n = GWT.create(CopyOfMessageProperties.class); 

	interface DiscoverToolTipUiBinder extends UiBinder<Widget, DiscoverToolTip> {
	}
	
	@UiField Label lblGooruLibrary;
	
	@UiField
	static HTMLPanel panelCode;

	@UiField HTMLEventPanel lblPartnerLibrary, partnerLibContainer, lblDistrictLibrary, districtLibContainer;
	
	@UiField HTMLPanel dropdownImg,tooltipPanel,dropdownImgLib;
	
//	@UiField LibraryStyleBundle libraryStyleUc;
	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public DiscoverToolTip() {
		setWidget(uiBinder.createAndBindUi(this));
		panelCode.getElement().setId("pnlPanelCode");
		lblDistrictLibrary.getElement().setInnerHTML(i18n.GL0515());
		lblDistrictLibrary.getElement().setId("epnlLblDistrictLibrary");
		lblDistrictLibrary.getElement().setAttribute("alt", i18n.GL0515());
		lblDistrictLibrary.getElement().setAttribute("title", i18n.GL0515());
		
		dropdownImg.getElement().setId("pnlDropdownImg");
		dropdownImgLib.getElement().setId("pnlDropdownImgLib");
		
		lblGooruLibrary.setText(i18n.GL0516());
		lblGooruLibrary.getElement().setId("lblLblGooruLibrary");
		lblGooruLibrary.getElement().setAttribute("alt", i18n.GL0516());
		lblGooruLibrary.getElement().setAttribute("title", i18n.GL0516());
		
		lblPartnerLibrary.add(new Label(i18n.GL1751()));
		lblPartnerLibrary.getElement().setId("epnlLblPartnerLibrary");
		
		partnerLibContainer.getElement().addClassName("setVisible");
		partnerLibContainer.getElement().setId("epnlPartnerLibContainer");

		districtLibContainer.getElement().addClassName("setVisible");
		districtLibContainer.getElement().setId("epnlDistrictLibContainer");
		
		lblPartnerLibrary.addMouseOverHandler(new OpenPartnerMenu());
		partnerLibContainer.addMouseOutHandler(new ClosePartnerMenu());
		
		lblGooruLibrary.addMouseOverHandler(new CloseOtherMenus());
		lblDistrictLibrary.addMouseOverHandler(new OpenDistrictMenus());
		
		tooltipPanel.getElement().setId("pnlTooltipPanel");
		
		final HashMap<String,String> publicPartnerList = getPublicLibraryPartners();
		
		for (final Object key : publicPartnerList.keySet()) {
			final Label partnerTitle = new Label(key.toString());
			partnerTitle.addStyleName("courseOption");
			partnerTitle.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					MixpanelUtil.mixpanelEvent("Community_Library_Click_"+publicPartnerList.get(key));
					setHeaderBrowserTitle(partnerTitle.getText());
					AppClientFactory.getPlaceManager().revealPlace(publicPartnerList.get(key));
				}
			});
			districtLibContainer.add(partnerTitle);
		}
		
		getPartners();
				
		this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
			    hide();
			}
		});
        
        lblGooruLibrary.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
			}
		});
        
        
        Boolean isIpad = !!Navigator.getUserAgent().matches("(.*)iPad(.*)");
        Boolean isAndriod = !!Navigator.getUserAgent().matches("(.*)Android(.*)");
		Boolean isWinDskp = !!Navigator.getUserAgent().matches("(.*)NT(.*)");
		  
		UAgentInfo detector = new UAgentInfo(Navigator.getUserAgent());
		  
		if(isIpad && !StringUtil.IPAD_MESSAGE_Close_Click){
			panelCode.getElement().getFirstChildElement().setAttribute("style", "position:relative;margin-top:-53px;");
			districtLibContainer.getElement().setAttribute("style", "margin-top:-4px;");
			partnerLibContainer.getElement().setAttribute("style", "margin-top:20px;");
		}else if(isAndriod && !StringUtil.IPAD_MESSAGE_Close_Click){
			panelCode.getElement().getFirstChildElement().setAttribute("style", "position:relative;margin-top:-53px;");
			districtLibContainer.getElement().setAttribute("style", "margin-top:-4px;");
			partnerLibContainer.getElement().setAttribute("style", "margin-top:20px;");
		}else{
			panelCode.getElement().getFirstChildElement().removeAttribute("style");
			districtLibContainer.getElement().removeAttribute("style");
			partnerLibContainer.getElement().removeAttribute("style");
		}
		
		Window.addWindowScrollHandler(new ScrollHandler() {
			@Override
			public void onWindowScroll(ScrollEvent event) {
				partnerLibContainer.getElement().getStyle().setMarginTop(event.getScrollTop()+50, Unit.PX);
				districtLibContainer.getElement().getStyle().setMarginTop(event.getScrollTop()+23, Unit.PX);
			}
		});
        
	}
	
	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}
	
	public static void onclickOfAndriodorIpadcloseDiv()
	{
		panelCode.getElement().getFirstChildElement().removeAttribute("style");
	}

	private class OpenPartnerMenu implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			
			lblPartnerLibrary.getElement().getStyle().setBackgroundColor("#cfe3f1");
			lblDistrictLibrary.getElement().getStyle().clearBackgroundColor();
			
			partnerLibContainer.getElement().removeClassName("setVisible");
			districtLibContainer.getElement().addClassName("setVisible");
		}
	}

	private class ClosePartnerMenu implements MouseOutHandler {
		@Override
		public void onMouseOut(MouseOutEvent event) {
			lblPartnerLibrary.getElement().getStyle().clearBackgroundColor();
			lblDistrictLibrary.getElement().getStyle().clearBackgroundColor();
			
			partnerLibContainer.getElement().addClassName("setVisible");
			districtLibContainer.getElement().addClassName("setVisible");
		}
	}
	private class OpenDistrictMenus implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			lblPartnerLibrary.getElement().getStyle().clearBackgroundColor();
			lblDistrictLibrary.getElement().getStyle().setBackgroundColor("#cfe3f1");
			
			partnerLibContainer.getElement().addClassName("setVisible");
			districtLibContainer.getElement().removeClassName("setVisible");
		}
	}
	private class CloseOtherMenus implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			lblPartnerLibrary.getElement().getStyle().clearBackgroundColor();
			lblDistrictLibrary.getElement().getStyle().clearBackgroundColor();
			
			partnerLibContainer.getElement().addClassName("setVisible");
			districtLibContainer.getElement().addClassName("setVisible");
		}
	}
	
	public void getPartners() {
		AppClientFactory.getInjector().getLibraryService().getPartners(new SimpleAsyncCallback<ArrayList<LibraryUserDo>>() {
			@Override
			public void onSuccess(ArrayList<LibraryUserDo> partnersList) {
				setPartners(partnersList);
			}
			
		});
	}
	private void setHeaderBrowserTitle(String courseLabel) {
		AppClientFactory.setBrowserWindowTitle(SeoTokens.COURSE_PAGE_TITLE+courseLabel);	
	}
	public void setPartners(ArrayList<LibraryUserDo> partnersList) {
		for(int i=0;i<partnersList.size();i++) {
			final LibraryUserDo libraryUserDo = partnersList.get(i);
			
			final Label partnerTitle = new Label(StringUtil.getPartnerName(libraryUserDo.getUsername()));
			partnerTitle.addStyleName("courseOption");
			partnerTitle.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					setHeaderBrowserTitle(partnerTitle.getText());
					String partnerName = libraryUserDo.getUsername();
					if(partnerName.equalsIgnoreCase("CFCI")) {
						partnerName = PlaceTokens.FINCAPINC;
					}
					AppClientFactory.getPlaceManager().revealPlace(partnerName);
				}
			});
			partnerLibContainer.add(partnerTitle);
		}
	}
	
//	private void getPartnersData() {
//		List<String> partnersData = new ArrayList<String>();
//		partnersData.add("Autodesk® ");
//		partnersData.add("8a6b75b8-0537-492e-8970-c41ade8723a6");
//		partnersData.add("Foundation for Teaching Economics (FTE)");
//		partnersData.add("de182361-8379-4d82-9168-e5bd8b658cff");
//		partnersData.add("SVEF's Lessonopoly");
//		partnersData.add("cd46b323-83d6-44ef-acf1-cef0705623db");
//		partnersData.add("New Global Citizens (NGC)");
//		partnersData.add("bac737f6-4945-4990-b3d6-8c07ec09f9c8");
//		partnersData.add("Office of Naval Research (ONR)");
//		partnersData.add("2e8dd71c-cef6-435d-bfd8-0afad9939b07");
//		partnersData.add("What So Proudly We Hail");
//		partnersData.add("593eeff6-2fa2-487b-941d-67d197e10201");
//		setPartnersData(partnersData);
//	}

	public class RedirectToPartnerPage implements ClickHandler {
		private String folderId;
		public RedirectToPartnerPage(String folderId) {
			this.folderId = folderId;
		}

		@Override
		public void onClick(ClickEvent event) {
			hide();
			Map<String,String> params = new HashMap<String, String>();
			params.put("pid", folderId);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.AUTODESK, params);
		}
	}
	
	private HashMap<String,String> getPublicLibraryPartners() {
		HashMap<String,String> publicPartners = new LinkedHashMap<String,String>();
		publicPartners.put(i18n.GL0515_1(),PlaceTokens.RUSD_LIBRARY);
		publicPartners.put(i18n.GL1898(),PlaceTokens.SAUSD_LIBRARY);
		return publicPartners;
	}
	
}