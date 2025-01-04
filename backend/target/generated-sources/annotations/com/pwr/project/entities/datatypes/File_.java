package com.pwr.project.entities.datatypes;

import com.pwr.project.entities.Notice;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(File.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class File_ {

	
	/**
	 * @see com.pwr.project.entities.datatypes.File#fileName
	 **/
	public static volatile SingularAttribute<File, String> fileName;
	
	/**
	 * @see com.pwr.project.entities.datatypes.File#id
	 **/
	public static volatile SingularAttribute<File, Long> id;
	
	/**
	 * @see com.pwr.project.entities.datatypes.File
	 **/
	public static volatile EntityType<File> class_;
	
	/**
	 * @see com.pwr.project.entities.datatypes.File#fileType
	 **/
	public static volatile SingularAttribute<File, String> fileType;
	
	/**
	 * @see com.pwr.project.entities.datatypes.File#fileContent
	 **/
	public static volatile SingularAttribute<File, String> fileContent;
	
	/**
	 * @see com.pwr.project.entities.datatypes.File#notice
	 **/
	public static volatile SingularAttribute<File, Notice> notice;

	public static final String FILE_NAME = "fileName";
	public static final String ID = "id";
	public static final String FILE_TYPE = "fileType";
	public static final String FILE_CONTENT = "fileContent";
	public static final String NOTICE = "notice";

}

