package com.ochafik.lang.jnaerator.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ochafik.lang.SyntaxUtils;

public abstract class Identifier extends Element implements Comparable<Object> {
	public enum QualificationSeparator {
		Colons("::"),
		Dot(".");
		
		private QualificationSeparator(String s) {
			this.s = s;
		}
		private String s;
		@Override
		public String toString() {
			return s;
		}
	}
	public QualifiedIdentifier derive(QualificationSeparator separator, Identifier... subIdentifiers) {
		QualifiedIdentifier qi = new QualifiedIdentifier(separator);
		qi.add(this);
		for (Identifier i : subIdentifiers)
			qi.add(i);
		return qi;
	}
	public abstract boolean isPlain();
	public abstract SimpleIdentifier resolveLastSimpleIdentifier();
	
	public static class SimpleIdentifier extends Identifier {
		private String name;
		protected List<Expression> templateArguments = new ArrayList<Expression>();
		
		public SimpleIdentifier() {}
		public SimpleIdentifier(String name, Expression... args) {
			setName(name);
			setTemplateArguments(Arrays.asList(args));
		}
		public void addTemplateArgument(Expression x) {
			if (x == null)
				return;
			x.setParentElement(this);
			templateArguments.add(x);
		}
		public List<Expression> getTemplateArguments() {
			return unmodifiableList(templateArguments);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setTemplateArguments(List<Expression> templateArguments) {
			changeValue(this, this.templateArguments, templateArguments);
		}
		@Override
		public void accept(Visitor visitor) {
			visitor.visitSimpleIdentifier(this);
		}
		@Override
		public Element getNextChild(Element child) {
			return getNextSibling(templateArguments, child);
		}
		@Override
		public Element getPreviousChild(Element child) {
			return getPreviousSibling(templateArguments, child);
		}
		@Override
		public boolean replaceChild(Element child, Element by) {
			return replaceChild(templateArguments, Expression.class, this, child, by);
		}
		@Override
		public String toString(CharSequence indent) {
			if (templateArguments.isEmpty())
				return name == null ? "" : name;
			
			String args = implode(templateArguments, ", ", indent);
			return name + "<" + args + (args.endsWith(">") ? " " : "") + ">";
		}
		@Override
		public boolean isPlain() {
			return templateArguments.isEmpty();
		}
		@Override
		public SimpleIdentifier resolveLastSimpleIdentifier() {
			return this;
		}
	}
	public static class QualifiedIdentifier extends Identifier {
		private List<SimpleIdentifier> identifiers = new ArrayList<SimpleIdentifier>();
		private QualificationSeparator separator;
		public List<SimpleIdentifier> getIdentifiers() {
			return unmodifiableList(identifiers);
		}
		public void add(Identifier identifier) {
			if (identifier instanceof SimpleIdentifier)
				addIdentifier((SimpleIdentifier)identifier);
			else {
				QualifiedIdentifier oqi = (QualifiedIdentifier)identifier;
				if (!SyntaxUtils.equal(oqi.getSeparator(), separator))
					throw new IllegalArgumentException("Attempting to derive qualified identifier " + this + " with mismatching separator " + separator + " and sub-name " + identifier);
				
				addIdentifiers(oqi.getIdentifiers());
			}
		}
		public QualifiedIdentifier() {}
		public QualifiedIdentifier(QualificationSeparator separator) {
			setSeparator(separator);
		}
		public void setSeparator(QualificationSeparator separator) {
			this.separator = separator;
		}
		public QualificationSeparator getSeparator() {
			return separator;
		}
		public void setIdentifiers(List<SimpleIdentifier> identifiers) {
			changeValue(this, this.identifiers, identifiers);
		}
		@Override
		public void accept(Visitor visitor) {
			visitor.visitQualifiedIdentifier(this);
		}
		@Override
		public Element getNextChild(Element child) {
			return getNextSibling(identifiers, child);
		}
		@Override
		public Element getPreviousChild(Element child) {
			return getPreviousSibling(identifiers, child);
		}
		@Override
		public boolean replaceChild(Element child, Element by) {
			return replaceChild(identifiers, SimpleIdentifier.class, this, child, by);
		}
		@Override
		public String toString(CharSequence indent) {
			return implode(identifiers, String.valueOf(separator), indent);
		}
		public void addIdentifiers(List<SimpleIdentifier> is) {
			for (SimpleIdentifier i : is)
				addIdentifier(i);
		}
			
		public void addIdentifier(SimpleIdentifier i) {
			if (i == null)
				return;
			i.setParentElement(this);
			identifiers.add(i);
		}
		@Override
		public boolean isPlain() {
			return identifiers.size() == 1 && identifiers.get(0).isPlain();
		}
		@Override
		public SimpleIdentifier resolveLastSimpleIdentifier() {
			return identifiers.isEmpty() ? null : identifiers.get(identifiers.size() - 1);
		}
	}

	public int compareTo(Object o) {
		String s = toString();
		if (s == null) {
			return o == null ? 0 : -1;
		}
		return s.compareTo(String.valueOf(o));
	}
	@Override
	public boolean equals(Object obj) {
		return compareTo(obj) == 0;
	}
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
