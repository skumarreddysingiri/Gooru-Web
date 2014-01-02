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
package org.ednovo.gooru.shared.model.content;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.shared.model.user.UserDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * 
 * @fileName : CollectionItemDo.java
 *
 * @description : This class is used as data object.
 *
 *
 * @version : 1.0
 *
 * @date: 31-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
@JsonInclude(Include.NON_NULL)
public class CollectionItemDo extends ResourceSearchResultDo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6903126019265446978L;

	private String collectionItemId;
	private CollectionDo collection;
	private ResourceDo resource;
	private int resourceCount = 0;
	private CollectionQuestionItemDo collectionQuestionItemDo;
	private String itemType;
	private Integer itemSequence;
	private String narration;
	private String narrationType;
	private String start;
	private String stop;
	private List<String> course;
	private Integer totalHitCount;
	
	private List<Map<String, String>> standards;
	
	private RatingDo rating;

	/** 
	 * This method is to get the collectionItemId
	 */
	public String getCollectionItemId() {
		return collectionItemId;
	}


	/** 
	 * This method is to set the collectionItemId
	 */
	public void setCollectionItemId(String collectionItemId) {
		this.collectionItemId = collectionItemId;
	}


	/** 
	 * This method is to get the collection
	 */
	public CollectionDo getCollection() {
		return collection;
	}


	/** 
	 * This method is to set the collection
	 */
	public void setCollection(CollectionDo collection) {
		this.collection = collection;
	}


	/** 
	 * This method is to get the resource
	 */
	public ResourceDo getResource() {
		return resource;
	}


	/** 
	 * This method is to set the resource
	 */
	public void setResource(ResourceDo resource) {
		this.resource = resource;
	}


	/** 
	 * This method is to get the resourceCount
	 */
	public int getResourceCount() {
		return resourceCount;
	}


	/** 
	 * This method is to set the resourceCount
	 */
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}


	/** 
	 * This method is to get the collectionQuestionItemDo
	 */
	public CollectionQuestionItemDo getCollectionQuestionItemDo() {
		return collectionQuestionItemDo;
	}


	/** 
	 * This method is to set the collectionQuestionItemDo
	 */
	public void setCollectionQuestionItemDo(
			CollectionQuestionItemDo collectionQuestionItemDo) {
		this.collectionQuestionItemDo = collectionQuestionItemDo;
	}


	/** 
	 * This method is to get the itemType
	 */
	public String getItemType() {
		return itemType;
	}


	/** 
	 * This method is to set the itemType
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}


	/** 
	 * This method is to get the itemSequence
	 */
	public Integer getItemSequence() {
		return itemSequence;
	}


	/** 
	 * This method is to set the itemSequence
	 */
	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}


	/** 
	 * This method is to get the narration
	 */
	public String getNarration() {
		return narration;
	}


	/** 
	 * This method is to set the narration
	 */
	public void setNarration(String narration) {
		this.narration = narration;
	}


	/** 
	 * This method is to get the narrationType
	 */
	public String getNarrationType() {
		return narrationType;
	}


	/** 
	 * This method is to set the narrationType
	 */
	public void setNarrationType(String narrationType) {
		this.narrationType = narrationType;
	}


	/** 
	 * This method is to get the start
	 */
	public String getStart() {
		return start;
	}


	/** 
	 * This method is to set the start
	 */
	public void setStart(String start) {
		this.start = start;
	}


	/** 
	 * This method is to get the stop
	 */
	public String getStop() {
		return stop;
	}


	/** 
	 * This method is to set the stop
	 */
	public void setStop(String stop) {
		this.stop = stop;
	}


	/** 
	 * This method is to get the course
	 */
	public List<String> getCourse() {
		return course;
	}


	/** 
	 * This method is to set the course
	 */
	public void setCourse(List<String> course) {
		this.course = course;
	}


	/** 
	 * This method is to get the totalHitCount
	 */
	public Integer getTotalHitCount() {
		return totalHitCount;
	}


	/** 
	 * This method is to set the totalHitCount
	 */
	public void setTotalHitCount(Integer totalHitCount) {
		this.totalHitCount = totalHitCount;
	}


	/** 
	 * This method is to get the standards
	 */
	public List<Map<String, String>> getStandards() {
		return standards;
	}


	/** 
	 * This method is to set the standards
	 */
	public void setStandards(List<Map<String, String>> standards) {
		this.standards = standards;
	}


	/** 
	 * This method is to get the rating
	 */
	public RatingDo getRating() {
		return rating;
	}


	/** 
	 * This method is to set the rating
	 */
	public void setRating(RatingDo rating) {
		this.rating = rating;
	}


	@Override
	public String getGooruOid() {
		return getResource().getGooruOid();
	}
	
	
	public String getDescription() {
		return getResource().getGoals();
	}
	
	public String getResourceTitle() {
		return getResource().getTitle();
	}
	
	public String getUrl() {
        return getResource().getThumbnails().getUrl();
    }
	
	public String getViews() {
		return getResource().getViews();
	}
	
	public Integer getVotesUp() {
		return getResource().getVotesUp();
	}
	
	public UserDo getCreator() {
		return getResource().getCreator();
	}
	
	public LicenseDo getLicense() {
		return getResource().getLicense();
	}
	
	public String getAssetURI() {
        return getResource().getAssetURI();
    }
	
	public String getGrade() {
		return getResource().getGrade();
	}


}
