package chs.wechat.spy.mybatis.model;

import java.util.Date;

public class MsgFile {
    private String id;

    private String user_id;

    private String from_id;

    private String file_name;

    private String ext;

    private Date file_time;

    private byte[] file_blob;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id == null ? null : from_id.trim();
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name == null ? null : file_name.trim();
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

    public Date getFile_time() {
        return file_time;
    }

    public void setFile_time(Date file_time) {
        this.file_time = file_time;
    }

    public byte[] getFile_blob() {
        return file_blob;
    }

    public void setFile_blob(byte[] file_blob) {
        this.file_blob = file_blob;
    }
}