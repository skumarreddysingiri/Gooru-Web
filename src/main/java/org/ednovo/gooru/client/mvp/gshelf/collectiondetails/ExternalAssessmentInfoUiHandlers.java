package org.ednovo.gooru.client.mvp.gshelf.collectiondetails;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;

public interface ExternalAssessmentInfoUiHandlers extends BaseUiHandlers {
	/**
	 * This method is used for checking profanity
	 * @param textValue
	 * @param isCreate
	 * @param type
	 */
	public void checkProfanity(String textValue,boolean isCreate,int index);
	/**
	 * To Create Course
	 * @param createObj {@link String} 
	 * @param isCreateAssessment {@link boolean} 
	 */
	void createAndSaveAssessmentDetails(CreateDo createObj,boolean isCreateAssessment);
	/**
	 * To update the course details
	 * @param courseTitle {@link String} 
	 */
	void updateAssessmentDetails(CreateDo createOrUpDate, String id,boolean isCreateAssessment);
}