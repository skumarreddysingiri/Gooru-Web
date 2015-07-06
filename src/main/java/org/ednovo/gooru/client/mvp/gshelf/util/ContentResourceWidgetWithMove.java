package org.ednovo.gooru.client.mvp.gshelf.util;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.event.InvokeLoginEvent;
import org.ednovo.gooru.client.mvp.addTagesPopup.AddTagesPopupView;
import org.ednovo.gooru.client.mvp.gshelf.collectioncontent.CollectionContentPresenter;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.SuccessPopupViewVc;
import org.ednovo.gooru.client.uc.ConfirmationPopupVc;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.util.ImageUtil;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public abstract class ContentResourceWidgetWithMove extends Composite{

	private static ContentResourceWidgetWithMoveUiBinder uiBinder = GWT
			.create(ContentResourceWidgetWithMoveUiBinder.class);

	interface ContentResourceWidgetWithMoveUiBinder extends
			UiBinder<Widget, ContentResourceWidgetWithMove> {
	}

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	//All Ui fields
	@UiField Label lblTopArrow,lblDownArrow,lblItemSequence,lblResourceTitle,videoTimeField,fromLblDisplayText,startStopTimeDisplayText,
				   lblUpdateTextMessage,lblCharLimit,narrationAlertMessageLbl,lblStartPage,lblEndPage,lblEditSartPageText,lblError;
	@UiField HTMLPanel pnlArrows,pnlNarration,pnlYoutubeContainer,pnlTimeIcon,pnlEditContainer;
	@UiField FlowPanel actionVerPanel,narrationConatainer,pnlPdfEdiContainer;
	@UiField TextBox txtMoveTextBox,startpdfPageNumber,stoppdfPageNumber;
	@UiField TextArea narrationTxtArea;
	@UiField UlPanel ulGradePanel;
	@UiField Anchor updateResourceBtn,editInfoLbl,editVideoTimeLbl,editStartPageLbl,copyResource,confirmDeleteLbl,addTages;
	@UiField HTML resourceNarrationHtml;
	@UiField Image imgDisplayIcon;
	@UiField Button btnEdit;

	//final strings
	private static final String VIDEO_TIME =i18n.GL0974();
	private static final String START_PAGE=i18n.GL0961();
	private static final String DEFAULT_START_PAGE="1";
	private static final String START_MINUTE="00";
	private static final String START_SEC="00";
	private static final String END_MINUTE="00";
	private static final String END_SEC="00";
	private static final String ADD_NARRATION_FOR_YOUR_VIEWERS =i18n.GL0967();
	private static final String MESSAGE_CONTENT =i18n.GL0968();
	private static final String MESSAGE_HEADER =i18n.GL0748();
	private static final String VALID_END_PAGE = i18n.GL2025();

	boolean youtube,isPdf;
	boolean isHavingBadWords=false;
	boolean isEditResourceClicked=false;

	private int totalVideoLength;
	Integer totalPages;

	CollectionItemDo collectionItem;

	CollectionContentPresenter collectionContentPresenter;

	private ConfirmationPopupVc deleteConfirmationPopupVc;
	AddTagesPopupView popup;

	public ContentResourceWidgetWithMove(int index,CollectionItemDo collectionItem) {
		this.collectionItem=collectionItem;
		initWidget(uiBinder.createAndBindUi(this));
		lblTopArrow.addClickHandler(new ArrowClickHandler(false));
		lblDownArrow.addClickHandler(new ArrowClickHandler(true));
		narrationTxtArea.getElement().setAttribute("maxlength", "500");
		narrationTxtArea.getElement().setId("tatNarrationTxtArea");

		actionVerPanel.setVisible(false);
		lblUpdateTextMessage.setVisible(false);
		narrationConatainer.setVisible(false);
		pnlPdfEdiContainer.setVisible(false);
		lblEditSartPageText.setVisible(false);
		startStopTimeDisplayText.setVisible(false);

		ulGradePanel.setStyleName("dropdown-menu");
		ulGradePanel.setVisible(false);
		actionVerPanel.getElement().setId("fpnlActionVerPanel");

		videoTimeField.setText(VIDEO_TIME);
		StringUtil.setAttributes(videoTimeField.getElement(), "lblVideoTimeField", VIDEO_TIME, VIDEO_TIME);

		lblStartPage.setText(i18n.GL0961());
		StringUtil.setAttributes(videoTimeField.getElement(), "lblStartPageLbl",  i18n.GL0961(),  i18n.GL0961());

		lblEndPage.setText(i18n.GL2026());
		StringUtil.setAttributes(lblEndPage.getElement(), "lblEndPage",i18n.GL2026(),i18n.GL2026());

		fromLblDisplayText.getElement().setId("lblFromLblDisplayText");
		setData(index,collectionItem);
		MouseOutHandler mouseOutHandler=new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				//resetNarrationDetails();
			}
		};
		this.addDomHandler(mouseOutHandler, MouseOutEvent.getType());

		btnEdit.getElement().setAttribute("aria-expanded", "false");
		btnEdit.getElement().setAttribute("aria-haspopup", "true");
		btnEdit.getElement().setAttribute("data-toggle", "dropdown");

		InlineLabel caret = new InlineLabel();
		caret.setStyleName("caret");

		Event.addNativePreviewHandler(new NativePreviewHandler() {
	        public void onPreviewNativeEvent(NativePreviewEvent event) {
	        	hidePopup(event);
	          }
	    });
	}

	protected void hidePopup(NativePreviewEvent event) {
		if(event.getTypeInt()==Event.ONCLICK){
    		Event nativeEvent = Event.as(event.getNativeEvent());
        	boolean target=eventTargetsPopup(nativeEvent);
        	if(!target){
        		if (isEditResourceClicked && ulGradePanel != null){
        			ulGradePanel.setVisible(ulGradePanel.isVisible() ? false : true);
        			isEditResourceClicked = false;
        		}
        	}
    	}
	}

	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			return  btnEdit.getElement().isOrHasChild(Element.as(target));
		}
		return false;
	}
	public void setData(int index,CollectionItemDo collectionItem){
		int indexVal=index+1;
		if(indexVal==1){
			lblTopArrow.setVisible(false);
		}

		lblItemSequence.setText(indexVal+"");
		lblResourceTitle.getElement().setInnerHTML(collectionItem.getResourceTitle()!=null? StringUtil.removeAllHtmlCss(collectionItem.getResourceTitle()):"");
		pnlNarration.getElement().setInnerHTML(collectionItem.getNarration()!=null?(collectionItem.getNarration().trim().isEmpty()?i18n.GL0956():collectionItem.getNarration()):i18n.GL0956());

		String resourceType = collectionItem.getResource().getResourceType().getName();
		youtube = resourceType.equalsIgnoreCase(ImageUtil.YOUTUBE);
		checkYoutubeResourceOrNot(collectionItem,youtube);
		enableEditInfoButton();

		txtMoveTextBox.setText(indexVal+"");
		txtMoveTextBox.getElement().setAttribute("index",index+"");
		txtMoveTextBox.getElement().setAttribute("moveId",collectionItem.getCollectionItemId()+"");
		txtMoveTextBox.addKeyPressHandler(new HasNumbersOnly());
		txtMoveTextBox.addKeyUpHandler(new ReorderText());
		//This blur handler reset the previous value when the text box value is empty.
		txtMoveTextBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				String enteredString=txtMoveTextBox.getText().toString().trim();
				String currentWidgetString=txtMoveTextBox.getElement().getAttribute("index").trim();
				if(enteredString.isEmpty()){
					txtMoveTextBox.setText((Integer.parseInt(currentWidgetString)+1)+"");
				}
			}
		});
	}
	/**
	 * This method is used to enable or disabling the editinfo button
	 * @param collectionItem
	 */
	private void enableEditInfoButton() {
		// To check whether resource is public and is created by logged in user
		String resourceShare = collectionItem.getResource().getSharing();
		String resourceCategory = collectionItem.getResource().getResourceFormat() !=null ? collectionItem.getResource().getResourceFormat().getDisplayName() : "text";
		if (resourceShare.equalsIgnoreCase("public") && !resourceCategory.equalsIgnoreCase("question")) {
			editInfoLbl.setVisible(false);
		} else if (resourceShare.equalsIgnoreCase("public")	&& resourceCategory.equalsIgnoreCase("question") && checkLoggedInUser()) {
			editInfoLbl.setVisible(true);
		} else if (resourceShare.equalsIgnoreCase("private") && !resourceCategory.equalsIgnoreCase("question") && checkLoggedInUser()) {
			editInfoLbl.setVisible(true);
		} else if (!checkLoggedInUser()) {
			editInfoLbl.setVisible(false);
		}
	}
	/**
	 * This method is used to check whether the user is logged in user or not.
	 * @return
	 */
	public boolean checkLoggedInUser() {
		boolean isValid = true;
		String gooruUId = "";
		if(collectionItem.getResource().getUser()==null){
			 gooruUId=collectionItem.getGooruUId();
		}else{
			 gooruUId=collectionItem.getResource().getUser().getGooruUId();
		}
		if (AppClientFactory.getLoggedInUser().getGooruUId().equalsIgnoreCase(gooruUId)) {
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
	}
	/**
	 * This inner class used for disabling up and down arrow based on user entered reorder value.
	 */
	public class ReorderText implements KeyUpHandler {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			String enteredString=txtMoveTextBox.getText().toString().trim();
			String currentWidgetString=txtMoveTextBox.getElement().getAttribute("index");
			if(!enteredString.isEmpty()){
				int enteredValue=Integer.parseInt(enteredString);
				int currentWidgetValue=Integer.parseInt(currentWidgetString)+1;
				if(currentWidgetValue==enteredValue){
					lblDownArrow.setVisible(true);
					lblTopArrow.setVisible(true);
				}else if(currentWidgetValue>enteredValue){
					lblDownArrow.setVisible(false);
					lblTopArrow.setVisible(true);
				}else{
					lblTopArrow.setVisible(false);
					lblDownArrow.setVisible(true);
				}
			}
		}
	}
	/**
	 * This inner class used for to restrict text box values to have only numbers
	 *
	 */
	public class HasNumbersOnly implements KeyPressHandler {
		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (!Character.isDigit(event.getCharCode())
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_SHIFT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_ENTER
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_LEFT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_RIGHT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_DELETE){
				((TextBox) event.getSource()).cancelKey();
			}
			if (event.getNativeEvent().getKeyCode() == 46
					|| event.getNativeEvent().getKeyCode() == 37) {
				((TextBox) event.getSource()).cancelKey();
			}
		}
	}
	/**
	 * This inner class will handle the click event on the Arrows
	 */
	class ArrowClickHandler implements ClickHandler{
		boolean isDownArrow;
		ArrowClickHandler(boolean isDownArrow){
			this.isDownArrow=isDownArrow;
		}
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {
				@Override
				public void onSuccess() {
					String movingPosition=txtMoveTextBox.getText().toString().trim();
					String currentWidgetPosition=txtMoveTextBox.getElement().getAttribute("index").trim();
					String moveId=txtMoveTextBox.getElement().getAttribute("moveId");
					if(!movingPosition.isEmpty()){
						int movingValue=Integer.parseInt(movingPosition);
						int currentWidgetValue=Integer.parseInt(currentWidgetPosition);
						//This one will execute when user enter a number in text field and click on either up and down arrow.
						if(movingValue!=(currentWidgetValue+1)){
							moveWidgetPosition(movingPosition,currentWidgetPosition,isDownArrow,moveId);
						}else if(movingValue==(currentWidgetValue+1)){
							//This one will execute when user directly clicks on either up and down arrow.
							if(isDownArrow){
								moveWidgetPosition((movingValue+1)+"",currentWidgetPosition,isDownArrow,moveId);
							}else{
								moveWidgetPosition((movingValue-1)+"",currentWidgetPosition,isDownArrow,moveId);
							}
						}
					}
				}
			});
		}
	}
	public Label getTopArrow(){
		return lblTopArrow;
	}
	public Label getDownArrow(){
		return lblDownArrow;
	}
	/**
	 * This method is used to check whether the resource is youtube resource or not and if it is will display the duration of that resource
	 * @param collectionItemDo
	 * @param youtube
	 */
	public void checkYoutubeResourceOrNot(CollectionItemDo collectionItemDo,boolean youtube){
		if (youtube){
			isPdf=false;
			imgDisplayIcon.setUrl("images/timeIcon.png");
			enableOrDisableYoutubeFields(true);
			videoTimeField.setText(VIDEO_TIME);
			videoTimeField.getElement().setAttribute("alt", VIDEO_TIME);
			videoTimeField.getElement().setAttribute("title", VIDEO_TIME);
			String stopTime = (collectionItemDo.getStop() == null) ? "00:00:00": collectionItemDo.getStop();
			String startTime = (collectionItemDo.getStart() == null) ? "00:00:00": collectionItemDo.getStart();
			startTime = startTime.replaceAll("\\.", ":");
			stopTime = stopTime.replaceAll("\\.", ":");
			String youTubeVideoId = ResourceImageUtil.getYoutubeVideoId(collectionItemDo.getResource().getUrl());
			//This will set the end time of the video
			AppClientFactory.getInjector().getResourceService().getYoutubeDuration(youTubeVideoId,new SimpleAsyncCallback<String>() {
				@Override
				public void onSuccess(String youtubeInfo) {
					if (youtubeInfo != null) {
						totalVideoLength = Integer.parseInt(youtubeInfo);
						startStopTimeDisplayText.setText(i18n.GL0957()+checkForTwoDigits(totalVideoLength/60)+":"
						+checkForTwoDigits(totalVideoLength%60));
					}
				}
			});
			//This if block will set the youtube resource time if already exists.
			if (!"00:00:00".equalsIgnoreCase(stopTime) ||!"00:00:00".equalsIgnoreCase(startTime)) {
					String[] VideoStartTime=startTime.split(":");
					String[] VideoEndTime=stopTime.split(":");
					String startMm=Integer.parseInt(VideoStartTime[0])*60+Integer.parseInt(VideoStartTime[1])+"";
					String startSec =null;
					String endSec = null;
					if (VideoStartTime.length>2){
						startSec=Integer.parseInt(VideoStartTime[2])+"";
					}else{
						startSec="00";
					}
					String endMm=Integer.parseInt(VideoEndTime[0])*60+Integer.parseInt(VideoEndTime[1])+"";
					if (VideoEndTime.length>2){
						endSec=Integer.parseInt(VideoEndTime[2])+"";
					}else{
						endSec="00";
					}
					String displayTime=checkLengthOfSting(startMm)+":"+checkLengthOfSting(startSec)
							+" "+i18n.GL_GRR_Hyphen()+" "+checkLengthOfSting(endMm)+":"+checkLengthOfSting(endSec);
					fromLblDisplayText.setText(displayTime);
					StringUtil.setAttributes(fromLblDisplayText.getElement(),displayTime, displayTime);
			}else{
			   String videoId = ResourceImageUtil.getYoutubeVideoId(collectionItemDo.getResource().getUrl());
				if (videoId != null) {
					AppClientFactory.getInjector().getResourceService().getYoutubeDuration(videoId, new SimpleAsyncCallback<String>() {
						@Override
						public void onSuccess(String youtubeInfo) {
							if(youtubeInfo != null) {
								totalVideoLength = Integer.parseInt(youtubeInfo);
								String displayTime=START_MINUTE	+ ":"+ START_SEC+i18n.GL_GRR_Hyphen()
										+checkForTwoDigits(totalVideoLength/60)+ ":"+ checkForTwoDigits(totalVideoLength%60);
								fromLblDisplayText.setText(displayTime);
								StringUtil.setAttributes(fromLblDisplayText.getElement(),displayTime, displayTime);
							}else{
								String displayTime=START_MINUTE+":"+ START_SEC
												+" "+i18n.GL_GRR_Hyphen()+" "
												+ START_MINUTE+":"+ END_MINUTE+":"+END_SEC;
								fromLblDisplayText.setText(displayTime);
								StringUtil.setAttributes(fromLblDisplayText.getElement(),displayTime, displayTime);
							}
						}
					});
				}
			}
		}else if(collectionItemDo.getResource() !=null && collectionItemDo.getResource().getUrl() != null && collectionItemDo.getResource().getUrl().endsWith(".pdf")){
			isPdf=true;
			imgDisplayIcon.setUrl("images/note.png");

			enableOrDisableYoutubeFields(true);
			editVideoTimeLbl.setVisible(false);
			videoTimeField.setVisible(false);

			fromLblDisplayText.setVisible(true);
			editStartPageLbl.setVisible(true);

			String startPageNumber=collectionItemDo.getStart();
			totalPages = collectionItemDo.getTotalPages();
			if(totalPages == null){
				fromLblDisplayText.setVisible(false);
				editStartPageLbl.setVisible(false);
				imgDisplayIcon.setVisible(false);
			}else{
				fromLblDisplayText.setVisible(true);
				lblEditSartPageText.setText(i18n.GL2039() + totalPages);
				editStartPageLbl.setVisible(true);
				imgDisplayIcon.setVisible(true);
			}
			String endPageNumber=collectionItemDo.getStop();
			if(startPageNumber==null){
				startpdfPageNumber.setText("1");
				StringUtil.setAttributes(startpdfPageNumber.getElement(), "1", "1");
				fromLblDisplayText.setText(START_PAGE+DEFAULT_START_PAGE);
				StringUtil.setAttributes(fromLblDisplayText.getElement(),START_PAGE+DEFAULT_START_PAGE, START_PAGE+DEFAULT_START_PAGE);
			}else{
				String pdfText = "";
				if(endPageNumber!=null){
					if(endPageNumber.equalsIgnoreCase("")){
						pdfText=START_PAGE+startPageNumber+" - "+ i18n.GL2026()+totalPages;
						fromLblDisplayText.setText(pdfText);
						stoppdfPageNumber.setText(totalPages+"");
					}else{
						pdfText=START_PAGE+startPageNumber+" - "+i18n.GL2026()+endPageNumber;
						fromLblDisplayText.setText(pdfText);
						stoppdfPageNumber.setText(endPageNumber+"");
					}
				}
				StringUtil.setAttributes(fromLblDisplayText.getElement(), pdfText, pdfText);
				startpdfPageNumber.setText(startPageNumber);
				StringUtil.setAttributes(startpdfPageNumber.getElement(), startPageNumber, startPageNumber);
			}
		}else{
			isPdf=false;
			enableOrDisableYoutubeFields(false);
		}
	}
	/**
	 * This method is used to check the given value is two digit number or not, if not it will add the 0.
	 * @param value
	 * @return
	 */
	public String checkForTwoDigits(int value){
		String valueString;
		if (value < 10) {
			valueString = "0"+ value;
		} else {
			valueString = value+ "";
		}
		return valueString;
	}
	/**
	 * This method is used to check length of a string and it will append the 0
	 * @param value
	 * @return
	 */
	public String checkLengthOfSting(String value){
		if(value.length()<2){
			value="0"+value;
		}
		return value;
	}
	/**
	 * This method is used to enable or disable the youtube related fields based on the boolean value.
	 * @param isTrue
	 */
	public void enableOrDisableYoutubeFields(boolean isTrue){
		pnlYoutubeContainer.setVisible(isTrue);
		pnlTimeIcon.setVisible(isTrue);
		editVideoTimeLbl.setVisible(isTrue);
		editStartPageLbl.setVisible(false);
	}
	public Label getItemSequenceLabel(){
		return lblItemSequence;
	}
	public TextBox getTextBox(){
		return txtMoveTextBox;
	}
	/**
	 * Edit collection item , update collection item
	 * @param clickEvent
	 */
	@UiHandler("updateResourceBtn")
	public void onEditClick(ClickEvent clickEvent) {
		MixpanelUtil.Organize_Click_Edit_Narration();
		enableDisableNarration(false);
		editAndUpdateResource();
		lblCharLimit.setVisible(true);
		pnlTimeIcon.setVisible(false);
		pnlYoutubeContainer.setVisible(false);
		resourceNarrationHtml.getElement().getStyle().setWidth(230, Unit.PX);
	}
	/*
	 * This clickEvent is used to cancel narration edit
	 */
	@UiHandler("btnCancel")
	public void onclickcancelNarrationBtn(ClickEvent event){
		resetNarrationDetails();
	}
	/**
	 * This method is used to reset the narration details on click of cancel and mouse out of this widget.
	 */
	public void resetNarrationDetails(){
		String narrationText=collectionItem.getNarration()!=null?collectionItem.getNarration():"";
		narrationTxtArea.setText(narrationText);
		StringUtil.setAttributes(narrationTxtArea.getElement(),narrationText, narrationText);
		if(youtube || isPdf){
			pnlTimeIcon.setVisible(true);
			pnlYoutubeContainer.setVisible(true);
			fromLblDisplayText.setVisible(true);
			pnlPdfEdiContainer.setVisible(false);
			lblEditSartPageText.setVisible(false);
			lblError.setVisible(false);
		}else{
			pnlTimeIcon.setVisible(false);
			pnlYoutubeContainer.setVisible(false);
		}
		enableDisableNarration(true);
		lblCharLimit.setVisible(false);
		resourceNarrationHtml.getElement().getStyle().clearWidth();
	}

	/**
	 * This method is used to enable and disable the narration fields
	 * @param isValue
	 */
	public void enableDisableNarration(boolean isValue){
		pnlEditContainer.setVisible(isValue);
		pnlArrows.setVisible(isValue);
		pnlNarration.setVisible(isValue);

		narrationConatainer.setVisible(!isValue);
		resourceNarrationHtml.setVisible(!isValue);
		actionVerPanel.setVisible(!isValue);
	}
	/**
	 * Update the collection item meta data
	 */
	public void editAndUpdateResource() {
		if (collectionItem.getNarration() != null) {
			String narrationText=collectionItem.getNarration();
			narrationTxtArea.setText(narrationText);
			StringUtil.setAttributes(narrationTxtArea.getElement(), narrationText, narrationText);
		}
		resourceNarrationHtml.getElement().getStyle().setWidth(230, Unit.PX);
		resourceNarrationHtml.setHTML(ADD_NARRATION_FOR_YOUR_VIEWERS);
		String value = StringUtil.generateMessage(i18n.GL2103(), "500");
		lblCharLimit.setText(value);
		lblCharLimit.setVisible(true);
		StringUtil.setAttributes(resourceNarrationHtml.getElement(), ADD_NARRATION_FOR_YOUR_VIEWERS, ADD_NARRATION_FOR_YOUR_VIEWERS);
	}
	/*
	 * This clickEvent is used to update narration
	 */
	@UiHandler("btnUpdate")
	public void onclickOfnarrationUpdate(ClickEvent event){
		if(youtube){

		}else if(isPdf){
			updatePdfStartPage();
		}else{
			lblUpdateTextMessage.setVisible(true);
			actionVerPanel.setVisible(false);
			Map<String, String> parms = new HashMap<String, String>();
			parms.put("text", narrationTxtArea.getText());
			AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
				@Override
				public void onSuccess(Boolean value) {
					isHavingBadWords = value;
					if (value){
						narrationAlertMessageLbl.addStyleName("narrationTxtArea titleAlertMessageActive");
						narrationAlertMessageLbl.removeStyleName("titleAlertMessageDeActive");

						narrationTxtArea.getElement().getStyle().setBorderColor("orange");
						narrationAlertMessageLbl.setText(i18n.GL0554());
						StringUtil.setAttributes(narrationAlertMessageLbl.getElement(), i18n.GL0554(), i18n.GL0554());

						narrationAlertMessageLbl.setVisible(true);
						actionVerPanel.setVisible(true);
						lblUpdateTextMessage.setVisible(true);
						MixpanelUtil.mixpanelEvent("Collaborator_edits_collection");
					}else{
						String narration = null;
						MixpanelUtil.Organize_Click_Edit_Narration_Update();

						if (resourceNarrationHtml.getHTML().length() > 0) {
							narration = trim(narrationTxtArea.getText());
							collectionItem.setNarration(narration);
							pnlNarration.getElement().setInnerHTML(collectionItem.getNarration()!=null?(collectionItem.getNarration().trim().isEmpty()?i18n.GL0956():collectionItem.getNarration()):i18n.GL0956());
						}
						try{
							updateNarration(collectionItem, narration);
							enableDisableNarration(true);
							//getPresenter().updateNarrationItem(collectionItem.getCollectionItemId(), narration);
						}catch(Exception e){
							AppClientFactory.printSevereLogger(e.getMessage());
						}
						lblUpdateTextMessage.setVisible(false);
						lblCharLimit.setVisible(false);
						resourceNarrationHtml.getElement().getStyle().clearWidth();
					}
				}
			});
		}
	}
	/**
	 * This method is used to update the pdf start and end page
	 */
	public void updatePdfStartPage(){
		String start = startpdfPageNumber.getText();
		String enteredStopPage = stoppdfPageNumber.getText();
		boolean isValid = this.validatePDF(start, enteredStopPage, totalPages);
		if (isValid) {
			MixpanelUtil.Organize_Click_Edit_Start_Page_Update();
			lblError.setText("");
			fromLblDisplayText.setVisible(true);
			actionVerPanel.setVisible(false);
			collectionContentPresenter.updateCollectionItem(collectionItem, collectionItem.getNarration(), start, enteredStopPage);
			enablePdfButtons(true);
		}
	}
	/**
	 * PDF validations
	 * @param startpage
	 * @param stopPage
	 * @param totalPage
	 * @return
	 */
	public boolean validatePDF(String startpage,String stopPage,Integer totalPage){
		boolean isValid;
		Integer enteredStopPage =	Integer.parseInt(stopPage);
		Integer startpdfpage = Integer.parseInt(startpage);
		if(enteredStopPage > totalPage){
			lblError.setText(VALID_END_PAGE);
			isValid = false;
		}else if( startpdfpage > totalPage){
			lblError.setText(VALID_END_PAGE);
			isValid = false;
		}else if( enteredStopPage < startpdfpage){
			lblError.setText(VALID_END_PAGE);
			isValid = false;
		}else{
			isValid = true;
			lblError.setText("");
		}
		return isValid;
	}
	/**
	 * Confirmation popup for collection item delete, delete collection item
	 * regarding the popup action
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("confirmDeleteLbl")
	public void deleteCollectionItem(ClickEvent clickEvent) {
		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(88, false));
		deleteConfirmationPopupVc = new ConfirmationPopupVc(MESSAGE_HEADER,MESSAGE_CONTENT) {
			@Override
			public void onDelete(ClickEvent clickEvent) {
				collectionContentPresenter.deleteCollectionItem(collectionItem.getCollectionItemId(), collectionItem.getItemSequence());
				deleteConfirmationPopupVc.hide();
				ContentResourceWidgetWithMove.this.removeFromParent();
			}
		};
	}
	/**
	 * This will handle the click event on the add tags for resoruce
	 * @param clickEvent
	 */
	@UiHandler("addTages")
	public void onAddTagesClick(ClickEvent clickEvent) {
		if(AppClientFactory.isAnonymous()) {
			AppClientFactory.fireEvent(new InvokeLoginEvent());
		} else {
			Window.enableScrolling(false);
			popup=new AddTagesPopupView(collectionItem.getResource().getGooruOid()){
				@Override
				public void closePoup(boolean isCancelclicked) {
			        this.hide();
			        if(!isCancelclicked){
			        SuccessPopupViewVc success = new SuccessPopupViewVc() {
						@Override
						public void onClickPositiveButton(ClickEvent event) {
							this.hide();
							if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_SEARCH) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.RESOURCE_SEARCH)){
								Window.enableScrolling(false);
							}else{
								Window.enableScrolling(true);
							}
						}
					};
					success.setPopupTitle(i18n.GL1795());
					success.setDescText(i18n.GL1796());
					success.enableTaggingImage();
					success.setPositiveButtonText(i18n.GL0190());
					success.center();
					success.show();
			        }else{
			        	if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_SEARCH) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.RESOURCE_SEARCH)){
			    			Window.enableScrolling(false);
			    		}else{
			    			Window.enableScrolling(true);
			    		}
			        }
				}
			};
			popup.show();
			popup.setPopupPosition(popup.getAbsoluteLeft(),Window.getScrollTop()+10);
		}
	}
	/*
	 * This clickEvent is used to edit pdf
	 */
	@UiHandler("editStartPageLbl")
	public void oneditStartPageLblClick(ClickEvent clickEvent) {
		MixpanelUtil.Organize_Click_Edit_Start_Page();
		enablePdfButtons(false);
	}

	@UiHandler("btnEdit")
	public void onEditResourceClick(ClickEvent event){
		if (ulGradePanel.isVisible()){
			ulGradePanel.setVisible(false);
			isEditResourceClicked = false;
		}else{
			ulGradePanel.setVisible(true);
			isEditResourceClicked = true;
		}

	}

	/**
	 * This method is used to enable and disable the pdf buttons
	 * @param isValue
	 */
	public void enablePdfButtons(boolean isValue){
		pnlPdfEdiContainer.setVisible(!isValue);
		lblEditSartPageText.setVisible(!isValue);
		actionVerPanel.setVisible(!isValue);

		pnlEditContainer.setVisible(isValue);
		pnlArrows.setVisible(isValue);
		fromLblDisplayText.setVisible(isValue);
	}
	/**
	 * This method is used to trim the text of rich text box.
	 * @function trim
	 *
	 * @description :
	 *
	 * @parm(s) : @param s
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public String trim(String s) {
	   String result = s.trim();
	   String x = result.replaceAll("<br>", "");
	   x = x.replaceAll("&nbsp;", "");
	   x = x.trim();
	   if(x.equals("")) {
	       return x;
	   } else {
	       return result;
	   }
	}
	public abstract void moveWidgetPosition(String movingPosition,String currentWidgetPosition,boolean isDownArrow,String moveId);

	public abstract void updateNarration(CollectionItemDo collectionItem,String narration);

	public void setPresenter(CollectionContentPresenter collectionContentPresenter) {
		this.collectionContentPresenter=collectionContentPresenter;
	}
}