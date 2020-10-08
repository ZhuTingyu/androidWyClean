package com.zhu.designpatterns.factory.factoryExp;

import com.zhu.designpatterns.factory.factoryExp.demo.IRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.JsonRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.PropertiesRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.XmlRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.YamlRuleConfigParser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Quiet Zhu
 * @date 2020/10/8
 * @description 获取数据解析类的 工厂模式
 */
class RuleConfigParserFactory {
    public static IRuleConfigParser createParser(String configFormat) {
        IRuleConfigParser parser = null;
        if ("json".equalsIgnoreCase(configFormat)) {
            parser = new JsonRuleConfigParser();
        } else if ("xml".equalsIgnoreCase(configFormat)) {
            parser = new XmlRuleConfigParser();
        } else if ("yaml".equalsIgnoreCase(configFormat)) {
            parser = new YamlRuleConfigParser();
        } else if ("properties".equalsIgnoreCase(configFormat)) {
            parser = new PropertiesRuleConfigParser();
        }
        return parser;
    }
}

/**
 *  加上缓存的
 */
class RuleConfigParserFactoryC {
    private static final Map<String, IRuleConfigParser> cachedParsers = new HashMap<>();

    static {
        cachedParsers.put("json", new JsonRuleConfigParser());
        cachedParsers.put("xml", new XmlRuleConfigParser());
        cachedParsers.put("yaml", new YamlRuleConfigParser());
        cachedParsers.put("properties", new PropertiesRuleConfigParser());
    }

    public static IRuleConfigParser createParser(String configFormat) {
        if (configFormat == null || configFormat.isEmpty()) {
            //返回null还是IllegalArgumentException全凭你自己说了算
            return null;
        }
        return cachedParsers.get(configFormat.toLowerCase());
    }
}
