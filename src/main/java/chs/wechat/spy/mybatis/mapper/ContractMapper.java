package chs.wechat.spy.mybatis.mapper;

import chs.wechat.spy.mybatis.model.Contract;
import chs.wechat.spy.mybatis.model.ContractWithBLOBs;

public interface ContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractWithBLOBs record);

    int insertSelective(ContractWithBLOBs record);

    ContractWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ContractWithBLOBs record);

    int updateByPrimaryKey(Contract record);
}