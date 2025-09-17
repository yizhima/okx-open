package org.dromara.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.okx.util.DataSyncUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DataSyncUtil 测试类
 *
 * @author Generated
 */
@SpringBootTest
public class DataSyncUtilTest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestEntity {
        private String id;
        private String name;
        private Integer value;
    }

    @Test
    void testGetDataToInsert() {
        List<TestEntity> apiData = Arrays.asList(
                new TestEntity("1", "api1", 100),
                new TestEntity("2", "api2", 200),
                new TestEntity("3", "api3", 300)
        );

        List<TestEntity> dbData = Arrays.asList(
                new TestEntity("1", "api1", 100), // 已存在
                new TestEntity("4", "db4", 400)   // 不在api中
        );

        List<TestEntity> insertData = DataSyncUtil.getDataToInsert(apiData, dbData);

        assertEquals(2, insertData.size(), "应该有2条数据需要插入");
        assertTrue(insertData.stream().anyMatch(e -> "2".equals(e.getId())), "应该包含id为2的数据");
        assertTrue(insertData.stream().anyMatch(e -> "3".equals(e.getId())), "应该包含id为3的数据");
    }

    @Test
    void testGetDataToInsertWithEmptyApiData() {
        List<TestEntity> apiData = new ArrayList<>();
        List<TestEntity> dbData = Arrays.asList(
                new TestEntity("1", "db1", 100)
        );

        List<TestEntity> insertData = DataSyncUtil.getDataToInsert(apiData, dbData);

        assertTrue(insertData.isEmpty(), "接口数据为空时，不应该有需要插入的数据");
    }

    @Test
    void testGetDataToInsertWithEmptyDbData() {
        List<TestEntity> apiData = Arrays.asList(
                new TestEntity("1", "api1", 100),
                new TestEntity("2", "api2", 200)
        );
        List<TestEntity> dbData = new ArrayList<>();

        List<TestEntity> insertData = DataSyncUtil.getDataToInsert(apiData, dbData);

        assertEquals(2, insertData.size(), "数据库为空时，所有接口数据都应该插入");
    }

    @Test
    void testGetDataToUpdate() {
        List<TestEntity> apiData = Arrays.asList(
                new TestEntity("1", "api1", 100),
                new TestEntity("2", "api2", 200),
                new TestEntity("3", "api3", 300)
        );

        List<TestEntity> dbData = Arrays.asList(
                new TestEntity("1", "api1", 100), // 存在于api中
                new TestEntity("2", "api2", 200), // 存在于api中
                new TestEntity("4", "db4", 400)   // 不存在于api中
        );

        List<TestEntity> updateData = DataSyncUtil.getDataToUpdate(apiData, dbData);

        assertEquals(2, updateData.size(), "应该有2条数据需要更新");
        assertTrue(updateData.stream().anyMatch(e -> "1".equals(e.getId())), "应该包含id为1的数据");
        assertTrue(updateData.stream().anyMatch(e -> "2".equals(e.getId())), "应该包含id为2的数据");
    }

    @Test
    void testGetDataToUpdateWithEmptyData() {
        List<TestEntity> apiData = new ArrayList<>();
        List<TestEntity> dbData = Arrays.asList(
                new TestEntity("1", "db1", 100)
        );

        List<TestEntity> updateData = DataSyncUtil.getDataToUpdate(apiData, dbData);

        assertTrue(updateData.isEmpty(), "接口数据为空时，不应该有需要更新的数据");
    }

    @Test
    void testGetDataToDelete() {
        List<TestEntity> apiData = Arrays.asList(
                new TestEntity("1", "api1", 100),
                new TestEntity("2", "api2", 200)
        );

        List<TestEntity> dbData = Arrays.asList(
                new TestEntity("1", "api1", 100), // 存在于api中
                new TestEntity("3", "db3", 300),  // 不存在于api中
                new TestEntity("4", "db4", 400)   // 不存在于api中
        );

        List<TestEntity> deleteData = DataSyncUtil.getDataToDelete(apiData, dbData);

        assertEquals(2, deleteData.size(), "应该有2条数据需要删除");
        assertTrue(deleteData.stream().anyMatch(e -> "3".equals(e.getId())), "应该包含id为3的数据");
        assertTrue(deleteData.stream().anyMatch(e -> "4".equals(e.getId())), "应该包含id为4的数据");
    }

    @Test
    void testGetDataToDeleteWithEmptyApiData() {
        List<TestEntity> apiData = new ArrayList<>();
        List<TestEntity> dbData = Arrays.asList(
                new TestEntity("1", "db1", 100),
                new TestEntity("2", "db2", 200)
        );

        List<TestEntity> deleteData = DataSyncUtil.getDataToDelete(apiData, dbData);

        assertEquals(2, deleteData.size(), "接口数据为空时，所有数据库数据都应该删除");
    }

    @Test
    void testGetDataToDeleteWithEmptyDbData() {
        List<TestEntity> apiData = Arrays.asList(
                new TestEntity("1", "api1", 100)
        );
        List<TestEntity> dbData = new ArrayList<>();

        List<TestEntity> deleteData = DataSyncUtil.getDataToDelete(apiData, dbData);

        assertTrue(deleteData.isEmpty(), "数据库数据为空时，不应该有需要删除的数据");
    }

    @Test
    void testCompleteDataSyncScenario() {
        // 模拟完整的数据同步场景
        List<TestEntity> apiData = Arrays.asList(
                new TestEntity("1", "updated1", 150), // 更新
                new TestEntity("2", "api2", 200),     // 不变
                new TestEntity("4", "new4", 400)      // 新增
        );

        List<TestEntity> dbData = Arrays.asList(
                new TestEntity("1", "old1", 100),     // 将被更新
                new TestEntity("2", "api2", 200),     // 不变
                new TestEntity("3", "old3", 300)      // 将被删除
        );

        List<TestEntity> toInsert = DataSyncUtil.getDataToInsert(apiData, dbData);
        List<TestEntity> toUpdate = DataSyncUtil.getDataToUpdate(apiData, dbData);
        List<TestEntity> toDelete = DataSyncUtil.getDataToDelete(apiData, dbData);

        assertEquals(1, toInsert.size(), "应该有1条数据需要插入");
        assertEquals("4", toInsert.get(0).getId(), "新增的应该是id为4的数据");

        assertEquals(2, toUpdate.size(), "应该有2条数据需要更新");
        
        assertEquals(1, toDelete.size(), "应该有1条数据需要删除");
        assertEquals("3", toDelete.get(0).getId(), "删除的应该是id为3的数据");
    }
}