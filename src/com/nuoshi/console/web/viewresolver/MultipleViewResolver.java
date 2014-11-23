package com.nuoshi.console.web.viewresolver;

import java.util.Locale;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * <b>www.taofang.com</b> <b>function:</b>
 * 
 * @author lizhenmin
 * @createDate 2011 Jul 25, 2011 11:21:37 AM
 * @email lizm@taofang.com
 * @version 1.0
 */
public class MultipleViewResolver implements ViewResolver {
	private Map<String, ViewResolver> resolvers;
	private String defaultViewResolverName = null;//默认的视图解析器名称

	public void setDefaultViewResolverName(String defaultViewResolverName) {
		this.defaultViewResolverName = defaultViewResolverName;
	}

	public String getDefaultViewResolverName() {
		return defaultViewResolverName;
	}

	public void setResolvers(Map<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
	}

	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		ViewResolver resolver = null;
		String resolverName = null;//视图解析器名称
		if(viewName.equalsIgnoreCase("json")){
			ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
			return mv.getView();
		}
		int resolverNameIndex = viewName.indexOf(":");
		if (resolverNameIndex == (-1)) {
			resolver = getDefaultView();
		} else {

			// 视图解析器名称:
			resolverName = viewName.substring(0,resolverNameIndex);
			if("redirect".equalsIgnoreCase(resolverName)){
				resolver = getDefaultView();
			}else{
				// 取出对应的ViewResolver:
				resolver = resolvers.get(resolverName);
				//视图名称
				viewName = viewName.substring(resolverNameIndex + 1);
			}
		}
		if (resolver != null) {
			/*if(suffix.equalsIgnoreCase("tiles")){
				return resolver.resolveViewName(viewName.substring(0, suffixIndex), locale);
			}else{
				return resolver.resolveViewName(viewName, locale);
			}*/
			return resolver.resolveViewName(viewName, locale);
		}
		// 没有找到对应的ViewResolver就抛异常:
		return null;

	}

	private ViewResolver getDefaultView() {
		return resolvers.get(defaultViewResolverName); // 默认的视图解析器
	}

}
