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
package org.ednovo.gooru.client.mvp.classpages.classlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.client.PlaceTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.mvp.classpages.event.GetStudentJoinListEvent;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.DeletePopupViewVc;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.SuccessPopupViewVc;
import org.ednovo.gooru.client.uc.EmailShareUc;
import org.ednovo.gooru.client.uc.suggestbox.widget.AutoSuggestForm;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.shared.i18n.MessageProperties;
import org.ednovo.gooru.shared.model.content.ClasspageDo;
import org.ednovo.gooru.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.shared.model.social.SocialShareDo;
import org.ednovo.gooru.shared.model.user.SettingDo;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimpleRadioButton;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @fileName : ClassListView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 13-Mar-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class ClassListView  extends BaseViewWithHandlers<ClassListUiHandlers> implements IsClassListView, SelectionHandler<SuggestOracle.Suggestion> {

	private static ClassListViewUiBinder uiBinder = GWT.create(ClassListViewUiBinder.class);
	

    
	@UiField(provided = true)
	ClasslistpageCBundle res;
	
	@UiField HTMLPanel publicAssignContainer, privateAssignContainer, panelSuggestBox, panelActions,privacyPolicyPanel;
	
	@UiField VerticalPanel panelActiveMembersList,panelPendingMembersList;

	@UiField HTMLPanel panelCode,privateMsgPanel, publicMsgPanel, panelNoMembers, panelMembersList, panelPendingMembersContainer, panelActiveMembersContainter,assignHeader;
	
	@UiField Anchor ancPendingListSeeMore, ancActiveListSeeMore;
	
	@UiField HTMLPanel panelLoading,titleTxt,emailTxt,shareTxt,shareTitle,joinTxt,inviteTxt;
	
	@UiField TextBox txtClasspageLinkShare,txtClasspageCodeShare;
	
	@UiField Label visibilityTitle,openClassLabelTitle,openClassLabelDesc,openClosedLabelTitle,openClosedLabelDesc,lblMonitorDes;
	
	@UiField RadioButton visibilityRadioOpen,visibilityRadioInviteOnly;
	
	@UiField HTMLPanel questionMarkPanel,questionMarkPanel1,questionMarkPanel2;
	
	@UiField Label manageHeader,manageTxt,trackTxt;

	@UiFactory
	public SimpleRadioButton createRadioButton() {
	    return new SimpleRadioButton("");
	}
	
	@UiField Button btnInvite;
	
	@UiField Label lblErrorMessage, lblPleaseWait, lblPendingMembers,lblActiveMembers,lblText, lblPendingPleaseWait, lblActivePleaseWait,lblActiveMembersDesc;
	@UiField InlineHTML publicDescTxt;
	AutoSuggestForm autoSuggetTextBox =null;
	
	@UiField InlineLabel lblPii,toUsText;
	@UiField Anchor ancprivacy;
		
	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
    MessageProperties i18n = GWT.create(MessageProperties.class);
    
	private static int studentsLimitCount = 500;

	int currentStudentsCount=0;

	int overAllStudentsCount = 0;
	
	int pendingOffset = 0;
	
	int activeOffset = 0;
	
	int activeMemberCounter = 0;
	
	int pendingMemberCounter = 0;
	
	private int pageSize = 20;
	
	private int activeListTotalCount=0;
	
	private int  activeListPageNum=0;
	
	private int pendingListTotalCount=0;
	
	private int pendingListPageNum=0;
	
	private int pendingOffsetValue=0;

	String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	
	private ClasspageDo classpageDo;
	
	private SocialShareDo shareDo;
	
	private TermsOfUse termsOfUse;
	
	private static final String PUBLIC="public";
	private static final String SHORTEN_URL = "shortenUrl";
	
	private PopupPanel toolTipPopupPanelNew = new PopupPanel();
	private PopupPanel toolTipPopupPanelNew1 = new PopupPanel();
	private PopupPanel toolTipPopupPanelNew2 = new PopupPanel();
	

	
	interface ClassListViewUiBinder extends UiBinder<Widget, ClassListView> {
	}

	public ClassListView() {
		this.res = ClasslistpageCBundle.INSTANCE;
		res.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		
		setUiElements();
		
		
/*		if(sharingType != null){
			if(sharingType.equalsIgnoreCase("public")){
				publicAssignContainer.setVisible(true);
				privateAssignContainer.setVisible(false);
			}else{
				publicAssignContainer.setVisible(false);
				privateAssignContainer.setVisible(true);
			}
		}else{
			publicAssignContainer.setVisible(true);
			privateAssignContainer.setVisible(false);
		}

		button.setText("Click me"); */
		txtClasspageLinkShare.addClickHandler(new TextCopyHandler());
		txtClasspageCodeShare.addClickHandler(new ClassCodeCopy());

//		webLinkBtn.addClickHandler(new InviteStudentInClass());
	}

	/**
	 * @function setTextAndIds 
	 * 
	 * @created_date : Mar 16, 2014
	 * 
	 * @description
	 * 
	 * 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	private void setUiElements() {
		panelLoading.getElement().setId("pnlLoadingPanel");
		panelLoading.getElement().getStyle().setZIndex(9999);
		panelLoading.getElement().getStyle().setPosition(Position.RELATIVE);
		
		txtClasspageCodeShare.setReadOnly(true);
		txtClasspageCodeShare.getElement().setId("txtClassPageCodeShare");
		StringUtil.setAttributes(txtClasspageCodeShare, true);
		
		txtClasspageLinkShare.setReadOnly(true);
		txtClasspageLinkShare.getElement().setId("txtClassPageLinkShare");
		StringUtil.setAttributes(txtClasspageLinkShare, true);
		
		btnInvite.setText(i18n.GL0944());
		btnInvite.getElement().setId("btnInvite");
		btnInvite.getElement().setAttribute("alt",i18n.GL0944());
		btnInvite.getElement().setAttribute("title",i18n.GL0944());
		btnInvite.setEnabled(true);
		btnInvite.setVisible(true);
		
		visibilityTitle.setText(i18n.GL2019());
		openClassLabelTitle.setText(i18n.GL2020());
		openClassLabelDesc.setText(i18n.GL2021());
		openClosedLabelTitle.setText(i18n.GL2022());
		openClosedLabelDesc.setText(i18n.GL2023());
		
		lblMonitorDes.setText(i18n.GL2119());
		lblMonitorDes.getElement().setId("lblMonitorDes");
		lblMonitorDes.getElement().setAttribute("alt",i18n.GL2119());
		lblMonitorDes.getElement().setAttribute("title",i18n.GL2119());
		
		manageTxt.setText(i18n.GL2120());
		manageTxt.getElement().setId("lblmanageTxt");
		manageTxt.getElement().setAttribute("alt",i18n.GL2120());
		manageTxt.getElement().setAttribute("title",i18n.GL2120());
	
		privateMsgPanel.getElement().setId("pnlPrivateMsg");
		
/*		privateMsgDesc.setText(i18n.GL1599());
		privateMsgDesc.getElement().setId("spnPrivateMsgDesc");
		privateMsgDesc.getElement().setAttribute("alt",i18n.GL1599());
		privateMsgDesc.getElement().setAttribute("title",i18n.GL1599());*/
		
		//publicClassTxt.getElement().setInnerHTML(GL1601);
		//publicClassTxt.getElement().setId("pnlPublicClass");
		publicDescTxt.getElement().setId("spnPublicDesc");
		publicMsgPanel.getElement().setId("pnlPublicMsg");
		//publicDescTxt.setText(GL1602);
/*		publicTxt.getElement().setInnerHTML(i18n.GL1621()+i18n.GL_SPL_EXCLAMATION());
		publicTxt.getElement().setId("pnlPublic");
		publicTxt.getElement().setAttribute("alt",i18n.GL1621());
		publicTxt.getElement().setAttribute("title",i18n.GL1621());*/
		
/*		publicTxtDesc.setText(i18n.GL1599());
		publicTxtDesc.getElement().setId("spnPublicTxtDesc");
		publicTxtDesc.getElement().setAttribute("alt",i18n.GL1599());
		publicTxtDesc.getElement().setAttribute("title",i18n.GL1599());*/

		
/*		inviteDesc.setText(i18n.GL1604());
		inviteDesc.getElement().setId("spnInviteDesc");
		inviteDesc.getElement().setAttribute("alt",i18n.GL1604());
		inviteDesc.getElement().setAttribute("title",i18n.GL1604());*/
		
		titleTxt.getElement().setInnerHTML(i18n.GL1590());
		titleTxt.getElement().setId("pnlTitle");
		titleTxt.getElement().setAttribute("alt",i18n.GL1590());
		titleTxt.getElement().setAttribute("title",i18n.GL1590());
		
		emailTxt.getElement().setInnerHTML(i18n.GL1591());
		emailTxt.getElement().setId("pnlEmail");
		emailTxt.getElement().setAttribute("alt",i18n.GL1591());
		emailTxt.getElement().setAttribute("title",i18n.GL1591());
		
		shareTxt.getElement().setInnerHTML(i18n.GL1592());
		shareTxt.getElement().setId("pnlShare");
		shareTxt.getElement().setAttribute("alt",i18n.GL1592());
		shareTxt.getElement().setAttribute("title",i18n.GL1592());
		
		shareTitle.getElement().setInnerHTML(i18n.GL1594());
		shareTitle.getElement().setId("pnlShareTitle");
		shareTitle.getElement().setAttribute("alt",i18n.GL1594());
		shareTitle.getElement().setAttribute("title",i18n.GL1594());
		
/*		shareDesc.setText(i18n.GL1595());
		shareDesc.getElement().setId("spnShareDesc");
		shareDesc.getElement().setAttribute("alt",i18n.GL1595());
		shareDesc.getElement().setAttribute("title",i18n.GL1595());*/
		
		joinTxt.getElement().setInnerHTML(i18n.GL1596());
		joinTxt.getElement().setId("pnlJoinText");
		joinTxt.getElement().setAttribute("alt",i18n.GL1596());
		joinTxt.getElement().setAttribute("title",i18n.GL1596());
		
		/*manageTxt.getElement().setInnerHTML(i18n.GL1597());
		manageTxt.getElement().setId("pnlManageText");
		manageTxt.getElement().setAttribute("alt",i18n.GL1597());
		manageTxt.getElement().setAttribute("title",i18n.GL1597());*/
		
		manageHeader.setText(i18n.GL1597());
		manageHeader.getElement().setId("pnlManageText");
		manageHeader.getElement().setAttribute("alt",i18n.GL1597());
		manageHeader.getElement().setAttribute("title",i18n.GL1597());
		
		trackTxt.setText(i18n.GL1598());
		trackTxt.getElement().setId("pnlTrackText");
		trackTxt.getElement().setAttribute("alt",i18n.GL1598());
		trackTxt.getElement().setAttribute("title",i18n.GL1598());
		

		
		inviteTxt.getElement().setId("pnlInvite");
		//inviteTxt.getElement().setInnerHTML(GL1589);
/*		shareTxtDesc.setText(i18n.GL1593());
		shareTxtDesc.getElement().setId("spnShareTxtDesc");
		shareTxtDesc.getElement().setAttribute("alt",i18n.GL1593());
		shareTxtDesc.getElement().setAttribute("title",i18n.GL1593());*/
		

		
		assignHeader.getElement().setInnerText(i18n.GL1584());
		assignHeader.getElement().setId("pnlAssignHeader");
		assignHeader.getElement().setAttribute("alt",i18n.GL1584());
		assignHeader.getElement().setAttribute("title",i18n.GL1584());
		
/*		assignSubHeader.getElement().setInnerText(i18n.GL1585());
		assignSubHeader.getElement().setId("pnlAssignSubHeader");
		assignSubHeader.getElement().setAttribute("alt",i18n.GL1585());
		assignSubHeader.getElement().setAttribute("title",i18n.GL1585());*/

		
		lblPleaseWait.setText(i18n.GL1137());
		lblPleaseWait.getElement().setId("lblPleaseWait");
		lblPleaseWait.getElement().setAttribute("alt",i18n.GL1137());
		lblPleaseWait.getElement().setAttribute("title",i18n.GL1137());
		
		lblText.setText(StringUtil.generateMessage(i18n.GL1528(), studentsLimitCount+""));
		lblText.getElement().setId("lblText");
		lblText.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL1528(), studentsLimitCount+""));
		lblText.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL1528(), studentsLimitCount+""));
		
		lblPii.setText(i18n.GL1892());
		lblPii.getElement().setId("spnPii");
		lblPii.getElement().setAttribute("alt",i18n.GL1892());
		lblPii.getElement().setAttribute("title",i18n.GL1892());
		
		ancprivacy.setText(i18n.GL1893());
		ancprivacy.getElement().setId("lnkPrivacy");
		ancprivacy.getElement().setAttribute("alt",i18n.GL1893());
		ancprivacy.getElement().setAttribute("title",i18n.GL1893());
		
		toUsText.setText(i18n.GL1894());
		toUsText.getElement().setId("spnUsText");
		toUsText.getElement().setAttribute("alt",i18n.GL1894());
		toUsText.getElement().setAttribute("title",i18n.GL1894());
		
		privacyPolicyPanel.setVisible(false);
		privacyPolicyPanel.getElement().setId("pnlPrivacyPolicy");
		
		lblPleaseWait.setVisible(false);
		lblErrorMessage.setVisible(false);
		lblErrorMessage.getElement().setId("errlblErrorMessage");
		
		panelNoMembers.setVisible(false);
		panelNoMembers.getElement().setId("pnlNoMembers");
		
		panelMembersList.setVisible(false);
		panelMembersList.getElement().setId("pnlMembersList");
		
		lblPendingMembers.setText(i18n.GL1525());
		lblPendingMembers.getElement().setId("lblPendingMembers");
		lblPendingMembers.getElement().setAttribute("alt",i18n.GL1525());
		lblPendingMembers.getElement().setAttribute("title",i18n.GL1525());
		
		lblActiveMembers.setText(i18n.GL1526());
		lblActiveMembers.getElement().setId("lblActiveMembers");
		lblActiveMembers.getElement().setAttribute("alt",i18n.GL1526());
		lblActiveMembers.getElement().setAttribute("title",i18n.GL1526());
		
		lblPendingPleaseWait.setVisible(false);
		lblActivePleaseWait.setVisible(false);
		
		panelMembersList.setVisible(false);
		lblPendingMembers.setVisible(false);
		questionMarkPanel2.setVisible(false);
		lblActiveMembers.setVisible(false);
		
		lblPendingPleaseWait.setText(i18n.GL0339().toLowerCase());
		lblPendingPleaseWait.getElement().setId("lblPendingPleaseWait");
		lblPendingPleaseWait.getElement().setAttribute("alt",i18n.GL0339().toLowerCase());
		lblPendingPleaseWait.getElement().setAttribute("title",i18n.GL0339().toLowerCase());
		
		lblActivePleaseWait.setText(i18n.GL0339().toLowerCase());
		lblActivePleaseWait.getElement().setId("lblActivePleaseWait");
		lblActivePleaseWait.getElement().setAttribute("alt",i18n.GL0339().toLowerCase());
		lblActivePleaseWait.getElement().setAttribute("title",i18n.GL0339().toLowerCase());
		
		ancPendingListSeeMore.setText(i18n.GL0508().toLowerCase());
		ancPendingListSeeMore.getElement().setId("lnkPendingListSeeMore");
		ancPendingListSeeMore.getElement().setAttribute("alt",i18n.GL0508().toLowerCase());
		ancPendingListSeeMore.getElement().setAttribute("title",i18n.GL0508().toLowerCase());
		
		
		ancActiveListSeeMore.setText(i18n.GL0508().toLowerCase());
		ancActiveListSeeMore.getElement().setId("lnkActiveListSeeMore");
		ancActiveListSeeMore.getElement().setAttribute("alt",i18n.GL0508().toLowerCase());
		ancActiveListSeeMore.getElement().setAttribute("title",i18n.GL0508().toLowerCase());
		
		lblActiveMembersDesc.setText(i18n.GL1633());
		lblActiveMembersDesc.getElement().setId("lblActiveMembersDesc");
		lblActiveMembersDesc.getElement().setAttribute("alt",i18n.GL1633());
		lblActiveMembersDesc.getElement().setAttribute("title",i18n.GL1633());
		lblActiveMembersDesc.setVisible(false);
		
		panelActiveMembersList.getElement().setId("ActiveMembersList");
		panelPendingMembersList.getElement().setId("PendingMembersList");
		
		panelSuggestBox.getElement().setId("pnlSuggestbox");
		panelActions.getElement().setId("pnlActions");
		panelCode.getElement().setId("pnlCode");
		publicAssignContainer.getElement().setId("pnlPublicAssignContainer");
		panelPendingMembersContainer.getElement().setId("pnlPendingMembersContainer");
		panelActiveMembersContainter.getElement().setId("pnlActiveMembersContainter");
		
		final Image imgNotFriendly = new Image("images/mos/questionmark.png");
		imgNotFriendly.getElement().getStyle().setLeft(121, Unit.PX);
		imgNotFriendly.getElement().getStyle().setMarginTop(-16, Unit.PX);
		imgNotFriendly.getElement().getStyle().setPosition(Position.ABSOLUTE);
		imgNotFriendly.getElement().getStyle().setCursor(Cursor.POINTER);
		imgNotFriendly.addMouseOverHandler(new MouseOverShowClassCodeToolTip1());
		imgNotFriendly.addMouseOutHandler(new MouseOutHideToolTip1());
		
		final Image imgNotFriendly1 = new Image("images/mos/questionmark.png");
		imgNotFriendly1.getElement().getStyle().setLeft(135, Unit.PX);
		imgNotFriendly1.getElement().getStyle().setMarginTop(-16, Unit.PX);
		imgNotFriendly1.getElement().getStyle().setPosition(Position.ABSOLUTE);
		imgNotFriendly1.getElement().getStyle().setCursor(Cursor.POINTER);
		imgNotFriendly1.addMouseOverHandler(new MouseOverShowClassCodeToolTip2());
		imgNotFriendly1.addMouseOutHandler(new MouseOutHideToolTip2());
		
		final Image imgNotFriendly2 = new Image("images/mos/questionmark.png");
		imgNotFriendly2.getElement().getStyle().setLeft(145, Unit.PX);
		imgNotFriendly2.getElement().getStyle().setMarginTop(-26, Unit.PX);
		imgNotFriendly2.getElement().getStyle().setPosition(Position.ABSOLUTE);
		imgNotFriendly2.getElement().getStyle().setCursor(Cursor.POINTER);
		imgNotFriendly2.addMouseOverHandler(new MouseOverShowClassCodeToolTip3());
		imgNotFriendly2.addMouseOutHandler(new MouseOutHideToolTip3());

		questionMarkPanel.clear();
		questionMarkPanel.add(imgNotFriendly);
		questionMarkPanel1.clear();
		questionMarkPanel1.add(imgNotFriendly1);
		questionMarkPanel2.clear();
		questionMarkPanel2.add(imgNotFriendly2);
		createAutoSuggestBox();
	}

	/**
	 * @function createAutoSuggestBox 
	 * 
	 * @created_date : Jan 30, 2014
	 * 
	 * @description
	 * 
	 * 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	@Override
	public void createAutoSuggestBox() {
		panelSuggestBox.setStyleName("auto_suggest");
		autoSuggetTextBox = new AutoSuggestForm(oracle) {

			@Override
			public void onSubmit(DomEvent<EventHandler> event) {
				// Nothing to do....
			}

			@Override
			public void dataObjectModel(String text) {
				if (text.trim().length() > 0){
					AppClientFactory.getInjector().getClasspageService().getSuggestionByName(text.trim(), new SimpleAsyncCallback<List<String>>() {
	
						@Override
						public void onSuccess(List<String> lstOfSuggestedCollaborators) {
							autoSuggetTextBox.clearAutoSuggestData();
							for (String lstCollab : lstOfSuggestedCollaborators){
								oracle.add(lstCollab);
							}
							autoSuggetTextBox.setAutoSuggest(oracle);
						}
					});
				}
			}

			@Override
			public void keyPressOnTextBox(KeyPressEvent event) {
				lblErrorMessage.setVisible(false);
			}

			@Override
			public void errorMsgVisibility(boolean visibility, String emailId) {
				if (visibility){
					showErrorMessage(StringUtil.generateMessage(i18n.GL1019(), emailId));
				}else{
					lblErrorMessage.setVisible(false);
				}
			}

		};
		autoSuggetTextBox.clearAutoSuggestData();
		panelSuggestBox.clear();
		panelSuggestBox.add(autoSuggetTextBox);
		autoSuggetTextBox.getTxtInput().getTxtInputBox().setFocus(true);
	}
	
	public class MouseOverShowClassCodeToolTip1 implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew.clear();
			toolTipPopupPanelNew.setWidget(new GlobalToolTip(i18n.GL2090()));
			toolTipPopupPanelNew.setStyleName("");
			toolTipPopupPanelNew.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 14, event.getRelativeElement().getAbsoluteTop());
			toolTipPopupPanelNew.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew.show();
		}

	}

	public class MouseOutHideToolTip1 implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew.hide();
		}
	}
	
	public class MouseOverShowClassCodeToolTip2 implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew1.clear();
			toolTipPopupPanelNew1.setWidget(new GlobalToolTip(i18n.GL2091()));
			toolTipPopupPanelNew1.setStyleName("");
			toolTipPopupPanelNew1.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 14, event.getRelativeElement().getAbsoluteTop());
			toolTipPopupPanelNew1.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew1.show();
		}

	}

	public class MouseOutHideToolTip2 implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew1.hide();
		}
	}
	
	public class MouseOverShowClassCodeToolTip3 implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew2.clear();
			toolTipPopupPanelNew2.setWidget(new GlobalToolTip(i18n.GL2092()));
			toolTipPopupPanelNew2.setStyleName("");
			toolTipPopupPanelNew2.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 14, event.getRelativeElement().getAbsoluteTop());
			toolTipPopupPanelNew2.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew2.show();
		}

	}

	public class MouseOutHideToolTip3 implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew2.hide();
		}
	}
	
	
	public class InviteStudentInClass implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			
//			new InviteStudentsPopup();
			AppClientFactory.getInjector().getUserService().getUserProfileDetails(AppClientFactory.getLoggedInUser().getGooruUId(), new SimpleAsyncCallback<SettingDo>() {

				@Override
				public void onSuccess(SettingDo result) {
					shareDo.setEmailId(result.getExternalId());
					EmailShareUc emailShare=new EmailShareUc(shareDo);
					emailShare.show();
					emailShare.center();
				}
			});
		}
		
	}

	public class TextCopyHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			txtClasspageLinkShare.selectAll();
			txtClasspageLinkShare.setFocus(true);
		}
		
	}
	
	public class ClassCodeCopy implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			txtClasspageCodeShare.selectAll();
			txtClasspageCodeShare.setFocus(true);
		}
		
	}

	/**
	 * This method is used to set the class page Do
	 */
	@Override
	public void setClassPageDo(ClasspageDo classpageDo) {
		this.classpageDo = classpageDo;
		clearMembersListPanel();
		setLoadingPanelVisibility(true);
		txtClasspageCodeShare.setText(classpageDo.getClasspageCode().toUpperCase());
		activeListPageNum=0;
		activeListTotalCount=0;
		pendingListPageNum=0;
		pendingListTotalCount=0;
		pendingOffsetValue=0;
		getUiHandlers().getActiveMembersListByCollectionId(classpageDo.getClasspageCode(),  pageSize*activeListPageNum, pageSize, "active",true,true);	//this will callback displayActiveMembersList method ....
				
		if(classpageDo.getSharing().equalsIgnoreCase(PUBLIC)){
			visibilityRadioOpen.setChecked(true);
			visibilityRadioInviteOnly.setChecked(false);
		}else{
			visibilityRadioOpen.setChecked(false);
			visibilityRadioInviteOnly.setChecked(true);
		}
	}
	private void addShareClass() {
		shareDo = new SocialShareDo();
		if(classpageDo != null)
		{
		shareDo.setTitle(classpageDo.getTitle());
		shareDo.setDecodeRawUrl(txtClasspageCodeShare.getText());
		shareDo.setBitlylink(txtClasspageLinkShare.getText());
		shareDo.setCategoryType(AppClientFactory.getLoggedInUser().getUsername());
		shareDo.setOnlyIcon(false);
		shareDo.setIsSearchShare(false);
		}
	}

	/**
	 * This method is used to set the the share shorten Url
	 */
	@Override
	public void setShareUrl(Map<String, String> shortenUrl) {
		if (shortenUrl != null && shortenUrl.containsKey(SHORTEN_URL)) {
			txtClasspageLinkShare.setText(shortenUrl.get(SHORTEN_URL));
		}
		addShareClass();
	}
	
	@UiHandler("ancPendingListSeeMore")
	public void onClickPendingListSeeMore(ClickEvent event){
		lblPendingPleaseWait.setVisible(true);
		ancPendingListSeeMore.setVisible(false);
		getUiHandlers().getMembersListByCollectionId(classpageDo.getClasspageCode(),  pendingOffsetValue, pageSize, "pending",true);	//this will callback displayPendingMembersList method ....
	}
	@UiHandler("ancActiveListSeeMore")
	public void onClickActiveListSeeMore(ClickEvent event){
		lblActivePleaseWait.setVisible(true);
		ancActiveListSeeMore.setVisible(false);
		int offset=(pageSize*activeListPageNum);
		getUiHandlers().getActiveMembersListByCollectionId(classpageDo.getClasspageCode(),  offset, pageSize, "active",true,false);	//this will callback displayActiveMembersList method ....
	}
	
	@UiHandler("visibilityRadioOpen")
	public void onvisibilityRadioOpenClicked(ClickEvent click){
		visibilityRadioOpen.setChecked(true);
		visibilityRadioInviteOnly.setChecked(false);
		getUiHandlers().updateClassPageInfo(classpageDo.getClasspageId(), null, null, "public");
	}
	
	@UiHandler("visibilityRadioInviteOnly")
	public void onvisibilityRadioInviteOnlyClicked(ClickEvent click){
		visibilityRadioOpen.setChecked(false);
		visibilityRadioInviteOnly.setChecked(true);
		getUiHandlers().updateClassPageInfo(classpageDo.getClasspageId(), null, null, "private");

	}
	
	
	
	/**
	 * 
	 * @function showErrorMessage 
	 * 
	 * @created_date : Mar 16, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param errorMessage
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	
	public void showErrorMessage(String errorMessage){
		lblErrorMessage.setText(errorMessage);
		lblErrorMessage.getElement().setAttribute("alt",errorMessage);
		lblErrorMessage.getElement().setAttribute("title",errorMessage);
		lblErrorMessage.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.logical.shared.SelectionHandler#onSelection(com.google.gwt.event.logical.shared.SelectionEvent)
	 */
	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
	
	}
	
	//UI Handlers
	@UiHandler("btnInvite")
	public void OnClickInvite(ClickEvent event){
		//Check for null - Check for Empty
		lblPleaseWait.setVisible(true);
		lblActiveMembers.setVisible(true);
		lblActiveMembersDesc.setVisible(true);
		btnInvite.setVisible(false);
		String studentsEmailIds = autoSuggetTextBox.getSelectedItemsAsString();
		String emailIds[] = studentsEmailIds.trim().split("\\s*,\\s*");
		List<String> lstEmailID = new ArrayList<String>();
		for (int i=0; i<emailIds.length; i++){
			lstEmailID.add("\""+emailIds[i].toLowerCase().trim()+"\"");	
		}
		if (studentsEmailIds != null && studentsEmailIds.equalsIgnoreCase("")){
			lblPleaseWait.setVisible(false);
			btnInvite.setVisible(true);
			return;
		}
		
		currentStudentsCount = emailIds.length + overAllStudentsCount;
		if (currentStudentsCount > studentsLimitCount){
			lblPleaseWait.setVisible(false);
			btnInvite.setVisible(true);
			showErrorMessage(StringUtil.generateMessage(i18n.GL1523(), ""+studentsLimitCount));
			return;
		}
				
		//Check for Valid Email ID format
		boolean from;
		for (int i=0; i<emailIds.length; i++){
			String emailID = emailIds[i].toLowerCase().trim();
			from = emailID.matches(EMAIL_REGEX);
			if (!from){
				lblPleaseWait.setVisible(false);
				btnInvite.setVisible(true);
				showErrorMessage(StringUtil.generateMessage(i18n.GL1019(), emailID));
				return;
			}
		}
		if (AppClientFactory.getLoggedInUser().getEmailId()!=null){
			boolean isValid=true;
			for (int i=0; i<emailIds.length; i++){
				String emailId = emailIds[i].toLowerCase().trim();
				if (emailId.equalsIgnoreCase(AppClientFactory.getLoggedInUser().getEmailId())){
					lblPleaseWait.setVisible(false);
					btnInvite.setVisible(true);
					showErrorMessage(i18n.GL1524());
					isValid = false;
					break;
				}
			}
			if (!isValid){
				return;
			}
		}
	
		btnInvite.getElement().addClassName("disabled");
		btnInvite.setEnabled(false);
		//Call API to add the Student to the class.
		getUiHandlers().addMembers(classpageDo.getClasspageId(), lstEmailID);	// this will callback the displayPendingMembersList method.
		
	}
	@Override
	public void setLoadingPanelVisibility(boolean visibility){
		panelLoading.setVisible(visibility);
	}

	/**
	 * @function displayPendingMembersList 
	 * 
	 * @created_date : Mar 17, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param lstPendingMembers
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	@Override
	public void displayPendingMembersList(List<CollaboratorsDo> lstPendingMembers, boolean isNew, int totalCount,boolean increasePageNum,boolean insertTop) {
		setLoadingPanelVisibility(false);
		lblPleaseWait.setVisible(false);
		lblErrorMessage.setVisible(false);
		if(!insertTop){
			this.pendingListTotalCount=totalCount;
		}
		if(increasePageNum){
			pendingOffsetValue=pendingOffsetValue+pageSize;
		}
		if(pendingListTotalCount==0&&activeListTotalCount==0){
			panelNoMembers.setVisible(true);
			panelMembersList.setVisible(false);
			lblPendingMembers.setVisible(false);
			questionMarkPanel2.setVisible(false);
			lblActiveMembers.setVisible(false);
			lblActiveMembersDesc.setVisible(false);
			ancPendingListSeeMore.setVisible(false);
		}else if(pendingListTotalCount==0){
			lblPendingMembers.setVisible(false);
			ancPendingListSeeMore.setVisible(false);
			questionMarkPanel2.setVisible(false);
		}else{
			lblPendingPleaseWait.setVisible(false);
			for (int k=0; k<lstPendingMembers.size();k++){
				lblPendingMembers.setVisible(true);
				questionMarkPanel2.setVisible(true);
				panelNoMembers.setVisible(false);
				panelMembersList.setVisible(true);
				panelPendingMembersContainer.setVisible(true);
				if(insertTop){
					pendingOffsetValue++;
					pendingListTotalCount++;
				}
				if((pendingOffsetValue)< pendingListTotalCount){
					ancPendingListSeeMore.setVisible(true);
				}
				else{
					ancPendingListSeeMore.setVisible(false);
				}
				insertPendingUserAfterDeletion(lstPendingMembers.get(k),isNew,totalCount,k,insertTop);
				enableInvite();
			}
		}
	}
	@Override
	 public void insertPendingUserAfterDeletion(final CollaboratorsDo lstPendingMembers, boolean isNew, int totalCount, int intPos,boolean insertAtTop){
				final MembersViewVc membersViewVc = new MembersViewVc(AppClientFactory.getCurrentPlaceToken(), isNew, lstPendingMembers, classpageDo,intPos) {
				public void removeUserWidget(){
					ArrayList<String> arrayEmailId = new ArrayList<String>();
					arrayEmailId.add('"'+lstPendingMembers.getEmailId()+'"');
					getUiHandlers().removeUserFromClass(classpageDo, arrayEmailId.toString(),0, true,this);
				}
				@Override
				public void setCollabCount(int count, String type) {}
				@Override
				public void setStudentsListContainer(ClickEvent event) {
					Window.enableScrolling(false);
					AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
					DeletePopupViewVc delete = new DeletePopupViewVc() {
						@Override
						public void onClickPositiveButton(ClickEvent event) {
							removeUserWidget();
							Window.enableScrolling(true);
							AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
							hide();
						}

						@Override
						public void onClickNegitiveButton(ClickEvent event) {
							Window.enableScrolling(true);
							AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
							hide();
						}
					};
					delete.setPopupTitle(i18n.GL1548());
					delete.setNotes(i18n.GL0748());
					delete.setDescText(StringUtil.generateMessage(i18n.GL1547(), lblUserName.getText() != null && !lblUserName.getText().equalsIgnoreCase("") ? "<i>"+lblUserName.getText()+"</i>" : "<i>"+lblEmailId.getText()+"</i>"));
					delete.setPositiveButtonText(i18n.GL0190());						
					delete.setNegitiveButtonText(i18n.GL0142());
					delete.setDeleteValidate("delete");
					delete.setPixelSize(450, 344);		
					delete.show();
					delete.center();
				}
			};
			if(insertAtTop){
				panelPendingMembersList.insert(membersViewVc,0);
			}else{
				panelPendingMembersList.add(membersViewVc);
			}
	 }
	
	public void removePendiUserWidget(MembersViewVc membersViewVc,boolean isPendingList){
		membersViewVc.removeFromParent();
		if(isPendingList){
			getUiHandlers().getMembersListByCollectionId(classpageDo.getClasspageCode(), pendingOffsetValue-1, 1, "pending",false);
		}else{
			getUiHandlers().getActiveMembersListByCollectionId(classpageDo.getClasspageCode(),  (activeListPageNum*pageSize)-1, 1, "active",false,false);
		}
	}
	
	
	@Override
	public Anchor getSeeMorePendingLabel() {
		return ancPendingListSeeMore;
	}

	/**
	 * @function displayActiveMembersList 
	 * 
	 * @created_date : Mar 17, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param lstActiveMembers
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	@Override
	public void displayActiveMembersList(List<CollaboratorsDo> lstActiveMembers, boolean isNew, int totalCount,boolean increasePageNum) {
		setLoadingPanelVisibility(false);
		lblPleaseWait.setVisible(false);
		lblErrorMessage.setVisible(false);
		AppClientFactory.fireEvent(new GetStudentJoinListEvent(activeMemberCounter));
		if(increasePageNum){
			activeListPageNum++;
		}
		this.activeListTotalCount=totalCount;
		if(activeListTotalCount==0){
			lblActiveMembers.setVisible(true);
			lblActiveMembersDesc.setVisible(true);
			ancActiveListSeeMore.setVisible(false);
			Label noActiveStudents = new Label(i18n.GL1527());
			panelActiveMembersContainter.setVisible(true);
			noActiveStudents.getElement().addClassName(res.css().noActiveStudents());
			panelActiveMembersList.add(noActiveStudents);
		}else{
			for (int k=0; k<lstActiveMembers.size();k++){
				lblActiveMembers.setVisible(true);
				lblActiveMembersDesc.setVisible(true);
				panelNoMembers.setVisible(false);
				panelMembersList.setVisible(true);
				panelActiveMembersContainter.setVisible(true);
				insertActiveUserAfterDeletion(lstActiveMembers.get(k),isNew,totalCount,k);
				if((pageSize*activeListPageNum)<activeListTotalCount){
					ancActiveListSeeMore.setVisible(true);
				}else{
					ancActiveListSeeMore.setVisible(false);
				}
				enableInvite();
			}
		}
	}
	
	public void getPendingMembersList(){
		getUiHandlers().getMembersListByCollectionId(classpageDo.getClasspageCode(), 0, pageSize, "pending",true);	//this will callback displayPendingMembersList method ....
	}
	
	@Override
	 public void insertActiveUserAfterDeletion(final CollaboratorsDo lstActiveMembers, boolean isNew, int totalCount, int intPos){
		MembersViewVc membersViewVc = new MembersViewVc(AppClientFactory.getCurrentPlaceToken(), isNew, lstActiveMembers, classpageDo,intPos) {
			public void removeActiveUserObject(){
				ArrayList<String> arrayEmailId = new ArrayList<String>();
				arrayEmailId.add('"'+lstActiveMembers.getEmailId()+'"');
				getUiHandlers().removeUserFromClass(classpageDo,arrayEmailId.toString(),0,false,this);
			}
			@Override
			public void setCollabCount(int count, String type) {}
			@Override
			public void setStudentsListContainer(ClickEvent event) {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				DeletePopupViewVc delete = new DeletePopupViewVc() {
					@Override
					public void onClickPositiveButton(ClickEvent event) {
						removeActiveUserObject();
						Window.enableScrolling(true);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
						hide();
					}

					@Override
					public void onClickNegitiveButton(ClickEvent event) {
						Window.enableScrolling(true);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
						hide();
						
					}
				};
				delete.setPopupTitle(i18n.GL1548());
				delete.setNotes(i18n.GL0748());
				delete.setDescText(StringUtil.generateMessage(i18n.GL1547(), lblUserName.getText() != null && !lblUserName.getText().equalsIgnoreCase("") ? "<i>"+lblUserName.getText()+"</i>" : "<i>"+lblEmailId.getText()+"</i>"));
				delete.setPositiveButtonText(i18n.GL0190());						
				delete.setNegitiveButtonText(i18n.GL0142());
				delete.setDeleteValidate("delete");
				delete.setPixelSize(450, 344);		
				delete.show();
				delete.center();
			}
		};
		//this validation added for checking duplicate emailIds in pending list
		lblActivePleaseWait.setVisible(false);
		panelActiveMembersList.add(membersViewVc);
//		if (isNew){
//			panelActiveMembersList.insert(membersViewVc, intPos);
//		}else{
//			panelActiveMembersList.insert(membersViewVc, panelActiveMembersList.getWidgetCount());
//		}
	 }

	/**
	 * @function clearMembersListPanel 
	 * 
	 * @created_date : Mar 17, 2014
	 * 
	 * @description
	 * 
	 * 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	@Override
	public void clearMembersListPanel() {
		panelActiveMembersList.clear();
		panelPendingMembersList.clear();
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpages.classlist.IsClassListView#enableInvite()
	 */
	@Override
	public void enableInvite() {
		lblPleaseWait.setVisible(false);
		btnInvite.setVisible(true);
		
		if (overAllStudentsCount >= studentsLimitCount){
			btnInvite.setEnabled(false);
			btnInvite.getElement().addClassName("disabled");
			//Enable this line if we need to disable after max number of user/students invited.
			panelActions.getElement().addClassName(res.css().buttonTooltip());
		}else{
			btnInvite.setEnabled(true);
			btnInvite.getElement().removeClassName("disabled");
			
			panelActions.getElement().removeClassName(res.css().buttonTooltip());
		}
		
		createAutoSuggestBox();
	}

	@Override
	public void displayInvitationSuccessPopUp(int listSize) {
		Window.enableScrolling(false);
		SuccessPopupViewVc success = new SuccessPopupViewVc() {

			@Override
			public void onClickPositiveButton(ClickEvent event) {
				// TODO Auto-generated method stub
				this.hide();
				if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_SEARCH) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.RESOURCE_SEARCH)){
					Window.enableScrolling(false);
				}else{
					Window.enableScrolling(true);
				}
			}
			
		};
		success.setHeight("253px");
		success.setWidth("450px");
		success.setPopupTitle(i18n.GL1556());
		if (listSize>1){
			success.setDescText(i18n.GL1557());
		}else{
			success.setDescText(i18n.GL1360());
		}
		success.setPositiveButtonText(i18n.GL0190());
		success.center();
		success.show();
		
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpages.classlist.IsClassListView#getInviteButton()
	 */
	@Override
	public Button getInviteButton() {
		return btnInvite;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpages.classlist.IsClassListView#getLblPleaseWait()
	 */
	@Override
	public Label getLblPleaseWait() {
		return lblPleaseWait;
	}

	@Override
	public void clearDataAndErrorMessages() {
		createAutoSuggestBox();
        lblErrorMessage.setVisible(false);
	}
	
	@UiHandler("ancprivacy")
	public void onClickPrivacyAnchor(ClickEvent clickEvent){
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		termsOfUse=new TermsOfUse(){

			@Override
			public void openParentPopup() {
				Window.enableScrolling(true);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
			}
			
		};
		termsOfUse.show();
		termsOfUse.setSize("902px", "300px");
		termsOfUse.center();
		termsOfUse.getElement().getStyle().setZIndex(999);//To display the view in collection player.
	}
}
