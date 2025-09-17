package org.dromara.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.okx.util.KeyUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * KeyUtil 测试类
 * 
 * @author Generated
 */
@SpringBootTest
public class KeyUtilTest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestEntity {
        private String id;
        private String name;
        private Integer value;
    }

    @Test
    void testKeyGenerationConsistency() {
        TestEntity entity1 = new TestEntity("1", "test", 100);
        TestEntity entity2 = new TestEntity("1", "test", 100);
        
        String key1 = KeyUtil.key(entity1);
        String key2 = KeyUtil.key(entity2);
        
        assertEquals(key1, key2, "相同内容的对象应该生成相同的key");
    }

    @Test
    void testKeyGenerationDifference() {
        TestEntity entity1 = new TestEntity("1", "test", 100);
        TestEntity entity2 = new TestEntity("2", "test", 100);
        
        String key1 = KeyUtil.key(entity1);
        String key2 = KeyUtil.key(entity2);
        
        assertNotEquals(key1, key2, "不同内容的对象应该生成不同的key");
    }

    @Test
    void testNullObject() {
        String key = KeyUtil.key(null);
        assertEquals("", key, "null对象应该返回空字符串");
    }

    @Test
    void testNullFields() {
        TestEntity entity = new TestEntity(null, null, null);
        String key = KeyUtil.key(entity);
        
        assertNotNull(key, "包含null字段的对象应该能生成key");
        assertTrue(key.length() > 0, "生成的key不应该为空");
    }

    @Test
    void testEmptyStringFields() {
        TestEntity entity = new TestEntity("", "", 0);
        String key = KeyUtil.key(entity);
        
        assertNotNull(key, "包含空字符串的对象应该能生成key");
        assertTrue(key.length() > 0, "生成的key不应该为空");
    }

    @Test
    void testKeyContainsFieldNames() {
        TestEntity entity = new TestEntity("1", "test", 100);
        String key = KeyUtil.key(entity);
        
        assertTrue(key.contains("id"), "key应该包含字段名id");
        assertTrue(key.contains("name"), "key应该包含字段名name");  
        assertTrue(key.contains("value"), "key应该包含字段名value");
    }
}