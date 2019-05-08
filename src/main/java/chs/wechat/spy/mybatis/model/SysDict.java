package chs.wechat.spy.mybatis.model;

public class SysDict {
    private String id;

    private String dic_type;

    private String dic_name;

    private String dic_value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDic_type() {
        return dic_type;
    }

    public void setDic_type(String dic_type) {
        this.dic_type = dic_type == null ? null : dic_type.trim();
    }

    public String getDic_name() {
        return dic_name;
    }

    public void setDic_name(String dic_name) {
        this.dic_name = dic_name == null ? null : dic_name.trim();
    }

    public String getDic_value() {
        return dic_value;
    }

    public void setDic_value(String dic_value) {
        this.dic_value = dic_value == null ? null : dic_value.trim();
    }
}