package chs.wechat.spy.websdk.api.model;

import chs.wechat.spy.websdk.api.enums.RetCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 心跳检查返回
 *
 * @author biezhi
 * @date 2018/1/20
 */
@Data
@AllArgsConstructor
public class SyncCheckRet {

    private RetCode retCode;
    private int     selector;

}
