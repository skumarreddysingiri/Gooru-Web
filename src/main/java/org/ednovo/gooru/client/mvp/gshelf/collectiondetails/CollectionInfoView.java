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
package org.ednovo.gooru.client.mvp.gshelf.collectiondetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.library.DomainStandardsDo;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.CenturySkillsView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.DepthKnowledgeView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.LanguageView;
import org.ednovo.gooru.client.mvp.gshelf.util.CourseGradeWidget;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.InfoUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author Search Team
 *
 */
public class CollectionInfoView extends BaseViewWithHandlers<CollectionInfoUiHandlers> implements IsCollectionInfoView {

	private static CollectionInfoViewUiBinder uiBinder = GWT.create(CollectionInfoViewUiBinder.class);

	@UiTemplate("CollectionInfoView.ui.xml")
	interface CollectionInfoViewUiBinder extends UiBinder<Widget, CollectionInfoView> {
	}	

	@UiField HTMLPanel collectionInfo,newdok,newtype,thumbnailImageContainer,standardsUI;
	@UiField TextBox collectionTitle;
	@UiField Button saveCollectionBtn,uploadImageLbl;
	@UiField TextArea learningObjective;
	@UiField Label lblErrorMessage, lblErrorMessageForLO,newlbl;
	@UiField Image collThumbnail;
	@UiField Anchor dok,centurySkills,languageObj;
	@UiField HTMLEventPanel btnStandardsBrowse;
	@UiField UlPanel standardsDropListValues;
	@UiField DepthKnowledgeView depthOfKnowledgeContainer;
	@UiField LanguageView languageObjectiveContainer;
	@UiField CenturySkillsView centurySkillContainer;
	@UiField PPanel colltitle,collimagetitle,tagcollectiontitle;
	@UiField UlPanel ulSelectedItems;
	
	
	private boolean isLanguageObjectInfo=false;
	private boolean isCenturySkillsInfo=false;    
	private boolean isDepthOfKnlzeInfo = false;
	
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);
	List<Integer> selectedValues=new ArrayList<Integer>();
	
	String[] standardsTypesArray = new String[]{i18n.GL3379(),i18n.GL3322(),i18n.GL3323(),i18n.GL3324(),i18n.GL3325()};
	
	private String type="";

	private static final String ASSESSMENT = "assessment";

	private static final String DEFULT_ASSESSMENT_IMG = "images/default-assessment-image -160x120.png";

	private static final String DEFULT_COLLECTION_IMG = "images/default-collection-image-160x120.png";

	

	final String COLLECTION = "collection";
	private static final String ASSESSMENT_URL = "assessment/url";

	CourseGradeWidget courseGradeWidget;
	public FolderDo courseObjG;
	final String ACTIVE="active";
	

	/**
	 * Class constructor 
	 * @param eventBus {@link EventBus}
	 */
	@Inject
	public CollectionInfoView() {
		setWidget(uiBinder.createAndBindUi(this));
		collectionInfo.getElement().setId("pnlCollectionInfo");
		
		depthOfKnowledgeContainer.setVisible(true);
		languageObjectiveContainer.setVisible(false);
		centurySkillContainer.setVisible(false);
		uploadImageLbl.setText(i18n.GL0912());
		populateStandardValues();
		btnStandardsBrowse.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(!standardsDropListValues.getElement().getAttribute("style").equalsIgnoreCase("display:block;")){
					standardsDropListValues.getElement().setAttribute("style", "display:block;");
				}else{
					standardsDropListValues.getElement().removeAttribute("style");
				}
			}
		});
		
		dok.addClickHandler(new dokClickHandlers());
		centurySkills.addClickHandler(new CenturySkillsClickHandlers());
		languageObj.addClickHandler(new Language_ObjectiveClickHandlers());
	}	

	/**
	 * This inner class is used to get selected subjects grades
	 */
	class ClickOnSubject implements ClickHandler{
		String selectedText;
		LiPanel liPanel;
		int subjectId;
		ClickOnSubject(String selectedText,LiPanel liPanel,int subjectId){
			this.selectedText=selectedText;
			this.liPanel=liPanel;
			this.subjectId=subjectId;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(liPanel.getStyleName().contains(ACTIVE)){
				liPanel.removeStyleName(ACTIVE);
			}else{
				liPanel.addStyleName(ACTIVE);
			}
		}
	}

	@Override
	public void setCollectionType(String collectionType) {
			if(collectionType.equalsIgnoreCase("collection"))
			{
			collThumbnail.setUrl(DEFULT_COLLECTION_IMG);
			}
			else
			{
			collThumbnail.setUrl(DEFULT_ASSESSMENT_IMG);
			}

	}
	public void displayStandardsList(final List<DomainStandardsDo> standardsList){
		standardsUI.clear();
		for(int i=0;i<standardsList.size();i++)
		{
			final StandardsCodeDecView standardsCode = new StandardsCodeDecView(standardsList.get(i));
			final DomainStandardsDo domainStand = standardsList.get(i);
			standardsCode.getWidgetContainer().addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					standardsCode.setStyleName("active");
					
					if(!selectedValues.contains(domainStand.getCodeId())){
						selectedValues.add(domainStand.getCodeId());
					}
					
					final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(domainStand.getCode());
					liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							//This will remove the selected value when we are trying by close button
							if(selectedValues.contains(domainStand.getCodeId())){
								selectedValues.remove(domainStand);
							}
							standardsCode.removeStyleName("active");
							removeGradeWidget(ulSelectedItems,domainStand.getCodeId());
							liPanelWithClose.removeFromParent();
						}
					});
					//selectedValues.add(domainStand.getCodeId());
					liPanelWithClose.setId(domainStand.getCodeId());
					liPanelWithClose.setName(domainStand.getCode());
					liPanelWithClose.setRelatedId(domainStand.getCodeId());
					ulSelectedItems.add(liPanelWithClose);
				}
			});
			standardsUI.add(standardsCode);
		}
		

	}
	/**
	 * This method will remove the widget based on the codeId in the UlPanel
	 * @param ulPanel
	 * @param codeId
	 */
	public void removeGradeWidget(UlPanel ulPanel,long codeId){
		Iterator<Widget> widgets=ulPanel.iterator();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				if(obj.getId()==codeId){
					obj.removeFromParent();
				}
			}
			if(widget instanceof LiPanel){
				LiPanel obj=(LiPanel) widget;
				if(obj.getCodeId()==codeId){
					obj.removeStyleName("active");
				}
			}
		}
	}
	public void populateStandardValues(){
		for(int i=0; i<standardsTypesArray.length; i++){		
			List<String> standardsDescriptionList = Arrays.asList(standardsTypesArray[i].toString().split(","));
			LiPanel liPanel = new LiPanel();
			for(int j=0; j<standardsDescriptionList.size(); j++){
				HTMLPanel headerDiv = new HTMLPanel("");
				if(j==0){
					if(standardsDescriptionList.get(j).toString().equalsIgnoreCase("CA CCSS")){
						liPanel.getElement().setId("CA");
					}else{
						liPanel.getElement().setId(standardsDescriptionList.get(j).toString());
					}
					headerDiv.setStyleName("liPanelStyle");
				}else{
					headerDiv.setStyleName("liPanelStylenonBold");	
					liPanel.getElement().setAttribute("standarddesc", standardsDescriptionList.get(j).toString());
				}
				headerDiv.getElement().setInnerHTML(standardsDescriptionList.get(j).toString());
				liPanel.add(headerDiv);
			}
			liPanel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {				
					String standardsVal = event.getRelativeElement().getAttribute("id");
					String standardsDesc = event.getRelativeElement().getAttribute("standarddesc");
					getUiHandlers().showStandardsPopup(standardsVal,standardsDesc);
				}
			});
			standardsDropListValues.add(liPanel);
		}

	}

	public void setDetaultImage(String collectionType){
		collThumbnail.setUrl(COLLECTION.equalsIgnoreCase(collectionType)?DEFULT_COLLECTION_IMG:DEFULT_ASSESSMENT_IMG);
	}
	

	

	@Override
	public void setCouseData(final FolderDo courseObj, String type) {
		this.type = type;

		if(courseObj!=null){
			this.courseObjG=courseObj;
			courseObjG.setCollectionType(type);
			if(courseObj.getThumbnails()!=null){
				collThumbnail.setUrl(courseObj.getThumbnails().getUrl());
			}else{
				setDetaultImage(courseObj.getType());
			}
		}
		setStaticData(type);			
		collectionTitle.setText((courseObj==null&&COLLECTION.equalsIgnoreCase(type))?i18n.GL3367():
								(courseObj==null&&ASSESSMENT.equalsIgnoreCase(type))?i18n.GL3460():courseObj.getTitle());

		collThumbnail.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				collThumbnail.setUrl((COLLECTION.equalsIgnoreCase(CollectionInfoView.this.type))?DEFULT_COLLECTION_IMG:DEFULT_ASSESSMENT_IMG);
			}
		});
	}
	public void setStaticData(String type)
	{   
		if(type.equalsIgnoreCase(ASSESSMENT))
		{
			colltitle.setText(i18n.GL3381());
			collimagetitle.setText(i18n.GL3382());
			thumbnailImageContainer.setStyleName("assessmentThumbnail");
			tagcollectiontitle.setText(i18n.GL3385());
			saveCollectionBtn.setText(i18n.GL3386());
		}
		else
		{
			colltitle.setText(i18n.GL3380());
			collimagetitle.setText(i18n.GL3383());
			thumbnailImageContainer.setStyleName("collectionThumbnail");	
			tagcollectiontitle.setText(i18n.GL3384());
			saveCollectionBtn.setText(i18n.GL3368());
		}
	}
	@UiHandler("saveCollectionBtn")
	public void clickOnSaveCourseBtn(ClickEvent saveCourseEvent){
		getUiHandlers().checkProfanity(collectionTitle.getText().trim(),true,0,type);
	}
	
	@UiHandler("uploadImageLbl")
	public void clickOnUploadImg(ClickEvent saveCourseEvent){
		CreateDo createOrUpDate=new CreateDo();
		createOrUpDate.setTitle(collectionTitle.getText());
		createOrUpDate.setDescription(learningObjective.getText());
		createOrUpDate.setCollectionType(type);
		getUiHandlers().uploadCollectionImage(createOrUpDate);
	}	
	
	/**
	 * This method is used to call create and update API
	 * @param index
	 * @param isCreate
	 */
	@Override
	public void callCreateAndUpdate(boolean isCreate, Boolean result, int index,String collectionType) {
		if(result && index==0){
			SetStyleForProfanity.SetStyleForProfanityForTextBox(collectionTitle, lblErrorMessage, result);
		}else if(result && index==1){
			SetStyleForProfanity.SetStyleForProfanityForTextArea(learningObjective, lblErrorMessageForLO, result);
		}else{
			if(index==0){
				getUiHandlers().checkProfanity(learningObjective.getText().trim(),true,1,collectionType);
			}else if(index==1){
				CreateDo createOrUpDate=new CreateDo();
				createOrUpDate.setTitle(collectionTitle.getText());
				createOrUpDate.setDescription(learningObjective.getText());
				createOrUpDate.setCollectionType(collectionType);
				String id= AppClientFactory.getPlaceManager().getRequestParameter("id",null);
				if(id!=null){
					getUiHandlers().updateCourseDetails(createOrUpDate,id,isCreate);
				}else{
					getUiHandlers().createAndSaveCourseDetails(createOrUpDate,isCreate);
				}
			}
		}
	}
	
	private class dokClickHandlers implements ClickHandler{
		public dokClickHandlers() {
		}
		@Override
		public void onClick(ClickEvent event) {
			setSelectedDepathOfKnowledge();
			
		}
	}
	private class CenturySkillsClickHandlers implements ClickHandler{
		public CenturySkillsClickHandlers() {
		}
		@Override
		public void onClick(ClickEvent event) {
			setSelectedCenturySkills();
		}
	}
	private class Language_ObjectiveClickHandlers implements ClickHandler{
		public Language_ObjectiveClickHandlers() {
		}
		@Override
		public void onClick(ClickEvent event) {
			setSelectedLanguageObjective();

			
		}
	}
	protected void setDepthOfKnlze() {
		List<String> depthofknowledgedetails = new ArrayList<String>();

		if(courseObjG.getDepthOfKnowledges()!=null){
			if(courseObjG.getDepthOfKnowledges().size()>0){
				for(int i=0;i<courseObjG.getDepthOfKnowledges().size();i++){
					if(courseObjG.getDepthOfKnowledges().get(i).isSelected())
					{
						depthofknowledgedetails.add(courseObjG.getDepthOfKnowledges().get(i).getValue());
						isDepthOfKnlzeInfo = true;
					}
				}
				InfoUtil.setDepthofknowledgeDetails(depthofknowledgedetails, newtype, newlbl, newdok);
				//dKnowledgePanel.setVisible(true);
			}else{
				newdok.setVisible(false);
				isDepthOfKnlzeInfo = false;
			}
		}else{
			newdok.setVisible(false);
			isDepthOfKnlzeInfo = false;
		}
	}
	
	public void setSelectedDepathOfKnowledge(){
		if(isDepthOfKnlzeInfo){
			dok.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3376());
			depthOfKnowledgeContainer.setVisible(false);
			isDepthOfKnlzeInfo=false;
		}else{
			dok.setText("-"+"  "+i18n.GL3376());
			depthOfKnowledgeContainer.setVisible(true);
			isDepthOfKnlzeInfo=true;
		}
	}
	public void setSelectedCenturySkills(){
		if(isCenturySkillsInfo){
			centurySkills.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3377());
			centurySkillContainer.setVisible(false);
			isCenturySkillsInfo=false;
		}else{
			centurySkills.setText("-"+"  "+i18n.GL3377());
			centurySkillContainer.setVisible(true);
			isCenturySkillsInfo=true;
		}
	}
	public void setSelectedLanguageObjective(){
		if(isLanguageObjectInfo){
			languageObj.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3378());
			languageObjectiveContainer.setVisible(false);
			isLanguageObjectInfo=false;

		}else{
			languageObj.setText("-"+"  "+i18n.GL3378());
			languageObjectiveContainer.setVisible(true);
			isLanguageObjectInfo=true;
		}
	}
}
