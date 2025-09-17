package org.dromara.okx.util;

import cn.hutool.core.collection.CollUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.common.core.utils.StreamUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 数据同步工具类
 * 提供通用的数据同步功能，支持任意数据接口对象和实体对象
 * 
 * @author Generated
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataSyncUtil {

    /**
     * 获取需要新增到数据库的数据
     * 
     * @param apiDataList      接口数据列表
     * @param dbDataList       数据库数据列表  
     * @param <T>              数据类型
     * @return 需要新增到数据库的数据列表
     */
    public static <T> List<T> getDataToInsert(Collection<T> apiDataList, Collection<T> dbDataList) {
        if (CollUtil.isEmpty(apiDataList)) {
            return CollUtil.newArrayList();
        }
        
        if (CollUtil.isEmpty(dbDataList)) {
            return CollUtil.newArrayList(apiDataList);
        }
        
        // 将数据库数据转换为以key为键的Map
        Map<String, T> dbDataMap = StreamUtils.toIdentityMap(dbDataList, KeyUtil::key);
        
        // 筛选出接口数据中不存在于数据库的数据
        return apiDataList.stream()
                .filter(apiData -> !dbDataMap.containsKey(KeyUtil.key(apiData)))
                .collect(Collectors.toList());
    }

    /**
     * 获取需要更新的数据库数据
     * 
     * @param apiDataList      接口数据列表
     * @param dbDataList       数据库数据列表
     * @param <T>              数据类型  
     * @return 需要更新的数据库数据列表
     */
    public static <T> List<T> getDataToUpdate(Collection<T> apiDataList, Collection<T> dbDataList) {
        if (CollUtil.isEmpty(apiDataList) || CollUtil.isEmpty(dbDataList)) {
            return CollUtil.newArrayList();
        }
        
        // 将接口数据转换为以key为键的Map
        Map<String, T> apiDataMap = StreamUtils.toIdentityMap(apiDataList, KeyUtil::key);
        
        // 筛选出存在于接口数据中的数据库数据（这些数据需要更新）
        return dbDataList.stream()
                .filter(dbData -> apiDataMap.containsKey(KeyUtil.key(dbData)))
                .collect(Collectors.toList());
    }

    /**
     * 获取需要从数据库中删除的数据
     * 
     * @param apiDataList      接口数据列表
     * @param dbDataList       数据库数据列表
     * @param <T>              数据类型
     * @return 需要从数据库中删除的数据列表  
     */
    public static <T> List<T> getDataToDelete(Collection<T> apiDataList, Collection<T> dbDataList) {
        if (CollUtil.isEmpty(dbDataList)) {
            return CollUtil.newArrayList();
        }
        
        if (CollUtil.isEmpty(apiDataList)) {
            return CollUtil.newArrayList(dbDataList);
        }
        
        // 将接口数据转换为以key为键的Map
        Map<String, T> apiDataMap = StreamUtils.toIdentityMap(apiDataList, KeyUtil::key);
        
        // 筛选出数据库中不存在于接口数据中的数据
        return dbDataList.stream()
                .filter(dbData -> !apiDataMap.containsKey(KeyUtil.key(dbData)))
                .collect(Collectors.toList());
    }
}