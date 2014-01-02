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
package org.ednovo.gooru.client.mvp.home.library;

import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.model.library.CourseDo;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @fileName : FeaturedCourseListView.java
 *
 * @description : This class is used to display the view for featured courses.
 *
 * @version : 1.0
 *
 * @date: 30-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class FeaturedCourseListView extends Composite {
	
	@UiField HTMLEventPanel featuredCourse;
	@UiField Label courseTitle;
	@UiField Label courseAuthor;
	@UiField Image featuredCourseImage, contributorImage;
	
	private Integer courseId;
	
	private final static String DEFAULT_USER_IMG = "../images/settings/setting-user-image.png";
	
	private final static String COURSE_100_75_IMG = "../images/library/course-100x75.png";
	
	private final static String COURSE_100_75_CROP = "-100x75.";
	
	private static final String PNG = ".png";
	
	private final static String MR = "By Mr. ";
	
	private final static String MS = "By Ms. ";

	private final static String FEMALE = "female";

	private final static String MALE = "male";
	
	private static FeaturedCourseListViewUiBinder uiBinder = GWT
			.create(FeaturedCourseListViewUiBinder.class);

	interface FeaturedCourseListViewUiBinder extends
			UiBinder<Widget, FeaturedCourseListView> {
	}

	public FeaturedCourseListView(CourseDo courseDo) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(courseDo);
	}
	
	/**
	 * 
	 * @function setData 
	 * 
	 * @created_date : 11-Dec-2013
	 * 
	 * @description : This method is used to set the course data.
	 * 
	 * @parm(s) : @param courseDo
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	private void setData(CourseDo courseDo) {
		courseTitle.setText(courseDo.getLabel());
		featuredCourseImage.setUrl(StringUtil.formThumbnailName(courseDo.getThumbnails().getUrl(),COURSE_100_75_CROP));
		featuredCourseImage.setWidth("100px");
		featuredCourseImage.setHeight("75px");

		featuredCourseImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				featuredCourseImage.setUrl(COURSE_100_75_IMG);
			}
		});

		contributorImage.setHeight("46px");
		contributorImage.setWidth("46px");
		if(courseDo.getCreator()!=null) {
			courseAuthor.setVisible(true);
			String authorName = "";
			if(courseDo.getCreator().getGender()!=null) {
				if(courseDo.getCreator().getGender().equalsIgnoreCase(MALE)) {
					authorName = MR+courseDo.getCreator().getLastName();
				} else if(courseDo.getCreator().getGender().equalsIgnoreCase(FEMALE)) {
					authorName = MS+courseDo.getCreator().getLastName();
				}
			}
			courseAuthor.setText(authorName);
			contributorImage.setUrl(AppClientFactory.getLoggedInUser().getSettings().getProfileImageUrl() + courseDo.getCreator().getGooruUId()+PNG);
			contributorImage.addErrorHandler(new ErrorHandler() {
				@Override
				public void onError(ErrorEvent event) {
					contributorImage.setUrl(DEFAULT_USER_IMG);
				}
			});
		} else {
			courseAuthor.setVisible(false);
			contributorImage.setUrl(DEFAULT_USER_IMG);
		}
		setCourseId(courseDo.getCodeId());
	}
	/**
	 * This method is used to return featured course panel.
	 */
	public HTMLEventPanel getfeaturedCoursePanel() {
		return featuredCourse;
	}
	/**
	 * This method is used to get course id.
	 */
	public Integer getCourseId() {
		return courseId;
	}
	/**
	 * This method is used to set course id.
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
}
