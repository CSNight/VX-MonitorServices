package chs.wechat.spy.utils;

public class DownloadItem {
    private String url;
    private String table;
    private String field;
    private String id;

    public DownloadItem(String id, String table, String url, String field) {
        this.url = url;
        this.field = field;
        this.table = table;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
