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
package org.ednovo.gooru.shared.model.search;

import java.io.Serializable;

public class CollectionItemSearchResultDo extends ResourceSearchResultDo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String collectionItemId;
	private String collectionId;
	private Integer itemSequence;
	private String itemType;
	private String narration;	
	public CollectionItemSearchResultDo(){}
	public String getCollectionItemId() {
		return collectionItemId;
	}
	public Integer getItemSequence() {
		return itemSequence;
	}
	public String getItemType() {
		return itemType;
	}
	public String getNarration() {
		return narration;
	}
	public void setCollectionItemId(String collectionItemId) {
		this.collectionItemId = collectionItemId;
	}
	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	
}
