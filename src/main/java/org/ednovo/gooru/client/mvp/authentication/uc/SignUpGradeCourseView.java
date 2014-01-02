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
package org.ednovo.gooru.client.mvp.authentication.uc;

import java.util.List;

import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.mvp.authentication.SignUpCBundle;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.uc.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.model.code.LibraryCodeDo;
import org.ednovo.gooru.shared.model.user.ProfileDo;
import org.ednovo.gooru.shared.model.user.UserDo;
import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.dom.client.Style.Float;;

public class SignUpGradeCourseView extends PopupPanel {

	@UiField HTMLPanel signupBgPanel, metaDataSelectionPanel, courseContainer;
	
	@UiField Label lblTitle, lblCancel, lblErrorMessage;
	
	@UiField FlowPanel registerGradeList;
	
	@UiField HTMLEventPanel mathCourseLbl, scienceCourseLbl, elaCourseLbl, socialCourseLbl;
	
	@UiField Button skipBtn, submitBtn;
	
	HTMLPanel mathCourseContainer, scienceCourseContainer, elaCourseContainer, socialCourseContainer;
	
	private int selectedCourseCount = 0;
	
	private List<LibraryCodeDo> libraryDo = null;
	
	private static final String SCIENCE_LBL = "Science";
	
	private static final String MATH_LBL = "Math";
	
	private static final String SOCIAL_LBL = "Social Sciences";

	private static final String ELA_LBL = "Language Arts";
	
	private static final String ARTS_HUMANITIES = "Arts & Humanities";
	
	private static final String TECH_ENGEE = "Technology & Engineering";
	
	@UiField Image imgLoading;
	
	
	
	UserDo userDo= null;
	
	private static SignUpGradeCourseViewUiBinder uiBinder = GWT
			.create(SignUpGradeCourseViewUiBinder.class);

	interface SignUpGradeCourseViewUiBinder extends
			UiBinder<Widget, SignUpGradeCourseView> {
	}
    /**
     * constructor
     * @param userDo
     */
	public SignUpGradeCourseView(UserDo userDo) {
		this.userDo = userDo;
		SignUpCBundle.INSTANCE.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		signupBgPanel.setWidth("700px");
		metaDataSelectionPanel.getElement().getStyle().setPadding(15, Unit.PX);
	
		lblTitle.setText(MessageProperties.GL0186 + MessageProperties.GL_SPL_EXCLAMATION);

		scienceCourseContainer = new HTMLPanel("");
		mathCourseContainer = new HTMLPanel("");
		socialCourseContainer = new HTMLPanel("");
		elaCourseContainer = new HTMLPanel("");
		
//		artsHumanitiesContainer = new HTMLPanel("");
//		techologyEngineeringContainer = new HTMLPanel("");
		
		imgLoading.setVisible(true);
		imgLoading.setUrl("images/core/B-Dot.gif");
		

		setRegisterGradeList();
		setRegisterCourseList();
		
		lblErrorMessage.setVisible(false);
		lblErrorMessage.setText(MessageProperties.GL0500);
		lblErrorMessage.getElement().getStyle().clearMarginLeft();
		lblErrorMessage.getElement().getStyle().clearWidth();
		lblErrorMessage.getElement().getStyle().setFloat(Float.RIGHT);
		lblErrorMessage.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
		lblErrorMessage.getElement().getStyle().setWidth(205, Unit.PX);

		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
        this.setGlassEnabled(true);
        this.removeStyleName("gwt-PopupPanel");
        this.getElement().getStyle().setZIndex(99999);
        this.show();
        this.center();
	}
	
	/**
	 * 
	 * @function closeSignUpGradeCourseView 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description : This method is used to open Thanks Popup.
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
	public void closeSignUpGradeCourseView() {
//		Window.enableScrolling(true);
//		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
//		String account = AppClientFactory.getPlaceManager().getRequestParameter("account") !=null ? AppClientFactory.getPlaceManager().getRequestParameter("account") : "regular";
//		if (account.equalsIgnoreCase("parent")){
//			OpenThanksPopup();
//		}else{
			AppClientFactory.fireEvent(new SetHeaderEvent(userDo));
			OpenThanksPopup();
//		}
		
	}
	/**
	 * 
	 * @function OpenThanksPopup 
	 * 
	 * @created_date : 26-Dec-2013
	 * 
	 * @description : This method is used to open Thanks Popup.
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
	private void OpenThanksPopup(){
		this.hide();
		ThanksPopupUc thanks = new ThanksPopupUc();
		if (AppClientFactory.getLoggedInUser().getAccountTypeId() == 2){
			thanks.setAccountType("normal");
		}
		thanks.center();
		thanks.show();
	}
	
	/**
	 * 
	 * @function setRegisterGradeList 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description  : This method is used to set the RegisterGradeList
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
	private void setRegisterGradeList() {
		registerGradeList.add(new SignupGradeLabel("Kindergarten", new ProfileDo()));
		for (int i = 1; i <= 12; i++) {
				registerGradeList.add(new SignupGradeLabel(i + "", new ProfileDo()));
		}
		registerGradeList.add(new SignupGradeLabel("Higher Education",new ProfileDo()));
	}

	/**
	 * 
	 * @function setRegisterCourseList 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description   :  This method is used to set the RegisterCourseList
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
	private void setRegisterCourseList() {
		mathCourseLbl.add(new InlineLabel(MATH_LBL));
		scienceCourseLbl.add(new InlineLabel(SCIENCE_LBL));
		elaCourseLbl.add(new InlineLabel(ELA_LBL));
		socialCourseLbl.add(new InlineLabel(SOCIAL_LBL));
//		artsAndHumanitiesLbl.add(new InlineLabel(ARTS_HUMANITIES));
//		technologyAndEngineeringLbl.add(new InlineLabel(TECH_ENGEE));

		imgLoading.setVisible(true);
		AppClientFactory.getInjector().getTaxonomyService().getCourse(new SimpleAsyncCallback<List<LibraryCodeDo>>() {
			@Override
			public void onSuccess(List<LibraryCodeDo> result) {
				libraryDo = result;
				if (libraryDo != null) {
					setCourseData(libraryDo.get(0),libraryDo.get(0).getLabel());
				}
			}
		});
	}
	
	/**
	 * 
	 * @function setCourseData 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description	based on api response data set the label and respect images
	 * 
	 * 
	 * @parm(s) : @param libraryCodeDo
	 * @parm(s) : @param courseLabel
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	private void setCourseData(LibraryCodeDo libraryCodeDo, String courseLabel) {
		List<LibraryCodeDo> liCodeDo = libraryCodeDo.getNode();
		for(int i = 0; i < liCodeDo.size(); i++) {
			SignupCourseLabel signupCourseLabel = new SignupCourseLabel(liCodeDo.get(i).getCodeId(), liCodeDo.get(i).getLabel().trim(), new ProfileDo(), "images/course/"+courseLabel.trim().toLowerCase().replaceAll(" ", "_")+"/"+liCodeDo.get(i).getLabel().trim().replaceAll(" ", "_")+".png", courseLabel) {
					@Override
					public int selectCourseLabel(boolean isSelected) {
							if(isSelected == true) {
								if(selectedCourseCount<=5) {
									selectedCourseCount++;
								}
							} else {
								selectedCourseCount--;
							}
						return selectedCourseCount;
					}

					@Override
					public void showErrorMessage(boolean value) {
						lblErrorMessage.setVisible(value);
					}
			};
			
			if(courseLabel.equalsIgnoreCase(MATH_LBL)) {
				mathCourseContainer.add(signupCourseLabel);
			} else if(courseLabel.equalsIgnoreCase(SCIENCE_LBL)) {
				scienceCourseContainer.add(signupCourseLabel);
			} else if(courseLabel.equalsIgnoreCase(ELA_LBL)) {
				elaCourseContainer.add(signupCourseLabel);
			} else if(courseLabel.equalsIgnoreCase(SOCIAL_LBL)) {
				socialCourseContainer.add(signupCourseLabel);
			}
//			else if(courseLabel.equalsIgnoreCase(ARTS_HUMANITIES)) {
//				 artsHumanitiesContainer.add(signupCourseLabel);
//			} else if(courseLabel.equalsIgnoreCase(TECH_ENGEE)) {
//				techologyEngineeringContainer.add(signupCourseLabel);
//			}
		}
		imgLoading.setVisible(false);
		if(courseLabel.equalsIgnoreCase(MATH_LBL)) {
			courseContainer.add(mathCourseContainer);
		} else if(courseLabel.equalsIgnoreCase(SCIENCE_LBL)) {
			courseContainer.add(scienceCourseContainer);
		} else if(courseLabel.equalsIgnoreCase(ELA_LBL)) {
			courseContainer.add(elaCourseContainer);
		} else if(courseLabel.equalsIgnoreCase(SOCIAL_LBL)) {
			courseContainer.add(socialCourseContainer);
		}
//		else if(courseLabel.equalsIgnoreCase(ARTS_HUMANITIES)) {
//			courseContainer.add(artsHumanitiesContainer);
//		} else if(courseLabel.equalsIgnoreCase(TECH_ENGEE)) {
//			courseContainer.add(techologyEngineeringContainer);
//		}
				
	}
	/* Ui Hanlders*/
	/**
	 * 
	 * @function clickScienceLbl 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description   : This UiHandler will set the science CourseContainer data when scienceCourseLbl clicked.
	 * 
	 * 
	 * @parm(s) : @param event
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	@UiHandler("scienceCourseLbl")
	public void clickScienceLbl(ClickEvent event) {
		removeStyleNames();
		scienceCourseLbl.addStyleName(SignUpCBundle.INSTANCE.css().active());
		if(!(scienceCourseContainer.getWidgetCount()>0)) {
			setCourseData(libraryDo.get(0), libraryDo.get(0).getLabel());
		}
		setCourseContainerVisibility(SCIENCE_LBL);
	}
	/**
	 * 
	 * @function clickMathLbl 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description  :  This UiHandler will set the math CourseContainer data when mathCourseLbl clicked.
	 * 
	 * 
	 * @parm(s) : @param event
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	@UiHandler("mathCourseLbl")
	public void clickMathLbl(ClickEvent event) {
		removeStyleNames();
		mathCourseLbl.addStyleName(SignUpCBundle.INSTANCE.css().mathActive());
		if(!(mathCourseContainer.getWidgetCount()>0)) {
			setCourseData(libraryDo.get(1), MATH_LBL);
		}
		setCourseContainerVisibility(MATH_LBL);
	}
	 /**
	  * 
	  * @function clickSocialLbl 
	  * 
	  * @created_date : Nov 8, 2013
	  * 
	  * @description   :  This UiHandler will set the social CourseContainer data when socialCourseLbl clicked.
	  * 
	  * 
	  * @parm(s) : @param event
	  * 
	  * @return : void
	  *
	  * @throws : <Mentioned if any exceptions>
	  *
	  * 
	  *
	  *
	  */
	@UiHandler("socialCourseLbl")
	public void clickSocialLbl(ClickEvent event) {
		removeStyleNames();
		socialCourseLbl.addStyleName(SignUpCBundle.INSTANCE.css().ssActive());
		if(!(socialCourseContainer.getWidgetCount()>0)) {
			setCourseData(libraryDo.get(2), SOCIAL_LBL);
		}
		setCourseContainerVisibility(SOCIAL_LBL);
	}
	/**
	 * 
	 * @function clickElaLbl 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description  :  This UiHandler will set the ela CourseContainer data when elaCourseLbl clicked.
	 * 
	 * 
	 * @parm(s) : @param event
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	@UiHandler("elaCourseLbl")
	public void clickElaLbl(ClickEvent event) {
		removeStyleNames();
		elaCourseLbl.addStyleName(SignUpCBundle.INSTANCE.css().elaActive());
		if(!(elaCourseContainer.getWidgetCount()>0)) {
			setCourseData(libraryDo.get(3), ELA_LBL);
		}
		setCourseContainerVisibility(ELA_LBL);
	}
	
	/**
	 * 
	 * @function clickArtsAndHumanitiesLbl 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param event
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
//	@UiHandler("artsAndHumanitiesLbl")
//	public void clickArtsAndHumanitiesLbl(ClickEvent event){
////		removeStyleNames();
////		artsAndHumanitiesLbl.addStyleName(SignUpCBundle.INSTANCE.css().active());
////		
////		if(!(artsHumanitiesContainer.getWidgetCount()>0)) {
////			setCourseData(libraryDo.get(4), ARTS_HUMANITIES);
////		}
////		
////		setCourseContainerVisibility(ARTS_HUMANITIES);
//	}
	/**
	 * 
	 * @function clickTechEnggLbl 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param event
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	
//	@UiHandler("technologyAndEngineeringLbl")
//	public void clickTechEnggLbl(ClickEvent event){
//		removeStyleNames();
////		technologyAndEngineeringLbl.addStyleName(SignUpCBundle.INSTANCE.css().active());
////		
////		if(!(techologyEngineeringContainer.getWidgetCount()>0)) {
////			setCourseData(libraryDo.get(5), TECH_ENGEE);
////		}
////		
////		setCourseContainerVisibility(TECH_ENGEE);
//	}
	/**
	 * Hide the popup and redirect to home page while clicking cancel
	 * @param clickEvent instance of {@link ClickEvent}
	 */
	@UiHandler("lblCancel")
	public void onCancelClick(ClickEvent clickEvent) {
		closeSignUpGradeCourseView();
	}
	/**
	 * 
	 * @function onSkipClick 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description  : This UiHandler is used to skip signup  grade course. 
	 * 
	 * 
	 * @parm(s) : @param clickEvent
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	@UiHandler("skipBtn")
	public void onSkipClick(ClickEvent clickEvent) {
		MixpanelUtil.skip_grade_course();
		closeSignUpGradeCourseView();
	}
	/**
	 * 
	 * @function onSubmitClick 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description  :  This UiHandler is used to submit signup  grade course. 
	 * 
	 * 
	 * @parm(s) : @param clickEvent
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	@UiHandler("submitBtn")
	public void onSubmitClick(ClickEvent clickEvent) {
		MixpanelUtil.submit_grade_course();
		closeSignUpGradeCourseView();
	}
	
	
	/**
	 * 
	 * @function removeStyleNames 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description	to the set default css for all items
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
	private void removeStyleNames() {
		mathCourseLbl.removeStyleName(SignUpCBundle.INSTANCE.css().mathActive());
		scienceCourseLbl.removeStyleName(SignUpCBundle.INSTANCE.css().active());
		elaCourseLbl.removeStyleName(SignUpCBundle.INSTANCE.css().elaActive());
		socialCourseLbl.removeStyleName(SignUpCBundle.INSTANCE.css().ssActive());
//		artsAndHumanitiesLbl.removeStyleName(SignUpCBundle.INSTANCE.css().active());
//		technologyAndEngineeringLbl.removeStyleName(SignUpCBundle.INSTANCE.css().active());
	}
	/**
	 * 
	 * @function setCourseContainerVisibility 
	 * 
	 * @created_date : Nov 8, 2013
	 * 
	 * @description	set the visibility for all language containers and make visible based on click/selection.
	 * 
	 * 
	 * @parm(s) : @param courseLabel
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	private void setCourseContainerVisibility(String courseLabel) {
		mathCourseContainer.setVisible(courseLabel.equalsIgnoreCase(MATH_LBL) ? true : false);
		scienceCourseContainer.setVisible(courseLabel.equalsIgnoreCase(SCIENCE_LBL) ? true : false);
		elaCourseContainer.setVisible(courseLabel.equalsIgnoreCase(ELA_LBL) ? true : false);
		socialCourseContainer.setVisible(courseLabel.equalsIgnoreCase(SOCIAL_LBL) ? true : false);
//		artsHumanitiesContainer.setVisible(courseLabel.equalsIgnoreCase(ARTS_HUMANITIES) ? true : false);
//		techologyEngineeringContainer.setVisible(courseLabel.equalsIgnoreCase(TECH_ENGEE) ? true : false);		
	}
}
