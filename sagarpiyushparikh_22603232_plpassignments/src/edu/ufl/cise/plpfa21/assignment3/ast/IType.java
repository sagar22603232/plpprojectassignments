package edu.ufl.cise.plpfa21.assignment3.ast;

import edu.ufl.cise.plpfa21.assignment3.ast.IType.TypeKind;

public interface IType extends IASTNode {

	public static enum TypeKind {
		INT,
		BOOLEAN,
		STRING,
		LIST,
		GLOBAL,
		LOCAL
	}
	
	boolean isInt();
	boolean isBoolean();
	boolean isString();
	boolean isList();
	boolean isGlobal();
	boolean isLocal();
	boolean isKind(TypeKind kind);
	
	String getDesc() throws Exception;
	String getClassName() throws Exception;
	
}
