package indi.jl.sp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class SpelParserService {

    @Autowired
    WebApplicationContext wac;

    private ExpressionParser parser;
    private StandardEvaluationContext context;
    private ParserContext parserContext;

    @PostConstruct
    public void init() {
        parser = new SpelExpressionParser();
        context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(wac));
        parserContext = new TemplateParserContext();
    }

    /**
     * 解析spel
     *
     * @param spel spel
     * @return 解析后返回值
     */
    public String parse(String spel) {
        return parse(spel, null, true);
    }

    /**
     * 解析spel
     *
     * @param spel     spel
     * @param vars     参数
     * @param isExpTpl 是否扩展模板
     * @return 解析后的spel
     */
    public String parse(String spel, Map<String, Object> vars, boolean isExpTpl) {
        return parse(spel, vars, isExpTpl, String.class);
    }

    /**
     * 解析spel
     *
     * @param spel spel
     * @param clz  返回值类型
     * @param <T>  泛型
     * @return 解析后的spel
     */
    public <T> T parse(String spel, Class<T> clz) {
        return parse(spel, null, false, clz);
    }

    /**
     * 解析spel
     *
     * @param spel spel
     * @param vars 参数
     * @param clz  返回值类型
     * @param <T>  泛型
     * @return 解析后的spel
     */
    public <T> T parse(String spel, Map<String, Object> vars, Class<T> clz) {
        return parse(spel, vars, false, clz);
    }

    /**
     * 解析spel
     *
     * @param spel     spel
     * @param vars     参数
     * @param isExpTpl 是否扩展模板
     * @param clz      返回值类型
     * @param <T>      泛型
     * @return 解析后的spel
     */
    public <T> T parse(String spel, Map<String, Object> vars, boolean isExpTpl, Class<T> clz) {
        // 不使用 Expression template 时，过滤掉表达式中的单行注释内容
        spel = isExpTpl ? spel : spel.replaceAll("//.*", "");
        if (vars != null) {
            context.setVariables(vars);
        }
        Expression expression = isExpTpl ? parser.parseExpression(spel, parserContext) : parser.parseExpression(spel);
        return expression.getValue(context, clz);
    }

}
