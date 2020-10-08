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
 * @description 简单工厂
 */
class SimpleFactory {

}


class SimpleFactoryRuleConfigSource {
    public RuleConfig load(String ruleConfigFilePath) throws Throwable {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
        IRuleConfigParser parser = null;
        if ("json".equalsIgnoreCase(ruleConfigFileExtension)) {
            parser = new JsonRuleConfigParser();
        } else if ("xml".equalsIgnoreCase(ruleConfigFileExtension)) {
            parser = new XmlRuleConfigParser();
        } else if ("yaml".equalsIgnoreCase(ruleConfigFileExtension)) {
            parser = new YamlRuleConfigParser();
        } else if ("properties".equalsIgnoreCase(ruleConfigFileExtension)) {
            parser = new PropertiesRuleConfigParser();
        } else {
            throw new Throwable(
                "Rule config file format is not supported: " + ruleConfigFilePath);
        }

        String configText = "";
        //从ruleConfigFilePath文件中读取配置文本到configText中
        RuleConfig ruleConfig = parser.parse(configText);
        return ruleConfig;
    }

    private String getFileExtension(String filePath) {
        //...解析文件名获取扩展名，比如rule.json，返回json
        return "json";
    }

    /**
     * 第一次重构的代码 抽离 createParser() 方法
     */
    public RuleConfig loadNew(String ruleConfigFilePath) throws Throwable {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
        IRuleConfigParser parser = createParser(ruleConfigFileExtension);
        if (parser == null) {
            throw new Throwable("Rule config file format is not supported: " + ruleConfigFilePath);
        }
        String configText = ""; //从ruleConfigFilePath文件中读取配置文本到configText中
        return parser.parse(configText);
    }

    private IRuleConfigParser createParser(String configFormat) {
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

