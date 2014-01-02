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
package org.ednovo.gooru.client.mvp.shelf.list;

import org.ednovo.gooru.client.effects.FontWeightEffect;
import org.ednovo.gooru.client.util.ImageUtil;
import org.ednovo.gooru.shared.model.content.CollectionItemDo;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

/**
 * @author Search Team
 *
 */
public class ShelfResource extends FlowPanel {

	private Label imageLbl;
	private HTML titleLbl;
	
	private CollectionItemDo collectionItemDo = null;

	/**
	 * Class constructor , assign colletionItem instance
	 * @param collectionItem instance of {@link CollectionItemDo}
	 */
	public ShelfResource(CollectionItemDo collectionItem) {
		this.collectionItemDo = collectionItem;
		imageLbl = new Label();
		titleLbl = new HTML();
		this.getElement().setAttribute("collectionType", collectionItem.getResource().getResourceType().getName());
		titleLbl.setStyleName(ShelfListCBundle.INSTANCE.css().shelfResourceTitle());
		titleLbl.addStyleName("collectionItemTitle");
		titleLbl.getElement().getStyle().setWhiteSpace(WhiteSpace.NOWRAP);
		this.setStyleName(ShelfListCBundle.INSTANCE.css().shelfResourcePanel());
		this.setData(collectionItem);
	}

	public void updateCurrentTitle(String newTitle) {
		titleLbl.setHTML("");
		if (newTitle != null && newTitle.length() > 0) {
			titleLbl.setHTML(newTitle.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", ""));
		} else {
			titleLbl.setHTML("--");
		}
	}
	
	/**
	 * Assign collection item info  title, image url 
	 * @param collectionItem instance of {@link CollectionItemDo}
	 */
	private void setData(CollectionItemDo collectionItem) {
		ImageUtil.renderResourceImage(imageLbl, collectionItem.getResource().getCategory());
		if (collectionItem.getResource().getTitle() != null && collectionItem.getResource().getTitle().length() > 0) {
//			titleLbl.setText(StringUtil.truncateText(collectionItem.getResource().getTitle(), 30));
			titleLbl.setHTML(collectionItem.getResource().getTitle().replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", ""));
		} else {
			titleLbl.setHTML("--");
		}
		this.add(imageLbl);
		this.add(titleLbl);
	}

	public void glowTitle() {
		new FontWeightEffect(titleLbl.getElement(), FontWeight.BOLD, FontWeight.NORMAL, 5000);
	}

	/**
	 * @return instance of {@link CollectionItemDo} 
	 */
	public CollectionItemDo getCollectionItemDo() {
		return collectionItemDo;
	}

	/**
	 * @param collectionItemDo the {@link CollectionItemDo} to set
	 */
	public void setCollectionItemDo(CollectionItemDo collectionItemDo) {
		this.collectionItemDo = collectionItemDo;
	}
	

}
