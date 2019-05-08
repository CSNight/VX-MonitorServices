package chs.wechat.spy.mybatis.mapper;

import chs.wechat.spy.mybatis.model.PublicContract;
import chs.wechat.spy.mybatis.model.PublicContractWithBLOBs;

public interface PublicContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(PublicContractWithBLOBs record);

    int insertSelective(PublicContractWithBLOBs record);

    PublicContractWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PublicContractWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PublicContractWithBLOBs record);

    int updateByPrimaryKey(PublicContract record);
}