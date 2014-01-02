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
package org.ednovo.gooru.client.gin;

import org.ednovo.gooru.client.AppPlaceKeeper;
import org.ednovo.gooru.client.AppRootPresenter;
import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.mvp.authentication.IsSignUpView;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.authentication.SignUpView;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.IsAfterThirteen;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.IsSignUpCompleteProfile;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpAfterThirteenPresenter;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpCompleteProfilePresenter;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpCompleteProfileView;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpTurnsAfterThirteenView;
import org.ednovo.gooru.client.mvp.classpages.ClasspagePresenter;
import org.ednovo.gooru.client.mvp.classpages.ClasspagePresenter.IsClasspageProxy;
import org.ednovo.gooru.client.mvp.classpages.ClasspageView;
import org.ednovo.gooru.client.mvp.classpages.IsClasspageView;
import org.ednovo.gooru.client.mvp.classpages.assignments.AddAssignmentContainerPresenter;
import org.ednovo.gooru.client.mvp.classpages.assignments.AddAssignmentContainerView;
import org.ednovo.gooru.client.mvp.classpages.assignments.IsAddAssignmentContainerView;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspagePresenter;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspagePresenter.IsEditClasspageProxy;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspageView;
import org.ednovo.gooru.client.mvp.classpages.edit.IsEditClasspageView;
import org.ednovo.gooru.client.mvp.classpages.studentView.IsStudentAssignmentView;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentPresenter;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentPresenter.IsStudentAssignmentProxy;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentView;
import org.ednovo.gooru.client.mvp.classpages.study.ClassCodePresenter;
import org.ednovo.gooru.client.mvp.classpages.study.ClassCodePresenter.IsClassCodeProxy;
import org.ednovo.gooru.client.mvp.classpages.study.ClassCodeView;
import org.ednovo.gooru.client.mvp.classpages.study.IsClassCodeView;
import org.ednovo.gooru.client.mvp.devicesupport.DeviceSupportPresenter;
import org.ednovo.gooru.client.mvp.devicesupport.DeviceSupportPresenter.IsDeviceSupportProxy;
import org.ednovo.gooru.client.mvp.devicesupport.DeviceSupportView;
import org.ednovo.gooru.client.mvp.devicesupport.IsDeviceSupportView;
import org.ednovo.gooru.client.mvp.error.ErrorPresenter;
import org.ednovo.gooru.client.mvp.error.ErrorPresenter.IsErrorProxy;
import org.ednovo.gooru.client.mvp.error.ErrorView;
import org.ednovo.gooru.client.mvp.error.IsErrorView;
import org.ednovo.gooru.client.mvp.folders.FoldersPresenter;
import org.ednovo.gooru.client.mvp.folders.FoldersPresenter.IsFoldersProxy;
import org.ednovo.gooru.client.mvp.folders.FoldersView;
import org.ednovo.gooru.client.mvp.folders.IsFoldersView;
import org.ednovo.gooru.client.mvp.folders.edit.EditFolderPresenter;
import org.ednovo.gooru.client.mvp.folders.edit.EditFolderPresenter.IsEditFolderProxy;
import org.ednovo.gooru.client.mvp.folders.edit.EditFolderView;
import org.ednovo.gooru.client.mvp.folders.edit.IsEditFolderView;
import org.ednovo.gooru.client.mvp.folders.edit.tab.content.FolderContentTabPresenter;
import org.ednovo.gooru.client.mvp.folders.edit.tab.content.FolderContentTabView;
import org.ednovo.gooru.client.mvp.folders.edit.tab.content.IsFolderContentTabView;
import org.ednovo.gooru.client.mvp.folders.edit.tab.info.FolderInfoTabPresenter;
import org.ednovo.gooru.client.mvp.folders.edit.tab.info.FolderInfoTabView;
import org.ednovo.gooru.client.mvp.folders.edit.tab.info.IsFolderInfoTabView;
import org.ednovo.gooru.client.mvp.folders.newfolder.FolderFormViewPresenter;
import org.ednovo.gooru.client.mvp.folders.newfolder.FolderFormViewPresenter.IsFolderPopUpFormProxy;
import org.ednovo.gooru.client.mvp.folders.newfolder.FolderPopUpUiBinder;
import org.ednovo.gooru.client.mvp.folders.newfolder.IsFoldersPopupView;
import org.ednovo.gooru.client.mvp.home.HomePresenter;
import org.ednovo.gooru.client.mvp.home.HomePresenter.IsHomeProxy;
import org.ednovo.gooru.client.mvp.home.HomeView;
import org.ednovo.gooru.client.mvp.home.IsHomeView;
import org.ednovo.gooru.client.mvp.home.register.IsUserRegistrationView;
import org.ednovo.gooru.client.mvp.home.register.UserRegistrationPresenter;
import org.ednovo.gooru.client.mvp.home.register.UserRegistrationView;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadView;
import org.ednovo.gooru.client.mvp.image.upload.IsImageUploadView;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerPresenter.IsCollectionPlayerProxy;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerView;
import org.ednovo.gooru.client.mvp.play.collection.IsCollectionPlayerView;
import org.ednovo.gooru.client.mvp.play.collection.add.AddCollectionPresenter;
import org.ednovo.gooru.client.mvp.play.collection.add.AddCollectionView;
import org.ednovo.gooru.client.mvp.play.collection.add.IsAddCollectionView;
import org.ednovo.gooru.client.mvp.play.collection.body.CollectionPlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.collection.body.CollectionPlayerMetadataView;
import org.ednovo.gooru.client.mvp.play.collection.body.IsCollectionPlayerMetadataView;
import org.ednovo.gooru.client.mvp.play.collection.end.CollectionEndPresenter;
import org.ednovo.gooru.client.mvp.play.collection.end.CollectionEndView;
import org.ednovo.gooru.client.mvp.play.collection.end.IsCollectionEndView;
import org.ednovo.gooru.client.mvp.play.collection.info.IsResourceInfoView;
import org.ednovo.gooru.client.mvp.play.collection.info.ResourceInfoPresenter;
import org.ednovo.gooru.client.mvp.play.collection.info.ResourceInfoView;
import org.ednovo.gooru.client.mvp.play.collection.share.CollectionSharePresenter;
import org.ednovo.gooru.client.mvp.play.collection.share.CollectionShareView;
import org.ednovo.gooru.client.mvp.play.collection.share.IsCollectionShareView;
import org.ednovo.gooru.client.mvp.play.collection.toc.CollectionPlayerTocPresenter;
import org.ednovo.gooru.client.mvp.play.collection.toc.CollectionPlayerTocView;
import org.ednovo.gooru.client.mvp.play.collection.toc.IsCollectionPlayerTocView;
import org.ednovo.gooru.client.mvp.play.resource.IsResourcePlayerView;
import org.ednovo.gooru.client.mvp.play.resource.ResourcePlayerPresenter;
import org.ednovo.gooru.client.mvp.play.resource.ResourcePlayerPresenter.IsResourcePlayerProxy;
import org.ednovo.gooru.client.mvp.play.resource.ResourcePlayerView;
import org.ednovo.gooru.client.mvp.play.resource.add.AddResourceCollectionPresenter;
import org.ednovo.gooru.client.mvp.play.resource.add.AddResourceCollectionView;
import org.ednovo.gooru.client.mvp.play.resource.add.IsAddResourceCollectionView;
import org.ednovo.gooru.client.mvp.play.resource.body.IsResourcePlayerMetadataView;
import org.ednovo.gooru.client.mvp.play.resource.body.ResourcePlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.resource.body.ResourcePlayerMetadataView;
import org.ednovo.gooru.client.mvp.play.resource.narration.IsResourceNarrationView;
import org.ednovo.gooru.client.mvp.play.resource.narration.ResourceNarrationPresenter;
import org.ednovo.gooru.client.mvp.play.resource.narration.ResourceNarrationView;
import org.ednovo.gooru.client.mvp.play.resource.question.IsQuestionResourceView;
import org.ednovo.gooru.client.mvp.play.resource.question.QuestionResourcePresenter;
import org.ednovo.gooru.client.mvp.play.resource.question.QuestionResourceView;
import org.ednovo.gooru.client.mvp.play.resource.share.IsResourceShareView;
import org.ednovo.gooru.client.mvp.play.resource.share.ResourceSharePresenter;
import org.ednovo.gooru.client.mvp.play.resource.share.ResourceShareView;
import org.ednovo.gooru.client.mvp.player.CollectionPlayPresenter;
import org.ednovo.gooru.client.mvp.player.CollectionPlayPresenter.IsCollectionPlayProxy;
import org.ednovo.gooru.client.mvp.player.CollectionPlayView;
import org.ednovo.gooru.client.mvp.player.IsCollectionPlayView;
import org.ednovo.gooru.client.mvp.player.IsResourcePlayView;
import org.ednovo.gooru.client.mvp.player.ResourcePlayPresenter;
import org.ednovo.gooru.client.mvp.player.ResourcePlayPresenter.IsResourcePlayProxy;
import org.ednovo.gooru.client.mvp.player.ResourcePlayView;
import org.ednovo.gooru.client.mvp.prime.IsPrimeView;
import org.ednovo.gooru.client.mvp.prime.PrimePresenter;
import org.ednovo.gooru.client.mvp.prime.PrimePresenter.IsPrimeProxy;
import org.ednovo.gooru.client.mvp.prime.PrimeView;
import org.ednovo.gooru.client.mvp.profilepage.IsProfilePageView;
import org.ednovo.gooru.client.mvp.profilepage.ProfilePagePresenter;
import org.ednovo.gooru.client.mvp.profilepage.ProfilePagePresenter.IsProfilePageProxy;
import org.ednovo.gooru.client.mvp.profilepage.ProfilePageView;
import org.ednovo.gooru.client.mvp.profilepage.list.IsProfilePageListView;
import org.ednovo.gooru.client.mvp.profilepage.list.ProfilePageListPresenter;
import org.ednovo.gooru.client.mvp.profilepage.list.ProfilePageListView;
import org.ednovo.gooru.client.mvp.profilepage.tab.content.IsProfilePageContentTabView;
import org.ednovo.gooru.client.mvp.profilepage.tab.content.ProfilePageContentTabPresenter;
import org.ednovo.gooru.client.mvp.profilepage.tab.content.ProfilePageContentTabView;
import org.ednovo.gooru.client.mvp.register.IsRegisterView;
import org.ednovo.gooru.client.mvp.register.RegisterPresenter;
import org.ednovo.gooru.client.mvp.register.RegisterPresenter.IsRegisterProxy;
import org.ednovo.gooru.client.mvp.register.RegisterView;
import org.ednovo.gooru.client.mvp.search.IsSearchRootView;
import org.ednovo.gooru.client.mvp.search.SearchRootPresenter;
import org.ednovo.gooru.client.mvp.search.SearchRootPresenter.IsSearchRootProxy;
import org.ednovo.gooru.client.mvp.search.SearchRootView;
import org.ednovo.gooru.client.mvp.search.collection.CollectionSearchPresenter;
import org.ednovo.gooru.client.mvp.search.collection.CollectionSearchPresenter.IsCollectionSearchProxy;
import org.ednovo.gooru.client.mvp.search.collection.CollectionSearchView;
import org.ednovo.gooru.client.mvp.search.collection.IsCollectionSearchView;
import org.ednovo.gooru.client.mvp.search.resource.IsResourceSearchView;
import org.ednovo.gooru.client.mvp.search.resource.ResourceSearchPresenter;
import org.ednovo.gooru.client.mvp.search.resource.ResourceSearchPresenter.IsResourceSearchProxy;
import org.ednovo.gooru.client.mvp.search.resource.ResourceSearchView;
import org.ednovo.gooru.client.mvp.settings.IsUserSettingsView;
import org.ednovo.gooru.client.mvp.settings.UserSettingsPresenter;
import org.ednovo.gooru.client.mvp.settings.UserSettingsPresenter.IsUserSettingProxy;
import org.ednovo.gooru.client.mvp.settings.UserSettingsView;
import org.ednovo.gooru.client.mvp.shelf.IsShelfView;
import org.ednovo.gooru.client.mvp.shelf.ShelfPresenter;
import org.ednovo.gooru.client.mvp.shelf.ShelfPresenter.IsShelfProxy;
import org.ednovo.gooru.client.mvp.shelf.ShelfView;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionFormInPlayPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionFormInPlayView;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionFormPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionFormPresenter.IsCollectionFormProxy;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionFormView;
import org.ednovo.gooru.client.mvp.shelf.collection.IsCollectionFormInPlayView;
import org.ednovo.gooru.client.mvp.shelf.collection.IsCollectionFormView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.assign.CollectionAssignTabPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.assign.CollectionAssignTabView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.assign.IsCollectionAssignTab;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.info.CollectionInfoTabPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.info.CollectionInfoTabView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.info.IsCollectionInfoTabView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.CollectionResourceTabPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.CollectionResourceTabView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.IsCollectionResourceTabView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddResourcePresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddResourceView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.IsAddResourceView;
import org.ednovo.gooru.client.mvp.shelf.list.IsShelfListView;
import org.ednovo.gooru.client.mvp.shelf.list.ShelfListPresenter;
import org.ednovo.gooru.client.mvp.shelf.list.ShelfListView;
import org.ednovo.gooru.client.mvp.wrap.IsWrapView;
import org.ednovo.gooru.client.mvp.wrap.WrapPresenter;
import org.ednovo.gooru.client.mvp.wrap.WrapPresenter.IsWrapProxy;
import org.ednovo.gooru.client.mvp.wrap.WrapView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.annotations.GaAccount;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalytics;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalyticsImpl;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;
/**
 * 
 * @fileName : AppModule.java
 *
 * @description : All presenter class will be bind.
 *
 *
 * @version : 1.0
 *
 * @date: 26-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class AppModule extends AppPresenterModule {

	public static final String GA_ACCOUNT = "UA-20089789-1";

	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(PlaceManager.class).to(AppPlaceManager.class).in(Singleton.class);
		bind(AppPlaceKeeper.class).in(Singleton.class);
		bind(AppClientFactory.class).asEagerSingleton();
		bind(IsPlaceManager.class).to(AppPlaceManager.class)
				.in(Singleton.class);
		bind(TokenFormatter.class).to(AppParameterTokenFormatter.class).in(
				Singleton.class);
		bind(RootPresenter.class).to(AppRootPresenter.class).asEagerSingleton();

		bindConstant().annotatedWith(AppDefaultPlace.class)
				.to(PlaceTokens.HOME);
		bindPresenter(PrimePresenter.class, IsPrimeView.class, PrimeView.class,
				IsPrimeProxy.class);
		bindPresenter(HomePresenter.class, IsHomeView.class, HomeView.class,
				IsHomeProxy.class);
		bindPresenter(WrapPresenter.class, IsWrapView.class, WrapView.class,
				IsWrapProxy.class);
		bindPresenter(RegisterPresenter.class, IsRegisterView.class,
				RegisterView.class, IsRegisterProxy.class);
		bindPresenter(SearchRootPresenter.class, IsSearchRootView.class,
				SearchRootView.class, IsSearchRootProxy.class);
		bindPresenter(CollectionSearchPresenter.class,
				IsCollectionSearchView.class, CollectionSearchView.class,
				IsCollectionSearchProxy.class);
		bindPresenter(ResourceSearchPresenter.class,
				IsResourceSearchView.class, ResourceSearchView.class,
				IsResourceSearchProxy.class);
		bindPresenter(ErrorPresenter.class, IsErrorView.class, ErrorView.class,
				IsErrorProxy.class);
		bindPresenter(ShelfPresenter.class, IsShelfView.class, ShelfView.class,
				IsShelfProxy.class);
		bindPresenter(UserSettingsPresenter.class, IsUserSettingsView.class,
				UserSettingsView.class, IsUserSettingProxy.class);
		// 5.2 Changes
		bindPresenter(ClasspagePresenter.class, IsClasspageView.class,
				ClasspageView.class, IsClasspageProxy.class);
		bindPresenter(FoldersPresenter.class, IsFoldersView.class,
				FoldersView.class, IsFoldersProxy.class);
		bindPresenter(EditClasspagePresenter.class, IsEditClasspageView.class,
				EditClasspageView.class, IsEditClasspageProxy.class);

		bindPresenter(FolderFormViewPresenter.class, IsFoldersPopupView.class,
				FolderPopUpUiBinder.class, IsFolderPopUpFormProxy.class);
		bindPresenter(EditFolderPresenter.class, IsEditFolderView.class,
				EditFolderView.class, IsEditFolderProxy.class);

		bindPresenter(ProfilePagePresenter.class, IsProfilePageView.class,
				ProfilePageView.class, IsProfilePageProxy.class);

		bindPresenterWidget(CollectionResourceTabPresenter.class,
				IsCollectionResourceTabView.class,
				CollectionResourceTabView.class);
		bindPresenterWidget(CollectionInfoTabPresenter.class,
				IsCollectionInfoTabView.class, CollectionInfoTabView.class);

		bindPresenterWidget(FolderInfoTabPresenter.class,
				IsFolderInfoTabView.class, FolderInfoTabView.class);

		bindPresenterWidget(FolderContentTabPresenter.class,
				IsFolderContentTabView.class, FolderContentTabView.class);

		bindSingletonPresenterWidget(ShelfListPresenter.class,
				IsShelfListView.class, ShelfListView.class);
		bindPresenter(CollectionFormPresenter.class,
				IsCollectionFormView.class, CollectionFormView.class,
				IsCollectionFormProxy.class);
		bindPresenter(ResourcePlayPresenter.class, IsResourcePlayView.class,
				ResourcePlayView.class, IsResourcePlayProxy.class);
		bindPresenter(CollectionPlayPresenter.class,
				IsCollectionPlayView.class, CollectionPlayView.class,
				IsCollectionPlayProxy.class);
		bindPresenterWidget(ImageUploadPresenter.class,
				IsImageUploadView.class, ImageUploadView.class);
		bindPresenterWidget(AddResourcePresenter.class,
				IsAddResourceView.class, AddResourceView.class);

		bindPresenterWidget(AddAssignmentContainerPresenter.class,
				IsAddAssignmentContainerView.class,
				AddAssignmentContainerView.class);

		bindPresenter(ClassCodePresenter.class, IsClassCodeView.class,
				ClassCodeView.class, IsClassCodeProxy.class);

		bindPresenter(StudentAssignmentPresenter.class,
				IsStudentAssignmentView.class, StudentAssignmentView.class,
				IsStudentAssignmentProxy.class);

		bindPresenterWidget(ProfilePageContentTabPresenter.class,
				IsProfilePageContentTabView.class,
				ProfilePageContentTabView.class);

		/*bindPresenterWidget(ProfilePageInfoTabPresenter.class,
				IsProfilePageInfoTabView.class, ProfilePageInfoTabView.class);
*/
		bindPresenter(DeviceSupportPresenter.class, IsDeviceSupportView.class,
				DeviceSupportView.class, IsDeviceSupportProxy.class);

		bindPresenterWidget(ProfilePageListPresenter.class,
				IsProfilePageListView.class, ProfilePageListView.class);

		bindPresenterWidget(UserRegistrationPresenter.class,
				IsUserRegistrationView.class, UserRegistrationView.class);

		bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(
				Singleton.class);
		bindConstant().annotatedWith(GaAccount.class).to(GA_ACCOUNT);
		bind(GoogleAnalyticsNavigationTracker.class).asEagerSingleton();
		bindPresenterWidget(CollectionFormInPlayPresenter.class,
				IsCollectionFormInPlayView.class,
				CollectionFormInPlayView.class);

		bindPresenterWidget(CollectionAssignTabPresenter.class,
				IsCollectionAssignTab.class, CollectionAssignTabView.class);

		bindPresenterWidget(SignUpPresenter.class, IsSignUpView.class,SignUpView.class);
		bindPresenter(CollectionPlayerPresenter.class, IsCollectionPlayerView.class, CollectionPlayerView.class,IsCollectionPlayerProxy.class);
		bindPresenter(ResourcePlayerPresenter.class, IsResourcePlayerView.class, ResourcePlayerView.class,IsResourcePlayerProxy.class);
		bindPresenterWidget(CollectionPlayerMetadataPresenter.class, IsCollectionPlayerMetadataView.class, CollectionPlayerMetadataView.class);
		bindPresenterWidget(CollectionPlayerTocPresenter.class, IsCollectionPlayerTocView.class, CollectionPlayerTocView.class);
		bindPresenterWidget(ResourcePlayerMetadataPresenter.class, IsResourcePlayerMetadataView.class, ResourcePlayerMetadataView.class);
		bindPresenterWidget(ResourceNarrationPresenter.class, IsResourceNarrationView.class, ResourceNarrationView.class);
		bindPresenterWidget(CollectionSharePresenter.class, IsCollectionShareView.class, CollectionShareView.class);
		bindPresenterWidget(ResourceInfoPresenter.class, IsResourceInfoView.class, ResourceInfoView.class);
		bindPresenterWidget(QuestionResourcePresenter.class, IsQuestionResourceView.class, QuestionResourceView.class);
		bindPresenterWidget(CollectionEndPresenter.class, IsCollectionEndView.class, CollectionEndView.class);
		bindPresenterWidget(ResourceSharePresenter.class, IsResourceShareView.class, ResourceShareView.class);
		bindPresenterWidget(AddResourceCollectionPresenter.class, IsAddResourceCollectionView.class, AddResourceCollectionView.class);	
		bindPresenterWidget(AddCollectionPresenter.class, IsAddCollectionView.class, AddCollectionView.class);
		bindPresenterWidget(SignUpCompleteProfilePresenter.class,IsSignUpCompleteProfile.class,SignUpCompleteProfileView.class);
		bindPresenterWidget(SignUpAfterThirteenPresenter.class,IsAfterThirteen.class,SignUpTurnsAfterThirteenView.class);
	}
	

}
