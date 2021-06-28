package edu.pse.beast.api.codegen.c_code;

import java.util.ArrayList;
import java.util.List;

public class CFile {

	private List<CFunction> funcs = new ArrayList<>();
	private List<CFunction> funcDecls = new ArrayList<>();
	private List<CInclude> includes = new ArrayList<>();
	private List<CDefine> defines = new ArrayList<>();
	private List<CStruct> structs = new ArrayList<>();
	private List<CTypeDef> typedefs = new ArrayList<>();
	private List<String> declarations = new ArrayList<>();

	public void addTypeDef(CTypeDef typeDef) {
		this.typedefs.add(typeDef);
	}

	public void addTypeDef(List<CTypeDef> typeDefs) {
		for (CTypeDef td : typeDefs)
			addTypeDef(td);
	}

	public void addFunction(CFunction func) {
		this.funcs.add(func);
	}

	public void addFunctionDecl(String returnType, String name,
			List<String> args) {
		funcDecls.add(new CFunction(name, args, returnType));
	}

	public void include(String filePath) {
		this.includes.add(new CInclude(filePath));
	}

	public void define(String toReplace, String replaceWith) {
		defines.add(new CDefine(toReplace, replaceWith));
	}

	public void addStructDef(CStruct struct) {
		structs.add(struct);
	}

	public String generateCode() {
		List<String> created = new ArrayList<>();
		for (CInclude inc : includes) {
			created.add(inc.generateCode());
		}
		created.add("\n");
		for (CDefine def : defines) {
			created.add(def.generateCode());
		}
		created.add("\n");
		for (CTypeDef tdef : typedefs) {
			created.add(tdef.generateCode());
		}
		created.add("\n");
		for (String decl : declarations) {
			created.add(decl);
		}
		created.add("\n");
		for (CStruct s : structs) {
			created.add(s.generateDefCode());
		}
		created.add("\n");
		for (CFunction func : funcDecls) {
			created.add(func.generateDeclCode());
		}
		created.add("\n");
		for (CFunction func : funcs) {
			created.add(func.generateDefCode());
			created.add("\n");
		}
		created.add("\n");
		return String.join("\n", created);
	}

	public void declare(String declCString) {
		declarations.add(declCString + ";\n");
	}

}
