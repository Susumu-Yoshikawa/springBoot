package com.tuyano.springboot;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

public class MyPageAttributeTagProcessor extends AbstractAttributeTagProcessor {

	private static final String ATTR_NAME = "mypage";
	private static final int PRECEDENCE = 10000;
	public static int size = 2;

	public MyPageAttributeTagProcessor(final String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
	}

	protected MyPageAttributeTagProcessor(
			TemplateMode templateMode,
			String dialectPrefix, String elementName,
			boolean prefixElementName,
			String attributeName,
			boolean prefixAttributeName,
			int precedence,
			boolean removeAttribute) {
		super(templateMode,dialectPrefix,elementName,prefixElementName,attributeName,prefixAttributeName,precedence,removeAttribute);

	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		final IEngineConfiguration configuration = context.getConfiguration();
		final IStandardExpressionParser parser =
				StandardExpressions.getExpressionParser(configuration);
		final IStandardExpression expression = parser.parseExpression(context, attributeValue);

		int value = (int)expression.execute(context);
		value = value < 0 ? 0 : value;
		structureHandler.setAttribute("href", "/page?size="+size + "&page="+value);

	}

}
