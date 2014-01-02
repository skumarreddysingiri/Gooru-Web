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
/**
 * 
*/
package org.ednovo.gooru.client.mvp.folders.edit;

import java.util.List;

import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.mvp.folders.edit.tab.info.FolderCBundle;
import org.ednovo.gooru.client.mvp.folders.newfolder.FolderDeleteConfirmationPopUp;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.ShelfCBundle;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionTabTitleVc;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshCollectionInShelfListEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.uc.FolderEditableLabelUc;
import org.ednovo.gooru.client.uc.FolderEditableTextAreaUc;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.model.content.CollectionDo;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
/**
 * @fileName : EditFolderView.java
 *
 * @description : This class is used to set the view for Edit folder.
 *
 * @version : 1.0
 *
 * @date: 30-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class EditFolderView extends BaseViewWithHandlers<EditFolderUiHandlers> implements IsEditFolderView,ClickHandler{
	
	@UiField 
	SimplePanel shelfTabSimPanel;
	
	@UiField
	SimplePanel collectionMetaDataSimPanel;
	
	@UiField
	CollectionTabTitleVc infoTabVc, contentTabVc;

	@UiField
	FocusPanel simplePencilFocPanel;
	
	@UiField
	Label collectionEditImageLbl/*, folderPanelRedirect*/;

	@UiField
	HTMLPanel workspaceFoldersList,info,clearPanel;
	
	@UiField(provided = true)
	FolderEditableLabelUc folderTitle;
	
	@UiField(provided = true)
	FolderEditableTextAreaUc folderDescription;
	
	@UiField HTMLEventPanel folderDeleteLabel,editFolderSaveButton,
	editFolderSaveButtonCancel,editFolderDescSaveButton,editFolderDescSaveButtonCancel,simplePencilPanel,
	collectionDescriptionTitleContainer,editFolderDesc,editFolderTitle,myFolderRightContainer;
	
	@UiField
	Label foldersBreadCrumps,titleAlertMessageLbl,descriptionAlertMessageLbl,collectionDescriptionTitle;
	
	@UiField
	HTML backToSearchPreHtml, backToSearchHtml;
	
	@UiField
	FlowPanel backToSearchFloPanel;
	
	@UiField(provided = true)
	ShelfCBundle res;

	private CollectionDo collectionDo=null;
	
	/*HTML5 Storage implementation for tab persistance*/
	private Storage stockStore = null;
	
	private PlaceRequest searchRequest = null;
	
	private static final String PRE_SEARCH_LINK = "Back To Search \"";
	
	private FolderDeleteConfirmationPopUp folderDeleteConfirmationPopUp;
	
	private static EditFolderViewUiBinder uiBinder = GWT.create(EditFolderViewUiBinder.class);
	interface EditFolderViewUiBinder extends UiBinder<Widget, EditFolderView>{}
	/**
	 * Class constructor.
	 */
	@Inject
	public EditFolderView() {
		this.res = ShelfCBundle.INSTANCE;
		folderDescription = new FolderEditableTextAreaUc() {
			@Override
			public void onEditDisabled(String text) 
			{
				descriptionAlertMessageLbl.addStyleName("titleAlertMessageDeActive");
				descriptionAlertMessageLbl.removeStyleName("titleAlertMessageActive");
				editFolderDescSaveButton.getElement().getStyle().setDisplay(Display.NONE);
				editFolderDescSaveButtonCancel.getElement().getStyle().setDisplay(Display.NONE);
				collectionDo.setGoals(text);
				getUiHandlers().updateCollectionInfo(collectionDo.getGooruOid(), null, text);
			}

			@Override
			public void checkCharacterLimit(String text) 
			{
				if (text.length() >= 415) 
				{
					descriptionAlertMessageLbl.addStyleName("titleAlertMessageActive");
					descriptionAlertMessageLbl.removeStyleName("titleAlertMessageDeActive");
				}
				else 
				{
					descriptionAlertMessageLbl.addStyleName("titleAlertMessageDeActive");
					descriptionAlertMessageLbl.removeStyleName("titleAlertMessageActive");
				}
			}
		};

		folderTitle = new FolderEditableLabelUc() 
		{
			@Override
			public void onEditDisabled(String text) 
			{
				titleAlertMessageLbl.addStyleName("titleAlertMessageDeActive");
				titleAlertMessageLbl.removeStyleName("titleAlertMessageActive");
				collectionDo.setTitle(text);
				editFolderSaveButton.getElement().getStyle().setDisplay(Display.NONE);
				editFolderSaveButtonCancel.getElement().getStyle().setDisplay(Display.NONE);
				getUiHandlers().updateCollectionInfo(collectionDo.getGooruOid(), text, null);
			}

			@Override
			public void checkCharacterLimit(String text) 
			{
				if (text.length() >= 50) 
				{
					titleAlertMessageLbl.addStyleName("titleAlertMessageActive");
					titleAlertMessageLbl.removeStyleName("titleAlertMessageDeActive");
				} 
				else 
				{
					titleAlertMessageLbl.addStyleName("titleAlertMessageDeActive");
					titleAlertMessageLbl.removeStyleName("titleAlertMessageActive");
				}
			}
		};
		FolderCBundle.INSTANCE.css().ensureInjected();
		res.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		
		simplePencilFocPanel.addMouseOverHandler(new hideEditPencil());
		simplePencilFocPanel.addMouseOutHandler(new showEditPencil());
		collectionEditImageLbl.addClickHandler(new OnEditImageClick());
		editFolderTitle.addClickHandler(new OnEditImageClick());
		
		editFolderSaveButton.getElement().getStyle().setDisplay(Display.NONE);
		editFolderSaveButtonCancel.getElement().getStyle().setDisplay(Display.NONE);
		editFolderDescSaveButton.getElement().getStyle().setDisplay(Display.NONE);
		editFolderDescSaveButtonCancel.getElement().getStyle().setDisplay(Display.NONE);
		simplePencilPanel.getElement().getStyle().setDisplay(Display.NONE);
		backToSearchFloPanel.getElement().getStyle().setDisplay(Display.NONE);
		
		collectionDescriptionTitleContainer.addMouseOverHandler(new OnCollectionDescriptionClick());
		collectionDescriptionTitleContainer.addMouseOutHandler(new OnCollectionDescriptionOut());
		simplePencilPanel.addClickHandler(new OpenCollectionEditDescription());
		editFolderDesc.addClickHandler(new OpenCollectionEditDescription());
		
		myFolderRightContainer.addMouseOverHandler(new ActionPanelHover());
		myFolderRightContainer.addMouseOutHandler(new ActionPanelOut());
		
		folderDeleteLabel.setVisible(false);
		infoTabVc.addClickHandler(this);
		contentTabVc.addClickHandler(this);
		backToSearchHtml.addClickHandler(new BackToSearchHtmlClick());
		collectionEditImageLbl.setVisible(false);
	}
	
	/**
	 * 
	 * To show the ResourceMetaDataInfo Edit,Copy and Remove buttons
	 */
	private class ActionPanelHover implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			folderDeleteLabel.setVisible(true);
		}
	}

	/**
	 * 
	 * To hide the ResourceMetaDataInfo Edit,Copy and Remove buttons
	 */
	private class ActionPanelOut implements MouseOutHandler {
		@Override
		public void onMouseOut(MouseOutEvent event) {
			folderDeleteLabel.setVisible(false);
		}
	}
	/**
	 * This inner class is used to handle the click event on the back to search html panel.
	 */
	public class BackToSearchHtmlClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			Object source = event.getSource();
			if (source.equals(backToSearchHtml)) {
				AppClientFactory.getPlaceManager().revealPlace(false, searchRequest);
			}
		}
	}
	/**
	 * This inner class is used to handle the click event on the edit description.
	 */
	public class OpenCollectionEditDescription implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			folderDescription.switchToEdit();
			editFolderDescSaveButton.getElement().getStyle().setDisplay(Display.BLOCK);
			editFolderDescSaveButtonCancel.getElement().getStyle().setDisplay(Display.BLOCK);
		}
	}
	/**
	 * This inner class is used to handle the mouse over  event on the edit description.
	 */
	public class OnCollectionDescriptionClick implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			simplePencilPanel.getElement().getStyle().setDisplay(Display.BLOCK);
		}
	}
	/**
	 * This inner class is used to handle the mouse out event on the edit description.
	 */
	public class OnCollectionDescriptionOut implements MouseOutHandler {
		@Override
		public void onMouseOut(MouseOutEvent event) {
			simplePencilPanel.getElement().getStyle().setDisplay(Display.NONE);
		}
	}
	/**
	 * This inner class is used to handle the mouse over event on the edit pencil to show the pencil.
	 */
	public class hideEditPencil implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			collectionEditImageLbl.setVisible(true);
		}
	}
	/**
	 * This inner class is used to handle the mouse out event on the hide edit to hide the pencil.
	 */
	public class showEditPencil implements MouseOutHandler {
		@Override
		public void onMouseOut(MouseOutEvent event) {
			collectionEditImageLbl.setVisible(false);
		}
	}
	/**
	 * This inner class is used to handle the click event on the edit image button.
	 */
	private class OnEditImageClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			editFolderSaveButton.getElement().getStyle().setDisplay(Display.BLOCK);
			editFolderSaveButtonCancel.getElement().getStyle().setDisplay(Display.BLOCK);
			folderTitle.switchToEdit();
		}
	}
	/**
	 * @function clickedOnEditFolderSaveButton 
	 * 
	 * @created_date : 30-Dec-2013
	 * 
	 * @description : This will handle the click event on the save button.
	 * 
	 * @parm(s) : @param event
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("editFolderSaveButton")
	public void clickedOnEditFolderSaveButton(ClickEvent event)
	{
		titleAlertMessageLbl.setVisible(false);
		folderTitle.switchToLabel();
		//editFolderSaveButton.getElement().getStyle().setDisplay(Display.NONE);
		//editFolderSaveButtonCancel.getElement().getStyle().setDisplay(Display.NONE);
	}
	/**
	 * @function clickedOneditFolderDescSaveButton 
	 * 
	 * @created_date : 30-Dec-2013
	 * 
	 * @description : This will handle  the click event on the folder desc save button.
	 * 
	 * 
	 * @parm(s) : @param event
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("editFolderDescSaveButton")
	public void clickedOneditFolderDescSaveButton(ClickEvent event)
	{
		descriptionAlertMessageLbl.setVisible(false);
		folderDescription.switchToLabel();
		//editFolderDescSaveButton.getElement().getStyle().setDisplay(Display.NONE);
		//editFolderDescSaveButtonCancel.getElement().getStyle().setDisplay(Display.NONE);
	}
	/**
	 * @function clickedOnEditFolderSaveButtonCancelButton 
	 * 
	 * @created_date : 30-Dec-2013
	 * 
	 * @description : This will handle the click event on the folder save and cancel buttons.
	 * 
	 * 
	 * @parm(s) : @param event
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("editFolderSaveButtonCancel")
	public void clickedOnEditFolderSaveButtonCancelButton(ClickEvent event)
	{
		titleAlertMessageLbl.setVisible(false);
		titleAlertMessageLbl.addStyleName("titleAlertMessageDeActive");
		titleAlertMessageLbl.removeStyleName("titleAlertMessageActive");
		folderTitle.switchToTitleCancelLabel();
		editFolderSaveButton.getElement().getStyle().setDisplay(Display.NONE);
		editFolderSaveButtonCancel.getElement().getStyle().setDisplay(Display.NONE);
	}
	/**
	 * @function clickedOnEditFolderDescSaveButtonCancel 
	 * 
	 * @created_date : 30-Dec-2013
	 * 
	 * @description : This will handle the click event on the folder desc save and cancel buttons.
	 * 
	 * 
	 * @parm(s) : @param event
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("editFolderDescSaveButtonCancel")
	public void clickedOnEditFolderDescSaveButtonCancel(ClickEvent event)
	{
		descriptionAlertMessageLbl.setVisible(false);
		descriptionAlertMessageLbl.addStyleName("titleAlertMessageDeActive");
		descriptionAlertMessageLbl.removeStyleName("titleAlertMessageActive");
		folderDescription.switchToDescCancelLabel();
		editFolderDescSaveButton.getElement().getStyle().setDisplay(Display.NONE);
		editFolderDescSaveButtonCancel.getElement().getStyle().setDisplay(Display.NONE);
	}
	/**
	 * This method is sued to set the data.
	 */
	@Override
	public void setData(List<CollectionDo> collectionDoList) {
		
	}
	/**
	 * GWTP will call setInSlot when a child presenter asks to be added under this view. 
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (content != null) {
			if (slot == EditFolderUiHandlers.TYPE_FOLDERS_SHELF_VIEW) {
				shelfTabSimPanel.setWidget(content);
			}
		}
	}
	/**
	 * This method adds some content in a specific slot of the Presenter. 
	 */
	@Override
	public void addToSlot(Object slot, Widget content) {
		if (content != null) {
			if (slot == EditFolderUiHandlers.TYPE_FOLDER_CONTENT_TAB) {
				collectionMetaDataSimPanel.setWidget(content);
			} else if (slot == EditFolderUiHandlers.TYPE_FOLDER_INFO_TAB) {
				collectionMetaDataSimPanel.setWidget(content);
			}
		}
	}
	/**
	 * This method is used to add the folder.
	 */
	@Override
	public void addFolder(CollectionDo collectionDo) {
		
	}
	/**
	 * This method is used to set the folder meta data.
	 */
	@Override
	public void setFolderMetaData(CollectionDo collectionDo) {
		folderTitle.setText(collectionDo.getTitle());
		folderDescription.setText(collectionDo.getGoals());
		foldersBreadCrumps.setText("Folders > "+collectionDo.getTitle());
	}	
	/**
	 * This method is used to set the collection.
	 */
	@Override
	public void setCollection(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
		getUiHandlers().clearTabSlot();
		setTab(getPersistantTabObjectUsingTabFlag());
	}
	/**
	 * @function setTab 
	 * 
	 * @created_date : 30-Dec-2013
	 * 
	 * @description : This method is used to set the tab.
	 * 
	 * 
	 * @parm(s) : @param tab
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public void setTab(Object tab) {
		if (tab.equals(infoTabVc)) {
			setPersistantTabFlag("infoTab");
			infoTabVc.setSelected(true);
			getUiHandlers().revealTab(EditFolderUiHandlers.TYPE_FOLDER_INFO_TAB, collectionDo);
		} else if (tab.equals(contentTabVc)) {
			setPersistantTabFlag("contentTab");
			contentTabVc.setSelected(true);
			getUiHandlers().revealTab(EditFolderUiHandlers.TYPE_FOLDER_CONTENT_TAB, collectionDo);
		} 
	}
	/**
	 * @function setPersistantTabFlag 
	 * 
	 * @created_date : 30-Dec-2013
	 * 
	 * @description : This method is used to set the flag.
	 * 
	 * @parm(s) : @param flag
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public void setPersistantTabFlag(String flag){
		stockStore = Storage.getLocalStorageIfSupported();
		if (stockStore != null) {
		  stockStore.setItem("tabKey", flag);
		}
		
	}
	/**
	 * Lifecycle method called on all visible presenters whenever a
	 * presenter is revealed anywhere in the presenter hierarchy.
	 */
	@Override
	public void reset() {
		super.reset();
		this.collectionDo = null;
		collectionMetaDataSimPanel.clear();
		editFolderDescSaveButton.getElement().getStyle().setDisplay(Display.NONE);
		editFolderDescSaveButtonCancel.getElement().getStyle().setDisplay(Display.NONE);
		editFolderSaveButton.getElement().getStyle().setDisplay(Display.NONE);
		editFolderSaveButtonCancel.getElement().getStyle().setDisplay(Display.NONE);
		folderDescription.switchToDescCancelLabel();
		folderTitle.switchToTitleCancelLabel();
	}
	/**
	 * @function getPersistantTabObjectUsingTabFlag 
	 * 
	 * @created_date : 30-Dec-2013
	 * 
	 * @description : This method is used get the collection tab title.
	 * 
	 * @parm(s) : @return
	 * 
	 * @return : CollectionTabTitleVc
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private CollectionTabTitleVc getPersistantTabObjectUsingTabFlag() {
		CollectionTabTitleVc objectToReturn = contentTabVc;
		stockStore = Storage.getLocalStorageIfSupported();
		String tabFlag = stockStore.getItem("tabKey");
		if (stockStore != null) {
			if (stockStore != null)
			{
				if(tabFlag.equalsIgnoreCase("infoTab"))
				{
					objectToReturn = infoTabVc;
				}
				else if(tabFlag.equalsIgnoreCase("contentTab"))
				{
					objectToReturn = contentTabVc;
				}
			}
			else {
				objectToReturn = contentTabVc;
			}
		}
		return objectToReturn;
	}
	/**
	 * This  will handle the click event.
	 */
	@Override
	public void onClick(ClickEvent event)
	{
		Object source = event.getSource();
		getUiHandlers().clearTabSlot();
		setTab(source);
	}
	/**
	 * This method is used to update the collection.
	 */
	@Override
	public void onPostCollectionUpdate() {
		AppClientFactory.fireEvent(new RefreshCollectionInShelfListEvent(collectionDo, RefreshType.UPDATE));
		
	}
	/**
	 * This method is used to clear the panels.
	 */
	@Override
	public void clearPanels() {
		clearPanel.clear();
		
	}

/*	@UiHandler("folderPanelRedirect")
	public void OnClickFolderPanelRedirect(ClickEvent event) {
			getUiHandlers().initFolderRedirect();
	}

*/	
	/**
	 * This method is used to set data on back to search.
	 */
	@Override
	public void setBackToSearch() {
		boolean visible = false;
		searchRequest = AppClientFactory.getPlaceManager().getPreviousRequest();
		if (searchRequest != null) {
			String query = searchRequest.getParameter("query", null);
			visible = searchRequest != null && query != null;
			backToSearchFloPanel.getElement().getStyle().setDisplay(Display.BLOCK);
			if (visible) {
				if (query.length() > 50) {
					query = query.substring(0, 50) + "...";
					backToSearchHtml.setHTML(PRE_SEARCH_LINK + query + "\"");
				} else {
					backToSearchHtml.setHTML(PRE_SEARCH_LINK + query + "\"");
				}
			}
		}
		backToSearchPreHtml.setVisible(visible);
		backToSearchHtml.setVisible(visible);
	}

	/**
	 * create delete confirmation pop and delete the collection if user wants
	 * 
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("folderDeleteLabel")
	public void deleteCollectionPopup(ClickEvent clickEvent) {
		folderDeleteConfirmationPopUp = new FolderDeleteConfirmationPopUp("Are you sure?", "\""+collectionDo.getTitle()+"\"" +  " Folder.") {

			@Override
			public void onTextConfirmed() {
				getUiHandlers().deleteMyFolder(collectionDo.getGooruOid(), collectionDo);
				folderDeleteConfirmationPopUp.hide();
				Window.enableScrolling(true);
		        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));

//					AppClientFactory.fireEvent(new RefreshCollectionInShelfListEvent(collectionDo, RefreshType.DELETE));
			}
		};
	}


}
