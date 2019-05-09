package chs.wechat.spy.mybatis.model;

public class Contact {
    private String id;

    private String userId;

    private String contractId;

    private String contractName;

    private String remark;

    private String loc;

    private Integer sex;

    private String stranger;

    private String signature;

    private String addSource;

    private String smallHeadUrl;

    private String bigHeadUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc == null ? null : loc.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getStranger() {
        return stranger;
    }

    public void setStranger(String stranger) {
        this.stranger = stranger == null ? null : stranger.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getAddSource() {
        return addSource;
    }

    public void setAddSource(String addSource) {
        this.addSource = addSource == null ? null : addSource.trim();
    }

    public String getSmallHeadUrl() {
        return smallHeadUrl;
    }

    public void setSmallHeadUrl(String smallHeadUrl) {
        this.smallHeadUrl = smallHeadUrl == null ? null : smallHeadUrl.trim();
    }

    public String getBigHeadUrl() {
        return bigHeadUrl;
    }

    public void setBigHeadUrl(String bigHeadUrl) {
        this.bigHeadUrl = bigHeadUrl == null ? null : bigHeadUrl.trim();
    }
}