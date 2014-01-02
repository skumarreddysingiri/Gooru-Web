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
package org.ednovo.gooru.client.mvp.search;

import org.ednovo.gooru.client.SearchAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.search.event.SearchEvent;
import org.ednovo.gooru.client.service.SearchServiceAsync;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppSuggestBox;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.model.search.AutoSuggestKeywordSearchDo;
import org.ednovo.gooru.shared.model.search.SearchDo;
import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * 
 * @fileName : SearchBarVc.java
 *
 * @description : To show the auto suggestion list in search.
 *
 *
 * @version : 1.0
 *
 * @date: 31-Dec-2013
 *
 * @Author : Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class SearchBarVc extends Composite implements SelectionHandler<SuggestOracle.Suggestion>{
	
	private SearchDo<AutoSuggestKeywordSearchDo> autoSuggestKeywordDo = new SearchDo<AutoSuggestKeywordSearchDo>();
	private SearchAsyncCallback<SearchDo<AutoSuggestKeywordSearchDo>> autoKeyWordSuggestionAsyncCallback;

	private String initialSearchQuery = "";
	private final String QUERY = "query";
	private static SearchBarVcUiBinder uiBinder = GWT.create(SearchBarVcUiBinder.class);
	interface SearchBarVcUiBinder extends UiBinder<Widget, SearchBarVc> {
	}

	@UiField(provided=true)
	AppSuggestBox searchTxtBox;
	@UiField Button searchBtn;
		private AppMultiWordSuggestOracle autokeySuggestOracle;
		String searchData="";
		private String GOORU_SEARCH=" -<n> Gooru Search</n>";
	/**
	 * Class constructor, crates new {@link KeyUpHandler}
	 */
	public SearchBarVc() {
		autokeySuggestOracle = new AppMultiWordSuggestOracle(true);
		searchTxtBox=new AppSuggestBox(autokeySuggestOracle){

			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				return null;
			}

			@Override
			public void keyAction(String text) {
				MixpanelUtil.Search_autocomplete_select();
				autokeySuggestOracle.clear();
				autoSuggestKeywordDo.setQuery(text);
				searchData=searchTxtBox.getValue();
				if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().contains("resource-search"))
				{
					autoSuggestKeywordDo.setType("");
					autoSuggestKeywordDo.setType("resource");	
				}
				else if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().contains("collection-search"))
				{
					autoSuggestKeywordDo.setType("");
					autoSuggestKeywordDo.setType("collection");
				}
				if (text != null && text.trim().length() > 0) {
					requestAutoSuggestKeyword(autoSuggestKeywordDo);
				}
				else
				{
					searchTxtBox.hideSuggestionList();	
				}
				
			}
		};
		searchTxtBox.addSelectionHandler(this);
		searchTxtBox.setPopupStyleName("SearchVcTextBox");
		initWidget(uiBinder.createAndBindUi(this));
//		searchTxtBox.getElement().setAttribute("maxlength","50");
		searchTxtBox.addKeyUpHandler(new SearchKeyUpHandler());
		
		initialSearchQuery = AppClientFactory.getPlaceManager().getRequestParameter(QUERY);
		searchTxtBox.getTextBox().getElement().setAttribute("placeholder", MessageProperties.GL0448);
		Cookies.setCookie("searchvalue", initialSearchQuery);
		searchTxtBox.getElement().setId("txtSearch");
		searchBtn.getElement().setId("btnSearch");
	
	}
	/**
	 * 
	 * @fileName : SearchBarVc.java
	 *
	 * @description : Search Keyup Handler.
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 31-Dec-2013
	 *
	 * @Author : Gooru Team
	 *
	 * @Reviewer: Gooru Team
	 */
	private class SearchKeyUpHandler implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				if (searchTxtBox.getText() != null && searchTxtBox.getText().length() > 0) {
					MixpanelUtil.Perform_Search(searchTxtBox.getText().trim().toLowerCase(),"SearchBarVc");
					AppClientFactory.fireEvent(new SearchEvent(getSearchText()));
					searchTxtBox.hideSuggestionList();
				}
			}
		}
	}

	/**
	 * Call search event
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("searchBtn")
	public void searchBtnClicked(ClickEvent clickEvent) {
			
		if (searchTxtBox.getText() != null && searchTxtBox.getText().length() > 0) {
			MixpanelUtil.Perform_Search(searchTxtBox.getText().trim().toLowerCase(),"SearchBarVc");
			AppClientFactory.fireEvent(new SearchEvent(getSearchText()));
			searchTxtBox.hideSuggestionList();
		}
	}

	/**
	 * Set search text to textbox
	 * @param text which is to be set as input text
	 */
	public void setSearchText(String text) {
		searchTxtBox.setText(text);
	}

	/**
	 * Get enter search text by user
	 * @return search Text if search text box has any value else null 
	 */
	public String getSearchText() {
		String searchText = searchTxtBox.getText();
		Cookies.setCookie("searchvalue", initialSearchQuery);
		if (searchText != null && searchText.length() > 0) {
			initialSearchQuery =searchText;
			return searchText;
		} else {
			return null;
		}

	}
	/**
	 * 
	 * @function setInitialSearchQuery 
	 * 
	 * @created_date : 31-Dec-2013
	 * 
	 * @description :This is used to set initial search query.
	 * 
	 * 
	 * @parm(s) : 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public void setInitialSearchQuery(){
		if(!AppClientFactory.getPlaceManager().getRequestParameter(QUERY).equals("")){
		initialSearchQuery = AppClientFactory.getPlaceManager().getRequestParameter(QUERY);
		}	
		searchTxtBox.setText(searchTxtBox.getText());
		if(searchTxtBox.getText().equals("")){
			searchTxtBox.setText(initialSearchQuery);
		}
		
	}
	/**
	 * @return suggestion standards for the collection as map string
	 */
	public SearchAsyncCallback<SearchDo<AutoSuggestKeywordSearchDo>> getAutoSuggestionKeyWordAsyncCallback() {
		if (autoKeyWordSuggestionAsyncCallback == null) {
			autoKeyWordSuggestionAsyncCallback = new SearchAsyncCallback<SearchDo<AutoSuggestKeywordSearchDo>>() {

				@Override
				protected void run(SearchDo<AutoSuggestKeywordSearchDo> searchDo) {
					//getSearchService().getSuggestedAutokeyword(searchDo, this);
					AppClientFactory.getInjector().getSearchService().getSuggestedAutokeyword(searchDo, this);
					
				}

				@Override
				public void onCallSuccess(
					SearchDo<AutoSuggestKeywordSearchDo> result) {
					setAutoKeyWordSuggestions(result);
					
					
				}

				
			};
		}
		return autoKeyWordSuggestionAsyncCallback;
	}
	/**
	 * 
	 * @function requestAutoSuggestKeyword 
	 * 
	 * @created_date : 31-Dec-2013
	 * 
	 * @description :This is used to get AutoSuggestion KeyWord AsyncCallback.
	 * 
	 * 
	 * @parm(s) : @param searchDo
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public void requestAutoSuggestKeyword(
			SearchDo<AutoSuggestKeywordSearchDo> searchDo) {
			getAutoSuggestionKeyWordAsyncCallback().execute(searchDo);
	}
	/**
	 * 
	 * @function setAutoKeyWordSuggestions 
	 * 
	 * @created_date : 31-Dec-2013
	 * 
	 * @description :To show the suggestion list.
	 * 
	 * 
	 * @parm(s) : @param autoSuggestKeywordDo
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public void setAutoKeyWordSuggestions(SearchDo<AutoSuggestKeywordSearchDo> autoSuggestKeywordDo)
	{
			autokeySuggestOracle.clear();
			this.autoSuggestKeywordDo = autoSuggestKeywordDo;
			searchData=searchData+GOORU_SEARCH;
			autokeySuggestOracle.add(searchData);
			if (this.autoSuggestKeywordDo.getSearchResults() != null) {
				for (AutoSuggestKeywordSearchDo autoSuggestKeywordSearchDo : autoSuggestKeywordDo.getSearchResults()) {
					autokeySuggestOracle.add(autoSuggestKeywordSearchDo.getKeyword());
				}
			}
			searchTxtBox.showSuggestionList();
			
		}
	/**
	 * This method is used to fire search event.
	 */
	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		String searchText = searchTxtBox.getText();
		searchText= searchText.replaceAll("-<n> Gooru Search</n>", "");
		searchTxtBox.setText(searchText.trim());
		if (searchTxtBox.getText() != null && searchTxtBox.getText().length() > 0) {
			MixpanelUtil.Perform_Search(searchTxtBox.getText().trim().toLowerCase(),"SearchBarVc");
			AppClientFactory.fireEvent(new SearchEvent(getSearchText()));
			searchTxtBox.hideSuggestionList();
		}
		
	}	
	

}
