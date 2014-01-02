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
package org.ednovo.gooru.client.mvp.shelf;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.client.AppPlaceKeeper;
import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.event.ActivateSearchBarEvent;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.gin.BasePlacePresenter;
import org.ednovo.gooru.client.mvp.home.event.HeaderTabType;
import org.ednovo.gooru.client.mvp.home.event.HomeEvent;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;
import org.ednovo.gooru.client.mvp.search.event.ConfirmStatusPopupEvent;
import org.ednovo.gooru.client.mvp.search.event.SetFooterEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.assign.CollectionAssignTabPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.info.CollectionInfoTabPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.CollectionResourceTabPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.item.ShelfCollectionResourceChildView;
import org.ednovo.gooru.client.mvp.shelf.event.CollectionAssignShareEvent;
import org.ednovo.gooru.client.mvp.shelf.event.CollectionAssignShareHandler;
import org.ednovo.gooru.client.mvp.shelf.event.GetEditPageHeightEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshCollectionInShelfListEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshType;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshUserShelfCollectionsEvent;
import org.ednovo.gooru.client.mvp.shelf.event.UpdateResourceCountEvent;
import org.ednovo.gooru.client.mvp.shelf.list.ShelfListPresenter;
import org.ednovo.gooru.client.service.ResourceServiceAsync;
import org.ednovo.gooru.client.service.ShelfServiceAsync;
import org.ednovo.gooru.shared.model.content.CollectionDo;
import org.ednovo.gooru.shared.model.content.PermissionsDO;
import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

/**
 * 
 * @fileName : ShelfPresenter.java
 *
 * @description : This is the presenter class for shelfview.java
 *
 *
 * @version : 1.0
 *
 * @date: 02-Jan-2014
 *
 * @Author : Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class ShelfPresenter extends BasePlacePresenter<IsShelfView, ShelfPresenter.IsShelfProxy> implements ShelfUiHandlers {

	@Inject
	private ShelfServiceAsync shelfService;

	@Inject
	private ResourceServiceAsync resourceService;

	private ShelfListPresenter shelfListPresenter;

	private CollectionResourceTabPresenter collectionResourceTabPresenter;

	private CollectionInfoTabPresenter collectionInfoTabPresenter;
	
	private CollectionAssignTabPresenter collectionAssignTabPresenter;

	private SimpleAsyncCallback<CollectionDo> collectionAsyncCallback;

	private SimpleAsyncCallback<CollectionDo> copyCollectionAsyncCallback;
	
	private SimpleAsyncCallback<PermissionsDO> permissionsAsyncCallback;

	private SimpleAsyncCallback<Void> deleteCollectionAsyncCallback;

	private ImageUploadPresenter imageUploadPresenter;

	private SimpleAsyncCallback<CollectionDo> updateCollectionAsyncCallback;

	private HandlerRegistration viewClickRegistration;

	private CollectionDo collectionDo;

	@ProxyCodeSplit
	@NameToken(PlaceTokens.SHELF)
	@UseGatekeeper(AppPlaceKeeper.class)
	public interface IsShelfProxy extends ProxyPlace<ShelfPresenter> {

	}

	/**
	 * class constructor to set all class of instance
	 * 
	 * @param imageUploadPresenter
	 *            instance of {@link ImageUploadPresenter}
	 * @param shelfTabPresenter
	 *            instance of {@link ShelfListPresenter}
	 * @param collectionResourceTabPresenter
	 *            instance of {@link CollectionResourceTabPresenter}
	 * @param collectionInfoTabPresenter
	 *            instance {@link CollectionInfoTabPresenter}
	 * @param collectionAssignTabPresenter
	 *            instance {@link CollectionAssignTabPresenter}
	 * @param view
	 *            {@link View}
	 * @param proxy
	 *            {@link Proxy}
	 */
	@Inject
	public ShelfPresenter(ImageUploadPresenter imageUploadPresenter, ShelfListPresenter shelfTabPresenter, CollectionResourceTabPresenter collectionResourceTabPresenter, CollectionInfoTabPresenter collectionInfoTabPresenter, CollectionAssignTabPresenter collectionAssignTabPresenter, IsShelfView view, IsShelfProxy proxy) {
		super(view, proxy);
		getView().setUiHandlers(this);
		getView().getLoadingImageVisible();
		this.shelfListPresenter = shelfTabPresenter;
		this.collectionResourceTabPresenter = collectionResourceTabPresenter;
		this.collectionInfoTabPresenter = collectionInfoTabPresenter;
		this.collectionAssignTabPresenter = collectionAssignTabPresenter;
		this.imageUploadPresenter = imageUploadPresenter;
		addRegisteredHandler(GetEditPageHeightEvent.TYPE, this);
		addRegisteredHandler(UpdateResourceCountEvent.TYPE, this);
		
		CollectionAssignShareHandler handler = new CollectionAssignShareHandler() {
			
			@Override
			public void updateShareType(String shareType) {
				collectionDo.setSharing(shareType);
			}
		};
		
		addRegisteredHandler(CollectionAssignShareEvent.TYPE, handler);
	}
	/**
	 * This method is used to read url parameters.
	 */
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
	}
	/**
	 * This method is called when the presenter is instantiated.
	 */
	@Override
	public void onBind() {
		super.onBind();
		setCollectionAsyncCallback(new SimpleAsyncCallback<CollectionDo>() {

			@Override
			public void onSuccess(CollectionDo collection) {
				if (collection.getMeta().getPermissions().toString().contains("edit")){
					getView().setCollection(collection);
					fireEvent(new RefreshCollectionInShelfListEvent(collection, RefreshType.OPEN));
					getView().getLoadingImageInvisible();
				}else{
					ErrorPopup errorPopup = new ErrorPopup(MessageProperties.GL0340);
					errorPopup.show();
					errorPopup.center();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				AppClientFactory.fireEvent(new RefreshCollectionInShelfListEvent(null, RefreshType.OPEN));
			}
		});
	}
	
	@Override
	public void onUnbind(){
		super.onUnbind();
	}
	/**
	 * This method is called whenever the Presenter was not visible on screen and becomes visible.
	 */
	@Override
	protected void onReveal() {
		Window.scrollTo(0, 0);
		viewClickRegistration = RootPanel.get().addDomHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				ShelfCollectionResourceChildView.checkEditState();
				
			}
		}, ClickEvent.getType());
		super.onReveal();
		if(AppClientFactory.isAnonymous())
		{	
			Map<String, String> params = new HashMap<String, String>();
			params.put("loginEvent","true");
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME, params);
		} else {
			getView().setBackToSearch();
			AppClientFactory.setBrowserWindowTitle(SeoTokens.WORKSPACE_TITLE);
			AppClientFactory.setMetaDataDescription(SeoTokens.HOME_META_DESCRIPTION);
			fireEvent(new ActivateSearchBarEvent(true));
			AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.ORGANIZE));
			String id = getPlaceManager().getRequestParameter("id", "INVALID");
			if(id.equalsIgnoreCase("INVALID") && !AppClientFactory.isAnonymous())
			{
				getView().setNoDataCollection();
				Window.enableScrolling(true);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
			}
			getView().setBalloonPopup();
			Document doc = Document.get();
			doc.getElementById("uvTab").getStyle().setDisplay(Display.BLOCK);
			AppClientFactory.fireEvent(new SetFooterEvent(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken()));
			//Call Event for Setting Confirm popup
			AppClientFactory.fireEvent(new ConfirmStatusPopupEvent(true));
		}
	}
	/**
	 * This method is called whenever the user navigates to a page that shows the presenter, whether it was visible or not.
	 */
	@Override
	protected void onReset() {
		super.onReset();
		if(!AppClientFactory.isAnonymous()) {
			String id = getPlaceManager().getRequestParameter("id", "INVALID");
			if (!id.equalsIgnoreCase("INVALID") && AppClientFactory.isAnonymous()) {
				AppClientFactory.getPlaceManager().redirectPlace(PlaceTokens.SHELF);
			} else if (AppClientFactory.getPlaceManager().refreshPlace()) {
				String eventType = getPlaceManager().getRequestParameter("eventType");
				if(eventType!=null) {
					AppClientFactory.fireEvent(new RefreshUserShelfCollectionsEvent());
				}
				if (!id.equalsIgnoreCase("INVALID") && !id.equalsIgnoreCase("")) {
					getView().getLoadingImageVisible();
//					this.getResourceService().getPermissions(id,getPermissionsAsyncCallback());
					getResourceService().getCollection(id, false,
							getCollectionAsyncCallback());
				}else{
					getView().setNoDataCollection();
					Window.enableScrolling(true);
					AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
				}
				collectionInfoTabPresenter.getView().reset();
				collectionResourceTabPresenter.getView().reset();
				collectionAssignTabPresenter.getView().reset();
				setInSlot(TYPE_SHELF_TAB, shelfListPresenter);
			}
			AppClientFactory.fireEvent(new SetFooterEvent(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken()));
		}
	}
	/**
	 * This method is to close the opened popup's.
	 */
	@Override
	protected void onHide() {
		super.onHide();
		viewClickRegistration.removeHandler();
		getView().hideAllOpenedPopUp();
		imageUploadPresenter.getView().closeImageUploadWidget();
//		collectionResourceTabPresenter.closePopUp();
		
	}
	/**
	 * This method is used to delete the collections.
	 */
	@Override
	public void deleteCollection(String collectionId) {
		this.getResourceService().deleteCollection(collectionId, getDeleteCollectionAsyncCallback());
	}
	/**
	 * 
	 * @function getShelfService 
	 * 
	 * @created_date : 02-Jan-2014
	 * 
	 * @description :returns shelfService. 
	 * 
	 * 
	 * @parm(s) : @return
	 * 
	 * @return : ShelfServiceAsync
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public ShelfServiceAsync getShelfService() {
		return shelfService;
	}
	/**
	 * 
	 * @function getResourceService 
	 * 
	 * @created_date : 02-Jan-2014
	 * 
	 * @description :returns resourceService.
	 * 
	 * 
	 * @parm(s) : @return
	 * 
	 * @return : ResourceServiceAsync
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}
	/**
	 * 
	 * @function setResourceService 
	 * 
	 * @created_date : 02-Jan-2014
	 * 
	 * @description : To set resourceService.
	 * 
	 * 
	 * @parm(s) : @param resourceService
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}
	/**
	 * 
	 * @function getCollectionAsyncCallback 
	 * 
	 * @created_date : 02-Jan-2014
	 * 
	 * @description : returns collectionAsyncCallback.
	 * 
	 * 
	 * @parm(s) : @return
	 * 
	 * @return : SimpleAsyncCallback<CollectionDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public SimpleAsyncCallback<CollectionDo> getCollectionAsyncCallback() {
		return collectionAsyncCallback;
	}
	/**
	 * @return instance of {@link CollectionDo}
	 */
	public SimpleAsyncCallback<CollectionDo> getCopyCollectionAsyncCallback() {
		if (copyCollectionAsyncCallback == null) {
			copyCollectionAsyncCallback = new SimpleAsyncCallback<CollectionDo>() {

				@Override
				public void onSuccess(CollectionDo result) {
					AppClientFactory.fireEvent(new RefreshCollectionInShelfListEvent(result, RefreshType.INSERT_AND_VIEW));
					getView().editCopyCollectionTitle();
				}
			};
		}
		return copyCollectionAsyncCallback;
	}
		/**
		 * 
		 * @function getPermissionsAsyncCallback 
		 * 
		 * @created_date : 02-Jan-2014
		 * 
		 * @description : Returns permissionsAsyncCallback.
		 * 
		 * 
		 * @parm(s) : @return
		 * 
		 * @return : SimpleAsyncCallback<PermissionsDO>
		 *
		 * @throws : <Mentioned if any exceptions>
		 *
		 * 
		 *
		 *
		 */
	public SimpleAsyncCallback<PermissionsDO> getPermissionsAsyncCallback() {
		if (permissionsAsyncCallback == null) {
			permissionsAsyncCallback = new SimpleAsyncCallback<PermissionsDO>() {

				@Override
				public void onSuccess(PermissionsDO result) {
					if (result.getPermissions().size() != 0) {
						getView().getLoadingImageVisible();
						String Values = result.getPermissions().toString();
//						for (int i = 0; i < result.getPermissions().size(); i++) {
							if (Values.contains("edit")) {
								String id = getPlaceManager()
										.getRequestParameter("id", "INVALID");
								getResourceService().getCollection(id, false,
										getCollectionAsyncCallback());
							} else {
								ErrorPopup errorPopup = new ErrorPopup(MessageProperties.GL0340);
								errorPopup.show();
								errorPopup.center();
							}
//						}
					} else {
						ErrorPopup errorPopup = new ErrorPopup(MessageProperties.GL0340);
						errorPopup.show();
						errorPopup.center();
					}
					//AppClientFactory.fireEvent(new RefreshCollectionInShelfListEvent(result, RefreshType.INSERT_AND_VIEW));
				}
			};
		}
		return permissionsAsyncCallback;
	}
	/**
	 * @param collectionAsyncCallback
	 *            instance of {@link CollectionDo}
	 */
	public void setCollectionAsyncCallback(SimpleAsyncCallback<CollectionDo> collectionAsyncCallback) {
		this.collectionAsyncCallback = collectionAsyncCallback;
	}
	/**
	 * 
	 * @function getDeleteCollectionAsyncCallback 
	 * 
	 * @created_date : 02-Jan-2014
	 * 
	 * @description :returns deleteCollectionAsyncCallback.
	 * 
	 * 
	 * @parm(s) : @return
	 * 
	 * @return : SimpleAsyncCallback<Void>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public SimpleAsyncCallback<Void> getDeleteCollectionAsyncCallback() {
		if (deleteCollectionAsyncCallback == null) {
			deleteCollectionAsyncCallback = new SimpleAsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					getView().onPostCollectionDelete();
				}
			};
		}
		return deleteCollectionAsyncCallback;
	}

	/**
	 * @return instance of {@link CollectionDo}
	 */
	public SimpleAsyncCallback<CollectionDo> getUpdateCollectionAsyncCallback() {
		if (updateCollectionAsyncCallback == null) {
			updateCollectionAsyncCallback = new SimpleAsyncCallback<CollectionDo>() {

				@Override
				public void onSuccess(CollectionDo result) {
					getView().onPostCollectionUpdate();
				}
			};
		}
		return updateCollectionAsyncCallback;
	}
	/**
	 * To get the place token.
	 */
	@Override
	public String getViewToken() {
		return PlaceTokens.SHELF;
	}
	/**
	 * This method will set the data to perticular presenter base on collection types.
	 */
	@Override
	public void revealTab(Type<RevealContentHandler<?>> tabType, CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
		if (tabType.equals(TYPE_COLLECTION_RESOURCE_TAB)) {
			addToSlot(TYPE_COLLECTION_RESOURCE_TAB, collectionResourceTabPresenter);
			collectionResourceTabPresenter.getView().setData(collectionDo);
		} else if (tabType.equals(TYPE_COLLECTION_INFO_TAB)) {
			addToSlot(TYPE_COLLECTION_INFO_TAB, collectionInfoTabPresenter);
			collectionInfoTabPresenter.getView().setData(collectionDo);
		}else if(tabType.equals(TYPE_ASSIGN_INFO_TAB)){
			addToSlot(TYPE_ASSIGN_INFO_TAB, collectionAssignTabPresenter);
			collectionAssignTabPresenter.getClasspage(collectionDo, collectionDo.getSharing());
		}
	}
	/**
	 * This method is used to clear the tab slots.
	 */
	@Override
	public void clearTabSlot() {
		clearSlot(TYPE_COLLECTION_RESOURCE_TAB);
		clearSlot(TYPE_COLLECTION_INFO_TAB);
		clearSlot(TYPE_ASSIGN_INFO_TAB);
	}
	/**
	 * This method is used to add imageUploadPresenter to addToPopUPSlot.
	 */
	@Override
	public void imageUpload() {
		addToPopupSlot(imageUploadPresenter);
	}
	/**
	 * This is used to update collection information.
	 */
	@Override
	public void updateCollectionInfo(String collectionId, String title, String description) {
		getResourceService().updateCollectionMetadata(collectionId, title, description, null, null, null, null, null, null, null, getUpdateCollectionAsyncCallback());
	}
	/**
	 * This method is used to copy the Collections.
	 */
	@Override
	public void copyCollection(String collectionUid) {
		CollectionDo collection = new CollectionDo();
		collection.setGooruOid(collectionUid);
		getResourceService().copyCollection(collection, "true", null, getCopyCollectionAsyncCallback());
	}
	/**
	 * This method is used to get the edit page height.
	 */
	public  void getEditPageHeight(PopupPanel editQuestionPopupPanel,boolean isHeightClear){
		
        int height=1230+editQuestionPopupPanel.getAbsoluteTop();
        int containerHeight=getView().getShelfViewMainContainer().getOffsetHeight();
        containerHeight=containerHeight+getView().getShelfViewMainContainer().getAbsoluteTop();
        if(isHeightClear){
                getView().getShelfViewMainContainer().getElement().getStyle().clearHeight();
        }else{
                if(height >= containerHeight){                
                        int adjustableHeight=height-containerHeight;
                        getView().getShelfViewMainContainer().setHeight((containerHeight+adjustableHeight)+"px");
                        increaseGlassHeight(editQuestionPopupPanel.getElement().getPreviousSiblingElement());
                }else{
                	
                }
        }
        
}
	/**
	 * 
	 * @function increaseGlassHeight 
	 * 
	 * @created_date : 02-Jan-2014
	 * 
	 * @description :This method is used to increse the popup glass height.
	 * 
	 * 
	 * @parm(s) : @param glass
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
public void increaseGlassHeight(Element glass){
        
         Style style = glass.getStyle();

      int winWidth = Window.getClientWidth();
      int winHeight = Window.getClientHeight();

      // Hide the glass while checking the document size. Otherwise it would
      // interfere with the measurement.
      style.setDisplay(Display.NONE);
      style.setWidth(0, Unit.PX);
      style.setHeight(0, Unit.PX);

      int width = Document.get().getScrollWidth();
      int height = Document.get().getScrollHeight();

      // Set the glass size to the larger of the window's client size or the
      // document's scroll size.
      style.setWidth(Math.max(width, winWidth), Unit.PX);
      style.setHeight(Math.max(height, winHeight), Unit.PX);

      // The size is set. Show the glass again.
      style.setDisplay(Display.BLOCK);
}
	/**
	 * This method is used to update the resource count.
	 */
@Override
public void updateResourceCount(int resourceCount) {
	getView().updateResoureCount(resourceCount);
	
}
	
	
}
