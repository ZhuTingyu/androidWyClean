package com.zhu.designpatterns.factory.factoryExp;

import com.zhu.designpatterns.factory.factoryExp.demo.IRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.ISystemConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.JsonRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.JsonSystemConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.XmlRuleConfigParser;
import com.zhu.designpatterns.factory.factoryExp.demo.XmlSystemConfigParser;

/**
 * @author Quiet Zhu
 * @date 2020/10/8
 * @description 抽象工厂
 */
class AbstractFactory {
}

interface IConfigParserFactory {
    IRuleConfigParser createRuleParser();
    ISystemConfigParser createSystemParser();
    //此处可以扩展新的parser类型，比如IBizConfigParser
}

 class JsonConfigParserFactory implements IConfigParserFactory {
    @Override
    public IRuleConfigParser createRuleParser() {
        return new JsonRuleConfigParser();
    }

    @Override
    public ISystemConfigParser createSystemParser() {
        return new JsonSystemConfigParser();
    }
}
 class XmlConfigParserFactory implements IConfigParserFactory {
    @Override
    public IRuleConfigParser createRuleParser() {
        return new XmlRuleConfigParser();
    }

    @Override
    public ISystemConfigParser createSystemParser() {
        return new XmlSystemConfigParser();
    }
}

// 省略YamlConfigParserFactory和PropertiesConfigParserFactory代码