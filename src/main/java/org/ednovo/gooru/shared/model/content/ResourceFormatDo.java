package org.ednovo.gooru.shared.model.content;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResourceFormatDo implements Serializable {
	private static final long serialVersionUID = -6275994990965281074L;

	private String value;
	private String displayName;
	
	public ResourceFormatDo(){}

	/** 
	 * This method is to get the value
	 */
	public String getValue() {
		return value;
	}

	/** 
	 * This method is to set the value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/** 
	 * This method is to get the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/** 
	 * This method is to set the displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}