package cn.stylefeng.guns.modular.demos.service;

import cn.stylefeng.guns.modular.device.entity.Device;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.guns.sys.core.util.SpringUtil;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/11/13
 * @since JDK 1.8
 */
@Service
public class AsyncService {
    @Async
    public void insertTokenToSql(Token token){
        TokenService tokenService = SpringUtil.getBean(TokenService.class);
        tokenService.save(token);
    }

    /**
     * 先删除在新增token
     * @param delNum 删除数量
     * @param cardId 卡密id
     * @param tokenList token集合
     */
    @Async
    public void delAndInsertToken(Integer delNum, Long cardId, List<Token> tokenList){
        TokenService tokenService = SpringUtil.getBean(TokenService.class);
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        //先删除然后新增一个
        for (int i = 0; i < delNum; i++) {
            redisUtil.hdel(RedisType.TOKEN.getCode() + cardId, tokenList.get(i).getToken());
            tokenService.deleteByToken(tokenList.get(i).getToken());
        }
    }

    @Async
    public void insertDevice(Device device) {
        DeviceService deviceService = SpringUtil.getBean(DeviceService.class);
        deviceService.save(device);
    }

    /**
     * 更新token和缓存
     * @param cardId 卡密id
     * @param token token
     */
    @Async
    public void updateTokenAndRedis(Long cardId, Token token, Integer clearSpace, Date expireTime) {
        TokenService tokenService = SpringUtil.getBean(TokenService.class);
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        redisUtil.hset(RedisType.TOKEN.getCode() + cardId,token.getToken(),token, CardDateUtil.getClearSpace(expireTime,clearSpace));
        tokenService.updateById(token);
    }
}
