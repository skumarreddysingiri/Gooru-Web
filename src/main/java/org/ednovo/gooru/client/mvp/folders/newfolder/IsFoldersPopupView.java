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
package org.ednovo.gooru.client.mvp.folders.newfolder;

import java.util.List;

import org.ednovo.gooru.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.shared.model.code.LibraryCodeDo;
import org.ednovo.gooru.shared.model.content.CollectionDo;

import com.gwtplatform.mvp.client.PopupView;

/**
 * @fileName : IsFoldersPopupView.java
 *
 * @description :  Acts as a bridge between the Presenter and view.
 *
 * @version : 1.0
 *
 * @date: 30-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public interface IsFoldersPopupView extends PopupView,
		IsViewWithHandlers<FoldersPopupUiHandlers> {
	/**
	 * set collection meta data info
	 * 
	 * @param collection
	 *            instance of {@link CollectionDo}
	 * @return instance of {@link CollectionDo}
	 */
	CollectionDo setData(CollectionDo collection);

	/**
	 * @return collection data instance of {@link CollectionDo}
	 */
	CollectionDo getData();

	/**
	 * Set library information
	 * 
	 * @param libraryCode
	 *            instance of {@link LibraryCodeDo} as List
	 */
	void setLibraryCodes(List<LibraryCodeDo> libraryCode);

	/**
	 * @return code id of the course
	 */
	String getCourseCodeId();
	
	/**
	 * hide popup panel.
	 */

	void closePopupPanel();

}
