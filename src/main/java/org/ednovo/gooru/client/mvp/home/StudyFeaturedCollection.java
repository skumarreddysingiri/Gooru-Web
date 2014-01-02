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
package org.ednovo.gooru.client.mvp.home;

import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @fileName : StudyFeaturedCollection.java
 *
 * @description : To Set the featured collection image
 *
 *
 * @version : 1.0
 *
 * @date: 30-Dec-2013
 *
 * @Author : Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class StudyFeaturedCollection extends Composite  {

	@UiField
	FlowPanel contentImageFloPanel;
	
	@UiField
	Image contentUrlImg;
	
	@UiField
	FlowPanel featuredStartStudyFloPanel;
	
	private static StudyFeaturedCollectionUiBinder uiBinder = GWT
			.create(StudyFeaturedCollectionUiBinder.class);

	interface StudyFeaturedCollectionUiBinder extends
			UiBinder<Widget, StudyFeaturedCollection> {
	}

	/**
	 * Class constructor
	 */
	public StudyFeaturedCollection() {
		initWidget(uiBinder.createAndBindUi(this));
		featuredStartStudyFloPanel.setVisible(false);
	}

	/**
	 * Set featured collection image
	 * @param url collection image url
	 */
	public void setFeaturedCollectionImageUrl(String url){
		contentUrlImg.setUrl(StringUtil.formThumbnailName(url, "-280x215."));
	}
	/**
	 * 
	 * @function getContentUrlImg 
	 * 
	 * @created_date : 30-Dec-2013
	 * 
	 * @description : getter for contentUrlImg.
	 * 
	 * 
	 * @parm(s) : @return
	 * 
	 * @return : Image
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public Image getContentUrlImg() {
		return contentUrlImg;
	}
	
}
