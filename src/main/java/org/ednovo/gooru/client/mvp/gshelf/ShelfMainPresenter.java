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
package org.ednovo.gooru.client.mvp.gshelf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.AppPlaceKeeper;
import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.client.service.ShelfServiceAsync;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.gshelf.courselist.MyCollectionsListPresenter;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;
import org.ednovo.gooru.client.mvp.home.AlmostDoneUc;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.SetFolderMetaDataEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.events.SetFolderParentNameEvent;
import org.ednovo.gooru.client.mvp.shelf.event.GetEditPageHeightEvent;
import org.ednovo.gooru.client.mvp.shelf.event.UpdateResourceCountEvent;
import org.ednovo.gooru.client.mvp.shelf.list.ShelfListView;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 * @author Search Team
 * 
 */
public class ShelfMainPresenter extends BasePlacePresenter<IsShelfMainView, ShelfMainPresenter.IsShelfMainProxy> implements ShelfMainUiHandlers {

	@Inject
	private ShelfServiceAsync shelfService;

	@Inject
	private ResourceServiceAsync resourceService;

	private boolean clrPanel=false;
	
	private String type="Course";
	
	private static final String VIEW= "view";
	
	private static final String FOLDER = "Folder";
	
	public static final  Object RIGHT_SLOT = new Object();
	
	private static final String O1_LEVEL = "o1";
	
	MyCollectionsListPresenter myCollectionsListPresenter;
	
	SignUpPresenter signUpViewPresenter;
	
	private String version = null;
	
	boolean isApiCalled=false;
	
	private SimpleAsyncCallback<FolderListDo> userCollectionAsyncCallback;
	
	private static final String CALLBACK = "callback";
	
	String parentId;
	
	private List<FolderDo> searchResult = new ArrayList<FolderDo>();
	
	@ProxyCodeSplit
	@NameToken(PlaceTokens.MYCONTENT)
	@UseGatekeeper(AppPlaceKeeper.class)
	public interface IsShelfMainProxy extends ProxyPlace<ShelfMainPresenter> {

	}

	/**
	 * class constructor to set all class of instance
	 * 
	 * @param myCollectionsListPresenter
	 *            instance of {@link MyCollectionsListPresenter}
	 * @param signUpPresenter
	 *            instance of {@link SignUpPresenter}
	 * @param view
	 *            {@link View}
	 * @param proxy
	 *            {@link Proxy}
	 */
	@Inject
	public ShelfMainPresenter(SignUpPresenter signUpViewPresenter,MyCollectionsListPresenter myCollectionsListPresenter,IsShelfMainView view, IsShelfMainProxy proxy) {
		super(view, proxy);
		getView().setUiHandlers(this);
		//getView().getLoadingImageVisible();
		this.signUpViewPresenter = signUpViewPresenter;
		this.myCollectionsListPresenter=myCollectionsListPresenter;
		myCollectionsListPresenter.setShelfMainPresenter(this);
		addRegisteredHandler(GetEditPageHeightEvent.TYPE, this);
		addRegisteredHandler(UpdateResourceCountEvent.TYPE, this);
		Document doc = Document.get();
		doc.getBody().setClassName(""); 
		addRegisteredHandler(SetFolderParentNameEvent.TYPE, this);
		addRegisteredHandler(SetFolderMetaDataEvent.TYPE, this);
		myCollectionsListPresenter.getScrollPanel().addScrollHandler(new ScrollHandler() {
			@Override
			public void onScroll(ScrollEvent event) {
				getView().executeScroll(false);
			}
		});
	}
	
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		callBackMethods();
	}

	private void callBackMethods(){
		if (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("signup")) {
			//To show SignUp (Registration popup)
			if (AppClientFactory.isAnonymous()){
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				String type = getPlaceManager().getRequestParameter("type") ;
				int displayScreen =getPlaceManager().getRequestParameter("type") !=null  ? Integer.parseInt(type) : 1;
				signUpViewPresenter.displayPopup(displayScreen);
				addToPopupSlot(signUpViewPresenter);
			}
		}
		int flag = AppClientFactory.getLoggedInUser().getViewFlag();
		final String loginType = AppClientFactory.getLoggedInUser().getLoginType() !=null ? AppClientFactory.getLoggedInUser().getLoginType() : "";
		if(!AppClientFactory.isAnonymous() && flag==0 &&  !loginType.equalsIgnoreCase("Credential")) {
			AlmostDoneUc update = new AlmostDoneUc(AppClientFactory.getLoggedInUser().getEmailId(), AppClientFactory.getLoggedInUser());
			update.setGlassEnabled(true);
			update.show();
			update.center();
		}
	}
	
	
	@Override
	public void onBind() {
		super.onBind();
		Window.enableScrolling(true);
		
	}
	
	@Override
	public void onUnbind(){
		super.onUnbind();
	}
	
	@Override
	protected void onReveal() {
		super.onReveal();
		AppClientFactory.printInfoLogger("OnReveal");
		Window.enableScrolling(true);
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		AppClientFactory.printInfoLogger("OnReset");
		Window.enableScrolling(true);
		if (AppClientFactory.isAnonymous()){
			getView().setNoDataForAnonymousUser(true);
		}else{
			if (version == null || (version != null && !version.equalsIgnoreCase(AppClientFactory.getLoggedInUser().getToken()))) {
				AppClientFactory.printInfoLogger("callAPI");
				callWorkspaceApi();
				version = AppClientFactory.getLoggedInUser().getToken();
			}
			//setRightPanelData(null,null);
		}
		
	}
	/**
	 * This method will call the workspace API
	 */
	public void callWorkspaceApi(){
		getView().setNoDataForAnonymousUser(false);
		String view= AppClientFactory.getPlaceManager().getRequestParameter(VIEW);
		type=view;
		String typeVal=type;
		if(type!=null && type.equalsIgnoreCase(FOLDER)){
			typeVal=null;//if we are passing as null we get all the folders and collections
		}
		getResourceService().getFolderWorkspace((ShelfListView.getpageNumber()-1)*20, 20,null,typeVal,false,getUserCollectionAsyncCallback(true));
		getView().setDefaultOrganizePanel(view);
	}
	public ShelfServiceAsync getShelfService() {
		return shelfService;
	}

	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}
	@Override
	public String getViewToken() {
		return PlaceTokens.MYCONTENT;
	}

	@Override
	public void updateResourceCount(int resourceCount) {
		//getView().updateResoureCount(resourceCount);
		
	}
	
	public void getUserSheldId(){
		if(!AppClientFactory.isAnonymous()){
			String userUid=AppClientFactory.getLoggedInUser().getGooruUId();
			AppClientFactory.getInjector().getResourceService().getUserShelfDetails(userUid, new SimpleAsyncCallback<String>() {
				@Override
				public void onSuccess(String result) {
					AppClientFactory.getPlaceManager().setUserShelfId(result);
				}
			});
		}
	}

	@Override
	public void getEditPageHeight(PopupPanel editQuestionPopupPanel,boolean isHeightClear) {
		
	}
	
	public SimpleAsyncCallback<FolderListDo> getUserCollectionAsyncCallback(boolean clearShelfPanel) {
		clrPanel=clearShelfPanel;
		if (userCollectionAsyncCallback == null) {
			userCollectionAsyncCallback = new SimpleAsyncCallback<FolderListDo>() {
				@Override
				public void onSuccess(FolderListDo result) {
					String o1=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
					if(o1==null){
						if(clrPanel){
							setRightListData(result.getSearchResult(),null);
						}else{
							myCollectionsListPresenter.setData(type,result.getSearchResult(),clrPanel,false,null);
						}
					}
					getView().setUserShelfData(result.getSearchResult(),clrPanel);
				}
			};
		}
		return userCollectionAsyncCallback;
	}
	
	@Override
	public void getChildFolderItems(final String folderId, final boolean isDataCalled) {
		if(isDataCalled) {
			getView().getChildFolderItems(null);
		} else {
			AppClientFactory.getInjector().getfolderService().getChildFolders((getView().getChildPageNumber()-1)*20, 20, folderId,null, null,false,new SimpleAsyncCallback<FolderListDo>() {
				@Override
				public void onSuccess(FolderListDo result) {
					searchResult.addAll(result.getSearchResult());
					if(result.getSearchResult().size()==20) {
						getView().setChildPageNumber(getView().getChildPageNumber()+1);
						setPaginatedChildFolders(folderId, isDataCalled);
					} else {
						getView().setChildPageNumber(1);
						getView().getChildFolderItems(searchResult);
						searchResult.clear();
					}
				}
			});
		}
	}
	@Override
	public void setRightPanelData(FolderDo folderObj,String clickedItemType){
		clearSlot(ShelfMainPresenter.RIGHT_SLOT);
		getMyCollectionsRightClusterPresenter().setTabItems(1, clickedItemType,folderObj);
		setInSlot(ShelfMainPresenter.RIGHT_SLOT, getMyCollectionsRightClusterPresenter());
	}
	
	@Override
	public void setRightListData(List<FolderDo> listOfContent,FolderDo folderDo){
		clearSlot(RIGHT_SLOT);
		String view= AppClientFactory.getPlaceManager().getRequestParameter(VIEW);
		myCollectionsListPresenter.setData(view,listOfContent,clrPanel,false,folderDo);
		setInSlot(RIGHT_SLOT, myCollectionsListPresenter,false);
	}

	private void setPaginatedChildFolders(String folderId, boolean isDataCalled) {
		getChildFolderItems(folderId, isDataCalled);
	}

	@Override
	public void setFolderParentName(String folderName) {
		
	}

	@Override
	public void setFolderMetaData(Map<String, String> folderMetaData) {
		
	}
	@Override
	public void updateLeftShelfPanelActiveStyle() {
		getView().updateLeftShelfPanelActiveStyle();
	}
	
	@Override
	public void setListPresenterBasedOnType(String type) {
		this.type=type;
		version=null;
		Map<String,String> params = new HashMap<String,String>();
		params.put(VIEW, type);
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
		//getResourceService().getFolderWorkspace((ShelfListView.getpageNumber()-1)*20, 20,null,type,false,getUserCollectionAsyncCallback(true));
	}
	
	public MyCollectionsRightClusterPresenter getMyCollectionsRightClusterPresenter() {
		return myCollectionsListPresenter.getMyCollectionsRightClusterPresenter();
	}
	@Override
	public MyCollectionsListPresenter getMyCollectionsListPresenter(){
		return  myCollectionsListPresenter;
	}

	@Override
	public void getMoreListItems(int pageSize, Integer pageNumber, boolean clearShelfPanel) {
		String typeVal=type;
		if(type.equalsIgnoreCase(FOLDER)){
			typeVal=null;//if we are passing as null we get all the folders and collections
		}
		getResourceService().getFolderWorkspace((pageNumber-1)*pageSize,pageSize,null,typeVal,false,getUserCollectionAsyncCallback(clearShelfPanel));		
	}
}
