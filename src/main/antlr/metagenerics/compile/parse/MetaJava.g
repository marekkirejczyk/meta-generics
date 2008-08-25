/** A Java 1.5 grammar for ANTLR v3 derived from the spec
 *
 *  This is a very close representation of the spec; the changes
 *  are comestic (remove left recursion) and also fixes (the spec
 *  isn't exactly perfect).  I have run this on the 1.4.2 source
 *  and some nasty looking enums from 1.5, but have not really
 *  tested for 1.5 compatibility.
 *
 *  I built this with: java -Xmx100M org.antlr.Tool java.g 
 *  and got two errors that are ok (for now):
 *  java.g:691:9: Decision can match input such as
 *    "'0'..'9'{'E', 'e'}{'+', '-'}'0'..'9'{'D', 'F', 'd', 'f'}"
 *    using multiple alternatives: 3, 4
 *  As a result, alternative(s) 4 were disabled for that input
 *  java.g:734:35: Decision can match input such as "{'$', 'A'..'Z',
 *    '_', 'a'..'z', '\u00C0'..'\u00D6', '\u00D8'..'\u00F6',
 *    '\u00F8'..'\u1FFF', '\u3040'..'\u318F', '\u3300'..'\u337F',
 *    '\u3400'..'\u3D2D', '\u4E00'..'\u9FFF', '\uF900'..'\uFAFF'}"
 *    using multiple alternatives: 1, 2
 *  As a result, alternative(s) 2 were disabled for that input
 *
 *  You can turn enum on/off as a keyword :)
 *
 *  Version 1.0 -- initial release July 5, 2006 (requires 3.0b2 or higher)
 *
 *  Primary author: Terence Parr, July 2006
 *
 *  Version 1.0.1 -- corrections by Koen Vanderkimpen & Marko van Dooren,
 *      October 25, 2006;
 *      fixed normalInterfaceDeclaration: now uses typeParameters instead
 *          of typeParameter (according to JLS, 3rd edition)
 *      fixed castExpression: no longer allows expression next to type
 *          (according to semantics in JLS, in contrast with syntax in JLS)
 *
 *  Version 1.0.2 -- Terence Parr, Nov 27, 2006
 *      java spec I built this from had some bizarre for-loop control.
 *          Looked weird and so I looked elsewhere...Yep, it's messed up.
 *          simplified.
 *
 *  Version 1.0.3 -- Chris Hogue, Feb 26, 2007
 *      Factored out an annotationName rule and used it in the annotation rule.
 *          Not sure why, but typeName wasn't recognizing references to inner
 *          annotations (e.g. @InterfaceName.InnerAnnotation())
 *      Factored out the elementValue section of an annotation reference.  Created 
 *          elementValuePair and elementValuePairs rules, then used them in the 
 *          annotation rule.  Allows it to recognize annotation references with 
 *          multiple, comma separated attributes.
 *      Updated elementValueArrayInitializer so that it allows multiple elements.
 *          (It was only allowing 0 or 1 element).
 *      Updated localVariableDeclaration to allow annotations.  Interestingly the JLS
 *          doesn't appear to indicate this is legal, but it does work as of at least
 *          JDK 1.5.0_06.
 *      Moved the Identifier portion of annotationTypeElementRest to annotationMethodRest.
 *          Because annotationConstantRest already references variableDeclarator which 
 *          has the Identifier portion in it, the parser would fail on constants in 
 *          annotation definitions because it expected two identifiers.  
 *      Added optional trailing ';' to the alternatives in annotationTypeElementRest.
 *          Wouldn't handle an inner interface that has a trailing ';'.
 *      Swapped the expression and type rule reference order in castExpression to 
 *          make it check for genericized casts first.  It was failing to recognize a
 *          statement like  "Class<Byte> TYPE = (Class<Byte>)...;" because it was seeing
 *          'Class<Byte' in the cast expression as a less than expression, then failing 
 *          on the '>'.
 *      Changed createdName to use typeArguments instead of nonWildcardTypeArguments.
 *          Again, JLS doesn't seem to allow this, but java.lang.Class has an example of
 *          of this construct.
 *      Changed the 'this' alternative in primary to allow 'identifierSuffix' rather than
 *          just 'arguments'.  The case it couldn't handle was a call to an explicit
 *          generic method invocation (e.g. this.<E>doSomething()).  Using identifierSuffix
 *          may be overly aggressive--perhaps should create a more constrained thisSuffix rule?
 * 		
 *  Version 1.0.4 -- Hiroaki Nakamura, May 3, 2007
 *
 *	Fixed formalParameterDecls, localVariableDeclaration, forInit,
 *	and forVarControl to use variableModifier* not 'final'? (annotation)?
 *
 *  Version 1.0.5 -- Terence, June 21, 2007
 *	--a[i].foo didn't work. Fixed unaryExpression
 *
 *  Version 1.1.0 -- Marek Kirejczyk, Juli 18, 2007
 *	Metagenerics
 */
grammar MetaJava; 

options {k=2; backtrack=true; memoize=true;}

@lexer::header {package metagenerics.compile.parse;} 

@parser::header {
package metagenerics.compile.parse; 
import metagenerics.ast.metageneric.*;
import metagenerics.ast.common.*;
import metagenerics.ast.member.*;
import metagenerics.ast.declarations.*;
import metagenerics.ast.unit.*;
import metagenerics.ast.*;

} 



@lexer::members {
protected boolean enumIsKeyword = true;
}

// starting point for parsing a java file 
compilationUnit returns [UnitAst unit = new UnitAst();]
	: 
		(annotations {$unit.setAnnotations($annotations.value);})?
		(d=packageDeclaration {
			$unit.setPackageDeclaration($d.value);
			$unit.setInfo($start, $stop, $text);
		} )? 
        (i=importsDeclaration {
        	$unit.setImports($i.value);
        	$unit.setInfo($start, $stop, $text);
        } )
        (t=typeDeclarations {
        	$unit.setElements($t.value);
        	$unit.setInfo($start, $stop, $text);
        })
        
;

typeDeclarations returns [Elements value = new Elements();] : 
(t=typeDeclaration {$value.add($t.value);})* {
	$value.setInfo($start, $stop, $text);
}
;

packageDeclaration returns [PackageDeclaration value = new PackageDeclaration();]
	: 'package' q=qualifiedName ';' {
		$value.setInfo($packageDeclaration.start, $packageDeclaration.stop, $packageDeclaration.text);
		$value.setPackagePath($q.value);
		}
	;
	
importsDeclaration returns [List<ImportAst> value = new ArrayList<ImportAst>();] 
	: (i=importDeclaration {$value.add($i.value);})* 
	;
	
importDeclaration returns [ImportAst value = new ImportAst();]
	:	'import' ('static' {$value.setStatic(true);})? id1=Identifier {$value.addIdentifier($id1.text);} ('.' id=Identifier {$value.addIdentifier($id.text);})* ('.' '*' {$value.setGeneral(true);})? ';' 
	;
	
typeDeclaration returns [Element value]
	:	classOrInterface=classOrInterfaceDeclaration {$value = $classOrInterface.value;}
	|   meta=metaDeclaration {$value = $meta.value;}
    |   ';' {
		$value = new Semicolon();
    	$value.setInfo($start, $stop, $text);
    }
	;
	
metaDeclaration returns [MetaDeclaration value]
	: typedef=metaTypeDef {$value = $typedef.value;}
	| meta=metaGeneric {$value = $meta.value;}
	;

evalInstruction returns [String value = ""]
	: 'eval' '(' l=StringLiteral {$value = $l.text;} ')'  
	;

evalOrType returns [String value =""]
	: l=type {$value = $l.text;}
	| i=evalInstruction {$value = $i.text;};

 

metaGeneric returns [MetaGenericAst value = new MetaGenericAst();]
	:  (m0=modifiers 'meta' 'class' Identifier (metaParameters {$value.setGenericParameters($metaParameters.value);})?	
        ('extends' type {$value.setSuperClass($type.text);})?
        ('implements' typeList {$value.setImplementedInterfaces($typeList.value);} )?
     	classBody ) {	
        	$value.setInfo($start, $stop, $text);
			$value.setName($Identifier.text);
			$value.setChildren($classBody.value);
			$value.setModifiers($m0.value);
        }
		
	;
	

	
metaTypeDef returns [MetaTypedefAst value = new MetaTypedefAst();]
	: m0=modifiers 'class' id=Identifier '=' mid=packageOrTypeName (args=metaParameters)? {
		$value = new MetaTypedefAst();
		$value.setFunction($mid.text);
		$value.setName($id.text);
		$value.setParameters($args.value);
		$value.setInfo($start, $stop, $text);
		$value.setModifiers($m0.value);
	 }
	;

metaParameters returns [List<String> value = new ArrayList<String>()]
	: ( '<' p=metaParameter {$value.add($p.text);} (',' r=metaParameter {$value.add($r.text);})* '>' ) 
	;
	
metaParameter 
	: Identifier ('.' Identifier)*
	;	
	
classOrInterfaceDeclaration returns [Element value]
	:	(m0=modifiers classDeclaration) {
			$value = $classDeclaration.value;
			$value.setModifiers($m0.value);
		} 
	|   (m1=modifiers interfaceDeclaration) {
			$value = $interfaceDeclaration.value;
			$value.setModifiers($m1.value);
		} 
	;
	
classDeclaration returns [Element value]
	:	normalClassDeclaration {$value = $normalClassDeclaration.value;}
    |   enumDeclaration {$value = $enumDeclaration.value;}
	;
	
normalClassDeclaration returns [ClassDeclaration value = new ClassDeclaration();]
	:	'class' Identifier (typeParameters)?
        ('extends' type {$value.setSuperClass($type.text);})?
        ('implements' typeList {$value.setImplementedInterfaces($typeList.value);} )?
        classBody {
			$value.setInfo($start, $stop, $text);
			$value.setName($Identifier.text);
			$value.setGenericParameters($typeParameters.value);
			$value.setChildren($classBody.value);
		}
	;
	
typeParameters returns [List<String> value = new ArrayList<String>();]
	:	('<' e1=typeParameter {$value.add($e1.text);}) (',' e=typeParameter {$value.add($e.text);})* '>'
	;

typeParameter 
	:	Identifier ('extends' bound)?
	;
		
bound
	:	type ('&' type)*
	;

enumDeclaration returns [EnumDeclaration value]
	:	ENUM Identifier ('implements' typeList)? enumBody {
			$value = new EnumDeclaration();
			$value.setInfo($start, $stop, $text);
		}
	;
	
enumBody
	:	'{' enumConstants? ','? enumBodyDeclarations? '}'
	;

enumConstants
	:	enumConstant (',' enumConstant)*
	;
	
enumConstant
	:	annotations? Identifier (arguments)? (classBody)?
	;
	
enumBodyDeclarations
	:	';' (classBodyDeclaration)*
	;
	



interfaceDeclaration returns [Element value]
	: normalInterfaceDeclaration {$value = $normalInterfaceDeclaration.value;}
	| annotationTypeDeclaration {$value = $annotationTypeDeclaration.value;}
	;
	
normalInterfaceDeclaration returns [Interface value]
	:	'interface' Identifier typeParameters? ('extends' typeList)? interfaceBody {
			$value = new Interface();
			$value.setInfo($start, $stop, $text);
	}
	;
	
typeList returns [List<String> value = new ArrayList<String>();]
	:	id1=type {$value.add($id1.text);} (',' id=type {$value.add($id.text);})*
	;
	
classBody returns [List<Node> value = new ArrayList<Node>();]
	:	'{' (classBodyDeclaration {$value.addAll($classBodyDeclaration.value);})* '}'
	;
	
interfaceBody
	:	'{' interfaceBodyDeclaration* '}'
	;

modifiers returns [Modifiers value = new Modifiers();]
	: (m=modifier {
		$value.setInfo($start, $stop, $text);
		$value.add($m.value);
	})*
	;
	
classBodyDeclaration returns [List<Node> value = new ArrayList<Node>();]
	:	';' {
			Semicolon semicolon = new Semicolon ();
			semicolon.setInfo($start, $stop, $text);
			$value.add(semicolon);
		}
	|	(s='static' | m='meta')? block {
			Block member = new Block();
			if (s != null) 
				member.setType(Block.Type.STATIC);
			else if (m != null)
				member.setType(Block.Type.META);
			member.setInfo($start, $stop, $text);
			member.setInstructionBlock(new InstructionBlock($block.start, $block.stop, $block.text));
			$value.add(member);
		}
	|	modifiers d=memberDecl  {
			((Element)$memberDecl.value).setModifiers($modifiers.value);
			$memberDecl.value.setInfo($start, $stop, $text);
			$value.add($memberDecl.value);
		}
	;
	
memberDecl returns [Node value]
	:	rest1=genericMethodOrConstructorDecl {
		$value = $rest1.value;
	}
	|	methodDeclaration  {
		$value = $methodDeclaration.value;
	}
	|	'void' id=Identifier voidMethodDeclaratorRest {
		Method m = (Method)$voidMethodDeclaratorRest.value;
		$value = m;
		m.setName($id.text);
		m.setType("void");
	}
	|	id=Identifier rest2=constructorDeclaratorRest {
		$value = $rest2.value;
		((AbstractMethod)$value).setName($id.getText());
	}
	|	fieldDeclaration {
		$value = $fieldDeclaration.value;
	}
	|	interfaceDeclaration {
		$value = $interfaceDeclaration.value;
	}
	|	classDeclaration {
		$value = $classDeclaration.value;
	}
	;
	
genericMethodOrConstructorDecl returns [AbstractMethod value]
	:	t=typeParameters rest=genericMethodOrConstructorRest {
		$value = $rest.value;
		$value.setGenericParameters($t.value);
	}
	;
	
genericMethodOrConstructorRest returns [AbstractMethod value]
	:	(t=type | 'void') id=Identifier rest1=methodDeclaratorRest {
		$value = $rest1.value;
		((Method)$value).setType(t == null ? "void" : $t.text);
		$value.setName($id.text);
	}
	|	id=Identifier rest2=constructorDeclaratorRest {
		$value = $rest2.value;
		$value.setName($id.text);
	}
	;

methodDeclaration returns [Method value]
	:	t=type id=Identifier rest=methodDeclaratorRest {
		$value = $rest.value;
		$value.setType($type.text);
		$value.setName($id.text);
		}
	;

fieldDeclaration returns [VariableBuilder value] 
	:	type variableDeclarators ';' {
		$value = $variableDeclarators.value;
		$value.setType($type.text);
	}
	;
		
interfaceBodyDeclaration
	:	modifier* interfaceMemberDecl
	|   ';'
	;

interfaceMemberDecl
	:	interfaceMethodOrFieldDecl
	|   interfaceGenericMethodDecl
    |   'void' Identifier voidInterfaceMethodDeclaratorRest
    |   interfaceDeclaration
    |   classDeclaration
	;
	
interfaceMethodOrFieldDecl
	:	type Identifier interfaceMethodOrFieldRest
	;
	
interfaceMethodOrFieldRest
	:	constantDeclaratorsRest ';'
	|	interfaceMethodDeclaratorRest
	;
	
methodDeclaratorRest returns [Method value = new Method();]
	:	p=formalParameters ('[' ']')*
        ('throws' qualifiedNameList)?
        ( b=methodBody | ';' ) {
        	if (b != null) {
				Block block = new Block();
				block.setInfo($b.start, $b.stop, $b.text);
	        	$value.setBlock(block);
        	}
			$value.setRest($text);
			$value.setArguments($p.value);
        }
	;
	
voidMethodDeclaratorRest returns [Method value = new Method();]
	:	parameters=formalParameters ('throws' qualifiedNameList)?
        ( body=methodBody | ';') {
			if (body != null) {
        		Block block = new Block();
				block.setInfo($body.start, $body.stop, $body.text);
  	        	$value.setBlock(block);
        	}
			$value.setRest($text);
			$value.setArguments($parameters.value);
        }
	;
	
interfaceMethodDeclaratorRest
	:	formalParameters ('[' ']')* ('throws' qualifiedNameList)? ';'
	;
	
interfaceGenericMethodDecl
	:	typeParameters (type | 'void') Identifier
        interfaceMethodDeclaratorRest
	;
	
voidInterfaceMethodDeclaratorRest
	:	formalParameters ('throws' qualifiedNameList)? ';'
	;
	
constructorDeclaratorRest returns [Constructor value = new Constructor();]
	:	formalParameters ('throws' qualifiedNameList)? methodBody {
		$value.setRest($text);
	}
	;

constantDeclarator
	:	Identifier constantDeclaratorRest
	;
	
variableDeclarators returns  [VariableBuilder value = new VariableBuilder();]
	:	v1=variableDeclarator {$value.add($v1.value);}
	 (',' vn=variableDeclarator {$value.add($vn.value);})*
	;

variableDeclarator returns [Field value = new Field()]    
	:	id=Identifier rest=variableDeclaratorRest {
		$value.setName($id.text);
		$value.setRest($rest.text);
		$value.setInfo($start, $stop, $text);
	}
	;
	
variableDeclaratorRest 
	:	('[' ']')+ ('=' variableInitializer)?
	|	'=' variableInitializer
	|
	;
	
constantDeclaratorsRest
    :   constantDeclaratorRest (',' constantDeclarator)*
    ;

constantDeclaratorRest
	:	('[' ']')* '=' variableInitializer
	;
	
variableDeclaratorId
	:	Identifier ('[' ']')*
	;

variableInitializer
	:	arrayInitializer
    |   expression
	;
	
arrayInitializer
	:	'{' (variableInitializer (',' variableInitializer)* (',')? )? '}'
	;

modifier returns [Object value;]
    :   a=annotation {$value = $a.value;}
    |   'public' {$value = Modifier.fromText($modifier.text);}
    |   'protected' {$value = Modifier.fromText($modifier.text);}
    |   'private' {$value = Modifier.fromText($modifier.text);}
    |   'static' {$value = Modifier.fromText($modifier.text);}
    |   'abstract' {$value = Modifier.fromText($modifier.text);}
    |   'final' {$value = Modifier.fromText($modifier.text);}
    |   'native' {$value = Modifier.fromText($modifier.text);}
    |   'synchronized' {$value = Modifier.fromText($modifier.text);}
    |   'transient' {$value = Modifier.fromText($modifier.text);}
    |   'volatile' {$value = Modifier.fromText($modifier.text);}
    |   'strictfp' {$value = Modifier.fromText($modifier.text);}
    ;

packageOrTypeName
	:	Identifier ('.' Identifier)*
	;

enumConstantName
    :   Identifier
    ;

typeName
	:   Identifier
    |   packageOrTypeName '.' Identifier
	;

type
	:	Identifier (typeArguments)? ('.' Identifier (typeArguments)? )* ('[' ']')*
	|	primitiveType ('[' ']')*
	;

primitiveType
    :   'boolean'
    |	'char'
    |	'byte'
    |	'short'
    |	'int'
    |	'long'
    |	'float'
    |	'double'
    ;

variableModifier
	:	'final'
    |   annotation
	;

typeArguments
	:	'<' typeArgument (',' typeArgument)* '>'
	;
	
typeArgument
	:	type
	|	'?' (('extends' | 'super') type)?
	;
	
qualifiedNameList
	:	qualifiedName (',' qualifiedName)*
	;
	
formalParameters returns [List<Argument> value]
	:	'(' d=formalParameterDecls? ')' {
		value = (d == null) ? new ArrayList<Argument>() : $d.value;
	}
	;


formalParameterDecls returns [List<Argument> value = new ArrayList<Argument>();]
	: p1=formalParameter {
		$value.add($p1.value);
	  }
	  (',' pi=formalParameter {$value.add($pi.value);})* 
	  (variableModifier* t=type '...' d=variableDeclaratorId {
	  	$value.add(new Argument($t.text,$d.text));
	  }) ?
	;
	

	
methodBody
	:	block
	;

qualifiedName returns [List<String> value = new ArrayList<String>();]
	:	i1=Identifier {$value.add($i1.text);} ('.' i=Identifier{$value.add($i.text);})*
	;
		
	
literal	
	:   integerLiteral
    |   FloatingPointLiteral
    |   CharacterLiteral
    |   StringLiteral
    |   booleanLiteral
    |   'null'
	;

integerLiteral
    :   HexLiteral
    |   OctalLiteral
    |   DecimalLiteral
    ;

booleanLiteral
    :   'true'
    |   'false'
    ;

// ANNOTATIONS

annotations returns [Annotations value = new Annotations();] 
	:	(a=annotation {$value.addAnnotation($a.value);})+ {			$value.setInfo($start, $stop, $annotations.text);}
	;

annotation returns [Annotation value = new Annotation();]
	:	('@' name=annotationName ('(' args=elementValuePairs? ')')?) {
			$value.setName($name.text);
			$value.setInfo($start, $stop, $annotation.text);
			$value.setArguments($args.value);
		}
	;
	
annotationName
	: Identifier ('.' Identifier)*
	;
	
elementValuePairs returns [List<Argument> value = new ArrayList<Argument>();]
	: e1=elementValuePair {$value.add($e1.value);} (',' en=elementValuePair {$value.add($en.value);})*
	;
	
elementValuePair returns [Argument value]
	: (id=Identifier '=')? v=elementValue {
		String idName = ($id.text == null) ? "" : $id.text;
		$value = new Argument(idName, $v.text);
	}
	;
	
elementValue
	:	conditionalExpression
	|   annotation
	|   elementValueArrayInitializer
	;
	
elementValueArrayInitializer
	:	'{' (elementValue (',' elementValue )*)? '}'
	;
	
annotationTypeDeclaration returns [AnnotationDeclaration value = new AnnotationDeclaration();]
	:	('@' 'interface' id=Identifier annotationTypeBody) {
			$value.setName($id.text);
			$value.setInfo($start, $stop, $annotationTypeDeclaration.text);
	}
	;
	
annotationTypeBody
	:	'{' (annotationTypeElementDeclarations)? '}'
	;
	
annotationTypeElementDeclarations
	:	(annotationTypeElementDeclaration) (annotationTypeElementDeclaration)*
	;
	
annotationTypeElementDeclaration
	:	(modifier)* annotationTypeElementRest
	;
	
annotationTypeElementRest
	:	type annotationMethodOrConstantRest ';'
	|   classDeclaration ';'?
	|   interfaceDeclaration ';'?
	|   enumDeclaration ';'?
	|   annotationTypeDeclaration ';'?
	;
	
annotationMethodOrConstantRest
	:	annotationMethodRest
	|   annotationConstantRest
	;
	
annotationMethodRest
 	:	Identifier '(' ')' (defaultValue)?
 	;
 	
annotationConstantRest 
 	:	variableDeclarators  
 	;
 	
defaultValue
 	:	'default' elementValue
 	;

// STATEMENTS / BLOCKS

block
	:	'{' blockStatement* '}'
	;
	
blockStatement
	:	localVariableDeclaration
	|	classOrInterfaceDeclaration
    	|	statement
	;
	
localVariableDeclaration
	:	variableModifier* type variableDeclarators ';'
	;
	
statement
	: block
    | 'assert' expression (':' expression)? ';'
    | 'if' parExpression statement (options {k=1;}:'else' statement)?
    | 'for' '(' forControl ')' statement
    | 'while' parExpression statement
    | 'do' statement 'while' parExpression ';'
    | 'try' block
      (	catches 'finally' block
      | catches
      | 'finally' block
      )
    | 'switch' parExpression '{' switchBlockStatementGroups '}'
    | 'synchronized' parExpression block
    | 'return' expression? ';'
    | 'throw' expression ';'
    | 'break' Identifier? ';'
    | 'continue' Identifier? ';'
    | ';'
    | statementExpression ';'
    | Identifier ':' statement
	;
	
catches
	:	catchClause (catchClause)*
	;
	
catchClause
	:	'catch' '(' formalParameter ')' block
	;

formalParameter returns [Argument value]
	:	variableModifier* t=type n=variableDeclaratorId {
		$value = new Argument($t.text, $n.text);
	}
	;
		
switchBlockStatementGroups
	:	(switchBlockStatementGroup)*
	;
	
switchBlockStatementGroup
	:	switchLabel blockStatement*
	;
	
switchLabel
	:	'case' constantExpression ':'
	|   'case' enumConstantName ':'
	|   'default' ':'
	;
	
moreStatementExpressions
	:	(',' statementExpression)*
	;

forControl
options {k=3;} // be efficient for common case: for (ID ID : ID) ...
	:	forVarControl
	|	forInit? ';' expression? ';' forUpdate?
	;

forInit
	:	variableModifier* type variableDeclarators 
	|	expressionList
	;
	
forVarControl
	:	variableModifier* type Identifier ':' expression
	;

forUpdate
	:	expressionList
	;

// EXPRESSIONS

parExpression
	:	'(' expression ')'
	;
	
expressionList
    :   expression (',' expression)*
    ;

statementExpression
	:	expression
	;
	
constantExpression
	:	expression
	;
	
expression
	:	conditionalExpression (assignmentOperator expression)?
	;
	
assignmentOperator
	:	'='
    |   '+='
    |   '-='
    |   '*='
    |   '/='
    |   '&='
    |   '|='
    |   '^='
    |   '%='
    |   '<' '<' '='
    |   '>' '>' '='
    |   '>' '>' '>' '='
	;

conditionalExpression
    :   conditionalOrExpression ( '?' expression ':' expression )?
	;

conditionalOrExpression
    :   conditionalAndExpression ( '||' conditionalAndExpression )*
	;

conditionalAndExpression
    :   inclusiveOrExpression ( '&&' inclusiveOrExpression )*
	;

inclusiveOrExpression
    :   exclusiveOrExpression ( '|' exclusiveOrExpression )*
	;

exclusiveOrExpression
    :   andExpression ( '^' andExpression )*
	;

andExpression
    :   equalityExpression ( '&' equalityExpression )*
	;

equalityExpression
    :   instanceOfExpression ( ('==' | '!=') instanceOfExpression )*
	;

instanceOfExpression
    :   relationalExpression ('instanceof' type)?
	;

relationalExpression
    :   shiftExpression ( relationalOp shiftExpression )*
	;
	
relationalOp
	:	('<' '=' | '>' '=' | '<' | '>')
	;

shiftExpression
    :   additiveExpression ( shiftOp additiveExpression )*
	;

        // TODO: need a sem pred to check column on these >>>
shiftOp
	:	('<' '<' | '>' '>' '>' | '>' '>')
	;


additiveExpression
    :   multiplicativeExpression ( ('+' | '-') multiplicativeExpression )*
	;

multiplicativeExpression
    :   unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )*
	;
	
unaryExpression
    :   '+' unaryExpression
    |	'-' unaryExpression
    |   '++' unaryExpression
    |   '--' unaryExpression
    |   unaryExpressionNotPlusMinus
    ;

unaryExpressionNotPlusMinus
    :   '~' unaryExpression
    | 	'!' unaryExpression
    |   castExpression
    |   primary selector* ('++'|'--')?
    ;

castExpression
    :  '(' primitiveType ')' unaryExpression
    |  '(' (type | expression) ')' unaryExpressionNotPlusMinus
    ;

primary
    :	parExpression
    |   nonWildcardTypeArguments
        (explicitGenericInvocationSuffix | 'this' arguments)
    |   'this' ('.' Identifier)* (identifierSuffix)?
    |   'super' superSuffix
    |   literal
    |   'new' creator
    |   Identifier ('.' Identifier)* (identifierSuffix)?
    |   primitiveType ('[' ']')* '.' 'class'
    |   'void' '.' 'class'
	;

identifierSuffix
	:	('[' ']')+ '.' 'class'
	|	('[' expression ']')+ // can also be matched by selector, but do here
    |   arguments
    |   '.' 'class'
    |   '.' explicitGenericInvocation
    |   '.' 'this'
    |   '.' 'super' arguments
    |   '.' 'new' (nonWildcardTypeArguments)? innerCreator
	;
	
creator
	:	nonWildcardTypeArguments? createdName
        (arrayCreatorRest | classCreatorRest)
	;

createdName
	:	Identifier typeArguments?
        ('.' Identifier typeArguments?)*
    |	primitiveType
	;
	
innerCreator
	:	Identifier classCreatorRest
	;

arrayCreatorRest
	:	'['
        (   ']' ('[' ']')* arrayInitializer
        |   expression ']' ('[' expression ']')* ('[' ']')*
        )
	;

classCreatorRest
	:	arguments classBody?
	;
	
explicitGenericInvocation
	:	nonWildcardTypeArguments explicitGenericInvocationSuffix
	;
	
nonWildcardTypeArguments
	:	'<' typeList '>'
	;
	
explicitGenericInvocationSuffix
	:	'super' superSuffix
	|   Identifier arguments
	;
	
selector
	:	'.' Identifier (arguments)?
	|   '.' 'this'
	|   '.' 'super' superSuffix
	|   '.' 'new' (nonWildcardTypeArguments)? innerCreator
	|   '[' expression ']'
	;
	
superSuffix
	:	arguments
	|   '.' Identifier (arguments)?
    ;

arguments
	:	'(' expressionList? ')'
	;

// LEXER

HexLiteral : '0' ('x'|'X') HexDigit+ IntegerTypeSuffix? ;

DecimalLiteral : ('0' | '1'..'9' '0'..'9'*) IntegerTypeSuffix? ;

OctalLiteral : '0' ('0'..'7')+ IntegerTypeSuffix? ;

fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
IntegerTypeSuffix : ('l'|'L') ;

FloatingPointLiteral
    :   ('0'..'9')+ '.' ('0'..'9')* Exponent? FloatTypeSuffix?
    |   '.' ('0'..'9')+ Exponent? FloatTypeSuffix?
    |   ('0'..'9')+ Exponent FloatTypeSuffix?
    |   ('0'..'9')+ Exponent? FloatTypeSuffix
	;

fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

CharacterLiteral
    :   '\'' ( EscapeSequence | ~('\''|'\\') ) '\''
    ;

StringLiteral
    :  '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    ;



fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

ENUM:	'enum' {if ( !enumIsKeyword ) $type=Identifier;}
	;
	
Identifier 
    :   Letter (Letter|JavaIDDigit)*
    ;

/**I found this char range in JavaCC's grammar, but Letter and Digit overlap.
   Still works, but...
 */
fragment
Letter
    :  '\u0024' |
       '\u0041'..'\u005a' |
       '\u005f' |
       '\u0061'..'\u007a' |
       '\u00c0'..'\u00d6' |
       '\u00d8'..'\u00f6' |
       '\u00f8'..'\u00ff' |
       '\u0100'..'\u1fff' |
       '\u3040'..'\u318f' |
       '\u3300'..'\u337f' |
       '\u3400'..'\u3d2d' |
       '\u4e00'..'\u9fff' |
       '\uf900'..'\ufaff'
    ;

fragment
JavaIDDigit
    :  '\u0030'..'\u0039' |
       '\u0660'..'\u0669' |
       '\u06f0'..'\u06f9' |
       '\u0966'..'\u096f' |
       '\u09e6'..'\u09ef' |
       '\u0a66'..'\u0a6f' |
       '\u0ae6'..'\u0aef' |
       '\u0b66'..'\u0b6f' |
       '\u0be7'..'\u0bef' |
       '\u0c66'..'\u0c6f' |
       '\u0ce6'..'\u0cef' |
       '\u0d66'..'\u0d6f' |
       '\u0e50'..'\u0e59' |
       '\u0ed0'..'\u0ed9' |
       '\u1040'..'\u1049'
   ;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;
