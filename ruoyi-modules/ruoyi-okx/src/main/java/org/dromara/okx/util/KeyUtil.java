package org.dromara.okx.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * 数据同步Key工具类
 * 
 * @author Generated
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KeyUtil {
    
    /**
     * 生成数据同步的唯一标识Key
     * 根据对象的所有字段值生成唯一标识，用于数据同步对比
     * 
     * @param obj 需要生成key的对象
     * @return 生成的key字符串
     */
    public static String key(Object obj) {
        if (ObjectUtil.isNull(obj)) {
            return "";
        }
        
        Field[] fields = ReflectUtil.getFields(obj.getClass());
        if (fields.length == 0) {
            return obj.toString();
        }
        
        // 按字段名排序确保key的一致性
        Arrays.sort(fields, (f1, f2) -> f1.getName().compareTo(f2.getName()));
        
        StringJoiner joiner = new StringJoiner("|");
        for (Field field : fields) {
            try {
                Object value = ReflectUtil.getFieldValue(obj, field);
                String valueStr = ObjectUtil.isNull(value) ? "" : value.toString();
                joiner.add(field.getName() + ":" + valueStr);
            } catch (Exception e) {
                // 忽略无法访问的字段
            }
        }
        
        return joiner.toString();
    }
}