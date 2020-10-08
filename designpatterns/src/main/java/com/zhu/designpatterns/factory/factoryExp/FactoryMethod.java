package com.zhu.designpatterns.factory.factoryExp;

import com.zhu.designpatterns.factory.factoryExp.demo.IRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.JsonRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.PropertiesRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.RuleConfig;
import com.zhu.designpatterns.factory.factoryExp.demo.XmlRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.YamlRuleConfigParser;

/**
 * @author Quiet Zhu
 * @date 2020/10/8
 * @description 工厂方法
 */
class FactoryMethod {
}

interface IRuleConfigParserFactory {
    IRuleConfigParser createParser();
}

class JsonRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() { return new JsonRuleConfigParser(); }
}

class XmlRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() { return new XmlRuleConfigParser(); }
}

class YamlRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() { return new YamlRuleConfigParser(); }
}

class PropertiesRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() { return new PropertiesRuleConfigParser(); }
}

class FactoryMethodRuleConfigSource {
    public RuleConfig load(String ruleConfigFilePath) {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
        IRuleConfigParserFactory parserFactory = null;
        if ("json".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new JsonRuleConfigParserFactory();
        } else if ("xml".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new XmlRuleConfigParserFactory();
        } else if ("yaml".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new YamlRuleConfigParserFactory();
        } else if ("properties".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new PropertiesRuleConfigParserFactory();
        } else {

        }
        IRuleConfigParser parser = parserFactory.createParser();
        String configText = ""; //从ruleConfigFilePath文件中读取配置文本到configText中
        RuleConfig ruleConfig = parser.parse(configText);
        return ruleConfig;
    }

    private String getFileExtension(String filePath) {
        //...解析文件名获取扩展名，比如rule.json，返回json return "json"; }
        return "";
    }
}