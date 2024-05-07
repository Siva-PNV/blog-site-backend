package com.fse.modals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogsModal {
	String blogName;
	String category;
	String article;
	String authorName;
	String creationTimeStamp;
}
